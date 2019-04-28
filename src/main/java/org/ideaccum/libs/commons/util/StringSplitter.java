package org.ideaccum.libs.commons.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * 文字列を任意のデリミタワードで分割してそのトークンを取得するための処理を提供します。<br>
 * <p>
 * {@link java.util.StringTokenizer}類似の処理を提供するクラスですが、主に下記の機能が異なる仕様として提供されます。<br>
 * </p>
 * <ul>
 * <li>デリミタを文字列で指定することが可能です</li>
 * <li>同時に複数のデリミタ文字を指定することが可能です</li>
 * <li>デリミタをエスケープするトークンクォート文字を指定することが可能です</li>
 * </ul>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/01/31	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class StringSplitter implements Enumeration<String> {

	/** 文字列 */
	private String string;

	/** デリミタワード */
	private String[] delims;

	/** クォート文字 */
	private Character quote;

	/** デリミタ返却フラグ */
	private boolean returnDelims;

	/** 末端判定フラグ */
	private boolean judgmentTerminate;

	/** 解析インデックス */
	private int index;

	/**
	 * コンストラクタ<br>
	 * @param string 対象文字列
	 * @param delims デリミタワード
	 * @param quote クォート文字(この文字で囲まれている間はデリミタは無視します)
	 * @param returnDelims デリミタ返却フラグ
	 * @param judgmentTerminate 末端判定フラグ(このフラグをfalseとした場合、{@link #nextElement()}実行時に次の要素がない場合でも例外はスローしません)
	 */
	public StringSplitter(String string, String[] delims, Character quote, boolean returnDelims, boolean judgmentTerminate) {
		super();
		this.string = string;
		this.delims = delims == null ? null : CollectionUtil.select(delims, new CollectionUtil.CollectionSelector<String>() {
			@Override
			public boolean match(String e) {
				return e != null && !StringUtil.isEmpty(e);
			}
		});
		this.quote = quote;
		this.returnDelims = returnDelims;
		this.judgmentTerminate = judgmentTerminate;
		this.index = 0;
	}

	/**
	 * コンストラクタ<br>
	 * このコンストラクタで初期化されたインスタンスはデリミタの返却は行いません。<br>
	 * また、トークン末端にて{@link #nextElement()}を呼び出した場合に{@link java.util.NoSuchElementException}をスローします。<br>
	 * @param string 対象文字列
	 * @param delims デリミタワード
	 * @param quote クォート文字(この文字で囲まれている間はデリミタは無視します)
	 */
	public StringSplitter(String string, String[] delims, Character quote) {
		this(string, delims, quote, false, true);
	}

	/**
	 * コンストラクタ<br>
	 * このコンストラクタで初期化されたインスタンスはデリミタの返却は行いません。<br>
	 * また、トークン末端にて{@link #nextElement()}を呼び出した場合に{@link java.util.NoSuchElementException}をスローします。<br>
	 * @param string 対象文字列
	 * @param delims デリミタワード
	 */
	public StringSplitter(String string, String[] delims) {
		this(string, delims, null, false, true);
	}

	/**
	 * 次のトークン要素が存在するか判定します。<br>
	 * @return 次のトークン要素が存在する場合にtrueを返却
	 * @see java.util.Enumeration#hasMoreElements()
	 */
	@Override
	public boolean hasMoreElements() {
		if (StringUtil.isEmpty(string)) {
			return false;
		}
		if ((delims == null || delims.length <= 0) && index >= string.length()) {
			return false;
		}
		int indexBackup = index;
		String token = nextToken();
		index = indexBackup;
		return token != null;
	}

	/**
	 * 次のトークン要素を提供します。<br>
	 * デリミタ返却フラグが有効な場合は、トークンとしてデリミタ自体も返却します。<br>
	 * また、末端判定フラグを無効とした場合、{@link #hasMoreElements()}がfalseを返却する場合でも{@link java.util.NoSuchElementException}はスローせずに空文字を返却し続けることに注意してください。<br>
	 * @return 次のトークン要素
	 * @see java.util.Enumeration#nextElement()
	 */
	@Override
	public String nextElement() {
		StringBuilder builder = new StringBuilder();

		String token = nextToken();
		builder.append(StringUtil.nvl(token));
		if (quote != null && token != null) {
			boolean quoting = StringUtil.count(StringUtil.nvl(token), quote) % 2 == 1;
			while (quoting) {
				token = nextToken();
				builder.append(StringUtil.nvl(token));
				if (StringUtil.count(StringUtil.nvl(token), quote) % 2 == 1) {
					quoting = !quoting;
				}
			}
		}
		return builder.toString();
	}

	/**
	 * 次のトークン要素を提供します。<br>
	 * デリミタ返却フラグが有効な場合は、トークンとしてデリミタ自体も返却します。<br>
	 * また、末端判定フラグを無効とした場合、{@link #hasMoreElements()}がfalseを返却する場合でも{@link java.util.NoSuchElementException}はスローせずに空文字を返却し続けることに注意してください。<br>
	 * @return 次のトークン要素
	 */
	private String nextToken() {
		if ((delims == null || delims.length <= 0) && index >= string.length()) {
			index = string.length();
			return string;
		}
		if (judgmentTerminate && index >= string.length()) {
			throw new NoSuchElementException();
		}
		String matchedDelim = null;
		Integer matchedIndex = null;
		for (String delim : delims) {
			int match = string.indexOf(delim, index);
			if (match < 0) {
				continue;
			}
			if (matchedIndex == null || match < matchedIndex) {
				matchedIndex = match;
				matchedDelim = delim;
			}
		}
		if (matchedIndex != null) {
			if (matchedIndex - index == 0) {
				index += matchedDelim.length();
				if (returnDelims) {
					return matchedDelim;
				} else {
					return "";
				}
			} else if (matchedIndex - index >= 0) {
				String token = string.substring(index, matchedIndex);
				index = matchedIndex + matchedDelim.length();
				return token;
			}
		}
		if (index <= string.length() - 1) {
			String token = string.substring(index);
			index = string.length();
			return token;
		} else {
			return null;
		}
	}
}
