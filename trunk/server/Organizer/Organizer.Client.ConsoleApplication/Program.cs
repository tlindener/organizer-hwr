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

            tp.AddNewCalendar(cal);

            foreach (Calendar calendar in tp.GetAllCalendar())
            {
                //Console.WriteLine(calendar.CalendarEntries[0].StartDate.ToString());
                //Console.WriteLine(calendar.Owner.Surname);
            }
            Console.Read();
        }
    }
}
