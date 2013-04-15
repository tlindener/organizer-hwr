using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces
{
    public class Calendar
    {
        [Key]
        public int CalendarId { get; set; }
        [Required]
        public User Owner { get; set; }
        public virtual List<CalendarEntry> CalendarEntries { get; set; }

        public Calendar()
        {
            CalendarEntries = new List<CalendarEntry>();
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
