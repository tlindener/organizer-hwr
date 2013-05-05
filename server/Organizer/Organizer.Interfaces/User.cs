#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/24/2013
#endregion
#region Usings

using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Security.Cryptography;
using System.Text;

#endregion

namespace Organizer.Interfaces
{
   
    public class User
    {
        [Key]
     
        public int UserId { get; set; }
        [Required]
       
        public string GivenName { get; set; }
        [Required]
    
        public string Surname { get; set; }
        [Required]
       
        public string MailAddress { get; set; }
        [Required]
  
        public string PhoneNumber { get; set; }

        public string UserName { get; set; }

        //SHA512Hashed

        private string _password = null;
        public string Password
        {
            get
            {
                return _password;
            }
            set
            {
                _password = getSHA512Hash(value);
            }
        }
        public int CalendarId { get; set; }
        public virtual ICollection<Calendar> Calendar { get; set; }
        public virtual ICollection<Group> Groups { get; set; }
        public virtual ICollection<Invite> Invites { get; set; }

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
