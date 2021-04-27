package trackerapp.domain;

/**
 *
 * @author lehtonep
 */
public enum ObjectType {
    EMPTY(" . . . ");

    private String value;

    private ObjectType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
