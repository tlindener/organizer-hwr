#region License
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
        /// <param name="calendarId"></param>
        /// <param name="userAuth"></param>
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
        /// <param name="userAuth"></param>
        /// <returns>Returns int value which specifies id of added object</returns>
        /// <seealso cref="Organizer.TimePlanner.AddCalendar"/>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddCalendar(int ownerId, string name, string description, string userAuth);

        /// <summary>
        /// Removes a calendar from database
        /// </summary>
        /// <param name="calendarId">Specifies the chosen calendar</param>
        /// <param name="userAuth"></param>
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
        /// <param name="userAuth"></param>
        /// <returns>Returns a collection of WebCalendarEntries</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendarEntry> GetAllCalendarEntriesByOwnerId(int ownerId, string userAuth);

        /// <summary>
        /// Returns WebCalendarEntry specified by id
        /// </summary>
        /// <param name="calendarEntryId">Specifies chosen calendar entry</param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebCalendarEntry GetCalendarEntryById(int calendarEntryId, string userAuth);

        /// <summary>
        /// Returns all events linked to specified room
        /// </summary>
        /// <param name="roomId">Specifies chosen room</param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendarEntry> GetAllCalendarEntriesByRoomId(int roomId, string userAuth);



        /// <summary>
        /// Adds a new calendar entry based on parameters
        /// </summary>
        /// <param name="title"></param>
        /// <param name="description"></param>
        /// <param name="startDate"></param>
        /// <param name="endDate"></param>
        /// <param name="ownerId"></param>
        /// <param name="roomId"></param>
        /// <param name="calendarId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddCalendarEntry(string title, string description, DateTime startDate, DateTime endDate, int ownerId, int roomId, int calendarId, string userAuth);

        /// <summary>
        /// Removes calendar entry from calendar
        /// </summary>
        /// <param name="entryId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveCalendarEntry(int calendarEntryId, string userAuth);
        #endregion

        #region User
        /// <summary>
        /// Returns a list of all users in database
        /// </summary>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebUser> GetAllUser(string userAuth);

        /// <summary>
        /// Returns a user object by specified userid
        /// </summary>
        /// <param name="userId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
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
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddUser(string givenName, string surname, string mailAddress, string phoneNumber, string password);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="userId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveUser(int userId, string userAuth);
        #endregion

        #region Room
        /// <summary>
        /// 
        /// </summary>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebRoom> GetAllRooms(string userAuth);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="roomId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebRoom GetRoomById(int roomId, string userAuth);



        /// <summary>
        /// 
        /// </summary>
        /// <param name="description"></param>
        /// <param name="location"></param>
        /// <param name="seats"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddRoom(string description, string location, int seats, string userAuth);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="roomId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveRoom(int roomId, string userAuth);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="roomId"></param>
        /// <param name="calendarEntryId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool ChangeRoomForCalendarEntry(int roomId, int calendarEntryId, string userAuth);

        #endregion

        #region Group
        /// <summary>
        /// 
        /// </summary>
        /// <param name="groupId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebGroup GetGroupById(int groupId, string userAuth);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebGroup> GetAllGroupsByUserId(int userId, string userAuth);


        /// <summary>
        /// 
        /// </summary>
        /// <param name="description"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddGroup(string description, string userAuth);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool AddUserToGroup(int groupId, int userId, string userAuth);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="userId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveUserFromGroup(int groupId, int userId, string userAuth);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveGroup(int groupId, string userAuth);
        #endregion Group

        #region Invite
        /// <summary>
        /// 
        /// </summary>
        /// <param name="userId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebInvite> GetAllInvitesByUserId(int userId, string userAuth);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="inviteId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AcceptInvite(int inviteId, string userAuth);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <param name="userId"></param>
        /// <param name="userAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddInvite(int calendarEntryId, int userId, string userAuth);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveInvite(int calendarEntryId, int userId, string userAuth);



        #endregion

        /// <summary>
        /// Deprecated
        /// </summary>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        void InsertTestData();

    }


}
