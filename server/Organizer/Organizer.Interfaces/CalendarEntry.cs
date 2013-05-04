#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/24/2013
#endregion
#region Usings

using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

#endregion

namespace Organizer.Interfaces
{

    public class CalendarEntry
    {
        public CalendarEntry()
        {
            Invitees = new List<User>();
        }
        public virtual ICollection<User> Invitees { get; set; }


        public bool IsEmpty
        {
            get
            {
                if (Invitees.Count == 0)
                {
                    return true;
                }
                return false;
            }
        }


        [Key]
        public int CalendarEntryId { get; set; }
        [Required]
        public DateTime StartDate { get; set; }
        [Required]
        public DateTime EndDate { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public double Duration
        {
            get
            {
                return EndDate.Subtract(StartDate).TotalMinutes;
            }
        }
        public virtual User Owner { get; set; }
        public int? CalendarId { get; set; }
        public virtual Calendar Calendar { get; set; }
        public virtual Room Room { get; set; }
    }
}
