import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import java.util.Set;
import javafx.event.*;

import javax.swing.JFrame;
import javax.swing.*;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.scene.image.*;
import java.util.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.Transition;
import javafx.animation.Animation;

import javafx.embed.swing.SwingNode;
/**
 * The WelcomeGUI is the main stage where it calls the other panels.
 * The WelcomeGUI accepts input of the user preferences from the user.
 *
 * @author Alexandra Encarnacion and Sakura Mikano
 * @version 25.3.22
 */

public class WelcomeGUI extends Application {
    public Stage stage;
    private int selectedFromPrice;
    private int selectedToPrice;
    private int minNights;
    JPanel panel = new JPanel();
    private Information info = new Information();

    private ComboBox<Integer> fromPrices_combobox;
    private ComboBox<Integer> toPrices_combobox;
    private Label fromLabel;
    private Label toLabel;

    private TextField minNights_textfield ;
    private Label selectPriceRange_errorLabel;
    private Label minNights_errorLabel;

    private BorderPane root;
    private Pane bottomRootPane;
    private VBox welcomeVbox ;
    private Pane topRootPane;

    private Label instructions;
    private VBox errorVbox;
    private VBox userPrefVbox;

    private HBox pricesHbox;
    private HBox minNightsHbox;

    public WelcomeGUI(){
        stage = new Stage();
    }

    /**
     * The start method is the main entry point for every JavaFX application.
     * It is called after the init() method has returned and after
     * the system is ready for the application to begin running.
     *
     * @param stage the primary stage for this application.
     */

    public void start(Stage stage) {

        root = new BorderPane(center(), topRoot(), null, buttonRootPane(), null);
        Scene scene = new Scene(root, 1500, 500);
        
        scene.getStylesheets().add("WelcomeGUI.css");
        stage.setTitle("AirBnB Property Viewer");
        stage.setScene(scene);
        stage.setResizable(false);
        this.stage = stage;
        stage.show();
    }
    
    /**
     * This creates the buttons for the welcome page.
     * @return bottomRootPane
     */
    public Pane buttonRootPane(){
        return bottomRootPane = new BorderPane(null, null, insertForwardButton(), null, insertBackButton());
    }

