﻿#region License
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
    ///     Includes DatabaseContext for Organizer Database. Offers methods to Create, Update and Delete items from database
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
            _logger = LogManager.GetLogger(typeof(TimePlanner));

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

            try
            {
                User owner = _calendarDatabase.User.Find(calendar.Owner.UserId);
                if (owner == null)
                    return 0;


                _calendarDatabase.Calendar.Add(calendar);
                _calendarDatabase.SaveChanges();

                /*resolution of nullable integer is a manual job
                 * Next step would be to implement 1:n relationship von user and calendar
                 */
                owner.CalendarId = calendar.CalendarId;
                _calendarDatabase.SaveChanges();

                return calendar.CalendarId;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return 0;
        }

        public ICollection<Calendar> GetAllCalendar()
        {
            return _calendarDatabase.Calendar.ToList();
        }

        /// <summary>
        ///     Adds a new calendar item to database
        /// </summary>
        /// <param name="calendar"></param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddCalendar(int ownerId, string name, string description)
        {

            try
            {
                User owner = _calendarDatabase.User.Find(ownerId);
                if (owner == null)
                    return 0;

                var calendar = GetCalendarByOwner(owner.UserId);

                if (calendar != null)
                {
                    return calendar.CalendarId;
                }

                Calendar cal = new Calendar()
                {
                    Owner = owner,
                    Name = name,
                    Description = description
                };


                _calendarDatabase.Calendar.Add(cal);
                _calendarDatabase.SaveChanges();

                /*resolution of nullable integer is a manual job
                 * Next step would be to implement 1:n relationship von user and calendar
                 */
                owner.CalendarId = cal.CalendarId;
                _calendarDatabase.SaveChanges();

                return cal.CalendarId;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return 0;

        }

        /// <summary>
        ///     Returns a collection of calendar items
        /// </summary>
        /// <returns></returns>
        public ICollection<Calendar> GetAllCalendarFromUser(int userId)
        {
            try
            {
                return _calendarDatabase.Calendar.Where(p => p.Owner.UserId == userId).ToList();
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

        /// <summary>
        ///     Removes specified calendar
        /// </summary>
        /// <param name="calendarId"></param>
        /// <returns></returns>
        public bool RemoveCalendar(int calendarId)
        {

            Calendar calendar = _calendarDatabase.Calendar.Find(calendarId);
            if (calendar == null)
                return false;

            try
            {
                if (calendar.CalendarEntries.Count > 0)
                {
                    // Second step is to remove all calendar entries
                    var calendarEntries = calendar.CalendarEntries.ToList();
                    foreach (var entry in calendarEntries)
                    {

                        //First step is to remove all invites
                        var invites = entry.Invitations.ToList();
                        foreach (Invite inv in invites)
                        {

                            _calendarDatabase.Invites.Remove(inv);

                        }
                        _calendarDatabase.CalendarEntries.Remove(entry);
                    }

                }
                //third step is to delete calendar
                _calendarDatabase.Calendar.Remove(calendar);
                _calendarDatabase.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return false;
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
                    return 0;

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



        public ICollection<CalendarEntry> GetAllCalendarEntries()
        {
            return _calendarDatabase.CalendarEntries.ToList();
        }

        public ICollection<Invite> GetAllInvites()
        {
            return _calendarDatabase.Invites.ToList();
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
                    return null;

                return _calendarDatabase.CalendarEntries.Where(p => p.Room.RoomId == room.RoomId).ToList();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
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
                return false;

            try
            {
                calendarEntry.Room = room;
                _calendarDatabase.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return false;
        }

        /// <summary>
        ///     Removes entry from calendar
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <returns></returns>
        public bool RemoveCalendarEntry(int calendarEntryId)
        {
            try
            {
                //checks if calendar and entry is available
                CalendarEntry entry = _calendarDatabase.CalendarEntries.Find(calendarEntryId);
                Calendar calendar = _calendarDatabase.Calendar.Find(entry.CalendarId);
                if (entry == null && calendar == null)
                {
                    return false;
                }
                if (entry.Invitations.Count > 0)
                {
                    entry.Invitations = null;
                    _calendarDatabase.SaveChanges();
                }
                _calendarDatabase.CalendarEntries.Remove(entry);
                _calendarDatabase.SaveChanges();
                return true;

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
        /// <summary>
        ///     Adds a user to database
        /// </summary>
        /// <param name="dbUser"></param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        public int AddUser(User dbUser)
        {
            if (!Utils.IsEmailValid(dbUser.MailAddress))
                return 0;

            var users = this.GetAllUser();

            if (users != null && users.Where(p => p.MailAddress.Equals(dbUser.MailAddress,StringComparison.InvariantCultureIgnoreCase)).Count() > 0)
            {
                return 0;
            }

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
        ///     Removes specified user
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        public bool RemoveUser(int userId)
        {
            User user = _calendarDatabase.User.Find(userId);
            if (user == null)
                return false;

            try
            {
                //each values with constraint has to be set to null
                if (user.Calendar != null)
                {
                    RemoveCalendar(user.Calendar.CalendarId);
                    user.Calendar = null;
                }
                if (user.Invites != null)
                {
                    var invites = user.Invites.ToList();
                    foreach (Invite inv in invites)
                    {
                        _calendarDatabase.Invites.Remove(inv);
                    }
                }
                user.Groups = null;
                _calendarDatabase.User.Remove(user);
                _calendarDatabase.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return false;
        }
        #endregion

        #region Rooms

        /// <summary>
        ///     Returns all existing rooms
        /// </summary>
        /// <returns></returns>
        public ICollection<Room> GetAllRooms()
        {
            try
            {
                return _calendarDatabase.Rooms.ToList();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        /// <summary>
        ///     Returns specified room
        /// </summary>
        /// <param name="roomId"></param>
        /// <returns></returns>
        public Room GetRoomById(int roomId)
        {
            try
            {
                return _calendarDatabase.Rooms.Find(roomId);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
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
            try
            {
                _calendarDatabase.Rooms.Remove(room);
                _calendarDatabase.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return false;
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
                var groups = _calendarDatabase.Groups.ToList();
                if (groups != null)
                {
                    return groups;
                }
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
                var group = _calendarDatabase.Groups.Find(groupId);
                if (group != null)
                {
                    return group;
                }
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

                return member.Groups;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
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
            try
            {
                if (group.Members.Contains(user))
                {
                    return false;
                }
                user.Groups.Add(group);
                _calendarDatabase.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return false;
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
            try
            {
                group.Members.Remove(user);
                _calendarDatabase.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return false;
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
            try
            {
                _calendarDatabase.Groups.Remove(group);
                _calendarDatabase.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return false;
        }


        #endregion


        #region Invites

        /// <summary>
        /// Retuns invite by specified id
        /// </summary>
        /// <param name="inviteId"></param>
        /// <returns></returns>
        public Invite GetInviteById(int inviteId)
        {
            try
            {
                Invite invite = _calendarDatabase.Invites.Find(inviteId);
                return invite;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
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
            try
            {
                return _calendarDatabase.Invites.Where(p => p.Owner == user).ToList();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return null;
        }

        /// <summary>
        /// Declines the invite
        /// </summary>
        /// <param name="inviteId"></param>
        /// <returns></returns>
        public int DeclineInvite(int inviteId)
        {
            try
            {

                Invite invite = _calendarDatabase.Invites.Find(inviteId);
                if (invite.Accepted == -1)
                {
                    return 0;
                }
                invite.Accepted = -1;
                _calendarDatabase.SaveChanges();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
                return 0;
            }
            return 1;
        }

        /// <summary>
        ///     Accepts the invitation from the invited user and returns the calendarEntryId for the accepting user
        /// </summary>
        /// <param name="inviteId"></param>
        /// <returns></returns>
        public int AcceptInvite(int inviteId)
        {

            Invite invite = _calendarDatabase.Invites.Find(inviteId);
            if (invite.Accepted == 1)
            {
                return 0;
            }

            invite.Accepted = 1;
            var calendar = GetCalendarByOwner(invite.Owner.UserId);
            var ce = invite.CalendarEntry;
            int userId = invite.Owner.UserId;
            int roomId = 0;
            if (ce.Room != null)
            {
                roomId = ce.Room.RoomId;
            }

            if (calendar != null)
            {
                return AddCalendarEntry(ce.Title, ce.Description, ce.StartDate, ce.EndDate, userId, roomId, calendar.CalendarId);
            }
            return 0;
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
            if (calendarEntry.Invitations.Where(p => p.Owner == user).Count() > 0)
            {
                return calendarEntry.Invitations.Where(p => p.Owner == user).First().InviteId;
            }
            var invite = new Invite
            {
                Accepted = 0,
                CalendarEntry = calendarEntry,
                Owner = user
            };
            try
            {
                calendarEntry.Invitations.Add(invite);
                _calendarDatabase.SaveChanges();
                return invite.InviteId;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return 0;
        }

        /// <summary>
        ///     Removes a person from list invited people in calendar entry and from table Invites
        /// </summary>
        /// <param name="inviteId"></param>
        /// <returns></returns>
        public bool RemoveInvite(int inviteId)
        {

            var invite = _calendarDatabase.Invites.Find(inviteId);

            if (invite == null)
            {
                return false;
            }
            try
            {
                _calendarDatabase.Invites.Remove(invite);
                _calendarDatabase.SaveChanges();
                return true;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.ToString());
            }
            return true;
        }

        #endregion


        /// <summary>
        /// Adds single calendar entry
        /// </summary>
        /// <param name="title"></param>
        /// <param name="description"></param>
        /// <param name="startDate"></param>
        /// <param name="endDate"></param>
        /// <param name="ownerId"></param>
        /// <param name="roomId"></param>
        /// <param name="calendarId"></param>
        /// <returns></returns>
        private int AddCalendarEntry(string title, string description, DateTime startDate, DateTime endDate, int ownerId, int roomId, int calendarId)
        {

            var owner = GetUserById(ownerId);
            var room = GetRoomById(roomId);
            var calendar = GetCalendarById(calendarId);

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
            return AddCalendarEntry(calendarEntry);
        }

        /// <summary>
        /// Returns the single calendar by owner
        /// </summary>
        /// <param name="ownerId"></param>
        /// <returns></returns>
        private Calendar GetCalendarByOwner(int ownerId)
        {
            var calendar = _calendarDatabase.Calendar.Where(p => p.Owner.UserId == ownerId);

            if (calendar != null && calendar.Count() > 0)
            {
                return calendar.First();
            }
            return null;
        }



        /// <summary>
        /// Updates specified calendar
        /// </summary>
        /// <param name="calendarId"></param>
        /// <param name="name"></param>
        /// <param name="description"></param>
        /// <returns></returns>
        public bool UpdateCalendar(int calendarId, string name, string description)
        {
            var calendar = _calendarDatabase.Calendar.Find(calendarId);
            if (calendar != null)
            {
                calendar.Name = name;
                calendar.Description = description;
                try
                {
                    _calendarDatabase.SaveChanges();
                    return true;
                }
                catch (Exception ex)
                {
                    _logger.Error(ex.ToString());
                }
            }
            return false;

        }

        /// <summary>
        /// Update specified calendarEntry
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <param name="title"></param>
        /// <param name="description"></param>
        /// <param name="startDate"></param>
        /// <param name="endDate"></param>
        /// <param name="roomId"></param>
        /// <returns></returns>
        public bool UpdateCalendarEntry(int calendarEntryId, string title, string description, DateTime startDate, DateTime endDate, int roomId)
        {
            var calendarEntry = _calendarDatabase.CalendarEntries.Find(calendarEntryId);
            var room = _calendarDatabase.Rooms.Find(roomId);

            if (calendarEntry != null)
            {
                calendarEntry.Title = title;
                calendarEntry.Description = description;
                calendarEntry.StartDate = startDate;
                calendarEntry.EndDate = endDate;
                calendarEntry.Room = room;
                try
                {
                    _calendarDatabase.SaveChanges();
                    return true;
                }
                catch (Exception ex)
                {
                    _logger.Error(ex.ToString());
                }
            }
            return false;
        }

        /// <summary>
        /// Update specified user
        /// </summary>
        /// <param name="userId"></param>
        /// <param name="givenName"></param>
        /// <param name="surname"></param>
        /// <param name="mailAddress"></param>
        /// <param name="phoneNumber"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        public bool UpdateUser(int userId, string givenName, string surname, string mailAddress, string phoneNumber, string password)
        {
            if (!Utils.IsEmailValid(mailAddress))
                return false;

            var users = this.GetAllUser();

            if (users != null && users.Where(p => p.MailAddress == mailAddress).Count() > 0)
            {
                return false;
            }
            if (String.IsNullOrEmpty(password))
            {
                return false;
            }

            var user = _calendarDatabase.User.Find(userId);
            if (user != null)
            {
                user.GivenName = givenName;
                user.Surname = surname;
                user.MailAddress = mailAddress;
                user.PhoneNumber = phoneNumber;
                user.Password = password;
                try
                {
                    _calendarDatabase.SaveChanges();
                    return true;
                }
                catch (Exception ex)
                {
                    _logger.Error(ex.ToString());
                }
            }
            return false;

        }

        /// <summary>
        /// Update specified room
        /// </summary>
        /// <param name="roomId"></param>
        /// <param name="description"></param>
        /// <param name="location"></param>
        /// <param name="seats"></param>
        /// <returns></returns>
        public bool UpdateRoom(int roomId, string description, string location, int seats)
        {
            var room = _calendarDatabase.Rooms.Find(roomId);
            if (room != null)
            {
                room.Description = description;
                room.Location = location;
                room.Seats = seats;

                try
                {
                    _calendarDatabase.SaveChanges();
                    return true;
                }
                catch (Exception ex)
                {
                    _logger.Error(ex.ToString());
                }
            }
            return false;
        }

        /// <summary>
        /// Update specified group
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="description"></param>
        /// <returns></returns>
        public bool UpdateGroup(int groupId, string description)
        {
            var group = _calendarDatabase.Groups.Find(groupId);
            if (group != null)
            {
                group.Description = description;

                try
                {
                    _calendarDatabase.SaveChanges();
                    return true;
                }
                catch (Exception ex)
                {
                    _logger.Error(ex.ToString());
                }
            }
            return false;
        }

        /// <summary>
        /// Method is used to allow automated UnitTesting. Security issue!
        /// </summary>
        /// <returns></returns>
        public bool RemoveDatabase()
        {
            return _calendarDatabase.Database.Delete();
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

        /// <summary>
        /// Used to refine the generated database model
        /// </summary>
        /// <param name="modelBuilder"></param>
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {

            modelBuilder.Entity<User>()
            .HasOptional(a => a.Calendar)
            .WithMany()
            .HasForeignKey(u => u.CalendarId);
            modelBuilder.Entity<User>()
               .HasOptional(a => a.Calendar)
               .WithMany()
               .HasForeignKey(u => u.CalendarId).WillCascadeOnDelete(false);


        }
    }
}