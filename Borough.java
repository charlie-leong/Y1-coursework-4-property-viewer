import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.*;

/**
 * The purpose of this class is to split up the statistics given in the cvs file.
 * Authors: Sakura Mikano, Charlotte Leong
 * Version: 2
 */
//public class Borough<Arraylist> {
public class Borough {
    private int numberOfProperties;
    private ArrayList<AirbnbListing> boroughListings;
    private String boroughName;

    /**
     * Creates a new list of neighbourhoods, calls the appropriate methods.
     * Instantiates other variables.
     *
     * @param name
     * @param allProperties all properties received from the csv file to be filtered into boroughs
     */
    public Borough(String name, ArrayList<AirbnbListing> allProperties) {
        this.boroughName = name;
        boroughListings =(listsInTheBorough(allProperties));
        this.numberOfProperties = boroughListings.size();
    }

    /**
     * Creates a stream that creates a list of all the properties within the same boroughs.
     *
     * @param properties all properties received from csv file
     * @return properties in the given borough
     */
    
    public ArrayList<AirbnbListing> listsInTheBorough(ArrayList<AirbnbListing> properties) {
        ArrayList<AirbnbListing> filteredProperties = new ArrayList<>();
        for (AirbnbListing property : properties) {
            if (property.getNeighbourhood().equals(boroughName)) {
                filteredProperties.add(property);
            }
        }
        return filteredProperties;
    }
    
    /**
     * return the listings in this borough
     * @return arraylist of properties in borough
     */
    public ArrayList<AirbnbListing> getBoroughListings (){
        return boroughListings;
    }

    /**
     * return the number of properties in borough
     * @return number of properties in borough
     */
    public int getNumberOfProperties(){
        return numberOfProperties;
    }

    /**
     * get the name of the borough
     * @return the borough name
     */
    public String getBoroughName(){
        return boroughName;
    }
    
    public void  updateProperties(int minPrice, int maxPrice, int nights){
        ArrayList<AirbnbListing> filteredProperties = new ArrayList<>();
        for (AirbnbListing property : boroughListings) {
            if (property.getPrice()>=minPrice && property.getPrice()<=maxPrice && property.getMinimumNights()<=nights) {
                filteredProperties.add(property);
            }
        }
        
        boroughListings = new ArrayList<>(listsInTheBorough(filteredProperties));
        numberOfProperties = boroughListings.size();

    }
    
}
