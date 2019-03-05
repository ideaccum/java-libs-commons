package org.ideaccum.libs.commons.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.jar.JarFile;

import javax.swing.ImageIcon;

/**
 * リソース操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * クラスローダーから参照出来るリソース、ファイルシステム上のリソースへの操作で利用頻度の高い文字列操作をメソッドとして提供します。<br>
 * </p>
 *
 * @author Kitagawa<br>
 *
 *<!--
 * 更新日		更新者			更新内容
 * 2009/04/16	Kitagawa		新規作成
 * 2018/05/16	Kitagawa		再構築(最低保証バージョンをJava8として全面改訂)
 *-->
 */
public final class ResourceUtil {

	/** バイトバッファサイズ */
	private static final int BYTE_BUFFER = 2048;

	/**
	 * コンストラクタ<br>
	 */
	private ResourceUtil() {
		super();
	}

	/**
	 * スタティックイニシャライザ<br>
	 */
	static {
		// JUnit+EclEmmaによるコードカバレッジの為のダミーコード
		new ResourceUtil();
	}

	/**
	 * 指定されたリソースパスからリソースURLを取得します。<br>
	 * URL変換対象リソースパスはクラスローダから提供されるリソース、
	 * ファイルシステムから提供されるリソースの順序で検索が行われます。<br>
	 * @param name リソースパス
	 * @return 指定されたリソースパスのURLオブジェクト
	 * @throws IOException 指定されたリソース名からURLに変換することが出来なかった場合にスローされます
	 */
	@SuppressWarnings("deprecation")
	public static URL getURL(String name) throws IOException {
		Class<?> clazz = ResourceUtil.class;
		ClassLoader loader = null;
		URL url = null;

		/*
		 * カレントクラスローダーによるロード試行
		 */
		loader = Thread.currentThread().getContextClassLoader();
		if (loader != null) {
			if (url == null) {
				url = loader.getResource(name);
			}
			if (url == null) {
				url = loader.getResource("/" + name);
			}
			if (url == null) {
				url = loader.getResource("META-INF/" + name);
			}
			if (url == null && name.startsWith("META-INF/")) {
				url = loader.getResource("/" + name.substring(9));
			}
		}

		/*
		 * クラスローダーによるロード試行
		 */
		loader = clazz.getClassLoader();
		if (loader != null) {
			if (url == null) {
				url = loader.getResource(name);
			}
			if (url == null) {
				url = loader.getResource("/" + name);
			}
			if (url == null) {
				url = loader.getResource("META-INF/" + name);
			}
			if (url == null && name.startsWith("META-INF/")) {
				url = loader.getResource("/" + name.substring(9));
			}
		}

		/*
		 * クラスによるロード試行
		 */
		if (url == null) {
			url = clazz.getResource(name);
		}
		if (url == null) {
			url = clazz.getResource("/" + name);
		}
		if (url == null) {
			url = clazz.getResource("META-INF/" + name);
		}
		if (url == null && name.startsWith("META-INF/")) {
			url = clazz.getResource("/" + name.substring(9));
		}

		/*
		 * 物理ファイルによるロード試行
		 */
		if (url == null) {
			File file = new File(name);
			if (file.exists()) {
				url = file.toURL();
			} else {
				file = new File("classes/" + name);
				if (file.exists()) {
					url = file.toURL();
				}
			}
		}
		if (url == null) {
			File file = new File(name);
			url = file.toURL();
		}

		return url;
	}

	/**
	 * 指定されたリソースパスからリソースURIを取得します。<br>
	 * URI変換対象リソースパスはクラスローダから提供されるリソース、
	 * ファイルシステムから提供されるリソースの順序で検索が行われます。<br>
	 * @param name リソースパス
	 * @return 指定されたリソースパスのURIオブジェクト
	 * @throws IOException 指定されたリソース名からURLに変換することが出来なかった場合にスローされます
	 * @throws URISyntaxException URLからURIへの変換時に不正なURIパス文字列となった場合にスローされます
	 */
	public static URI getURI(String name) throws IOException, URISyntaxException {
		return getURL(name).toURI();
	}

