import java.util.HashMap;
import java.util.Map;
/**
 * PetID - class for generating unique, immutable PetIDs. PetID has the following format: A pet ID has two components - a single letter followed
 * by a two-digit number. For example: A00. Allows up to 2600 unique PetIDs.
 * @version 1.0 Date created: 12/02/2024
 * @author Robert Petecki
 */
public final class PetID implements Comparable<PetID> {

    //private and final fields to ensure immutability

    /**
     * Storing unique PetIDs, mapped by their String representation
     */
    private static final Map<String, PetID> PET_IDS = new HashMap<String, PetID>(); //map for imposing uniqueness
    /**
     * Immutable component 1 of PetID - a single letter.
     */
    private final char componentPetID1;
    /**
     * Immutable component 2 of PetID - a two-digit number.
     */
    private final int componentPetID2;
    /**
     * Immutable String representation of PetID.
     */
    private final String stringPetID;
    /**
     * Character iteration for generating component 1 of PetID.
     */
    private static char charComponentCount = 'A';
    /**
     * int iteration for generating component 2 of PetID
     */
    private static int intComponentCount = -1; //start at -1 to include 0 in the componentID2 instances


    //private constructor to ensure uniqueness, client can't use
    /**
     * Constructs a unique, immutable PetID.
     */
    private PetID()
    {
        int cID2 = generateComponentID(); //generator method
        stringPetID = charComponentCount + String.format("%02d", intComponentCount); //String representation of PetID ensuring two-digit number format
        componentPetID1 = charComponentCount; //component 1
        componentPetID2 = cID2; //component 2

    }

    //static factory method ensuring uniqueness
    /**
     * Factory method for generating unique PetID instances.
     * @return
     */
    public static PetID getPetIDInstance()
    {
        String stringPetID = charComponentCount + "" + intComponentCount;
        PetID petID = PET_IDS.get(stringPetID); //ensuring uniqueness
        if (petID == null) //unique
        {
            petID = new PetID(); //unique pet id instance
            PET_IDS.put(stringPetID, petID); //add to the map
        }
        return petID;
    }

    //access to each component of PetID

    /**
     * Returns component 1 of PetID.
     * @return componentPetID1
     */
    public String getComponentID1()
    {
        return String.valueOf(componentPetID1);
    }

    /**
     * Returns component 2 of PetID.
     * @return componentPetID2
     */
    public String getComponentID2()
    {
        return String.valueOf(componentPetID2);
    }


    //generator method for generating unique components
    /**
     * Generates next component 2 of PetID and changes charComponentCount to the next char if 100 (not two-digit) reached.
     * @return int
     */
    private int generateComponentID()
    {
        //back to -1 if reaches 100 as compID2 needs to be two digit
        if(intComponentCount == 99)
        {
            intComponentCount = 0;
            if (charComponentCount == 'Z')
            {
                throw new IllegalArgumentException("No more letters can be used to generate unique ID in the current ID format");
            }
            else
            {
                charComponentCount ++; //next letter when 100 reached
            }
            return intComponentCount;
        }
        intComponentCount++;
        return intComponentCount;
    }

    /**
     * Returns string representation of PetID
     * @return
     */
    @Override
    public String toString()
    {
        return stringPetID;
    }

    /**
     * Overriden compareTo for TreeMap implementation in shelter manager, that compares two PetID String representations
     * @param otherPetID other PetID to comnpare to.
     * @return a negative integer, zero, or a positive integer as this PetID is less than, equal to, or greater than the specified PetID.
     */
    public int compareTo(PetID otherPetID) {
         return this.stringPetID.compareTo(otherPetID.stringPetID);
    }
}
