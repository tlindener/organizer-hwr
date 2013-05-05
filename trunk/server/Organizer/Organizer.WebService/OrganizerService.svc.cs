#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/24/2013
#endregion
#region Usings

using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using Organizer.Interfaces;
using Organizer.Interfaces.Json;

#endregion

namespace Organizer.WebService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service1.svc or Service1.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(IncludeExceptionDetailInFaults = false)]
    public class OrganizerService : IOrganizerService
    {

        public void InsertTestData()
        {
            var calendarEntries = new List<CalendarEntry>();

            var owner = new Organizer.Interfaces.User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress = "tobias.lindener@gmail.com",
                PhoneNumber = "01773071234",
                Password = "Test",
                UserName = "Tobias"


            };

            var user = new Organizer.Interfaces.User()
            {
                GivenName = "Hans",
                Surname = "Dieter",
                MailAddress = "tobias.lindener@gmail.com",
                PhoneNumber = "01773071234",
                Password = "Test",
                UserName = "Dieter"


            };
            List<User> invitees = new List<User>();
            invitees.Add(user);
            calendarEntries.Add(new CalendarEntry() { Owner = owner, Title = "Arbeit", StartDate = DateTime.Now, EndDate = DateTime.Now.AddHours(3) });
            Calendar cal = new Calendar()
            {
                Owner = owner,
                Name = "MyCalendar",
                CalendarEntries = calendarEntries


            };

            timeplanner.AddCalendar(cal);

            var entry = new CalendarEntry() { Invitees = invitees, CalendarId = timeplanner.GetAllCalendar().First().CalendarId, Owner = owner, StartDate = DateTime.Now, EndDate = DateTime.Now.AddHours(24) };
            timeplanner.AddCalendarEntry(entry);

        }
        Organizer.TimePlanner timeplanner;
        public OrganizerService()
        {
            timeplanner = new TimePlanner();
        }


        #region Calendar
        public ICollection<WebCalendar> GetAllCalendar()
        {
            var calendar = timeplanner.GetAllCalendar();
            return calendar.Select(p => p.ToWebCalendar()).ToList();
        }

        public WebCalendar GetCalendarById(int calendarId)
        {
            return timeplanner.GetCalendarById(calendarId).ToWebCalendar();
        }

        public int AddCalendar(int ownerId, string name, string description)
        {
            return timeplanner.AddCalendar(new Calendar()
            {
                Name = name,
                Description = description,
                Owner = timeplanner.GetUserById(ownerId)

            });
        }

        public int AddCalendarByObject(WebCalendar calendar)
        {
            return timeplanner.AddCalendar(new Calendar()
            {
                Name = calendar.Name,
                Description = calendar.Description,
                Owner = timeplanner.GetUserById(calendar.OwnerId)

            });
        }

        public bool RemoveEntryFromCalendar(int calendarId, int calendarEntryId)
        {
            return timeplanner.RemoveEntryFromCalendar(calendarId, calendarEntryId);
        }

        public bool RemoveCalendar(int calendarId)
        {
            return timeplanner.RemoveCalendar(calendarId);
        }
        #endregion

        #region CalendarEntries

        public ICollection<WebCalendarEntry> GetAllCalendarEntriesByOwnerId(int ownerId)
        {
            return timeplanner.GetAllEntriesByOwner(ownerId).Select(p => p.ToWebCalendarEntry()).ToList();
        }

        public WebCalendarEntry GetCalendarEntryById(int calendarEntryId)
        {
            return timeplanner.GetCalendarEntryById(calendarEntryId).ToWebCalendarEntry();
        }

        public ICollection<WebCalendarEntry> GetAllCalendarEntriesByRoomId(int roomId)
        {
            return timeplanner.GetEntriesByRoom(roomId).Select(p => p.ToWebCalendarEntry()).ToList();
        }

        public int AddCalendarEntryByObject(WebCalendarEntry calendarEntry)
        {
            var calendar = timeplanner.GetCalendarById(calendarEntry.CalendarId);

            var owner = timeplanner.GetUserById(calendarEntry.OwnerId);
            var room = timeplanner.GetRoomById(calendarEntry.RoomId);
            CalendarEntry entry = new CalendarEntry()
            {
                StartDate = calendarEntry.StartDate,
                EndDate = calendarEntry.EndDate,
                Description = calendarEntry.Description,
                Owner = owner,
                Room = room,
                Calendar = calendar

            };
            return timeplanner.AddCalendarEntry(entry);

        }
        public int AddCalendarEntry(string title, string description, DateTime startDate, DateTime endDate, int ownerId, int roomId, int calendarId)
        {
            var owner = timeplanner.GetUserById(ownerId);
            var room = timeplanner.GetRoomById(roomId);
            var calendar = timeplanner.GetCalendarById(calendarId);

            CalendarEntry calendarEntry = new CalendarEntry()
            {
                Description = description,
                Title = title,
                StartDate = startDate,
                EndDate = endDate,
                Calendar = calendar,
                CalendarId = calendarId,
                Owner = owner,
                Room = room


            };
            return timeplanner.AddCalendarEntry(calendarEntry);
        }


        #endregion


        #region User
        public ICollection<WebUser> GetAllUser()
        {
            var users = timeplanner.GetAllUser();
            return users.Select(p => p.ToWebUser()).ToList();
        }

        public WebUser GetUserById(int userId)
        {
            return timeplanner.GetUserById(userId).ToWebUser();
        }

        public int AddUserByObject(WebUser user, string password)
        {

            return timeplanner.AddUser(new User()
            {
                UserName = user.UserName,
                Surname = user.Surname,
                GivenName = user.GivenName,
                PhoneNumber = user.PhoneNumber,
                MailAddress = user.MailAddress,
                Password = password
            });
        }

        public int AddUser(string givenName, string surname, string mailAddress, string phoneNumber, string userName, string password)
        {
            User user = new User()
            {
                GivenName = givenName,
                Surname = surname,
                MailAddress = mailAddress,
                PhoneNumber = phoneNumber,
                UserName = userName,
                Password = password
            };
            return timeplanner.AddUser(user);
        }

        public bool RemoveUser(int userId, string adminAuth)
        {
            if (ValidateAdmin(adminAuth))
            {
                return timeplanner.RemoveUser(userId);
            }
            return false;
        }

        #endregion

        #region Room
        public ICollection<WebRoom> GetAllRooms()
        {
            return timeplanner.GetAllRooms().Select(p => p.ToWebRoom()).ToList();
        }

        public WebRoom GetRoomById(int roomId)
        {
            return timeplanner.GetRoomById(roomId).ToWebRoom();
        }
        public int AddRoom(string description, string location, int seats)
        {
            Room room = new Room()
            {
                Description = description,
                Location = location,
                Seats = seats
            };
            return timeplanner.AddRoom(room);
        }
        public int AddRoomByObject(WebRoom room)
        {
            Room dbRoom = new Room();
            return timeplanner.AddRoom(dbRoom);
        }

        public bool RemoveRoom(int roomId, string adminAuth)
        {
            if (ValidateAdmin(adminAuth))
            {
                return timeplanner.RemoveRoom(roomId);
            }
            return false;
        }

        public bool ChangeRoomForCalendarEntry(int roomId, int calendarEntryId)
        {
            return timeplanner.ChangeRoomForCalendarEntry(roomId, calendarEntryId);
        }
        #endregion

        #region Group
        public WebGroup GetGroupById(int groupId)
        {
            return timeplanner.GetGroupById(groupId).ToWebGroup();
        }

        public ICollection<WebGroup> GetAllGroupsByUserId(int userId)
        {
            var groups = timeplanner.GetGroupsByUserId(userId);
            return groups.Select(p => p.ToWebGroup()).ToList();
        }
        public int AddGroup(string description)
        {
            Group group = new Group()
            {
                Description = description
            };

            return timeplanner.AddGroup(group);
        }
        public int AddGroupByObject(WebGroup group)
        {

            return timeplanner.AddGroup(new Group()
            {
                Description = group.Description
            });
        }
        public bool AddUserToGroup(int groupId, int userId)
        {
            return timeplanner.AddUserToGroup(groupId, userId);
        }

        public bool RemoveUserFromGroup(int groupId, int userId)
        {
            return timeplanner.RemoveUserFromGroup(groupId, userId);
        }

        public bool RemoveGroup(int groupId)
        {
            return timeplanner.RemoveGroup(groupId);
        }


        #endregion



        #region Invites

        public ICollection<WebInvite> GetAllInvitesByUserId(int userId)
        {
            return timeplanner.GetAllInvitesByUserId(userId).Select(p => p.ToWebInvite()).ToList();
        }

        public int AcceptInvite(int inviteId)
        {
            return timeplanner.AcceptInvite(inviteId);
        }

        public int AddInvite(int calendarEntryId, int userId)
        {
            return timeplanner.AddInvite(calendarEntryId, userId);
        }

        public bool RemoveInvite(int calendarEntryId, int userId)
        {
            return timeplanner.RemoveInvite(calendarEntryId, userId);
        }
        #endregion




        public bool ValidateAdmin(string adminAuth)
        {
            return true;
        }









    }


    public static class ExtensionMethods
    {

        public static WebCalendar ToWebCalendar(this Calendar calendar)
        {
            if (calendar == null)
            {
                return null;
            }
            return new WebCalendar()
            {
                OwnerId = calendar.Owner.UserId,
                Id = calendar.CalendarId,
                Name = calendar.Name,
                Description = calendar.Description,
                CalendarEntries = calendar.CalendarEntries.Select(p => p.ToWebCalendarEntry()).ToList()

            };


        }
        public static WebCalendarEntry ToWebCalendarEntry(this CalendarEntry calendarEntry)
        {
            if (calendarEntry == null)
            {
                return null;

            }
            int ownerId = 0;
            int roomId = 0;

            if (calendarEntry.Owner != null)
            {
                ownerId = calendarEntry.Owner.UserId;
            }
            if (calendarEntry.Room != null)
            {
                roomId = calendarEntry.Room.RoomId;
            }


            return new WebCalendarEntry()
            {

                CalendarId = calendarEntry.CalendarEntryId,
                Description = calendarEntry.Description,
                StartDate = calendarEntry.StartDate,
                EndDate = calendarEntry.EndDate,
                Title = calendarEntry.Title,
                Id = calendarEntry.CalendarEntryId,
                RoomId = roomId,
                OwnerId = ownerId,
                Invitees = calendarEntry.Invitees.Select(p => p.ToWebUser()).ToList()
            };


        }
        public static WebUser ToWebUser(this User user)
        {

            if (user == null)
            {
                return null;
            }
            return new WebUser()
            {
                Id = user.UserId,
                UserName = user.UserName,
                GivenName = user.GivenName,
                Surname = user.Surname,
                MailAddress = user.MailAddress,
                PhoneNumber = user.PhoneNumber,
                CalendarIds = user.Calendar.Select(p => p.CalendarId).ToList(),
                GroupIds = user.Groups.Select(p => p.GroupId).ToList(),
                InviteIds = user.Invites.Select(p => p.InviteId).ToList()

            };

        }

        public static WebGroup ToWebGroup(this Group group)
        {

            if (group == null)
            {
                return null;
            }

            return new WebGroup()
            {
                Description = group.Description,
                Id = group.GroupId,
                Members = group.Members

            };

        }


        public static WebRoom ToWebRoom(this Room room)
        {

            if (room == null)
            {
                return null;
            }

            return new WebRoom()
            {
                Id = room.RoomId,
                Description = room.Description,
                Location = room.Location,
                Seats = room.Seats

            };
        }

        public static WebInvite ToWebInvite(this Invite invite)
        {
            if (invite == null)
            {
                return null;
            }
            return new WebInvite()
            {
                Id = invite.InviteId,
                CalendarEntryId = invite.CalendarEntry.CalendarEntryId,
                Accepted = invite.Accepted,
                OwnerId = invite.Owner.UserId
            };

        }
    }
}
