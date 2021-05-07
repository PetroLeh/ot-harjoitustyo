package trackerapp.dao;

import java.io.File;
import java.io.FileWriter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import trackerapp.domain.Masterpiece;

/**
 *
 * @author lehtonep
 */
public class FileMasterpieceDaoTest {

    private FileMasterpieceDao testDao;

    @Before
    public void setup() {
        testDao = new FileMasterpieceDao();
    }

    @Test
    public void saveMasterpieceReturnsFalseIfError() {
        assertFalse(testDao.saveMasterpiece(null, new FileInstrumentLibraryDao("instruments.csv")));
        assertFalse(testDao.saveMasterpiece(new Masterpiece("test", 1, 1), null));

        // Mitään tiedostoa ei ole asetettu:
        assertFalse(testDao.saveMasterpiece(new Masterpiece("test", 1, 1), new FileInstrumentLibraryDao("instruments.csv")));
    }

    @Test
    public void saveMasterpieceWritesToAFile() {
        File testFile = new File("masterpiecesavetest.mpt");

        if (!testFile.exists()) {
            testDao.setFile(testFile);
            Masterpiece testpiece = new Masterpiece("test", 1, 1);
            testpiece.addRow();
            assertTrue(testDao.saveMasterpiece(testpiece, new FileInstrumentLibraryDao("instruments.csv")));
            assertTrue(testFile.exists());
            testFile.deleteOnExit();
        }
    }

    @Test
    public void loadMasterpieceReturnsAMasterpiece() {
        File testFile = new File("masterpieceloadtest.mpt");

        if (!testFile.exists()) {
            
            FileInstrumentLibraryDao library = new FileInstrumentLibraryDao("instruments.csv");
            Masterpiece testpiece = new Masterpiece("test", 1, 2);
            testpiece.addRow();
            String instrument = library.getInstruments().get(0);
            String id = library.getInstrumentIdList(instrument).get(0);
            
            testpiece.addObject(0, 0, library.getInstrumentObject(instrument, id));
            testDao.setFile(testFile);
            testDao.saveMasterpiece(testpiece, library);
            Masterpiece anotherTestpiece = testDao.loadMasterpiece();
            assertEquals(anotherTestpiece.size(), 1);
            assertEquals(anotherTestpiece.getName(), "test");
            testFile.deleteOnExit();
        }
    }
}
