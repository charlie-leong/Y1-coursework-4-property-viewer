
/**
 * This class holds information about tourist destination
 *
 * @author Charlotte Leong k21062990
 * @version 1
 */
public class Destination extends Property{
    private String destinationName;
    private String description;
    

    /**
     * Constructor for destination
     */
    public Destination(String name, double lon, double lat, String description){
        setLongitude(lon);
        setLatitude(lat);
        this.destinationName = name;
        this.description = description;
    }

    /**
     * this method returns the name of the destination
     *
     * @return the name of the destination
     */
    public String getDestinationName(){
        return destinationName;
    }
    
    /**
     * this method returns a formatted description of the destination
     */
    public String getDescription(){
        return (destinationName + "--" + description);
    }
    
}
