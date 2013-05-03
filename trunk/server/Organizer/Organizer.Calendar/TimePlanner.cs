using log4net;
using Organizer.Interfaces;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
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
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddCalendar(Calendar calendar)
        {

            if (Utils.isCalendarValid(calendar))
            {
                try
                {
                    calendarDatabase.Calendar.Add(calendar);
                    calendarDatabase.SaveChanges();
                    return calendar.CalendarId;
                }
                catch (Exception ex)
                {
                    logger.Error(ex.ToString());
                }
            }
            return 0;
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
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddCalendarEntry(CalendarEntry entry)
        {
            try
            {
                var calendar = calendarDatabase.Calendar.Find(entry.CalendarId);
                if (calendar == null)
                {
                    return 0;
                }
                calendar.CalendarEntries.Add(entry);
                calendarDatabase.SaveChanges();
                return entry.CalendarEntryId;
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return 0;


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

        /// <summary>
        /// Returns a collection of groups
        /// </summary>
        /// <returns></returns>
        public ICollection<Group> GetAllGroups()
        {
            try
            {
                return calendarDatabase.Groups.ToList();
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return null;
        }
        /// <summary>
        /// Returns a group by specified Id
        /// </summary>
        /// <param name="groupId"></param>
        /// <returns></returns>
        public Group GetGroupById(int groupId)
        {
            try
            {
                return calendarDatabase.Groups.Find(groupId);
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return null;
        }

        /// <summary>
        /// Returns a collection of groups by specified user
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        public ICollection<Group> GetGroupsByUserId(int userId)
        {
            try
            {
                var member = calendarDatabase.User.Find(userId);
                if (member == null)
                {
                    return null;
                }
                return calendarDatabase.Groups.Where(p => p.Members == member).ToList();
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return null;
        }
        #endregion




        /// <summary>
        /// Adds a user to database
        /// </summary>
        /// <param name="dbUser"></param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddUser(User dbUser)
        {
            try
            {
                calendarDatabase.User.Add(dbUser);
                calendarDatabase.SaveChanges();
                return dbUser.UserId;
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return 0;

        }
        /// <summary>
        /// Adds a room to database
        /// </summary>
        /// <param name="dbRoom"></param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddRoom(Room dbRoom)
        {
            try
            {
                calendarDatabase.Rooms.Add(dbRoom);
                calendarDatabase.SaveChanges();
                return dbRoom.RoomId;
            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return 0;

        }
        /// <summary>
        /// Adds a group to database
        /// </summary>
        /// <param name="dbGroup"></param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddGroup(Group dbGroup)
        {
            try
            {
                calendarDatabase.Groups.Add(dbGroup);
                calendarDatabase.SaveChanges();
                return dbGroup.GroupId;

            }
            catch (Exception ex)
            {
                logger.Error(ex.ToString());
            }
            return 0;

        }


        //public bool ValidateUser(String userName, string password)
        //{

        //    var user = calendarDatabase.User.Where(p => p.UserName == userName && p.Password == password);
        //    if (user != null && user.First() != null)
        //    {
        //        return true;
        //    }
        //    return false;

        //}


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
