using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Organizer.Interfaces;
using System.Collections.Generic;

namespace Organizer.UnitTests
{
    [TestClass]
    public class TimeplannerTests
    {

        /// <summary>
        /// Tests adding/getting a calendar 
        /// </summary>
        [TestMethod]
        public void TestAddCalendar()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();

            Room room1 = new Room()
            {
                Description = "1.1",
                Location = "1",
                Seats = 8
            };
            timeplanner.AddRoom(room1);

            User user1 = new User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress = "t.lindener@gmail.com",
                PhoneNumber = "030123456789"
            };
            timeplanner.AddUser(user1);

            CalendarEntry ce1 = new CalendarEntry()
            {
                Room = room1,
                Owner = user1,
                Description = "Sportplatz vor der Tür",
                StartDate = new DateTime(2013, 5, 1, 11, 30, 0),
                EndDate = new DateTime(2013, 5, 1, 12, 0, 0),
                Title = "Fuuuußball"
            };

            CalendarEntry ce2 = new CalendarEntry()
            {
                Room = room1,
                Owner = user1,
                Description = "Bei Jenny",
                StartDate = new DateTime(2013, 5, 4, 13, 30, 0),
                EndDate = new DateTime(2013, 5, 4, 16, 0, 0),
                Title = "Netzwerkbesprechung"
            };

            List<CalendarEntry> calendarEntryList = new List<CalendarEntry>();
            calendarEntryList.Add(ce1);
            calendarEntryList.Add(ce2);

            Calendar calendar1 = new Calendar()
            {
                Description = "Fußballkalender für meine Freunde",
                Name = "Fußballkalender",
                Owner = user1,
                CalendarEntries = calendarEntryList
            };
            timeplanner.AddCalendar(calendar1);

