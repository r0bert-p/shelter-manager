import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test ShelterManager class - testing methods of the ShelterManager class.
 * @version 1.0 Date created: 06/03/2024
 * @author Robert Petecki
 */
class ShelterManagerTest {

    /**
     * Testing addPet() method.
     * @see ShelterManager#addPet(String)
     * Normal case: adding a Cat.
     * Boundary cases: adding 3 cats and 3 dogs with different capitalisation of parameter String.
     * Exceptional case: invalid pet type.
     */
    @Test
    void testAddPet() {
        //Normal case: adding a Cat
        var sm = new ShelterManager(); //shelter manager object initialised, empty shelter

        Pet addedPet = sm.addPet("Cat"); //addedPet is the Pet object returned by the addPet method

        String expectedPetType = "Cat"; //expected petType of added Pet specified in addPet parameter
        String actualPetType = addedPet.getPetType(); //actual petType of the added Pet
        PetID actualPetID = addedPet.getPetID(); //actual PetID of the added Pet

        assertNotNull(addedPet); //assert Pet object was returned, not null
        assertEquals(expectedPetType, actualPetType); //assert Pet object has specified petType
        assertNotNull(actualPetID); //assert PetID was allocated to the added Pet, not null
        assertNotNull(sm.shelterAllPets); //assert added Pet was added to the shelter record, not null


        //Boundary cases: adding 3 cats and 3 dogs with different capitalisation of parameter String
        sm = new ShelterManager(); //empty shelter
        //cats
        Pet addedCat1 = sm.addPet("cat"); //adding cats
        Pet addedCat2 = sm.addPet("CaT");
        Pet addedCat3 = sm.addPet("CAt");
        //dogs
        Pet addedDog1 = sm.addPet("dog"); //adding dogs
        Pet addedDog2 = sm.addPet("DoG");
        Pet addedDog3 = sm.addPet("DOG");
        //expected
        String expectedPetTypeCat = "Cat"; //expected petType is Cat
        String expectedPetTypeDog = "Dog"; //expected petType is Dog
        //assert
        assertNotNull(addedCat1); //assert Pet object was returned for all method calls, cats
        assertNotNull(addedCat2);
        assertNotNull(addedCat3);
        assertNotNull(addedDog1); //dogs
        assertNotNull(addedDog2);
        assertNotNull(addedDog3);

        assertEquals(expectedPetTypeCat, addedCat1.getPetType()); //assert Pet object has specified petType, cats
        assertEquals(expectedPetTypeCat, addedCat2.getPetType());
        assertEquals(expectedPetTypeCat, addedCat3.getPetType());
        assertEquals(expectedPetTypeDog, addedDog1.getPetType()); //dogs
        assertEquals(expectedPetTypeDog, addedDog2.getPetType());
        assertEquals(expectedPetTypeDog, addedDog3.getPetType());

        assertNotNull(addedCat1.getPetID()); //assert PetID was allocated to the added Pet, not null, cats
        assertNotNull(addedCat2.getPetID());
        assertNotNull(addedCat3.getPetID());
        assertNotNull(addedDog1.getPetID()); //dogs
        assertNotNull(addedDog2.getPetID());
        assertNotNull(addedDog3.getPetID());

        assertNotNull(sm.shelterAllPets); //assert added Pet objects wer added to the shelter record, not null
        assertEquals(6, sm.shelterAllPets.size()); //assert all 6 Pet objects were added to shelter record


        //Exceptional case: invalid pet type
        sm = new ShelterManager(); //empty shelter

        try
        {
            Pet addedPetInvalid = sm.addPet("parrot"); //addedPetInvalid should be assigned null

            String actualPetInvalidType = addedPetInvalid.getPetType(); //actual petType
            PetID actualPetInvalidID = addedPetInvalid.getPetID(); //actual PetID

            assertNull(addedPetInvalid); //assert Pet object was null for invalid petType
            assertEquals(null, addedPetInvalid); //assert Pet object has no petType, null
            assertNull(actualPetInvalidID); //assert PetID was not allocated, null
            assertNull(sm.shelterAllPets); //assert Pet was not added to shelter record, null
        }
        catch (Throwable t)  //t can be either IllegalArgumentException or NullPointerException
        {
            try
            {
                uk.ac.ncl.teach.ex.test.Assertions.assertExpectedThrowable(IllegalArgumentException.class, t); //assert exception expected IllegalArgumentException thrown
            }
            catch (AssertionError assertionError) //catch error thrown by assertExpectedThrowable, means t is not IllegalArgument exception
            {
                uk.ac.ncl.teach.ex.test.Assertions.assertExpectedThrowable(NullPointerException.class, t); //assert other expected NullPointerException thrown
            }
        }
    }


