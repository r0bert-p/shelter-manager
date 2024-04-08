/**
 * Pet - interface to a pet.
 *
 * @author Rouaa Yassin Kassab
 * Copyright (C) 2024 Newcastle University, UK
 */
public interface Pet {
	/**
	 * Returns the pet ID. 
	 * All pets must have an ID
	 * @return the PetID object
	 */
	PetID getPetID();
	

	/**
	 * Returns the pet type. 
	 * a pet can be either a cat or a dog
	 * @return a string (cat or dog)
	 */
	String getPetType();

	
	 /**
     * Returns a boolean indicating whether or not the pet is adopted.     *
     * @return true if pet is adopted and false otherwise
     */
	boolean getAdopted();

	/**
	 * Returns a string "adopted" or "not adopted" dependent on whether or not the pet is adopted.
	 * @return
	 */
	String getAdoptedString();

	/**
	 * Sets adoption status of the pet.
	 * @param adopted is the desired adoption status
	 */
	void setAdopted(boolean adopted);
	
	 /**
     * Returns a String indicating the care instructions.     *
     * @return a string
     */
	String getCareInstructions();

	/**
	 * Sets specified care instructions.
	 * @param careInstructions specified instructions
	 */
	void setCareInstructions(String careInstructions);

}
