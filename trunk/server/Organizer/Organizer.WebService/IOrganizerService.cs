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
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendar> GetAllCalendar();
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebCalendar GetCalendarById(int calendarId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool AddNewCalendar();



        #region CalendarEntry
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebCalendarEntry> GetCalendarEntriesByOwnerId(int ownerId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebCalendarEntry GetCalendarEntryById(int entryId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<Organizer.Interfaces.CalendarEntry> GetEntriesByRoom(int roomId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool AddCalendarEntryToCalendar(int calendarId, DateTime startDate,DateTime endDate,String description,int ownerId, int roomId);

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
        #endregion

        #region Room
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebRoom> GetAllRooms();

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebRoom GetRoomById(int roomId);
        #endregion



        #region Group
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebGroup GetGroupById(int groupId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        ICollection<WebGroup> GetGroupsByUserId(int userId);

        #endregion Group

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
      ICollection<WebUser> GetInviteesFromAppointment(int appointmentId);
        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebRoom GetRoomById(int roomId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebUser GetUserById(int userId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveEntryFromCalendar(int calendarId, int entryId);


        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        void InsertTestData();

    }  


}
