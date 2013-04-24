using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces.Json
{

    public class WebCalendar
    {

        public int CalendarId { get; set; }
        public int OwnerId { get; set; }
        public String Description { get; set; }
        public List<WebCalendarEntry> CalendarEntries { get; set; }
        public List<Appointment> AppointmentEntries { get; set; }

        public WebCalendar()
        {
            CalendarEntries = new List<WebCalendarEntry>();
            AppointmentEntries = new List<Appointment>();
        }
    }
}
