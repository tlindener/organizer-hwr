using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces.Json
{

    public class WebCalendarEntry
    {

        public int Id { get; set; }
        public String Title { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public String Description { get; set; }
        public int OwnerId { get; set; }
        public int CalendarId { get; set; }
        public int RoomId { get; set; }
        public double Duration { get; set; }


    }
}
