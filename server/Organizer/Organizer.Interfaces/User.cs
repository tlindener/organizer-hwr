using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Runtime.Serialization;
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

        public int CalendarId { get; set; }
        public virtual ICollection<Calendar> Calendar { get; set; }
        public virtual ICollection<Group> Groups { get; set; }

    }
}
