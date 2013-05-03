using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces.Json
{
    public class WebInvite
    {
        public int InviteId { get; set; }
        public int CalendarEntryId { get; set; }
        public bool Accepted { get; set; }
        public int OwnerId { get; set; }
    }
}
