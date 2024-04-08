/**
 * Name - immutable class for storing customer first name and last name
 * @version 1.0 Date created: 06/02/2024
 * @author Robert Petecki
 */
final class Name {

    /**
     * First name of a customer.
     */
    private final String firstName;

    /**
     * Last name of a customer.
     */
    private final String lastName;

    /**
     * Construct a Name object from the first and last names passed as constructor parameters.
     * @param firstName the first name of the (full) name, must be without whitespaces
     * @param lastName the last name of the (full) name, must be without whitespaces
     * // @throws NullPointerException if either <code>firstName</code> or <code>lastName</code> is null
     *
     */
    public Name(String firstName, String lastName)
    {
        this.firstName = firstName.trim(); //removing whitespaces
        this.lastName = lastName.trim(); //removing whitespaces
    }

    /**
     * Return the first name of the (full) name.
     * @return the first name
     */
    public String getFirstName() {
        return new String(firstName);
    }

    /**
     * Return the last name of the (full) name.
     * @return the last name
     */
    public String getLastName() {
        return new String(lastName);
    }

    @Override
    public boolean equals(Object otherName)
    {
        if (this == otherName) return true; // reflexivity
        if (!(otherName instanceof Name)) return false; // non-nullity
        Name n = (Name) otherName; // consistency
        return (firstName == null
                ? n.getFirstName() == null
                : firstName.equals(n.getFirstName()))
                && (lastName == null
                ? n.getLastName() == null
                : lastName.equals(n.getLastName()));
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
                + (firstName == null ? 0 :firstName.hashCode());
        hc = multiplier * hc
                + (lastName == null ? 0 : lastName.hashCode());

        return hc;
    }

    /**
     * Returns a name as a string: first name and last name seperated by space.
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return firstName + " " + lastName;
    }

}
