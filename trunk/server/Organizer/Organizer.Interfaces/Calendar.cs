#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/24/2013
#endregion
#region Usings

using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

#endregion

namespace Organizer.Interfaces
{
    /// <summary>
    /// Model for database entity: Calendar
    /// </summary>
    public class Calendar
    {
        [Key]
        public int CalendarId { get; set; }

        [Required]
        public virtual User Owner { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public virtual ICollection<CalendarEntry> CalendarEntries { get; set; }
        

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
