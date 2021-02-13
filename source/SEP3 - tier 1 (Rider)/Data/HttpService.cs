using System;
using System.Net.Http;

namespace WebApplication7.Data
{
    public class HttpService : IHttpService
    {
        private readonly HttpClient _client;

        public HttpService()
        {
            _client = new HttpClient {BaseAddress = new Uri("http://localhost:8080/game_rental/")};
        }
        
        public HttpClient GetClient()
        {
            return _client;
        }
    }
}