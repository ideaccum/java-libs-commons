package org.ideaccum.libs.commons.eztag.builder;

import org.ideaccum.libs.commons.util.StringUtil;

/**
 * タグ要素属性のうちスタイル属性に特化した内容のエントリを管理するためのインタフェースを提供します。<br>
 * <p>
 * 要素タグにおけるスタイル属性内容を管理するためのコンポジットクラスです。<br>
 * {@link org.ideaccum.libs.commons.eztag.builder.TagBuilder}に対してスタイル属性を設定する際に文字列として値管理ではなく、オブジェクトとして値管理を行う事を可能とする為に設置されました。<br>
 * </p>
 * 
 * @param <T> スタイル属性値の型
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
public class TagStyle<T> extends TagBuildableEntry<TagStyle<T>> {

	/** スタイル名 */
	private String name;

	/** スタイル値 */
	private TagValue<T> value;

	/** 優先フラグ */
	private boolean important;

	/**
	 * コンストラクタ<br>
	 * @param name スタイル名
	 * @param value スタイル値
	 * @param important 優先フラグ
	 */
	public TagStyle(String name, TagValue<T> value, boolean important) {
		super();
		this.name = name;
		this.value = value;
		this.important = important;
	}

	/**
	 * コンストラクタ<br>
	 * @param name スタイル名
	 * @param value スタイル値
	 */
	public TagStyle(String name, TagValue<T> value) {
		this(name, value, false);
	}

	/**
	 * コンストラクタ<br>
	 */
	private TagStyle() {
		this(null, null, false);
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 * @see org.ideaccum.libs.commons.eztag.builder.TagEntry#clone()
	 */
	@Override
	public TagStyle<T> clone() {
		TagStyle<T> clone = new TagStyle<>();
		clone.name = name;
		clone.value = value == null ? null : value.clone();
		clone.important = important;
		return clone;
	}

	/**
	 * クラス情報文字列を提供します。<br>
	 * @return クラス情報文字列
	 * @see java.lang.Object#toString()
	 * @see org.ideaccum.libs.commons.eztag.builder.TagEntry#toString()
	 */
	@Override
	public String toString() {
		return "{name=" + name + ", value=" + value + ", important=" + important + "}";
	}

	/**
	 * 実際にソースとして出力される要素文字列を構築します。<br>
	 * @return 実際にソースとして出力される要素文字列
	 * @see org.ideaccum.libs.commons.eztag.builder.TagBuildableEntry#build()
	 */
	@Override
	public String build() {
		if (StringUtil.isEmpty(name)) {
			return "";
		}
		if (value == null || value.isEmpty()) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append(": ");
		builder.append(value.build());
		if (important) {
			builder.append(" !important");
		}
		builder.append(";");
		return builder.toString();
	}

	/**
	 * スタイル名を取得します。<br>
	 * @return スタイル名
	 */
	public String getName() {
		return name;
	}

	/**
	 * スタイル名を設定します。<br>
	 * @param name スタイル名
	 */
	public void setName(String name) {
		this.name = StringUtil.trim(name);
	}

	/**
	 * スタイル値を取得します。<br>
	 * @return スタイル値
	 */
	public TagValue<T> getValue() {
		return value;
	}

	/**
	 * スタイル値を設定します。<br>
	 * @param value スタイル値
	 */
	public void setValue(TagValue<T> value) {
		this.value = value;
	}

	/**
	 * スタイル値を設定します。<br>
	 * @param value スタイル値
	 */
	public void setValue(T value) {
		this.value = new TagValue<>(value);
	}

	/**
	 * スタイル値を設定します。<br>
	 * @param value スタイル値
	 * @param unit 値単位
	 */
	public void setValue(T value, TagValueUnit unit) {
		this.value = new TagValue<>(value, unit);
	}

	/**
	 * スタイル値を設定します。<br>
	 * @param value スタイル値
	 * @param unit 値単位
	 */
	public void setValue(T value, String unit) {
		this.value = new TagValue<>(value, unit);
	}

	/**
	 * 優先フラグを取得します。<br>
	 * @return 優先フラグ
	 */
	public boolean isImportant() {
		return important;
	}

	/**
	 * 優先フラグを設定します。<br>
	 * @param important 優先フラグ
	 */
	public void setImportant(boolean important) {
		this.important = important;
	}
}
