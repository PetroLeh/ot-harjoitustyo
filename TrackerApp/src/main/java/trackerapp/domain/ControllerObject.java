
package trackerapp.domain;

/**
 *
 * @author lehtonep
 */
public class ControllerObject implements TrackObject {
    private String id;
    
    @Override
    public void activate (Controller c) {
        System.out.println("Aktivoitu: " + getId());
    }
    
    @Override public String getId() {
        return this.id;
    }
    
}
