import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * CustomerRecordFactory - abstract class with factory method getCustomerRecordInstance for creating Customer Record instances with unique Customer Number for each Customer.
 * @version 1.0 Date created: 12/02/2024
 * @author Robert Petecki
 */
public abstract class CustomerRecordFactory implements Customer {


    /**
     * Map of customers registered - used to ensure uniqueness of Customer Number per Customer.
     */
    private static final Map<CustomerNumber, Customer> CUSTOMER_RECORDS = new HashMap<CustomerNumber, Customer>();

    /**
     * Immutable customer name.
     */
    private final Name customerName;

    /**
     * Immutable customer date of birth.
     */
    private final Date customerDOB;

    /**
     * Unique, immutable customer number.
     */
    private final CustomerNumber customerNumber;

    /**
     * Storing instance of a customer number generated.
     */
    private static CustomerNumber customerNumberInstance;

    /**
     * Immutable customer record issue date.
     */
    private final Date customerDateIssuedRecord;

    /**
     *  Mutable status indicating if customer has a garden.
     */
    private boolean customerHasGarden;


    //package private constructor
    //initialising state of common variables
    /**
     * Initialising state of common variables.
     * @param firstName first name of the customer
     * @param lastName last name of the customer
     * @param dob date of birth
     */
    CustomerRecordFactory(String firstName, String lastName, Date dob)
    {
        customerName = new Name(firstName, lastName);
        customerDOB = new Date(dob.getTime());
        customerNumber = customerNumberInstance;
        customerDateIssuedRecord = new Date();

    }

    //factory method
    /**
     * Factory method creating unique instances of customer records with unique customer number per customer.
     * @param firstName first name of the customer
     * @param lastName last name of the customer
     * @param dob date of birth
     * @param hasGarden indicating if customer has a garden
     * @return unique instance of a customer record, based on customer number-customer record
     */
    public static CustomerRecord getCustomerRecordInstance(String firstName, String lastName, Date dob, Boolean hasGarden)
    {
        final Date dateIssuedNow = new Date(); //date of issue won't be changed during the method execution

        customerNumberInstance = CustomerNumber.getCustomerNumberInstance(firstName, (Date) dateIssuedNow.clone()); //create customer number

        Customer customerRecord = CUSTOMER_RECORDS.get(customerNumberInstance); //ensuring unique customer record instance per customer number by checking hashmap, note: getCustomerNumberInstance already ensures uniqueness of customerNumber

        if (customerRecord != null) //imposing uniqueness
            return (CustomerRecord) customerRecord;

        //code below ignored if customer record instance per customer number was already present in the hash map, i.e. not unique
        customerRecord = new CustomerRecord(firstName, lastName, dob, dateIssuedNow, hasGarden); //uniqueness ensured so create new customer record

        CUSTOMER_RECORDS.put(customerRecord.getCustomerNumber(), customerRecord); //put customer record in customer records map

        return (CustomerRecord) customerRecord;
    }

    /**
     * @see Customer#getCustomerName()
     */
    public Name getCustomerName()
    {
        return customerName;
    }

    /**
     * @see Customer#getCustomerDOB()
     */
    public Date getCustomerDOB()
    {
        return (Date) customerDOB.clone(); //defensive copy to ensure immutability
    }

    /**
     * @see Customer#getDateIssuedRecord()
     */
    public Date getDateIssuedRecord()
    {
        return (Date) customerDateIssuedRecord.clone(); //defensive copy to ensure immutability
    }

    /**
     * @see Customer#getCustomerNumber()
     */
    public CustomerNumber getCustomerNumber()
    {
        return customerNumber;
    }


    /**
     * @see Customer#getHasGarden()
     */
    public boolean getHasGarden()
    {
        return customerHasGarden;
    }

    public void setHasGarden(boolean hasGarden)
    {
        customerHasGarden = hasGarden;
    }

    /**
     * @see Customer#getHasGardenString()
     */
    public String getHasGardenString()
    {
        return (getHasGarden()) ? "yes" : "no";
    }


    /**
     * Returns logical equivalence of customer records based on customer's name, and date of birth
     * @param otherCustomerRecord object to compare
     * @return true if logically the same, false otherwise
     */
    @Override
    public boolean equals(Object otherCustomerRecord) {
        if (this == otherCustomerRecord) return true; // reflexivity
        if (!(otherCustomerRecord instanceof Customer)) return false; // non-nullity
        Customer c = (Customer) otherCustomerRecord; // consistency
        return (customerName == null
                ? c.getCustomerName() == null
                : customerName.equals(c.getCustomerName()))
                && (customerDOB == null
                ? c.getCustomerDOB() == null
                : customerDOB.equals(c.getCustomerDOB()));
    }

    /**
     * Returns a hash code value for the object.
     * @return hash code value for this distinct object
     */
    @Override
    public int hashCode() {
        int hc = 17;
        int multiplier = 37;
        hc = multiplier * hc
                + (customerName == null ? 0 :customerName.hashCode());
        hc = multiplier * hc
                + (customerDOB == null ? 0 : customerDOB.hashCode());

        return hc;
    }

    /**
     * Returns String with information about customer.
     * @return String with information
     */
    @Override
    public String toString() {
        return "Customer name: " + customerName + " " +
                "Date of birth: " + customerDOB + " " +
                "Customer number: " + customerNumber + " " +
                "Date of record issue: " + getDateIssuedRecord() + " " +
                "Has Garden: " + getHasGardenString();
    }
}
