using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;

namespace Organizer.Interfaces
{
    public class Group
    {
        [Key]
        public int GroupId { get; set; }
        public String Description { get; set; }
        public virtual ICollection<User> Users { get; set; }

    }
}
