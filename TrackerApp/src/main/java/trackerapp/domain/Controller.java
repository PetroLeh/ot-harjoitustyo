
package trackerapp.domain;

/**
 *
 * @author lehtonep
 */
public class Controller {
    private Masterpiece masterpiece;
        
    private int currentTempo, masterVolume, currentRow, nextRow;
    private boolean playing;
    
    public Controller(Masterpiece masterpiece) {        
        this.masterpiece = masterpiece;
        this.playing = false;
        this.currentRow = 0;
    }
    
    public void play() {
        this.playing = true;
    }
    
    public void stop() {
        this.playing = false;
    }
    
    public void pause() {
        this.playing = !this.playing;
    }
    
    public int getNextRow() {
        return this.nextRow;
    }
    
    public int getMasterVolume() {
        return this.masterVolume;
    }
    
    public boolean isPlaying() {
        return playing;
    }
    
    public boolean activateTrackContainer(int row) {
        TrackContainer tc = masterpiece.getTrackContainer(row);
        if (tc == null) {
            return false;
        }
        for (TrackObject object : tc.getObjects()) {
            if(object != null) {
                object.activate(this);
            }
        }
        return true;
    }
    
}
