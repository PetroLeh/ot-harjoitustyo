
package trackerapp.domain;

/**
 *
 * @author lehtonep
 */
public class InstrumentObject implements TrackObject {
    private String id;
    private int volume;
    
    public InstrumentObject(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }
    
    @Override
    public void activate(Controller c) {
        System.out.println("Aktivoitu: " + getId());
    }
    
    @Override
    public String getId() {
        return this.id;
    }
}
