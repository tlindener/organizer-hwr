using Organizer.Interfaces;
using Organizer.Interfaces.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

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
            calendarEntries.Add(new CalendarEntry() { Owner = owner, Title = "Arbeit", StartDate = DateTime.Now, EndDate = DateTime.Now.AddHours(3) });
            Calendar cal = new Calendar()
            {
                Owner = owner,
                Name = "MyCalendar",
                CalendarEntries = calendarEntries


            };

            timeplanner.AddNewCalendar(cal);

            var entry = new CalendarEntry() { CalendarId = timeplanner.GetAllCalendar().First().CalendarId, Owner = owner, StartDate = DateTime.Now, EndDate = DateTime.Now.AddHours(24) };
            timeplanner.AddEntryToCalendar(entry);

        }


        Organizer.TimePlanner timeplanner;

        public OrganizerService()
        {
            timeplanner = new TimePlanner();
        }

        public ICollection<WebCalendar> GetAllCalendar()
        {
            var calendar = timeplanner.GetAllCalendar();
            return calendar.Select(p => p.ToWebCalendar()).ToList();
        }


        public ICollection<WebUser> GetAllUser()
        {
            var users = timeplanner.GetAllUser();
            return users.Select(p => p.ToWebUser()).ToList();
        }



        public WebCalendar GetCalendarById(int calendarId)
        {
            return timeplanner.GetCalendarById(calendarId).ToWebCalendar();
        }


        public bool RemoveEntryFromCalendar(int calendarId, int calendarEntryId)
        {
            return timeplanner.RemoveEntryFromCalendar(calendarId, calendarEntryId);
        }

        public WebUser GetUserById(int userId)
        {
            return timeplanner.GetUserById(userId).ToWebUser();
        }


        public ICollection<WebCalendarEntry> GetCalendarEntriesByOwnerId(int ownerId)
        {
            return timeplanner.GetAllEntriesByOwner(ownerId).Select(p => p.ToWebCalendarEntry()).ToList();
        }

        public WebCalendarEntry GetCalendarEntryById(int calendarEntryId)
        {
            return timeplanner.GetCalendarEntryById(calendarEntryId).ToWebCalendarEntry();
        }

        public ICollection<WebRoom> GetAllRooms()
        {
            return timeplanner.GetAllRooms().Select(p => p.ToWebRoom()).ToList();
        }

        public WebRoom GetRoomById(int roomId)
        {
            return timeplanner.GetRoomById(roomId).ToWebRoom();
        }



        public ICollection<WebCalendarEntry> GetEntriesByRoom(int roomId)
        {
            return timeplanner.GetEntriesByRoom(roomId).Select(p => p.ToWebCalendarEntry()).ToList();
        }

        public WebGroup GetGroupById(int groupId)
        {
            return timeplanner.GetGroupById(groupId).ToWebGroup();
        }

        public ICollection<WebGroup> GetGroupsByUserId(int userId)
        {
            var groups = timeplanner.GetGroupsByUserId(userId);
            return groups.Select(p => p.ToWebGroup()).ToList();
        }



        public bool AddNewCalendar(string name, string description, int ownerId)
        {
            return timeplanner.AddNewCalendar(new Calendar()
            {
                Name = name,
                Description = description,
                Owner = timeplanner.GetUserById(ownerId)

            });
        }


        public bool AddNewCalendar(WebCalendar calendar)
        {
            throw new NotImplementedException();
        }

        public bool AddCalendarEntryToCalendar(WebCalendarEntry calendarEntry)
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
            return timeplanner.AddEntryToCalendar(entry);

        }

        public bool AddUser(WebUser user)
        {
            User dbUser = new User();
            return timeplanner.AddUser(dbUser);
        }

        public bool AddRoom(WebRoom room)
        {
            Room dbRoom = new Room();
            return timeplanner.AddRoom(dbRoom);
        }

        public bool AddGroup(WebGroup group)
        {
            Group dbGroup = new Group();
            return timeplanner.AddGroup(dbGroup);
        }


        public ICollection<WebInvite> GetAllInvitesByUserId(int userId)
        {
            throw new NotImplementedException();
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
       
            if(calendarEntry.Owner != null) 
            {
                ownerId =calendarEntry.Owner.UserId;
            }
            if(calendarEntry.Room != null)
            {
                roomId = calendarEntry.Room.RoomId;
            }   
            

            return new WebCalendarEntry()
            {
               
                CalendarId = calendarEntry.CalendarEntryId,
                Description = calendarEntry.Description,
                StartDate = calendarEntry.StartDate,
                EndDate = calendarEntry.EndDate,
                Duration = calendarEntry.Duration,
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
                GivenName = user.GivenName,
                Surname = user.Surname,
                MailAddress = user.MailAddress,
                PhoneNumber = user.PhoneNumber,
                CalendarIds = user.Calendar.Select(p => p.CalendarId).ToList(),
                GroupIds = user.Groups.Select(p => p.GroupId).ToList(),
                InviteIds = user.Invites.Select(p=> p.InviteId).ToList()

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
    }
}
