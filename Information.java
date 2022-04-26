import java.util.ArrayList;
import java.util.Hashtable;

/**
* This class is where each of the panels gets and shares the information around 
* with the other panels. The instance of the class is created with the welcomeGUI
* and then passed as a parameter through when each of the other panels is called. 
* This class has methods to get information and also holds the methods to calculate 
* the different statistics from the statPanelGUI class.
*
* Dominic Child
* 30/03/2022
*/

public class Information {
    public ArrayList<Borough> allBoroughs;
    public Hashtable<String, Borough> boroughAbbreviations;
    private DataLoader data = new DataLoader();
    private ArrayList<Destination> destinationList = new ArrayList<>();
    private ArrayList<AirbnbListing> listOfListings;
    
    private int minimumCost;
    private int maximumCost;
    private int minimumNights;
  
    public Information(){
        this.boroughAbbreviations = new Hashtable<>();
        setBoroughs(data.loadAirbnbData());
        listOfListings = data.loadAirbnbData();
       
    }
    

    /**
     * This method constructs and adds all borough instances into the borough arraylist
     * this sets their names and links them to a more accessible abbreviation
     * @properties list of total properties to be filtered through
     */
    public void setBoroughs(ArrayList<AirbnbListing> properties){
        boroughAbbreviations.put("ENFI", new Borough("Enfield", properties));
        boroughAbbreviations.put("BARN", new Borough("Barnet", properties));
        boroughAbbreviations.put("HRGY", new Borough("Haringey", properties));
        boroughAbbreviations.put("WALT", new Borough("Waltham", properties));
        boroughAbbreviations.put("HRRW", new Borough("Harrow", properties));
        boroughAbbreviations.put("BREN", new Borough("Brent", properties));
        boroughAbbreviations.put("CAMD", new Borough("Camden", properties));
        boroughAbbreviations.put("ISLI", new Borough("Islington", properties));
        boroughAbbreviations.put("HACK", new Borough("Hackney", properties));
        boroughAbbreviations.put("REDB", new Borough("Redbridge", properties));
        boroughAbbreviations.put("HILL", new Borough("Hillingdon", properties));
        boroughAbbreviations.put("EALI", new Borough("Ealing", properties));
        boroughAbbreviations.put("KENS", new Borough("Kensington", properties));
        boroughAbbreviations.put("WSTM", new Borough("Westminster", properties));
        boroughAbbreviations.put("TOWH", new Borough("Tower Hamlets", properties));
        boroughAbbreviations.put("NEWH", new Borough("Newham", properties));
        boroughAbbreviations.put("HOUN", new Borough("Hounslow", properties));
        boroughAbbreviations.put("HAMM", new Borough("Hammersmith and Fulham", properties));
        boroughAbbreviations.put("WAND", new Borough("Wandsworth", properties));
        boroughAbbreviations.put("CITY", new Borough("City of London", properties));
        boroughAbbreviations.put("GWCH", new Borough("Greenwich", properties));
        boroughAbbreviations.put("BEXL", new Borough("Bexley", properties));
        boroughAbbreviations.put("RICH", new Borough("Richmond", properties));
        boroughAbbreviations.put("MERT", new Borough("Merton", properties));
        boroughAbbreviations.put("LAMB", new Borough("Lambeth", properties));
        boroughAbbreviations.put("STHW", new Borough("Southwark", properties));
        boroughAbbreviations.put("LEWS", new Borough("Lewisham", properties));
        boroughAbbreviations.put("KING", new Borough("Kingston upon Thames", properties));
        boroughAbbreviations.put("SUTT", new Borough("Sutton", properties));
        boroughAbbreviations.put("CROY", new Borough("Croydon", properties));
        boroughAbbreviations.put("BROM", new Borough("Bromley", properties));
        boroughAbbreviations.put("HAVE", new Borough("Havering", properties));
        boroughAbbreviations.put("BARK", new Borough("Barking and Dagenham", properties));

    }

    /**
    * Method that returns a borough when given the borough 
    * abbreviation
    */
    
    public Borough getBorough(String n){
        return boroughAbbreviations.get(n);
    }

    /**
    * A method which returns the number of listings 
    * in a borough
    */
    
    public int getNumberOfListing(String n) {
        return boroughAbbreviations.get(n).getNumberOfProperties();
    }
    
    /**
    * A method that returns the minimum nmber of nights
    */
    
    public int getMinimumNights(){
        return minimumNights;
    }
    
    /**
    * A method that returns the maximum cost for filtering
    */
    
    public int getMaximumCost(){
        return maximumCost;
    }
    
    /**
    * A method that returns the minimum cost for filtering
    */
    
    public int getMinimumCost(){
        return minimumCost;
    }

    /**
    * A method that sets the minimum cost
    */
    
    public void setMinCost(int cost){
        this.minimumCost = cost;
    }
    
    /**
    * A method that sets the maximum cost 
    */ 
    
    public void setMaxCost(int cost){
        this.maximumCost = cost;
    }
    
    /**
    * A method that sets the minimum number of nights
    */
    
    public void setMinNights(int nights){
        this.minimumNights = nights;
    }

/**
     * A method which loads the data set and then returns 
     * the average amount of reviews per listing. 
     */
    public double averageReviews()
    {
        int numberOfListings = listOfListings.size();
        int numberOfReviews = 0;
        for(AirbnbListing temp : listOfListings)
        {
            numberOfReviews = numberOfReviews + temp.getNumberOfReviews();
        }
        
        return(numberOfReviews / numberOfListings);
    }
 
