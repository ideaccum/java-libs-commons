package org.ideaccum.libs.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Locale;
import java.util.Properties;

/**
 * プロパティリソースに対する操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * 単一行でのプロパティロードを行う為の操作や目的型でのプロパティ値を取得メソッド等を提供します。<br>
 * </p>
 *
 * @author Kitagawa<br>
 *
 *<!--
 * 更新日		更新者			更新内容
 * 2007/02/02	Kitagawa		新規作成
 * 2018/05/16	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
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
	 * 入力ストリームからプロパティリソースを読み込みます。<br>
	 * @param stream 入力ストリームオブジェクト
	 * @return プロパティオブジェクト
	 * @throws IOException プロパティリソースの読込み時に失敗した場合にスローされます
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
	 * 入力ストリームからプロパティリソースを読み込みます。<br>
	 * @param reader 入力ストリームオブジェクト
	 * @return プロパティオブジェクト
	 * @throws IOException プロパティリソースの読込み時に失敗した場合にスローされます
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
	 * リソースパスに存在するプロパティリソースを読み込みます。<br>
	 * @param path プロパティリソースパス
	 * @param locale ロケールを指定した場合プロパティリソース名のサフィックスに該当のロケール文字列を含むものを優先に読み込みます
	 * @return プロパティオブジェクト
	 * @throws IOException プロパティリソースの読込み時に失敗した場合にスローされます
	 */
	public static Properties load(String path, Locale locale) throws IOException {
		String basename = path.substring(0, path.lastIndexOf("."));
		String target = null;
		if (locale != null) {
			target = basename + "_" + locale.getLanguage() + "_" + locale.getCountry() + ".properties";
			if (!ResourceUtil.exists(target)) {
				target = basename + "_" + locale.getLanguage() + ".properties";
			}
			if (!ResourceUtil.exists(target)) {
				target = basename + "_" + locale.getCountry() + ".properties";
			}
			if (!ResourceUtil.exists(target)) {
				target = basename + ".properties";
			}
		} else {
			target = path;
		}
		InputStream stream = ResourceUtil.getInputStream(target);
		if (stream != null) {
			try {
				return load(stream);
			} finally {
				stream.close();
			}
		} else {
			return new Properties();
		}
	}

	/**
	 * リソースパスに存在するプロパティリソースを読み込みます。<br>
	 * @param path プロパティリソースパス
	 * @return プロパティオブジェクト
	 * @throws IOException プロパティリソースの読込み時に失敗した場合にスローされます
	 */
	public static Properties load(String path) throws IOException {
		return load(path, Locale.getDefault());
	}

	/**
	 * プロパティ値を文字列として取得します。<br>
	 * プロパティが存在しない場合は空文字を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param name プロパティ名
	 * @return プロパティ値
	 */
	public static String getString(Properties properties, String name) {
		String value = properties == null ? "" : properties.getProperty(name);
		return value == null ? "" : value;
	}

	/**
	 * 指定されたキーのプロパティ値をshort型として取得します。<br>
	 * プロパティが存在しない場合は0を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param name プロパティ名
	 * @return プロパティ値
	 */
	public static int getShort(Properties properties, String name) {
		String value = properties == null ? "" : properties.getProperty(name);
		return StringUtil.toPShort(value);
	}

	/**
	 * 指定されたキーのプロパティ値をint型として取得します。<br>
	 * プロパティが存在しない場合は0を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param name プロパティ名
	 * @return プロパティ値
	 */
	public static int getInteger(Properties properties, String name) {
		String value = properties == null ? "" : properties.getProperty(name);
		return StringUtil.toPInt(value);
	}

	/**
	 * 指定されたキーのプロパティ値をlong型として取得します。<br>
	 * プロパティが存在しない場合は0を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param name プロパティ名
	 * @return プロパティ値
	 */
	public static long getLong(Properties properties, String name) {
		String value = properties == null ? "" : properties.getProperty(name);
		return StringUtil.toPLong(value);
	}

	/**
	 * 指定されたキーのプロパティ値をfloat型として取得します。<br>
	 * プロパティが存在しない場合は0を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param name プロパティ名
	 * @return プロパティ値
	 */
	public static float getFloat(Properties properties, String name) {
		String value = properties == null ? "" : properties.getProperty(name);
		return StringUtil.toPFloat(value);
	}

	/**
	 * 指定されたキーのプロパティ値をdouble型として取得します。<br>
	 * プロパティが存在しない場合は0を返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param name プロパティ名
	 * @return プロパティ値
	 */
	public static double getDouble(Properties properties, String name) {
		String value = properties == null ? "" : properties.getProperty(name);
		return StringUtil.toPDouble(value);
	}

	/**
	 * 指定されたキーのプロパティ値をboolean型として取得します。<br>
	 * プロパティが存在しない場合はfalseを返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param name プロパティ名
	 * @return プロパティ値
	 */
	public static boolean getBoolean(Properties properties, String name) {
		String value = properties == null ? "" : properties.getProperty(name);
		return StringUtil.toPBoolean(value);
	}

	/**
	 * 指定されたキーのプロパティ値を指定された列挙型として取得します。<br>
	 * プロパティが存在しない場合や一致する列挙型が存在しない場合はnullを返却します。<br>
	 * @param properties 対象プロパティオブジェクト
	 * @param name プロパティ名
	 * @return プロパティ値
	 */
	public static <E extends Enum<E>> E getEnum(Properties properties, String name, Class<E> enumClass) {
		String value = properties == null ? "" : properties.getProperty(name);
		if (enumClass == null) {
			return null;
		}
		for (E e : enumClass.getEnumConstants()) {
			if (StringUtil.equalsIgnoreCase(e.name(), value)) {
				return e;
			}
		}
		return null;
	}
}
