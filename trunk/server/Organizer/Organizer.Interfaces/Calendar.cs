using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces
{

    public class Calendar
    {
        [Key]
        public int CalendarId { get; set; }
        [Required]
        public virtual User Owner { get; set; }
        public String Description { get; set; }
        public virtual ICollection<CalendarEntry> CalendarEntries { get; set; }
        public virtual ICollection<Appointment> AppointmentEntries { get; set; }

        public Calendar()
        {
            CalendarEntries = new List<CalendarEntry>();
            AppointmentEntries = new List<Appointment>();
        }

        public bool IsEmpty
        {
            get
            {
                if (CalendarEntries.Count == 0)
                {
                    return true;
                }
                return false;
            }
        }



    }
}
