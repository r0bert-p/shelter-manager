import java.util.*;

/**
 * ShelterManager class - implementation of shelter management system maintainaning a TreeMap of all pets added to the shelter.
 * @version 1.0 Date created: 12/02/2024
 * @author Robert Petecki
 */

public class ShelterManager {
	/**
	 * TreeMap for maintaining sorted record (by PetID) of all pets added to the shelter.
	 */
	Map<PetID, Pet> shelterAllPets = new TreeMap<PetID, Pet>();

	/**
	 * LinkedList for maintaining record of pets available for adoption, updated with method updateListAvailablePets().
	 */
	List<Pet> shelterAvailablePets = new LinkedList<Pet>();

	/**
	 * TreeSet for maintaining ordered record of all existing customer records (by CustomerNumber).
	 */
	Set<CustomerRecord> shelterAllCustomerRecords = new TreeSet<CustomerRecord>();

	/**
	 * HashMap for maintaining record of the existing customer numbers and the list of their adopted pets
	 */
	Map<CustomerNumber, List<Pet>> shelterAdoptionsRecord = new HashMap<CustomerNumber, List<Pet>>();


	/**
	 * Adds a new pet of the specified type <code>petType</code> to the shelter and allocates it a pet ID.
	 *
	 * @param petType is the type of pet to be added, must be String "dog" or "cat" (case-insensitive)
	 * @return the Pet object if successful
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public Pet addPet(String petType) throws IllegalArgumentException, NullPointerException {
		if (!petType.toLowerCase().equals("cat") && !petType.toLowerCase().equals("dog")) //valid petType parameters, case-insensitive
			throw new IllegalArgumentException("Invalid pet type. Pet was not added."); //exception message for invalid pet type

		//code below reached only if petType parameter is valid
		Pet addedPet = PetFactory.getPetInstance(petType); //using factory method to create a unique Pet instance

		if (addedPet.equals(null)) //if Pet object was not successfully created
			throw new NullPointerException("Pet object was not created. Pet was not added."); //exception message

		//code below reached only if Pet object was successfully created
		shelterAllPets.put(addedPet.getPetID(), addedPet); //add pet to shelter register
		updateListAvailablePets(); //newly added pet is "not adopted" so update shelter list of available pets

		return addedPet;
	}

	/**
	 * Updates the training status of an existing pet depending on its PetID (must be a dog).
	 *
	 * @param petID   is a PetID of the Pet to update, each Pet has their own unique PetID
	 * @param trained is a Boolean indicating desired trained status of the Pet
	 * @return true if successful update, false otherwise
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public Boolean updatePetRecord(PetID petID, Boolean trained) throws IllegalArgumentException, NullPointerException {
		if (petID.equals(null)) //validate petID parameter
			throw new NullPointerException("PetID cannot be null, must have a value. Pet record was not updated."); //exception message

		//code below reached only if petID parameter has a value
		boolean updateStatus = false; //update operation status

		Pet petToUpdate = shelterAllPets.get(petID);//retrieve Pet object with the PetID specified in parameter, from shelter register


		//conditionals validating pet entry exists in the register, and if it can be trained (must be dog)
		if (petToUpdate != null && petToUpdate.getPetType().equals("Dog")) //validate Pet is in the register and is a dog
		{
			((Dog) petToUpdate).setTrained(trained); //update trained status as specified in parameter

			shelterAllPets.put(petID, petToUpdate); //update pet in shelter register

			//check if update was successful
			petToUpdate = shelterAllPets.get(petID); //get register entry, expected updated Pet
			updateStatus = ((Dog) petToUpdate).getTrained() == trained;//true if successful update
			updateListAvailablePets(); //update shelter list of available pets
			return updateStatus;
		} else if (petToUpdate == null) //exceptional case, Pet object cannot be retrieved from shelter register depending on PetID
		{
			throw new IllegalArgumentException("Pet with PetID " + petID + " could not be retrieved from the shelter register. Pet record was not updated.");
		} else if (!petToUpdate.getPetType().equals("Dog")) //exceptional case, pet specified in parameter is not a dog
		{
			throw new IllegalArgumentException("Pet with PetID " + petID + " is not a dog and cannot be trained. Pet record was not updated.");
		}
		return false;
	}

	/**
	 * Returns the number of pets of the specified type that are not adopted.
	 *
	 * @param petType specifies the pet type of interest
	 * @return int indicating the number of available pet of the specified type (available)
	 */
	public int noOfAvailablePets(String petType) {
		int countPets = 0; //initialise counter to 0
		petType = petType.toLowerCase(); //ensuring case-insensitive pet type parameter

		for (Pet pet : shelterAllPets.values()) {
			if (pet.getPetType().toLowerCase().equals(petType) && !pet.getAdopted()) //checking pet type match and adopted status == false
			{
				countPets++;
			}
		}
		return countPets;
	}

