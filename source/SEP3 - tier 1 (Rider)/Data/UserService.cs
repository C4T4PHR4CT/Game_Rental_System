using System;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using WebApplication7.Model;


namespace WebApplication7.Data
{
    public class UserService : IUserService
    {
        private readonly IHttpService _httpService;

        public UserService(IHttpService httpService)
        {
            _httpService = httpService;
        }

        public async Task<User> ValidateUser(string userName, string password)
        {
            StringContent data = new StringContent(JsonSerializer.Serialize(new UserPw(userName, password)), Encoding.UTF8, "application/json");
            HttpResponseMessage response = await _httpService.GetClient().PostAsync("validateUser", data);
            if (response.IsSuccessStatusCode)
                return (User) JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
            throw response.StatusCode switch
            {
                HttpStatusCode.Forbidden => new Exception("Invalid password"),
                HttpStatusCode.NotFound => new Exception("Can't find user"),
                _ => new Exception("Internal Server Error")
            };
        }

        public async Task<User> RegisterUser(UserPw userPw)
        {
            var json = JsonSerializer.Serialize(userPw);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await _httpService.GetClient().PostAsync("registerUser", data);
            if (response.IsSuccessStatusCode)
                return (User) JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
            throw response.StatusCode switch
            {
                HttpStatusCode.InternalServerError => new Exception("Internal Server Error"),
                _ => new Exception("Username taken")
            };
        }

        public async Task UpdatePw(UserPw userPw)
        {
            var json = JsonSerializer.Serialize(userPw);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            await _httpService.GetClient().PatchAsync("patchUserPassword", data);
        }

        public async Task PutUser(User user, String oldName)
        {
            var json = JsonSerializer.Serialize(user);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            await _httpService.GetClient().PutAsync("putUser/" + oldName, data);
        }
        
        public async Task<User> GetUser(string userName)
        {
            HttpResponseMessage response = await _httpService.GetClient().GetAsync("getUser/" + userName);
            if (response.IsSuccessStatusCode)
                return (User) JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(User));
            return new User();
        }
    }
}