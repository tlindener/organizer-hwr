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
        #region Calendar
        /// <summary>
        /// Returns a list of all Calendars 
        /// </summary>
        /// <returns>Collection of items of type WebCalendar</returns>
        /// <seealso cref="Organizer.TimePlanner.GetAllCalendar"/>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendar> GetAllCalendar();

        /// <summary>
        /// Returns a specified calendar of type WebCalendar
        /// </summary>
        /// <param name="calendarId"></param>
        /// <returns>WebCalendar</returns>
        /// <seealso cref="Organizer.TimePlanner.GetCalendarById"/>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebCalendar GetCalendarById(int calendarId);

        /// <summary>
        /// Adds a new calendar based on json object
        /// </summary>
        /// <param name="calendar"></param>
        /// <returns>Returns int value which specifies id of added object</returns>
        /// <seealso cref="Organizer.TimePlanner.AddCalendar"/>
        [OperationContract]
        [WebInvoke(RequestFormat = WebMessageFormat.Json)]
        int AddCalendarByObject(WebCalendar calendar);

        /// <summary>
        /// Adds a new calendar based on parameters
        /// </summary>
        /// <param name="ownerId">Specifies id of the calendars owner</param>
        /// <param name="name">Specifies name of new calendar</param>
        /// <param name="description">Specifies description of new calendar</param>
        /// <returns>Returns int value which specifies id of added object</returns>
        /// <seealso cref="Organizer.TimePlanner.AddCalendar"/>
        [OperationContract]
        [WebGet(RequestFormat = WebMessageFormat.Json)]
        int AddCalendar(int ownerId, string name, string description);

        /// <summary>
        /// Removes a calendar from database
        /// </summary>
        /// <param name="calendarId">Specifies the chosen calendar</param>
        /// <returns>Boolean indicates success of operation</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveCalendar(int calendarId);


        #endregion

        #region CalendarEntry

        /// <summary>
        /// Returns a collection of calendar entries for specified owner
        /// </summary>
        /// <param name="ownerId">Specifies the owner of the calendar entries</param>
        /// <returns>Returns a collection of WebCalendarEntries</returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendarEntry> GetAllCalendarEntriesByOwnerId(int ownerId);

        /// <summary>
        /// Returns WebCalendarEntry specified by id
        /// </summary>
        /// <param name="calendarEntryId">Specifies chosen calendar entry</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebCalendarEntry GetCalendarEntryById(int calendarEntryId);

        /// <summary>
        /// Returns all events linked to specified room
        /// </summary>
        /// <param name="roomId">Specifies chosen room</param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendarEntry> GetAllCalendarEntriesByRoomId(int roomId);

        /// <summary>
        /// Adds calendar entry by json object
        /// </summary>
        /// <param name="calendarEntry"></param>
        /// <returns></returns>
        [OperationContract]
        [WebInvoke(RequestFormat = WebMessageFormat.Json)]
        int AddCalendarEntryByObject(WebCalendarEntry calendarEntry);

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
        /// <returns></returns>
        [OperationContract]
        [WebGet(RequestFormat = WebMessageFormat.Json)]
        int AddCalendarEntry(string title, string description, DateTime startDate, DateTime endDate, int ownerId, int roomId, int calendarId);

        /// <summary>
        /// Removes calendar entry from calendar
        /// </summary>
        /// <param name="calendarId"></param>
        /// <param name="entryId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveEntryFromCalendar(int calendarId, int entryId);
        #endregion

        #region User
        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebUser> GetAllUser();

        /// <summary>
        /// 
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebUser GetUserById(int userId);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="user"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        [OperationContract]
        [WebInvoke(ResponseFormat = WebMessageFormat.Json)]
        int AddUserByObject(WebUser user, string password);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="givenName"></param>
        /// <param name="surname"></param>
        /// <param name="mailAddress"></param>
        /// <param name="phoneNumber"></param>
        /// <param name="userName"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddUser(string givenName, string surname, string mailAddress, string phoneNumber, string userName, string password);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="userId"></param>
        /// <param name="adminAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveUser(int userId, string adminAuth);
        #endregion

        #region Room
        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebRoom> GetAllRooms();

        /// <summary>
        /// 
        /// </summary>
        /// <param name="roomId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebRoom GetRoomById(int roomId);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="room"></param>
        /// <returns></returns>
        [OperationContract]
        [WebInvoke(ResponseFormat = WebMessageFormat.Json)]
        int AddRoomByObject(WebRoom room);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="description"></param>
        /// <param name="location"></param>
        /// <param name="seats"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddRoom(string description, string location, int seats);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="roomId"></param>
        /// <param name="adminAuth"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveRoom(int roomId, string adminAuth);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="roomId"></param>
        /// <param name="calendarEntryId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool ChangeRoomForCalendarEntry(int roomId, int calendarEntryId);

        #endregion

        #region Group
        /// <summary>
        /// 
        /// </summary>
        /// <param name="groupId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebGroup GetGroupById(int groupId);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebGroup> GetAllGroupsByUserId(int userId);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="group"></param>
        /// <returns></returns>
        [OperationContract]
        [WebInvoke(ResponseFormat = WebMessageFormat.Json)]
        int AddGroupByObject(WebGroup group);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="description"></param>
        /// <returns></returns>
        [OperationContract]
        [WebInvoke(ResponseFormat = WebMessageFormat.Json)]
        int AddGroup(string description);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool AddUserToGroup(int groupId, int userId);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="groupId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveUserFromGroup(int groupId, int userId);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="groupId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveGroup(int groupId);
        #endregion Group

        #region Invite
        /// <summary>
        /// 
        /// </summary>
        /// <param name="userId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebInvite> GetAllInvitesByUserId(int userId);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="inviteId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AcceptInvite(int inviteId);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddInvite(int calendarEntryId, int userId);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="calendarEntryId"></param>
        /// <param name="userId"></param>
        /// <returns></returns>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveInvite(int calendarEntryId, int userId);



        #endregion

        /// <summary>
        /// Deprecated
        /// </summary>
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        void InsertTestData();

    }


}
