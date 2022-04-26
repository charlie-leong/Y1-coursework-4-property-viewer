import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javafx.geometry.Pos;
import java.util.Random;

import javax.swing.Timer;

/**
 * The StatPanelGUI class is responsible for the creations and the displaying 
 * the stat panel. This panel has 4 stat boxes which each hold and disply a 
 * stat. These boxes are borderpanes with buttons which allow the user to 
 * scroll through the different stats. 
 *
 * @author Dominic Child, Charlotte Leong
 * @version 30.3.22
 */
public class StatPanelGUI {
    public Stage stage;
    
    //Buttons
    private Button buttonRight1;
    private Button buttonLeft1;
    private Button buttonRight2;
    private Button buttonLeft2;
    private Button buttonRight3;
    private Button buttonLeft3;
    private Button buttonRight4;
    private Button buttonLeft4;
    
    //BorderPanes
    private BorderPane statBoxOne;
    private BorderPane statBoxTwo;
    private BorderPane statBoxThree;
    private BorderPane statBoxFour;
    
    //Information class
    private Information info;
    
    //Arrays for holding the stats and the names
    private ArrayList<String> statListCurrent;
    private ArrayList<String> statListNext;
    private ArrayList<String> statNameCurrent;
    private ArrayList<String> statNameNext;
    
    //instance variables
    private int maxPrice;
    private int minPrice;
    private int minNumberOfNights;
    
    /**
     * constructs an instance of the statisitcs panel
     * instantiates all initial valeus
     */
    public StatPanelGUI(Information info)
    {
        this.info = info;
        
        statListCurrent = new ArrayList<>();
        statListNext = new ArrayList<>();
        statNameCurrent = new ArrayList<>();
        statNameNext = new ArrayList<>();
        minPrice = info.getMinimumCost();
        maxPrice = info.getMaximumCost();
        minNumberOfNights = info.getMinimumNights();
        setStatArrays();
        setNameArrays();
    }
    
    /**
    * This method adds each of the stats to one of the arrays.
    * The 4 origional stats are added to the current array, meaning
    * they will be displayed when the stat panel is first shown, and 
    * the additional stats are added to the next array.
    */
    private void setStatArrays(){
        
        statListCurrent.add(info.averageReviews() + "");
        statListCurrent.add(info.totalNumberOfListings()+"");
        statListCurrent.add(info.totalNumberOfEntireHomes()+"");
        statListCurrent.add(info.mostExpensiveBorough()+"");
        statListNext.add(info.totalListingOverPriceRange(minPrice, maxPrice)+"");
        statListNext.add(info.totalListingOverNumOfNights(minNumberOfNights)+"");
        statListNext.add(info.mostAvailableBorough()+"");
        statListNext.add(info.averageDestinations()+"");

    }
    
    /**
    * This method adds the corresponding names of each stat to the right array,
    * that being either the current or the next array.
    */
    private void setNameArrays(){
        
        statNameCurrent.add("Average number of reviews per Property");
        statNameCurrent.add("Number of available properties");
        statNameCurrent.add("Number of entire homes and appartments");
        statNameCurrent.add("Most Expensive Borough");
        statNameNext.add("Number of listings over price range");
        statNameNext.add("Number of listings over minimum number of nights");
        statNameNext.add("Most Available borough");
        statNameNext.add("Average number of destinations per borough");
    }
    
    /**
     * Method which is called when one of the forward buttons in the statbox is pressed.
     * It takes the statbox number as a parameter and moves the current stat in that 
     * statbox to the back of the list of the next stats. Then moves the next in-line 
     * stat into the statbox. Also used for the names of stats.
     */
    private void ArrayShiftingForward(int statBoxNum, ArrayList<String> current, ArrayList<String> next)
    {
        String temp1 = current.get(statBoxNum - 1);
        current.remove(statBoxNum - 1);
        next.add(temp1);
        String temp2 = next.get(0);
        next.remove(0);
        current.add(statBoxNum - 1, temp2);
    }
    
    /**
    * This method shifts the next stat and stat name into the chosen 
    * stat box and shifts the current ones out.
    */
    
