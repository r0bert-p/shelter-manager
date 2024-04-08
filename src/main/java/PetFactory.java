import java.util.HashMap;
import java.util.Map;

/**
 * PetFactory - abstract implementation of Pet with a factory method for getting unique Pet instances .getPetInstance()
 * @version 1.0 Date created: 12/02/2024
 * @author Robert Petecki
 */
public abstract class PetFactory implements Pet {

    //constants
    /**
     * Constant value, used for checking if parameter input value is "cat".
     */
    public static final String CAT_PET = "cat";
    /**
     * Constant value, used for checking if parameter input value is "dog".
     */
    public static final String DOG_PET = "dog";


    //map of pet instances
    /**
     * Storing unique Pets based on their PetID, used for ensuring unique instances.
     */
    private static final Map<PetID, Pet> pets = new HashMap<PetID, Pet>();


    //fields
    /**
     * Immutable, unique PetID.
     */
    private final PetID petID;
    /**
     * Storing PetID instance.
     */
    private static PetID petIDInstance;
    /**
     * Immutable pet type.
     */
    private final String petType;
    /**
     * Mutable adoption status.
     */
    private boolean isAdopted;
    /**
     * Adoption status String representation
     */
    private String isAdoptedString;
    /**
     * Care instructions.
     */
    private String careInstructions;


    //package-private constructor
    /**
     * Assumes pet is not adopted when it's created.
     */
    PetFactory() {
        isAdopted = false;
        petType = this.getClass().toString().substring(6);
        petID = petIDInstance;
    }


    //factory method
    /**
     * Creates a unique Pet instance of specified pet type.
     * @param petType specified pet type
     * @return unique Pet instance with randomly generated unique PetID
     */
    public static Pet getPetInstance(String petType)
    {
        petType = petType.toLowerCase(); //petType parameter is case-insensitive

        petIDInstance = PetID.getPetIDInstance();

        Pet pet = pets.get(petIDInstance); //ensuring single instance per pet ID

        if (pet != null) //imposing uniqueness
            return pet;

        if (petType.equals(CAT_PET))
        {
            pet = new Cat();
        }
        else if (petType.equals(DOG_PET))
        {
            pet = new Dog();
        }
        else
        {
            throw new IllegalArgumentException("Invalid pet type: " + petType);
        }

        //put pet in pets map
        pets.put(pet.getPetID(), pet);
        //System.out.println(pets); CODE TEST

        //return the instance
        return pet;
    }

    /**
     * @see Pet#getPetID()
     */
    public PetID getPetID()
    {
        return petID;
    }


    /**
     * @see Pet#getPetType()
     */
    public String getPetType() {
        return petType;
    }

    /**
     * @see Pet#getAdopted()
     */
    public boolean getAdopted() {
        return isAdopted;
    }

    /**
     * @see Pet#getAdoptedString()
     */
    public String getAdoptedString() {
        return (getAdopted()) ? "adopted" : "not adopted";
    }

    /**
     * @see Pet#setAdopted(boolean)
     */
    public void setAdopted(boolean adopted)
    {
        isAdopted = adopted;
    }

    /**
     * @see Pet#getCareInstructions()
     */
    public String getCareInstructions()
    {
        return careInstructions;
    }

    /**
     * @see Pet#setCareInstructions(String)
     */
    public void setCareInstructions(String careInstructions)
    {
        this.careInstructions = careInstructions;
    }

    /**
     * Returns a string representation of Pet information including Pet ID, pet type, adoption status, care instructions.
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return "Pet ID: " + getPetID() + " " +
                "Pet type: " + getPetType() + " " +
                "Adoption status: " + getAdoptedString() + " " +
                "Care instructions: " + getCareInstructions();
    }

}
