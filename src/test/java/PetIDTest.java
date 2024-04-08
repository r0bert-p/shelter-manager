import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test PetID class - testing methods of the PetID class.
 * @version 1.0 Date created: 06/03/2024
 * @author Robert Petecki
 */
class PetIDTest {

    /**
     * Testing getPetIDInstance() method.
     * @see PetID#getPetIDInstance()
     */
    @Test
    void getPetIDInstance() {
        PetID petID1 = PetID.getPetIDInstance(); //new unique instance
        PetID petID1Copy = petID1; //logically-equivalent to petID1
        PetID petID2 = PetID.getPetIDInstance(); //new unique instance

        assertNotNull(petID1); //PetID was generated
        assertNotEquals(petID1, petID2); //unique petID generation
        assertEquals(petID1, petID1Copy); //logical equivalence
    }

    /**
     * Testing getComponentID1() accessor method.
     * @see PetID#getComponentID1() 
     */
    @Test
    void getComponentID1() {
        PetID petID = PetID.getPetIDInstance();
        String componentID1 = petID.getComponentID1();

        assertTrue(Character.isLetter(componentID1.charAt(0))); //Component 1 is a letter
    }

    /**
     * Testing getComponentID2() accessor method.
     * @see PetID#getComponentID2()
     */
    @Test
    void getComponentID2() {
        PetID petID = PetID.getPetIDInstance();
        String componentID2 = petID.getComponentID2();

        assertTrue(Character.isDigit(componentID2.charAt(0))); //Component 2 has only digits
        assertEquals(1, componentID2.length()); //Component 2 is a two digit number
    }

    /**
     * Testing overriden toString()  method.
     * @see PetID#toString()
     */
    @Test
    void testToString() {
        PetID petID = PetID.getPetIDInstance();

        assertEquals("A00", petID.toString()); //First generated ID is "A00"
    }

    /**
     * Testing overriden compareTo() method.
     * @see PetID#compareTo(PetID)
     */
    @Test
    void testCompareTo()
    {
        PetID petID1 = PetID.getPetIDInstance(); //A00
        PetID petID2 = PetID.getPetIDInstance(); //A01

        assertTrue(petID1.compareTo(petID2) < 0); //expected petID1 < petID2
    }
}