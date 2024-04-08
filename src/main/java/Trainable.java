/**
 * Trainable - interface to a trainable entity.
 * @version 1.0 Date created: 12/02/2024
 * @author Robert Petecki
 */
public interface Trainable {

    /**
     * Returns a boolean indicating whether or not the entity is trained.
     * @return true if pet is trained and false otherwise
     */
    boolean getTrained();

    /**
     * Returns a string informing whether or not the entity is trained.
     * @return "trained" or "not trained"
     */
    String getTrainedString();

    /**
     * Updates a boolean status informing whether or not the entity is trained.
     * @param trainedUpdate boolean parameter expressing desired update
     */
    void setTrained(boolean trainedUpdate);
}
