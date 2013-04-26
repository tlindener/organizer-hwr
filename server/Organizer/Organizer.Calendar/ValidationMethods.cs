using Organizer.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer
{
    public static class ValidationMethods
    {
        public static bool isCalendarValid(Calendar calendar)
        {

            if (!String.IsNullOrEmpty(calendar.Owner.Surname) && !String.IsNullOrEmpty(calendar.Owner.GivenName))
            {
                return true;
            }
            return false;
        }
    }
}
