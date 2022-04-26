
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * The test class InformationTest.
 *
 * @author  Alexandra Encarnacion
 * @version 30.03.22
 */
public class InformationTest
{
    /**
     * Default constructor for test class InformationTest
     */
    public InformationTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    /**
     * This test ensures that the available boundary price range and minimum nights 
     * is initialised correctly.
     */
    @Test
    public void IntialiseBoundaryPriceRange()
    {
        Information informat1 = new Information();
        informat1.setMinCost(50);
        informat1.setMaxCost(10000);
        informat1.setMinNights(1);
        assertEquals(50, informat1.getMinimumCost());
        assertEquals(10000, informat1.getMaximumCost());
        assertEquals(1, informat1.getMinimumNights());
    }

    /**
     * Ensures that the correct total number of listings is being read.
     */
    @Test
    public void NumberListing()
    {
        Information informat1 = new Information();
        assertEquals(53904, informat1.totalNumberOfListings());
    }

    /**
     * This test makes sure that the correct number of listings is being assigned to the
     * correct borough.
     */
    @Test
    public void CheckNumListings()
    {
        Information informat1 = new Information();
        assertEquals(553, informat1.getNumberOfListing("CROY"));
    }

    /**
     * This test ensures that the correct borough is retrieved when showing the statistics 
     * for which borough is the most expensive.
     */
    @Test
    public void MostExpensiveBorough()
    {
        Information informat1 = new Information();
        assertEquals("Westminster", informat1.mostExpensiveBorough());
    }

    /**
     * This test ensures that the correct borough is retrieved for the the most available borough.
     */
    @Test
    public void MostAvailable()
    {
        Information informat2 = new Information();
        assertEquals("Tower Hamlets", informat2.mostAvailableBorough());
    }

    /**
     * This test ensures that the largest number of properties in a borough is retrieved correctly. 
     */
    @Test
    public void LargestNumProperties()
    {
        Information informat1 = new Information();
        assertEquals(5613, informat1.largestNumOfProperties());
    }

    /**
     * This test checks whether a known borough abbreviation exist within the hash table.
     */
    @Test
    public void testGetBoroughName()
    {
        Information informat1 = new Information();
        assertNotNull(informat1.getBorough("SUTT"));
    }

    /**
     *  This test checks whether an unknown borough abbreviation does not exist within 
     *  the hash table.
     */
    @Test
    public void getNonExistingBorough()
    {
        Information informat1 = new Information();
        assertNull(informat1.getBorough("RED"));
    }

    /**
     * Checks whether the correct number of properties that are not private rooms are listed.
     */
    @Test
    public void totalNumHomes()
    {
        Information informat1 = new Information();
        assertEquals(27885, informat1.totalNumberOfEntireHomes());
    }

    /**
     * This test ensures that the correct number of total listed properties is retrieved
     * given the boundary price range.
     */
    @Test
    public void TNumListingBoundaryPriceRange()
    {
        Information informat2 = new Information();
        assertEquals(43501, informat2.totalListingOverPriceRange(50, 10000));
    }

    /**
     * This test ensures that the result of the average results is correct.
     */
    @Test
    public void checkAverageReview()
    {
        Information informat2 = new Information();
        assertEquals(12.0, informat2.averageReviews(), 0.1);
    }

    /**
     * This test confirms that the average number of tourist destinations in a borough, is correct.
     */
    @Test
    public void testAvgDestinations()
    {
        Information informat1 = new Information();
        assertEquals(0.8181818, informat1.averageDestinations(), 0.1);
    }

    /**
     * This test ensures that the loadBorough method is  correctly loading the 
     * boroughs into the list.
     */
    @Test
    public void testLoadBorough()
    {
        Information informat2 = new Information();
        assertNotNull(informat2.loadBoroughs());
    }

    /**
     * This test checks that the data is being loaded correctly from the dataLoader class.
     * As the list created in the dataLoader class is not null, then the map in the information 
     * class is also not null.
     */
    @Test
    public void testingSetBoroughs()
    {
        Information informat1 = new Information();
        DataLoader dataLoad1 = new DataLoader();
        java.util.ArrayList<AirbnbListing> arrayLis1 = dataLoad1.loadAirbnbData();
        assertNotNull(arrayLis1);
        informat1.setBoroughs(arrayLis1);
    }

    /**
     * This test ensures that the lowest number of minimum number of nights retrieves the 
     * correct amount of property listings.
     */

    @Test
    public void lowNumListingNights()
    {
        Information informat1 = new Information();
        assertEquals(20683, informat1.totalListingOverNumOfNights(1));
    }
    
    /**
     * This test ensures that the highest number of minimum number of nights retrieves the 
     * correct amount of propert listings.
     */

    @Test
    public void highNumList()
    {
        Information informat1 = new Information();
        assertEquals(53904, informat1.totalListingOverNumOfNights(5000));
    }


}


















