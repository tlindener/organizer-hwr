using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces
{
    public class User
    {
        [Key]
        public int UserId { get; set; }
        [Required]
        public String Name { get; set; }

        public int CalendarId { get; set; }
        public virtual Calendar Calendar { get; set; }

    }
}
