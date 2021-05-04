package trackerapp.domain;

import java.util.ArrayList;

/**
 *
 * @author lehtonep
 */
public class Chord implements TrackObject {

    private String id;
    private ArrayList<InstrumentObject> inChord;

    public Chord() {
        this.id = "";
        inChord = new ArrayList<>();
    }

    @Override
    public void activate() {
        inChord.forEach(InstrumentObject :: activate);
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void addToChord(InstrumentObject o) {
        if (o == null || this.contains(o)) {
            return;
        }
        inChord.add(o);
    }

    public void removeFromChord(InstrumentObject o) {
        if (o == null || !this.contains(o)) {
            return;
        }
        InstrumentObject toRemove = null;
        for (InstrumentObject i : inChord) {
            if (i.getId().equals(o.getId())) {
                toRemove = i;
            }
        }
        if (toRemove != null) {
            inChord.remove(toRemove);
        }
    }

    public boolean contains(InstrumentObject o) {
        if (o == null) {
            return false;
        }
        for (InstrumentObject i : inChord) {
            if (i.getId().equals(o.getId())) {
                return true;
            }
        }
        return false;
    }

}
