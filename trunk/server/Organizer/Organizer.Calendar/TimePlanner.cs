using Organizer.Interfaces;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer
{
    public class TimePlanner
    {
        CalendarContext calendarDatabase;

        public TimePlanner()
        {
            calendarDatabase = new CalendarContext();
            Console.WriteLine(calendarDatabase.Database.Connection.ConnectionString);

        }

        #region Calendar
        public bool AddCalendar(Calendar calendar)
        {
            if (isCalendarValid(calendar))
            {
                calendarDatabase.Calendar.Add(calendar);
                calendarDatabase.SaveChanges();
                return true;
            }
            return false;
        }
        private bool isCalendarValid(Calendar calendar)
        {

            if (!String.IsNullOrEmpty(calendar.Owner.Name))
            {
                return true;
            }
            return false;
        }
        public List<Calendar> GetAllCalendar()
        {
            return calendarDatabase.Calendar.ToList();
        }
        #endregion

        #region CalendarEntry
        public List<CalendarEntry> findAppointment(List<Calendar> calendar, DateTime startDate, double duration)
        {

            foreach (Calendar cal in calendar)
            {
                findConflictInCalendar(cal, startDate, duration);
            }
            return null;

        }
        private CalendarEntry findConflictInCalendar(Calendar calendar, DateTime startDate, double duration)
        {

            return null;
        }
        #endregion

    }

    public class CalendarContext : DbContext
    {
        public DbSet<Calendar> Calendar { get; set; }
        public DbSet<CalendarEntry> CalendarEntries { get; set; }
        public DbSet<User> User { get; set; }

    }
}
