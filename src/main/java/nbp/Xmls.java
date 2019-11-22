package nbp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Xmls {

    private String start;
    private String end;
    private static String BASE_URL = "http://www.nbp.pl/kursy/xml/";

    public Xmls(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public List<String> fetchXmlFilesList(List<String> listOfDirsPaths) {
        List<String> result = new ArrayList<>();

        for (String dirPath : listOfDirsPaths) {
            result.addAll(selectXmlFilesFromDir(dirPath));
        }
        return result;
    }

    private List<String> selectXmlFilesFromDir(String filePath) {

        int startInt = parseInt(start.substring(2));
        int endInt = parseInt(end.substring(2));
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            return stream.filter(line -> line.startsWith("c") && startInt <= parseInt(line.substring(5)) && parseInt(line.substring(5)) <= endInt)
                    .map(line -> BASE_URL + line + ".xml")
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Exception occurred: " + e.getMessage() + " casue: " + e.getCause() + " stack trace: " + e.getStackTrace());
            return new ArrayList<>();
        }
    }
}
