#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/24/2013
#endregion
#region Usings

using System.Collections.Generic;

#endregion

namespace Organizer.Interfaces.Json
{
    /// <summary>
    /// 
    /// </summary>
    /// <see cref="Organizer.Interfaces.User"/>
    public class WebUser
    {
        public int Id { get; set; }
        public string UserName { get; set; }
        public string GivenName { get; set; }
        public string Surname { get; set; }
        public string MailAddress { get; set; }
        public string PhoneNumber { get; set; }
        public int CalendarId { get; set; }
        public ICollection<int> GroupIds { get; set; }
        public ICollection<int> InviteIds { get; set; }


    }
}
