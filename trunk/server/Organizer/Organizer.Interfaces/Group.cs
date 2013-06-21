#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/26/2013
#endregion
#region Usings

using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

#endregion

namespace Organizer.Interfaces
{
    /// <summary>
    /// Model for database entity: Groups
    /// </summary>
    public class Group
    {
        [Key]
        public int GroupId { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public virtual ICollection<User> Members { get; set; }

    }
}
