﻿#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 05/03/2013
#endregion
namespace Organizer.Interfaces.Json
{
    /// <summary>
    /// JSON interface for Entity: Invite
    /// <seealso cref="Organizer.Interface.Invite"/>
    /// </summary>
    public class WebInvite
    {
        public int Id { get; set; }
        public int CalendarEntryId { get; set; }
        public int Accepted { get; set; }
        public int UserId { get; set; }
    }
}
