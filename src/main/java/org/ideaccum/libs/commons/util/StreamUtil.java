package org.ideaccum.libs.commons.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

/**
 * 入出力ストリーム操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * システム開発時に利用するストリーム操作で利用頻度の高い文字列操作をメソッドとして提供します。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2008/11/05	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		再構築(最低保証バージョンをJava8として全面改訂)
 *-->
 */
public final class StreamUtil {

	/** ストリームバッファサイズ */
	public static final int BUFFER_SIZE = 2048;

	/**
	 * コンストラクタ<br>
	 */
	private StreamUtil() {
		super();
	}

	/**
	 * スタティックイニシャライザ<br>
	 */
	static {
		// JUnit+EclEmmaによるコードカバレッジの為のダミーコード
		new StreamUtil();
	}

	/**
	 * 入力ストリームが提供するデータを別の出力ストリームに転送します。<br>
	 * @param is 入力ストリームオブジェクト
	 * @param os 出力ストリームオブジェクト
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	@SuppressWarnings("resource")
	public static void pipe(InputStream is, OutputStream os) throws IOException {
		if (is == null) {
			return;
		}
		BufferedOutputStream bos = os == null ? null : new BufferedOutputStream(os);
		while (true) {
			byte[] data = new byte[BUFFER_SIZE];
			int readedsize = is.read(data);
			if (readedsize == -1) {
				break;
			}
			if (bos != null) {
				bos.write(data, 0, readedsize);
				bos.flush();
			}
		}
		if (bos != null) {
			bos.flush();
		}
	}

	/**
	 * 入力ストリームから指定された長さのバイトデータを取得します。<br>
	 * 尚、指定されたサイズまでのデータが存在しなかった場合は、読み込めたデータ長のバイト配列が返却されます。<br>
	 * また、取得長に0以下の数値を指定した場合は、入力ストリームから読み込むことが可能な全てバイトデータを返却します。<br>
	 * @param is Input入力ストリームオブジェクト
	 * @param length 取得するバイト長
	 * @return 取得されたバイトデータ
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static byte[] reads(InputStream is, int length) throws IOException {
		if (is == null) {
			return new byte[0];
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int total = 0;
		while (true) {
			int readsize = -1;
			if (length > 0) {
				readsize = length < total + BUFFER_SIZE ? length - total : BUFFER_SIZE;
			} else {
				readsize = BUFFER_SIZE;
			}
			byte[] data = new byte[readsize];
			int readedsize = is.read(data);
			if (readedsize == -1) {
				break;
			}
			total += readedsize;
			bos.write(data, 0, readedsize);
			if (length > 0) {
				if (total >= length) {
					break;
				}
			}
		}
		return bos.toByteArray();
	}

	/**
	 * 入力ストリームが提供する情報を文字列として読み込みます。<br>
	 * @param is 入力ストリームオブジェクト
	 * @param charset 文字コード
	 * @return Input入力ストリームから提供された文字列
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	@SuppressWarnings("resource")
	public static String readString(InputStream is, String charset) throws IOException {
		if (is == null) {
			return "";
		}
		BufferedReader reader = charset == null ? new BufferedReader(new InputStreamReader(is)) : new BufferedReader(new InputStreamReader(is, charset));
		StringWriter string = new StringWriter();
		BufferedWriter writer = new BufferedWriter(string);
		for (String line; (line = reader.readLine()) != null;) {
			writer.write(line);
			writer.newLine();
			writer.flush();
		}
		writer.close();
		return string.toString();
	}

	/**
	 * 入力ストリームが提供する情報を文字列として読み込みます。<br>
	 * @param is 入力ストリームオブジェクト
	 * @return Input入力ストリームから提供された文字列
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static String readString(InputStream is) throws IOException {
		return readString(is, null);
	}

	/**
	 * 出力ストリームに対して指定されたバイトデータを出力します。<br>
	 * 当メソッドは単純に{@link java.io.OutputStream#write(byte[])}委譲メソッドで、{@link #reads(InputStream, int)}メソッドとの一貫性の為に設置されました。<br>
	 * @param os OutputStreamオブジェクト
	 * @param bytes 出力するバイト配列
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static void writes(OutputStream os, byte[] bytes) throws IOException {
		if (os == null || bytes == null) {
			return;
		}
		os.write(bytes);
	}
}
