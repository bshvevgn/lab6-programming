
package data.old;

import parameters.MusicBand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Base Reader interface. Should be implemented for using in Loader class.
 *
 * @see Loader
 * @since 1.0
 * @author zerumi
 */
public interface BaseReader {

    /**
     * Base method for reading file and providing Address-Value interpretation of this File.
     *
     * @param path Full path to file.
     * @return Collection of values with full address from File.
     * @throws IOException When something will go wrong during file handling
     */
    ArrayList<MusicBand> serialize(String path) throws IOException;
}