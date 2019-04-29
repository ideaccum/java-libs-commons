package org.ideaccum.libs.commons.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.ideaccum.libs.commons.util.StringUtil;

/**
 * 16進表記のアスキー文字列で表現されたリソースに対してストリーム入力を行い、バイトデータとして提供するストリーム入力インタフェースを提供します。<br>
 * <p>
 * このクラスはシステム間でのバイナリデータ連携時にシステム構成や環境的な要因でバイトデータの欠損が発生する状況下において、16進数表記でバイト欠損を回避するために設置されました。<br>
 * システム連携時などにおいて、文字として扱えない文字を一部含む文字列データをパラメータで受け渡す事が困難な場合等に利用します。<br>
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
public class HexInputStream extends InputStream {

	/** 親入力ストリームオブジェクト */
	private InputStream parent;

	/** 入力位置 */
	private int position;

	/** マーク位置 */
	private int positionOfMark;

	/**
	 * コンストラクタ<br>
	 * @param parent 親入力ストリームオブジェクト
	 * @param size ストリームバッファサイズ
	 */
	public HexInputStream(InputStream parent, int size) {
		super();
		this.parent = new BufferedInputStream(parent, size);
		this.position = 0;
		this.positionOfMark = -1;
	}

	/**
	 * コンストラクタ<br>
	 * @param parent 親入力ストリームオブジェクト
	 */
	public HexInputStream(InputStream parent) {
		this(parent, 4096);
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
		int i1 = parent.read();
		int i2 = parent.read();
		if (i1 == -1) {
			return -1;
		}
		if (i2 == -1) {
			throw new IOException("Stream is odd number length");
		}

		char c1 = (char) i1;
		char c2 = (char) i2;
		String hex = "" + c1 + c2;
		//byte data = (byte) Integer.parseInt(hex, 16);
		int data = 0;
		if (!StringUtil.isBlank(hex)) {
			data = Integer.parseInt(hex, 16);
		}

		position++;

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
