package Info;

import java.util.HashMap;

/**
 *
 * @author Simen
 *
 * This class represent a dictionary for all the tags. This class can be used to
 * get more information about the different tags, like name and description
 */
public class Dictionary {

    //Felles for alle instanser av Dictionary
    private static HashMap<Tags, Tag> dictionary;

    public Dictionary() {
        dictionary = new HashMap<>();
        fillDictionary();
    }

    public static HashMap<Tags, Tag> getDictionary() {
        return dictionary;
    }

    /**
     *
     * This method fills up a HashMap with all the tags, name and description
     */
    private void fillDictionary() {
        insertTag(Tags.IART, "Artist", "Lists the artist of the original subject of the file. For example, Michaelangelo");
        insertTag(Tags.INAM, "Track", "Stores the title of the subject of the file, such as Seattle From Above");
        insertTag(Tags.IGNR, "Genre", "Describe the original work, such as jazz, classical, rock, etc.");
        insertTag(Tags.IKEY, "Keywords", "Provides a list of keywords that refer to the file or subject of the file. Separate multiple keywords with a semicolon and a blank. For example, Seattle; zoology; The Civil War.");
        insertTag(Tags.ISRF, "Digitization Source", "Identifies the orignal form of the material that was digitized, such as record, sampling CD, TV sound track and so forth. This is not necessarily the same as IMED");
        insertTag(Tags.IMED, "Original Medium", "Describes the original subject of the file, CD and so forth.");
        insertTag(Tags.IENG, "Engineers", "Stores the name of the engineer who worked on the file. If there are multiple engineers, separate the names by a semicolon and a blank. For example: Smith,John;Adams,Joe.");
        insertTag(Tags.ITCH, "Digitizer", "Identifies the technician who sampled the subject file. For example: Smith,John.");
        insertTag(Tags.ISRC, "Source Supplier", "Identifies the name of the person or organization who supplied the original subject of the file. For example: NARA.");
        insertTag(Tags.ICOP, "Copyright", "Records the copyright information for the file. For example, Copyright Encyclopedia International 1991. If there are multiple copyrights, separate them by a semicolon followed by a space");
        insertTag(Tags.ISFT, "Software Package", "(Automatically generated:) Identifies the name of the software package used to create the file, such as Audacity 1.3.9.");
        insertTag(Tags.ICRD, "Creation date", "(Automatically generated:) Specifies the date the subject of the file was created. List dates in year-month-day format, padding one-digit months and days with a zero on the left. For example: 1553-05-03 for May 3,1553. The year should always be given using four digits");
        insertTag(Tags.IALB, "Album", "Specifies the album for wich the track is a part of");
        insertTag(Tags.ILAN, "Language", "language(s) in which the lyrics to the music is sung");
        insertTag(Tags.ICMT, "Comments", "Provides general comments about the file or the subject of the file. If the comments is several sentences long, end each sentence with a period. Do not include newline characters. ");
        insertTag(Tags.ISBJ, "Subjects", "Describes the contents of the file, such as Metadata Management");
        insertTag(Tags.ISON, "Song(BLOB)", "The blob file");
        insertTag(Tags.ILEN, "Length", "Length of the file in minutes and seconds. For example 03:45 (three minutes and 45 seconds)");
        insertTag(Tags.ICON, "Country/Area", "country(-ies)/nationality(-ies) of the artist(s) performing the music");
        insertTag(Tags.ICOM, "Composer", "Name of person(s) having composed the music");
        insertTag(Tags.ILYW, "Lyric Writer", "Name of person(s) having written the words to the music");
        insertTag(Tags.IPLC, "Place", "City(-ies)/town(s)/village(s) where the artist(s) come from");
        insertTag(Tags.IREG, "Region", "Region within the country that the music comes from – used with traditional music");
        insertTag(Tags.IDIS, "District", "District within the country that the music comes from – used with traditional music");
        insertTag(Tags.IVIL, "Village", "Village within the country that the music comes from – used with traditional music");
        insertTag(Tags.IPGR, "People Group", "Nationality/tribe/ethnicity of the culture that the music comes from-  used with traditional music");
        insertTag(Tags.IODA, "Original date", "Date of recording of the original track – yyyy-mm-dd");


    }

    /**
     * This method is simply a helper method to the filling of the hashmap
     */
    private void insertTag(Tags tag, String name, String desc) {
        dictionary.put(tag, new Tag(name, desc));

    }
}
