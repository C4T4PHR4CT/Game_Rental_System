namespace WebApplication7.Model
{
    public class UserPw
    {
        public string Name { set; get;}
        public string Password { set; get; }

        public UserPw(string name, string password)
        {
            Name = name;
            Password = password;
        }
        
        public override string ToString()
        {
            return Name + " " + Password;
        }
    }
}