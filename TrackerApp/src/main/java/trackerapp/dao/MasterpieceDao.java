package trackerapp.dao;

import trackerapp.domain.Masterpiece;

/**
 *
 * @author lehtonep
 */
public interface MasterpieceDao {

    Masterpiece getNewMasterpiece(int rows, int tracks);

    Masterpiece loadMasterpiece();

    boolean saveMasterpiece(Masterpiece masterpiece);
}