    /**
     * Testing updatePetRecord().
     * @see ShelterManager#updatePetRecord(PetID, Boolean)
     * Normal case: updating a record of a dog, from "not trained" to "trained".
     * Boundary cases: 1- updating trained status to the same value as it is, 2- updating from true to false, 3- updating a cat
     * Exceptional cases: 1- passing a null PetID, 2- passing a non-existing PetID
     */
    @Test
    void testUpdatePetRecord() {
        var sm = new ShelterManager(); //shelter manager object initialised, empty shelter
        Pet addedCat = sm.addPet("Cat"); //Adding a cat to the shelter
        Pet addedDog = sm.addPet("Dog"); //Adding a dog to the shelter

        //Normal case: updating a record of a dog, from "not trained" to "trained"
        boolean actualUpdate = sm.updatePetRecord(addedDog.getPetID(), true); //true if update successful
        Pet updatedDog = sm.shelterAllPets.get(addedDog.getPetID()); //expected trained status: true
        uk.ac.ncl.teach.ex.test.Assertions.assertTrue(actualUpdate); //assert update returned true
        uk.ac.ncl.teach.ex.test.Assertions.assertTrue(((Dog) updatedDog).getTrained()); //assert pet was updated in shelter register

        //Boundary cases: updating trained status to the same value as it is, updating from true to false, updating a cat
        //1
        actualUpdate = sm.updatePetRecord(updatedDog.getPetID(), true); //updating to the same status
        uk.ac.ncl.teach.ex.test.Assertions.assertTrue(actualUpdate); //assert update returned true
        uk.ac.ncl.teach.ex.test.Assertions.assertTrue(((Dog) updatedDog).getTrained()); //assert pet was updated in shelter register
        //2
        actualUpdate = sm.updatePetRecord(updatedDog.getPetID(), false); //updating from true to false
        uk.ac.ncl.teach.ex.test.Assertions.assertTrue(actualUpdate); //assert update returned true
        uk.ac.ncl.teach.ex.test.Assertions.assertFalse(((Dog) updatedDog).getTrained()); //assert pet was updated in shelter register
        //3
        try
        {
            actualUpdate = sm.updatePetRecord(addedCat.getPetID(), true); //updating a cat, expected exception
        }
        catch (Throwable t)
        {
            uk.ac.ncl.teach.ex.test.Assertions.assertExpectedThrowable(IllegalArgumentException.class, t); //assert exception expected, IllegalArgumentException thrown
        }

        //Exceptional cases: passing a null PetID, passing a non-existing PetID
        PetID nullPetID = null; //null PetID for testing
        PetID nonExistingPetID = PetID.getPetIDInstance(); //non-existing PetID for testing
        //1
        try //null PetID case
        {
            actualUpdate = sm.updatePetRecord(nullPetID, true); //expected NullPointerException
            uk.ac.ncl.teach.ex.test.Assertions.assertFalse(actualUpdate); //not reached
        }
        catch (Throwable t)
        {
            uk.ac.ncl.teach.ex.test.Assertions.assertExpectedThrowable(NullPointerException.class, t); //assert exception expected, NullPointerException
        }
        //2
        try //non-existing PetID case
        {
            actualUpdate = sm.updatePetRecord(nonExistingPetID, true); //expected IllegalArgumentException
            uk.ac.ncl.teach.ex.test.Assertions.assertFalse(actualUpdate); //not reached
        }
        catch (Throwable t)
        {
            uk.ac.ncl.teach.ex.test.Assertions.assertExpectedThrowable(IllegalArgumentException.class, t); //assert exception expected, IllegalArgumentException thrown
        }
    }

    /**
     * Testing noOfAvailablePets() method.
     * @see ShelterManager#noOfAvailablePets(String)
     * Normal case: number of available cats, expected 2
     * Boundary cases: 1- empty shelter, 2- parameter capitalisation, 3- one type of pet in the shelter
     * Exceptional cases: invalid pet type
     */
    @Test
    void testNoOfAvailablePets() {
        var sm = new ShelterManager(); //shelter manager object initialised, empty shelter
        Pet addedCat1 = sm.addPet("Cat"); //Adding cats to the shelter
        Pet addedCat2 = sm.addPet("Cat");
        Pet addedDog = sm.addPet("Dog"); //Adding a dog to the shelter

        //Normal case: number of available cats (2)
        int actual = sm.noOfAvailablePets("Cat"); //expected 2
        assertEquals(2, actual); //assert expected 0

        //Boundary cases: empty shelter, parameter capitalisation, one type of pet in the shelter
        sm = new ShelterManager(); //empty shelter
        //1
        actual = sm.noOfAvailablePets("Cat"); //expected 0
        assertEquals(0, actual); //assert expected 0
        //2
        sm.addPet("Cat"); //add 1 cat to shelter
        actual = sm.noOfAvailablePets("CaT"); //parameter capitalisation, expected 1
        assertEquals(1, actual); //assert expected 1
        //3
        actual = sm.noOfAvailablePets("dOg"); //one pet type in shelter, parameter capitalisation
        assertEquals(0, actual); //assert expected 0

        //Exceptional cases: invalid pet type
        actual = sm.noOfAvailablePets("parrot"); //invalid pet type, expected 0
        assertEquals(0, actual); //assert expected 0
    }

