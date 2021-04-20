package trackerapp.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lehtonep
 */
public class TrackContainerTest {

    @Test
    public void constructorCreatesArrayCorrectly() {
        TrackContainer tc = new TrackContainer(5);
        assertEquals(5, tc.getAllObjects().length);
    }
    
       
    @Test
    public void addingObjectsToContainerWorks() {
        TrackContainer tc = new TrackContainer(5);
        TrackObject testObject = new InstrumentObject("test", null);
        
        assertFalse(tc.addObject(-1, testObject));
        assertFalse(tc.addObject(5, testObject));
        
        assertTrue(tc.addObject(0, testObject));
        assertEquals("test", tc.getObject(0).getId());
    }
}
