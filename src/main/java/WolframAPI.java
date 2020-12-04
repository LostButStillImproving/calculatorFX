import javafx.application.Application;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WolframAPI {

    String appID = "UP6GR5-H4GLRGT249";

    public String ask(String query) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        URL url = new URL("http://api.wolframalpha.com/v2/query?appid=" + appID + "&input="
                + query.replaceAll(" ", "%20") + "&podindex=1,2");
        Document doc = db.parse(url.openStream());
        NodeList nodes = doc.getElementsByTagName("plaintext");
        String result = "";

        for (int i = 0; i < nodes.getLength(); i++) {

            result += nodes.item(i).getTextContent() + "\n";
        }

        return result;
    }
}
