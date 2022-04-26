import javafx.scene.input.KeyCombination;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.geometry.Pos;

import javafx.scene.layout.StackPane;

import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;

/**
 * Displays the properties that have been filtered based on the number of nights and the price range from the user input.
 * If the "view more" button has been clicked then the description of the property is displayed.
 *
 * @author Sakura Mikano and Charlotte Leong
 * @version 30/03/2022
 */
public class DisplayProperties extends CurrentPropertyData {
    private Stage stage;

    private String nameOfBorough;
    private String nameOfHost;
    private String priceOfProperty;
    private String numOfReviews;
    private String minNights;

    private Label nameOfHostLabel;
    private Label priceOfPropertyLabel;
    private Label numOfReviewsLabel;
    private Label minNightsLabel;

    private ArrayList<AirbnbListing> propertyList;
    
    //Maps button to index of arrayList of buttons
    private ArrayList<Button> viewMore;
    private ArrayList<Button> mapArray;
    private HashMap<Integer, AirbnbListing> findButton;
    private HashMap<Integer, AirbnbListing> findMapButton;
    
    private AirbnbListing currentProperty;
    
    private double xCoordinates = 0;
    private double yCoordinates = 0;

    private VBox root;
    private ScrollPane data;
    private BorderPane contentPane;
    //link to the borough class
    /**
     * Constructor for objects of class GUIDisplayProperties.
     * Initialises all the labels, filtered property arraylist, button hashMaps and arrayList.
     */
    public DisplayProperties(String currentBoroughName, ArrayList<AirbnbListing> passedPropertyList)
    {
        nameOfHostLabel = new Label("");
        priceOfPropertyLabel = new Label("");
        numOfReviewsLabel = new Label("");
        minNightsLabel = new Label("");

        nameOfBorough = currentBoroughName;
        stage = new Stage();

        propertyList = new ArrayList<>(passedPropertyList);
        findButton = new HashMap<>();
        viewMore = new ArrayList<>();
        findMapButton = new HashMap<>();
         mapArray = new ArrayList<>();
    }
    
    protected void setCurrentProperty(AirbnbListing currentProperty){
        this.currentProperty = currentProperty;
    }
    
    protected AirbnbListing getCurrentProperty() {
        return currentProperty;
    }

    /**
     * When exit button is clicked, the window will close.
     */
    private void exitButtonAction(ActionEvent e) {
        stage.close();
    }

    /**
     * Sets the currentProperty of the button being called using the hashmap. Then calls the class DisplayDescription which would give further details.
     * @param key This is the key of the hashMap and index of the button arrayList.
     */
    private void viewMoreAction(int key) {
        setCurrentProperty(findButton.get(key));
        DisplayDescription displayDescription = new DisplayDescription(getCurrentProperty());
        displayDescription.start();
    }
    
    /**
     * Links to the appropriate hashmap to the ArrayList of button's index and the currentProperty.
     * @param index Key of the hashmap and index of the arrayList.
     * @param currentProperty The currentProperty being instantiated in the method displayPanel.
     */
    private void linkButtonToProperty(int index, AirbnbListing currentProperty){
            findButton.put(index, currentProperty);
            findMapButton.put(index, currentProperty);
    }
    
    /**
     * This method is used when the window is dragged, creating the new co-ordinates.
     */
    private void MouseDragged(MouseEvent e) {
        stage.setX(e.getScreenX() + xCoordinates);
        stage.setY(e.getScreenY() + yCoordinates);
    }
    
    /**
     * Keeps the current coordinates of the stage within the window.
     */
    private void MousePressed(MouseEvent e) {
        xCoordinates = stage.getX() - e.getScreenX();
        yCoordinates = stage.getY() - e.getScreenY();
    }

    /**
     * Sorts the ArrayList propertyList by the number of reviews.
     * Ordering is from highest to lowest.
     */
    private void sortByNumViewsAction(ActionEvent e) {
        propertyList.sort(Comparator.comparing(AirbnbListing::getNumberOfReviews).reversed());
        refreshData();
    }

    /**
     * Sorts the ArrayList: propertyList by the price.
     * Ordering is from lowest to highest.
     */
    private void sortByPriceAction(ActionEvent e) {
        propertyList.sort(Comparator.comparing(AirbnbListing::getPrice));
        refreshData();
    }

    /**
     * Sorts the ArrayList: propertyList based on the host's name.
     * Ordering is alphabetical. 
     */
    private void sortByAlphabeticalAction(ActionEvent e){
        propertyList.sort(Comparator.comparing(AirbnbListing::getHost_name));
        refreshData();
    }

    /**
     * Clears the data and then reinstantiates it with the new sorted arrayList: propertyList.
     */
    private void refreshData(){
        data.setContent(null);
        contentPane.setCenter(null);
        data = makeScrollPane(root);
        contentPane.setCenter(data);
    }

    /**
     * Calls the appropriate methods to instantiate the scene. Also creates an exit button.
     */
    public void start()
    {
        stage.initStyle(StageStyle.UNDECORATED);
 
        root = new VBox();
        root.setPrefSize(360,360);
        root.setMaxSize(500,500);

        makeMenuBar(root);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(this::exitButtonAction);
        HBox toolBar = new HBox(exitButton);
        toolBar.setId("exit");
        Pane m = new VBox(10);

        data = makeScrollPane(root);
        data.setId("information");
        contentPane = new BorderPane(data, titleOfBorough(root), null, toolBar, null);
        root.getChildren().addAll(contentPane);

        Scene scene = new Scene(root);

        scene.getStylesheets().add("GUIDisplayProperties.css");
        stage.setScene(scene);

        // Show the Stage (window)
        stage.show();
    }

