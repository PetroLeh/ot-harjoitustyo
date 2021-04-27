package trackerapp.domain;

import java.util.ArrayList;

/**
 *
 * @author lehtonep
 */
public class Masterpiece {

    private String name;
    private int bpm;
    private ArrayList<TrackContainer> rows;

    public Masterpiece(String name, int bpm) {
        this.name = name;
        this.bpm = bpm;
        this.rows = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public String getName() {
        return name;
    }

    public int getBpm() {
        return bpm;
    }

    public boolean addObject(int row, int track, TrackObject object) {
        if (row >= 0 && row < rows.size()) {
            return rows.get(row).addObject(track, object);
        }
        return false;
    }

    public void addRow(int tracks) {
        rows.add(new TrackContainer(tracks));
    }

    public void removeRow() {
        if (size() > 0) {
            rows.remove(size() - 1);
        }
    }

    public TrackContainer getTrackContainer(int row) {
        if (row >= 0 && row < rows.size()) {
            return rows.get(row);
        }
        return null;
    }

    public ArrayList<TrackContainer> getAllTrackContainers() {
        return rows;
    }

    public int size() {
        return rows.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
