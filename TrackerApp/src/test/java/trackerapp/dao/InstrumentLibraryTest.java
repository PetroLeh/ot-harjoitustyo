package trackerapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author lehtonep
 */
public class InstrumentLibraryTest {

    FileInstrumentLibraryDao library;

    @Before
    public void setUp() {
        library = new FileInstrumentLibraryDao("instrument.csv");
    }

    @Test
    public void creatingLibraryFromFileWorks() {
        String file = "thisShouldNotWork";

        FileInstrumentLibraryDao libraryTest = new FileInstrumentLibraryDao(file);
        assertTrue(libraryTest.getLibrary().isEmpty());
        assertEquals(libraryTest.getSource(), "thisShouldNotWork");

        file = "instruments.csv";

        libraryTest = new FileInstrumentLibraryDao(file);
        assertFalse(libraryTest.getLibrary().isEmpty());
        assertEquals(libraryTest.getSource(), "instruments.csv");
    }

    @Test
    public void addingInstrumentWorks() {
        
        assertTrue(library.getLibrary().isEmpty());
        library.addNewInstrument("test");
        assertEquals(library.getInstruments().size(), 1);
        assertTrue(library.getInstruments().contains("test"));
    }

    @Test    
    public void linkingAudioToInstrumentWorks() {
        
        library.addNewInstrument("test");
        library.addToInstrument("not existing", "should not add", null);

        assertTrue(library.getInstrumentIdList("test").isEmpty());

        library.addToInstrument("test", "testId", null);
        assertEquals(library.getInstrumentIdList("test").size(), 1);
        assertEquals(library.getInstrumentIdList("test").get(0), "testId");
    }

    @Test
    public void getInstrumentObjectReturnsCorrectObject() {

        library.addNewInstrument("test1");
        library.addNewInstrument("test2");

        library.addToInstrument("test1", "object3", null);
        library.addToInstrument("test2", "object4", null);

        assertEquals(library.getInstrumentObject("test1", "not existing"), null);
        assertEquals(library.getInstrumentObject("test1", "object3").getId(), "test1:object3");
        assertEquals(library.getInstrumentObject("test2", "object3"), null);
        assertEquals(library.getInstrumentObject("test2", "object4").getId(), "test2:object4");
    }

    @Test
    public void getInstrumentIdListReturnsListCorrectly() {

        library.addNewInstrument("test");

        assertTrue(library.getInstrumentIdList("test").isEmpty());

        library.addToInstrument("test", "object1", null);
        library.addToInstrument("test", "object2", null);

        assertEquals(library.getInstrumentIdList("test").size(), 2);
        assertTrue(library.getInstrumentIdList("test").contains("object1"));
        assertTrue(library.getInstrumentIdList("test").contains("object2"));
    }
}
