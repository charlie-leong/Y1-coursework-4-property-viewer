/**
 * Write a description of class Property here.
 *
 * @author Charlotte Leong k21062990
 * @version 1
 */
public class Property{
   
    //The location on a map where the property is situated.
    private double latitude;
    private double longitude;

    /**
     * Constructor for objects of class Property
     */
    public Property(){
        
    }

    /**
     * return the longitude coordinate of the property
     * @return the property's longitude
     */
    public double getLongitude(){
        return longitude;
    }
    
    /**
     * return the latitude coordinate of the property
     * @return the property's latitude
     */
    public double getLatitude(){
        return latitude;
    }
    
    public void setLongitude(double lon){
        this.longitude = lon;
    }
    
    public void setLatitude(double lat){
        this.latitude = lat;
    }
    
    /**
     * calculate the distance between two properties using the longitude and latitude coordinates
     * @start starting property instance
     * @end ending property instance
     * @return the distance between the two properties in km
     */
    public double calculateDistance(Property end){
        int radiansToKm = 6371;
        
        double latDistance = degreesToRadians(end.getLatitude() - latitude);
        double lonDistance = degreesToRadians(end.getLongitude() - longitude);
        
        double startLat = degreesToRadians(latitude);
        double endLat = degreesToRadians(end.getLatitude());
        
        double working = Math.sin(latDistance/2) * Math.sin(latDistance/2) +
          Math.sin(lonDistance/2) * Math.sin(lonDistance/2) * Math.cos(startLat) * Math.cos(endLat); 
        
        return radiansToKm * (2 * Math.atan2(Math.sqrt(working), Math.sqrt(1-working)));
           
    }
    
    /**
     * converts a value from degrees to radian 
     * @degrees the value in degrees to be converted
     * @return the calculated value in radians
     */
    public double degreesToRadians(double degrees){
        return degrees * Math.PI/180;
    }
}
