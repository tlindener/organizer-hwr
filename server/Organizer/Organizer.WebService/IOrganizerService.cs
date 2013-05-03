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
        bool AddCalendar(WebCalendar calendar);

        [OperationContract]
        [WebGet(RequestFormat = WebMessageFormat.Json)]
        bool AddCalendar(int ownerId, String name, String description);
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
        bool AddCalendarEntry(WebCalendarEntry calendarEntry);

        [OperationContract]
        [WebGet(RequestFormat = WebMessageFormat.Json)]
        bool AddCalendarEntry(String title,String description,DateTime startDate,DateTime endDate,int ownerId,int roomId, int calendarId);

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
        bool AddUser(WebUser user);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool AddUser(String givenName,String surname,String mailAddress,String phoneNumber,String userName,String password);
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
        bool AddRoom(WebRoom room);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool AddRoom(String description,String location, int seats);
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
        bool AddGroup(WebGroup group);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool AddGroup(String description);
        #endregion Group
        #region Invite
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebInvite> GetAllInvitesByUserId(int userId);
       
        #endregion

        #region Invite


        #endregion


        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        void InsertTestData();

    }


}