    /**
     * This creates the top of the welcome page, where the user is able to enter the data to filter the properties that they are looking for.
     * @return topRootPane
     */
    public Pane topRoot(){
        userPrefVbox = new VBox();
        errorVbox = new VBox();

        pricesHbox = new HBox();
        minNightsHbox = new HBox();
        fromPrices_combobox = new ComboBox<Integer>();
        toPrices_combobox = new ComboBox<Integer>();

        fromLabel = new Label("FROM: ");
        toLabel = new Label("TO: ");
        toPrices_combobox.setPromptText("--Select price--");
        fromPrices_combobox.setPromptText("--Select price--");

        ObservableList<Integer> fromPricelist = fromPrices_combobox.getItems();
        ObservableList<Integer> toPricelist = toPrices_combobox.getItems();

        for (int price = 50; price < 10050; price += 50) {
            fromPricelist.add(price);
        }

        fromPrices_combobox.setOnAction((event) -> {
                int selectedPrice = fromPrices_combobox.getSelectionModel().getSelectedItem();
                toPricelist.clear();
                for (int toPrices = selectedPrice; toPrices < 10050; toPrices += 50) {
                    toPricelist.add(toPrices);
                }
            });

        pricesHbox.setMargin(fromLabel, new Insets(5, 5, 0, 0));
        pricesHbox.setMargin(toLabel, new Insets(5, 5, 0, 0));
        pricesHbox.setMargin(fromPrices_combobox, new Insets(0, 5, 0, 0));
        pricesHbox.setMargin(toPrices_combobox, new Insets(0, 5, 0, 0));
        pricesHbox.getChildren().addAll(fromLabel, fromPrices_combobox, toLabel, toPrices_combobox);

        Label enterMinNights_label = new Label("Enter number of intended nights: ");
        minNights_textfield = new TextField();
        minNights_textfield.setPrefWidth(40);

        minNightsHbox.setMargin(enterMinNights_label, new Insets(5, 5, 0, 0));
        minNightsHbox.setMargin(minNights_textfield, new Insets(0, 5, 0, 0));
        selectPriceRange_errorLabel = new Label("Please select price range before searching\nfor properties...");
        minNights_errorLabel = new Label("Please enter the minimum number of nights\n you are going to stay for...");
        errorVbox.setMargin(selectPriceRange_errorLabel, new Insets(5, 5, 0, 5));
        errorVbox.setMargin(minNights_errorLabel, new Insets(5, 5, 0, 5));
        errorVbox.getChildren().addAll(selectPriceRange_errorLabel, minNights_errorLabel);
        selectPriceRange_errorLabel.getStyleClass().add("errorLabel");
        minNights_errorLabel.getStyleClass().add("errorLabel");

        selectPriceRange_errorLabel.setVisible(false);
        minNights_errorLabel.setVisible(false);

        minNights_textfield.textProperty().addListener(new ChangeListener<String>() { // only allows numbers to be typed into the textfield
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                String newValue) {
                    if (!newValue.matches("\\d*")) {
                        minNights_textfield.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });

        minNightsHbox.getChildren().addAll(enterMinNights_label, minNights_textfield);
        userPrefVbox.setMargin(minNightsHbox, new Insets(5, 5, 0, 0));
        userPrefVbox.getChildren().addAll(pricesHbox, minNightsHbox);

        topRootPane = new BorderPane(errorVbox, null, userPrefVbox, null, null);
        return topRootPane;
    }
    
    /**
     * Create the instructions and the welcome sign of the GUI.
     * @return welcomeVbox The welcome sign.
     */
    public Pane center(){
        welcomeVbox = new VBox();
        instructions = new Label("");

        Image img = new Image("instructions.jpg");
        ImageView view = new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        instructions.setGraphic(view);
        instructions.setPadding( new Insets(0, 0, 50, 0));

        final String content = "WELCOME!";
        final Text text = new Text(10, 20, "");
        text.getStyleClass().add("welcomeSign");
        welcomeVbox.getChildren().addAll(text,instructions);
        welcomeVbox.setAlignment(Pos.CENTER);

        final Animation animation = new Transition() {
                {
                    setCycleDuration(Duration.millis(2000));
                    setCycleCount(INDEFINITE);
                }

                protected void interpolate(double frac) {
                    final int length = content.length();
                    final int n = Math.round(length * (float) frac);
                    text.setText(content.substring(0, n));
                }

            };

        animation.play();

        return welcomeVbox;
    }
    
    /**
     * Creates the forward button and assigns the action method.
     * @return forwardHbox Where the forward button is placed.
     */
    public Pane insertForwardButton(){
        Button forward_Btn = new Button("Forward");
        forward_Btn.setPrefWidth(80);
        forward_Btn.getStyleClass().add("searchButton");
        forward_Btn.setAlignment(Pos.CENTER);

        HBox forwardHbox = new HBox();
        forwardHbox.getChildren().add(forward_Btn);
        forwardHbox.setMargin(forward_Btn, new Insets(0, 10, 10, 0));
        forward_Btn.setOnAction(this::forwardAction);
        return forwardHbox;
    }

    /**
     * Creates the back button and assigns the action method.
     * @return backHbox Where the back button is stored.
     */
    public Pane insertBackButton(){
        Button back_Btn = new Button("Back");
        back_Btn.setPrefWidth(80);
        back_Btn.getStyleClass().add("searchButton");
        back_Btn.setAlignment(Pos.CENTER);
        HBox backHbox = new HBox();
        backHbox.getChildren().add(back_Btn);
        backHbox.setMargin(back_Btn, new Insets(0, 0, 10, 10));

        back_Btn.setOnAction(this::backAction);

        return backHbox;
    }
    
/**
* Calls the StatPanelGUI.
*/
    public Pane callStatsPanelGUI(){
        StatPanelGUI statPanel = new StatPanelGUI(info);
        return statPanel.start();
    }
    
    /**
    *   This method is called whenever the forward button is clicked.
    * @param ActionEvent e
    */
    public void forwardAction(ActionEvent e) {
        if (isValidInputs()){
            VBox panel = new VBox();
            panel.getChildren().add(callStatsPanelGUI());
            panel.setAlignment(Pos.CENTER);
            
            VBox legendUI = new VBox();
            legendUI.getChildren().add(callLegendGUI());
            legendUI.setAlignment(Pos.CENTER);
           
            SwingNode swingMapNode = new SwingNode();

            createSwingContent(swingMapNode);

            HBox pane = new HBox(20);
            pane.getChildren().addAll(swingMapNode);
            pane.setAlignment(Pos.CENTER_LEFT);
           
            root.setTop(panel);
            root.setCenter(pane);
            root.setLeft(legendUI);

        }
    }

    /**
    * This method is called whenever the back button is clicked.
    * @param ActionEvent e
    */
    public void backAction(ActionEvent e) {
        if (isValidInputs()){
            root.getChildren().clear();
            root.setCenter(center());
            root.setTop(topRoot());
            root.setBottom(buttonRootPane());
        }
    }

    /**
    * Checks whether input values are complete.
    * @return checkFlag. True if all the input fields are completed and therefore all their flags are true.
    */
    public boolean isValidInputs(){
        boolean checkFlag =false;
        boolean priceFlag;
        boolean minNightsFlag;
        int minPrice = 0;
        int maxPrice = 0;
        int minNights = 0;
        if (fromPrices_combobox.getSelectionModel().isEmpty() || toPrices_combobox.getSelectionModel().isEmpty()) {
            selectPriceRange_errorLabel.setVisible(true);
            priceFlag = false; //price range has not been chosen, shouldn't allow to search
        } else {
            minPrice = fromPrices_combobox.getSelectionModel().getSelectedItem();
            maxPrice = toPrices_combobox.getSelectionModel().getSelectedItem();
            selectPriceRange_errorLabel.setVisible(false);
            priceFlag = true;
        }
        if (minNights_textfield.getText().isEmpty()) {
            minNights_errorLabel.setVisible(true);
            minNightsFlag = false; //minimum nights have not been entered, shouldn't allow to search
        } else {
            minNights = Integer.valueOf( minNights_textfield.getText());
            minNights_errorLabel.setVisible(false);
            minNightsFlag = true;
        }
        if (priceFlag && minNightsFlag ) {
                checkFlag = true;
                info.setMinCost(minPrice);
                info.setMaxCost(maxPrice);
                info.setMinNights(minNights);
                for (Map.Entry<String, Borough> boroughName : info.boroughAbbreviations.entrySet()){
                    boroughName.getValue().updateProperties(minPrice, maxPrice, minNights);
                }
            }
        return checkFlag;
    }
    
    /**
     * creates the legend for the map image
     * @return pane containing contents of legend instance
     */
    public Pane callLegendGUI(){
        Legend legend = new Legend(info);
        return legend.start();
    }

    /**
     * converts the GUI created by java swing into a node that can be used by javafx
     * @param swingNode the node that information will be stored into
     */
    private void createSwingContent(SwingNode swingNode) {
        LondonMapArea mapArea = new LondonMapArea(info);
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try{
                        swingNode.setContent(mapArea.initUI());
                        // legend.setContent(uhh.getLegendUI());
                    }catch(Exception e){
                        System.out.println("there is an exception");
                        return;
                    }
                }
            });
    }

    
}

