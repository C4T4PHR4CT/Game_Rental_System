namespace WebApplication7.Model
{
    public class Search
    {
        public string Type { get; set; }
        
        public string OrderBy { get; set; }
        
        public bool IsDescending { get; set; }
        
        public string Genre { get; set; }
        
        public string Name { get; set; }

        public override string ToString()
        {
            return "Type: " + Type + "\nOrderBy: " + OrderBy + "\nIsDescending: " + IsDescending + "\nPageSize: " + "\nGenre: " + Genre + "\nName: " + Name;
        }
    }
}