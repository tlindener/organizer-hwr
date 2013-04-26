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
        IEnumerable<WebCalendar> GetAllCalendar();

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebCalendar GetCalendarById(int calendarId);


        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool AddCalendarEntryToCalendar(int calendarId, DateTime startDate,DateTime endDate,String description,int ownerId, int roomId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        bool RemoveEntryFromCalendar(int calendarId, int entryId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        WebUser GetUserById(int userId);

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        IEnumerable<WebUser> GetAllUser();

        [OperationContract]
        [WebGet(ResponseFormat = WebMessageFormat.Json)]
        void InsertTestData();

    }  


}
