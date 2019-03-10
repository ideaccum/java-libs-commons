package org.ideaccum.libs.commons.util;

import java.io.Serializable;

/**
 * 特定の文字列の文字を常に繰返して提供する処理を提供します。<br>
 * <p>
 * アプリケーションのユーザーに対するコンテンツ表現等において、特定の文字列を繰返した内容を特定範囲に提供したい場合等に利用します。<br>
 * 例えば、"CONFIDENTIAL"と言う文字列を繰返してページ上の行内区切り等で表現したい場合等に利用します。<br>
 * 80カラムのページにおいて"CONFIDENTIALCONFIDENTIALCONFIDENTIALCONFIDENTIAL..."のような形式の文字列を出力したい場合、下記のようなコードを実装します。<br>
 * </p>
 * <code>
 * CharLoop loop = new CharLoop("CONFIDENTAIL");
 * String line = loop.next(80);
 * </code>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/05/15	Kitagawa		新規作成
 *-->
 */
public class StringBelt implements Serializable {

	/** 繰返し対象文字群 */
	private char[] chars;

	/** インデックス */
	private int index;

	/** 前方処理フラグ */
	private Boolean ahead;

	/**
	 * コンストラクタ<br>
	 * @param string 繰返し対象文字列
	 */
	public StringBelt(String string) {
		super();
		this.chars = StringUtil.nvl(string).toCharArray();
		this.index = 0;
		this.ahead = null;
		if (this.chars.length <= 0) {
			throw new IllegalArgumentException("Characters length is 0");
		}
	}

	/**
	 * コンストラクタ<br>
	 * @param chars 繰返し対象文字群
	 */
	public StringBelt(char... chars) {
		super();
		this.chars = chars;
		this.index = 0;
		this.ahead = null;
		if (this.chars == null) {
			throw new IllegalArgumentException("Characters is null");
		}
		if (this.chars.length <= 0) {
			throw new IllegalArgumentException("Characters length is 0");
		}
	}

	/**
	 * オブジェクト情報を文字列として提供します。<br>
	 * @return オブジェクト情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (char c : chars) {
			builder.append(c);
		}
		return builder.toString();
	}

	/**
	 * 繰返し文字群の後方に向かって次に提供される文字を提供します。<br>
	 * @return 文字群後方側の次の文字
	 */
	public char next() {
		if (ahead == null) {
			ahead = true;
			index = 0;
		} else if (!ahead) {
			ahead = true;
		} else {
			index += 1;
			if (index > chars.length - 1) {
				index = 0;
			}
		}
		char c = chars[index];
		return c;
	}

	/**
	 * 繰返し文字群の後方に向かって提供される指定文字長分の文字集合を文字列として提供します。<br>
	 * @param length 取得文字長
	 * @return 文字集合文字列
	 */
	public String next(int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= length - 1; i++) {
			builder.append(next());
		}
		return builder.toString();
	}

	/**
	 * 繰返し文字群の前方に向かって次に提供される文字を提供します。<br>
	 * @return 文字群前方側の次の文字
	 */
	public char prev() {
		if (ahead == null) {
			ahead = false;
			index = chars.length - 1;
		} else if (ahead) {
			ahead = false;
		} else {
			index -= 1;
			if (index < 0) {
				index = chars.length - 1;
			}
		}
		char c = chars[index];
		return c;
	}

	/**
	 * 繰返し文字群の前方に向かって提供される指定文字長分の文字集合を文字列として提供します。<br>
	 * @param length 取得文字長
	 * @return 文字集合文字列
	 */
	public String prev(int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= length - 1; i++) {
			builder.insert(0, prev());
		}
		return builder.toString();
	}
}