    /**
     * Testing addCustomerRecord() method.
     * @see ShelterManager#addCustomerRecord(String, String, Date, Boolean)
     * Normal case: creating two customer records for different people
     * Boundary case: existing customer
     * Exceptional case: passing null parameters
     */
    @Test
    void testAddCustomerRecord() {
        var sm = new ShelterManager(); //shelter manager object initialised, empty shelter

        Calendar calendar1 = Calendar.getInstance(); //calendar object
        calendar1.set(1990, 1,20 ); //calendar date is 20th February 1990
        Date dob1 = calendar1.getTime(); //create a Date object for date of birth, using calendar object set above
        calendar1.set(1991, 0, 1); //calendar date is 1st January 1991
        Date dob2 = calendar1.getTime(); //another Date object for date of birth, date above

        //Normal case: creating two customer records for different people
        CustomerRecord cr1 = sm.addCustomerRecord("Jan", "Kowalski", dob1, true);//create customer records
        CustomerRecord cr2 = sm.addCustomerRecord("Anna", "Smith", dob1, true);
        assertNotEquals(cr1, cr2); //assert two different objects
        assertTrue(sm.shelterAllCustomerRecords.contains(cr1)); //assert customer records were added to shelter register
        assertTrue(sm.shelterAllCustomerRecords.contains(cr2));

        //Boundary case: existing customer
        try
        {
            CustomerRecord cr3 = sm.addCustomerRecord("Jan", "Kowalski", dob1, false); //logically equivalent to cr1
        }
        catch(Error e)
        {
            uk.ac.ncl.teach.ex.test.Assertions.assertExpectedThrowable(Error.class, e); //assert error expected, Error
        }

        //Exceptional case: passing null parameters
        try
        {
            CustomerRecord cr4 = sm.addCustomerRecord(null, null, null, null); //passing null values
        }
        catch (Throwable t)
        {
            uk.ac.ncl.teach.ex.test.Assertions.assertExpectedThrowable(IllegalArgumentException.class, t); //assert exception expected, IllegalArgumentException
        }
    }

    /**
     * Testing adoptPet() method.
     * @see ShelterManager#adoptPet(CustomerRecord, String)
     * Normal case: customer adopts an untrained dog, eligible customer
     * Boundary cases: 1- no pets available, 2- customer wants to adopt a trained dog but not eligible, 3- customer adopted 3 pets already
     * Exceptional case: empty shelter
     */
    @Test
    void testAdoptPet() {
        System.out.println("\nTesting adoptPet():");
        //setup
        var sm = new ShelterManager(); //shelter manager object initialised, empty shelter
        Calendar calendar1 = Calendar.getInstance(); //calendar object
        calendar1.set(1990, 1,20 ); //calendar date is 20th February 1990
        Date dob1 = calendar1.getTime(); //create a Date object for date of birth, using calendar object set above
        CustomerRecord cr1 = sm.addCustomerRecord("Jan", "Kowalski", dob1, true);//create customer records
        Pet addedDog1 = sm.addPet("Dog"); //adding a dog

        //Normal case: customer adopts an untrained dog, eligible customer
        System.out.println("Normal case, customer adopts an untrained dog, is eligible:");
        assertTrue(sm.adoptPet(cr1, "Dog")); //assert expected true, successful adoption
        assertTrue(sm.adoptedPetsByCustomer(cr1.getCustomerNumber()).size() == 1); //assert expected true, adoption appears in customer individual adoption record
        assertTrue(sm.adoptedPetsByCustomer(cr1.getCustomerNumber()).contains(addedDog1)); //assert expected true, adopted dog in customers individual adoption record
        assertTrue(sm.shelterAllPets.get(addedDog1.getPetID()).getAdopted()); //assert expected true, adoption status changed

        //Boundary cases: no pets available; customer wants to adopt a trained dog but not eligible; customer adopted 3 pets already
        //1
        System.out.println("\nBoundary case 1, no pets available:");
        calendar1.set(2004, 0, 1); //calendar date is 1st January 2004
        Date dob2 = calendar1.getTime(); //another Date object for date of birth, date above
        CustomerRecord cr2 = sm.addCustomerRecord("Anna", "Smith", dob2, true); //new customer with age <21 years old
        assertFalse(sm.adoptPet(cr2, "Cat")); //assert expected false, unsuccessful adoption due to no pets available
        //2
        System.out.println("\nBoundary case 2, no pets available: customer wants to adopt a trained dog but not eligible:");
        Pet addedDog2 = sm.addPet("Dog"); //new dog in shelter, only available and untrained
        assertFalse(sm.adoptPet(cr2, "Dog")); //assert expected false, unsuccessful adoption due to age
        //3
        System.out.println("\nBoundary case 3, customer adopted 3 pets already:");
        Pet addedCat1 = sm.addPet("Cat"); //adding cats
        Pet addedCat2 = sm.addPet("Cat");
        assertTrue(sm.adoptPet(cr1, "Cat")); //cr1, adoption no.2
        assertTrue(sm.adoptPet(cr1, "Cat")); //cr1, adoption no.3, no more allowed after this
        assertFalse( sm.adoptPet(cr1, "Dog"));//cr1, attempt of adoption no.4
        assertTrue(sm.adoptedPetsByCustomer(cr1.getCustomerNumber()).size() == 3); //assert cr1 adopted 3 pets

        //Exceptional case: empty shelter
        System.out.println("\nExceptional case, empty shelter:");
        sm = new ShelterManager();
        CustomerRecord cr4 = sm.addCustomerRecord("Jan", "Kowalski", dob1, true);
        assertFalse(sm.adoptPet(cr4, "Cat")); //assert expected false, cannot adopt
    }

