/*
 * Petro Lehtonen
 */
package trackerapp.dao;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javafx.scene.media.AudioClip;
import trackerapp.domain.InstrumentObject;

/**
 *
 * @author lehtonep
 */
public class FileInstrumentLibraryDao implements InstrumentLibraryDao {

    private HashMap<String, HashMap<String, InstrumentObject>> library;
    private String file;
    
    public FileInstrumentLibraryDao(String file) {
        this.file = file;
        library = new HashMap<>();
        System.out.println("Adding instruments to library (from '" + file + "')...");
        try {
            Scanner reader = new Scanner(Paths.get(file));
            String instrument = "";
            String directory = "";

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (!line.isBlank()) {
                    String[] pieces = line.split(";");
                    if (pieces[0].charAt(0) == '@') {
                        instrument = pieces[0].replace("@", "");
                        directory = pieces[1];
                        addNewInstrument(instrument);
                    } else {
                        String instrumentId = pieces[0];
                        String audioFile = directory + pieces[1];
                        addToInstrument(instrument, instrumentId, audioFile);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Virhe luettaessa '" + file + "':\n" + e.toString());
        }
    }

    public void addNewInstrument(String instrument) {
        library.putIfAbsent(instrument, new HashMap<>());
    }

    public void addToInstrument(String instrument, String id, String file) {
        String instrumentId = instrument + ":" + id;
        if (library.containsKey(instrument)) {
            InstrumentObject objectToAdd = new InstrumentObject(instrumentId, new AudioClip("file:" + file));  
            library.get(instrument).put(id, objectToAdd);
        }
    }

    public HashMap getLibrary() {
        return library;
    }

    public ArrayList<String> getInstruments() {
        ArrayList<String> r = new ArrayList<>();
        library.keySet().forEach(key -> {
            r.add(key);
        });
        return r;
    }

    public ArrayList<String> getInstrumentIdList(String instrument) {
        ArrayList<String> r = new ArrayList<>();
        if (library.containsKey(instrument)) {
            library.get(instrument).keySet().forEach(id -> {
                r.add(id);
            });
        }
        return r;
    }

    public InstrumentObject getInstrumentObject(String instrument, String id) {
        if (library.containsKey(instrument)) {
            if (library.get(instrument).containsKey(id)) {
                return library.get(instrument).get(id);
            }
        }
        return null;
    }
    
    public String getSource() {
        return this.file;
    }
}
