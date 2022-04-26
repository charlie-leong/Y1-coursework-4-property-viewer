import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import com.opencsv.CSVReader;
import java.net.URISyntaxException;

public class DataLoader {
 
    /** 
     * Return an ArrayList containing the rows in the AirBnB London data set csv file.
     * @return arraylist of airbnb listings
     */
    public ArrayList<AirbnbListing> loadAirbnbData() {
        System.out.print("Begin loading Airbnb london dataset...");
        ArrayList<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        try{
            URL url = getClass().getResource("airbnb-london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String id = line[0];
                String name = line[1];
                String host_id = line[2];
                String host_name = line[3];
                String neighbourhood = line[4];
                double latitude = convertDouble(line[5]);
                double longitude = convertDouble(line[6]);
                String room_type = line[7];
                int price = convertInt(line[8]);
                int minimumNights = convertInt(line[9]);
                int numberOfReviews = convertInt(line[10]);
                String lastReview = line[11];
                double reviewsPerMonth = convertDouble(line[12]);
                int calculatedHostListingsCount = convertInt(line[13]);
                int availability365 = convertInt(line[14]);

                AirbnbListing listing = new AirbnbListing(id, name, host_id,
                        host_name, neighbourhood, latitude, longitude, room_type,
                        price, minimumNights, numberOfReviews, lastReview,
                        reviewsPerMonth, calculatedHostListingsCount, availability365
                    );
                listings.add(listing);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        System.out.println("Success! Number of loaded records: " + listings.size());
        return listings;
    }
     /**
      * Returns an arraylist containing the row in the destination data set csv file
      * @return arraylist containging tourist destination locations
      */
    public ArrayList<Destination> loadDestinationData(){
        System.out.print("Begin loading london destination dataset...");
        ArrayList<Destination> destinations = new ArrayList<>();
        try{
            URL url = getClass().getResource("Destination-list.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //the first row containing column headers is skipped
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String name = line[0]; 
                double lat = convertDouble(line[1]);
                double lon = convertDouble(line[2]);
                String description = line[3];
                
                destinations.add(new Destination(name, lon, lat, description));
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("yikes sorry! something went wrong");
        }
        System.out.println("great! number of records loaded: "+ destinations.size());
        return destinations;
    }

    /**
     *
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    private Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

    /**
     *
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is 
     * either empty or just whitespace
     */
    private Integer convertInt(String intString){
        if(intString != null && !intString.trim().equals("")){
            return Integer.parseInt(intString);
        }
        return -1;
    }
 
 /**
     * A method which loads the data set and then returns 
     * the average amount of reviews per listing. 
     */
    public double averageReviews()
    {
        ArrayList<AirbnbListing> list = loadAirbnbData();
        int numberOfListings = list.size();
        int numberOfReviews = 0;
        for(AirbnbListing temp : list)
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
        ArrayList<AirbnbListing> list = loadAirbnbData();
        int total = 0;
        for(int i = 0; i <= list.size(); i++)
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
        ArrayList<AirbnbListing> list = loadAirbnbData();
        int total = 0;
        for(AirbnbListing temp : list)
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
        ArrayList<AirbnbListing> listOfListings = loadAirbnbData();
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
        ArrayList<AirbnbListing> listOfListings = loadAirbnbData();
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
       ArrayList<AirbnbListing> list = loadAirbnbData();
        int total = 0;
        for(AirbnbListing temp : list)
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
        ArrayList<AirbnbListing> list = loadAirbnbData();
        int total = 0;
        for(AirbnbListing temp : list)
        {
            if(minNumOfNights > temp.getMinimumNights())
            {
                total = total + 1;
            }
        }
        
        return(total); 
    }
 
 public String mostAvailableBorough()
    {
        ArrayList<AirbnbListing> listOfListings = loadAirbnbData();
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
    
 

}
