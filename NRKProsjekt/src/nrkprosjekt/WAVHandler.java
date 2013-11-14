/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nrkprosjekt;

import java.io.File;
import java.io.IOException;
import jwawfile.BadRIFFException;
import jwawfile.Metadata;
import jwawfile.Tag;

/**
 *
 * @author Simen
 */
public class WAVHandler {

    Metadata metadata;

    public WAVHandler(File file) throws BadRIFFException, IOException {
        //metadata = new Metadata(file.getAbsolutePath());
    }

    public void setMetadata(File file) throws BadRIFFException, IOException {
        //metadata = new Metadata(file.getAbsolutePath());
    }

    public void getMetaInfo() {
        System.out.println(metadata.getTag(Tag.IART));
    }
}
