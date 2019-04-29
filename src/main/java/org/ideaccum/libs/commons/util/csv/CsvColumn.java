package org.ideaccum.libs.commons.util.csv;

import java.io.Serializable;
import java.util.Arrays;

import org.ideaccum.libs.commons.util.StringUtil;

/**
 * CSVレコード上の単一カラムを管理するためのインタフェースを提供します。<br>
 * <p>
 * このクラスは項目の値をCSVカラム情報として管理するために設置されたクラスです。<br>
 * 利用者はCSVカラムに特化したエスケープ処理(ダブルクォートやカンマ、改行などに対するエスケープ処理)を意識せずに項目値の取得、設定を行います。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2007/02/16	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class CsvColumn implements Serializable {

	/** セパレータ文字 */
	public static final String SEPARATOR = ",";

	/** クォート文字 */
	public static final String QUOTE = "\"";

	/** 改行文字 */
	public static final String LINEFEED = "\n";

	/** カラム値 */
	private Object value;

	/** 強制クォート */
	private boolean forceQuote;

	/**
	 * コンストラクタ<br>
	 * @param value カラム値
	 * @param forceQuote 強制クォートフラグ
	 */
	public CsvColumn(Object value, boolean forceQuote) {
		super();
		this.value = value;
		this.forceQuote = forceQuote;
	}

	/**
	 * コンストラクタ<br>
	 * @param value カラム値
	 */
	public CsvColumn(Object value) {
		this(value, false);
	}

	/**
	 * コンストラクタ<br>
	 */
	public CsvColumn() {
		this(null);
	}

	/**
	 * 指定されたオブジェクトを文字列として提供します。<br>
	 * オブジェクトにnullが指定された場合は空文字列が返却されます。<br>
	 * @param object オブジェクト
	 * @return オブジェクトから編集された文字列
	 */
	private static String toString(Object object) {
		if (object == null) {
			return "";
		}
		String string = "";
		if (object.getClass().isArray()) {
			Class<?> clazz = object.getClass().getComponentType();
			if (clazz == String.class) {
				string = Arrays.toString((Object[]) object);
			} else if (clazz == boolean.class) {
				string = Arrays.toString((boolean[]) object);
			} else if (clazz == int.class) {
				string = Arrays.toString((int[]) object);
			} else if (clazz == long.class) {
				string = Arrays.toString((long[]) object);
			} else if (clazz == byte.class) {
				string = Arrays.toString((byte[]) object);
			} else if (clazz == short.class) {
				string = Arrays.toString((short[]) object);
			} else if (clazz == float.class) {
				string = Arrays.toString((float[]) object);
			} else if (clazz == double.class) {
				string = Arrays.toString((double[]) object);
			} else if (clazz == char.class) {
				string = Arrays.toString((char[]) object);
			} else {
				string = Arrays.toString((Object[]) object);
			}
		} else {
			string = object.toString();
		}
		return string;
	}

	/**
	 * オブジェクトをCSVカラム値として利用できる文字列にエンコードして提供します。<br>
	 * @param value エンコード対象値
	 * @param forceQuote クォートが不要なCSVカラム値であっても強制的にクォート文字で囲む場合はtrueを指定します
	 * @return CSVカラム値として利用できる文字列
	 */
	public static String encode(Object value, boolean forceQuote) {
		String string = toString(value);
		if (StringUtil.isEmpty(string)) {
			return "";
		} else {
			if (string.indexOf(QUOTE) >= 0 //
					|| string.indexOf(SEPARATOR) >= 0 //
					//|| string.indexOf(LINEFEED) >= 0 //
					|| string.indexOf(new String(new byte[] { 0x0D })) >= 0 //
					|| string.indexOf(new String(new byte[] { 0x0A })) >= 0 //
			) {
				StringBuilder builder = new StringBuilder();
				builder.append(QUOTE);
				builder.append(StringUtil.replace(string, QUOTE, QUOTE + QUOTE));
				builder.append(QUOTE);
				return builder.toString();
			} else if (forceQuote) {
				StringBuilder builder = new StringBuilder();
				builder.append(QUOTE);
				builder.append(StringUtil.replace(string, QUOTE, QUOTE + QUOTE));
				builder.append(QUOTE);
				return builder.toString();
			} else {
				return string;
			}
		}
	}

	/**
	 * オブジェクトをCSVカラム値として利用できる文字列にエンコードして提供します。<br>
	 * カラム値のクォート処理は必要な場合にのみ行われます。<br>
	 * @param value エンコード対象値
	 * @return CSVカラム値として利用できる文字列
	 */
	public static String encode(Object value) {
		return encode(value, false);
	}

	/**
	 * CSVカラムとして必要なエスケープ処理が行われた文字列を通常の文字列にデコードして提供します。<br>
	 * @param value CSVカラム文字列
	 * @return デコードされた通常文字列
	 */
	public static String decode(String value) {
		String buffer = value;
		if (buffer.startsWith(QUOTE) && buffer.endsWith(QUOTE)) {
			buffer = buffer.substring(1, buffer.length() - 1);
		}
		buffer = StringUtil.replace(buffer, QUOTE + QUOTE, QUOTE);
		return buffer;
	}

	/**
	 * クラス情報を文字列で取得します。<br>
	 * @return クラス情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toString(value);
	}

	/**
	 * CSVカラム値としての文字列を提供します。<br>
	 * @return CSVカラム値としての文字列
	 */
	public String toCsvValue() {
		return encode(toString(value), forceQuote);
	}

	/**
	 * カラム値を取得します。<br>
	 * @return カラム値
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * カラム値を設定します。<br>
	 * @param value カラム値
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 強制クォートを取得します。<br>
	 * @return 強制クォート
	 */
	public boolean isForceQuote() {
		return forceQuote;
	}

	/**
	 * 強制クォートを設定します。<br>
	 * @param forceQuote 強制クォート
	 */
	public void setForceQuote(boolean forceQuote) {
		this.forceQuote = forceQuote;
	}
}
