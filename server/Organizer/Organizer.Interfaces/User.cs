using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Runtime.Serialization;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces
{
   
    public class User
    {
        [Key]
     
        public int UserId { get; set; }
        [Required]
       
        public String GivenName { get; set; }
        [Required]
    
        public String Surname { get; set; }
        [Required]
       
        public String MailAddress { get; set; }
        [Required]
  
        public String PhoneNumber { get; set; }

        public String UserName { get; set; }

        //SHA512Hashed

        private String _password = null;
        public String Password
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

        public static String getSHA512Hash(String text)
        {
            string hash = "";
            SHA512 alg = SHA512.Create();
            byte[] result = alg.ComputeHash(Encoding.UTF8.GetBytes(text));
            hash = Encoding.UTF8.GetString(result);
            return hash;
        }


    }
}
