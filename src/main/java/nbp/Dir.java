package nbp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Dir {

    private static final String BASE_URL = "http://www.nbp.pl/kursy/xml/";
    private static final String BASE_FILE_NAME = "src/main/resources/";
    private String start;
    private String end;

    public Dir(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public void downloadDirs() {

        selectDirFiles().parallelStream()
                .forEach(dirName -> download(BASE_URL + dirName, BASE_FILE_NAME + dirName));
    }

    public List<String> getDirsPathsList() {
        return selectDirFiles().stream()
                .map(dirName -> BASE_FILE_NAME + dirName)
                .collect(Collectors.toList());
    }

    public List<String> selectXmlFilesFromDir(String filePath) {

        int startInt = parseInt(start.substring(2));
        int endInt = parseInt(end.substring(2));
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return stream.filter(line -> line.startsWith("c") && startInt <= parseInt(line.substring(5)) && parseInt(line.substring(5)) <= endInt)
                    .map(line -> BASE_URL + line + ".xml")
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<String> selectDirFiles() {
        List<String> dirs = new ArrayList<>();
        String startYear = start.substring(0, 4);
        String endYear = end.substring(0, 4);
        if (parseInt(startYear) == LocalDate.now().getYear()) {
            dirs.add("dir.txt");
        } else if (parseInt(endYear) == LocalDate.now().getYear()) {
            for (int i = parseInt(startYear); i < parseInt(endYear); i++) {
                dirs.add("dir" + i + ".txt");
            }
            dirs.add("dir.txt");
        } else {
            for (int i = parseInt(startYear); i <= parseInt(endYear); i++) {
                dirs.add("dir" + i + ".txt");
            }
        }
        return dirs;
    }

    private void download(String url, String filePath) {
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url).openStream())) {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }


}
