namespace Model
{
    public class Game
    {
        public int? Id { get; set; }
        public string Name { get; set; }
        public string Details { get; set; }
        public double Price { get; set; }
        public string User { get; set; }
        
        public string Type { get; set; }
        
        public string Genre { get; set; }
        
        public double? Rating { get; set; }
        
        public int? UserRating { get; set; }

        public override string ToString()
        {
            string temp = "Id: " + Id + "\nName: " + Name + "\nPrice: " + Price + "\nUser: " + User + "\nType: " + Type + "\nDetails: ";
            if (Details == null)
                temp += "null";
            else
                temp += Details;
            temp += "\nGenre: ";
            if (Genre == null)
                temp += "null";
            else
                temp += Genre;
            temp += "\nRating: ";
            if (Rating == null)
                temp += "null";
            else
                temp += Rating;
            temp += "\nUserRating: ";
            if (UserRating == null)
                temp += "null";
            else
                temp += UserRating;
            return temp;
        }
    }
}