    /**
     * Assigns the data to the variables of the currentProperty.
     */
    private void getInformation() {
        nameOfHost = getCurrentProperty().getHost_name();
        priceOfProperty = "" + getCurrentProperty().getPrice();
        numOfReviews = "" + getCurrentProperty().getNumberOfReviews();
        minNights = "" + getCurrentProperty().getMinimumNights();
    }

    /**
     * Created the main scroll pane for the data to be stored in.
     * @param parent The root.
     * @return scrollPane All the data in a scroll pane.
     */
    private ScrollPane makeScrollPane(Pane parent) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(multiplePanel(parent));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        return scrollPane;
    }

    /**
     * Creating the title of the window and returning the stackPane.
     * @param parent The root.
     * @return title The name of the borough.
     */
    private Pane titleOfBorough(Pane parent) {
        Label boroughNameLabel = new Label("");

        boroughNameLabel.setText(nameOfBorough);
        StackPane title = new StackPane(boroughNameLabel);

        title.setId("boroughTitle");
        return title;
    }

    /**
     * Creates a single text of the properties in the borough
     * @param index Passes the index of the arrayList which is the key of the hashmaps for the buttons
     * @return main BorderPane of one property in the borough
     */
    private Pane displayPanel(int index) {
        //data of the property
        Label nameOfHostLabel = new Label(getNameOfHostData());
        Label priceOfPropertyLabel = new Label(getPriceOfPropertyData());
        Label numOfReviewsLabel = new Label(getNumOfReviewsData());
        Label minNightsLabel = new Label(getMinNightsData());
        
        //constants    
        Label hostNameText2 = new Label(getHostNameText());
        Label pricePropertyText2 = new Label(getPricePropertyText());
        Label numReviewsText2 = new Label(getNumReviewsText());
        Label minNightsText2 = new Label(getMinNightsText());
        
        //Puts the constants and data into a VBox
        VBox data = new VBox(hostNameText2, pricePropertyText2, numReviewsText2, minNightsText2);
        data.setId("data");
        VBox setLabels = new VBox(nameOfHostLabel, priceOfPropertyLabel, numOfReviewsLabel, minNightsLabel);
        setLabels.setId("dataLabels");
        
        //Creates the buttons
        Button openMap = new Button("Find nearest destination");
        Button viewMore2= new Button("View more"); 
        
        mapArray.add(index, openMap);//Adds each button the the arrayList
        viewMore.add(index,viewMore2);
        
        linkButtonToProperty(index, currentProperty); //Calls method to link the index to the arrayList and current property
        mapArray.get(index).setOnAction(action -> {openMapAction(index);}); //Allows each button to call the action method.
        viewMore.get(index).setOnAction(action -> {viewMoreAction(index);});
        
        openMap.setId("destinationButton");
        
        HBox buttonPanel = new HBox(viewMore.get(index), mapArray.get(index));

        Pane main = new BorderPane(setLabels, null, null, buttonPanel, data);
        main.setId("mainData");
        return main;
    }
    
    /**
     * Creates an instance of the fourth panel.
     * @param index The key for the hashMap to find the property
     */
    private void openMapAction(int index){
        setCurrentProperty(findMapButton.get(index));
        ScreenFour test = new ScreenFour(currentProperty);
        //Property nearestDestination = test.findNearestDestination(currentProperty);
        try{
           test.viewMap(currentProperty);
        }
        catch (Exception ev)
        {
            ev.printStackTrace();
        }
    }
    
    /**
     * This groups all of the VBox of the data in the borough into one VBox.
     * @return multiplePanel All of the properties in the borough that fit the criteria 
     */
    private Pane multiplePanel(Pane parent) {
        Pane multiplePanel = new VBox(10);
        
        int count = 0; //index of the arrayList for the buttons
        for (AirbnbListing c: propertyList) {
            setCurrentProperty(c);
            getInformation();
            multiplePanel.getChildren().add(displayPanel(count));
           count ++;
        }
        return multiplePanel;
    }

    /**
     * This creates the menu bar.
     * Creates the different sorting methods and keyboard shortcuts. Also allows the user to drag the window around using the
     * menubar. 
     */
    private void makeMenuBar(Pane parent)
    {
        MenuBar menubar = new MenuBar();
        parent.getChildren().add(menubar);

        Menu sorting = new Menu("Sort By");

        MenuItem sortByNumViews = new MenuItem("Number Of Views");
        sortByNumViews.setOnAction(this::sortByNumViewsAction);
        sortByNumViews.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
        
        MenuItem sortByPrice = new MenuItem("Price");
        sortByPrice.setOnAction(this::sortByPriceAction);
        sortByPrice.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN));

        MenuItem sortByAlphabetically = new MenuItem("Host Name");
        sortByAlphabetically.setOnAction(this::sortByAlphabeticalAction);
        sortByNumViews.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));

        sorting.getItems().addAll(sortByNumViews, sortByPrice, sortByAlphabetically);

        menubar.getMenus().addAll(sorting);

        //Allows the menubar to be dragged
        menubar.setOnMousePressed(this::MousePressed); 
        menubar.setOnMouseDragged(this::MouseDragged);
   
    }

}