	/**
	 * Creates a customerRecord with today's date of issue, ensuring customers firstName, lastName, and dob are unique for each customer.
	 *
	 * @param firstName first name of the customer
	 * @param lastName  last name of the customer
	 * @param dob       date of birth of the customer
	 * @param hasGarden indicates if customer has a garden
	 * @return customerRecord based on the given information if there is no record
	 * @throws IllegalArgumentException
	 * @throws Error
	 */
	public CustomerRecord addCustomerRecord(String firstName, String lastName, Date dob, Boolean hasGarden) throws IllegalArgumentException, Error {
		//Validate if parameter values are not null
		if (firstName == null || lastName == null || dob == null || hasGarden == null) //OR conditionals
			throw new IllegalArgumentException("Values passed in parameters cannot be null!"); //exception message

		//Validate if new customer is unique based on firstName, lastName, and dob
		CustomerRecord tempCustomerRecord = new CustomerRecord(firstName, lastName, dob); //temporary customer record
		boolean isUniqueCustomer = true; //local validation boolean, start as true meaning customer from parameters is unique
		for (CustomerRecord customerRecord : shelterAllCustomerRecords) //loop over customer records registered
		{
			if (tempCustomerRecord.equals(customerRecord))
				isUniqueCustomer = false; //true only if matching customer record is found (logical equivalence)
		}

		if (!isUniqueCustomer) //false meaning customer is not a unique customer
			throw new Error("Customer with similar information exists in shelter register"); //error if not a unique customer

		//Validated: code below reached if customer is unique based on firstName, lastName, and dob
		CustomerRecord addedCustomerRecord = CustomerRecordFactory.getCustomerRecordInstance(firstName, lastName, dob, hasGarden);

		if (shelterAllCustomerRecords.add(addedCustomerRecord)) //add to set, true if unique record added to the set, false otherwise
			return addedCustomerRecord; //method was successful

		//code below ignored if method was successful
		throw new IllegalArgumentException("Unsuccessful, customer record was not added. Customer record was not unique.");
	}

	/**
	 * Returns Boolean indicating whether adoption was successful or not, determined through eligibility criteria.
	 * If the customer can adopt the pet and there is a pet available in the shelter, it gives them a pet of the specified type (at random).
	 * It then adds this pet to the list of adopted pets by the customer (so that the shelter has a record of adopted pets and the customers adopting them).
	 * If a pet is adopted, its record should not be removed from the shelter. Only the status of the pet will change from “not adopted” to “adopted”.
	 * Determines:
	 * - whether the customer is eligible to adopt a pet of the specified type (see the rules below).
	 * The rules:
	 * - a customer cannot adopt more than three pets of all types (e.g. 2 Cats and a Dog)
	 * - if the available dog is a trained Dog, a customer must be at least 18 years old and have a garden to adopt it.
	 * - if the available dog is an untrained Dog, a customer must be at least 21 years old and have a garden to adopt it.
	 * - to adopt a uk.ac.ncl.csc8014.cw.factory.pet.Cat, a customer must be at least 18 years old.
	 *
	 * @param customerRecord customer record of the adopting customer
	 * @param petType        specified pet type desired for adoption
	 * @return true and a confirmation message with details if adoption successful, false and message with a reason otherwise
	 */
	public Boolean adoptPet(CustomerRecord customerRecord, String petType) {
		petType = petType.toLowerCase(); //ensuring case-insensitivity of method parameter
		//Customer age calculation, local variable
		int customerAge = customerRecord.ageCalculator(); //used for pet type adoption eligibility criteria

		//First check, adoption criteria: customer age must be at least 18 to progress with any adoption
		if (!getIs18(customerAge))
			return false; //adoption unsuccessful

		//Adoption criteria: pet type from parameter is available for adoption
		if (noOfAvailablePets(petType) == 0) {
			System.out.println("Adoption unsuccessful. There are no " + petType + "s available.");
			return false; //adoption unsuccessful
		}

		//List for storing individual adoptions record, used by subsequent methods
		List<Pet> customerIndividualAdoptionsRecord = shelterAdoptionsRecord.get(customerRecord.getCustomerNumber()); //assign individual customer adoption record found (or null) to the variable;

		//Adoption criteria: eligibility based on number of adoptions by the customer
		if (!getIsEligibleAdoptionNumber(customerIndividualAdoptionsRecord)) //check eligibility for adoption: number of adoptions, prints explanation
			return false; //method returns false if not eligible, the rest of method code ignored

		//Adoption: get available pets of pet type specified from parameters
		List<Pet> listAvailablePetType = shelterAvailablePets; //initialise to shelter list of available pets
		if (petType.equals("cat")) //for cat, age not needed for list of available pets
		{
			listAvailablePetType = getListAvailablePetType(petType);
		} else if (petType.equals("dog")) //for dog, age needed as per age eligibility requirement depending on dog trained status
		{
			listAvailablePetType = getListAvailablePetType(petType, customerAge); //list of dogs that customer is eligible for based on age
			if (listAvailablePetType.isEmpty()) {
				System.out.println("Adoption unsuccessful. There are no dogs available based on the customer's age eligibility.");
				return false; //adoption unsuccessful
			}
		}

		//Adoption: get a random pet from available pets assign to Pet object called adoptedPet
		Random random = new Random(); //random number generator object
		int randomIndex = random.nextInt(listAvailablePetType.size()); //random number with an exclusive bound of list size
		Pet adoptedPet = listAvailablePetType.get(randomIndex); //get a random pet from available pets, assign to Pet object

		//Adoption criteria: checking eligibility based on random pet from available pets
		if (!getIsEligibleAdoptionPet(customerAge, customerRecord.getHasGarden(), petType, adoptedPet))
			return false; //not eligible, adoption unsuccessful

		//Adoption: add newly adopted Pet to the individual adoption record list, update the shelter manager map maintaining individual adoption records
		adoptedPet.setAdopted(true);
		shelterAllPets.put(adoptedPet.getPetID(), adoptedPet); //update adopted Pet's status from "not adopted" to "adopted"
		if (customerIndividualAdoptionsRecord == null) // if customer hasn't adopted before their individual adoption record is null
		{
			customerIndividualAdoptionsRecord = new ArrayList<Pet>();
		}
		customerIndividualAdoptionsRecord.add(adoptedPet); //updated individual adoption record
		CustomerNumber customerNumber = customerRecord.getCustomerNumber(); //customer number needed for updating Map of shelter adoption records
		shelterAdoptionsRecord.put(customerNumber, customerIndividualAdoptionsRecord); //updated Map

		//Post-adoption: update Pet's adopted status, and update shelter list of available pets
		updateListAvailablePets(); //update shelter list of available pets

		//Adoption successful: return adoption information
		System.out.println("Customer number " + customerRecord.getCustomerNumber() + " is adopting a " + petType + " with PetID: " + adoptedPet.getPetID());
		return true; //adoption successful
	}

