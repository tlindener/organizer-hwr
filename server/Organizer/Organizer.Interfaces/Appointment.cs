using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces
{
    public class Appointment : CalendarEntry
    {

        public Appointment()
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

    }
}
