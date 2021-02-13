using System.Net.Http;

namespace WebApplication7.Data
{
    public interface IHttpService
    {
        HttpClient GetClient();
    }
}