	/**
	 * Returns unmodifiable collection of all pets currently adopted by the customer with the specified customer number.
	 *
	 * @param customerNumber specifies customer of interest
	 * @return unmodifiable Collection of all pets currently adopted by the specified customer
	 * @throws NullPointerException
	 */
	public Collection<Pet> adoptedPetsByCustomer(CustomerNumber customerNumber) throws NullPointerException {

		if (shelterAdoptionsRecord.get(customerNumber) == null) {
			return Collections.unmodifiableList(new ArrayList<>()); //unmodifiable empty list if no adoptions
		}
		return Collections.unmodifiableList(shelterAdoptionsRecord.get(customerNumber)); //unmodifiable
	}

	//Validator methods
	/**
	 * Validates if customer is at least 18 years old (must be at least 18 for any adoption).
	 * @param customerAge customer's age
	 * @return true if is at least 18, false otherwise
	 */
	private boolean getIs18(int customerAge) {
		if (customerAge < 18) {
			System.out.println("Customer needs to be at least 18 years old.");
			return false;
		}
		return true;
	}

	/**
	 * Validates if customer has adopted before.
	 * @param customerIndividualAdoptionsRecord individual adoptions record of a customer of interest
	 * @return true if has adopted before (individual adoptions record exists), false otherwise
	 */
	private boolean getHasAdoptedBefore(List<Pet> customerIndividualAdoptionsRecord) {
		if (customerIndividualAdoptionsRecord != null) //customer has adopted before, i.e. has adoptions record
			return true;
		//code below ignored if has adopted before
		System.out.println();
		return false; //has not adopted before, i.e. new adoptions record needs to be created
	}

