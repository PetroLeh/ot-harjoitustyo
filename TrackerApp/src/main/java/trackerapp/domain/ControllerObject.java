package trackerapp.domain;

/**
 *
 * @author lehtonep
 */
public class ControllerObject implements TrackObject {

    private String id;

    @Override
    public void activate() {
        System.out.println("Aktivoitu: " + getId());
    }

    @Override
    public String getId() {
        return id;
    }

}
