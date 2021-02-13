using System;

namespace Model
{
    public class Comment
    {
        public int GameId { get; set; }
        public string User { get; set; }
        public string Text { get; set; }
        public long? Time
        {
            get
            {
                if (TimeStamp == null)
                    return null;
                return (TimeStamp.Value.Ticks - new DateTime(1970, 1, 1).Ticks) / 10000;
            }
            set {if (value != null) TimeStamp = new DateTime(value.Value * 10000 + new DateTime(1970, 1, 1).Ticks);}
        }

        public DateTime? TimeStamp { get; set; }

        public override string ToString()
        {
            return "GameId: " + GameId + "\nUser: " + User + "\nText: " + Text + "\nTimeStamp: " + TimeStamp;
        }
    }
}