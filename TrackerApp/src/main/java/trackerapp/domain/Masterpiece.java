
package trackerapp.domain;

import java.util.ArrayList;

/**
 *
 * @author lehtonep
 */
public class Masterpiece {
    private String name;
    private int tempo;
    private ArrayList<TrackContainer> rows;
    
    public Masterpiece() {
        this.name = "nimetön";
        this.tempo = 120;
        this.rows = new ArrayList<>();        
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
    
    public int size() {
        return this.rows.size();
    }
}
