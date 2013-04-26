using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces.JSONModel
{
    public class WebGroup
    {
        public int Id { get; set; }
        public String Description { get; set; }
        ICollection<User> Member { get; set; }
    }
}
