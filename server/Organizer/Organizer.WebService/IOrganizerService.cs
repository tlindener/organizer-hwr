﻿#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/24/2013
#endregion
#region Usings

using System;
using System.Collections.Generic;
using System.ServiceModel;
using System.ServiceModel.Web;
using Organizer.Interfaces.Json;

#endregion

namespace Organizer.WebService
{
    /// <summary>
    /// Organizer JSON interface
    /// </summary>
    [ServiceContract]
    public interface IOrganizerService
    {
        /// <summary>
        /// Returns a WebUser object based on given mailadress and password
        /// </summary>
        /// <param name="mailAdress"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebUser Login(string mailAddress, string password);

        #region Calendar
        /// <summary>
        /// Returns a specified calendar of type WebCalendar
        /// </summary>
        /// <param name="calendarId">Unique calendarId</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>WebCalendar</returns>
        /// <seealso cref="Organizer.TimePlanner.GetCalendarById"/>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebCalendar GetCalendarById(int calendarId, string userAuth);



        /// <summary>
        /// Adds a new calendar based on parameters
        /// </summary>
        /// <param name="ownerId">Specifies id of the calendars owner</param>
        /// <param name="name">Specifies name of new calendar</param>
        /// <param name="description">Specifies description of new calendar</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        /// <seealso cref="Organizer.TimePlanner.AddCalendar"/>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddCalendar(int ownerId, string name, string description, string userAuth);

        /// <summary>
        /// Updates specified calendar
        /// </summary>
        /// <param name="calendarId">Specifies calendar</param>
        /// <param name="name">Specifies name of new calendar</param>
        /// <param name="description">Specifies description of new calendar</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>Success indicator</returns>
        /// <seealso cref="Organizer.TimePlanner.UpdateCalendar"/>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool UpdateCalendar(int calendarId, string name, string description, string userAuth);

        /// <summary>
        /// Removes a calendar from database
        /// </summary>
        /// <param name="calendarId">Specifies the chosen calendar</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>Boolean indicates success of operation</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveCalendar(int calendarId, string userAuth);


        #endregion

        #region CalendarEntry

        /// <summary>
        /// Returns a collection of calendar entries for specified owner
        /// </summary>
        /// <param name="ownerId">Specifies the owner of the calendar entries</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>Returns a collection of WebCalendarEntries</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendarEntry> GetAllCalendarEntriesByOwnerId(int ownerId, string userAuth);

        /// <summary>
        /// Returns WebCalendarEntry specified by id
        /// </summary>
        /// <param name="calendarEntryId">Specifies chosen calendar entry</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>Requested WebCalendarEntry</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebCalendarEntry GetCalendarEntryById(int calendarEntryId, string userAuth);

        /// <summary>
        /// Returns all events linked to specified room
        /// </summary>
        /// <param name="roomId">Specifies chosen room</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>Collection of WebCalendarEntry</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendarEntry> GetAllCalendarEntriesByRoomId(int roomId, string userAuth);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveDatabase();

        /// <summary>
        /// Adds a new calendar entry based on parameters
        /// </summary>
        /// <param name="title">Title of entry</param>
        /// <param name="description">Description of the entry</param>
        /// <param name="startDate">Start date and time</param>
        /// <param name="endDate">End date and time</param>
        /// <param name="ownerId">Owner of the entry</param>
        /// <param name="roomId">Room in which meeting takes place</param>
        /// <param name="calendarId">Specifies the chosen calendar</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddCalendarEntry(string title, string description, DateTime startDate, DateTime endDate, int ownerId, int roomId, int calendarId, string userAuth);

        /// <summary>
        /// Updates calendar entry based on parameters
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <param name="title">Title of entry</param>
        /// <param name="description">Description of the entry</param>
        /// <param name="startDate">Start date and time</param>
        /// <param name="endDate">End date and time</param>
        /// <param name="ownerId">Owner of the entry</param>
        /// <param name="roomId">Room in which meeting takes place</param>
        /// <param name="calendarId">Specifies the chosen calendar</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool UpdateCalendarEntry(int calendarEntryId,string title, string description, DateTime startDate, DateTime endDate, int roomId, string userAuth);

        /// <summary>
        /// Removes calendar entry from calendar
        /// </summary>
        /// <param name="calendarEntryId">Unique calendarEntry identification</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>SuccessIndicator</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveCalendarEntry(int calendarEntryId, string userAuth);
        #endregion

        #region User
        /// <summary>
        /// Returns a list of all users in database
        /// </summary>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>Collection of WebUser</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebUser> GetAllUser(string userAuth);

        /// <summary>
        /// Returns a user object by specified userid
        /// </summary>
        /// <param name="userId">Unique identification of user</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>Requested WebUser</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebUser GetUserById(int userId, string userAuth);


        /// <summary>
        /// Adds a new user to the database. 
        /// </summary>
        /// <param name="givenName"></param>
        /// <param name="surname"></param>
        /// <param name="mailAddress"></param>
        /// <param name="phoneNumber"></param>
        /// <param name="password"></param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddUser(string givenName, string surname, string mailAddress, string phoneNumber, string password);

