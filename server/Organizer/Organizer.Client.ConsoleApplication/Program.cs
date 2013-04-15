using Organizer.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Client.ConsoleApplication
{
    class Program
    {
        static void Main(string[] args)
        {
            Organizer.TimePlanner tp = new TimePlanner();
            
            var calendarEntries = new List<CalendarEntry>();
            calendarEntries.Add(new CalendarEntry() { Owner = new User() { Name = "Tobias" }, StartDate = DateTime.Now, EndDate = DateTime.Now.AddHours(3) });
            Calendar cal = new Calendar()
            {
                Owner = new User()
                {
                    Name = "Tobias"
                },
                CalendarEntries = calendarEntries


            };

            tp.AddCalendar(cal);

            foreach (Calendar calendar in tp.GetAllCalendar())
            {
                Console.WriteLine(calendar.CalendarEntries[0].StartDate.ToString());
            }
            Console.Read();
        }
    }
}
