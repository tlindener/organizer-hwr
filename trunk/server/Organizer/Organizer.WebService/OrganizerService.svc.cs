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
            calendarEntries.Add(new CalendarEntry() { Owner = owner, StartDate = DateTime.Now, EndDate = DateTime.Now.AddHours(3) });
            Calendar cal = new Calendar()
            {
                Owner = owner,
                CalendarEntries = calendarEntries


            };

            timeplanner.AddCalendar(cal);


        }

        public IEnumerable<WebUser> GetAllUser()
        {
            throw new NotImplementedException();
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
                CalendarId = calendar.CalendarId,
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

    }
}
