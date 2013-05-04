#region License
// Copyright: Tobias Lindener
// Author: Tobias Lindener
// Date: 04/25/2013
#endregion
namespace Organizer.Interfaces.Json
{
    public class WebRoom
    {
        public int Id { get; set; }
        public string Description { get; set; }
        public string Location { get; set; }
        public int Seats { get; set; }
    }
}
