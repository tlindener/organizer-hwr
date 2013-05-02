using Organizer.Interfaces;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Security.Cryptography;
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
        }

        #region Calendar
        public bool AddNewCalendar(Calendar calendar)
        {

            if (ValidationMethods.isCalendarValid(calendar))
            {
                calendarDatabase.Calendar.Add(calendar);
                calendarDatabase.SaveChanges();
                return true;
            }
            return false;
        }
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

            AddNewCalendar(cal);

            var entry = new CalendarEntry() { Owner = owner, StartDate = DateTime.Now, EndDate = DateTime.Now.AddHours(24) };
            AddEntryToCalendar(GetAllCalendar().First().CalendarId, entry);

        }
        public ICollection<Calendar> GetAllCalendar()
        {
            return calendarDatabase.Calendar.ToList();
        }
        public Calendar GetCalendarById(int calendarId)
        {
            return calendarDatabase.Calendar.Find(calendarId);
        }
        #endregion

        #region CalendarEntry
        public CalendarEntry GetCalendarEntryById(int calendarEntryId)
        {
            return calendarDatabase.CalendarEntries.Find(calendarEntryId);
        }
        public bool AddEntryToCalendar(int calendarId, CalendarEntry entry)
        {

            var calendar = calendarDatabase.Calendar.Find(calendarId);
            if (calendar == null)
            {
                return false;
            }
            calendar.CalendarEntries.Add(entry);
            calendarDatabase.SaveChanges();
            return true;

        }
        public ICollection<CalendarEntry> GetAllEntriesByOwner(int ownerId)
        {
            return calendarDatabase.CalendarEntries.Where(p => p.Owner.UserId == ownerId).ToList();
        }
        public ICollection<CalendarEntry> GetEntriesByRoom(int roomId)
        { 
            var room = calendarDatabase.Rooms.Find(roomId);
    
            if (room == null)
            {
                return null;
            }
            return calendarDatabase.CalendarEntries.Where(p => p.Room == room).ToList();
        }
        public bool RemoveEntryFromCalendar(int calendarId, int entryId)
        {
            var entry = calendarDatabase.CalendarEntries.Find(entryId);
            return calendarDatabase.Calendar.Find(calendarId).CalendarEntries.Remove(entry);
        }
        #endregion

        #region User

        public ICollection<User> GetAllUser()
        {
            return calendarDatabase.User.ToList();
        }
        public User GetUserById(int userId)
        {
            return calendarDatabase.User.Find(userId);
        }

        #endregion

        #region Rooms
        public ICollection<Room> GetAllRooms()
        {
            return calendarDatabase.Rooms.ToList();
        }
        public Room GetRoomById(int roomId)
        {
            return calendarDatabase.Rooms.Find(roomId);
        }

        #endregion
        #region Groups
        public ICollection<Group> GetAllGroups()
        {
            return calendarDatabase.Groups.ToList();
        }
        public Group GetGroupById(int groupId)
        {
            return calendarDatabase.Groups.Find(groupId);
        }
        public ICollection<Group> GetGroupsByUserId(int userId)
        {
            var member = calendarDatabase.User.Find(userId);
            if (member == null)
            {
                return null;
            }
            return calendarDatabase.Groups.Where(p => p.Members == member).ToList();
        }
        #endregion

        public bool ValidateUser(String userName, string password)
        {

            var user = calendarDatabase.User.Where(p => p.UserName == userName && p.Password == password);
            if (user != null && user.First() != null)
            {
                return true;
            }
            return false;

        }
        private static string getSHA512Hash(string text)
        {
            string hash = "";
            SHA512 alg = SHA512.Create();
            byte[] result = alg.ComputeHash(Encoding.UTF8.GetBytes(text));
            hash = Encoding.UTF8.GetString(result);
            return hash;
        }


  
    }

    public class CalendarContext : DbContext
    {
        public DbSet<Calendar> Calendar { get; set; }
        public DbSet<CalendarEntry> CalendarEntries { get; set; }
        public DbSet<User> User { get; set; }      
        public DbSet<Room> Rooms { get; set; }
        public DbSet<Group> Groups { get; set; }
    }
}