    private void buttonForward(int statBoxNum){
        ArrayShiftingForward(statBoxNum, statListCurrent, statListNext);
        ArrayShiftingForward(statBoxNum, statNameCurrent, statNameNext);
        updateBoxes();
    }
    
    
    /**
     * Method which is called when one of the backwards buttons in the statboxes is
     * pressed. It takes the statbox number as a parameter and moves the current stat 
     * in the statbox to the front of the next in-line list, and takes the last stat 
     * from that list and adds it to the current statbox. Also used for the names of stats 
     */
    private void ArrayShiftingBackward(int statBoxNum, ArrayList<String> current, ArrayList<String> next)
    {
        String temp1 = current.get(statBoxNum - 1);
        current.remove(statBoxNum - 1);
        next.add(0, temp1);
        String temp2 = next.get(next.size() - 1);
        next.remove(next.size() - 1);
        current.add(statBoxNum - 1, temp2);
    }
    
    /**
    * This method moves the current stat and stat name out of the chosen 
    * stat box, and moves the previous ones back in.
    */
    
    private void buttonBackward(int statBoxNum){
        ArrayShiftingBackward(statBoxNum, statListCurrent, statListNext);
        ArrayShiftingBackward(statBoxNum, statNameCurrent, statNameNext);
        updateBoxes();
    }
    
    /**
     * A method which updates all of the labels so the interface will display the
     * correct stats. 
     */
    private void updateBoxes()
    {
        updateBox(statBoxOne, 1);
        updateBox(statBoxTwo, 2);
        updateBox(statBoxThree, 3);
        updateBox(statBoxFour, 4);
        
    }
    
    /**
    * retuns the statPanel as a pane so the welcomeGUI can display it 
    * constructs the stats panel
    */

        public Pane start(){

         
        int delay = 20000; // in mililliseconds
        ActionListener generatePopUp = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
              GenerateAds ad = new GenerateAds();
              ad.showAd();
          }
        };
        new javax.swing.Timer(delay, generatePopUp).start();
                
        HBox root = new HBox();
        root.setSpacing(100);
        
        statBoxOne = createStatBox(1, buttonRight1, buttonLeft1);
        statBoxTwo = createStatBox(2, buttonRight2, buttonLeft2);
        statBoxThree = createStatBox(3, buttonRight3, buttonLeft3);
        statBoxFour = createStatBox(4, buttonRight4, buttonRight4);
        
        root.getChildren().addAll(statBoxOne, statBoxTwo, statBoxThree, statBoxFour);
        
    
        return root;
 
    }
    
    /**
    * This method creates a statbox. It gives each stat box a unique 
    * id and forward and backward buttons. Also creates the labels 
    * to display the stats and the stat names.
    */
    
    public BorderPane createStatBox(int id, Button buttonRight, Button buttonLeft){
        
        buttonRight = new Button(">");
        buttonRight.setOnAction(action -> { buttonForward(id);});
        buttonRight.setStyle("-fx-background-color: #0096FF; -fx-text-fill: white");
        
        buttonLeft = new Button("<");
        buttonLeft.setOnAction(action -> { buttonBackward(id);});
        buttonLeft.setStyle("-fx-background-color: #0096FF; -fx-text-fill: white");
        
        Label statBoxInfo = new Label(statListCurrent.get(id-1));
        statBoxInfo.setMaxWidth(Double.MAX_VALUE);
        statBoxInfo.setMinWidth(250);
        statBoxInfo.setAlignment(Pos.CENTER);
        
        Label label = new Label(statNameCurrent.get(id-1));
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMinWidth(250);
        label.setAlignment(Pos.CENTER);
        
        BorderPane box = new BorderPane(statBoxInfo, label, buttonRight, null, buttonLeft);
        return box;
    }
    
    /**
    * This method is called after each button press. It updates 
    * the labels on the stat boxes with the new stat and 
    * current name.
    */
    public void updateBox(BorderPane statBox, int id){
        
        Label stat = new Label(statListCurrent.get(id-1));
        stat.setMaxWidth(Double.MAX_VALUE);
        stat.setMinWidth(250);
        stat.setAlignment(Pos.CENTER);
        statBox.setCenter(stat);
        
        Label name = new Label(statNameCurrent.get(id-1));
        name.setMaxWidth(Double.MAX_VALUE);
        name.setMinWidth(250);
        name.setAlignment(Pos.CENTER);
        statBox.setTop(name);

    }


    
}


