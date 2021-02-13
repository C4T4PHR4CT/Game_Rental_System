using System.Collections.Generic;
using System.Threading.Tasks;
using WebApplication7.Model;

namespace WebApplication7.Data
{
    public interface IRentService
    {
        
        public Task<List<Rent>> GetRentsByUser(string name);
        public Task<List<Rent>> GetRentsByGame(int gameId);
        public Task PostRent(Rent rent);

    }
}