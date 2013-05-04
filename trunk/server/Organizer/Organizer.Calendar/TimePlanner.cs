#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/15/2013
#endregion
#region Usings

using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using Organizer.Interfaces;
using log4net;
using log4net.Config;

#endregion

namespace Organizer
{
    /// <summary>
    ///     Included DatabaseContext for Organizer Database. Offers CRUD methods.
    /// </summary>
    public class TimePlanner
    {
        private readonly CalendarContext _calendarDatabase;
        private readonly ILog _logger;

        /// <summary>
        ///     Default constructor
        /// </summary>
        public TimePlanner()
        {
            XmlConfigurator.Configure();
            _logger = LogManager.GetLogger(typeof (TimePlanner));

            _calendarDatabase = new CalendarContext();
        }

        #region Calendar

        /// <summary>
        ///     Adds a new calendar item to database
        /// </summary>
        /// <param name="calendar"></param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddCalendar(Calendar calendar)
        {
            if (Utils.IsCalendarValid(calendar))
            {
                try
                {
                    _calendarDatabase.Calendar.Add(calendar);
                    _calendarDatabase.SaveChanges();
                    return calendar.CalendarId;
                }
                catch (Exception ex)
                {
                    _logger.Error(ex.ToString());
                }
            }
            return 0;
        }

