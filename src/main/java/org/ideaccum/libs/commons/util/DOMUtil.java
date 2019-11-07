package org.ideaccum.libs.commons.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.dom4j.io.DOMReader;
import org.dom4j.io.DOMWriter;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * ドキュメントオブジェクトモデルに対する簡易的な操作インタフェースを提供します。<br>
 * <p>
 * 当クラスは{@link org.w3c.dom.Document}及び、{@link org.dom4j.Document}を利用したドキュメントオブジェクトモデルの操作インタフェースを提供します。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2010/07/03  Kitagawa         新規作成
 * 2018/05/24  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class DOMUtil {

	/**
	 * コンストラクタ<br>
	 */
	private DOMUtil() {
		super();
	}

	/**
	 * XMLソースからW3Cドキュメントオブジェクトを提供します。<br>
	 * @param source XMLソース文字列
	 * @return W3Cドキュメントオブジェクト
	 * @throws ParserConfigurationException SAXパーサーの初期化に失敗した場合にスローされます
	 * @throws SAXException 指定されたXMLソースのパースに失敗した場合にスローされます
	 * @throws IOException XMLソースの読み込みに失敗した場合にスローされます
	 */
	public static org.w3c.dom.Document toW3CDocument(String source) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false); // default 'false'
		factory.setNamespaceAware(true); // default 'false'
		factory.setIgnoringElementContentWhitespace(false); // default 'false'
		factory.setExpandEntityReferences(true); // default 'true'
		factory.setIgnoringComments(false); // default 'false'
		factory.setCoalescing(false); // default 'false'
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new InputSource(new StringReader(source)));
	}

	/**
	 * XMLソースからDOM4Jドキュメントオブジェクトを提供します。<br>
	 * @param source XMLソース文字列
	 * @return DOM4Jドキュメントオブジェクト
	 * @throws ParserConfigurationException SAXパーサーの初期化に失敗した場合にスローされます
	 * @throws SAXException 指定されたXMLソースのパースに失敗した場合にスローされます
	 * @throws IOException XMLソースの読み込みに失敗した場合にスローされます
	 */
	public static org.dom4j.Document toDOM4JDocument(String source) throws ParserConfigurationException, SAXException, IOException {
		return convert(toW3CDocument(source));
	}

	/**
	 * XMLファイルからW3Cドキュメントオブジェクトを提供します。<br>
	 * @param xmlFile XMLファイルパス
	 * @return W3Cドキュメントオブジェクト
	 * @throws ParserConfigurationException SAXパーサーの初期化に失敗した場合にスローされます
	 * @throws SAXException 指定されたXMLファイルのパースに失敗した場合にスローされます
	 * @throws IOException XMLファイルの読み込みに失敗した場合にスローされます
	 */
	public static org.w3c.dom.Document loadByW3CFile(String xmlFile) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false); // default 'false'
		factory.setNamespaceAware(true); // default 'false'
		factory.setIgnoringElementContentWhitespace(false); // default 'false'
		factory.setExpandEntityReferences(true); // default 'true'
		factory.setIgnoringComments(false); // default 'false'
		factory.setCoalescing(false); // default 'false'
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(ResourceUtil.getInputStream(xmlFile));
	}

	/**
	 * XMLファイルからW3Cドキュメントオブジェクトを提供します。<br>
	 * @param xmlFile XMLファイル
	 * @return W3Cドキュメントオブジェクト
	 * @throws ParserConfigurationException SAXパーサーの初期化に失敗した場合にスローされます
	 * @throws SAXException 指定されたXMLファイルのパースに失敗した場合にスローされます
	 * @throws IOException XMLファイルの読み込みに失敗した場合にスローされます
	 */
	public static org.w3c.dom.Document loadByW3CFile(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
		return loadByW3CFile(xmlFile.getAbsolutePath());
	}

	/**
	 * XMLファイルからDOM4Jドキュメントオブジェクトを提供します。<br>
	 * @param xmlFile XMLファイルパス
	 * @return DOM4Jドキュメントオブジェクト
	 * @throws ParserConfigurationException SAXパーサーの初期化に失敗した場合にスローされます
	 * @throws SAXException 指定されたXMLファイルのパースに失敗した場合にスローされます
	 * @throws IOException XMLファイルの読み込みに失敗した場合にスローされます
	 */
	public static org.dom4j.Document loadByDOM4JFile(String xmlFile) throws ParserConfigurationException, SAXException, IOException {
		return convert(loadByW3CFile(xmlFile));
	}

	/**
	 * XMLファイルからDOM4Jドキュメントオブジェクトを提供します。<br>
	 * @param xmlFile XMLファイル
	 * @return DOM4Jドキュメントオブジェクト
	 * @throws ParserConfigurationException SAXパーサーの初期化に失敗した場合にスローされます
	 * @throws SAXException 指定されたXMLファイルのパースに失敗した場合にスローされます
	 * @throws IOException XMLファイルの読み込みに失敗した場合にスローされます
	 */
	public static org.dom4j.Document loadByDOM4JFile(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
		return convert(loadByW3CFile(xmlFile));
	}

	/**
	 * XMLソースがドキュメントオブジェクトとしてパース可能なソースであるか判定します。<br>
	 * @param source XMLソース文字列
	 * @return ドキュメントオブジェクトとして利用可能なXMLソースである場合にtrueを返却
	 */
	public static boolean isValidXMLSource(String source) {
		try {
			toDOM4JDocument(source);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * XMLファイルがドキュメントオブジェクトとしてパース可能なソースであるか判定します。<br>
	 * @param xmlFile XMLファイル
	 * @return ドキュメントオブジェクトとして利用可能なXMLソースである場合にtrueを返却
	 */
	public static boolean isValidXMLFile(String xmlFile) {
		try {
			loadByDOM4JFile(xmlFile);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * XMLファイルがドキュメントオブジェクトとしてパース可能なソースであるか判定します。<br>
	 * @param xmlFile XMLファイル
	 * @return ドキュメントオブジェクトとして利用可能なXMLソースである場合にtrueを返却
	 */
	public static boolean isValidXMLFile(File xmlFile) {
		try {
			loadByDOM4JFile(xmlFile);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * DOM4JドキュメントをW3Cドキュメントに変換します。<br>
	 * @param document DOM4Jドキュメントオブジェクト
	 * @return W3Cドキュメントオブジェクト 
	 * @throws DocumentException ドキュメントオブジェクトの変換に失敗した場合にスローされます
	 */
	public static org.w3c.dom.Document convert(org.dom4j.Document document) throws DocumentException {
		DOMWriter writer = new DOMWriter();
		return writer.write(document);
	}

	/**
	 * W3CドキュメントをDOM4Jドキュメントに変換します。<br>
	 * @param document W3Cドキュメントオブジェクト
	 * @return DOM4Jドキュメントオブジェクト 
	 */
	public static org.dom4j.Document convert(org.w3c.dom.Document document) {
		DOMReader reader = new DOMReader();
		org.dom4j.Document dom4jDoc = reader.read(document);
		dom4jDoc.setXMLEncoding(document.getXmlEncoding()); // DOM4jパース後encodingが消えてしまう為
		return dom4jDoc;
	}

	/**
	 * ドキュメントオブジェクトをXML文字列として提供します。<br>
	 * @param document ドキュメントオブジェクト
	 * @param charset キャラクタセット
	 * @return XML文字列
	 * @throws IOException XMLリソースの出力時に入出力例外が発生した場合にスローされます
	 */
	public static String toXMLString(org.dom4j.Document document, String charset) throws IOException {
		if (charset == null) {
			charset = document.getXMLEncoding();
		}
		StringWriter writer = new StringWriter();
		if (charset == null) {
			new XMLWriter(writer, new OutputFormat("  ", true)).write(document);
		} else {
			new XMLWriter(writer, new OutputFormat("  ", true, charset)).write(document);
		}
		return writer.toString();
	}

	/**
	 * ドキュメントオブジェクトをXML文字列として提供します。<br>
	 * @param document ドキュメントオブジェクト
	 * @return XML文字列
	 * @throws IOException XMLリソースの出力時に入出力例外が発生した場合にスローされます
	 */
	public static String toXMLString(org.dom4j.Document document) throws IOException {
		return toXMLString(document, null);
	}

	/**
	 * ドキュメントオブジェクトをXML文字列として提供します。<br>
	 * @param document ドキュメントオブジェクト
	 * @return XML文字列
	 * @throws IOException XMLリソースの出力時に入出力例外が発生した場合にスローされます
	 */
	public static String toXMLString(org.w3c.dom.Document document) throws IOException {
		return toXMLString(convert(document), null);
	}

	/**
	 * 指定されたパスのエレメントを取得します。<br>
	 * 検索パスは、"Path0/Path1/Path2..."の形式で指定します。<br>
	 * @param document ドキュメントオブジェクト
	 * @param path 検索パス
	 * @return 検索対象エレメント
	 */
	public static org.dom4j.Element[] finds(org.dom4j.Document document, String path) {
		if (document == null || path == null || path.length() == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (String name : path.split("/")) {
			name = StringUtil.nvl(name).trim();
			if (StringUtil.isEmpty(name)) {
				continue;
			}
			buffer.append("/*[local-name()=\"");
			buffer.append(name);
			buffer.append("\"]");
		}
		String xpath = buffer.toString();
		org.dom4j.Node[] nodes = findsByXPath(document, xpath);
		List<org.dom4j.Element> list = new LinkedList<org.dom4j.Element>();
		for (org.dom4j.Node node : nodes) {
			list.add((org.dom4j.Element) node);
		}
		return list.toArray(new org.dom4j.Element[0]);
	}

	/**
	 * 指定されたパスのエレメントを取得します。<br>
	 * 検索パスは、"Path0/Path1/Path2..."の形式で指定します。<br>
	 * @param element ドキュメントオブジェクト
	 * @param path 検索パス
	 * @return 検索対象エレメント
	 */
	public static org.dom4j.Element[] finds(org.dom4j.Element element, String path) {
		if (element == null || path == null || path.length() == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (String name : path.split("/")) {
			name = StringUtil.nvl(name).trim();
			if (StringUtil.isEmpty(name)) {
				continue;
			}
			buffer.append("/*[local-name()=\"");
			buffer.append(name);
			buffer.append("\"]");
		}
		String xpath = buffer.toString();
		org.dom4j.Node[] nodes = findsByXPath(element, xpath);
		List<org.dom4j.Element> list = new LinkedList<org.dom4j.Element>();
		for (org.dom4j.Node node : nodes) {
			list.add((org.dom4j.Element) node);
		}
		return list.toArray(new org.dom4j.Element[0]);
	}

	/**
	 * 指定されたパスの先頭エレメントを取得します。<br>
	 * 検索パスは、"Path0/Path1/Path2..."の形式で指定します。<br>
	 * @param document ドキュメントオブジェクト
	 * @param path 検索パス
	 * @return 検索対象エレメント
	 */
	public static org.dom4j.Element findAtFirst(org.dom4j.Document document, String path) {
		if (document == null || path == null || path.length() == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (String name : path.split("/")) {
			name = StringUtil.nvl(name).trim();
			if (StringUtil.isEmpty(name)) {
				continue;
			}
			buffer.append("/*[local-name()=\"");
			buffer.append(name);
			buffer.append("\"]");
			buffer.append("[1]");
		}
		String xpath = buffer.toString();
		org.dom4j.Node node = findAtFirstByXPath(document, xpath);
		return (org.dom4j.Element) node;
	}

	/**
	 * 指定されたパスの先頭エレメントを取得します。<br>
	 * 検索パスは、"Path0/Path1/Path2..."の形式で指定します。<br>
	 * @param element ドキュメントオブジェクト
	 * @param path 検索パス
	 * @return 検索対象エレメント
	 */
	public static org.dom4j.Element findAtFirst(org.dom4j.Element element, String path) {
		if (element == null || path == null || path.length() == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (String name : path.split("/")) {
			name = StringUtil.nvl(name).trim();
			if (StringUtil.isEmpty(name)) {
				continue;
			}
			buffer.append("/*[local-name()=\"");
			buffer.append(name);
			buffer.append("\"]");
			buffer.append("[1]");
		}
		String xpath = buffer.toString();
		org.dom4j.Node node = findAtFirstByXPath(element, xpath);
		return (org.dom4j.Element) node;
	}

	/**
	 * 指定されたパスのエレメントをXPathで取得します。<br>
	 * @param document ドキュメントオブジェクト
	 * @param xpath 検索パス
	 * @return 検索対象エレメント
	 */
	public static org.dom4j.Node[] findsByXPath(org.dom4j.Document document, String xpath) {
		if (document == null || xpath == null || xpath.length() == 0) {
			return null;
		}
		List<org.dom4j.Node> nodes = new LinkedList<org.dom4j.Node>();
		for (Object e : document.selectNodes(xpath)) {
			nodes.add((org.dom4j.Node) e);
		}
		return nodes.toArray(new org.dom4j.Node[0]);
	}

	/**
	 * 指定されたパスのエレメントをXPathで取得します。<br>
	 * @param element ドキュメントオブジェクト
	 * @param xpath 検索パス
	 * @return 検索対象エレメント
	 */
	public static org.dom4j.Node[] findsByXPath(org.dom4j.Element element, String xpath) {
		if (element == null || xpath == null || xpath.length() == 0) {
			return null;
		}
		List<org.dom4j.Node> nodes = new LinkedList<org.dom4j.Node>();
		for (Object e : element.selectNodes(xpath)) {
			nodes.add((org.dom4j.Node) e);
		}
		return nodes.toArray(new org.dom4j.Node[0]);
	}

	/**
	 * 指定されたパスの先頭エレメントをXPathで取得します。<br>
	 * @param document ドキュメントオブジェクト
	 * @param xpath 検索パス
	 * @return 検索対象エレメント
	 */
	public static org.dom4j.Node findAtFirstByXPath(org.dom4j.Document document, String xpath) {
		if (document == null || xpath == null || xpath.length() == 0) {
			return null;
		}
		return document.selectSingleNode(xpath);
	}

	/**
	 * 指定されたパスの先頭エレメントをXPathで取得します。<br>
	 * @param element ドキュメントオブジェクト
	 * @param xpath 検索パス
	 * @return 検索対象エレメント
	 */
	public static org.dom4j.Node findAtFirstByXPath(org.dom4j.Element element, String xpath) {
		if (element == null || xpath == null || xpath.length() == 0) {
			return null;
		}
		return element.selectSingleNode(xpath);
	}

	/**
	 * エレメントに対して名前空間情報を追加します。<br>
	 * @param element エレメントオブジェクト
	 * @param namespaces 名前空間オブジェクト配列
	 */
	public static void addNamespaces(org.dom4j.Element element, org.dom4j.Namespace... namespaces) {
		if (element == null || namespaces == null || namespaces.length == 0) {
			return;
		}
		for (org.dom4j.Namespace namespace : namespaces) {
			if (namespace != null) {
				element.addNamespace(namespace.getPrefix(), namespace.getURI());
			}
		}
	}
}
