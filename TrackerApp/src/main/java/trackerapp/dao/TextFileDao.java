/*
 * Petro Lehtonen
 */
package trackerapp.dao;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author lehtonep
 */
public class TextFileDao {

    public String getAsString(String fileName) {
        File file = new File(fileName);
        StringBuffer s = new StringBuffer();

        try {
            Scanner filereader = new Scanner(file);
            while (filereader.hasNextLine()) {
                String nextLine = filereader.nextLine() + "\n";
                s.append(nextLine);
            }
        } catch (Exception e) {
            String error = "error reading file: '" + file + "'";
            s.append(error);
        }

        return s.toString();
    }
}
