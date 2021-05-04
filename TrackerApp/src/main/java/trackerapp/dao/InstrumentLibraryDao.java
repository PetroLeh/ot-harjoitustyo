package trackerapp.dao;

import java.util.ArrayList;
import trackerapp.domain.InstrumentObject;

/**
 *
 * @author lehtonep
 */
public interface InstrumentLibraryDao {

    ArrayList<String> getInstruments();

    ArrayList<String> getInstrumentIdList(String instrument);

    InstrumentObject getInstrumentObject(String instrument, String id);
    
    String getSource();
}