	/**
	 * 指定されたリソースパスからリソースファイルオブジェクトを取得します。<br>
	 * URI変換対象リソースパスはクラスローダから提供されるリソース、
	 * ファイルシステムから提供されるリソースの順序で検索が行われます。<br>
	 * @param name リソースパス
	 * @return 指定されたリソースパスのファイルオブジェクトオブジェクト
	 * @throws IOException 指定されたリソース名からURLに変換することが出来なかった場合にスローされます
	 * @throws URISyntaxException URLからURIへの変換時に不正なURIパス文字列となった場合にスローされます
	 */
	public static File getFile(String name) throws IOException, URISyntaxException {
		//return new File(getURI(name));
		return new File(getURI(name).toURL().getFile());
	}

	/**
	 * 指定されたリソースパスからJarリソースファイルオブジェクトを取得します。<br>
	 * URI変換対象リソースパスはクラスローダから提供されるリソース、
	 * ファイルシステムから提供されるリソースの順序で検索が行われます。<br>
	 * @param name リソースパス
	 * @return 指定されたリソースパスのファイルオブジェクトオブジェクト
	 * @throws IOException 指定されたリソース名からURLに変換することが出来なかった場合にスローされます
	 * @throws URISyntaxException URLからURIへの変換時に不正なURIパス文字列となった場合にスローされます
	 */
	public static JarFile getJarFile(String name) throws IOException, URISyntaxException {
		String jarPath = getURI(name).toURL().getPath().substring(5, getURI(name).toURL().getPath().indexOf("!"));
		return new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
	}

	/**
	 * 呼び出し元クラスパッケージから見た相対リソース物理パスを取得します。<br>
	 * @param name リソース名
	 * @return リソース物理パス
	 * @throws IOException 指定されたリソース名からURLに変換することが出来なかった場合にスローされます
	 */
	public static String getPath(String name) throws IOException {
		String packagename;
		try {
			packagename = Class.forName(new Throwable().getStackTrace()[1].getClassName()).getPackage().getName();
		} catch (ClassNotFoundException e) {
			packagename = "java.lang";
		}
		packagename = packagename.replaceAll("\\.", "/");
		packagename = packagename + "/";
		return ResourceUtil.getURL(packagename + name).getFile();
	}

	/**
	 * 指定されたリソースがリソースパス又はファイルシステムに存在するか判定します。<br>
	 * @param name リソースパス
	 * @return 指定したリソースが存在する場合にtrueを返却します
	 * @throws IOException リソースの存在チェックが正しく行えなかった場合にスローされます
	 */
	public static boolean isExistResource(String name) throws IOException {
		URL url = null;
		URI uri = null;
		try {
			url = getURL(name);
			if (url == null) {
				return false;
			}
			uri = new URI(url.toString().replaceAll(" ", "%20"));
			if (uri != null && uri.toString().startsWith("jar:")) {
				return true;
			} else if (uri != null && uri.toString().startsWith("rsrc:")) {
				return true;
			} else if (uri != null && uri.toString().startsWith("vfszip:")) {
				return true;
			} else if (uri != null && uri.toString().startsWith("vfs:")) {
				return true;
			} else if (uri != null && uri.toString().startsWith("vfsfile:")) {
				return true;
			} else {
				return new File(new URI(getURL(name).toString().replaceAll(" ", "%20"))).exists();
			}
		} catch (Throwable e) {
			throw new IOException(name + " (" + e.getMessage() + ") / URL=" + url + " / URI=" + uri, e);
		}
	}

	/**
	 * 指定されたリソースがJarリソース内のものであるか判定します。<br>
	 * @param name リソースパス
	 * @return 指定されたリソースがJarリソース内のものである場合にtrueを返却
	 * @throws IOException リソース種別の判定が正しく行えなかった場合にスローされます
	 */
	public static boolean isJarFile(String name) throws IOException {
		return "jar".equals(getURL(name).getProtocol());
	}

