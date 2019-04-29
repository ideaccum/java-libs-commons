package org.ideaccum.libs.commons.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 固定桁長の行編成データに対するストリーム入力を行うためのインタフェースを提供します。<br>
 * <p>
 * 改行コードによる行編成データを入力する際に、入力時に改行コード部を無視して、本来のデータ部のみを入力する場合に利用します。<br>
 * このクラスによる入力ストリーム処理では、コンストラクタによって指定された{@link org.ideaccum.libs.commons.io.LineRecordLength}が提供する{@link org.ideaccum.libs.commons.io.LineRecordLength#getRecordLength()}のサイズに達したタイミングで、
 * {@link org.ideaccum.libs.commons.io.LineRecordLength#getLinefeedLength()}が提供する改行コード分のバイト情報を自動で読み飛ばす挙動となります。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2017/06/09	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class LineInputStream extends InputStream {

	/** 親入力ストリームオブジェクト */
	private InputStream parent;

	/** レコード改行情報 */
	private LineRecordLength linefeed;

	/** 入力位置 */
	private int position;

	/** レコード単位入力位置(改行レコード構成の場合のみ) */
	private int positionOfRecord;

	/** マーク位置 */
	private int positionOfMark;

	/**
	 * コンストラクタ<br>
	 * @param parent 親入力ストリームオブジェクト
	 * @param linefeed レコード改行情報
	 * @param size ストリームバッファサイズ
	 */
	public LineInputStream(InputStream parent, LineRecordLength linefeed, int size) {
		super();
		this.parent = new BufferedInputStream(parent, size);
		this.linefeed = linefeed;
		this.position = 0;
		this.positionOfRecord = 0;
		this.positionOfMark = -1;
	}

	/**
	 * コンストラクタ<br>
	 * @param parent 親入力ストリームオブジェクト
	 * @param linefeed レコード改行情報
	 */
	public LineInputStream(InputStream parent, LineRecordLength linefeed) {
		this(parent, linefeed, 4096);
	}

	/**
	 * クラス情報文字列を提供します。<br>
	 * @return クラス情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String hex = Integer.toHexString(position);
		while (hex.length() < 8) {
			hex = "0" + hex;
		}
		hex = "0x" + hex.toUpperCase();
		return hex + "(" + position + ")";
	}

	/**
	 * 入力ストリームからデータの次のバイトを読み込みます。<br>
	 * 値のバイトは、0 ～ 255 の範囲の int として返されます。<br>
	 * ストリームの終わりに達したために読み込むバイトがない場合は、値 -1 が返されます。<br>
	 * 入力データが読み込めるようになるか、ファイルの終わりが検出されるか、または例外が発生するまで、このメソッドはブロックされます。<br>
	 * <br>
	 * 尚、このクラスにおけるread()メソッドは以下の特殊な挙動仕様を提供します。<br>
	 * <ul>
	 * <li>2バイトセットで扱う為、奇数バイト長のファイルデータでは例外がスローされます</li>
	 * <li>一度のread()メソッド処理において、基底ストリームからは2バイトを読み込み、1バイトとして提供します</li>
	 * </ul>
	 * @return データの次のバイト。ストリームの終わりに達した場合は -1
	 * @throws IOException 入出力エラーが発生した場合にスローされます
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		int data = parent.read();
		if (data == -1) {
			return -1;
		}
		position++;
		positionOfRecord++;
		if (linefeed != null) {
			if (positionOfRecord > 0 && positionOfRecord == linefeed.getRecordLength()) {
				for (int i = 0; i <= linefeed.getLinefeedLength() - 1; i++) {
					parent.read();
				}
				positionOfRecord = 0;
			}
		}
		return data;
	}

	/**
	 * この入力ストリームの現在位置にマークを設定します。<br>
	 * @param readlimit マーク位置が無効になる前に読み込み可能なバイトの最大リミット
	 * @see java.io.InputStream#mark(int)
	 */
	@Override
	public synchronized void mark(int readlimit) {
		parent.mark(readlimit);
		positionOfMark = position;
	}

	/**
	 * このストリームの位置を、入力ストリームで最後に mark メソッドが呼び出されたときのマーク位置に再設定します。<br>
	 * @see java.io.InputStream#reset()
	 */
	@Override
	public synchronized void reset() throws IOException {
		parent.reset();
		if (positionOfMark >= 0) {
			position = positionOfMark;
			positionOfRecord = positionOfMark;
			positionOfMark = -1;
		}
	}

	/**
	 * 出力ストリームを閉じ、このストリームに関連するすべてのシステムリソースを解放します。<br>
	 * close の汎用規約では、出力ストリームを閉じます。閉じられたストリームは入出力処理を実行できません。<br>
	 * また、閉じられたストリームを開き直すことはできません。<br>
	 * @throws IOException 入出力エラーが発生した場合にスローされます
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		parent.close();
	}
}
