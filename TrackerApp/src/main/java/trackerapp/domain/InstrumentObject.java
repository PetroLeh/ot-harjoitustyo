package trackerapp.domain;

import javafx.scene.media.AudioClip;

/**
 *
 * @author lehtonep
 */
public class InstrumentObject implements TrackObject {

    private String id, instrument, plainId;
    private AudioClip audio;

    public InstrumentObject(String id, AudioClip audio) {
        this.id = id;
        this.audio = audio;
    }

    @Override
    public void activate() {
        audio.play();
    }

    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return id;
    }

}