	/**
	 * 指定されたリソースパスから入力ストリームを取得します。<br>
	 * @param name リソースパス
	 * @return 入力ストリームオブジェクト
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static InputStream getInputStream(String name) throws IOException {
		URL url = getURL(name);
		return url == null ? null : url.openStream();
	}

	/**
	 * 指定されたリソースパスから出力ストリームを取得します。<br>
	 * @param name リソースパス
	 * @return 出力ストリームオブジェクト
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static OutputStream getOutputStream(String name) throws IOException {
		//return new FileOutputStream(new File(getURL(name).getFile()));
		URL url = getURL(name);
		if ("file".equals(url.getProtocol())) {
			File file = new File(getURL(name).getFile());
			FileUtil.getDir(file).mkdirs();
			if (!file.exists()) {
				file.createNewFile();
			}
			return new FileOutputStream(file);
		} else {
			URLConnection connection = url == null ? null : url.openConnection();
			return connection == null ? null : connection.getOutputStream();
		}
	}

	/**
	 * 指定されたリソースパスからイメージを取得します。<br>
	 * @param name リソースパス
	 * @return イメージオブジェクト
	 * @throws IOException 不正なリソースパスが指定された場合にスローされます
	 */
	public static Image getImage(String name) throws IOException {
		return Toolkit.getDefaultToolkit().getImage(getURL(name));
	}

	/**
	 * 指定されたリソースパスからイメージアイコンを取得します。<br>
	 * @param name リソースパス
	 * @return イメージアイコンオブジェクト
	 * @throws IOException 不正なリソースパスが指定された場合にスローされます
	 */
	public static ImageIcon getImageIcon(String name) throws IOException {
		return new ImageIcon(getImage(name));
	}

	/**
	 * 指定されたリソースパスからテキストを取得します。<br>
	 * @param name リソースパス
	 * @param charset キャラクタセット
	 * @return リソース内に記述されている文字列
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static String getText(String name, String charset) throws IOException {
		BufferedReader reader = null;
		if (charset == null) {
			reader = new BufferedReader(new InputStreamReader(getInputStream(name)));
		} else {
			reader = new BufferedReader(new InputStreamReader(getInputStream(name), charset));
		}
		StringBuffer buffer = new StringBuffer();
		while (reader.ready()) {
			buffer.append(reader.readLine());
			if (reader.ready()) {
				buffer.append("\n");
			}
		}
		reader.close();
		return buffer.toString();
	}

	/**
	 * 指定されたリソースパスからテキストを取得します。<br>
	 * @param name リソースパス
	 * @return リソース内に記述されている文字列
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static String getText(String name) throws IOException {
		return getText(name, null);
	}

	/**
	 * 指定されたリソースパスに対してテキストを出力します。<br>
	 * @param name リソースパス
	 * @param text テキスト文字列
	 * @param charset キャラクタセット
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static void writeText(String name, String text, String charset) throws IOException {
		BufferedWriter writer = null;
		if (charset == null) {
			writer = new BufferedWriter(new OutputStreamWriter(getOutputStream(name)));
		} else {
			writer = new BufferedWriter(new OutputStreamWriter(getOutputStream(name), charset));
		}
		writer.write(text);
		writer.flush();
		writer.close();
	}

	/**
	 * 指定されたリソースパスに対してテキストを出力します。<br>
	 * @param name リソースパス
	 * @param text テキスト文字列
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static void writeText(String name, String text) throws IOException {
		writeText(name, text, null);
	}

	/**
	 * 指定されたリソースパスからバイトデータを取得します。<br>
	 * @param name リソースパス
	 * @return リソースデータバイト配列
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static byte[] getBytes(String name) throws IOException {
		InputStream inputStream = getInputStream(name);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int length = -1;
		byte[] buffer = new byte[BYTE_BUFFER];
		while ((length = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, length);
		}
		byte[] result = outputStream.toByteArray();

		inputStream.close();
		outputStream.close();

		return result;
	}

	/**
	 * 指定されたリソースパスからシリアライズされたオブジェクトを読み込みます。<br>
	 * @param name リソースパス
	 * @return 復元されたオブジェクト
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 * @throws ClassNotFoundException シリアライズオブジェクトのクラスが不明な場合にスローされます
	 */
	public static Object getObject(String name) throws IOException, ClassNotFoundException {
		ObjectInputStream inputStream = new ObjectInputStream(getInputStream(name));
		Object object = inputStream.readObject();
		inputStream.close();
		return object;
	}
}
