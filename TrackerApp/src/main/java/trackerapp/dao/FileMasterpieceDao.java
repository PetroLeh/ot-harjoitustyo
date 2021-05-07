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
    private String name, instrumentLibrary;
    private int numberOfRows, numberOfTracks, bpm;
    FileInstrumentLibraryDao library;

    public FileMasterpieceDao() {
        this.file = null;
        this.name = "nimetön";
        this.bpm = 180;
        this.instrumentLibrary = "instruments.csv";
        this.numberOfRows = 0;
        this.numberOfTracks = 6;
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

        System.out.println("Avataan " + file.getName() + "...");
        try {
            Scanner reader = new Scanner(file);
            String header = "";
            while (header.isBlank() && reader.hasNextLine()) {
                header = reader.nextLine();
            }

            boolean headerOk = parseHeader(header);

            if (headerOk) {
                Masterpiece masterpiece = getNewMasterpiece(numberOfRows, numberOfTracks);
                masterpiece.setName(name);
                masterpiece.setBpm(bpm);
                library = new FileInstrumentLibraryDao(instrumentLibrary);

                int rowNumber = 0;
                while (reader.hasNextLine()) {
                    String row = reader.nextLine();
                    if (!row.isBlank()) {
                        addToMasterpiece(masterpiece, rowNumber, row);
                        rowNumber++;
                    }
                }
                return masterpiece;
            }

        } catch (Exception e) {
            System.out.println("Virhe luettaessa tiedostoa '" + file.toString() + "':\n" + e.toString());
        }

        return getNewMasterpiece(0, 6);
    }

    @Override
    public boolean saveMasterpiece(Masterpiece masterpiece, InstrumentLibraryDao instrumentLibrary) {

        if (file == null || masterpiece == null || instrumentLibrary == null) {
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

    private boolean parseHeader(String header) {

        String[] pieces = header.split(";");
        for (String pair : pieces) {
            String key = pair.split("=")[0].toLowerCase();
            String value = pair.split("=")[1];
            System.out.println(key + " = " + value);
            if (key.equals("name")) {
                name = value;
            } else if (key.equals("rows")) {
                try {
                    numberOfRows = Integer.valueOf(value);
                } catch (NumberFormatException e) {
                    return false;
                }
            } else if (key.equals("tracks")) {
                try {
                    numberOfTracks = Integer.valueOf(value);
                } catch (NumberFormatException e) {
                    return false;
                }
            } else if (key.equals("bpm")) {
                try {
                    bpm = Integer.valueOf(value);
                } catch (NumberFormatException e) {
                    bpm = 180;
                }
            } else if (key.equals("library")) {
                instrumentLibrary = value;
            }
        }
        return true;
    }

    private void addToMasterpiece(Masterpiece masterpiece, int rowNumber, String row) {
        String[] tracks = row.split(";");
        for (int track = 0; track < tracks.length; track++) {
            if (!tracks[track].isBlank()) {
                String instrument = tracks[track].split(":")[0];
                String id = tracks[track].split(":")[1];
                masterpiece.addObject(rowNumber, track, library.getInstrumentObject(instrument, id));
            }
        }
    }

}
