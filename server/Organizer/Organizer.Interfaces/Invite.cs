#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 05/03/2013
#endregion
#region Usings

using System.ComponentModel.DataAnnotations;

#endregion

namespace Organizer.Interfaces
{
   public class Invite
    {

       [Key]
       public int InviteId { get; set; }
       public virtual CalendarEntry CalendarEntry { get; set; }
       public int Accepted { get; set; }
       public virtual User Owner {get; set;}

    }
}
