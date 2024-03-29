﻿#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/24/2013
#endregion
#region Usings

using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Security.Cryptography;
using System.Text;

#endregion

namespace Organizer.Interfaces
{

    /// <summary>
    /// Model for database entity: Users
    /// </summary>
    public class User
    {
        [Key]

        public int UserId { get; set; }
        public string GivenName { get; set; }
        public string Surname { get; set; }
        public string MailAddress { get; set; }
        public string PhoneNumber { get; set; }
        public virtual Calendar Calendar { get; set; }
        public int? CalendarId { get; set; }

        //SHA512Hashed

        private string _password = null;
        public string Password
        {
            get
            {
                return _password;
            }
            set
            {
                _password = value;
            }
        }


        public virtual ICollection<Group> Groups { get; set; }
        public virtual ICollection<Invite> Invites { get; set; }




    }
}
