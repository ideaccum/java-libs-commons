package org.ideaccum.libs.commons.io;

import java.io.Serializable;

/**
 * 改行付き固定桁長ストリーム(行編成ストリーム)を操作する際にのレコード桁数と改行コードを一元管理するインタフェースを提供します。<br>
 * <p>
 * ストリーム操作クラスに対してこのクラスインスタンスを指定する事で、管理されているレコード桁数のタイミングで改行コードが存在する形で処理が行われます。<br>
 * 即ち入力ストリームに対して、このクラスインスタンスを指定した場合は、改行コードのタイミングで改行コードが読み飛ばされる動きを行います。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2017/06/08	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class LineRecordLength implements Serializable {

	/** ディフォルト改行コード */
	private static final byte[] DEFAULT_LINEFEED = new byte[] { 0x0D, 0x0A };

	/** レコード長 */
	private int length;

	/** 改行コード */
	private byte[] linefeed;

	/**
	 * コンストラクタ<br>
	 * @param length レコード長
	 * @param linefeed 改行コード
	 */
	public LineRecordLength(int length, byte[] linefeed) {
		super();
		this.length = length;
		this.linefeed = linefeed;
	}

	/**
	 * コンストラクタ<br>
	 * @param length レコード長
	 */
	public LineRecordLength(int length) {
		this(length, DEFAULT_LINEFEED);
	}

	/**
	 * レコード長を取得します。<br>
	 * @return レコード長
	 */
	public int getRecordLength() {
		return length;
	}

	/**
	 * 改行コード長を取得します。<br>
	 * @return 改行コード長
	 */
	public int getLinefeedLength() {
		return linefeed == null ? 0 : linefeed.length;
	}

	/**
	 * 改行コード長を取得します。<br>
	 * @return 改行コード長
	 */
	public byte[] getLinefeed() {
		byte[] data = new byte[linefeed.length];
		System.arraycopy(linefeed, 0, data, 0, linefeed.length);
		return data;
	}
}
