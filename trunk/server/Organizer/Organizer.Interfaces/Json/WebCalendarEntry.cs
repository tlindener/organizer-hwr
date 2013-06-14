#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/24/2013
#endregion
#region Usings

using System;
using System.Collections.Generic;

#endregion

namespace Organizer.Interfaces.Json
{

    /// <summary>
    /// JSON interface for Entity: CalendarEntry 
    /// <seealso cref="Organizer.Interface.CalendarEntry"/>
    /// </summary>
    public class WebCalendarEntry
    {

        public int Id { get; set; }
        public string Title { get; set; }
        public String StartDate { get; set; }
        public String EndDate { get; set; }
        public string Description { get; set; }
        public int OwnerId { get; set; }
        public int CalendarId { get; set; }
        public int RoomId { get; set; }
        public ICollection<WebUser> Invitees { get; set; }
  


    }
}