        /// <summary>
        /// Updates specified user
        /// </summary>
        /// <param name="userId"></param>
        /// <param name="givenName"></param>
        /// <param name="surname"></param>
        /// <param name="mailAddress"></param>
        /// <param name="phoneNumber"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool UpdateUser(int userId, string givenName, string surname, string mailAddress, string phoneNumber, string password, string userAuth);

        /// <summary>
        /// Removes a user.
        /// </summary>
        /// <param name="userId">Unique identification of user</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveUser(int userId, string userAuth);
        #endregion

        #region Room
        /// <summary>
        /// Returns a list of all existing rooms
        /// </summary>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebRoom> GetAllRooms(string userAuth);

        /// <summary>
        /// Returns specified room
        /// </summary>
        /// <param name="roomId">Unique identification of Room</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebRoom GetRoomById(int roomId, string userAuth);



        /// <summary>
        /// Adds a new room. Only accessible for administrator.
        /// </summary>
        /// <param name="description">Description of the room</param>
        /// <param name="location">Location e.g. number of room</param>
        /// <param name="seats">Amount of available seats</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddRoom(string description, string location, int seats, string userAuth);

        /// <summary>
        /// Updates specified room
        /// </summary>
        /// <param name="roomId"></param>
        /// <param name="description">Description of the room</param>
        /// <param name="location">Location e.g. number of room</param>
        /// <param name="seats">Amount of available seats</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool UpdateRoom(int roomId, string description, string location, int seats, string userAuth);

        /// <summary>
        /// Removes a room. Only accessible for administrator.
        /// </summary>
        /// <param name="roomId">Unique identification of Room</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveRoom(int roomId, string userAuth);

        /// <summary>
        /// Modifies room in specified calendarEntry
        /// </summary>
        /// <param name="roomId">Unique identification of Room</param>
        /// <param name="calendarEntryId">Unique calendarEntry identification</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool ChangeRoomForCalendarEntry(int roomId, int calendarEntryId, string userAuth);

        #endregion

        #region Group
        /// <summary>
        /// Returns single group object by specified id
        /// </summary>
        /// <param name="groupId">Unique identification of group</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebGroup GetGroupById(int groupId, string userAuth);

        /// <summary>
        /// Returns all groups in which specified user is member
        /// </summary>
        /// <param name="userId">Unique identification of user</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebGroup> GetAllGroupsByUserId(int userId, string userAuth);


        /// <summary>
        /// Adds a new group
        /// </summary>
        /// <param name="description"></param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddGroup(string name,string description, string userAuth);

       /// <summary>
        ///  Update specified group
       /// </summary>
       /// <param name="groupId"></param>
       /// <param name="description"></param>
       /// <param name="userAuth"></param>
       /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool UpdateGroup(int groupId,string description, string userAuth);


        /// <summary>
        /// Adds user to specified group
        /// </summary>
        /// <param name="groupId">Unique identification of group</param>
        /// <param name="userId">Unique identification of user</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool AddUserToGroup(int groupId, int userId, string userAuth);

        /// <summary>
        /// Removes user from group
        /// </summary>
        /// <param name="groupId">Unique identification of group</param>
        /// <param name="userId">Unique identification of user</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveUserFromGroup(int groupId, int userId, string userAuth);

        /// <summary>
        /// Removes specified group
        /// </summary>
        /// <param name="groupId">Unique identification of group</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>Success indicator</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveGroup(int groupId, string userAuth);
        #endregion Group

        #region Invite


        /// <summary>
        /// Returns invitation by specified id
        /// </summary>
        /// <param name="inviteId">Unique invite identification</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>WebInvite</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebInvite GetInviteById(int inviteId, string userAuth);

        /// <summary>
        /// Accepts invitation
        /// </summary>
        /// <param name="inviteId">Unique invite identification</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>Success indicator</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AcceptInvite(int inviteId, string userAuth);

        /// <summary>
        /// Accepts invitation
        /// </summary>
        /// <param name="inviteId">Unique invite identification</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>Success indicator</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int DeclineInvite(int inviteId, string userAuth);

        /// <summary>
        /// Adds a new invite to calendarEntry
        /// </summary>
        /// <param name="calendarEntryId">Unique calendarEntry identification</param>
        /// <param name="userId">Unique identification of user</param>
        /// <param name="userAuth">Used to authenticate a user against the requested action</param>
        /// <returns>The primaryKey of the added item. Returns 0 if not successful</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddInvite(int calendarEntryId, int userId, string userAuth);

        /// <summary>
        /// Removes user invite
        /// </summary>
        /// <param name="calendarEntryId">Unique calendarEntry identification</param>
        /// <param name="userId">Unique identification of user</param>
        /// <returns>Success indicator</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveInvite(int inviteId, string userAuth);


        /// <summary>
        /// Returns a list of all available groups
        /// </summary>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebGroup> GetAllGroups(string userAuth);

        #endregion



    }


}
