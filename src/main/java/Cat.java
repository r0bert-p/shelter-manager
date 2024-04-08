/**
 * Cat - Pet of pet type "Cat" that has adoption status, has a unique PetID, and has care instructions. This Cat can be instantiated by classes in this same package.
 * Use PetFactory.getPetInstance("Cat") to get an instance.
 * @version 1.0 Date created: 12/02/2024
 * @author Robert Petecki
 */
final class Cat extends PetFactory {

/**
 * Constructs a Cat that is "Not adopted" with care instructions: "Feed two times a day."
 */
    Cat() //package-private constructor
    {
        super();
        setCareInstructions("Feed two times a day.");
    }
}
