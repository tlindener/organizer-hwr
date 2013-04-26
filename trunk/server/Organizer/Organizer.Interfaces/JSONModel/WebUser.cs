using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Interfaces.Json
{

    public class WebUser
    {

        public int Id { get; set; }
        public String GivenName { get; set; }
        public String Surname { get; set; }
        public String MailAddress { get; set; }
        public String PhoneNumber { get; set; }


    }
}
