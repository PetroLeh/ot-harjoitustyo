package trackerapp.domain;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.control.Label;
import trackerapp.dao.InstrumentLibraryDao;
import trackerapp.dao.MasterpieceDao;

/**
 *
 * @author lehtonep
 */
public class TrackerService {

    private Masterpiece masterpiece;
    private MasterpieceDao masterpieceDao;

    private int currentBpm, currentRow, nextRow;
    private String playerStatus;
    private Label infoLabel;
    private TrackObject selectedObject;
    private InstrumentLibraryDao instrumentLibrary;

    public TrackerService(MasterpieceDao masterpieceDao, InstrumentLibraryDao instrumentLibrary) {
        this.masterpieceDao = masterpieceDao;
        this.instrumentLibrary = instrumentLibrary;
        this.playerStatus = "pysäytetty";
        this.currentRow = 0;
    }

    public int getNextRow() {
        return nextRow;
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
    
    public void setInstrumentLibrary(InstrumentLibraryDao library) {
        this.instrumentLibrary = library;
    }
    
    public InstrumentLibraryDao getInstrumentLibrary() {
        return this.instrumentLibrary;
    }

    public void addNewRow() {
        masterpiece.addRow();
    }

    public void addNewRow(int numberOfRows) {
        for (int i = 0; i < numberOfRows; i++) {
            addNewRow();
        }
    }

    public void removeRow() {
        masterpiece.removeRow();
    }

    public void setPlayerStatus(String status) {
        playerStatus = status;
    }

    public String getTrackInfo(int row) {
        if (!masterpiece.isEmpty()) {
            return masterpiece.getTrackContainer(row).toString();
        }
        return "";
    }

    public void setNewMasterpiece(int rows, int tracks) {
        masterpiece = masterpieceDao.getNewMasterpiece(rows, tracks);
        currentBpm = masterpiece.getBpm();
        currentRow = 0;
        updateInfoBar();
    }
    
    public void setMasterpiece(Masterpiece masterpiece) {
        this.masterpiece = masterpiece;
        setBpm(masterpiece.getBpm());
        updateInfoBar();
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

    public Masterpiece getMasterpiece() {
        return masterpiece;
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

    public TrackObject getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(String instrument, String objectId) {
        if (instrument == null || objectId == null) {
            selectedObject = null;
        } else {
            selectedObject = instrumentLibrary.getInstrumentObject(instrument, objectId);
        }
    }

    public void setSelectedObject(TrackObject object) {
        selectedObject = object;
    }
}
