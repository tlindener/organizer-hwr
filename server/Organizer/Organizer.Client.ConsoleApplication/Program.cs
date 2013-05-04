#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/15/2013
#endregion
#region Usings

using System;
using System.Collections.Generic;
using Organizer.Interfaces;

#endregion

namespace Organizer.Client.ConsoleApplication
{
    /// <summary>
    /// This program is a test utility to check the consistent work of the Organizer.TimePlanner class
    /// </summary>
    public class Program
    {
        /// <summary>
        /// Main method
        /// </summary>
        /// <param name="args">Console arguments</param>
        public static void Main(string[] args)
        {
            Organizer.TimePlanner tp = new TimePlanner();
            
            var calendarEntries = new List<CalendarEntry>();
            var owner = new User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress ="tobias.lindener@gmail.com",
                PhoneNumber = "01773071234"
            };
            calendarEntries.Add(new CalendarEntry() { Owner = owner, StartDate = DateTime.Now, EndDate = DateTime.Now.AddHours(3) });
            Calendar cal = new Calendar()
            {
                Owner = owner,
                CalendarEntries = calendarEntries
            };

            tp.AddCalendar(cal);

            foreach (Calendar calendar in tp.GetAllCalendar())
            {
                //Console.WriteLine(calendar.CalendarEntries[0].StartDate.ToString());
                //Console.WriteLine(calendar.Owner.Surname);
            }
            Console.Read();
        }
    }
}
