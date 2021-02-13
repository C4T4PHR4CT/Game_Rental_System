using System.Collections.Generic;
using System.Threading.Tasks;
using WebApplication7.Model;

namespace WebApplication7.Data
{
    public interface IGameService
    {
        public Task<List<Game>> GetGamesBySearch(Search search);
        
        public Task<List<Game>> GetGamesOwned(string name);
        
        public Task<Game> GetGame(int id);

        public Task AddGame(Game game);

        public Task DeleteGame(int? id);

        public Task EditGame(Game game);
    }
}