            Assert.IsTrue(timeplanner.GetAllCalendar().Contains(calendar1));

        }

        /// <summary>
        /// Tests adding/getting a user
        /// </summary>
        [TestMethod]
        public void TestAddGetUser()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();
            User user1 = new User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress = "t.lindener@gmail.com",
                PhoneNumber = "030123456789"
            };
            timeplanner.AddUser(user1);
            Assert.IsTrue(timeplanner.GetAllUser().Contains(user1));
        }
        /// <summary>
        /// Tests adding/getting a Room
        /// </summary>
        [TestMethod]
        public void TestAddGetRoom()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();
            Room room1 = new Room()
            {
                Description = "1.1",
                Location = "1",
                Seats = 8
            };
            timeplanner.AddRoom(room1);
            Assert.IsTrue(timeplanner.GetAllRooms().Contains(room1));
        }
        /// <summary>
        /// Tests adding/getting a Group
        /// </summary>
        [TestMethod]
        public void TestAddGetGroup()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();
            Group group1 = new Group()
            {
                Description = "Netzwerke"
            };

            timeplanner.AddGroup(group1);
            Assert.IsTrue(timeplanner.GetAllGroups().Contains(group1));
        }

        /// <summary>
        /// Tests adding/getting a CalendarEntry
        /// </summary>
        [TestMethod]
        public void TestAddGetCalendarEntry()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();

            Room room1 = new Room()
            {
                Description = "1.1",
                Location = "1",
                Seats = 8
            };
            timeplanner.AddRoom(room1);

            User user1 = new User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress = "t.lindener@gmail.com",
                PhoneNumber = "030123456789"
            };
            timeplanner.AddUser(user1);
            Calendar calendar1 = new Calendar()
            {
                Description = "Fußballkalender für meine Freunde",
                Name = "Fußballkalender",
                Owner = user1
            };
            timeplanner.AddCalendar(calendar1);

            CalendarEntry ce1 = new CalendarEntry()
            {
                CalendarId=calendar1.CalendarId,
                Room = room1,
                Owner = user1,
                Description = "Sportplatz vor der Tür",
                StartDate = new DateTime(2013, 5, 1, 11, 30, 0),
                EndDate = new DateTime(2013, 5, 1, 12, 0, 0),
                Title = "Fuuuußball"
            };
            timeplanner.AddCalendarEntry(ce1);
            Assert.IsTrue(timeplanner.GetAllCalendarEntries().Contains(ce1));

        }

        /// <summary>
        /// Tests adding/getting an Invite
        /// </summary>
        [TestMethod]
        public void TestAddGetInvite()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();

            Room room1 = new Room()
            {
                Description = "1.1",
                Location = "1",
                Seats = 8
            };
            timeplanner.AddRoom(room1);

            User user1 = new User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress = "t.lindener@gmail.com",
                PhoneNumber = "030123456789"
            };
            User user2 = new User()
            {
                GivenName = "Jennifer",
                Surname = "Blumenthal",
                MailAddress = "j.blumenthal@gmail.com",
                PhoneNumber = "040333445561"       
            };
            timeplanner.AddUser(user1);
            timeplanner.AddUser(user2);
            Calendar calendar1 = new Calendar()
            {
                Description = "Fußballkalender für meine Freunde",
                Name = "Fußballkalender",
                Owner = user1
            };
            timeplanner.AddCalendar(calendar1);

            CalendarEntry ce1 = new CalendarEntry()
            {
                CalendarId = calendar1.CalendarId,
                Room = room1,
                Owner = user1,
                Description = "Sportplatz vor der Tür",
                StartDate = new DateTime(2013, 5, 1, 11, 30, 0),
                EndDate = new DateTime(2013, 5, 1, 12, 0, 0),
                Title = "Fuuuußball"
            };
            timeplanner.AddCalendarEntry(ce1);


            int inviteId = timeplanner.AddInvite(ce1.CalendarEntryId, user2.UserId);
            var invite = timeplanner.GetInviteById(inviteId);
            Assert.IsNotNull(invite);
            Assert.IsTrue(timeplanner.GetAllInvites().Contains(invite));

        }

  


        ///////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////REMOVE
        //////////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Tests removing a Calendar
        /// </summary>
        [TestMethod]
        public void TestRemoveCalendar()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();

            Room room1 = new Room()
            {
                Description = "1.1",
                Location = "1",
                Seats = 8
            };
            timeplanner.AddRoom(room1);

            User user1 = new User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress = "t.lindener@gmail.com",
                PhoneNumber = "030123456789"
            };
            timeplanner.AddUser(user1);

            CalendarEntry ce1 = new CalendarEntry()
            {
                Room = room1,
                Owner = user1,
                Description = "Sportplatz vor der Tür",
                StartDate = new DateTime(2013, 5, 1, 11, 30, 0),
                EndDate = new DateTime(2013, 5, 1, 12, 0, 0),
                Title = "Fuuuußball"
            };

            CalendarEntry ce2 = new CalendarEntry()
            {
                Room = room1,
                Owner = user1,
                Description = "Bei Jenny",
                StartDate = new DateTime(2013, 5, 4, 13, 30, 0),
                EndDate = new DateTime(2013, 5, 4, 16, 0, 0),
                Title = "Netzwerkbesprechung"
            };

            List<CalendarEntry> calendarEntryList = new List<CalendarEntry>();
            calendarEntryList.Add(ce1);
            calendarEntryList.Add(ce2);

            Calendar calendar1 = new Calendar()
            {
                Description = "Fußballkalender für meine Freunde",
                Name = "Fußballkalender",
                Owner = user1,
                CalendarEntries = calendarEntryList
            };
            timeplanner.AddCalendar(calendar1);

            Assert.IsTrue(timeplanner.GetAllCalendar().Contains(calendar1));
            Assert.IsTrue(timeplanner.RemoveCalendar(calendar1.CalendarId));

        }

        /// <summary>
        /// Tests removing an user
        /// </summary>
        [TestMethod]
        public void TestRemoveUser()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();
            User user1 = new User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress = "t.lindener@gmail.com",
                PhoneNumber = "030123456789"
            };
            timeplanner.AddUser(user1);
            Assert.IsTrue(timeplanner.GetAllUser().Contains(user1));
            Assert.IsTrue(timeplanner.RemoveUser(user1.UserId));
        }

        /// <summary>
        /// Tests removing a room
        /// </summary>
        [TestMethod]
        public void TestRemoveRoom()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();
            Room room1 = new Room()
            {
                Description = "1.1",
                Location = "1",
                Seats = 8
            };
            timeplanner.AddRoom(room1);
            Assert.IsTrue(timeplanner.GetAllRooms().Contains(room1));
            Assert.IsTrue(timeplanner.RemoveRoom(room1.RoomId));
        }

        /// <summary>
        /// Tests removing a group
        /// </summary>
        [TestMethod]
        public void TestRemoveGroup()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();
            Group group1 = new Group()
            {
                Description = "Netzwerke"
            };

            timeplanner.AddGroup(group1);
            Assert.IsTrue(timeplanner.GetAllGroups().Contains(group1));
            Assert.IsTrue(timeplanner.RemoveGroup(group1.GroupId));
        }

        /// <summary>
        /// Tests removing a calendar entry
        /// </summary>
        [TestMethod]
        public void TestRemoveCalendarEntry()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();

            Room room1 = new Room()
            {
                Description = "1.1",
                Location = "1",
                Seats = 8
            };
            timeplanner.AddRoom(room1);

            User user1 = new User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress = "t.lindener@gmail.com",
                PhoneNumber = "030123456789"
            };
            timeplanner.AddUser(user1);
            Calendar calendar1 = new Calendar()
            {
                Description = "Fußballkalender für meine Freunde",
                Name = "Fußballkalender",
                Owner = user1
            };
            timeplanner.AddCalendar(calendar1);

            CalendarEntry ce1 = new CalendarEntry()
            {
                CalendarId = calendar1.CalendarId,
                Room = room1,
                Owner = user1,
                Description = "Sportplatz vor der Tür",
                StartDate = new DateTime(2013, 5, 1, 11, 30, 0),
                EndDate = new DateTime(2013, 5, 1, 12, 0, 0),
                Title = "Fuuuußball"
            };
            timeplanner.AddCalendarEntry(ce1);
            Assert.IsTrue(timeplanner.GetAllCalendarEntries().Contains(ce1));
            Assert.IsTrue(timeplanner.RemoveCalendarEntry(ce1.CalendarEntryId));

        }
        /// <summary>
        /// Tests removing an invite
        /// </summary>
        [TestMethod]
        public void TestRemoveInvite()
        {
            TimePlanner timeplanner = new TimePlanner();
            timeplanner.RemoveDatabase();

            Room room1 = new Room()
            {
                Description = "1.1",
                Location = "1",
                Seats = 8
            };
            timeplanner.AddRoom(room1);

            User user1 = new User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress = "t.lindener@gmail.com",
                PhoneNumber = "030123456789"
            };
            User user2 = new User()
            {
                GivenName = "Jennifer",
                Surname = "Blumenthal",
                MailAddress = "j.blumenthal@gmail.com",
                PhoneNumber = "040333445561"
            };
            timeplanner.AddUser(user1);
            timeplanner.AddUser(user2);
            Calendar calendar1 = new Calendar()
            {
                Description = "Fußballkalender für meine Freunde",
                Name = "Fußballkalender",
                Owner = user1
            };
            timeplanner.AddCalendar(calendar1);

            CalendarEntry ce1 = new CalendarEntry()
            {
                CalendarId = calendar1.CalendarId,
                Room = room1,
                Owner = user1,
                Description = "Sportplatz vor der Tür",
                StartDate = new DateTime(2013, 5, 1, 11, 30, 0),
                EndDate = new DateTime(2013, 5, 1, 12, 0, 0),
                Title = "Fuuuußball"
            };
            timeplanner.AddCalendarEntry(ce1);


            int inviteId = timeplanner.AddInvite(ce1.CalendarEntryId, user2.UserId);
            var invite = timeplanner.GetInviteById(inviteId);
            Assert.IsNotNull(invite);
            Assert.IsTrue(timeplanner.GetAllInvites().Contains(invite));
            Assert.IsTrue(timeplanner.RemoveInvite(inviteId));
        }
    }
}