	/**
	 * Validates if customer is eligible for adoption of the specified Pet.
	 * @param customerAge customer's age for checking age eligibility criteria depending on the petType and petForAdoption training status if is a dog
	 * @param hasGarden indicates if customer has a garden, eligibility criteria for dogs
	 * @param petType pet type of the pet to be adopted
	 * @param petForAdoption Pet to be adopted
	 * @return true if eligibility criteria met by customer and can adopt specified pet, false otherwise
	 * @throws IllegalArgumentException if specified pet type is invalid
	 */
	private boolean getIsEligibleAdoptionPet(int customerAge, boolean hasGarden, String petType, Pet petForAdoption) throws IllegalArgumentException {
		if (petType.toLowerCase().equals("cat")) //cat criteria: age
		{
			return customerAge >= 18;
		} else if (petType.toLowerCase().equals("dog")) //dog criteria: garden, age < 21 trained, age > 21 trained or not trained
		{
			//garden criteria
			if (hasGarden) //must have garden regardless of dog's training status
			{
				Dog dogForAdoption = (Dog) petForAdoption; //cast to dog

				//age-training criteria
				if (dogForAdoption.getTrained()) //age criteria based on training: trained == true then at least 18
				{
					return customerAge >= 18; //true if age eligible, false otherwise
				} else //age criteria based on training: trained == false then at least 21 years old
				{
					return customerAge >= 21; //true if age eligible, false otherwise
				}
			}
			System.out.println("Customer needs to have a garden to adopt a dog"); //garden criteria message for dog adoption
			return false; //not eligible
		}
		throw new IllegalArgumentException("Invalid pet type."); //exception, pet type in parameters is invalid
	}

	/**
	 * Validates if a customer can adopt more pets (cannot adopt more than 3 pets of all types).
	 * @param customerIndividualAdoptionsRecord individual adoptions record of a customer that will be checked
	 * @return true if customer can adopt more pets (adopted < 3 pets), false otherwise
	 */
	private boolean getIsEligibleAdoptionNumber(List<Pet> customerIndividualAdoptionsRecord) {
		if (!getHasAdoptedBefore(customerIndividualAdoptionsRecord)) //check if customer has adopted before
			return true; //hasn't adopted before so number of adoptions irrelevant to check further
		//adoption eligibility criteria: a customer can adopt at most three pets of all types
		if (customerIndividualAdoptionsRecord.size() == 3) //cannot adopt more than three pets of all types
		{
			System.out.println("Customer cannot adopt more than three pets (of all types)."); //explanation for eligibility failure
			return false;
		}
		return true; //reached only if can adopt
	}

	//Available pets methods
	/**
	 * Updates List of available pets in the shelter based on pet's adoption status (available means "not adopted")
	 */
	private void updateListAvailablePets() {
		List<Pet> tempShelterAvailablePets = new ArrayList<Pet>();
		for (Pet pet : shelterAllPets.values()) {
			if (!pet.getAdopted()) //checking adopted status == false
			{
				tempShelterAvailablePets.add(pet);
			}
			shelterAvailablePets = tempShelterAvailablePets; //reassign list to the updated one
		}
	}

	/**
	 * Returns a List of available pet of the specified pet type.
	 * @param petType specified pet type
	 * @return ArrayList of available pets of the specified pet type
	 */
	private List<Pet> getListAvailablePetType(String petType) {
		List<Pet> listAvailablePetType = new ArrayList<Pet>(); //ArrayList of available pets of the specified type, later returned
        for (Pet petIteration : shelterAvailablePets) { //loop through shelterAvailablePets LinkedList
            String petTypeIteration = petIteration.getPetType().toLowerCase(); //ensuring case-insensitive pet type of pet
            if (petTypeIteration.equals(petType.toLowerCase())) //ensuring case-insensitive pet type of parameter
            {
                listAvailablePetType.add(petIteration); //add pet iteration if pet type matched to the specified pet type
            }
        }
		return listAvailablePetType; //ArrayList with available pets of the specified type
	}

	/**
	 * Returns a List of available pets of the specified pet types that a customer with the specified age could be eligible for.
	 * @param petType specified pet type
	 * @param customerAge age of a customer of interest
	 * @return ArrayList of Pets that a customer of the specified age could be eligible for
	 */
	//overloading
	private List<Pet> getListAvailablePetType(String petType, int customerAge) //use only for Dog!!
	{
		List<Pet> listAvailablePetType = new ArrayList<Pet>();
		for (int i = 0; i < shelterAvailablePets.size(); i++) {
			Pet petIteration = shelterAvailablePets.get(i);
			String petTypeIteration = petIteration.getPetType().toLowerCase(); //ensuring case-insensitive pet type of pet
			if (petTypeIteration.equals(petType.toLowerCase())) //ensuring case-insensitive pet type of parameter
			{
				listAvailablePetType.add(petIteration);
			}
		}
		//depending on age filter dogs based on trained status
		if (customerAge < 21) //dogs must be trained
		{
			for (int i = 0; i < listAvailablePetType.size(); i++) {
				Pet petIteration = listAvailablePetType.get(i);
				if (!((Dog) petIteration).getTrained())
					listAvailablePetType.remove(petIteration);
			}
		}
		return listAvailablePetType;
	}
}