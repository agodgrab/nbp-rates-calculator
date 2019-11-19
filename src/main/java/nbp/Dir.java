package nbp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Dir {
    private int start;
    private int end;

    public Dir(String start, String end) {
        this.start = parseInt(start.substring(2));
        this.end = parseInt(end.substring(2));
    }

    private static String BASE_URL = "http://www.nbp.pl/kursy/xml/";
    private static String URL = BASE_URL + "dir.txt";
    private static String FILE_NAME = "/Users/aleksandragodyn/PROGRAMMING/nbpdir.txt";


    public void download() {
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(URL).openStream())) {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }


    public List<String> select() {

        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            return stream.filter(line -> line.startsWith("c") && start <= parseInt(line.substring(5)) && parseInt(line.substring(5)) <= end)
                    .map(line -> BASE_URL + line + ".xml")
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
