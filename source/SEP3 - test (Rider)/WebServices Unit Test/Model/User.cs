namespace Model {
    public class User
    {
        public string Name { get; set; }
        
        public int Level { get; set; }
        
        public int? Phone { get; set; }

        public string City { get; set; }
        
        public int? ZipCode { get; set; }
        
        public string Street { get; set; }
        
        public string Number { get; set; }

        public override string ToString()
        {
            string temp = "Name: " + Name + "\nLevel: " + Level + "\nPhone: ";
            if (Phone == null)
                temp += "null";
            else
                temp += Phone;
            temp += "\nCity: ";
            if (City == null)
                temp += "null";
            else
                temp += City;
            temp += "\nZipCode: ";
            if (ZipCode == null)
                temp += "null";
            else
                temp += ZipCode;
            temp += "\nStreet: ";
            if (Street == null)
                temp += "null";
            else
                temp += Street;
            temp += "\nNumber: ";
            if (Number == null)
                temp += "null";
            else
                temp += Number;
            return temp;
        }
    }
    
}