import java.util.*;

/**
 * CustomerNumber - class for generating unique, immutable customer numbers. CustomerNumber has the following format:
 * the first component is the concatenation of the initial of the first name of the customer with an arbitrary serial number.
 * The second component is the concatenation of the month of issue with the year of issue of the record.
 * For example, the string representation of the CustomerNumber for a record issued to John Smith in January 2024 would have the form: J10.12024
 * where the 10 is a serial number that, with the initials and year, guarantees the uniqueness of the customer number as a whole (note the two parts are separated by a dot “.”).
 * @version 1.0 Date created: 06/02/2024
 * @author Robert Petecki
 */
public class CustomerNumber implements Comparable<CustomerNumber> {

    //private and final fields to ensure immutability
    /**
     * Storing unique customer number.
     */
    private static final Set<CustomerNumber> CUSTOMER_NUMBERS = new HashSet<CustomerNumber>(); //set for imposing uniqueness of CustomerNumber
    /**
     * Component 1 of customer number: concatenation of the initial of the first name of the customer with an arbitrary serial number.
     */
    private final String componentCustomerNumber1; //concatenation of first name initial and an arbitrary serial number
    /**
     * Component 2 of customer number: concatenation of the month of issue with the year of issue of the record.
     */
    private final String componentCustomerNumber2; //concatenation of the month of issue with the year of issue of the record

    /**
     * Random serial number generator used for generating serial number for component 1 of customer number.
     * @see Random
     */
    private static Random random = new Random(); //random generator for generating serial number used in componentCustomerNumber1

    //private constructor to ensure uniqueness, client can't see it

    /**
     * Constructs a customer number from specified first name and date of record issue.
     * @param firstName first name of the customer needed for component 1 of the customer number
     * @param dateRecordIssued date of record issue, needed for component 2 of the customer number
     */
    private CustomerNumber(String firstName, Date dateRecordIssued)
    {
        componentCustomerNumber1 = firstName.substring(0, 1) + random.nextInt(100); //first letter of the firstName, and random number up to two digits
        componentCustomerNumber2 = getComponentCustomerNumber2(dateRecordIssued); //date of record issue passed to the generator method
    }

    //generator method to create a string concatenation of month and year for component customer no 2
    /**
     * Generates component 2 of the customer number from the specified date of record issue.
     * @param dateRecordIssued specified date of reccord issue
     * @return String representation of component 2 of the customer number
     */
    private String getComponentCustomerNumber2(Date dateRecordIssued)
    {
        Calendar calendar = Calendar.getInstance(); //local calendar instance
        calendar.setTime(dateRecordIssued); //set calendar to the dateOfIssue from parameter

        int monthNumber = calendar.get(Calendar.MONTH) + 1; //get int month from calendar, month indexes start from 0 so increment by 1
        int yearNumber = calendar.get(Calendar.YEAR); //get int year from calendar

        return String.valueOf(monthNumber) + yearNumber; //return as a String
    }

    //static factory method ensuring uniqueness
    /**
     * Factory method for getting immutable, unique customer number instances from the specified customer first name and date of record issue.
     * @param firstName specified customer first name
     * @param dateRecordIssued specified date of record issue
     * @return instance of a unique, immutable customer number
     */
    public static CustomerNumber getCustomerNumberInstance(String firstName, Date dateRecordIssued)
    {
        CustomerNumber customerNumberInstance = new CustomerNumber(firstName, dateRecordIssued);
        boolean isUnique; //local boolean used to ensure unique customer number instance
        isUnique = CUSTOMER_NUMBERS.add(customerNumberInstance); //add to set, true if unique and added to the set, false otherwise

        while (isUnique == false) //while loop until uniqueness achieved (serial number generator)
        {
            customerNumberInstance = new CustomerNumber(firstName, dateRecordIssued); //new customer number
            isUnique = CUSTOMER_NUMBERS.add(customerNumberInstance); //add to set, if is unique the boolean reassigned and while loop stops
        }
        return customerNumberInstance;
    }

    /**
     * String representation of the customer number with two parts seperated by a dot ".".
     * @return String customer number
     */
    @Override
    public String toString()
    {
        return componentCustomerNumber1 + "." + componentCustomerNumber2;
    }

    /**
     * Compares CustomerNumber string representation.
     * @param otherCustomerNumber the CustomerNumber to be compared
     * @return a negative integer, zero, or a positive integer as this CustomerNumber is less than, equal to, or greater than the specified CustomerNumber.
     */
    public int compareTo(CustomerNumber otherCustomerNumber) {
        return this.toString().compareTo(otherCustomerNumber.toString());
    }
}
