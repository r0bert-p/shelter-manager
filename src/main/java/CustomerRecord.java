import java.util.Calendar;
import java.util.Date;

/**
 * CustomerRecord - customer record containing the following: first name, lastName, date of birth, date of record issue, if customer has a garden.
 * @version 1.0 Date created: 12/02/2024
 * @author Robert Petecki
 */
public final class CustomerRecord extends CustomerRecordFactory implements Comparable<CustomerRecord>{

    /**
     * CustomerRecord constructor with all fields.
     * @param firstName first name of a customer
     * @param lastName last name of a customer
     * @param dob date of birth of a customer
     * @param dateIssuedRecord date of customer record issue
     * @param hasGarden whether the customer has a garden true or false
     */
    CustomerRecord(String firstName, String lastName, Date dob, Date dateIssuedRecord, boolean hasGarden)
    {
        super(firstName, lastName, dob);
        this.setHasGarden(hasGarden);
    }

    /**
     * CustomerRecord constructor for a temporary customer record (not all fields)
     * @param firstName first name of a customer
     * @param lastName last name of a customer
     * @param dob date of birth of a customer
     */
    CustomerRecord(String firstName, String lastName, Date dob)
    {
        super(firstName, lastName, dob);
    }

    /**
     * @see Customer#ageCalculator()
     */
    public int ageCalculator() {
        //Setting calendars
        Calendar calendarNow = Calendar.getInstance(); //calendar with current date
        Calendar calendarDOB = Calendar.getInstance(); //calendar for date of birth
        calendarDOB.setTime(getCustomerDOB()); //calendarDOB set to date of birth from the customer record

        //Calculating age using calendars with conditional if birthday is later than current date in the current year
        int age = calendarNow.get(Calendar.YEAR) - calendarDOB.get(Calendar.YEAR); //age based on years
        age -= (calendarNow.get(Calendar.DAY_OF_YEAR) < calendarDOB.get(Calendar.DAY_OF_YEAR)) ? 1 : 0; //actual age, based on full DOB

        return age;
    }


    /**
     * Compares CustomerRecord based on CustomerNumber.
     * @param otherCustomerRecord to be compared.
     * @return a negative integer, zero, or a positive integer as this CustomerRecord is less than, equal to, or greater than the specified CustomerRecord.
     */
    public int compareTo(CustomerRecord otherCustomerRecord) {
        return this.getCustomerNumber().compareTo(otherCustomerRecord.getCustomerNumber());
    }
}