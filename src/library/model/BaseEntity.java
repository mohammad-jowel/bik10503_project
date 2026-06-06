package library.model;

// Base class providing common ID functionality for entities
public abstract class BaseEntity {
    private String id;

    // Constructor to initialize entity ID
    public BaseEntity(String id) {
        this.id = id;
    }

    // Getter for the entity ID
    public String getId() {
        return id;
    }

    // Setter for the entity ID
    public void setId(String id) {
        this.id = id;
    }
}
