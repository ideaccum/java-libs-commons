package org.ideaccum.libs.commons.toys.eztag.builder;

import org.ideaccum.libs.commons.util.StringUtil;

/**
 * 各種タグ操作を行う際の支援的な操作インタフェースを提供します。<br>
 * <p>
 * ここで提供する各種メソッドは{@link org.ideaccum.libs.commons.toys.eztag.builder.TagBuilder}を始めとするタグ操作ユーティリティ関連のクラスのみで利用します。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
final class TagOperateUtil {

	/**
	 * コンストラクタ<br>
	 */
	private TagOperateUtil() {
		super();
	}

	/**
	 * HTML文字列を出力する際の文字列値をエンコードして提供します。<br>
	 * @param value 対象文字列
	 * @return エンコードした文字列
	 */
	public static String encodeHTML(String value) {
		String buffer = value == null ? "" : value;
		buffer = encodeAttr(value);
		buffer = StringUtil.replace(buffer, " ", "&nbsp;");
		buffer = StringUtil.replace(buffer, "\t", "&nbsp;");
		buffer = StringUtil.replace(buffer, "\n", "<br>");
		return buffer;
	}

	/**
	 * タグ要素属性に出力する際の文字列値をエンコードして提供します。<br>
	 * @param value 対象文字列
	 * @return エンコードした文字列
	 */
	public static String encodeAttr(String value) {
		String buffer = value == null ? "" : value;
		buffer = StringUtil.replace(buffer, "&", "&amp;");
		//buffer = StringUtil.replace(buffer, "<", "&lt;");
		//buffer = StringUtil.replace(buffer, ">", "&gt;");
		buffer = StringUtil.replace(buffer, "\"", "&quot;");
		//buffer = StringUtil.replace(buffer, "'", "&#039;");
		//buffer = StringUtil.replace(buffer, "\\", "&yen;");
		return buffer;
	}

	/**
	 * スタイル定義値に出力する際の文字列値をエンコードして提供します。<br>
	 * @param value 対象文字列
	 * @return エンコードした文字列
	 */
	public static String encodeStyle(String value) {
		String buffer = value == null ? "" : value;
		if (buffer.contains(";")) {
			return "\"" + buffer + "\"";
		} else {
			return buffer;
		}
	}
}
