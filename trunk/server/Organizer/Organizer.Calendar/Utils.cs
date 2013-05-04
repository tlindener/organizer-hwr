#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/26/2013
#endregion
#region Usings

using System.Security.Cryptography;
using System.Text;
using Organizer.Interfaces;

#endregion

namespace Organizer
{
    /// <summary>
    /// Class for a variaty of utility methods
    /// </summary>
    public static class Utils
    {
        /// <summary>
        /// Checks if a calendar has valid user data
        /// </summary>
        /// <param name="calendar"></param>
        /// <returns></returns>
        public static bool IsCalendarValid(Calendar calendar)
        {

            if (!string.IsNullOrEmpty(calendar.Owner.Surname) && !string.IsNullOrEmpty(calendar.Owner.GivenName))
            {
                return true;
            }
            return false;
        }
        /// <summary>
        /// Generates SHA512 Hash for security purposes
        /// </summary>
        /// <param name="text"></param>
        /// <returns></returns>
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
