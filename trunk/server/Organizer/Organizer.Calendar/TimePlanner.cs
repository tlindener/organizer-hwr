using log4net;
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
        ILog logger = null;
        public TimePlanner()
        {
            log4net.Config.XmlConfigurator.Configure();
            logger = LogManager.GetLogger(typeof(TimePlanner));

            calendarDatabase = new CalendarContext();
        }

        #region Calendar
        /// <summary>
        /// Adds a new calendar item to database
        /// </summary>
        /// <param name="calendar"></param>
        /// <returns>Boolean value which indicates the success of the action</returns>
        public bool AddNewCalendar(Calendar calendar)
        {

            if (Utils.isCalendarValid(calendar))
            {
                try
                {
                    calendarDatabase.Calendar.Add(calendar);
                    calendarDatabase.SaveChanges();
                    return true;
                }
                catch (Exception ex)
                {
                    logger.Error(ex.ToString());
                    return false;
                }
            }
            return false;
        }
        /// <summary>
        /// Returns a collection of calendar items
        /// </summary>
        /// <returns></returns>
        public ICollection<Calendar> GetAllCalendar()
        {
            try
            {
                return calendarDatabase.Calendar.ToList();
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return null;
        }
        /// <summary>
        /// Returns a single calendar item based on the chosen id
        /// </summary>
        /// <param name="calendarId"></param>
        /// <returns></returns>
        public Calendar GetCalendarById(int calendarId)
        {
            try
            {
                return calendarDatabase.Calendar.Find(calendarId);
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return null;
        }
        #endregion

        #region CalendarEntry
        /// <summary>
        /// Returns a calendar entry based on calenEntryId.
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <returns></returns>
        public CalendarEntry GetCalendarEntryById(int calendarEntryId)
        {
            try
            {
                return calendarDatabase.CalendarEntries.Find(calendarEntryId);
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return null;
        }
        /// <summary>
        /// Adds a calendar entry to the specified calendar
        /// </summary>
        /// <param name="entry"></param>
        /// <returns></returns>
        public bool AddEntryToCalendar(CalendarEntry entry)
        {
            try
            {
                var calendar = calendarDatabase.Calendar.Find(entry.CalendarId);
                if (calendar == null)
                {
                    return false;
                }
                calendar.CalendarEntries.Add(entry);
                calendarDatabase.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return false;

        }

        /// <summary>
        /// Returns all calendar entries of specified owner
        /// </summary>
        /// <param name="ownerId"></param>
        /// <returns></returns>
        public ICollection<CalendarEntry> GetAllEntriesByOwner(int ownerId)
        {
            try
            {
                return calendarDatabase.CalendarEntries.Where(p => p.Owner.UserId == ownerId).ToList();
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return null;
        }
        /// <summary>
        /// Returns all calendar entries in specified room
        /// </summary>
        /// <param name="roomId"></param>
        /// <returns></returns>
        public ICollection<CalendarEntry> GetEntriesByRoom(int roomId)
        {
            try
            {
                // Check if given roomId is available
                var room = calendarDatabase.Rooms.Find(roomId);
                if (room == null)
                {
                    return null;
                }
                return calendarDatabase.CalendarEntries.Where(p => p.Room == room).ToList();
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return null;
        }

        /// <summary>
        /// Removes entry from calendar
        /// </summary>
        /// <param name="calendarId"></param>
        /// <param name="entryId"></param>
        /// <returns></returns>
        public bool RemoveEntryFromCalendar(int calendarId, int entryId)
        {

            try
            {
                //checks if calendar and entry is available
                var entry = calendarDatabase.CalendarEntries.Find(entryId);
                var calendar = calendarDatabase.Calendar.Find(calendarId);
                if (entry == null && calendar == null)
                {
                    return false;
                }
                return calendar.CalendarEntries.Remove(entry);
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return false;


        }
        #endregion

        #region User
        /// <summary>
        /// Returns all users
        /// </summary>
        /// <returns></returns>
        public ICollection<User> GetAllUser()
        {
            try
            {
                return calendarDatabase.User.ToList();
            
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return null;

        }
        public User GetUserById(int userId)
        {
            try
            {
                return calendarDatabase.User.Find(userId);
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return null;

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




        /// <summary>
        /// Adds a user to database
        /// </summary>
        /// <param name="dbUser"></param>
        /// <returns></returns>
        public bool AddUser(User dbUser)
        {
            try
            {
                calendarDatabase.User.Add(dbUser);
                calendarDatabase.SaveChanges();
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
                return false;
            }
            return true;
        }
        /// <summary>
        /// Adds a room to database
        /// </summary>
        /// <param name="dbRoom"></param>
        /// <returns></returns>
        public bool AddRoom(Room dbRoom)
        {
            try
            {
                calendarDatabase.Rooms.Add(dbRoom);
                calendarDatabase.SaveChanges();
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
                return false;
            }
            return true;
        }
        /// <summary>
        /// Adds a group to database
        /// </summary>
        /// <param name="dbGroup"></param>
        /// <returns></returns>
        public bool AddGroup(Group dbGroup)
        {
            try
            {
                calendarDatabase.Groups.Add(dbGroup);
                calendarDatabase.SaveChanges();
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
                return false;
            }
            return true;
        }
    }

    public class CalendarContext : DbContext
    {
        public DbSet<Calendar> Calendar { get; set; }
        public DbSet<CalendarEntry> CalendarEntries { get; set; }
        public DbSet<User> User { get; set; }
        public DbSet<Room> Rooms { get; set; }
        public DbSet<Group> Groups { get; set; }
        public DbSet<Invite> Invites { get; set; }
    }
}
