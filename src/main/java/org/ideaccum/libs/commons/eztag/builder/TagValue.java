package org.ideaccum.libs.commons.eztag.builder;

import java.text.DecimalFormat;

import org.ideaccum.libs.commons.util.StringUtil;

/**
 * タグ要素やスタイル要素の属性値を単位付きで管理するためのインタフェースを提供します。<br>
 * <p>
 * この列挙型クラスでは利用頻度の高い属性値単位を提供します。<br>
 * 列挙型として提供されない単位の属性値を利用する場合は、属性値設定時に単位を含めた値を設定する必要があります。<br>
 * </p>
 * 
 * @param <T> 属性値型
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
public class TagValue<T> extends TagBuildableEntry<TagValue<T>> {

	/** 値 */
	private T value;

	/** 値単位 */
	private TagValueUnit unit;

	/**
	 * コンストラクタ<br>
	 * @param value 値
	 * @param unit 値単位
	 */
	public TagValue(T value, TagValueUnit unit) {
		super();
		this.value = value;
		this.unit = unit;
	}

	/**
	 * コンストラクタ<br>
	 * @param value 値
	 * @param unit 値単位
	 */
	public TagValue(T value, String unit) {
		this(value, TagValueUnit.valueOf(unit));
	}

	/**
	 * コンストラクタ<br>
	 * @param value 値
	 */
	public TagValue(T value) {
		this(value, (String) null);
	}

	/**
	 * コンストラクタ<br>
	 */
	private TagValue() {
		this(null);
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 * @see org.ideaccum.libs.commons.eztag.builder.TagEntry#clone()
	 */
	@Override
	public TagValue<T> clone() {
		TagValue<T> clone = new TagValue<>();
		clone.value = value;
		clone.unit = unit;
		return clone;
	}

	/**
	 * オブジェクト情報を文字列として提供します。<br>
	 * @return オブジェクト情報文字列
	 * @see java.lang.Object#toString()
	 * @see org.ideaccum.libs.commons.eztag.builder.TagEntry#toString()
	 */
	@Override
	public String toString() {
		return build();
	}

	/**
	 * 実際にソースとして出力される要素文字列を構築します。<br>
	 * @return 実際にソースとして出力される要素文字列
	 * @see org.ideaccum.libs.commons.eztag.builder.TagBuildableEntry#build()
	 */
	@Override
	public String build() {
		if (value == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		if (value instanceof Number) {
			double dvalue = ((Number) value).doubleValue();
			if (dvalue - Math.floor(dvalue) != 0D) {
				builder.append(dvalue);
			} else {
				builder.append(new DecimalFormat("0").format(dvalue));
			}
		} else {
			builder.append(value.toString());
		}
		if (unit != null) {
			builder.append(unit.getUnit());
		}
		return builder.toString();
	}

	/**
	 * 属性値が空であるか判定します。<br>
	 * @return 属性値が空である場合にtrueを返却
	 */
	public boolean isEmpty() {
		if (value == null) {
			return true;
		} else if (value instanceof String) {
			return StringUtil.isEmpty((String) value);
		} else {
			return false;
		}
	}

	/**
	 * 値を取得します。<br>
	 * @return 値
	 */
	public T getValue() {
		return value;
	}

	/**
	 * 値を設定します。<br>
	 * @param value 値
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * 値を設定します。<br>
	 * @param value 値
	 * @param unit 値単位
	 */
	public void setValue(T value, TagValueUnit unit) {
		setValue(value);
		setUnit(unit);
	}

	/**
	 * 値を設定します。<br>
	 * @param value 値
	 * @param unit 値単位
	 */
	public void setValue(T value, String unit) {
		setValue(value);
		setUnit(unit);
	}

	/**
	 * 値単位を取得します。<br>
	 * @return 値単位
	 */
	public TagValueUnit getUnit() {
		return unit;
	}

	/**
	 * 値単位を設定します。<br>
	 * @param unit 値単位
	 */
	public void setUnit(TagValueUnit unit) {
		this.unit = unit;
	}

	/**
	 * 値単位を設定します。<br>
	 * @param unit 値単位
	 */
	public void setUnit(String unit) {
		this.unit = TagValueUnit.valueOf(unit);
	}
}
