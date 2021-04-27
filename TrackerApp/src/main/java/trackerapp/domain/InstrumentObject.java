package trackerapp.domain;

import javafx.scene.media.AudioClip;

/**
 *
 * @author lehtonep
 */
public class InstrumentObject implements TrackObject {

    private String id;
    private AudioClip audio;

    public InstrumentObject(String id, AudioClip audio) {
        this.id = id;
        this.audio = audio;
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
