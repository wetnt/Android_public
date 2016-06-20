package bbk.map.dat;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class BBKXML {

	// =====================================================================================
	public static Document strXmlToDocument(String strDoc) {
		// --------------------------------------------------
		Document document = null;
		try {
			// ----------------------------------------------
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(strDoc));
			// ----------------------------------------------
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(is);
			// ----------------------------------------------
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		// --------------------------------------------------
		return document;
		// --------------------------------------------------
	}

	// ====================================================================================
	public static String xmltostring(Document document, String ItemName) {
		String Ret = "-";
		try {
			// --------------------------------------------------
			NodeList employeeInfo = document.getElementsByTagName(ItemName);
			if (employeeInfo == null) {
				return Ret;
			}
			Node node = employeeInfo.item(0);
			if (node == null) {
				return Ret;
			}
			NodeList employeeMeta = node.getChildNodes();
			Ret = employeeMeta.item(0).getTextContent();
			// --------------------------------------------------
		} catch (DOMException e) {
			Ret = e.toString();
		}
		// --------------------------------------------------
		return Ret;
		// --------------------------------------------------
	}

	// ====================================================================================

	// public static void XmlSave(String XmlName, String XmlData) {
	// // --------------------------------------------------
	// try {
	// FileWriter fw = new FileWriter(XmlName, false);
	// fw.write(XmlData);
	// fw.flush();
	// fw.close();
	// } catch (IOException e) {
	// }
	// // --------------------------------------------------
	// }

	// ====================================================================================

	// //
	// ====================================================================================
	// public static void XMLRead(String bbtName, Lay_type lay) {
	// //
	// -----------------------------------------------------------------------------
	// try {
	// File file = new File(BBK.asksPath, bbtName);
	// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	// DocumentBuilder docbuder = dbf.newDocumentBuilder();
	// Document doc = docbuder.parse(file);
	// //
	// -----------------------------------------------------------------------------
	// BBKXMK.DocumentToLays(doc, lay);
	// //
	// -----------------------------------------------------------------------------
	// } catch (SAXException e) {
	// } catch (ParserConfigurationException e) {
	// } catch (FileNotFoundException e) {
	// } catch (IOException e) {
	// }
	// }

	// //
	// ====================================================================================
	// public static String connServerForResult(String strUrl) {
	// // --------------------------------------------------
	// HttpGet httpRequest = new HttpGet(strUrl);
	// String strResult = "";
	// // --------------------------------------------------
	// try {
	// // --------------------------------------------------
	// HttpClient httpClient = new DefaultHttpClient();
	// HttpResponse httpResponse = httpClient.execute(httpRequest);
	// int statusCode = httpResponse.getStatusLine().getStatusCode();
	// if (statusCode == HttpStatus.SC_OK) {
	// strResult = EntityUtils.toString(httpResponse.getEntity());
	// // --------------------------------------------------
	// String XmlName = BBK.softPath + "temp.xml";
	// XmlSave(XmlName, strResult);
	// // System.out.println(strResult);
	// // Log.d("TAB", strResult);
	// return strResult;
	// // --------------------------------------------------
	// }
	// // --------------------------------------------------
	// } catch (ClientProtocolException e) {
	// return "err-strResult";
	// } catch (IOException e) {
	// return "err-strResult";
	// }
	// // --------------------------------------------------
	// return strResult;
	// // --------------------------------------------------
	// }
}
