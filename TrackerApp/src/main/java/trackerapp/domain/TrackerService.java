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
public class TrackerService {

    private Masterpiece masterpiece;
    private MasterpieceDao masterpieceDao;

    private int currentBpm, masterVolume, currentRow, nextRow;
    private String playerStatus;
    private Label infoLabel;

    public TrackerService(MasterpieceDao masterpieceDao) {
        this.masterpieceDao = masterpieceDao;        
        this.playerStatus = "pysäytetty";
        this.currentRow = 0;        
    }


    public int getNextRow() {
        return nextRow;
    }

    public int getMasterVolume() {
        return masterVolume;
    }

    public void setBpm(int bpm) {
        currentBpm = bpm;        
    }
    
    public int getCurrentBpm() {
        return currentBpm;
    }

    public void setInfoBar(Label infoLabel) {
        this.infoLabel = infoLabel;
    }

    public void updateInfoBar() {
        infoLabel.setText(getInfo());
    }

    public int getCurrentRow() {
        return currentRow;
    }
    
    public void setCurrentRow(int row) {
        currentRow = row;
    }

    public void nextRow() {
        currentRow++;
        if (currentRow >= masterpiece.size()) {
            currentRow = 0;
        }
    }
    
    public void setPlayerStatus(String status) {
        playerStatus = status;
    }
    
    public String getTrackInfo(int row) {
        return masterpiece.getTrackContainer(row).toString();
    }

    public String getInfo() {
        if (masterpiece != null) {
            String info = "mestariteos: " + masterpiece.getName() + "\t"
                    + "bpm: " + currentBpm + "\t"
                    + "rivejä: " + masterpiece.size() + "\t"
                    + playerStatus;
            return info;
        }
        return "...";
    }

    public void setNewMasterpiece(int rows, int tracks) {
        masterpiece = masterpieceDao.getNewMasterpiece(rows, tracks);
        currentBpm = masterpiece.getBpm();
        currentRow = 0;
        updateInfoBar();
    }

    public void randomMasterpiece() {
        AudioClip audioKattila = new AudioClip("file:audio/testi_kattila.wav");
        AudioClip audioKansi = new AudioClip("file:audio/testi_kansi.wav");
        AudioClip audioKansi2 = new AudioClip("file:audio/testi_kansi2.wav");

        AudioClip audioKitaraA = new AudioClip("file:audio/testi_kitara_a.wav");
        AudioClip audioKitaraC = new AudioClip("file:audio/testi_kitara_c.wav");
        AudioClip audioKitaraE2 = new AudioClip("file:audio/testi_kitara_e2.wav");
        AudioClip audioKitaraG2 = new AudioClip("file:audio/testi_kitara_g2.wav");
        AudioClip audioKitaraA2 = new AudioClip("file:audio/testi_kitara_a2.wav");
        AudioClip audioKitaraC2 = new AudioClip("file:audio/testi_kitara_c2.wav");
        AudioClip audioKitaraE3 = new AudioClip("file:audio/testi_kitara_e3.wav");

        InstrumentObject kattila = new InstrumentObject("kattila", audioKattila);
        InstrumentObject kansi = new InstrumentObject("kansi", audioKansi);
        InstrumentObject kansi2 = new InstrumentObject("kansi2", audioKansi2);

        InstrumentObject kitaraA = new InstrumentObject("(kitara) a", audioKitaraA);
        InstrumentObject kitaraC = new InstrumentObject("(kitara) c", audioKitaraC);
        InstrumentObject kitaraE2 = new InstrumentObject("(kitara) e2", audioKitaraE2);
        InstrumentObject kitaraG2 = new InstrumentObject("(kitara) g2", audioKitaraG2);
        InstrumentObject kitaraA2 = new InstrumentObject("(kitara) a2", audioKitaraA2);
        InstrumentObject kitaraC2 = new InstrumentObject("(kitara) c2", audioKitaraC2);
        InstrumentObject kitaraE3 = new InstrumentObject("(kitara) e3", audioKitaraE3);

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
                        addObject(row, track, kitaraA);
                    } else if (n < 17) {
                        addObject(row, track, kitaraE2);
                    } else if (n < 21) {
                        addObject(row, track, kitaraA2);
                    } else if (n < 24) {
                        addObject(row, track, kitaraC2);
                    } else if (n < 26) {
                        addObject(row, track, kitaraE3);
                    } else if (n < 30) {
                        addObject(row, track, kitaraC);
                    } else if (n < 34) {
                        addObject(row, track, kitaraG2);
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

    public ArrayList<String[]> getMasterpieceInfo() {
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
