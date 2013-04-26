using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces.Json
{
    public class WebRoom
    {
        public int Id { get; set; }
        public String Description { get; set; }
        public String Location { get; set; }
        public int Seats { get; set; }
    }
}
