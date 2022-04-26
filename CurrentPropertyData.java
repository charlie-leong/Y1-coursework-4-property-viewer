/**
 * Used to instantiate constants and get the appropriate data for the current property. Such as the name of the host of the current property.
 * This class is then extended to the other two display classes for the properties and will call the appropriate methods when needed.
 * There is no GUI in this class.
 *
 * @author Sakura Mikano
 * @version 30/03/2022
 */
public abstract class CurrentPropertyData 
{
    private static final String hostNameText = "Name of host: ";
    private static final String pricePropertyText = "Price of property: ";
    private static final String numReviewsText = "Number of reviews: ";
    private static final String minNightsText = "Minimum number of nights: ";
    private static final String roomTypeText = "Type of Room: ";
    private static final String lastReviewText = "Last review: ";
    private static final String reviewsPerMonthText = "Number of reviews per month: ";
    private static final String calculatedHostListingText = "Number of listings of host: ";
     private static final String availbility365Text = "Availability out of the year: ";
    
    private String nameOfHost;
    private String priceOfProperty;
    private String numOfReviews;
    private String minNights;
    
    private AirbnbListing currentProperty;
     
    /**
     * Returns the constant for the name of host.
     * @return hostNameText
     */
    protected String getHostNameText()
    {
        return hostNameText;
    }
    
    protected String getPricePropertyText()
    {
        return pricePropertyText;
    }

    protected String getNumReviewsText()
    {
        return numReviewsText;
    }
    
    protected String getMinNightsText()
    {
        return minNightsText;
    }
    
    protected String getavailbility365Text()
    {
        return availbility365Text;
    }
    
   protected String getReviewsPerMonthText()
    {
        return reviewsPerMonthText;
    }
    
    protected String getLastReviewText()
    {
        return lastReviewText;
    }
    
    protected String getRoomTypeText()
    {
        return roomTypeText;
    }
    
    protected String getCalculatedHostListingText()
    {
        return calculatedHostListingText;
    }
    
    /**
     * Returns the current property's host name.
     * @return hostName 
     */
    protected String getNameOfHostData(){
        return getCurrentProperty().getHost_name();
    }
    
    protected String getPriceOfPropertyData(){
        return "£" + getCurrentProperty().getPrice() + ".00";
    }
    
    protected String getNumOfReviewsData(){
        return "" + getCurrentProperty().getNumberOfReviews();
    }
    
    protected String getMinNightsData(){
        return "" + getCurrentProperty().getMinimumNights();
    }
    
    protected String getRoomTypeData(){
        return getCurrentProperty().getRoom_type();
    }
    
    protected String getLastReviewData(){
        return getCurrentProperty().getLastReview();
    }
    
     protected String getReviewsPerMonthData(){
        return "" + getCurrentProperty().getReviewsPerMonth();
    }
    
     protected String getCalculatedHostListingsCountData(){
        return ""+ getCurrentProperty().getCalculatedHostListingsCount();
    }
    
    protected String getAvailbility365Data(){
        return ""+ getCurrentProperty().getAvailability365();
    }

    /**
     * Abstract method which is used to call the currentProperty.
     */
     protected abstract AirbnbListing getCurrentProperty();
}
