using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using WebApplication7.Model;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace WebApplication7.Data
{
    public class RentService : IRentService
    {
        private readonly IHttpService _httpService;
        
        public RentService(IHttpService httpService)
        {
            _httpService = httpService;
        }
        
        public async Task<List<Rent>> GetRentsByUser(string name)
        {
            HttpResponseMessage response = await _httpService.GetClient().GetAsync($"getRentsByUser/{name}");
            if (response.IsSuccessStatusCode)
                return (List<Rent>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Rent>));
            return new List<Rent>();
        }
        
        public async Task<List<Rent>> GetRentsByGame(int gameId)
        {
            HttpResponseMessage response = await _httpService.GetClient().GetAsync($"getRentsByGame/{gameId}");
            if (response.IsSuccessStatusCode)
                return (List<Rent>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Rent>));
            return new List<Rent>();
        }

        public async Task PostRent(Rent rent)
        {
            var json = JsonSerializer.Serialize(rent);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            await _httpService.GetClient().PostAsync("postRent", data);
        }
    }
}