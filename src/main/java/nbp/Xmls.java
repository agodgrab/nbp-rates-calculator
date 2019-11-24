package nbp;
import java.util.ArrayList;
import java.util.List;


public class Xmls {

    public List<String> fetchXmlFilesList(Dir dir) {
        List<String> result = new ArrayList<>();

        for (String dirPath : dir.getDirsPathsList()) {
            result.addAll(dir.selectXmlFilesFromDir(dirPath));
        }
        return result;
    }
}
