#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/24/2013
#endregion
#region Usings

using System.Collections.Generic;

#endregion

namespace Organizer.Interfaces.Json
{

    /// <summary>
    /// JSON interface for Entity: Calendar 
    /// <seealso cref="Organizer.Interfaces.Calendar"/>
    /// </summary>
    public class WebCalendar
    {

        public int Id { get; set; }
        public int OwnerId { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public List<WebCalendarEntry> CalendarEntries { get; set; }


        public WebCalendar()
        {
            CalendarEntries = new List<WebCalendarEntry>();            
        }
    }
}