        /// <summary>
        ///     Returns a collection of calendar items
        /// </summary>
        /// <returns></returns>
        public ICollection<Calendar> GetAllCalendar()
        {
            try
            {
                return _calendarDatabase.Calendar.ToList();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        /// <summary>
        ///     Returns a single calendar item based on the chosen id
        /// </summary>
        /// <param name="calendarId"></param>
        /// <returns></returns>
        public Calendar GetCalendarById(int calendarId)
        {
            try
            {
                return _calendarDatabase.Calendar.Find(calendarId);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        #endregion

        #region CalendarEntry

        /// <summary>
        ///     Returns a calendar entry based on calenEntryId.
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <returns></returns>
        public CalendarEntry GetCalendarEntryById(int calendarEntryId)
        {
            try
            {
                return _calendarDatabase.CalendarEntries.Find(calendarEntryId);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        /// <summary>
        ///     Adds a calendar entry to the specified calendar
        /// </summary>
        /// <param name="entry"></param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddCalendarEntry(CalendarEntry entry)
        {
            try
            {
                Calendar calendar = _calendarDatabase.Calendar.Find(entry.CalendarId);
                if (calendar == null)
                {
                    return 0;
                }
                calendar.CalendarEntries.Add(entry);
                _calendarDatabase.SaveChanges();
                return entry.CalendarEntryId;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return 0;
        }

        /// <summary>
        ///     Returns all calendar entries of specified owner
        /// </summary>
        /// <param name="ownerId"></param>
        /// <returns></returns>
        public ICollection<CalendarEntry> GetAllEntriesByOwner(int ownerId)
        {
            try
            {
                return _calendarDatabase.CalendarEntries.Where(p => p.Owner.UserId == ownerId).ToList();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        /// <summary>
        ///     Returns all calendar entries in specified room
        /// </summary>
        /// <param name="roomId"></param>
        /// <returns></returns>
        public ICollection<CalendarEntry> GetEntriesByRoom(int roomId)
        {
            try
            {
                // Check if given roomId is available
                Room room = _calendarDatabase.Rooms.Find(roomId);
                if (room == null)
                {
                    return null;
                }
                return _calendarDatabase.CalendarEntries.Where(p => p.Room == room).ToList();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        /// <summary>
        ///     Removes entry from calendar
        /// </summary>
        /// <param name="calendarId"></param>
        /// <param name="entryId"></param>
        /// <returns></returns>
        public bool RemoveEntryFromCalendar(int calendarId, int entryId)
        {
            try
            {
                //checks if calendar and entry is available
                CalendarEntry entry = _calendarDatabase.CalendarEntries.Find(entryId);
                Calendar calendar = _calendarDatabase.Calendar.Find(calendarId);
                if (entry == null && calendar == null)
                {
                    return false;
                }
                return calendar.CalendarEntries.Remove(entry);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return false;
        }

        #endregion

        #region User

        /// <summary>
        ///     Returns all users
        /// </summary>
        /// <returns></returns>
        public ICollection<User> GetAllUser()
        {
            try
            {
                return _calendarDatabase.User.ToList();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        /// <summary>
        ///     Retunrs specified user
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        public User GetUserById(int userId)
        {
            try
            {
                return _calendarDatabase.User.Find(userId);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        #endregion

        #region Rooms

        /// <summary>
        ///     Returns all existing rooms
        /// </summary>
        /// <returns></returns>
        public ICollection<Room> GetAllRooms()
        {
            return _calendarDatabase.Rooms.ToList();
        }

        /// <summary>
        ///     Returns specified room
        /// </summary>
        /// <param name="roomId"></param>
        /// <returns></returns>
        public Room GetRoomById(int roomId)
        {
            return _calendarDatabase.Rooms.Find(roomId);
        }

        #endregion

        #region Groups

        /// <summary>
        ///     Returns a collection of groups
        /// </summary>
        /// <returns></returns>
        public ICollection<Group> GetAllGroups()
        {
            try
            {
                return _calendarDatabase.Groups.ToList();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        /// <summary>
        ///     Returns a group by specified Id
        /// </summary>
        /// <param name="groupId"></param>
        /// <returns></returns>
        public Group GetGroupById(int groupId)
        {
            try
            {
                return _calendarDatabase.Groups.Find(groupId);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        /// <summary>
        ///     Returns a collection of groups by specified user
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        public ICollection<Group> GetGroupsByUserId(int userId)
        {
            try
            {
                User member = _calendarDatabase.User.Find(userId);
                if (member == null)
                {
                    return null;
                }
                return _calendarDatabase.Groups.Where(p => p.Members == member).ToList();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        #endregion

        /// <summary>
        ///     Adds a user to database
        /// </summary>
        /// <param name="dbUser"></param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddUser(User dbUser)
        {
            try
            {
                _calendarDatabase.User.Add(dbUser);
                _calendarDatabase.SaveChanges();
                return dbUser.UserId;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return 0;
        }

        /// <summary>
        ///     Adds a room to database
        /// </summary>
        /// <param name="dbRoom"></param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddRoom(Room dbRoom)
        {
            try
            {
                _calendarDatabase.Rooms.Add(dbRoom);
                _calendarDatabase.SaveChanges();
                return dbRoom.RoomId;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return 0;
        }

        /// <summary>
        ///     Adds a group to database
        /// </summary>
        /// <param name="dbGroup"></param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddGroup(Group dbGroup)
        {
            try
            {
                _calendarDatabase.Groups.Add(dbGroup);
                _calendarDatabase.SaveChanges();
                return dbGroup.GroupId;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return 0;
        }

        /// <summary>
        ///     Accepts the invitation from the invited user and returns the calendarEntryId for the accepting user
        /// </summary>
        /// <param name="inviteId"></param>
        /// <returns></returns>
        public int AcceptInvite(int inviteId)
        {
            Invite invite = _calendarDatabase.Invites.Find(inviteId);
            invite.CalendarEntry.Owner = invite.Owner;
            invite.Accepted = true;
            _calendarDatabase.CalendarEntries.Add(invite.CalendarEntry);
            _calendarDatabase.SaveChanges();
            return invite.CalendarEntry.CalendarEntryId;
        }

        /// <summary>
        ///     Removes specified calendar
        /// </summary>
        /// <param name="calendarId"></param>
        /// <returns></returns>
        public bool RemoveCalendar(int calendarId)
        {
            Calendar calendar = _calendarDatabase.Calendar.Find(calendarId);
            if (calendar == null)
            {
                return false;
            }
            _calendarDatabase.Calendar.Remove(calendar);
            _calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        ///     Removes specified user
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        public bool RemoveUser(int userId)
        {
            User user = _calendarDatabase.User.Find(userId);
            if (user == null)
            {
                return false;
            }
            _calendarDatabase.User.Remove(user);
            _calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        ///     Removes specified room
        /// </summary>
        /// <param name="roomId"></param>
        /// <returns></returns>
        public bool RemoveRoom(int roomId)
        {
            Room room = _calendarDatabase.Rooms.Find(roomId);
            if (room == null)
            {
                return false;
            }
            _calendarDatabase.Rooms.Remove(room);
            _calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        ///     Change specified room in calendar entry
        /// </summary>
        /// <param name="roomId"></param>
        /// <param name="calendarEntryId"></param>
        /// <returns></returns>
        public bool ChangeRoomForCalendarEntry(int roomId, int calendarEntryId)
        {
            Room room = _calendarDatabase.Rooms.Find(roomId);
            CalendarEntry calendarEntry = _calendarDatabase.CalendarEntries.Find(calendarEntryId);
            if (room == null || calendarEntry == null)
            {
                return false;
            }
            calendarEntry.Room = room;
            _calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        ///     Adds specified user to specified group
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        public bool AddUserToGroup(int groupId, int userId)
        {
            Group group = _calendarDatabase.Groups.Find(groupId);
            User user = _calendarDatabase.User.Find(userId);
            if (group == null || user == null)
            {
                return false;
            }
            group.Members.Add(user);
            _calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        ///     Removes specified user from group
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        public bool RemoveUserFromGroup(int groupId, int userId)
        {
            Group group = _calendarDatabase.Groups.Find(groupId);
            User user = _calendarDatabase.User.Find(userId);
            if (group == null || user == null)
            {
                return false;
            }
            group.Members.Remove(user);
            _calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        ///     Removes specified group
        /// </summary>
        /// <param name="groupId"></param>
        /// <returns></returns>
        public bool RemoveGroup(int groupId)
        {
            Group group = _calendarDatabase.Groups.Find(groupId);
            if (group == null)
            {
                return false;
            }
            _calendarDatabase.Groups.Remove(group);
            _calendarDatabase.SaveChanges();
            return true;
        }

        /// <summary>
        ///     Get all invites of one user
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        public ICollection<Invite> GetAllInvitesByUserId(int userId)
        {
            User user = _calendarDatabase.User.Find(userId);
            if (user == null)
            {
                return null;
            }
            return _calendarDatabase.Invites.Where(p => p.Owner == user).ToList();
        }

        /// <summary>
        ///     Adds a person to invite list of a calendar entry and to table invites
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        public int AddInvite(int calendarEntryId, int userId)
        {
            CalendarEntry calendarEntry = _calendarDatabase.CalendarEntries.Find(calendarEntryId);
            User user = _calendarDatabase.User.Find(userId);
            if (calendarEntry == null || user == null)
            {
                return 0;
            }
            calendarEntry.Invitees.Add(user);
            var invite = new Invite
                {
                    Accepted = false,
                    CalendarEntry = calendarEntry,
                    Owner = user,
                };
            _calendarDatabase.Invites.Add(invite);
            _calendarDatabase.SaveChanges();
            return invite.InviteId;
        }

        /// <summary>
        ///     Removes a person from list invited people in calendar entry and from table Invites
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        public bool RemoveInvite(int calendarEntryId, int userId)
        {
            CalendarEntry calendarEntry = _calendarDatabase.CalendarEntries.Find(calendarEntryId);
            User user = _calendarDatabase.User.Find(userId);
            if (calendarEntry == null || user == null)
            {
                return false;
            }
            calendarEntry.Invitees.Remove(user);
            List<Invite> invitees =
                _calendarDatabase.Invites.Where(p => p.CalendarEntry == calendarEntry && p.Owner == user).ToList();

            if (invitees.Count != 1)
            {
                return false;
            }
            _calendarDatabase.Invites.Remove(invitees.First());
            _calendarDatabase.SaveChanges();
            return true;
        }
    }

    /// <summary>
    ///     EF 5 CodeFirst DatabaseContext for Organizer.
    /// </summary>
    public class CalendarContext : DbContext
    {
        /// <summary>
        ///     DBSet of all calendars
        /// </summary>
        public DbSet<Calendar> Calendar { get; set; }

        /// <summary>
        ///     DBSet of all CalendarEntries
        /// </summary>
        public DbSet<CalendarEntry> CalendarEntries { get; set; }

        /// <summary>
        ///     DBSet of all users
        /// </summary>
        public DbSet<User> User { get; set; }

        /// <summary>
        ///     DBSet of all rooms
        /// </summary>
        public DbSet<Room> Rooms { get; set; }

        /// <summary>
        ///     DBSet of all groups
        /// </summary>
        public DbSet<Group> Groups { get; set; }

        /// <summary>
        ///     DBSet of all Invites
        /// </summary>
        public DbSet<Invite> Invites { get; set; }
    }
}