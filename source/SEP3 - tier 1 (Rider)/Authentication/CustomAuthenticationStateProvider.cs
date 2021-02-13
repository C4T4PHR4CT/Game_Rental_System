using System;
using System.Collections.Generic;
using System.Security.Claims;
using System.Text.Json;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.JSInterop;
using WebApplication7.Data;
using WebApplication7.Model;

namespace WebApplication7.Authentication
{
    public class CustomAuthenticationStateProvider : AuthenticationStateProvider
    {
        private readonly IJSRuntime _jsRuntime;
        private readonly IUserService _userService;
        private User _cachedUser;

        public CustomAuthenticationStateProvider(IJSRuntime jsRuntime, IUserService userService)
        {
            _jsRuntime = jsRuntime;
            _userService = userService;
        }

        public override async Task<AuthenticationState> GetAuthenticationStateAsync()
        {
            var identity = new ClaimsIdentity();
            if (_cachedUser == null)
            {
                string userAsJson = await _jsRuntime.InvokeAsync<string>("sessionStorage.getItem", "currentUser");
                if (!string.IsNullOrEmpty(userAsJson))
                {
                    _cachedUser = JsonSerializer.Deserialize<User>(userAsJson);

                    identity = new ClaimsIdentity(new List<Claim>(), "apiauth_type");
                }
            }
            else
            {
                identity = new ClaimsIdentity(new List<Claim>(), "apiauth_type");
            }

            ClaimsPrincipal cachedClaimsPrincipal = new ClaimsPrincipal(identity);
            return await Task.FromResult(new AuthenticationState(cachedClaimsPrincipal));
        }

        public async Task ValidateLogin(string username, string password)
        {
            Console.WriteLine("Validating log in");
            if (string.IsNullOrEmpty(username)) throw new Exception("Enter username");
            if (string.IsNullOrEmpty(password)) throw new Exception("Enter password");
            User user = await _userService.ValidateUser(username, password);
            var identity = new ClaimsIdentity(new List<Claim>(), "apiauth_type");
            string serialisedData = JsonSerializer.Serialize(user);
            await _jsRuntime.InvokeVoidAsync("sessionStorage.setItem", "currentUser", serialisedData);
            _cachedUser = user;
            NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(new ClaimsPrincipal(identity))));
        }

        public async Task Logout()
        {
            var user = new ClaimsPrincipal(new ClaimsIdentity());
            await _jsRuntime.InvokeVoidAsync("sessionStorage.setItem", "currentUser", "");
            NotifyAuthenticationStateChanged(Task.FromResult(new AuthenticationState(user)));
            _cachedUser = null;
        }

        public async Task<User> GetCachedUser()
        {
            if (_cachedUser == null)
            {
                string userAsJson = await _jsRuntime.InvokeAsync<string>("sessionStorage.getItem", "currentUser");
                if (!string.IsNullOrEmpty(userAsJson))
                    _cachedUser = JsonSerializer.Deserialize<User>(userAsJson);
            }
            return _cachedUser;
        }
    }
}
