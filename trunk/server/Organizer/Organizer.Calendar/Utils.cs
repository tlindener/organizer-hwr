#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/26/2013
#endregion
#region Usings

using System.Security.Cryptography;
using System.Text;
using Organizer.Interfaces;
using System;
using System.Text.RegularExpressions;

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
        /// Checks validity of mailAdress
        /// </summary>
        /// <param name="mailAddress"></param>
        /// <returns></returns>
        public static bool IsEmailValid(string mailAddress)
        {

            string validEmailPattern = @"^(?!\.)(""([^""\r\\]|\\[""\r\\])*""|"
                + @"([-a-z0-9!#$%&'*+/=?^_`{|}~]|(?<!\.)\.)*)(?<!\.)"
                + @"@[a-z0-9][\w\.-]*[a-z0-9]\.[a-z][a-z\.]*[a-z]$";

            
            Regex ex = new Regex(validEmailPattern, RegexOptions.IgnoreCase);            
            return ex.IsMatch(mailAddress);
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

            StringBuilder hex = new StringBuilder(result.Length * 2);
            foreach (byte b in result)
                hex.AppendFormat("{0:x2}", b);
            return hex.ToString();           
        }
        /// <summary>
        /// Decodes Base64 string
        /// </summary>
        /// <param name="encodedData"></param>
        /// <returns></returns>
        public static string DecodeFrom64(string encodedData)
        {
            byte[] encodedDataAsBytes
                = System.Convert.FromBase64String(encodedData);
            string returnValue =
               System.Text.ASCIIEncoding.ASCII.GetString(encodedDataAsBytes);
            return returnValue;
        }
        /// <summary>
        /// Encode string to Base64
        /// </summary>
        /// <param name="toEncode"></param>
        /// <returns></returns>
        static public string EncodeTo64(string toEncode)
        {
            byte[] toEncodeAsBytes
                  = System.Text.ASCIIEncoding.ASCII.GetBytes(toEncode);
            string returnValue
                  = System.Convert.ToBase64String(toEncodeAsBytes);
            return returnValue;
        }
    }
}
