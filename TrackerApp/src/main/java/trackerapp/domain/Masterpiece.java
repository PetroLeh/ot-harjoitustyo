
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
        return this.name;
    }
    
    public int getBpm() {
        return this.bpm;
    }
    
    public void addRow(int tracks) {
       this.rows.add(new TrackContainer(tracks));
    }    
        
    public TrackContainer getTrackContainer(int row) {
        if (row >= 0 && row < this.rows.size()) {
            return this.rows.get(row);
        }
        return null;
    }
    
    public ArrayList<TrackContainer> getAllTrackContainers() {
        return this.rows;
    }
    
    public int size() {
        return this.rows.size();
    }
}
