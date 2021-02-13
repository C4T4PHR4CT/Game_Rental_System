namespace Model
{
    public class Search
    {
        public string Type { get; set; }
        
        public string OrderBy { get; set; }
        
        public bool IsDescending { get; set; }
        
        public int PageSize { get; set; }
        
        public int Page { get; set; }
        
        public string Genre { get; set; }
        
        public string Name { get; set; }

        public override string ToString()
        {
            return "Type: " + Type + "\nOrderBy: " + OrderBy + "\nIsDescending: " + IsDescending + "\nPageSize: " + PageSize + "\nPage: " + Page + "\nGenre: " + Genre + "\nName: " + Name;
        }
    }
}