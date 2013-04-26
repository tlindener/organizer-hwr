using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Organizer.Interfaces
{
    public class Room 
    {
        [Key]
        public int RoomId { get; set; }
        public String Description { get; set; }
        public String Location { get; set; }
        public int Seats { get; set; }
        public virtual ICollection<CalendarEntry> CalendarEntries { get; set; }

    }
}
