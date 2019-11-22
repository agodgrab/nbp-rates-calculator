package nbp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Dir {
    private String start;
    private String end;
    private static String BASE_URL = "http://www.nbp.pl/kursy/xml/";
    private static String BASE_FILE_NAME = "src/main/resources/";

    public Dir(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public void downloadDirs() {

        selectDirFiles().stream()
                .forEach(dirName -> download(BASE_URL + dirName, BASE_FILE_NAME + dirName));
    }

    public List<String> getDirsPathsList() {
        return selectDirFiles().stream()
                .map(dirName -> BASE_FILE_NAME + dirName)
                .collect(Collectors.toList());
    }

    private List<String> selectDirFiles() {
        List<String> dirs = new ArrayList<>();
        String startYear = start.substring(0, 4);
        String endYear = end.substring(0, 4);
        if (parseInt(startYear) == LocalDate.now().getYear()) {
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
