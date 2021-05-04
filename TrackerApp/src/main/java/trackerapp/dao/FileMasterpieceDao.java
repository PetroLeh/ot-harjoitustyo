package trackerapp.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import trackerapp.domain.Masterpiece;

/**
 *
 * @author lehtonep
 */
public class FileMasterpieceDao implements MasterpieceDao {

    private File file;

    public FileMasterpieceDao() {
        this.file = null;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public Masterpiece getNewMasterpiece(int rows, int tracks) {
        Masterpiece newMasterpiece = new Masterpiece("nimetön", 180, tracks);
        for (int row = 0; row < rows; row++) {
            newMasterpiece.addRow();
        }
        return newMasterpiece;
    }

    @Override
    public Masterpiece loadMasterpiece() {

        String name = "nimetön";
        int numberOfRows = 0;
        int numberOfTracks = 6;
        int bpm = 180;
        String instrumentLibrary = "instruments.csv";
        
        System.out.println("Avataan " + file.getName() + "...");
        try { 
            Scanner reader = new Scanner(file);
            String header = "";
            while (header.isBlank() && reader.hasNextLine()) {
                header = reader.nextLine();
            }
            String[] pieces = header.split(";");
            for (String pair : pieces) {
                String key = pair.split("=")[0].toLowerCase();
                String value = pair.split("=")[1];
                System.out.println(key + " = " + value);
                if (key.equals("name")) {
                    name = value;
                } else if (key.equals("rows")) {
                    numberOfRows = Integer.valueOf(value);
                } else if (key.equals("tracks")) {
                    numberOfTracks = Integer.valueOf(value);
                } else if (key.equals("bpm")) {
                    bpm = Integer.valueOf(value);
                } else if (key.equals("library")) {
                    instrumentLibrary = value;
                }
            }
            Masterpiece masterpiece = getNewMasterpiece(numberOfRows, numberOfTracks);
            masterpiece.setName(name);
            masterpiece.setBpm(bpm);            

            FileInstrumentLibraryDao library = new FileInstrumentLibraryDao(instrumentLibrary);
            
            int rowNumber = 0;
            while (reader.hasNextLine()) {
                String row = reader.nextLine();
                if (!row.isBlank()) {
                    String[] tracks = row.split(";");
                    for (int track = 0; track < tracks.length; track++) {
                        if (!tracks[track].isBlank()) {
                            String instrument = tracks[track].split(":")[0];
                            String id = tracks[track].split(":")[1];
                            masterpiece.addObject(rowNumber, track, library.getInstrumentObject(instrument, id));
                        } 
                    }
                    rowNumber++;
                }
            }
            return masterpiece;

        } catch (Exception e) {
            System.out.println("Virhe luettaessa tiedostoa '" + file.toString() + "':\n" + e.toString());
        }
        return getNewMasterpiece(0, 6);
    }

    @Override
    public boolean saveMasterpiece(Masterpiece masterpiece, InstrumentLibraryDao instrumentLibrary) {
        String source = instrumentLibrary.getSource();
        if (file == null || masterpiece == null) {
            return false;
        }
        System.out.println("Tallennetaan...");
        try {
            FileWriter writer = new FileWriter(file);
            String header = "name=" + masterpiece.getName() + ";rows=" + masterpiece.size() + ";tracks=" + masterpiece.getTrackSize() + ";bpm="
                    + masterpiece.getBpm() + ";library=" + instrumentLibrary.getSource() + "\n";
            writer.write(header);
            for (int row = 0; row < masterpiece.size(); row++) {
                StringBuffer objects = new StringBuffer();
                for (int track = 0; track < masterpiece.getTrackSize(); track++) {
                    if (masterpiece.getTrackContainer(row).getObject(track) != null) {
                        objects.append(masterpiece.getTrackContainer(row).getObject(track).getId());
                    }
                    if (track < masterpiece.getTrackSize() - 1) {
                        objects.append(";");
                    }
                }
                writer.write(objects.toString() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("virhe: " + e.toString());
            return false;
        }

        return true;
    }
}
