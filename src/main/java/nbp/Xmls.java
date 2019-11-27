package nbp;
import java.util.List;
import java.util.stream.Collectors;


public class Xmls {

    public List<String> fetchXmlFilesList(Dir dir) {
        return dir.getDirsPathsList().parallelStream()
                .map(dir::selectXmlFilesFromDir)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
