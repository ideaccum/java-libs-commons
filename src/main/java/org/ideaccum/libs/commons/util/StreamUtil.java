package org.ideaccum.libs.commons.util;

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
 * システム開発時に利用するストリーム操作で利用頻度の高い操作を提供します。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2008/11/05  Kitagawa         新規作成
 * 2018/05/02  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class StreamUtil {

	/** ディフォルトストリームバッファサイズ */
	public static final int DEFAULT_BUFFER_SIZE = 2048;

	/**
	 * コンストラクタ<br>
	 */
	private StreamUtil() {
		super();
	}

	/**
	 * 入力ストリームが提供するデータを別の出力ストリームに転送します。<br>
	 * 出力ストリームにnullが指定された場合、入力ストリームが提供するデータはすべて切り捨てられます。<br>
	 * @param is 入力ストリーム
	 * @param os 出力ストリーム
	 * @param bufferSize ストリーム処理時のバッファサイズ
	 * @return 転送されたバイトサイズ
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static int pipe(InputStream is, OutputStream os, int bufferSize) throws IOException {
		if (is == null) {
			return 0;
		}
		int total = 0;
		while (true) {
			byte[] data = new byte[bufferSize];
			int readed = is.read(data);
			if (readed == -1) {
				break;
			}
			total += readed;
			if (os != null) {
				os.write(data, 0, readed);
			}
		}
		return total;
	}

	/**
	 * 入力ストリームが提供するデータを別の出力ストリームに転送します。<br>
	 * @param is 入力ストリームオブジェクト
	 * @param os 出力ストリームオブジェクト
	 * @return 転送されたバイトサイズ
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static int pipe(InputStream is, OutputStream os) throws IOException {
		return pipe(is, os, DEFAULT_BUFFER_SIZE);
	}

	/**
	 * 入力ストリームから指定された長さのデータを読み込み、バイトデータとして提供します。<br>
	 * 尚、指定されたサイズまでのデータが存在しなかった場合は、読み込めたデータ長のバイト配列が返却されます。<br>
	 * また、取得長に0以下の数値を指定した場合は、入力ストリームから読み込むことが可能な全てバイトデータを返却します。<br>
	 * @param is Input入力ストリームオブジェクト
	 * @param length 取得するバイト長
	 * @param bufferSize ストリーム処理時のバッファサイズ
	 * @return 取得されたバイトデータ
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static byte[] reads(InputStream is, int length, int bufferSize) throws IOException {
		if (is == null) {
			return new byte[0];
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int total = 0;
		while (true) {
			int readsize = -1;
			if (length > 0) {
				readsize = length < total + bufferSize ? length - total : bufferSize;
			} else {
				readsize = bufferSize;
			}
			byte[] data = new byte[readsize];
			int readed = is.read(data);
			if (readed == -1) {
				break;
			}
			total += readed;
			bos.write(data, 0, readed);
			if (length > 0) {
				if (total >= length) {
					break;
				}
			}
		}
		return bos.toByteArray();
	}

	/**
	 * 入力ストリームから指定された長さのデータを読み込み、バイトデータとして提供します。<br>
	 * 尚、指定されたサイズまでのデータが存在しなかった場合は、読み込めたデータ長のバイト配列が返却されます。<br>
	 * また、取得長に0以下の数値を指定した場合は、入力ストリームから読み込むことが可能な全てバイトデータを返却します。<br>
	 * @param is Input入力ストリームオブジェクト
	 * @param length 取得するバイト長
	 * @return 取得されたバイトデータ
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static byte[] reads(InputStream is, int length) throws IOException {
		return reads(is, length, DEFAULT_BUFFER_SIZE);
	}

	/**
	 * 入力ストリームからすべてのバイトデータを読み込み、バイトデータとして提供します。<br>
	 * @param is Input入力ストリームオブジェクト
	 * @return 取得されたバイトデータ
	 * @throws IOException ストリーム操作時に入出力例外が発生した場合にスローされます
	 */
	public static byte[] reads(InputStream is) throws IOException {
		return reads(is, -1, DEFAULT_BUFFER_SIZE);
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
