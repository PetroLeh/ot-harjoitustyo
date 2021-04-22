package trackerapp.domain;

import javafx.scene.media.AudioClip;

/**
 *
 * @author lehtonep
 */
public class InstrumentObject implements TrackObject {

    private String id;
    private double volume;
    private AudioClip audio;

    public InstrumentObject(String id, AudioClip audio) {
        this(id, audio, 1.0);
    }
    
    public InstrumentObject(String id, AudioClip audio, double volume) {
        this.id = id;
        this.audio = audio;
        this.volume = volume;        
    }

    @Override
    public void activate() {
        System.out.println("aktivoitu: " + getId());        
        audio.play();        
    }

    @Override
    public String getId() {
        return id;
    }
}
