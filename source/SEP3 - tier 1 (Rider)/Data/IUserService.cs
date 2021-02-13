using System.Threading.Tasks;
using WebApplication7.Model;


namespace WebApplication7.Data
{
    public interface IUserService
    {
        Task<User> ValidateUser(string userName, string password);

        Task<User> RegisterUser(UserPw userPw);

        Task UpdatePw(UserPw userPw);

        Task PutUser(User user, string oldName);
        
        Task<User> GetUser(string userName);
    }
}