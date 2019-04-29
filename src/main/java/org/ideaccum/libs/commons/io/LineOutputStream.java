package org.ideaccum.libs.commons.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 固定桁長の行編成データに対するストリーム出力を行うためのインタフェースを提供します。<br>
 * <p>
 * 利用者が固定長行編成データを出力する際に指定桁数に達した時点で改行コードを出力する処理を意識せずに固定長データを出力する場合に利用します。<br>
 * このクラスによるストリーム出力処理では、コンストラクタによって指定された{@link org.ideaccum.libs.commons.io.LineRecordLength}が提供する{@link org.ideaccum.libs.commons.io.LineRecordLength#getRecordLength()}のサイズに達したタイミングで、
 * {@link org.ideaccum.libs.commons.io.LineRecordLength#getLinefeed()}が提供する改行コードを自動的にストリーム出力する挙動となります。<br>
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
public class LineOutputStream extends OutputStream {

	/** 親出力ストリームオブジェクト */
	private OutputStream parent;

	/** レコード改行情報 */
	private LineRecordLength linefeed;

	/** 入力位置 */
	private int position;

	/** レコード単位入力位置(改行レコード構成の場合のみ) */
	private int positionOfRecord;

	/**
	 * コンストラクタ<br>
	 * @param parent 親出力ストリームオブジェクト
	 * @param linefeed レコード改行情報
	 * @param size ストリームバッファサイズ
	 */
	public LineOutputStream(OutputStream parent, LineRecordLength linefeed, int size) {
		super();
		this.parent = new BufferedOutputStream(parent, size);
		this.linefeed = linefeed;
		this.position = 0;
		this.positionOfRecord = 0;
	}

	/**
	 * コンストラクタ<br>
	 * @param parent 親出力ストリームオブジェクト
	 * @param linefeed レコード改行情報
	 */
	public LineOutputStream(OutputStream parent, LineRecordLength linefeed) {
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
	 * 出力ストリームを閉じ、このストリームに関連するすべてのシステムリソースを解放します。<br>
	 * close の汎用規約では、出力ストリームを閉じます。閉じられたストリームは入出力処理を実行できません。<br>
	 * また、閉じられたストリームを開き直すことはできません。<br>
	 * @throws IOException 入出力エラーが発生した場合にスローされます
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		flush();
		parent.close();
	}

	/**
	 * バッファリングされた出力ストリームをフラッシュします。<br>
	 * この処理により、バッファリングされているすべての出力バイトが基本となる出力ストリームに書き込まれます。<br>
	 * @throws IOException 入出力エラーが発生した場合にスローされます
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() throws IOException {
		parent.flush();
	}

	/**
	 * 指定されたバイト数をバッファリングされた出力ストリームに書き込みます。<br>
	 * @param b 書き込むバイト
	 * @throws IOException 入出力エラーが発生した場合にスローされます
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		parent.write(b);
		position++;
		positionOfRecord++;
		if (linefeed != null) {
			if (positionOfRecord > 0 && positionOfRecord == linefeed.getRecordLength()) {
				parent.write(linefeed.getLinefeed());
				positionOfRecord = 0;
			}
		}
	}
}
