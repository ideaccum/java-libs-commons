package org.ideaccum.libs.commons.toys.eztag.builder;

import org.ideaccum.libs.commons.util.StringUtil;

/**
 * タグ要素属性情報を管理するためのインタフェースを提供します。<br>
 * <p>
 * タグ要素における各種属性内容を管理するためのコンポジットクラスです。<br>
 * {@link org.ideaccum.libs.commons.toys.eztag.builder.TagBuilder}に対して属性を設定する際に文字列として値管理ではなく、オブジェクトとして値管理を行う事を可能とする為に設置されました。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
public class TagAttribute<T> extends TagBuildableEntry<TagAttribute<T>> {

	/** 属性名 */
	private String name;

	/** 属性値 */
	private TagValue<T> value;

	/**
	 * コンストラクタ<br>
	 * @param name 属性名
	 * @param value 属性値
	 */
	public TagAttribute(String name, TagValue<T> value) {
		super();
		this.name = name;
		this.value = value;
	}

	/**
	 * コンストラクタ<br>
	 * @param name 属性名
	 */
	public TagAttribute(String name) {
		this(name, null);
	}

	/**
	 * コンストラクタ<br>
	 */
	private TagAttribute() {
		this(null);
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.TagEntry#clone()
	 */
	@Override
	public TagAttribute<T> clone() {
		TagAttribute<T> clone = new TagAttribute<>();
		clone.name = name;
		clone.value = value == null ? null : value.clone();
		return clone;
	}

	/**
	 * クラス情報文字列を提供します。<br>
	 * @return クラス情報文字列
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.TagEntry#toString()
	 */
	@Override
	public String toString() {
		return "{name=" + name + ", value=" + value + "}";
	}

	/**
	 * 実際にソースとして出力される要素文字列を構築します。<br>
	 * @return 実際にソースとして出力される要素文字列
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.TagBuildableEntry#build()
	 */
	@Override
	public String build() {
		if (StringUtil.isEmpty(name)) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		if (value != null) {
			builder.append("=\"");
			builder.append(TagOperateUtil.encodeAttr(value.build()));
			builder.append("\"");
		}
		return builder.toString();
	}

	/**
	 * 属性名を取得します。<br>
	 * @return 属性名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 属性名を設定します。<br>
	 * @param name 属性名
	 */
	public void setName(String name) {
		this.name = StringUtil.trim(name);
	}

	/**
	 * 属性値を取得します。<br>
	 * @return 属性値
	 */
	public TagValue<T> getValue() {
		return value;
	}

	/**
	 * 属性値を設定します。<br>
	 * @param value 属性値
	 */
	public void setValue(TagValue<T> value) {
		this.value = value;
	}

	/**
	 * 属性値を設定します。<br>
	 * @param value 属性値
	 */
	public void setValue(T value) {
		this.value = new TagValue<>(value);
	}

	/**
	 * 属性値を設定します。<br>
	 * @param value 属性値
	 * @param unit 値単位
	 */
	public void setValue(T value, TagValueUnit unit) {
		this.value = new TagValue<>(value, unit);
	}

	/**
	 * 属性値を設定します。<br>
	 * @param value 属性値
	 * @param unit 値単位
	 */
	public void setValue(T value, String unit) {
		this.value = new TagValue<>(value, unit);
	}
}
