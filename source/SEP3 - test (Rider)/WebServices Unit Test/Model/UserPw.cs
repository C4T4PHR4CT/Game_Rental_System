namespace Model
{
    public class UserPw
    {
        
        public string Name { set; get;}
        public string Password { set; get; }

        public override string ToString()
        {
            return "Name: " + Name + "\nPassword: " + Password;
        }
    }
}