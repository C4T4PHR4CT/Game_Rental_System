using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using Model;
using System.Text.Json;
using System.Threading;
using NUnit.Framework;

namespace WebServicesUnitTest
{
    public class Tests
    {
        public HttpClient Client;
        
        [SetUp]
        public void Setup()
        {
            Client = new HttpClient {BaseAddress = new Uri("http://localhost:8080/game_rental/")};
        }

        [Test, Order(1)]
        public void RegisterUser_Valid1()
        {
            UserPw temp = new UserPw{Name = "User 1", Password = "1234"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("registerUser", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                User res = (User)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
                Console.WriteLine(res);
                Assert.IsTrue(res.Name.Equals(temp.Name));
            }
        }

        [Test, Order(1)]
        public void RegisterUser_Valid2()
        {
            UserPw temp = new UserPw{Name = "User 2", Password = "12345"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("registerUser", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                User res = (User)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
                Console.WriteLine(res);
                Assert.IsTrue(res.Name.Equals(temp.Name));
            }
        }
        
        [Test, Order(2)]
        public void RegisterUser_AlreadyExists()
        {
            UserPw temp = new UserPw{Name = "User 1", Password = "12345"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("registerUser", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(3)]
        public void ValidateUser_Valid1()
        {
            UserPw temp = new UserPw{Name = "User 1", Password = "1234"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("validateUser", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                User res = (User)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
                Console.WriteLine(res);
                Assert.IsTrue(res.Name.Equals(temp.Name));
            }
        }
        
        [Test, Order(3)]
        public void ValidateUser_Valid2()
        {
            UserPw temp = new UserPw{Name = "User 2", Password = "12345"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("validateUser", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                User res = (User)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
                Console.WriteLine(res);
                Assert.IsTrue(res.Name.Equals(temp.Name));
            }
        }
        
        [Test, Order(3)]
        public void ValidateUser_InvalidPw()
        {
            UserPw temp = new UserPw{Name = "User 1", Password = "12345"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("validateUser", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(3)]
        public void ValidateUser_InvalidName()
        {
            UserPw temp = new UserPw{Name = "User 4", Password = "12345"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("validateUser", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }

        [Test, Order(3)]
        public void GetUser_Valid1()
        {
            String temp = "User 1";
            HttpResponseMessage response = Client.GetAsync("getUser/" + temp).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                User res = (User)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
                Console.WriteLine(res);
                Assert.IsTrue(res.Name.Equals(temp));
            }
        }
        
        [Test, Order(3)]
        public void GetUser_Valid2()
        {
            String temp = "User 2";
            HttpResponseMessage response = Client.GetAsync("getUser/" + temp).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                User res = (User)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
                Console.WriteLine(res);
                Assert.IsTrue(res.Name.Equals(temp));
            }
        }
        
        [Test, Order(3)]
        public void GetUser_NotFound()
        {
            String temp = "User 3";
            HttpResponseMessage response = Client.GetAsync("getUser/" + temp).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(4)]
        public void DeleteUser_Valid()
        {
            UserPw temp = new UserPw{Name = "For Delete 1", Password = "1234"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            Client.PostAsync("registerUser", data);
            Thread.Sleep(200);
            
            String temp1 = "For Delete 1";
            HttpResponseMessage response = Client.DeleteAsync("deleteUser/" + temp1).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            Thread.Sleep(200);
            
            response = Client.GetAsync("getUser/" + temp1).Result;
            Assert.IsFalse(response.IsSuccessStatusCode);
        }

        [Test, Order(5)]
        public void PatchUserPassword_NotFound()
        {
            UserPw temp = new UserPw{Name = "User 3", Password = "123456"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PatchAsync("patchUserPassword", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(5)]
        public void PatchUserPassword_Valid()
        {
            UserPw temp = new UserPw{Name = "User 2", Password = "123456"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PatchAsync("patchUserPassword", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            Thread.Sleep(200);
            
            UserPw temp3 = new UserPw{Name = "User 2", Password = "12345"};
            json = JsonSerializer.Serialize(temp3);
            data = new StringContent(json, Encoding.UTF8, "application/json");
            response = Client.PostAsync("validateUser", data).Result;
            Assert.IsFalse(response.IsSuccessStatusCode);
            temp3 = new UserPw{Name = "User 2", Password = "123456"};
            json = JsonSerializer.Serialize(temp3);
            data = new StringContent(json, Encoding.UTF8, "application/json");
            response = Client.PostAsync("validateUser", data).Result;
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                User res = (User)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
                Console.WriteLine(res);
                Assert.IsTrue(res.Name.Equals(temp.Name));
            }
        }
        
        [Test, Order(5)]
        public void PutUser_NotFound()
        {
            User temp = new User{Name = "User 3", Level = 2, City = "Budapest", Number = "2, 126", Phone = 95913367, Street = "Risdalsvej", ZipCode = 1188};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PutAsync("putUser/" + temp.Name, data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(5)]
        public void PutUser_Valid1()
        {
            User temp = new User{Name = "User 2", Level = 2, City = "Budapest", Number = "2, 126", Phone = 95913367, Street = "Risdalsvej", ZipCode = 1188};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PutAsync("putUser/" + temp.Name, data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            Thread.Sleep(200);
            
            response = Client.GetAsync("getUser/User 2").Result;
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                User res = (User)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
                Console.WriteLine(res);
                Assert.IsTrue(res.City.Equals("Budapest"));
            }
        }
        
        [Test, Order(5)]
        public void PutUser_Valid2()
        {
            User temp = new User{Name = "User_1", Level = 2, City = "Budapest", Number = "2, 126", Phone = 95913367, Street = "Risdalsvej", ZipCode = 1188};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PutAsync("putUser/User 1", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            Thread.Sleep(200);
            
            response = Client.GetAsync("getUser/User_1").Result;
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                User res = (User)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
                Console.WriteLine(res);
                Assert.IsTrue(res.City.Equals("Budapest"));
            }
        }
        
        [Test, Order(6)]
        public void PostGame_Valid1()
        {
            Game temp = new Game{Name = "Game 1", Details = "Detail 1", Genre = "RPG", Price = 69, Type = "PC", User = "User_1"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postGame", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }
        
        [Test, Order(6)]
        public void PostGame_Valid2()
        {
            Game temp = new Game{Name = "Game 2", Details = "Detail 2", Genre = "FPS", Price = 420, Type = "PS4", User = "User 2"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postGame", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }

        [Test, Order(7)]
        public void PostGame_NoUser()
        {
            Game temp = new Game{Name = "Game 3", Details = "Detail 3", Genre = "ASDF", Price = 1488, Type = "XBOX ONE", User = "User 3"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postGame", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(7)]
        public void PostGame_NoType()
        {
            Game temp = new Game{Name = "Game 3", Details = "Detail 3", Genre = "ASDF", Price = 1488, Type = "kolbi.exe", User = "User 2"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postGame", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(8)]
        public void DeleteGame_Valid1()
        {
            HttpResponseMessage response = Client.DeleteAsync("deleteGame/1").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }
        
        [Test, Order(8)]
        public void DeleteGame_Valid2()
        {
            HttpResponseMessage response = Client.DeleteAsync("deleteGame/2").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }
        
        [Test, Order(8)]
        public void DeleteGame_NotFound()
        {
            HttpResponseMessage response = Client.DeleteAsync("deleteGame/3").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(9)]
        public void GetGamesBySearch_Descending()
        {
            for (int i = 10; i < 20; i++)
            {
                Game temp01 = new Game{Name = "Game " + i, Details = "Detail " + i, Genre = "RPG", Price = i, Type = "PC", User = "User 2"};
                var temp02 = JsonSerializer.Serialize(temp01);
                var temp03 = new StringContent(temp02, Encoding.UTF8, "application/json");
                Client.PostAsync("postGame", temp03);
            }
            for (int i = 20; i < 30; i++)
            {
                Game temp01 = new Game{Name = "Game " + i, Details = "Detail " + i, Genre = "FPS", Price = i, Type = "PS4", User = "User_1"};
                var temp02 = JsonSerializer.Serialize(temp01);
                var temp03 = new StringContent(temp02, Encoding.UTF8, "application/json");
                Client.PostAsync("postGame", temp03);
            }
            Thread.Sleep(2000);
            Search temp = new Search{OrderBy = "Name", PageSize = 5, Page = 1, Type = "PC", IsDescending = true};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("getGamesBySearch/User 2", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                List<Game> res = (List<Game>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Game>));
                Console.WriteLine("First:\n" + res[0] + "\n");
                Console.WriteLine("Last:\n" + res[^1] + "\n");
                Assert.IsTrue(res[0].Name.Equals("Game 19"));
                Assert.IsTrue(res[4].Name.Equals("Game 15"));
            }
        }
        
        [Test, Order(10)]
        public void GetGamesBySearch_Ascending()
        {
            Search temp = new Search{OrderBy = "Name", PageSize = 5, Page = 1, Type = "PC", IsDescending = false};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("getGamesBySearch/User 2", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                List<Game> res = (List<Game>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Game>));
                Console.WriteLine("First:\n" + res[0] + "\n");
                Console.WriteLine("Last:\n" + res[^1] + "\n");
                Assert.IsTrue(res[0].Name.Equals("Game 10"));
                Assert.IsTrue(res[4].Name.Equals("Game 14"));
            }
        }
        
        [Test, Order(10)]
        public void GetGamesBySearch_Page1()
        {
            Search temp = new Search{OrderBy = "Name", PageSize = 5, Page = 2, Type = "PC", IsDescending = false};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("getGamesBySearch/User 2", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                List<Game> res = (List<Game>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Game>));
                Console.WriteLine("First:\n" + res[0] + "\n");
                Console.WriteLine("Last:\n" + res[^1] + "\n");
                Assert.IsTrue(res[5].Name.Equals("Game 15"));
                Assert.IsTrue(res[9].Name.Equals("Game 19"));
            }
        }
        
        [Test, Order(10)]
        public void GetGamesBySearch_Page2()
        {
            Search temp = new Search{OrderBy = "Name", PageSize = 7, Page = 1, Type = "PC", IsDescending = false};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("getGamesBySearch/User 2", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                List<Game> res = (List<Game>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Game>));
                Console.WriteLine("First:\n" + res[0] + "\n");
                Console.WriteLine("Last:\n" + res[^1] + "\n");
                Assert.IsTrue(res[0].Name.Equals("Game 10"));
                Assert.IsTrue(res[6].Name.Equals("Game 16"));
            }
        }
        
        [Test, Order(10)]
        public void GetGamesBySearch_Page3()
        {
            Search temp = new Search{OrderBy = "Name", PageSize = 7, Page = 2, Type = "PC", IsDescending = false};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("getGamesBySearch/User 2", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                List<Game> res = (List<Game>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Game>));
                Console.WriteLine("First:\n" + res[0] + "\n");
                Console.WriteLine("Last:\n" + res[^1] + "\n");
                Assert.IsTrue(res[7].Name.Equals("Game 17"));
                //Assert.IsTrue(res[13].Name.Equals("Game 19"));
            }
        }
        
        /*[Test, Order(10)]
        public void GetGamesBySearch_PageNotFound()
        {
            Search temp = new Search{OrderBy = "Name", PageSize = 7, Page = 10, Type = "PC", IsDescending = false};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("getGamesBySearch/User 2", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }*/
        
        [Test, Order(10)]
        public void GetGamesByOwner_Valid1()
        {
            HttpResponseMessage response = Client.GetAsync("getGamesByOwner/User 2/User 2").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                List<Game> res = (List<Game>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Game>));
                Console.WriteLine("First:\n" + res[0] + "\n");
                Console.WriteLine("Last:\n" + res[^1] + "\n");
                for (int i = 0; i < res.Count; i++)
                    Assert.IsTrue(res[i].User.Equals("User 2"));
            }
        }
        
        [Test, Order(10)]
        public void GetGamesByOwner_Valid2()
        {
            HttpResponseMessage response = Client.GetAsync("getGamesByOwner/User_1/User 2").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                List<Game> res = (List<Game>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Game>));
                Console.WriteLine("First:\n" + res[0] + "\n");
                Console.WriteLine("Last:\n" + res[^1] + "\n");
                for (int i = 0; i < res.Count; i++)
                    Assert.IsTrue(res[i].User.Equals("User_1"));
            }
        }
        
        [Test, Order(10)]
        public void GetGamesByOwner_NotFound()
        {
            HttpResponseMessage response = Client.GetAsync("getGamesByOwner/User 3/User 2").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(11)]
        public void PutGame_Valid()
        {
            Game temp = new Game{Id = 10, Name = "Putted", Details = "Putted", Genre = "RPG", Price = 69, Type = "PC", User = "User_1"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PutAsync("putGame", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }
        
        [Test, Order(11)]
        public void PutGame_NotFound()
        {
            Game temp = new Game{Id = 1, Name = "Putted", Details = "Putted", Genre = "RPG", Price = 69, Type = "PC", User = "User_1"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PutAsync("putGame", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(12)]
        public void PostComment_Valid1()
        {
            Comment temp = new Comment{GameId = 20, User = "User 2", Text = "Comment 1"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postComment", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }
        
        [Test, Order(12)]
        public void PostComment_Valid2()
        {
            Comment temp = new Comment{GameId = 21, User = "User 2", Text = "Comment 1"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postComment", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }
        
        [Test, Order(13)]
        public void PostComment_Valid3()
        {
            Comment temp = new Comment{GameId = 20, User = "User_1", Text = "Comment 2"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postComment", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }
        
        [Test, Order(13)]
        public void PostComment_Valid4()
        {
            Comment temp = new Comment{GameId = 21, User = "User_1", Text = "Comment 2"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postComment", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }

        [Test, Order(14)]
        public void PostComment_GameNotFound()
        {
            Comment temp = new Comment{GameId = 1, User = "User_1", Text = "Comment 2"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postComment", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(14)]
        public void PostComment_UserNotFound()
        {
            Comment temp = new Comment{GameId = 20, User = "User 3", Text = "Comment 2"};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postComment", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }

        [Test, Order(14)]
        public void GetComments_Valid1()
        {
            HttpResponseMessage response = Client.GetAsync("getComments/20").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                List<Comment> res = (List<Comment>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Comment>));
                Console.WriteLine("First:\n" + res[0] + "\n");
                Console.WriteLine("Last:\n" + res[^1] + "\n");
                for (int i = 0; i < res.Count; i++)
                    Assert.IsTrue(res[i].GameId == 20);
            }
        }
        
        [Test, Order(14)]
        public void GetComments_Valid2()
        {
            HttpResponseMessage response = Client.GetAsync("getComments/21").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                List<Comment> res = (List<Comment>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Comment>));
                Console.WriteLine("First:\n" + res[0] + "\n");
                Console.WriteLine("Last:\n" + res[^1] + "\n");
                for (int i = 0; i < res.Count; i++)
                    Assert.IsTrue(res[i].GameId == 21);
            }
        }
        
        [Test, Order(14)]
        public void GetComments_NotFound()
        {
            HttpResponseMessage response = Client.GetAsync("getComments/1").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(12)]
        public void PostRating_Valid1()
        {
            Game temp2 = new Game{Name = "Rate Test", Details = "Rate Test", Genre = "RPG", Price = 69, Type = "PC", User = "User_1"};
            var json = JsonSerializer.Serialize(temp2);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            Client.PostAsync("postGame", data);
            Thread.Sleep(200);
            
            Rate temp = new Rate{GameId = 25, User = "User_1", Rating = 0};
            json = JsonSerializer.Serialize(temp);
            data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRating", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            
            Search temp1 = new Search{OrderBy = "Name", PageSize = 1, Page = 1, Type = "PC", IsDescending = false, Name = "Rate Test"};
            json = JsonSerializer.Serialize(temp1);
            data = new StringContent(json, Encoding.UTF8, "application/json");
            response = Client.PostAsync("getGamesBySearch/User 2", data).Result;
            if (response.IsSuccessStatusCode)
            {
                List<Game> res = (List<Game>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Game>));
                Console.WriteLine("Game:\n" + res[0] + "\n");
                Assert.IsTrue(res[0].UserRating == null);
                Assert.IsTrue(res[0].Rating == 0);
            }
        }
        
        [Test, Order(13)]
        public void PostRating_Valid2()
        {
            Rate temp = new Rate{GameId = 25, User = "User 2", Rating = 5};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRating", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            
            Search temp1 = new Search{OrderBy = "Name", PageSize = 1, Page = 1, Type = "PC", IsDescending = false, Name = "Rate Test"};
            json = JsonSerializer.Serialize(temp1);
            data = new StringContent(json, Encoding.UTF8, "application/json");
            response = Client.PostAsync("getGamesBySearch/User 2", data).Result;
            if (response.IsSuccessStatusCode)
            {
                List<Game> res = (List<Game>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Game>));
                Console.WriteLine("Game:\n" + res[0] + "\n");
                Assert.IsTrue(res[0].UserRating == 5);
                Assert.IsTrue(res[0].Rating == 2.5f);
            }
        }
        
        [Test, Order(13)]
        public void PostRating_AlreadyExists()
        {
            Rate temp = new Rate{GameId = 25, User = "User_1", Rating = 0};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRating", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(13)]
        public void PostRating_GameNotFound()
        {
            Rate temp = new Rate{GameId = 1, User = "User_1", Rating = 0};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRating", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(13)]
        public void PostRating_UserNotFound()
        {
            Rate temp = new Rate{GameId = 20, User = "User 3", Rating = 0};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRating", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(13)]
        public void PostRating_InvalidValue()
        {
            Rate temp = new Rate{GameId = 20, User = "User 2", Rating = 6};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRating", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(13)]
        public void PostRent_Valid1()
        {
            Rent temp = new Rent{GameId = 24, User = "User_1", RentFrom = new DateTime(2021,1,1), RentUntil = new DateTime(2021,1,5)};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRent", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }
        
        [Test, Order(13)]
        public void PostRent_Valid2()
        {
            Rent temp = new Rent{GameId = 24, User = "User 2", RentFrom = new DateTime(2021,1,6), RentUntil = new DateTime(2021,1,7)};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRent", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }
        
        [Test, Order(13)]
        public void PostRent_Valid3()
        {
            Rent temp = new Rent{GameId = 25, User = "User 2", RentFrom = new DateTime(2021,1,1), RentUntil = new DateTime(2021,1,5)};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRent", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }
        
        [Test, Order(13)]
        public void PostRent_Valid4()
        {
            Rent temp = new Rent{GameId = 25, User = "User_1", RentFrom = new DateTime(2021,1,6), RentUntil = new DateTime(2021,1,7)};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRent", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
        }
        
        [Test, Order(14)]
        public void PostRent_Conflict()
        {
            Rent temp = new Rent{GameId = 24, User = "User_1", RentFrom = new DateTime(2021,1,2), RentUntil = new DateTime(2021,1,5)};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRent", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(14)]
        public void PostRent_GameNotFound()
        {
            Rent temp = new Rent{GameId = 1, User = "User_1", RentFrom = new DateTime(2021,1,2), RentUntil = new DateTime(2021,1,5)};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRent", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(14)]
        public void PostRent_UserNotFound()
        {
            Rent temp = new Rent{GameId = 23, User = "User 3", RentFrom = new DateTime(2021,1,2), RentUntil = new DateTime(2021,1,5)};
            var json = JsonSerializer.Serialize(temp);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = Client.PostAsync("postRent", data).Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(14)]
        public void GetRentsByUser_Valid()
        {
            HttpResponseMessage response = Client.GetAsync("getRentsByUser/User_1").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                List<Rent> res = (List<Rent>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Rent>));
                Console.WriteLine("Rent 1:\n" + res[0] + "\n");
                Console.WriteLine("Rent 2:\n" + res[1] + "\n");
                Assert.IsTrue(res[0].User.Equals("User_1"));
                Assert.IsTrue(res[1].User.Equals("User_1"));
            }
        }
        
        [Test, Order(14)]
        public void GetRentsByUser_NotFound()
        {
            HttpResponseMessage response = Client.GetAsync("getRentsByUser/User 3").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
        
        [Test, Order(14)]
        public void GetRentsByGame_Valid()
        {
            HttpResponseMessage response = Client.GetAsync("getRentsByGame/25").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsTrue(response.IsSuccessStatusCode);
            if (response.IsSuccessStatusCode)
            {
                List<Rent> res = (List<Rent>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Rent>));
                Console.WriteLine("Rent 1:\n" + res[0] + "\n");
                Console.WriteLine("Rent 2:\n" + res[1] + "\n");
                Assert.IsTrue(res[0].GameId == 25);
                Assert.IsTrue(res[1].GameId == 25);
            }
        }
        
        [Test, Order(14)]
        public void GetRentsByGame_NotFound()
        {
            HttpResponseMessage response = Client.GetAsync("getRentsByGame/1").Result;
            Console.WriteLine("StatusCode: " + response.StatusCode + "\n");
            Assert.IsFalse(response.IsSuccessStatusCode);
        }
    }
}