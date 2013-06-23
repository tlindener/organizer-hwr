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
    /// <summary>
    /// Model for database entity: CalendarEntries
    /// </summary>
    public class CalendarEntry
    {
        public CalendarEntry()
        {
            Invitations = new List<Invite>();
        }
        public virtual ICollection<Invite> Invitations { get; set; }


        public bool IsEmpty
        {
            get
            {
                if (Invitations.Count == 0)
                {
                    return true;
                }
                return false;
            }
        }


        [Key]
        public int CalendarEntryId { get; set; }
   
        public DateTime StartDate { get; set; }
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
        public int CalendarId { get; set; }
        public virtual Calendar Calendar { get; set; }
        public virtual Room Room { get; set; }
    }
}
