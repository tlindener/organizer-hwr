﻿#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/25/2013
#endregion
namespace Organizer.Interfaces.Json
{
    /// <summary>
    /// WebRoom is the JSON interface version of Room
    /// </summary>
    /// <see cref="Organizer.Interfaces.Room"/>
    public class WebRoom
    {
        public int Id { get; set; }
        public string Description { get; set; }
        public string Location { get; set; }
        public int Seats { get; set; }
    }
}