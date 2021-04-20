package trackerapp.dao;

import java.io.File;
import trackerapp.domain.Masterpiece;

/**
 *
 * @author lehtonep
 */
public class FileMasterpieceDao implements MasterpieceDao {

    public Masterpiece getNewMasterpiece(int rows, int tracks) {
        Masterpiece newMasterpiece = new Masterpiece("nimetön", 180);
        for (int row = 0; row <= rows; row++) {
            newMasterpiece.addRow(tracks);
        }
        return newMasterpiece;
    }

    public Masterpiece getMasterpieceFromFile(File file) {
        return null;
    }

    public boolean saveMasterpieceToFile(File file) {
        return false;
    }
}
