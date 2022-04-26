import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import java.util.ArrayList;

import javafx.scene.layout.VBox;
import java.io.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.input.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.event.*;
import javafx.stage.StageStyle;
/**
 * Displays the additional information of each property. Including the name of the property, availibility and etc.
 *
 * @author Sakura Mikano
 * @version 30/03/2022
 */
public class DisplayDescription extends CurrentPropertyData {
 private Stage secondaryStage;
 private AirbnbListing currentProperty;
  
    private double xCoordinates = 0;
    private double yCoordinates = 0;
    /**
     * Instantiates the currentProperty and the stage.
     * @param currentProperty Passed from the displayProperty to know which property needs to be displayed.
     */
    public DisplayDescription(AirbnbListing currentProperty)
    {
       this.currentProperty = currentProperty;
       secondaryStage = new Stage();
    }
    
    /**
     * Once exitButton is clicked, the stage will close.
     */
    private void exitButtonAction(ActionEvent e) {
        secondaryStage.close();
    }

    /**
     * Decorates the new window with the appropriate information displayed.
     * Also creating an exit button which would close the window. 
     */
    
    public void start()
    {
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        
        VBox root2 = new VBox();
        root2.setId("root2");
        
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(this::exitButtonAction);
        HBox toolBar = new HBox(exitButton);
        toolBar.setId("exit2");
        
        Label nameOfProperty = new Label(currentProperty.getName());
        nameOfProperty.setId("nameProperty");
        
        Pane contentPane2 = new BorderPane(displayPanel(), nameOfProperty, null, toolBar, null);
        root2.getChildren().addAll(contentPane2);
        root2.setId("contentPane2");

        root2.setOnMousePressed(this::MousePressed); 
        root2.setOnMouseDragged(this::MouseDragged);
        
        Scene scene = new Scene(root2);
        scene.getStylesheets().add("GUIDisplayProperties.css");

        secondaryStage.setScene(scene);

        // Show the Stage (window)
        secondaryStage.show();
    }
   
    protected AirbnbListing getCurrentProperty()
    {
        return currentProperty;
    }
    
    /**
     * Sets each label as the constant and the data of the current property.
     * Then displays the data using two VBoxes and combining them with a border pane.
     * @return main The border pane of the data and constants.
     */
    private Pane displayPanel(){
        //Data of that specific property
         Label nameOfHostLabel = new Label(getNameOfHostData());
        Label priceOfPropertyLabel = new Label(getPriceOfPropertyData());
        Label numOfReviewsLabel = new Label(getNumOfReviewsData());
        Label minNightsLabel = new Label(getMinNightsData());
         Label roomTypeDataLabel = new Label(getRoomTypeData());
        Label lastReviewDataLabel = new Label(getLastReviewData());
        Label reviewsPerMonthDataLabel = new Label(getReviewsPerMonthData());
        Label calculatedHostListingsCountDataLabel = new Label(getCalculatedHostListingsCountData());
         Label availbility365DataLabel = new Label(getAvailbility365Data());
         
        //constants 
        Label hostNameText = new Label(getHostNameText());
        Label pricePropertyText = new Label(getPricePropertyText());
        Label numReviewsText = new Label(getNumReviewsText());
        Label minNightsText = new Label(getMinNightsText());
        Label roomTypeText = new Label(getRoomTypeText());
        Label lastReviewText = new Label(getLastReviewText());
        Label reviewsPerMonthText = new Label(getReviewsPerMonthText());
        Label calculatedHostListingsCountText = new Label(getCalculatedHostListingText());
        Label Availbility365Text = new Label( getAvailbility365Data());

        VBox data = new VBox(hostNameText, pricePropertyText, numReviewsText, minNightsText, roomTypeText, lastReviewText, reviewsPerMonthText, calculatedHostListingsCountText, Availbility365Text);
        data.setId("data");
        VBox setLabels = new VBox(nameOfHostLabel, priceOfPropertyLabel, numOfReviewsLabel, minNightsLabel, roomTypeDataLabel, lastReviewDataLabel, reviewsPerMonthDataLabel, calculatedHostListingsCountDataLabel, availbility365DataLabel);
        setLabels.setId("dataLabels");
        
        Pane main = new BorderPane(setLabels, null, null, null, data);
        return main;
    }
    
    /**
     * This method is used when the window is dragged, creating the new co-ordinates.
     */
    private void MouseDragged(MouseEvent e) {
        secondaryStage.setX(e.getScreenX() + xCoordinates);
        secondaryStage.setY(e.getScreenY() + yCoordinates);
    }
    
    /**
     * Keeps the current coordinates of the stage within the window.
     */
    private void MousePressed(MouseEvent e) {
        xCoordinates = secondaryStage.getX() - e.getScreenX();
        yCoordinates = secondaryStage.getY() - e.getScreenY();
    }
    
}
