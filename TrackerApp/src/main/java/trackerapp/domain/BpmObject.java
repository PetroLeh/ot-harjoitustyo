package trackerapp.domain;

/**
 *
 * @author lehtonep
 */
public class BpmObject implements TrackObject {

    private String id;
    private TrackerService tracker;
    private int bpm;

    public BpmObject(TrackerService tracker) {
        this(tracker, 120, "(bpm): ");
    }

    public BpmObject(TrackerService tracker, int bpm) {
        this(tracker, bpm, "(bpm): ");
    }

    public BpmObject(TrackerService tracker, int bpm, String id) {
        this.tracker = tracker;
        this.bpm = bpm;
        this.id = id;
    }

    @Override
    public void activate() {
        tracker.setBpm(bpm);
        System.out.println("Aktivoitu: " + getId());
    }

    @Override
    public String getId() {
        return id + bpm;
    }

}
