﻿#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/26/2013
#endregion
#region Usings

using System.Collections.Generic;

#endregion

namespace Organizer.Interfaces.Json
{
    public class WebGroup
    {
        public int Id { get; set; }
        public string Description { get; set; }
        public ICollection<User> Members { get; set; }
    }
}
