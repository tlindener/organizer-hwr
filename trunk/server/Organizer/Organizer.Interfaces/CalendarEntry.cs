using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces
{
    public class CalendarEntry
    {

        public int CalendarEntryId { get; set; }
        [Required]
        public DateTime StartDate { get; set; }
        [Required]
        public DateTime EndDate { get; set; }
        public String Description { get; set; }
        public double Duration
        {
            get
            {
                return EndDate.Subtract(StartDate).TotalMinutes;
            }
        }

        public User Owner { get; set; }
        public int CalendarId { get; set; }
        public virtual Calendar Calendar { get; set; }
    }
}
