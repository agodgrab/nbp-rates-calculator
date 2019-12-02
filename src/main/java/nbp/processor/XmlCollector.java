package nbp.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

public class XmlCollector {

    private static final String BASE_URL = "http://www.nbp.pl/kursy/xml/";
    private String start;
    private String end;

    public XmlCollector(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public List<String> fetchXmlFilesList() {
        return selectDirFiles().parallelStream()
                .map(this::getXmlsFromDir)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<String> selectDirFiles() {
        String startYear = start.substring(0, 4);
        String endYear = end.substring(0, 4);

        return IntStream.rangeClosed(parseInt(startYear), parseInt(endYear))
                .mapToObj(year-> year==Year.now().getValue()? "dir.txt" : "dir" + year + ".txt")
                .collect(Collectors.toList());
    }

    private List<String> getXmlsFromDir(String dirName){
        URL url = generateURL(dirName);
        return selectXmls(collectDirLines(url));
    }

    private List<String> selectXmls(List<String> lines){
        int startInt = parseInt(start.substring(2));
        int endInt = parseInt(end.substring(2));

        return lines.stream()
                .filter( line ->line.startsWith("c") && startInt <= parseInt(line.substring(5)) && parseInt(line.substring(5)) <= endInt )
                .map(line -> BASE_URL + line + ".xml")
                .collect(Collectors.toList());
    }

    private List<String> collectDirLines(URL url){
        List<String> dirlines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dirlines.add(line);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return dirlines;
    }

    private URL generateURL(String suffix) {
        URL url = null;
        try {
            url = new URL(BASE_URL + suffix);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(url);
    }
}
