package org.ideaccum.libs.commons.eztag.builder;

import java.util.LinkedHashMap;
import java.util.Map;

import org.ideaccum.libs.commons.util.Loop;
import org.ideaccum.libs.commons.util.StringUtil;

/**
 * タグ要素属性のうちスタイル属性に特化した内容を構築するためのインタフェースを提供します。<br>
 * <p>
 * 要素タグにおけるスタイル属性内容を構築するための操作インタフェースを提供します。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
public class TagStyleBuilder extends TagBuildableEntry<TagStyleBuilder> {

	/** タグスタイルエントリ */
	private Map<String, TagStyle<?>> entries;

	/**
	 * コンストラクタ<br>
	 */
	public TagStyleBuilder() {
		super();
		//this.entries = new HashMap<>();
		this.entries = new LinkedHashMap<>();
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 * @see org.ideaccum.libs.commons.eztag.builder.TagEntry#clone()
	 */
	@Override
	public TagStyleBuilder clone() {
		TagStyleBuilder clone = new TagStyleBuilder();
		for (String key : entries.keySet()) {
			TagStyle<?> entry = entries.get(key);
			clone.entries.put(key, entry == null ? null : entry.clone());
		}
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
		return build();
	}

	/**
	 * 実際にソースとして出力される要素文字列を構築します。<br>
	 * @return 実際にソースとして出力される要素文字列
	 * @see org.ideaccum.libs.commons.eztag.builder.TagBuildableEntry#build()
	 */
	@Override
	public String build() {
		StringBuilder builder = new StringBuilder();
		for (Loop<TagStyle<?>> loop : Loop.each(entries.values())) {
			TagStyle<?> entry = loop.value();
			if (entry == null) {
				continue;
			}
			builder.append(entry.build());
		}
		return builder.toString().trim();
	}

	/**
	 * 管理されているスタイル情報数を提供します。<br>
	 * @return 管理されているスタイル情報数
	 */
	public int size() {
		return entries.size();
	}

	/**
	 * 管理されている全てのスタイル情報を削除します。<br>
	 */
	public void clear() {
		entries.clear();
	}

	/**
	 * スタイル情報が存在するか判定します。<br>
	 * @param name スタイル名
	 * @return スタイル情報が存在する場合にtrueを返却
	 */
	public boolean contains(String name) {
		if (StringUtil.isBlank(name)) {
			return false;
		}
		String key = StringUtil.trim(name);
		return entries.containsKey(key);
	}

	/**
	 * タグスタイル情報を除去します。<br>
	 * @param name スタイル名
	 * @return スタイル情報が除去された場合にtrueを返却(java.util.Map#remove(String)の返却仕様とは異なります)
	 */
	public boolean remove(String name) {
		if (StringUtil.isBlank(name)) {
			return false;
		}
		String key = StringUtil.trim(name);
		return entries.remove(key) != null;
	}

	/**
	 * スタイル情報を設定します。<br>
	 * 既に追加されているスタイルの場合は上書きされます。<br>
	 * @param name スタイル名
	 * @param value スタイル値
	 */
	public void put(String name, TagStyle<?> value) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		entries.put(key, value);
	}

	/**
	 * スタイル情報を設定します。<br>
	 * 既に追加されているスタイルの場合は上書きされます。<br>
	 * @param name スタイル名
	 * @param value スタイル値
	 * @param unit 値単位
	 * @param important 優先フラグ
	 */
	public void put(String name, Object value, TagValueUnit unit, boolean important) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		put(key, new TagStyle<Object>(name, new TagValue<Object>(value, unit), important));
	}

	/**
	 * スタイル情報を設定します。<br>
	 * 既に追加されているスタイルの場合は上書きされます。<br>
	 * @param name スタイル名
	 * @param value スタイル値
	 * @param unit 値単位
	 */
	public void put(String name, Object value, TagValueUnit unit) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		put(key, new TagStyle<Object>(name, new TagValue<Object>(value, unit)));
	}

	/**
	 * スタイル情報を設定します。<br>
	 * 既に追加されているスタイルの場合は上書きされます。<br>
	 * @param name スタイル名
	 * @param value スタイル値
	 * @param important 優先フラグ
	 */
	public void put(String name, Object value, boolean important) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		put(key, new TagStyle<Object>(name, new TagValue<Object>(value), important));
	}

	/**
	 * スタイル情報を設定します。<br>
	 * 既に追加されているスタイルの場合は上書きされます。<br>
	 * @param name スタイル名
	 * @param value スタイル値
	 */
	public void put(String name, Object value) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		put(key, new TagStyle<Object>(name, new TagValue<Object>(value)));
	}

	/**
	 * 管理されているスタイル情報を取得します。<br>
	 * @param name スタイル名
	 * @return スタイル情報
	 */
	public TagStyle<?> get(String name) {
		if (StringUtil.isBlank(name)) {
			return null;
		}
		String key = StringUtil.trim(name);
		return entries.get(key);
	}
}
