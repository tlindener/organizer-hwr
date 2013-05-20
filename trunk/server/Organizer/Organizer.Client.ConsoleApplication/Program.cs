using Organizer.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Organizer.Client.ConsoleApplication
{
    class Program
    {
      


        static void Main(string[] args)
        {
            Console.ForegroundColor = ConsoleColor.DarkGreen;
            Console.BackgroundColor = ConsoleColor.Black;
            
            if (TestCalendar())
            {
                Console.WriteLine("");
                Console.WriteLine("Der Test ist erfolgreich gelaufen, die Datenbank funktioniert!!!");
            }
            Console.Read();
        }


        public static bool TestCalendar()
        {
            TimePlanner timeplanner = new TimePlanner();

            // Gruppen
            Group group1 = new Group()
            {
                Description = "Netzwerke"
            };

            Group group2 = new Group()
            {
                Description = "Fußball"
            };

            Group group3 = new Group()
            {
                Description = "tutorium"
            };

            Group group4 = new Group()
            {
                Description = "Abschlussplanung"
            };




            // Userdefinition
            List<Group> groupList = new List<Group>();
            groupList.Add(group1);
            groupList.Add(group4);

            User user1 = new User()
            {
                GivenName = "Tobias",
                Surname = "Lindener",
                MailAddress = "t.lindener@gmail.com",
                PhoneNumber = "030123456789",
                Groups = groupList
            };
            List<Group> groupList2 = new List<Group>();
            groupList2.Add(group2);

            User user2 = new User()
            {
                GivenName = "Jennifer",
                Surname = "Blumenthal",
                MailAddress = "j.blumenthal@gmail.com",
                PhoneNumber = "040333445561",
                Groups = groupList2
            };
            List<Group> groupList3 = new List<Group>();
            groupList3.Add(group1);
            groupList3.Add(group2);

            User user3 = new User()
            {
                GivenName = "Steffen",
                Surname = "Baumann",
                MailAddress = "sbaumann@gmail.com",
                PhoneNumber = "050989243556",
                Groups = groupList3
            };
            List<Group> groupList4 = new List<Group>();
            groupList4.Add(group3);

            User user4 = new User()
            {
                GivenName = "Svenja",
                Surname = "Moehring",
                MailAddress = "smoehring@gmail.com",
                PhoneNumber = "01024536788",
                Groups = groupList4
            };

            List<Group> groupList5 = new List<Group>();
            groupList5.Add(group3);

            User user5 = new User()
            {
                GivenName = "Marcel",
                Surname = "Hodan",
                MailAddress = "mhodan@gmail.com",
                PhoneNumber = "030987654321",
                Groups = groupList5
            };

            List<Group> groupList6 = new List<Group>();
            groupList6.Add(group1);
            groupList6.Add(group2);
            groupList6.Add(group3);
            groupList6.Add(group4);

            User user6 = new User()
            {
                GivenName = "Max",
                Surname = "Mustermann",
                MailAddress = "mmustermann@gmail.com",
                PhoneNumber = "0403334821941",
                Groups = groupList6
            };

            List<Group> groupList7 = new List<Group>();
            groupList7.Add(group2);

            User user7 = new User()
            {
                GivenName = "Karl",
                Surname = "Halter",
                MailAddress = "khalter@gmail.com",
                PhoneNumber = "05073924355",
                Groups = groupList7
            };

            List<Group> groupList8 = new List<Group>();
            groupList8.Add(group2);

            User user8 = new User()
            {
                GivenName = "Leif",
                Surname = "Reiser",
                MailAddress = "lreiser@gmail.com",
                PhoneNumber = "030739876356",
                Groups = groupList8
            };



            //Raumdefinition
            Room room1 = new Room()
            {
                Description = "1.1",
                Location = "1",
                Seats = 8
            };

            Room room2 = new Room()
            {
                Description = "1.2",
                Location = "1",
                Seats = 4
            };

            Room room3 = new Room()
            {
                Description = "1.3",
                Location = "1",
                Seats = 1
            };

            Room room4 = new Room()
            {
                Description = "2.1",
                Location = "2",
                Seats = 2
            };

            Room room5 = new Room()
            {
                Description = "2.2",
                Location = "2",
                Seats = 12
            };

            Room room6 = new Room()
            {
                Description = "3.1",
                Location = "3",
                Seats = 3
            };

            Room room7 = new Room()
            {
                Description = "3.2",
                Location = "3",
                Seats = 10
            };


            //Termindefinition
            CalendarEntry ce1 = new CalendarEntry()
            {
                Room = room1,
                Invitees = group2.Members,
                Owner = user8,
                Description = "Sportplatz vor der Tür",
                StartDate = new DateTime(2013,5,1,11,30,0),
                EndDate = new DateTime(2013,5,1,12,0,0),
                Title = "Fuuuußball"
            };

            CalendarEntry ce2 = new CalendarEntry()
            {
                Room = room2,
                Invitees = group1.Members,
                Owner = user3,
                Description = "Bei Jenny",
                StartDate = new DateTime(2013, 5, 4, 13, 30, 0),
                EndDate = new DateTime(2013, 5, 4, 16, 0, 0),
                Title = "Netzwerkbesprechung"
            };

            CalendarEntry ce3 = new CalendarEntry()
            {

                Room = room5,
                Owner = user1,
                Title = "Schnell sein!!",
                Description = "Arzttermin",
                StartDate = new DateTime(2013, 5, 1, 13, 15, 0),
                EndDate = new DateTime(2013, 5, 1, 17, 15, 0)
            };

            CalendarEntry ce4 = new CalendarEntry()
            {
                Room = room6,
                Invitees = group4.Members,
                Owner = user7,
                Description = "Unser nächste Reise nach Indien soll besprochen werden",
                StartDate = new DateTime(2013, 5, 2, 8, 0, 0),
                EndDate = new DateTime(2013, 5, 2, 12, 30, 0),
                Title = "Reiseplanung"
            };

            List<User> userList5 = new List<User>();
            userList5.Add(user6);
            CalendarEntry ce5 = new CalendarEntry()
            {
                Room = room3,
                Owner = user4,
                Description = "Geburtstagsfeier",
                StartDate = new DateTime(2013, 5, 11, 9, 0, 0),
                EndDate = new DateTime(2013, 5, 11, 11, 30, 0),
                Title = "Geheim",
                Invitees = userList5
            };

            CalendarEntry ce6 = new CalendarEntry()
            {
                Room = room6,
                Owner = user6,
                Description = "Bearbeitung der Client Server Aufgaben",
                StartDate = new DateTime(2013, 5, 2, 10, 0, 0),
                EndDate = new DateTime(2013, 5, 2, 15, 45, 0),
                Title = "CS"
            };

            List<User> userList7 = new List<User>();
            userList7.Add(user4);
            CalendarEntry ce7 = new CalendarEntry()
            {
                Room = room2,
                Owner = user1,
                Invitees = userList7,
                Description = "Letzte Vorbereitung",
                StartDate = new DateTime(2013, 5, 5, 11, 30, 0),
                EndDate = new DateTime(2013, 5, 5, 17, 30, 0),
                Title = "Arbeit :/"
            };

            CalendarEntry ce8 = new CalendarEntry()
            {
                Room = room2,
                Invitees = group2.Members,
                Owner = user2,
                Description = "Tunier gegen Parallelklasse",
                StartDate = new DateTime(2013, 5, 2, 12, 0, 0),
                EndDate = new DateTime(2013, 5, 2, 15, 45, 0),
                Title = "Fuuuußball!!!!"
            };

            List<User> userList9 = new List<User>();
            userList9.Add(user6);
            CalendarEntry ce9 = new CalendarEntry()
            {
                Room = room1,
                Owner = user5,
                Description = "Vortrag vorbereiten - letzte Wiederholung",
                StartDate = new DateTime(2013, 5, 3, 15, 0, 0),
                EndDate = new DateTime(2013, 5, 3,19, 0, 0),
                Title = "Vortrag vorbereiten",
                Invitees = userList9
            };

            List<User> userList10 = new List<User>();
            userList10.Add(user3);

            CalendarEntry ce10 = new CalendarEntry()
            {
                Room = room6,
                Owner = user4,
                Description = "Bearbeitung der Aufgabe",
                StartDate = new DateTime(2013, 5, 12, 16, 0, 0),
                EndDate = new DateTime(2013, 5, 12, 17, 30, 0),
                Title = "Netzwerkbesprechung",
                Invitees = userList10
            };

            List<User> userList11 = new List<User>();
            userList11.Add(user8);
            userList11.Add(user7);
            CalendarEntry ce11 = new CalendarEntry()
            {
                Room = room2,
                Invitees = userList11,
                Owner = user3,
                Description = "Bei Jenny",
                StartDate = new DateTime(2013, 5, 3, 19, 0, 0),
                EndDate = new DateTime(2013, 5, 3, 20, 0, 0),
                Title = "Netzwerkbesprechung"
            };

            List<User> userList12 = new List<User>();
            userList12.Add(user1);
            CalendarEntry ce12 = new CalendarEntry()
            {
                Room = room4,
                Owner = user6,
                Invitees = userList12,
                Description = "Zuhause",
                StartDate = new DateTime(2013, 5, 20, 16, 0, 0),
                EndDate = new DateTime(2013, 5, 20, 20, 30, 0),
                Title = "Freizeit :D"
            };

            CalendarEntry ce13 = new CalendarEntry()
            {
                Room = room7,
                Owner = user7,
                Description = "Telefonat mit Mama",
                StartDate = new DateTime(2013, 5, 3, 9, 0, 0),
                EndDate = new DateTime(2013, 5, 3, 13, 15, 0),
                Title = "Frei zu halten!"
            };

            CalendarEntry ce14 = new CalendarEntry()
            {
                Room = room7,
                Owner = user8,
                Description = "Putzfrau reinlassen und beaufsichtigen",
                StartDate = new DateTime(2013, 5, 18, 8, 30, 0),
                EndDate = new DateTime(2013, 5, 18,12, 0, 0),
                Title = "Homeoffice"
            };

            CalendarEntry ce15 = new CalendarEntry()
            {
                Room = room1,
                Owner = user5,
                Description = "Ruhiger Nachmittage - Really hard Lubbing",
                StartDate = new DateTime(2013, 5, 13, 14, 15, 0),
                EndDate = new DateTime(2013, 5, 13, 19, 0, 0),
                Title = "Quite time"
            };






            

            List<CalendarEntry> calenderentryList1 = new List<CalendarEntry>();
            calenderentryList1.Add(ce3);
            calenderentryList1.Add(ce7);

            Calendar calendar1 = new Calendar()
            {
                Description = "Fußballkalender für meine Freunde",
                Name = "Fußballkalender",
                Owner = user1,
                CalendarEntries = calenderentryList1
            };
            int calendarId = timeplanner.AddCalendar(calendar1);

            Calendar newCalendar1 = timeplanner.GetCalendarById(calendarId);
            if (CompareCalendar (calendar1, newCalendar1))
            {
            return true;
            }
            return false;
        

            List<CalendarEntry> calenderentryList2 = new List<CalendarEntry>();
            calenderentryList2.Add(ce8);

            Calendar calendar2 = new Calendar()
            {
                Description = "Persönlicher Kalender",
                Name = "Persönlich",
                Owner = user2,
                CalendarEntries = calenderentryList2
            };
            calendarId = timeplanner.AddCalendar(calendar1);

            Calendar newCalendar2 = timeplanner.GetCalendarById(calendarId);
            if (CompareCalendar (calendar2, newCalendar2))
            {
            return true;
            }
            return false;
        }




        public static bool CompareCalendar(Calendar cal1, Calendar cal2)
        {
            var owner1 = cal1.Owner;
            var owner2 = cal2.Owner;
            var calendarentries1 = cal1.CalendarEntries;
            var calendarentries2 = cal2.CalendarEntries;
            
            
            if (cal1.Description != cal2.Description)
            {
                return false;
            }
            if(cal1.Name != cal2.Name)
            {
                return false;
            }
            if (!compareUser(owner1, owner2))
            {
                return false;
            }
            if(!compareCalendarEntries(calendarentries1, calendarentries2))
            {
                return false;
            }
            return true;


        }
        public static bool compareUser(User user1, User user2)
        {        
            if(user1.Surname != user2.Surname)
             {
                 return false;
             }
            if(user1.Password != user2.Password)
             {
                 return false;
              }
            if(user1.PhoneNumber != user2.PhoneNumber)
             {
                return false;
              }
             if(user1.MailAddress != user2.MailAddress)
             {
                 return false;
             }
                  
            return true;
        }


        public static bool compareCalendarEntries(ICollection<CalendarEntry> list1, ICollection<CalendarEntry> list2)
        {
            if (list1.Count != list2.Count)
            {
                return false;
            }
            for (int i = 0; i < list1.Count; i++)
            {
                if(!compareCalendarEntry(list1.ElementAt(i), list2.ElementAt(i)))
                {
                    return false;
                }
            }
            return true;
        }


        public static bool compareCalendarEntry(CalendarEntry entry1, CalendarEntry entry2)
        {
            if (entry1.Description != entry2.Description)
            {
                return false;
            }
            if (entry1.EndDate != entry2.EndDate)
            {
                return false;
            }
            if (entry1.StartDate != entry2.StartDate)
            {
                return false;
            }
            if (entry1.Title != entry2.Title)
            {
                return false;
            }
            return true;
        }


    }
}
