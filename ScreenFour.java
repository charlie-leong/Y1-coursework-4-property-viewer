import java.util.ArrayList;
import java.net.URI;
import javafx.stage.Stage;
import java.awt.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.JFrame;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.URL;
import java.io.FileOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Box;

/**
 * This class retreives information about tourist destinations in london and allows for distance comparisons
 *
 * @author Charlotte Leong k21062990
 * @version 1
 */
public class ScreenFour{
    private Stage stage;
    private VBox titles;
    private VBox information;
    
    private Property property;
    
    private DataLoader data = new DataLoader();
    private ArrayList<Destination> destinations;

    /**
     * Constructor for the map API objects
     */
    public ScreenFour(Property property){
        destinations = data.loadDestinationData();
        this.property = property;
    }
    
    /**
     * this method iterates through the list of destinations to find the sloest property
     * @property the property the program is finding the closest destination to
     */
    public Destination findNearestDestination(AirbnbListing property){
        double smallestDistance = property.calculateDistance(destinations.get(0));
        Destination closestDestination = destinations.get(0);
        for(Destination destination: destinations){
            double distance = property.calculateDistance(destination);
            if(distance < smallestDistance){
                smallestDistance = distance;
                closestDestination = destination;
            }
        }
        return closestDestination;
    }
    
    /**
     * This method opens the system's default internet browser
     * The Google maps page shows the current property's location on the map based on the longitude
     * and latitude properties assigned to the property.
     */
    public void viewMaps(Property property) throws Exception{
       URI uri = new URI("https://www.google.com/maps/place/" + property.getLatitude() 
           + "," + property.getLongitude());
       java.awt.Desktop.getDesktop().browse(uri); 
    }
    
    /**
     * this method creates the screen that all information is displayed on
     * it finds and provides information on the nearest destination to an airbnb listing 
     * @param listing the airbnb listing the destination information is based on
     */
    public void viewMap(AirbnbListing listing){
        JFrame frame = new JFrame("map display");
        Destination nearest = findNearestDestination(listing);
        
        Box box = Box.createVerticalBox();
        box.add(new JLabel(nearest.getDescription()));
        box.add(new JLabel("distance: "+ String.valueOf(
            Math.round(listing.calculateDistance(nearest)*100.0)/100.0)+" km"));
    
        ImageIcon imageIcon = getMapImage(listing, nearest);
        
        // create a GUI component that loads the copied image        
        box.add(new JLabel(imageIcon));
        frame.add(box);
        
        // show the GUI window
        frame.setVisible(true);
        frame.pack();
    }
    
     /**
     * this method uses an API to find and download a map showing the route between a listing and a destination
     * if no route can be found, a straight line will be drawn between the properties
     * @param listing an airbnb listing
     * @param nearest the nearest destination to the listing
     * @return an image of the map between the two properties
     */
    public ImageIcon getMapImage(AirbnbListing listing, Destination nearest){
        try {
            String latitude = String.valueOf(nearest.getLatitude());
            String longitude = String.valueOf(nearest.getLongitude());

            String destinationFile = "map.jpg"; //where the copied image will be stored
            
            String token = "fcVqjeke4lwmzfF6snCgnXbUwrzdqnZU3fjOdtqycG6XuErXYYk4UDSPUpCyEGos";
            String imageUrl = "https://api.jawg.io/static?zoom=12&center="
                    +latitude+ "," +longitude+
                    "&size=400x300&scale=3&layer=jawg-sunny&format=png&access-token=" 
                    +token;
                    
            String altURL = "https://api.jawg.io/static?path=color:1392FC,"+
            "weight:3%7C"+latitude+","+longitude+"%7C"+listing.getLatitude()+","+listing.getLongitude()+"&"+
            "size=600x400&layer=jawg-streets&format=png&access-token="+token;
            
            URL url = new URL(altURL);
            InputStream input = url.openStream();
            OutputStream output = new FileOutputStream(destinationFile);

            //save an instance of the map
            byte[] b = new byte[2048];
            int length;
            while ((length = input.read(b)) != -1) {
                output.write(b, 0, length);
            }

            input.close();
            output.close();
            
        } 
        catch (IOException e) {
            System.exit(1);
        }
        
        return new ImageIcon((new ImageIcon("map.jpg"))
                .getImage().getScaledInstance(504, 480,
                        java.awt.Image.SCALE_SMOOTH));
    }
}

