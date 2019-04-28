package org.ideaccum.libs.commons.toys.eztag.builder;

import org.ideaccum.libs.commons.util.StringUtil;

/**
 * タグ要素配下のテキストノード情報を管理するためのインタフェースを提供します。<br>
 * <p>
 * テキスト内容の他、テキストに対してエスケープが必要かどうか等の属性情報を併せて管理します。<br>
 * textareaタグやcodeタグ等ではエスケープ(改行を&lt;br&gt;変換など)が不要である為、エスケープ不要として利用します。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
public class TagText extends TagBuildableEntry<TagText> {

	/** テキスト内容 */
	private String text;

	/** エスケープフラグ */
	private boolean escape;

	/**
	 * コンストラクタ<br>
	 * @param text テキスト内容
	 * @param escape　エスケープフラグ
	 */
	public TagText(String text, boolean escape) {
		super();
		this.text = text;
		this.escape = escape;
	}

	/**
	 * コンストラクタ<br>
	 * エスケープ不要テキストノードとしてインスタンス生成します。<br>
	 * エスケープが必要な場合は{@link #TagText(String, boolean)}によるインスタンス生成又は、インスタンス生成後に{@link #setEscape(boolean)}で設定が必要です。<br>
	 * @param text テキスト内容
	 */
	public TagText(String text) {
		this(text, false);
	}

	/**
	 * コンストラクタ<br>
	 */
	private TagText() {
		this(null, false);
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.TagEntry#clone()
	 */
	@Override
	public TagText clone() {
		TagText clone = new TagText();
		clone.text = text;
		clone.escape = escape;
		return clone;
	}

	/**
	 * オブジェクト情報を文字列として提供します。<br>
	 * @return オブジェクト情報文字列
	 * @see java.lang.Object#toString()
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.TagEntry#toString()
	 */
	@Override
	public String toString() {
		return text;
	}

	/**
	 * 実際にソースとして出力される要素文字列を構築します。<br>
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.TagBuildableEntry#build()
	 */
	@Override
	public String build() {
		if (escape) {
			return TagOperateUtil.encodeHTML(text);
		} else {
			return StringUtil.nvl(text);
		}
	}

	/**
	 * テキスト内容を取得します。<br>
	 * @return テキスト内容
	 */
	public String getText() {
		return text;
	}

	/**
	 * テキスト内容を設定します。<br>
	 * @param text テキスト内容
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * エスケープフラグを取得します。<br>
	 * @return エスケープフラグ
	 */
	public boolean isEscape() {
		return escape;
	}

	/**
	 * エスケープフラグを設定します。<br>
	 * @param escape エスケープフラグ
	 */
	public void setEscape(boolean escape) {
		this.escape = escape;
	}
}
