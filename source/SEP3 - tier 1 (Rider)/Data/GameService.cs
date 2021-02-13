using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using WebApplication7.Model;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace WebApplication7.Data
{
    public class GameService : IGameService
    {
        private readonly IHttpService _httpService;

        public GameService(IHttpService httpService)
        {
            _httpService = httpService;
        }
        
        public async Task<List<Game>> GetGamesBySearch(Search search)
        {
            var json = JsonSerializer.Serialize(search);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await _httpService.GetClient().PostAsync("getGamesBySearch/User 2", data);
            if (response.IsSuccessStatusCode)
                return (List<Game>) JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Game>));
            return new List<Game>();
        }

        public async Task<List<Game>> GetGamesOwned(string name)
        {
            HttpResponseMessage response = await _httpService.GetClient().GetAsync($"getGamesByOwner/{name}/{name}");
            if (response.IsSuccessStatusCode)
                return (List<Game>)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(List<Game>));
            return new List<Game>();
        }
        
        public async Task<Game> GetGame(int id)
        {
            HttpResponseMessage response = await _httpService.GetClient().GetAsync($"getGameById/{id}/{id}");
            if (response.IsSuccessStatusCode)
                return (Game)JsonSerializer.Deserialize(response.Content.ReadAsStringAsync().Result, typeof(Game));
            return new Game();
        }

        public async Task AddGame(Game game)
        {
            var json = JsonSerializer.Serialize(game);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            await _httpService.GetClient().PostAsync("postGame", data);
        }

        public async Task DeleteGame(int? id)
        {
            await _httpService.GetClient().DeleteAsync($"deleteGame/{id}");
        }

        public async Task EditGame(Game game)
        {
            var json = JsonSerializer.Serialize(game);
            var data = new StringContent(json, Encoding.UTF8, "application/json");
            await _httpService.GetClient().PutAsync("putGame", data);
        }
    }
}