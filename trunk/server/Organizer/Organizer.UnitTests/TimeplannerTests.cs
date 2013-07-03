using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Organizer.Interfaces;
using System.Collections.Generic;

namespace Organizer.UnitTests
{
    [TestClass]
    public class TimeplannerTests
    {

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
        //REMOVE
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
