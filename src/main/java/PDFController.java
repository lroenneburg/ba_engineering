import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class PDFController {

    String _firstUrlPart = "https://www.bundesverfassungsgericht.de/SharedDocs/Downloads/DE/";
    String _lastUrlPart = ".pdf?__blob=publicationFile";

    public PDFController(String ecli, String year, String month) throws MalformedURLException {
        downloadPDF(ecli, year, month);

    }

    /**
     *
     */
    private void downloadPDF(String ecli, String year, String month) throws MalformedURLException {
        String[] parts = ecli.split(":");
        String str = parts[parts.length - 1];
        str = str.replace(".", "_");

        String pdfUrl = _firstUrlPart + year + "/" + month + "/" + str + _lastUrlPart;

        URL url = new URL(pdfUrl);
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get("resources/DecisionPDFs/" + "temp_document" + ".pdf"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // handle exception
        }

        PDFToTextConverter pdfToTextConverter = new PDFToTextConverter();
        pdfToTextConverter.convertPDFToText("resources/DecisionPDFs/temp_document.pdf");

        File f = new File("resources/DecisionPDFs/temp_document.pdf");
        f.delete();

        ArrayList<String> decision_parts = new ArrayList<>();
        try {
            File myObj = new File("resources/DecisionPDFs/temp_document.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String str_part = myReader.nextLine();
                decision_parts.add(str_part);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("hallo");

    }
}
