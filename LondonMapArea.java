import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import java.util.*;
import java.util.Iterator;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.embed.swing.SwingNode;
import javafx.application.Application;

/**
 * Creates an interactive map based on a map image
 * Each map region is colouur-coded and selectable
 *
 * @author Charlotte Leong and Sakura Mikano
 * @version 30.3.22
 */
public class LondonMapArea{
    private JFrame frame;
    private JComponent ui = null;

    private JLabel output = new JLabel();
    private static int SIZE;
    private int imageWidth;
    private int imageHeight;
    private BufferedImage image;
    private Area area;
    private ArrayList<Shape> boroughList;
    private String[] abbreviationList;
    private HashMap<Integer, Color> colourLegend;

    private Information info;
    
    private int quartileOne;
    private int quartileTwo;
    private int quartileThree;
    private int quartileFour;

    private String currentBoroughName;
    private ArrayList<AirbnbListing> propertyList;

    /**
     * construct an instance of the London borough map
     * calls on methods to initialise legend hashmaps and list of boroughs
     * 
     * @param info updated and filtered information about the boroughs
     */
    public LondonMapArea(Information info) {
        this.info = info;
        setQuartileValues();
        colourLegend = new HashMap<>();
        initialiseLegend();   
        try {
            initUI();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //this order of this list is based on the order of that the boroughs are identified and would need to be updated given a different map
        abbreviationList = new String[]{"ENFI", "HRGY", "WALT", "BARN", "HRRW", "REDB", "BREN", 
            "HACK", "ISLI", "CAMD", "BARK", "CITY","NEWH", "HAVE", "EALI", "TOWH", "WSTM", "KENS", 
            "HAMM", "HILL", null , "GWCH", "STHW", "HOUN", "WAND", "LEWS", "BEXL", "LAMB", "RICH",
            "MERT", "KING", "SUTT", "BROM", "CROY" };
    }
    
    /**
     * this method sets the quartile values for the colour legend
     * the highest number of properties is found, quartered, and rounded
     */
    public void setQuartileValues(){
        int total = info.largestNumOfProperties();
        //uses division to round to nearest hundred (truncates decimal portion of the quotient)
        quartileOne = ((total/4 + 99) / 100 ) * 100;
        quartileTwo = ((total/2 + 99) / 100 ) * 100;
        quartileThree = ((3*(total/4) + 99) / 100 ) * 100;
        quartileFour = ((total + 99)/ 100 ) * 100;
    }

    /**
     * initialises the values the hash map to show what colour corresponds to what quartile
     * -1 represents all values higher than the fourth quartile (from rounding)
     */
    public void initialiseLegend(){
        colourLegend.put(quartileOne, new Color(154, 160, 168));
        colourLegend.put(quartileTwo, new Color(167, 196, 181));
        colourLegend.put(quartileThree, new Color(169, 216, 184));
        colourLegend.put(quartileFour, new Color(190, 255, 199));
        colourLegend.put(-1, new Color(114, 112, 91));
    }

    /**
     * initialise the main map user interface
     * creates component that the loaded map image is set onto
     */
    public final JComponent initUI() throws Exception {
        if (ui !=null) {
            return ui;
        }

        try{
            image = ImageIO.read(new File("smaller-borough-map.png"));

            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
        }catch(IOException e){
            e.printStackTrace();
        }

        area = getOutline(Color.WHITE, image, 5);

        boroughList = separateMapIntoRegions(area);
        ui = new JPanel(new BorderLayout(4, 4));
        ui.setBorder(new EmptyBorder(4, 4, 4, 4));
        output.addMouseMotionListener(new MousePositionListener()); //adds the position Listener
        output.addMouseListener(new MousePositionListener()); //adds the mouse clicking events
 
        ui.add(output, BorderLayout.CENTER);

        refresh();
        return ui;
    }
   

    /**
     * this method constructs a general outline of the map, separating it into regions based on the lines in the image
     * @param backgroundColour the base colour of the image (excluding map lines)
     * @param image the image to be separated
     * @param tolerance how far the pixel's colour can be from the base of the colour
     * @return the area created by the general path 
     */
    public Area getOutline(Color backgroundColour, BufferedImage image, int tolerance) {
        // construct the GeneralPath
        GeneralPath gp = new GeneralPath();

        boolean cont = false;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (isInColourRange(new Color(image.getRGB(x, y)), backgroundColour, tolerance)) {
                    if(cont) {
                        gp.lineTo(x, y);
                        gp.lineTo(x, y + 1);
                        gp.lineTo(x + 1, y + 1);
                        gp.lineTo(x + 1, y);
                        gp.lineTo(x, y);
                    } else {
                        gp.moveTo(x, y);
                    }
                    cont = true;
                } else {
                    cont = false;
                }
            }
            cont = false;
        }
        gp.closePath();
        //construct new area from the closed off general path
        return new Area(gp);
    }

    /**
     * this method separates the map into individual delectable regions by tracing the lines in the image
     * @return an arraylist of all the shapes created
     */
    public static ArrayList<Shape> separateMapIntoRegions(Shape shape) {
        ArrayList<Shape> regions = new ArrayList<>();

        PathIterator iterator = shape.getPathIterator(null);
        GeneralPath path = new GeneralPath();

        while (!iterator.isDone()) {
            double[] coordinates = new double[6];

            int pathSegmentType = iterator.currentSegment(coordinates);
            int windingRule = iterator.getWindingRule();

            path.setWindingRule(windingRule);

            if (pathSegmentType == PathIterator.SEG_MOVETO) { //start location for new path
                path = new GeneralPath();
                path.setWindingRule(windingRule);
                path.moveTo(coordinates[0], coordinates[1]);
            }
            else if (pathSegmentType == PathIterator.SEG_LINETO) { //continue path from previous line
                path.lineTo(coordinates[0], coordinates[1]);
            }

            else if (pathSegmentType == PathIterator.SEG_CLOSE) { //close off existing line
                path.closePath();
                regions.add(new Area(path));
            }
            else {
                System.err.println("oh no, " + pathSegmentType);
            }
            iterator.next();
        }

        return regions;
    }

    class MousePositionListener implements MouseListener, MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            // do nothing
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            refresh();
        }        

        /**
         * When the mouse clicks on the borough, it then displays the information of the properties which are part of filtered properties.
         * @param e Event when the mouse is clicked.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            Platform.runLater(() -> { //This prevent the AWT-EventQueue-0 error
                    DisplayProperties displayProperties = new DisplayProperties(getCurrentBoroughName(), getCurrentBoroughProperties());
                    displayProperties.start();
                });
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            refresh();
        }

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}
    }

    /**
     * Create a method which returns the borough name that was clicked.
     */
    public String getCurrentBoroughName() {
        return currentBoroughName;
    }

    /**
     * Create a method which returns the list of properties in the borough that was clicked.
     */
    public ArrayList<AirbnbListing> getCurrentBoroughProperties(){
        return propertyList;
    }

    /**
     * Create a method which sets the current borough that was clicked
     * @param int index of the borough
     */
    private void setCurrentBorough(int i) {
        currentBoroughName = info.getBorough(abbreviationList[i]).getBoroughName();
        propertyList = info.getBorough(abbreviationList[i]).getBoroughListings();
    }

    /**
     * this method checks whether a pixel is within the set colour range to create a binary image of purely "true" or "false" pixels
     * @param current the current pixel being tested
     * @param background the general background colour, for comparison
     * @param tolerance the amount of leeway a pixel is given to stray from the base values
     * @return true or false of whether the pixel is considered within the appropriate colour range
     */
    public static boolean isInColourRange(Color current, Color background, int tolerance) {
        //current pixel's colour value breakdown
        int rCurrent = current.getRed();
        int gCurrent = current.getGreen();
        int bCurrent = current.getBlue();

        //intended background colour's value breakdown
        int rTarget = background.getRed();
        int gTarget = background.getGreen();
        int bTarget = background.getBlue();
        
        //checking whether the current pixel is close enough to be considered the background colour
        return ((rTarget - tolerance <= rCurrent) && (rCurrent <= rTarget + tolerance)
            && (gTarget - tolerance <= gCurrent) && (gCurrent <= gTarget + tolerance)
            && (bTarget - tolerance <= bCurrent) && (bCurrent <= bTarget+tolerance));
    }

    /**
     * this method returns a map with updated colouring depending on the user's mouse positions
     * @return Image with updated colour preferences
     */
    private BufferedImage getImage() {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D mapImage = image.createGraphics();
        mapImage.drawImage(this.image, 0, 0, output);
        mapImage.setColor(Color.WHITE);
        mapImage.fill(area);
        mapImage.draw(area);
        try {
            Point point = MouseInfo.getPointerInfo().getLocation();
            Point currentPoint = output.getLocationOnScreen();
            int x = point.x - currentPoint.x;
            int y = point.y - currentPoint.y;
            Point pointOnImage = new Point(x, y);

            for(int s = 0; s< boroughList.size(); s++ ){
                Shape shape = boroughList.get(s);
                if (shape.contains(pointOnImage)) {
                    info.getBorough(abbreviationList[s]).getBoroughName();
                    Color regionColour = getQuantityColour(info.getBorough(abbreviationList[s]).getNumberOfProperties());
                    mapImage.setColor(regionColour);
                    mapImage.fill(shape);
                    setCurrentBorough(s); //Sets the current Borough

                    break;
                }
            }
        } catch (Exception e) {
        }
        mapImage.dispose();

        return image;
    }

    /**
     * this method returns a colour based on the number of properties
     * @param properties the number of properties in a given borough
     * @return the corresponding colour
     */
    public Color getQuantityColour(int properties){ 
        //might want to change this to a loop
        if(properties < quartileOne){
            return colourLegend.get(quartileOne);
        }
        else if(properties < quartileTwo){
            return colourLegend.get(quartileTwo);
        }
        else if(properties <quartileThree){
            return colourLegend.get(quartileThree);
        }
        else if(properties < quartileFour){
            return colourLegend.get(quartileFour);
        }
        else{
            return colourLegend.get(-1);
        }
    }

    /**
     * returns the UI
     * @return the UI component
     */
    public JComponent getUI() {
        return ui;
    }

    /**
     * updates the screen
     */
    private void refresh() {
        output.setIcon(new ImageIcon(getImage()));
    }

    public void backAction(){
        frame.dispose();  
    }
}
