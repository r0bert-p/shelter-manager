/**
 * Dog - Pet of pet type "Dog" that that has adoption status, has a unique PetID, has trained status, and has care instructions. This Dog can be instantiated by classes in this same package.
 * @version 1.0 Date created: 12/02/2024
 * @author Robert Petecki
 */
final class Dog extends PetFactory implements Trainable {

    /**
     * Training status of a Dog. Initialised to false in the constructor.
     */
    private boolean trained; //mutable using setTrained(boolean)

    /**
     * Constructs a Dog that is "Not adopted" and "Not trained" with care instructions "Feed three times a day, walk once a day.".
     */
    Dog()
    {
        super(); //PetFactory constructor
        setCareInstructions("Feed three times a day, walk once a day."); //Dog-specific
        trained = false; //Dog-specific
    }

    /**
     * @see Trainable#getTrained()
     * @return true if dog is trained and false otherwise
     */
    public boolean getTrained()
    {
        return trained;
    }

    /**
     * @see Trainable#getTrainedString()
     */
    public String getTrainedString() {
        return (getTrained()) ? "trained" : "not trained";
    }

    /**
     * @see Trainable#setTrained(boolean)
     */
    public void setTrained(boolean trainedUpdate)
    {
        this.trained = trainedUpdate;
    }


    @Override
    public String toString() {
        return super.toString() + " Training status:" + getTrainedString();
    }
}
