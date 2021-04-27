package trackerapp.domain;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javafx.scene.media.AudioClip;

/**
 *
 * @author lehtonep
 */
public class InstrumentLibrary {

    private HashMap<String, HashMap<String, InstrumentObject>> library;

    public InstrumentLibrary() {
        library = new HashMap<>();
    }

    public InstrumentLibrary(String file) {
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
        String instrumentId = instrument + " " + id;
        System.out.println("Instrument: '" + instrument + "' | id: '" + instrumentId + "' | file: '" + file + "'");
        if (library.containsKey(instrument)) {
            library.get(instrument).put(id, new InstrumentObject(instrumentId, new AudioClip("file:" + file)));
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

}
