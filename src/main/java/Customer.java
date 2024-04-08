import java.util.Date;

/**
 * Customer - interface to a customer.
 * @version 1.0 Date created: 12/02/2024
 * @author Robert Petecki
 */

public interface Customer {
    /**
     * Returns the name of a customer.
     * All customers have a name.
     *
     * @return the Name object
     */
    Name getCustomerName();

    /**
     * Returns the customer date of birth.
     *
     * @return a Date object
     */
    Date getCustomerDOB();

    /**
     * Returns the customer number.
     * All customers have a customer number
     *
     * @return a CustomerNumber object
     */
    CustomerNumber getCustomerNumber();

    /**
     * Returns the date of issue of the customer record
     *
     * @return a Date object
     */
    Date getDateIssuedRecord();

    /**
     * Returns a boolean indicating whether or not the customer has a garden.
     *
     * @return true if customer has a garden and false otherwise
     */
    boolean getHasGarden();

    /**
     * Returns a String indicating whether or not the customer has a garden.
     *
     * @return
     */
    String getHasGardenString();

    /**
     * Returns an int with customer's age.
     *
     * @return int age based on current date and customer's date of birth
     */
    int ageCalculator();
}
