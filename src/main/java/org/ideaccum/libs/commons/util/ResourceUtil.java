package org.ideaccum.libs.commons.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.jar.JarFile;

import javax.swing.ImageIcon;

/**
 * リソース操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * クラスローダーから参照出来るリソース、ファイルシステム上のリソースへの操作で利用頻度の高い文字列操作をメソッドとして提供します。<br>
 * </p>
 *
 *<!--
 * 更新日      更新者           更新内容
 * 2009/04/16  Kitagawa         新規作成
 * 2018/05/16  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class ResourceUtil {

	/**
	 * コンストラクタ<br>
	 */
	private ResourceUtil() {
		super();
	}

	/**
	 * リソースパス文字列からリソースにアクセスする際のURLオブジェクトを取得します。<br>
	 * 当処理におけるリソースパスの検索は下記の優先順序で取得試行を行い、取得が行えたタイミングのURLを返却します。<br>
	 * 下記の順序で存在するリソースが見つからなかった場合、必ず指定されたパスを物理パスと見立てたファイルオブジェクトが提供するURLが返却されることに注意してください	。<br>
	 * <ul>
	 * <li>指定リソースパスでのリソース取得</li>
	 * <li>指定リソースパスの先頭に"/"を付与したパスでのリソース取得</li>
	 * <li>指定リソースパスの先頭に"META-INF/"を付与したパスでのリソース取得</li>
	 * <li>指定リソースパスの先頭が"META-INF/"の場合にそれを除いたパスでのリソース取得</li>
	 * <li>指定リソースパスを物理パスに見立てたリソース取得</li>
	 * </ul>
	 * @param path リソースパス
	 * @return 指定されたリソースパスのURLオブジェクト
	 */
	public static URL getURL(String path) {
		ClassLoader loader = null;
		URL url = null;

		/*
		 * パス文字列補正
		 */
		path = path.replaceAll("//", "/");
		path = path.replaceAll("\\\\\\\\", "\\");

		/*
		 * カレントクラスローダーによるロード試行
		 */
		loader = Thread.currentThread().getContextClassLoader();
		if (loader != null) {
			if (url == null) {
				url = loader.getResource(path);
			}
			if (url == null) {
				url = loader.getResource("/" + path);
			}
			if (url == null) {
				url = loader.getResource("META-INF/" + path);
			}
			if (url == null && path.startsWith("META-INF/")) {
				url = loader.getResource("/" + path.substring(9));
			}
		}

		/*
		 * システムクラスローダーによるロード試行
		 */
		loader = ClassLoader.getSystemClassLoader();
		if (loader != null) {
			if (url == null) {
				url = loader.getResource(path);
			}
			if (url == null) {
				url = loader.getResource("/" + path);
			}
			if (url == null) {
				url = loader.getResource("META-INF/" + path);
			}
			if (url == null && path.startsWith("META-INF/")) {
				url = loader.getResource("/" + path.substring(9));
			}
		}

		/*
		 * 当クラスによるロード試行
		 */
		if (url == null) {
			url = ResourceUtil.class.getResource(path);
		}
		if (url == null) {
			url = ResourceUtil.class.getResource("/" + path);
		}
		if (url == null) {
			url = ResourceUtil.class.getResource("META-INF/" + path);
		}
		if (url == null && path.startsWith("META-INF/")) {
			url = ResourceUtil.class.getResource("/" + path.substring(9));
		}

		/*
		 * 物理ファイルによるロード試行
		 */
		if (url == null) {
			File file = new File(path);
			if (file.exists()) {
				try {
					url = file.toURI().toURL();
				} catch (MalformedURLException e) {
					// Resume next
				}
			} else {
				file = new File("classes/" + path);
				if (file.exists()) {
					try {
						url = file.toURI().toURL();
					} catch (MalformedURLException e) {
						// Resume next
					}
				}
			}
		}
		if (url == null) {
			File file = new File(path);
			try {
				url = file.toURI().toURL();
			} catch (MalformedURLException e) {
				// Resume next
			}
		}

		return url;
	}

	/**
	 * リソースパス文字列からリソースにアクセスする際のURIオブジェクトを取得します。<br>
	 * 指定されたリソースパスに対するリソースの検索方法は{@link #getURL(String)}の挙動に依存します。<br>
	 * @param path リソースパス
	 * @return 指定されたリソースパスのURIオブジェクト
	 */
	public static URI getURI(String path) {
		URL url = getURL(path);
		try {
			return url.toURI();
		} catch (URISyntaxException e) {
			// この処理におけるURISyntaxExceptionは発生しない想定
			throw new RuntimeException(e);
		}
	}

	/**
	 * リソースパス文字列からリソースにアクセスする際のファイルオブジェクトを取得します。<br>
	 * 指定されたリソースパスに対するリソースの検索方法は{@link #getURL(String)}の挙動に依存します。<br>
	 * @param path リソースパス
	 * @return 指定されたリソースパスのファイルオブジェクト
	 */
	public static File getFile(String path) {
		URI uri = getURI(path);
		//return Paths.get(uri).toFile();
		return new File(uri);
	}

	/**
	 * リソースパス文字列からリソースにアクセスする際のJarファイルオブジェクトを取得します。<br>
	 * 指定されたリソースパスに対するリソースの検索方法は{@link #getURL(String)}の挙動に依存します。<br>
	 * @param path リソースパス
	 * @return 指定されたリソースパスのJarファイルオブジェクト、但し、リソースパスがJarファイルでない場合や存在しないパスの場合はnullが返却されます
	 * @throws IOException リソースパスからJarファイルリソースパスとして正しくパス提供が行えない場合にスローれます
	 */
	public static JarFile getJarFile(String path) throws IOException {
		URL url = getURL(path);
		String urlPath = url.getPath();
		if (urlPath.indexOf(".jar!") < 0) {
			return null;
		}
		String jarPath = urlPath.substring(5, urlPath.indexOf("!"));
		return new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
	}

	/**
	 * リソースパスのリソースが提供される形式を取得します。<br>
	 * @param path リソースパス
	 * @return リソースが提供される形式
	 */
	public static String getProtocol(String path) {
		URL url = getURL(path);
		return url.getProtocol();
	}

	/**
	 * リソースパスに存在するリソースの入力ストリームを取得します。<br>
	 * @param path リソースパス
	 * @return 入力ストリームオブジェクト
	 * @throws IOException リソース入力ストリームをオープンすることができなかった場合にスローされます
	 */
	public static InputStream getInputStream(String path) throws IOException {
		URL url = getURL(path);
		return url.openStream();
	}

	/**
	 * リソースパスに存在するリソースの出力ストリームを取得します。<br>
	 * @param path リソースパス
	 * @return 出力ストリームオブジェクト
	 * @throws IOException リソース出力ストリームをオープンすることができなかった場合にスローされます
	 */
	public static OutputStream getOutputStream(String path) throws IOException {
		URL url = getURL(path);
		if ("file".equals(url.getProtocol())) {
			File file = new File(getURL(path).getFile());
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
	 * リソースパスに存在するリソースをイメージリソースとして取得します。<br>
	 * @param path リソースパス
	 * @return イメージオブジェクト
	 */
	public static Image getImage(String path) {
		return Toolkit.getDefaultToolkit().getImage(getURL(path));
	}

	/**
	 * リソースパスに存在するリソースをイメージアイコンリソースとして取得します。<br>
	 * @param path リソースパス
	 * @return イメージアイコンオブジェクト
	 */
	public static ImageIcon getImageIcon(String path) {
		return new ImageIcon(getImage(path));
	}

	/**
	 * リソースパスに存在するリソースをテキストファイルとして取得します。<br>
	 * @param path リソースパス
	 * @param charset キャラクタセット
	 * @return テキストファイル内容文字列
	 * @throws IOException リソースに対する入出力操作でエラーが発生した場合にスローされます
	 */
	public static String getText(String path, String charset) throws IOException {
		InputStream is = null;
		try {
			is = getInputStream(path);
			String text = StreamUtil.readString(is, charset);
			return text;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * リソースパスに存在するリソースをテキストファイルとして取得します。<br>
	 * @param path リソースパス
	 * @return テキストファイル内容文字列
	 * @throws IOException リソースに対する入出力操作でエラーが発生した場合にスローされます
	 */
	public static String getText(String path) throws IOException {
		return getText(path, null);
	}

	/**
	 * リソースパスに存在するリソースをバイトデータとして取得します。<br>
	 * @param path リソースパス
	 * @return バイト配列
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static byte[] getBytes(String path) throws IOException {
		InputStream is = null;
		try {
			is = getInputStream(path);
			byte[] data = StreamUtil.reads(is);
			return data;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * リソースパスに存在するリソースをシリアライズされたオブジェクトとして取得します。<br>
	 * @param path リソースパス
	 * @return オブジェクト
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 * @throws ClassNotFoundException シリアライズオブジェクトのクラスが不明な場合にスローされます
	 */
	public static Object getObject(String path) throws IOException, ClassNotFoundException {
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(getInputStream(path));
			Object object = is.readObject();
			return object;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * リソースが参照できる場所に存在するか判定します。<br>
	 * @param path リソースパス
	 * @return リソースが参照できる場所に存在する場合にtrueを返却します
	 */
	public static boolean exists(String path) {
		URL url = getURL(path);
		if ("file".equals(url.getProtocol())) {
			return getFile(path).exists();
		} else {
			return true;
		}
	}

	/**
	 * リソースパスのリソースがJarファイルに内包されて提供されるリソースであるかを判定します。<br>
	 * @param path リソースパス
	 * @return Jarファイルに内包されて提供されるリソースである場合にtrueを返却
	 */
	public static boolean isJarProvide(String path) {
		return "jar".equals(getProtocol(path));
	}
}
