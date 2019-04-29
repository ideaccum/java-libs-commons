package org.ideaccum.libs.commons.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * バイトデータを出力する際に16進表記のアスキー文字列としてストリーム出力するインタフェースを提供します。<br>
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
public class HexOutputStream extends OutputStream {

	/** 親出力ストリームオブジェクト */
	private OutputStream parent;

	/** 親出力ストリームライターオブジェクト */
	private PrintWriter writer;

	/** 入力位置 */
	private int position;

	/**
	 * コンストラクタ<br>
	 * @param parent 親出力ストリームオブジェクト
	 * @param size ストリームバッファサイズ
	 */
	public HexOutputStream(OutputStream parent, int size) {
		super();
		this.parent = new BufferedOutputStream(parent, size);
		this.writer = new PrintWriter(parent);
		this.position = 0;
	}

	/**
	 * コンストラクタ<br>
	 * @param parent 親出力ストリームオブジェクト
	 */
	public HexOutputStream(OutputStream parent) {
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
	 * 出力ストリームを閉じ、このストリームに関連するすべてのシステムリソースを解放します。<br>
	 * close の汎用規約では、出力ストリームを閉じます。閉じられたストリームは入出力処理を実行できません。<br>
	 * また、閉じられたストリームを開き直すことはできません。<br>
	 * @throws IOException 入出力エラーが発生した場合にスローされます
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		flush();
		writer.close();
		//parent.close();
	}

	/**
	 * バッファリングされた出力ストリームをフラッシュします。<br>
	 * この処理により、バッファリングされているすべての出力バイトが基本となる出力ストリームに書き込まれます。<br>
	 * @throws IOException 入出力エラーが発生した場合にスローされます
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() throws IOException {
		writer.flush();
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
		//String hex = Integer.toHexString(b).toUpperCase();
		//String hex = Integer.toHexString((byte) b & 0xFF).toUpperCase();
		String hex = String.format("%02x", (byte) b).toUpperCase();
		writer.print(hex);
		position++;
	}
}
