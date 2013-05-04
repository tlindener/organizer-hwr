#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/25/2013
#endregion
#region Usings

using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

#endregion

namespace Organizer.Interfaces
{
    public class Room 
    {
        [Key]
        public int RoomId { get; set; }
        public string Description { get; set; }
        public string Location { get; set; }
        public int Seats { get; set; }
        public virtual ICollection<CalendarEntry> CalendarEntries { get; set; }

    }
}
