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

        /// <summary>
        /// Accepts the invitation from the invited user and returns the calendarEntryId for the accepting user
        /// </summary>
        /// <param name="inviteId"></param>
        /// <returns></returns>
        public int AcceptInvite(int inviteId)
        {

            var invite = calendarDatabase.Invites.Find(inviteId);
            invite.CalendarEntry.Owner = invite.Owner;
            invite.Accepted = true;
            calendarDatabase.CalendarEntries.Add(invite.CalendarEntry);
            calendarDatabase.SaveChanges();
            return invite.CalendarEntry.CalendarEntryId;
        }
        /// <summary>
        /// Removes specified calendar
        /// </summary>
        /// <param name="calendarId"></param>
        /// <returns></returns>
        public bool RemoveCalendar(int calendarId)
        {
            var calendar = calendarDatabase.Calendar.Find(calendarId);
            if (calendar == null)
            {
                return false;
            }
            calendarDatabase.Calendar.Remove(calendar);
            calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        /// Removes specified user
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        public bool RemoveUser(int userId)
        {
            var user = calendarDatabase.User.Find(userId);
            if (user == null)
            {
                return false;
            }
            calendarDatabase.User.Remove(user);
            calendarDatabase.SaveChanges();
            return true;
        }
        /// <summary>
        /// Removes specified room
        /// </summary>
        /// <param name="roomId"></param>
        /// <returns></returns>
        public bool RemoveRoom(int roomId)
        {
            var room = calendarDatabase.Rooms.Find(roomId);
            if (room == null)
            {
                return false;
            }
            calendarDatabase.Rooms.Remove(room);
            calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        /// Change specified room in calendar entry
        /// </summary>
        /// <param name="roomId"></param>
        /// <param name="calendarEntryId"></param>
        /// <returns></returns>
        public bool ChangeRoomForCalendarEntry(int roomId, int calendarEntryId)
        {
            var room = calendarDatabase.Rooms.Find(roomId);
            var calendarEntry = calendarDatabase.CalendarEntries.Find(calendarEntryId);
            if (room == null || calendarEntry == null)
            {
                return false;
            }
            calendarEntry.Room = room;
            calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        /// Adds specified user to specified group
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        public bool AddUserToGroup(int groupId, int userId)
        {
            var group = calendarDatabase.Groups.Find(groupId);
            var user = calendarDatabase.User.Find(userId);
            if (group == null || user == null)
            {
                return false;
            }
            group.Members.Add(user);
            calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        /// Removes specified user from group
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        public bool RemoveUserFromGroup(int groupId, int userId)
        {
            var group = calendarDatabase.Groups.Find(groupId);
            var user = calendarDatabase.User.Find(userId);
            if (group == null || user == null)
            {

                return false;
            }
            group.Members.Remove(user);
            calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        /// Removes specified group
        /// </summary>
        /// <param name="groupId"></param>
        /// <returns></returns>
        public bool RemoveGroup(int groupId)
        {
            var group = calendarDatabase.Groups.Find(groupId);
            if (group == null)
            {

                return false;
            }
            calendarDatabase.Groups.Remove(group);
            calendarDatabase.SaveChanges();
            return true;

        }

        /// <summary>
        /// Get all invites of one user
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        public ICollection<Invite> GetAllInvitesByUserId(int userId)
        {
            var user = calendarDatabase.User.Find(userId);
            if (user == null)
            {
                return null;
            }
            return calendarDatabase.Invites.Where(p => p.Owner == user).ToList();
        }

        /// <summary>
        /// Adds a person to invite list of a calendar entry and to table invites
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        public int AddInvite(int calendarEntryId, int userId)
        {
            var calendarEntry = calendarDatabase.CalendarEntries.Find(calendarEntryId);
            var user = calendarDatabase.User.Find(userId);
            if (calendarEntry == null || user == null)
            {
                return 0;
            }
            calendarEntry.Invitees.Add(user);
            Invite invite = new Invite()
            {
                Accepted = false,
                CalendarEntry = calendarEntry,
                Owner = user,
            };
            calendarDatabase.Invites.Add(invite);
            calendarDatabase.SaveChanges();
            return invite.InviteId;
        }

        /// <summary>
        /// Removes a person from list invited people in calendar entry and from table Invites
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        public bool RemoveInvite(int calendarEntryId, int userId)
        {
            var calendarEntry = calendarDatabase.CalendarEntries.Find(calendarEntryId);
            var user = calendarDatabase.User.Find(userId);
            if (calendarEntry == null || user == null)
            {
                return false;
            }
            calendarEntry.Invitees.Remove(user);
            var invitees = calendarDatabase.Invites.Where(p => p.CalendarEntry == calendarEntry && p.Owner == user).ToList();

            if (invitees.Count != 1)
            {
                return false;
            }
            calendarDatabase.Invites.Remove(invitees.First());
            calendarDatabase.SaveChanges();
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
