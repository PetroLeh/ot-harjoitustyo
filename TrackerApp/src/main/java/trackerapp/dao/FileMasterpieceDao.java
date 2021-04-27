package trackerapp.dao;

import java.io.File;
import java.io.FileWriter;
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
        Masterpiece newMasterpiece = new Masterpiece("nimetön", 180);
        for (int row = 0; row < rows; row++) {
            newMasterpiece.addRow(tracks);
        }
        return newMasterpiece;
    }

    @Override
    public Masterpiece loadMasterpiece() {
        return null;
    }

    @Override
    public boolean saveMasterpiece(Masterpiece masterpiece) {
        if (file == null || masterpiece == null) {
            return false;
        }
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < masterpiece.size(); i++) {
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }
}
