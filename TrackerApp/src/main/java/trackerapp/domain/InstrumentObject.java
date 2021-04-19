
package trackerapp.domain;

/**
 *
 * @author lehtonep
 */
public class InstrumentObject implements TrackObject {
    private String id;
    private int volume;
    
    public InstrumentObject(String id) {
        this.id = id;        
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
