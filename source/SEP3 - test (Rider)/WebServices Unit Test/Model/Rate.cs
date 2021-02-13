namespace Model
{
    public class Rate
    {
        public int GameId { get; set; }
        
        public string User { get; set; }
        
        public int Rating { get; set; }

        public override string ToString()
        {
            return GameId + " " + User + " " + Rating;
        }
    }
}