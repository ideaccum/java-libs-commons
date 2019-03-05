package org.ideaccum.libs.commons.eztag.builder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.ideaccum.libs.commons.util.StringUtil;

/**
 * タグ属性値やスタイル属性値で設定する属性値の単位を列挙型形式で提供します。<br>
 * <p>
 * このクラスでは利用頻度の高い属性値単位を提供します。<br>
 * 列挙値として提供されない単位の属性値を利用する場合は、属性値設定時に単位を含めた値を設定する必要があります。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
public final class TagValueUnit implements Serializable {

	/** ピクセル */
	public static final TagValueUnit PX = new TagValueUnit("px");

	/** 文字単位 */
	public static final TagValueUnit EM = new TagValueUnit("em");

	/** インスタンスキャッシュ */
	private static Map<String, TagValueUnit> cache = new HashMap<>();

	/** 単位文字列 */
	private String unit;

	/**
	 * コンストラクタ<br>
	 * @param unit 単位文字列
	 */
	private TagValueUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * オブジェクト情報を文字列として提供します。<br>
	 * @return オブジェクト情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return StringUtil.nvl(unit);
	}

	/**
	 * 単位文字列から列挙型インスタンスを提供します。<br>
	 * @param unit 単位文字列
	 * @return 列挙型インスタンス
	 */
	static TagValueUnit valueOf(String unit) {
		synchronized (cache) {
			if (StringUtil.isEmpty(unit)) {
				return null;
			}
			String key = StringUtil.trim(unit);
			if (cache.containsKey(key)) {
				return cache.get(key);
			}
			TagValueUnit instance = new TagValueUnit(key);
			cache.put(key, instance);
			return instance;
		}
	}

	/**
	 * 単位文字列を取得します。<br>
	 * @return 単位文字列
	 */
	public String getUnit() {
		return unit;
	}
}
