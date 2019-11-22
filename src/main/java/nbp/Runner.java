package nbp;

import nbp.xmlparser.NbpSaxParser;

import java.util.List;

public class Runner {

    public void run() {

        Dir dir = new Dir(Request.INSTANCE.getStart(), Request.INSTANCE.getEnd());
        Xmls xmls = new Xmls(Request.INSTANCE.getStart(), Request.INSTANCE.getEnd());
        NbpSaxParser nbpSaxParser = new NbpSaxParser(Request.INSTANCE.getCurrency());

        dir.downloadDirs();
        List<String> listOfXmlFiles = xmls.fetchXmlFilesList(dir.getDirsPathsList());

        Results.calculateResults(listOfXmlFiles, nbpSaxParser);

    }


}
