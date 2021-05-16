package trackerapp.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import trackerapp.dao.FileInstrumentLibraryDao;
import trackerapp.dao.FileMasterpieceDao;

/**
 *
 * @author lehtonep
 */
public class TestTrackerService {

    FileInstrumentLibraryDao testLibrary;
    Masterpiece testMasterpiece;
    TrackerService testTracker;

    @Before
    public void setUp() {

        testLibrary = new FileInstrumentLibraryDao();
        testLibrary.addNewInstrument("testInstrument");
        testLibrary.addToInstrument("testInstrument", "testObject", null);

        testMasterpiece = new Masterpiece("test", 1, 1);

        testTracker = new TrackerService(new FileMasterpieceDao(), testLibrary);
    }

    @Test
    public void selectedObjectReturnsNullIfNothingSelected() {

        assertEquals(testTracker.getSelectedObject(), null);
    }

    @Test
    public void settingSelectedObjectWorks() {

        testTracker.setSelectedObject(new InstrumentObject("first:test", null));
        assertEquals(testTracker.getSelectedObject().getId(), "first:test");

        testTracker.setSelectedObject("doesNot", "exist");
        assertEquals(testTracker.getSelectedObject(), null);

        testTracker.setSelectedObject("testInstrument", "testObject");
        assertEquals(testTracker.getSelectedObject().getId(), "testInstrument:testObject");

        testTracker.setSelectedObject("testInstrument", null);
        assertEquals(testTracker.getSelectedObject(), null);
    }

    @Test
    public void getMasterpieceReturnsNullIfNotSet() {

        assertEquals(testTracker.getMasterpiece(), null);
    }

    @Test
    public void settingMasterpieceWorks() {

        assertEquals(testTracker.getMasterpiece(), null);
        testTracker.setMasterpiece(testMasterpiece);

        assertEquals(testTracker.getMasterpiece(), testMasterpiece);
    }

    @Test
    public void addingSingleRowWorks() {

        testTracker.setMasterpiece(testMasterpiece);
        assertEquals(testTracker.getMasterpiece().size(), 0);

        testTracker.addNewRow();
        assertEquals(testTracker.getMasterpiece().size(), 1);
    }

    @Test
    public void addingMultipleRowsWorks() {

        testTracker.setMasterpiece(testMasterpiece);
        assertEquals(testTracker.getMasterpiece().size(), 0);

        testTracker.addNewRows(10);
        assertEquals(testTracker.getMasterpiece().size(), 10);
    }

    @Test
    public void addingObjectOutOfRangeReturnsFalse() {

        testTracker.setMasterpiece(testMasterpiece);
        assertFalse(testTracker.addObject(2, 2, testLibrary.getInstrumentObject("testInstrument", "testObject")));
    }

    @Test
    public void addingObjectWorks() {

        testTracker.setMasterpiece(testMasterpiece);
        testMasterpiece.addRow();
        assertTrue(testTracker.addObject(0, 0, testLibrary.getInstrumentObject("testInstrument", "testObject")));
    }

    @Test
    public void getMasterpieceInfoReturnsNullForNoMasterpiece() {

        assertEquals(testTracker.getMasterpieceInfo(), null);
    }

    @Test
    public void getMasterpieceInfoReturnsCorrectInfo() {

        testTracker.setMasterpiece(testMasterpiece);
        testTracker.addNewRow();
        testTracker.addObject(0, 0, testLibrary.getInstrumentObject("testInstrument", "testObject"));

        assertEquals(testTracker.getMasterpieceInfo().size(), 1);
        assertEquals(testTracker.getMasterpieceInfo().get(0).length, 1);
        assertEquals(testTracker.getMasterpieceInfo().get(0)[0], "testInstrument:testObject");
    }

    @Test
    public void activatingTrackContainerOutOfRangeReturnsFalse() {
        
        testTracker.setMasterpiece(testMasterpiece);        
        assertFalse(testTracker.activateTrackContainer(0));
    }
    
    @Test
    public void activatingExistingTrackContainerReturnsTrue() {
        
        testTracker.setMasterpiece(testMasterpiece);
        testTracker.addNewRow();
        
        assertTrue(testTracker.activateTrackContainer(0));
    }
    
    @Test
    public void settingNewMasterpieceWorks() {
        
        assertEquals(testTracker.getMasterpiece(), null);
        testTracker.setNewMasterpiece(5, 5);
        
        assertEquals(testTracker.getMasterpiece().size(), 5);
        assertEquals(testTracker.getMasterpiece().getTrackSize(), 5);        
    }
    
    @Test
    public void getTrackInfoReturnsEmptyIfNoMasterpieceExisting() {
        
        assertEquals(testTracker.getTrackInfo(0), "");
    }
    
    @Test
    public void getTrackInfoReturnsCorrectlyIfMasterpieceExisting() {
        
        testTracker.setMasterpiece(testMasterpiece);
        testTracker.addNewRows(5);
        assertEquals(testTracker.getTrackInfo(6), "");
        assertNotEquals(testTracker.getTrackInfo(4), "");
    }
}
