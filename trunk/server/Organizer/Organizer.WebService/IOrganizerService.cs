using Organizer.Interfaces.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace Organizer.WebService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService1" in both code and config file together.
    [ServiceContract]
    public interface IOrganizerService
    {
        #region Calendar
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendar> GetAllCalendar();

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebCalendar GetCalendarById(int calendarId);

        [OperationContract]
        [WebInvoke(RequestFormat = WebMessageFormat.Json)]
        int AddCalendarByObject(WebCalendar calendar);

        [OperationContract]
        [WebGet(RequestFormat = WebMessageFormat.Json)]
        int AddCalendar(int ownerId, String name, String description);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveCalendar(int calendarId);


        #endregion

        #region CalendarEntry
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendarEntry> GetCalendarEntriesByOwnerId(int ownerId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebCalendarEntry GetCalendarEntryById(int calendarEntryId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendarEntry> GetEntriesByRoom(int roomId);

        [OperationContract]
        [WebInvoke(RequestFormat = WebMessageFormat.Json)]
        int AddCalendarEntryByObject(WebCalendarEntry calendarEntry);

        [OperationContract]
        [WebGet(RequestFormat = WebMessageFormat.Json)]
        int AddCalendarEntry(String title, String description, DateTime startDate, DateTime endDate, int ownerId, int roomId, int calendarId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveEntryFromCalendar(int calendarId, int entryId);
        #endregion

        #region User
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebUser> GetAllUser();

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebUser GetUserById(int userId);

        [OperationContract]
        [WebInvoke(ResponseFormat = WebMessageFormat.Json)]
        int AddUserByObject(WebUser user,String password);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddUser(String givenName, String surname, String mailAddress, String phoneNumber, String userName, String password);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveUser(int userId,String adminAuth);
        #endregion

        #region Room
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebRoom> GetAllRooms();

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebRoom GetRoomById(int roomId);

        [OperationContract]
        [WebInvoke(ResponseFormat = WebMessageFormat.Json)]
        int AddRoomByObject(WebRoom room);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddRoom(String description, String location, int seats);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        public bool RemoveRoom(int roomId, String adminAuth);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool ChangeRoomForCalendarEntry(int roomId, int calendarEntryId);

        #endregion

        #region Group
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebGroup GetGroupById(int groupId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebGroup> GetGroupsByUserId(int userId);

        [OperationContract]
        [WebInvoke(ResponseFormat = WebMessageFormat.Json)]
        int AddGroupByObject(WebGroup group);

        [OperationContract]
        [WebInvoke(ResponseFormat = WebMessageFormat.Json)]
        int AddGroup(String description);


        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool AddUserToGroup(int groupId, int userId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveUserFromGroup(int groupId, int userId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveGroup(int groupId);
        

        #endregion Group

        #region Invite
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebInvite> GetAllInvitesByUserId(int userId);      

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AcceptInvite(int inviteId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        int AddInvite(int calendarEntryId, int userId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveInvite(int calendarEntryId, int userId);



        #endregion


        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        void InsertTestData();

    }


}
