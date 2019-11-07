package org.ideaccum.libs.commons.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 16進表記のアスキー文字列で表現された固定桁長行編成のリソースに対してストリーム入力を行うインタフェースを提供します。<br>
 * <p>
 * このクラスは{@link org.ideaccum.libs.commons.io.LineInputStream}及び、{@link org.ideaccum.libs.commons.io.HexInputStream}をラップしたクラスとなります。<br>
 * 外部から入力されたストリームを16進表記行編成データとして入力し、このクラスをラップしたストリームでは通常のバイトデータとして扱うインタフェースを提供します。<br>
 * 尚、行編成として扱う際のレコード長はあくまでも固定長データのレコード長であり、16進表記データ時の行長ではないことに注意して下さい。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2017/06/09  Kitagawa         新規作成
 * 2018/05/02  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class HexLineInputStream extends InputStream {

	/** 親入力ストリームオブジェクト */
	private InputStream parent;

	/**
	 * コンストラクタ<br>
	 * @param parent 親入力ストリームオブジェクト
	 * @param linefeed レコード改行情報
	 * @param size ストリームバッファサイズ
	 */
	public HexLineInputStream(InputStream parent, LineRecordLength linefeed, int size) {
		super();
		this.parent = new HexInputStream(new LineInputStream(parent, new LineRecordLength(linefeed.getRecordLength() * 2, linefeed.getLinefeed()), size), size);
	}

	/**
	 * コンストラクタ<br>
	 * @param parent 親入力ストリームオブジェクト
	 * @param linefeed レコード改行情報
	 */
	public HexLineInputStream(InputStream parent, LineRecordLength linefeed) {
		this(parent, linefeed, 4096);
	}

	/**
	 * クラス情報文字列を提供します。<br>
	 * @return クラス情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return parent.toString();
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
		return parent.read();
	}

	/**
	 * この入力ストリームの現在位置にマークを設定します。<br>
	 * @param readlimit マーク位置が無効になる前に読み込み可能なバイトの最大リミット
	 * @see java.io.InputStream#mark(int)
	 */
	@Override
	public synchronized void mark(int readlimit) {
		parent.mark(readlimit);
	}

	/**
	 * このストリームの位置を、入力ストリームで最後に mark メソッドが呼び出されたときのマーク位置に再設定します。<br>
	 * @see java.io.InputStream#reset()
	 */
	@Override
	public synchronized void reset() throws IOException {
		parent.reset();
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
