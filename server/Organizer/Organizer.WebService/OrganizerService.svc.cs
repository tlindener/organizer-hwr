﻿#region License
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
using System.Security.Cryptography;

#endregion

namespace Organizer.WebService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service1.svc or Service1.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(IncludeExceptionDetailInFaults = false)]
    public class OrganizerService : IOrganizerService
    {
        private bool ValidateUser(string userAuth)
        {
            var authentication = userAuth.Split('_');
            int userId = 0;
            Int32.TryParse(authentication[0], out userId);
            if (userId == 0)
            {
                return false;
            }
            var user = timeplanner.GetUserById(userId);
            if (user != null)
            {
                var hash = Utils.getSHA512Hash(user.MailAddress);
                var base64String = Utils.EncodeTo64(hash);
                var authenticationString = base64String + user.Password;
                if (authentication[1].Equals(authenticationString))
                {
                    return true;
                }

            }
            return true;
        }


        public WebUser Login(string mailAddress, string password)
        {
            var users = timeplanner.GetAllUser();
            if (users != null && users.Count > 0)
            {
                var user = users.Where(p => p.MailAddress == mailAddress && p.Password == password);
                
                if (user != null && user.Count() > 0)
                {
                    return user.First().ToWebUser();
                }
            }
            return null;
        }


    
        Organizer.TimePlanner timeplanner;
        public OrganizerService()
        {
            timeplanner = new TimePlanner();
        }


        #region Calendar
        public ICollection<WebCalendar> GetAllCalendar(string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            var calendar = timeplanner.GetAllCalendar();
            if (calendar == null)
            {
                return null;
            }
            return calendar.Select(p => p.ToWebCalendar()).ToList();
        }

        public WebCalendar GetCalendarById(int calendarId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            return timeplanner.GetCalendarById(calendarId).ToWebCalendar();
        }

        public int AddCalendar(int ownerId, string name, string description, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return -1;

            return timeplanner.AddCalendar(ownerId,name,description);
              
        }



        public bool RemoveCalendarEntry(int calendarEntryId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return false;

            return timeplanner.RemoveCalendarEntry(calendarEntryId);
        }

        public bool RemoveCalendar(int calendarId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return false;

            return timeplanner.RemoveCalendar(calendarId);
        }
        #endregion

        #region CalendarEntries

        public ICollection<WebCalendarEntry> GetAllCalendarEntriesByOwnerId(int ownerId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            return timeplanner.GetAllEntriesByOwner(ownerId).Select(p => p.ToWebCalendarEntry()).ToList();
        }

        public WebCalendarEntry GetCalendarEntryById(int calendarEntryId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            return timeplanner.GetCalendarEntryById(calendarEntryId).ToWebCalendarEntry();
        }

        public ICollection<WebCalendarEntry> GetAllCalendarEntriesByRoomId(int roomId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            var calendarEntries = timeplanner.GetEntriesByRoom(roomId);

            if (calendarEntries != null && calendarEntries.Count > 0)
            {
                return calendarEntries.Select(p => p.ToWebCalendarEntry()).ToList();
            }
            return null;
        }

        public int AddCalendarEntry(string title, string description, DateTime startDate, DateTime endDate, int ownerId, int roomId, int calendarId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return 0;

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
        public ICollection<WebUser> GetAllUser(string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            var users = timeplanner.GetAllUser();
            return users.Select(p => p.ToWebUser()).ToList();
        }

        public WebUser GetUserById(int userId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            return timeplanner.GetUserById(userId).ToWebUser();
        }



        public int AddUser(string givenName, string surname, string mailAddress, string phoneNumber, string password)
        {
       

            User user = new User()
            {
                GivenName = givenName,
                Surname = surname,
                MailAddress = mailAddress,
                PhoneNumber = phoneNumber,
                Password = password
            };
            return timeplanner.AddUser(user);
        }

        public bool RemoveUser(int userId, string userAuth)
        {
            if (ValidateAdmin(userAuth))
            {
                return timeplanner.RemoveUser(userId);
            }
            return false;
        }

        #endregion

        #region Room
        public ICollection<WebRoom> GetAllRooms(string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            return timeplanner.GetAllRooms().Select(p => p.ToWebRoom()).ToList();
        }

        public WebRoom GetRoomById(int roomId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            return timeplanner.GetRoomById(roomId).ToWebRoom();
        }
        public int AddRoom(string description, string location, int seats, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return 0;

            Room room = new Room()
            {
                Description = description,
                Location = location,
                Seats = seats
            };
            return timeplanner.AddRoom(room);
        }


        public bool RemoveRoom(int roomId, string userAuth)
        {
            if (ValidateAdmin(userAuth))
            {
                return timeplanner.RemoveRoom(roomId);
            }
            return false;
        }

        public bool ChangeRoomForCalendarEntry(int roomId, int calendarEntryId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return false;

            return timeplanner.ChangeRoomForCalendarEntry(roomId, calendarEntryId);
        }
        #endregion

        #region Group
        public WebGroup GetGroupById(int groupId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            return timeplanner.GetGroupById(groupId).ToWebGroup();
        }

        public ICollection<WebGroup> GetAllGroupsByUserId(int userId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            var groups = timeplanner.GetGroupsByUserId(userId);
            if (groups == null)
            {
                return null;
            }
            return groups.Select(p => p.ToWebGroup()).ToList();
        }
        public int AddGroup(string description, string userAuth)
        {
            Group group = new Group()
            {
                Description = description
            };

            return timeplanner.AddGroup(group);
        }

        public bool AddUserToGroup(int groupId, int userId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return false;

            return timeplanner.AddUserToGroup(groupId, userId);
        }

        public bool RemoveUserFromGroup(int groupId, int userId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return false;

            return timeplanner.RemoveUserFromGroup(groupId, userId);
        }

        public bool RemoveGroup(int groupId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return false;

            return timeplanner.RemoveGroup(groupId);
        }


        #endregion



        #region Invites

        public ICollection<WebInvite> GetAllInvitesByUserId(int userId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            return timeplanner.GetAllInvitesByUserId(userId).Select(p => p.ToWebInvite()).ToList();
        }

        public int AcceptInvite(int inviteId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return 0;

            return timeplanner.AcceptInvite(inviteId);
        }

        public WebInvite GetInviteById(int inviteId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return null;

            return timeplanner.GetInviteById(inviteId).ToWebInvite();
        }

        public int AddInvite(int calendarEntryId, int userId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return 0;

            return timeplanner.AddInvite(calendarEntryId, userId);
        }

        public bool RemoveInvite(int inviteId, string userAuth)
        {
            if (!ValidateUser(userAuth))
                return false;

            return timeplanner.RemoveInvite(inviteId);
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
            
            List<int> calendarIds = new List<int>();
            var calendarId = user.CalendarId;
            if (calendarId.HasValue)
            {
                calendarIds.Add(calendarId.Value);
            }

            return new WebUser()
            {

                Id = user.UserId,
                GivenName = user.GivenName,
                Surname = user.Surname,
                CalendarIds = calendarIds,
                MailAddress = user.MailAddress,
                PhoneNumber = user.PhoneNumber,               
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
