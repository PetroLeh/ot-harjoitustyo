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
        assertEquals(5, tc.getObjects().length);
    }
}
