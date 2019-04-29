package org.ideaccum.libs.commons.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * バイトデータを出力する際に16進表記のアスキー文字列として固定桁長の行編成データとしてストリーム出力するインタフェースを提供します。<br>
 * <p>
 * このクラスは{@link org.ideaccum.libs.commons.io.LineOutputStream}及び、{@link org.ideaccum.libs.commons.io.HexOutputStream}をラップしたクラスとなります。<br>
 * 外部から入力されたストリームを16進表記行編成データとして入力し、このクラスをラップしたストリームでは通常のバイトデータとして扱うインタフェースを提供します。<br>
 * 尚、行編成として扱う際のレコード長はあくまでも固定長データのレコード長であり、16進表記データ時の行長ではないことに注意して下さい。<br>
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
public class HexLineOutputStream extends OutputStream {

	/** 親出力ストリームオブジェクト */
	private OutputStream parent;

	/** 16進数出力ストリームオブジェクト */
	private HexOutputStream hexStream;

	/** レコード改行情報 */
	private LineRecordLength linefeed;

	/** レコード単位入力位置(改行レコード構成の場合のみ) */
	private int positionOfRecord;

	/**
	 * コンストラクタ<br>
	 * @param parent 親出力ストリームオブジェクト
	 * @param linefeed レコード改行情報
	 * @param size ストリームバッファサイズ
	 */
	public HexLineOutputStream(OutputStream parent, LineRecordLength linefeed, int size) {
		super();
		this.parent = parent;
		this.hexStream = new HexOutputStream(new BufferedOutputStream(parent, size));
		this.linefeed = linefeed;
	}

	/**
	 * コンストラクタ<br>
	 * @param parent 親出力ストリームオブジェクト
	 * @param linefeed レコード改行情報
	 */
	public HexLineOutputStream(OutputStream parent, LineRecordLength linefeed) {
		this(parent, linefeed, 4096);
	}

	/**
	 * クラス情報文字列を提供します。<br>
	 * @return クラス情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return hexStream.toString();
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
		hexStream.close();
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
		hexStream.flush();
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
		hexStream.write(b);
		positionOfRecord++;
		if (linefeed != null) {
			if (positionOfRecord > 0 && positionOfRecord == linefeed.getRecordLength()) {
				hexStream.flush();
				parent.write(linefeed.getLinefeed());
				parent.flush();
				positionOfRecord = 0;
			}
		}
	}
}
