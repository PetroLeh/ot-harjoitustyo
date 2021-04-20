package trackerapp.domain;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import trackerapp.dao.MasterpieceDao;

/**
 *
 * @author lehtonep
 */
public class Controller {

    private Masterpiece masterpiece;
    private MasterpieceDao masterpieceDao;
    private Timer timer;

    private int currentBpm, masterVolume, currentRow, nextRow;
    private boolean playing;
    private Label infoLabel;
    
    public Controller(MasterpieceDao masterpieceDao) {
        this.masterpieceDao = masterpieceDao;
        this.playing = false;
        this.currentRow = 0;
        this.timer = new Timer(120);
    }

    public void play() {
        playing = true;
        updateInfoBar();
    }

    public void stop() {
        playing = false;
        currentRow = 0;
        updateInfoBar();
    }

    public void pause() {
        playing = !playing;
        updateInfoBar();
    }

    public int getNextRow() {
        return nextRow;
    }

    public int getMasterVolume() {
        return masterVolume;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setBpm(int bpm) {
        currentBpm = bpm;
        timer.setBpm(bpm);
    }
    
    public void setInfoBar(Label infoLabel) {
        this.infoLabel = infoLabel;
    }
    
    public void updateInfoBar() {
        infoLabel.setText(getInfo());
    }

    public void run() {
        if (playing) {
            if (timer.tick()) {
                activateTrackContainer(getCurrentRow());
                updateInfoBar();
                nextRow();
            }
        }
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void nextRow() {
        currentRow++;
        if (currentRow >= masterpiece.size()) {
            currentRow = 0;
        }
    }

    public String getInfo() {
        if (masterpiece != null) {
            String info = "mestariteos: " + masterpiece.getName() + "\t"
                    + "bpm: " + currentBpm + "\t"
                    + "rivejä: " + masterpiece.size() + "\t";
            if (playing) {
                info += "soitetaan (" + (currentRow + 1) + "): " + masterpiece.getTrackContainer(currentRow);
            } else {
                info += "pysäytetty";
            }
            return info;
        }
        return "";
    }

    public void setNewMasterpiece(int rows, int tracks) {
        masterpiece = masterpieceDao.getNewMasterpiece(rows, tracks);
        currentBpm = masterpiece.getBpm();
        currentRow = 0;
        timer.setBpm(currentBpm);
    }

    public void randomMasterpiece() {
        AudioClip audio_kattila = new AudioClip("file:audio/testi_kattila.wav");
        AudioClip audio_kansi = new AudioClip("file:audio/testi_kansi.wav");
        AudioClip audio_kansi2 = new AudioClip("file:audio/testi_kansi2.wav");

        AudioClip audio_kitara_a = new AudioClip("file:audio/testi_kitara_a.wav");
        AudioClip audio_kitara_c = new AudioClip("file:audio/testi_kitara_c.wav");
        AudioClip audio_kitara_e2 = new AudioClip("file:audio/testi_kitara_e2.wav");
        AudioClip audio_kitara_g2 = new AudioClip("file:audio/testi_kitara_g2.wav");
        AudioClip audio_kitara_a2 = new AudioClip("file:audio/testi_kitara_a2.wav");
        AudioClip audio_kitara_c2 = new AudioClip("file:audio/testi_kitara_c2.wav");
        AudioClip audio_kitara_e3 = new AudioClip("file:audio/testi_kitara_e3.wav");

        InstrumentObject kattila = new InstrumentObject("kattila", audio_kattila);
        InstrumentObject kansi = new InstrumentObject("kansi", audio_kansi);
        InstrumentObject kansi2 = new InstrumentObject("kansi2", audio_kansi2);

        InstrumentObject kitara_a = new InstrumentObject("(kitara) a", audio_kitara_a);
        InstrumentObject kitara_c = new InstrumentObject("(kitara) c", audio_kitara_c);
        InstrumentObject kitara_e2 = new InstrumentObject("(kitara) e2", audio_kitara_e2);
        InstrumentObject kitara_g2 = new InstrumentObject("(kitara) g2", audio_kitara_g2);
        InstrumentObject kitara_a2 = new InstrumentObject("(kitara) a2", audio_kitara_a2);
        InstrumentObject kitara_c2 = new InstrumentObject("(kitara) c2", audio_kitara_c2);
        InstrumentObject kitara_e3 = new InstrumentObject("(kitara) e3", audio_kitara_e3);

        Random r = new Random();
        for (int row = 0; row < masterpiece.size(); row++) {
            if (row > 0) {
                for (int track = 0; track < masterpiece.getTrackContainer(row).size(); track++) {
                    int n = r.nextInt(100);
                    if (n < 1) {
                        addObject(row, track, kattila);
                    } else if (n < 2) {
                        addObject(row, track, kansi);
                    } else if (n < 3) {
                        addObject(row, track, kansi2);
                    } else if (n < 10) {
                        addObject(row, track, kitara_a);
                    } else if (n < 17) {
                        addObject(row, track, kitara_e2);
                    } else if (n < 21) {
                        addObject(row, track, kitara_a2);
                    } else if (n < 24) {
                        addObject(row, track, kitara_c2);
                    } else if (n < 26) {
                        addObject(row, track, kitara_e3);
                    } else if (n < 30) {
                        addObject(row, track, kitara_c);
                    } else if (n < 34) {
                        addObject(row, track, kitara_g2);
                    }
                }
            }
        }
    }

    public boolean addObject(int row, int track, TrackObject object) {
        return masterpiece.addObject(row, track, object);
    }

    public boolean activateTrackContainer(int row) {
        TrackContainer tc = masterpiece.getTrackContainer(row);
        if (tc == null) {
            return false;
        }
        tc.activate();
        return true;
    }

    public ArrayList<String[]> getMasterpieceTrackInfo() {
        if (masterpiece == null) {
            return null;
        }
        ArrayList<String[]> trackInfo = new ArrayList<>();
        for (TrackContainer trackContainer : masterpiece.getAllTrackContainers()) {
            String[] objectId = new String[trackContainer.size()];
            for (int track = 0; track < trackContainer.size(); track++) {
                objectId[track] = trackContainer.getObjectId(track);
            }
            trackInfo.add(objectId);
        }
        return trackInfo;
    }
}
