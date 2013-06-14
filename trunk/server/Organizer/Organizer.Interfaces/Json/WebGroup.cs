#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/26/2013
#endregion
#region Usings

using System.Collections.Generic;

#endregion

namespace Organizer.Interfaces.Json
{
    /// <summary>
    /// JSON interface for Entity: Group
    /// <seealso cref="Organizer.Interface.Group"/>
    /// </summary>
    public class WebGroup
    {
        public int Id { get; set; }
        public string Description { get; set; }
        public ICollection<User> Members { get; set; }
    }
}
