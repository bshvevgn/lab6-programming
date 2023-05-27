package data.old;

import parameters.Coordinates;
import parameters.MusicBand;
import parameters.MusicGenre;
import parameters.Studio;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

/**
 * XML Reader class for <code>Loader</code> class. It reads XML file by given path and provides
 * <code>LinkedHashMap&#8249;String[], String></code>, where <code>String[]</code> is XML path to <code>String</code> value.
 *
 * @see LinkedHashMap
 * @since 1.0
 * @author zerumi
 */
public class NewParser  {

    private static ArrayList<MusicBand> list = new ArrayList<>();

    private static ArrayDeque<String> values = new ArrayDeque<>();

    private static ArrayList<String> currentKeys = new ArrayList<>();

    /**
     * Default constructor of XMLReader.
     */


    /**
     * Reads XML file by path and returns parsed <code>String[]</code>/<code>String</code> map.
     *
     * @param path path to XML file
     * @return Map of read items
     * @throws IOException If an I/O occurs opening source
     */
    public static ArrayList<MusicBand> serialize(String path) throws IOException {
        BufferedInputStream bfs = new BufferedInputStream(new FileInputStream(path));
        BufferedReader reader = new BufferedReader(new InputStreamReader(bfs, StandardCharsets.UTF_8));

        String line = reader.readLine();

        if (line == null)
        {
            System.out.println("XML file violates XML format and was skipped.");
            return list;
        }

        reader.readLine(); // skip 1st line

        while (line != null)
        {
            line = reader.readLine();
            if(line == null) continue;
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] regexSplittedLine = line.split("<.*?>");
            pushValue(regexSplittedLine);

            Pattern nameStartPattern = Pattern.compile("<[^/]*?>");
            var nameStartPatternMatcher = nameStartPattern.matcher(line);
            if (nameStartPatternMatcher.find())
            {
                String nameStartTag = nameStartPatternMatcher.group();
                currentKeys.add(nameStartTag.substring(1, nameStartTag.length() - 1));
            }

            handleCurrentKeyAndValue();

            Pattern nameEndPattern = Pattern.compile("</\\S*?>");
            var nameEndPatternMatcher = nameEndPattern.matcher(line);
            if (nameEndPatternMatcher.find())
            {
                String nameEndTag = nameEndPatternMatcher.group();
                System.out.println(nameEndTag.substring(2, nameEndTag.length() - 1));
                currentKeys.remove(nameEndTag.substring(2, nameEndTag.length() - 1));
            }
        }
        return list;
    }

    static MusicBand band = new MusicBand();
    static Coordinates coordinates = new Coordinates();
    static Studio studio = new Studio();


    private static void handleCurrentKeyAndValue() {
        if (!values.isEmpty())
        {
            String[] keys = new String[currentKeys.size()];
            int i = 0;
            for (String key : currentKeys)
            {
                keys[i++] = key;
            }
            band.setId(list.size());
            if(Objects.equals(keys[i-1], "name")) {
                band.setName(values.getLast());
            }
            if(Objects.equals(keys[i-1], "genre")) {
                band.setGenre(MusicGenre.valueOf(values.getLast()));
            }
            if(Objects.equals(keys[i-1], "x")) {
                coordinates.setX(Double.parseDouble(values.getLast()));
            }
            if(Objects.equals(keys[i-1], "y")) {
                coordinates.setY(Float.parseFloat(values.getLast()));
            }
            if(Objects.equals(keys[i-1], "studio")) {
                studio.setName(values.getLast());
            }
            if(Objects.equals(keys[i-1], "number_of_participants")) {
                band.setNOP(parseLong(values.getLast()));
            }

            band.setCoordinates(coordinates);
            band.setStudio(studio);
            list.add(band);
            values.removeLast();
        }
    }

    private static void pushValue(String[] regexSplitLine) {
        for (String e : regexSplitLine)
        {
            if (e.isEmpty()) continue;
            values.addLast(e);
        }
    }
}