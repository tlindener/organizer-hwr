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
    public class OrganizerService : IOrganizerService
    {
        Organizer.TimePlanner timeplanner;

        public OrganizerService()
        {

            timeplanner = new TimePlanner();
        }

        public IEnumerable<WebCalendar> GetAllCalendar()
        {
            var calendar = timeplanner.GetAllCalendar();
            return calendar.Select(p => p.ToJsonCalendar()).ToList();
        }
        public void InsertTestData()
        {
            var calendarEntries = new List<CalendarEntry>();
            var owner = new Organizer.Interfaces.User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress = "tobias.lindener@gmail.com",
                PhoneNumber = "01773071234"

            };
            calendarEntries.Add(new CalendarEntry() { Owner = owner, Title = "Arbeit",StartDate = DateTime.Now, EndDate = DateTime.Now.AddHours(3) });
            Calendar cal = new Calendar()
            {
                Owner = owner,
                Name = "MyCalendar",
                CalendarEntries = calendarEntries


            };

            timeplanner.AddNewCalendar(cal);

            var entry = new CalendarEntry() { Owner = owner, StartDate = DateTime.Now, EndDate = DateTime.Now.AddHours(24) };
            timeplanner.AddEntryToCalendar(timeplanner.GetAllCalendar().First().CalendarId, entry);

        }

        public IEnumerable<WebUser> GetAllUser()
        {
            var users = timeplanner.GetAllUser();
            return users.Select(p => p.ToJsonUser()).ToList();
        }



        public WebCalendar GetCalendarById(int calendarId)
        {
            return timeplanner.GetCalendarById(calendarId).ToJsonCalendar();
        }

        public bool AddCalendarEntryToCalendar(int calendarId, DateTime startDate, DateTime endDate, string description, int ownerId, int roomId)
        {
            var calendar = timeplanner.GetCalendarById(calendarId);
            var owner = timeplanner.GetUserById(ownerId);
            var room = timeplanner.GetRoomById(roomId);
            CalendarEntry calendarEntry = new CalendarEntry()
            {
                StartDate = startDate,
                EndDate = endDate,
                Description = description,
                Owner = owner,
                Room = room,
                Calendar = calendar

            };
            return timeplanner.AddEntryToCalendar(calendarId, calendarEntry);
        }

        public bool RemoveEntryFromCalendar(int calendarId, int entryId)
        {
           return timeplanner.RemoveEntryFromCalendar( calendarId,entryId);
        }

        public WebUser GetUserById(int userId)
        {
            return timeplanner.GetUserById(userId).ToJsonUser();
        }
    }


    public static class ExtensionMethods
    {

        public static WebCalendar ToJsonCalendar(this Calendar calendar)
        {
            if (calendar == null)
            {
                return null;
            }
            WebCalendar newCalendar = new WebCalendar()
            {
                OwnerId = calendar.Owner.UserId,
                Id = calendar.CalendarId,
                Name = calendar.Name,
                Description = calendar.Description,
                CalendarEntries = calendar.CalendarEntries.Select(p => p.ToJsonCalendarEntry()).ToList()
                 
            };
            return newCalendar;

        }
        public static WebCalendarEntry ToJsonCalendarEntry(this CalendarEntry calendarEntry)
        {
            if (calendarEntry == null)
            {
                return null;

            }

            WebCalendarEntry newCalendarEntry = new WebCalendarEntry()
            {
                OwnerId = calendarEntry.Owner.UserId,
                CalendarId = calendarEntry.CalendarEntryId,
                Description = calendarEntry.Description,
                StartDate = calendarEntry.StartDate,
                EndDate = calendarEntry.EndDate,
                Duration = calendarEntry.Duration

            };
            return newCalendarEntry;

        }
        public static WebUser ToJsonUser(this User user)
        {

            if (user == null)
            {
                return null;
            }
            WebUser newUser = new WebUser()
            {
                Id = user.UserId,
                GivenName = user.GivenName,
                Surname = user.Surname,
                MailAddress = user.MailAddress,
                PhoneNumber = user.PhoneNumber,
                CalendarIds = user.Calendar.Select(p => p.CalendarId).ToList(),
                GroupIds = user.Groups.Select(p => p.GroupId).ToList()

            };
            return newUser;
        }

        public static WebGroup ToJsonGroup(this Group group)
        {

            if (group == null)
            {
                return null;
            }

            WebGroup newGroup = new WebGroup()
            {
                Description = group.Description,
                Id = group.GroupId,
                Members = group.Members

            };
            return newGroup;
        }

    }
}
