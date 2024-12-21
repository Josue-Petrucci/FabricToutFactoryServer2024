package be.petrucci.connection;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XmlReader {
	public static Map<String, String> readContextParams(String filePath){
		Map<String, String> params = new HashMap<>();
		
		try {
			File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();
            
            NodeList paramNodes = doc.getElementsByTagName("context-param");
            
            for (int i = 0; i < paramNodes.getLength(); i++) {
                Node node = paramNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String paramName = element.getElementsByTagName("param-name").item(0).getTextContent();
                    String paramValue = element.getElementsByTagName("param-value").item(0).getTextContent();

                    params.put(paramName, paramValue);
                }
            }
		}catch (Exception e) {
			throw new RuntimeException("Erreur lors de la lecture du fichier XML : " + e.getMessage());
		}
		return params;
	}
}