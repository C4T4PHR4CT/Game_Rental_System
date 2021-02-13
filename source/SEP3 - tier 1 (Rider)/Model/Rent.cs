using System;

namespace WebApplication7.Model
{
    public class Rent
    {
        public int GameId { get; set; }
        public String User { get; set; }
        public long TimeFrom
        {
            get => (RentFrom.Ticks - new DateTime(1970,1, 1).Ticks) / 10000;
            set => RentFrom = new DateTime(value * 10000 + new DateTime(1970, 1, 1).Ticks);
        }
        public long TimeUntil
        {
            get => (RentUntil.Ticks - new DateTime(1970,1, 1).Ticks) / 10000;
            set => RentUntil = new DateTime(value * 10000 + new DateTime(1970, 1, 1).Ticks);
        }
        public DateTime RentFrom { get; set; }
        public DateTime RentUntil { get; set; }

        public override string ToString()
        {
            return "GameId: " + GameId + "\nUser: " + User + "\nRentFrom: " +  RentFrom + "\nRentUntil: " + RentUntil;
        }
    }
}