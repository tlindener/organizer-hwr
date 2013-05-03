using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces
{
   public class Invite
    {

       [Key]
       public int InviteId { get; set; }
       public virtual CalendarEntry CalendarEntry { get; set; }
       public bool Accepted { get; set; }
       public virtual User Owner {get; set;}

    }
}
