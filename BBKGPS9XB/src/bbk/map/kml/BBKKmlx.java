package bbk.map.kml;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;

import bbk.bbk.box.BBKSoft;
import bbk.map.abc.BBKMap;
import bbk.zzz.debug.BBKDebug;

import android.util.Log;

public class BBKKmlx {

	public static void runx() {
		String PathName = "D:/!项目/512/实际测试数据/333/678.kml";
		NavigationDataSet ss;
		String str = null;
		try {
			ss = BBKKmlx.getPerson(PathName);
			str = ss.getPlacemarks().get(0).title;
		} catch (Exception e) {
			e.printStackTrace();
		}
		BBKDebug.ddd(str);
		BBKSoft.MapFlash(true);
	}

	public static NavigationDataSet getPerson(String filestr) throws Exception {

		// NavigationDataSet navigationDataSet = null;
		// SAXParserFactory spf = SAXParserFactory.newInstance();
		// SAXParser sp = spf.newSAXParser();
		// XMLReader xr = sp.getXMLReader();
		// NavigationSaxHandler navSax2Handler = new NavigationSaxHandler();
		// xr.setContentHandler(navSax2Handler);
		// xr.parse(filestr);
		// navigationDataSet = navSax2Handler.getParsedData();
		// return navSax2Handler.getParsedData();

		NavigationSaxHandler navSax2Handler = new NavigationSaxHandler();
		navSax2Handler.startDocument();

		return navSax2Handler.getParsedData();

	}

	// public static void main(String[] args) throws Exception {
	// System.out.println("dddddddddddd:" + getPerson().size());
	// for (int i = 0; i < getPerson().size(); i++) {
	// Placemark person = getPerson().get(i);
	// System.out.println("name:" + person.getAddress() + " Address:" +
	// person.getCoordinates());
	// }
	// }
}