    /**
     * Testing adoptedPetsByCustomer() method.
     * @see ShelterManager#adoptedPetsByCustomer(CustomerNumber)
     * Normal case: customer has adopted one pet
     * Boundary cases: 1- customer has not adopted yet, 2- customer has adopted multiple pets
     * Exceptional case: attempt to modify the unmodifiable collection
     */
    @Test
    void testAdoptedPetsByCustomer() {
        //setup
        var sm = new ShelterManager(); //shelter manager object initialised, empty shelter
        Calendar calendar1 = Calendar.getInstance(); //calendar object
        calendar1.set(1990, 1,20 ); //calendar date is 20th February 1990
        Date dob1 = calendar1.getTime(); //create a Date object for date of birth, using calendar object set above
        CustomerRecord cr1 = sm.addCustomerRecord("Jan", "Kowalski", dob1, true);//create customer records
        Pet addedDog1 = sm.addPet("Dog"); //adding a dog
        Pet randomDog = PetFactory.getPetInstance("Dog");

        //Normal case: customer has adopted one pet
        sm.adoptPet(cr1, "Dog"); //adoption
        Collection<Pet> actualCollection = sm.adoptedPetsByCustomer(cr1.getCustomerNumber()); //assign unmodifiable collection
        assertTrue(actualCollection.contains(addedDog1)); //assert expected true, pets currently adopted by customer

        //Boundary cases: customer has not adopted yet, customer has adopted multiple pets
        //1
        CustomerRecord cr2 = sm.addCustomerRecord("Anna", "Smith", dob1, true);//create customer records
        assertTrue(sm.adoptedPetsByCustomer(cr2.getCustomerNumber()).isEmpty()); //assert expected true, empty Collection if not adopted yet

        //2
        System.out.println("Boundary case, customer has adopted multiple pets");
        Pet addedDog2 = sm.addPet("Dog"); //adding a dog
        Pet addedCat1 = sm.addPet("Cat"); //adding a cat
        sm.adoptPet(cr1, "Dog"); //cr1 adopts two more pets
        sm.adoptPet(cr1, "Cat");
        System.out.println("Unmodifiable collection: " + sm.adoptedPetsByCustomer(cr1.getCustomerNumber()));
        Collection<Pet> expectedCollection = sm.shelterAdoptionsRecord.get(cr1.getCustomerNumber()); //collection expected
        assertTrue(sm.adoptedPetsByCustomer(cr1.getCustomerNumber()).containsAll(expectedCollection)); //assert expected true, Collection contains all adopted pets


        //Exceptional case: attempt to modify the unmodifiable collection
        Collection<Pet> testCollection = actualCollection; //this is to attempt modification of unmodifiable collection
        try
        {
            testCollection.add(randomDog); //attempt to modify
        }
        catch (Throwable t)
        {
            uk.ac.ncl.teach.ex.test.Assertions.assertExpectedThrowable(UnsupportedOperationException.class, t); //assert exception expected, UnsupportedOperationException
        }
   }
}