 /**
     * A method to return the total number of listings in 
     * the data set.
     */
    public int totalNumberOfListings()
    {
        int total = 0;
        for(int i = 0; i <= listOfListings.size(); i++)
        {
            total = i;
        }
        
        return(total);
    }
 
 /**
     * A method to return the total number of listings which are not 
     * a private room, so listings which are entire houses or 
     * appartments.
     */
    public int totalNumberOfEntireHomes()
    {
        int total = 0;
        for(AirbnbListing temp : listOfListings)
        {
            if(!(temp.getRoom_type().equals("Private room")))
            {
                total = total + 1;
            }
        }
        
        return(total);
    }
 
 /**
     * A method which loads all of the boroughs into an arrayList
     * The boroughs are stored as strings.
     * The method ensures that the same borough is not added twice. 
     */
    public ArrayList<String> loadBoroughs()
    {
        ArrayList<String> listOfBoroughs = new ArrayList<String>();
        for(AirbnbListing tempListing : listOfListings)
        {
            if(!(listOfBoroughs.contains(tempListing.getNeighbourhood())))
            {
                listOfBoroughs.add(tempListing.getNeighbourhood());
            }
        }
        
        return(listOfBoroughs);
    }
 
 /**
     * This method loops through a list of the boroughs and with each
     * borough it loops through all of the listings. If the listing
     * matches the borough, then the total price of that listing is added 
     * to the borough price, and once all of the listings are looped through
     * the price is compared to the current max price and the max price 
     * is replaced if it is greater. 
     * 
     * returns the most expensive borough as a string.
     */
    public String mostExpensiveBorough()
    {
        ArrayList<String> listOfBoroughs = loadBoroughs();
        String mostExpensive = "";
        int maxPrice = 0;
        int boroughPrice = 0;
        for(String tempBorough : listOfBoroughs)
        {
            boroughPrice = 0;
            for(AirbnbListing tempListing : listOfListings)
            {
                if(tempListing.getNeighbourhood().equals(tempBorough))
                {
                    boroughPrice = boroughPrice + tempListing.getPrice() * tempListing.getMinimumNights();
                }
            }
            if(boroughPrice > maxPrice)
            {
                maxPrice = boroughPrice;
                mostExpensive = tempBorough;
            }
        }
        return(mostExpensive);
    }
 
 /**
     * A method which counts up all the listings which match the 
     * price range set by the user on panel 1. Right now, the method 
     * works on instance variables stored in AirbnbGUI as panel 1 is 
     * not finished yet.
     */
    public int totalListingOverPriceRange(int minPrice, int maxPrice)
    {
        int total = 0;
        for(AirbnbListing temp : listOfListings)
        {
            int listingCost = temp.getMinimumNights() * temp.getPrice();
            if(listingCost < maxPrice && listingCost > minPrice)
            {
                total = total + 1;
            }
        }
        
        return(total); 
    }
 
 /**
     * Method which will return the number of Listings which meet 
     * the users requirment on the minimum number of nights, this depends 
     * on panel 1, so the value passed in right now is stored in an 
     * instance variable in AirbnbGUI
     */
    public int totalListingOverNumOfNights(int minNumOfNights)
    {
        int total = 0;
        for(AirbnbListing temp : listOfListings)
        {
            if(minNumOfNights >= temp.getMinimumNights())
            {
                total = total + 1;
            }
        }
        
        return(total); 
    }
 
    /**
    * This method finds the most available borough in terms of 
    * available days. It takes each listing in a borough, and totals 
    * their available days, and compares it with the borough with the 
    * current largest.
    */
    
 public String mostAvailableBorough(){
        ArrayList<String> listOfBoroughs = loadBoroughs();
        String mostAvailable = "";
        int maxDays = 0;
        int boroughDays = 0;
        for(String tempBorough : listOfBoroughs)
        {
            boroughDays = 0;
            for(AirbnbListing tempListing : listOfListings)
            {
                if(tempListing.getNeighbourhood().equals(tempBorough))
                {
                    boroughDays = boroughDays + tempListing.getAvailability365();
                }
            }
            if(boroughDays > maxDays)
            {
                maxDays = boroughDays;
                mostAvailable = tempBorough;
            }
        }
        return(mostAvailable);
        
    }
    
    /**
    * This method takes the size of a second dataset which holds 
    * torist destinations, and divides that by the size of the 
    * borough list to find the average number of torist destinations 
    * per borough 
    */
    
    public float averageDestinations()
    {
        ArrayList<String> listOfBoroughs = loadBoroughs();
        float boroughSize = listOfBoroughs.size();
        destinationList = data.loadDestinationData();
        float destinationSize = destinationList.size();
        
        return(destinationSize / boroughSize);
    
    }
    
    /**
    * A method that returns the number of listings in the borough 
    * with the largest number of listings. 
    */
    
    public int largestNumOfProperties(){
        int max = 0;
            
        for(Borough borough: boroughAbbreviations.values()){
            int properties = borough.getNumberOfProperties();
            if(properties > max){
                max = properties;
            }
        }

        return max;
    }
 
}
