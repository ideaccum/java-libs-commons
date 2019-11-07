package org.ideaccum.libs.commons.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * バイト操作に関するユーティリティインタフェースを提供します。<br>
 * <p>
 * バイト情報を扱う際の支援的なメソッドを提供します。<br>
 * </p>
 *
 *<!--
 * 更新日      更新者           更新内容
 * 2008/10/31  Kitagawa         新規作成
 * 2018/05/02  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class ByteUtil {

	/**
	 * コンストラクタ<br>
	 */
	private ByteUtil() {
		super();
	}

	/**
	 * バイト情報に関するログ情報を出力します。<br>
	 * このメソッドは開発時にバイト情報を確認する目的で設置されました。<br>
	 * @param bytes バイト配列
	 * @param writer ログ出力先ストリーム
	 * @throws IOException ログ出力時に入出力例外が発生した場合にスローされます
	 */
	public static void printByteTrace(byte[] bytes, Writer writer) throws IOException {
		if (bytes == null) {
			writer.write("Bytes is null");
			writer.write("\n");
		} else {
			int length = bytes.length;
			writer.write("Byte Length : " + length);
			writer.write("\n");
			if (length == 0) {
				return;
			}
			for (int r = 0; r <= length / 16; r++) {
				if (r * 16 == length) {
					break;
				}

				StringBuffer line = new StringBuffer();

				// 行番号出力
				String row = Integer.toHexString(r * 16);
				while (row.length() < 8) {
					row = "0" + row;
				}
				line.append(row);
				line.append(": ");

				// 16バイト毎の行出力
				byte[] buffer = new byte[16];
				for (int i = r * 16, s = 0, c = 0; i <= r * 16 + 16 - 1; i++, c++) {
					if (i > bytes.length - 1) {
						// バイト長が末尾に達した場合も出力位置を揃える
						line.append("   ");
					} else {
						// Hex値が1byteの場合に2byte補正出力
						String hex = Integer.toHexString(bytes[i]).replaceAll("ffffff", "");
						while (hex.length() < 2) {
							hex = "0" + hex;
						}
						line.append(hex);
						line.append(" ");
					}

					// 8byte毎にセパレート
					if (s == 7) {
						line.append(" ");
						s = 0;
					} else {
						s++;
					}

					// 16byte分のバッファ
					buffer[c] = (i > bytes.length - 1) ? " ".getBytes()[0] : bytes[i];
					if ((buffer[c] >= 0 && buffer[c] <= 31) || buffer[c] == 127) {
						// 制御文字は.(ピリオドで出力)
						buffer[c] = ".".getBytes()[0];
					}
				}
				writer.write(line.toString() + new String(buffer));
				writer.write("\n");
				writer.flush();
			}
		}
		writer.flush();
	}

	/**
	 * バイト情報に関するログ情報を出力します。<br>
	 * このメソッドは開発時にバイト情報を確認する目的で設置されました。<br>
	 * @param bytes バイト配列
	 * @param stream ログ出力先ストリーム
	 * @throws IOException ログ出力時に入出力例外が発生した場合にスローされます
	 */
	public static void printByteTrace(byte[] bytes, OutputStream stream) throws IOException {
		printByteTrace(bytes, new OutputStreamWriter(stream));
	}

	/**
	 * バイト情報をクローンして提供します。<br>
	 * @param bytes バイトデータ
	 * @return クローンされたバイトデータ
	 */
	public static byte[] clone(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		byte[] clone = new byte[bytes.length];
		System.arraycopy(bytes, 0, clone, 0, clone.length);
		return clone;
	}
}
