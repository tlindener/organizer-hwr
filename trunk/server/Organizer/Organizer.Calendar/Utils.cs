using Organizer.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace Organizer
{
    public static class Utils
    {
        public static bool isCalendarValid(Calendar calendar)
        {

            if (!String.IsNullOrEmpty(calendar.Owner.Surname) && !String.IsNullOrEmpty(calendar.Owner.GivenName))
            {
                return true;
            }
            return false;
        }
        public static string getSHA512Hash(string text)
        {
            string hash = "";
            SHA512 alg = SHA512.Create();
            byte[] result = alg.ComputeHash(Encoding.UTF8.GetBytes(text));
            hash = Encoding.UTF8.GetString(result);
            return hash;
        }
    }
}
