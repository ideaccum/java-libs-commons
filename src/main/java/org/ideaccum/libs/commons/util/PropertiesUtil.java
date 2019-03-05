package org.ideaccum.libs.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Locale;
import java.util.Properties;

/**
 * プロパティリソースに対する操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * {@link java.util.Properties}を利用したプロパティリソース読み込み時のコーディングをラップして単一行でのプロパティロードを行う為の操作や、
 * プロパティ値を取得する際に目的の方へのパースを行う処理をラップしたメソッド等を提供します。<br>
 * </p>
 *
 * @author Kitagawa<br>
 *
 *<!--
 * 更新日		更新者			更新内容
 * 2007/02/02	Kitagawa		新規作成
 * 2018/05/16	Kitagawa		再構築(最低保証バージョンをJava8として全面改訂)
 *-->
 */
public final class PropertiesUtil {

	/**
	 * コンストラクタ<br>
	 */
	private PropertiesUtil() {
		super();
	}

	/**
	 * スタティックイニシャライザ<br>
	 */
	static {
		// JUnit+EclEmmaによるコードカバレッジの為のダミーコード
		new PropertiesUtil();
	}

	/**
	 * ストリームオブジェクトからプロパティリソースを読み込みます。<br>
	 * @param stream 入力ストリームオブジェクト
	 * @return プロパティオブジェクト
	 * @throws IOException 読込み時に入出力例外が発生した場合にスローされます
	 */
	public static Properties load(InputStream stream) throws IOException {
		Properties properties = new Properties();
		if (stream == null) {
			return properties;
		}
		properties.load(stream);
		return properties;
	}

	/**
	 * リーダーオブジェクトからプロパティファイルを読み込みます。<br>
	 * @param reader リーダーオブジェクト
	 * @return プロパティオブジェクト
	 * @throws IOException 読込み時に入出力例外が発生した場合にスローされます
	 */
	public static Properties load(Reader reader) throws IOException {
		Properties properties = new Properties();
		if (reader == null) {
			return properties;
		}
		properties.load(reader);
		return properties;
	}

	/**
	 * 指定されたファイル名のプロパティファイルを読み込みます。<br>
	 * @param filename ファイル名
	 * @param locale ロケールを指定した場合プロパティリソース名のサフィックスに該当のロケール文字列を含むものを優先に読み込みます
	 * @return プロパティオブジェクト
	 * @throws IOException 読込み時に入出力例外が発生した場合にスローされます
	 */
	public static Properties load(String filename, Locale locale) throws IOException {
		String basename = filename.substring(0, filename.lastIndexOf("."));
		String target = null;
		if (locale != null) {
			target = basename + "_" + locale.getLanguage() + "_" + locale.getCountry() + ".properties";
			if (!ResourceUtil.isExistResource(target)) {
				target = basename + "_" + locale.getLanguage() + ".properties";
			}
			if (!ResourceUtil.isExistResource(target)) {
				target = basename + "_" + locale.getCountry() + ".properties";
			}
			if (!ResourceUtil.isExistResource(target)) {
				target = basename + ".properties";
			}
		} else {
			target = filename;
		}
		InputStream stream = ResourceUtil.getInputStream(target);
		if (stream != null) {
			try {
				return load(stream);
			} catch (Throwable e) {
				throw new IOException(e);
			} finally {
				stream.close();
			}
		} else {
			return new Properties();
		}
	}

	/**
	 * 指定されたファイル名のプロパティファイルを読み込みます。<br>
	 * @param filename ファイル名
	 * @return プロパティオブジェクト
	 * @throws IOException 読込み時に入出力例外が発生した場合にスローされます
	 */
	public static Properties load(String filename) throws IOException {
		return load(filename, null);
	}

	/**
	 * プロパティ値を文字列として取得します。<br>
	 * 正常にプロパティ値を取得できない場合は例外はスローせずに空文字を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param key プロパティキー
	 * @return プロパティ値
	 */
	public static String getString(Properties properties, String key) {
		String value = properties == null ? "" : properties.getProperty(key);
		return value == null ? "" : value;
	}

	/**
	 * 指定されたキーのプロパティ値をshort型として取得します。<br>
	 * 正常にプロパティ値を取得できない場合は例外はスローせずに0を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param key プロパティキー
	 * @return プロパティ値
	 */
	public static int getShort(Properties properties, String key) {
		String value = properties == null ? "" : properties.getProperty(key);
		return StringUtil.toPShort(value);
	}

	/**
	 * 指定されたキーのプロパティ値をint型として取得します。<br>
	 * 正常にプロパティ値を取得できない場合は例外はスローせずに0を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param key プロパティキー
	 * @return プロパティ値
	 */
	public static int getInteger(Properties properties, String key) {
		String value = properties == null ? "" : properties.getProperty(key);
		return StringUtil.toPInt(value);
	}

	/**
	 * 指定されたキーのプロパティ値をlong型として取得します。<br>
	 * 正常にプロパティ値を取得できない場合は例外はスローせずに0を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param key プロパティキー
	 * @return プロパティ値
	 */
	public static long getLong(Properties properties, String key) {
		String value = properties == null ? "" : properties.getProperty(key);
		return StringUtil.toPLong(value);
	}

	/**
	 * 指定されたキーのプロパティ値をfloat型として取得します。<br>
	 * 正常にプロパティ値を取得できない場合は例外はスローせずに0を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param key プロパティキー
	 * @return プロパティ値
	 */
	public static float getFloat(Properties properties, String key) {
		String value = properties == null ? "" : properties.getProperty(key);
		return StringUtil.toPFloat(value);
	}

	/**
	 * 指定されたキーのプロパティ値をfloat型として取得します。<br>
	 * 正常にプロパティ値を取得できない場合は例外はスローせずに0を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param key プロパティキー
	 * @return プロパティ値
	 */
	public static double getDouble(Properties properties, String key) {
		String value = properties == null ? "" : properties.getProperty(key);
		return StringUtil.toPDouble(value);
	}

	/**
	 * 指定されたキーのプロパティ値をboolean型として取得します。<br>
	 * 正常にプロパティ値を取得できない場合は例外はスローせずにfalseを返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param key プロパティキー
	 * @return プロパティ値
	 */
	public static boolean getBoolean(Properties properties, String key) {
		String value = properties == null ? "" : properties.getProperty(key);
		return StringUtil.toPBoolean(value);
	}
}
