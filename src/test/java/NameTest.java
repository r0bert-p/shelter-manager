import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Name class - testing methods of Name class.
 * @version 1.0 Date created: 06/03/2024
 * @author Robert Petecki
 */

class NameTest {

    /**
     * Testing getFirstName() method.
     * @see Name#getFirstName()
     */
    @Test
    void getFirstName() {
        Name name = new Name("Anna", "Smith"); //new name
        assertEquals("Anna", name.getFirstName()); //assert expected equals "Anna"
    }

    /**
     * Testing getLastName() method.
     * @see Name#getLastName()
     */
    @Test
    void getLastName() {
        Name name = new Name("Anna", "Smith");
        assertEquals("Smith", name.getLastName()); //assert expected equals "Anna"
    }

    /**
     * Testing overriden equals() method.
     * @see Name#equals(Object)
     */
    @Test
    void testEquals() {
        Name name1 = new Name("Anna", "Smith"); //new logically-equivalent names
        Name name1Copy = new Name("Anna", "Smith");
        Name name2 = new Name("Jan", "Kowalski"); //new different names
        Name name3 = new Name("Anna", "Kowalski");
        assertTrue(name1.equals(name1Copy)); //assert expected true, logical equivalence overriden equals()
        assertFalse(name1.equals(null)); //non-nullity
        assertFalse(name1.equals(name2)); //not logically-equivalent as a whole
        assertFalse(name1.equals(name3));
        assertFalse(name2.equals(name3));

    }

    /**
     * Testing overriden hashCode() method.
     * @see Name#hashCode()
     */
    @Test
    void testHashCode() {
        Name name1 = new Name("Anna", "Smith"); //new logically-equivalent names
        Name name1Copy = new Name("Anna", "Smith");
        Name name2 = new Name("Jan", "Kowalski"); //different name
        assertTrue(name1.hashCode() == name1Copy.hashCode()); //assert expected not equal, overriden hashCode()
        assertFalse(name1.hashCode() == name2.hashCode());
    }

    /**
     * Testing overriden toString() method.
     * @see Name#toString()
     */
    @Test
    void testToString() {
        Name name1 = new Name("Anna", "Smith");
        assertEquals("Anna", name1.getFirstName().toString()); //first name match
        assertEquals("Smith", name1.getLastName().toString()); //last name match
        assertFalse(name1.toString().equals(null)); //not null
    }
}