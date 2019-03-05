package org.ideaccum.libs.commons.eztag.builder;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ideaccum.libs.commons.util.Loop;
import org.ideaccum.libs.commons.util.StringUtil;

/**
 * 要素タグ属性の各種属性内容を構築するためのインタフェースを提供します。<br>
 * <p>
 * 要素タグにおける属性内容を構築するための操作が提供されます。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
public class TagAttributeBuilder extends TagBuildableEntry<TagAttributeBuilder> {

	/** タグ属性エントリ */
	private Map<String, TagAttribute<?>> entries;

	/** スタイル属性ビルダ */
	private TagStyleBuilder style;

	/** スタイルクラスリスト */
	private List<String> styleClasses;

	/**
	 * コンストラクタ<br>
	 */
	public TagAttributeBuilder() {
		super();
		//this.entries = new HashMap<>();
		this.entries = new LinkedHashMap<>();
		this.style = new TagStyleBuilder();
		this.styleClasses = new LinkedList<String>();
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 * @see org.ideaccum.libs.commons.eztag.builder.TagEntry#clone()
	 */
	@Override
	public TagAttributeBuilder clone() {
		TagAttributeBuilder clone = new TagAttributeBuilder();
		clone.style = style.clone();
		for (String styleClass : styleClasses) {
			clone.styleClasses.add(styleClass);
		}
		for (String key : entries.keySet()) {
			TagAttribute<?> entry = entries.get(key);
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
		/*
		 * 通常属性情報生成
		 */
		for (Loop<TagAttribute<?>> loop : Loop.each(entries.values())) {
			TagAttribute<?> entry = loop.value();
			if (entry == null) {
				continue;
			}
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(entry.build());
		}
		/*
		 * スタイルクラス属性情報生成
		 */
		if (styleClasses.size() > 0) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			StringBuilder styleClassBuilder = new StringBuilder();
			for (String styleClass : styleClasses) {
				if (styleClassBuilder.length() > 0) {
					styleClassBuilder.append(" ");
				}
				styleClassBuilder.append(styleClass);
			}
			TagAttribute<String> entry = new TagAttribute<String>("class");
			entry.setValue(styleClassBuilder.toString());
			builder.append(entry.build());
		}
		/*
		 * スタイル属性情報生成
		 */
		if (style.size() > 0) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			TagAttribute<String> entry = new TagAttribute<String>("style");
			entry.setValue(style.build());
			builder.append(entry.build());
		}
		return builder.toString().trim();
	}

	/**
	 * 管理されている全てのタグ属性情報数を提供します。<br>
	 * @return 管理されている全てのタグ属性情報数
	 */
	public int size() {
		int size = entries.size();
		size += style.size() > 0 ? 1 : 0;
		size += styleClasses.size() > 0 ? 1 : 0;
		return size;
	}

	/**
	 * 管理されている全ての属性情報を削除します。<br>
	 */
	public void clear() {
		entries.clear();
		style.clear();
		styleClasses.clear();
	}

	/**
	 * 管理されている全てのタグ属性情報内に属性が存在するか判定します。<br>
	 * @param name 属性名
	 * @return タグ属性情報が存在する場合にtrueを返却
	 */
	public boolean contains(String name) {
		if (StringUtil.isBlank(name)) {
			return false;
		}
		String key = StringUtil.trim(name);
		if (containsAttr(key)) {
			return true;
		}
		if ("style".equalsIgnoreCase(key) && style.size() > 0) {
			return true;
		}
		if ("class".equalsIgnoreCase(key) && styleClasses.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 管理されているタグ属性情報を除去します。<br>
	 * @param name 属性名
	 */
	public void remove(String name) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		if ("style".equalsIgnoreCase(key)) {
			/*
			 * スタイル属性直接設定時
			 */
			style.clear();
		} else if ("class".equalsIgnoreCase(key)) {
			/*
			 * スタイルクラス属性直接設定時
			 */
			styleClasses.clear();
		} else {
			/*
			 * 通常属性設定時
			 */
			entries.remove(key);
		}
	}

	/**
	 * 管理されているタグ属性情報のうちスタイル及び、スタイルクラス以外の属性数を提供します。<br>
	 * @return 管理されているスタイル属性数
	 */
	public int sizeOfAttr() {
		return entries.size();
	}

	/**
	 * 管理されている属性情報のうちスタイル及び、スタイルクラス以外の属性を全て削除します。<br>
	 */
	public void clearAttr() {
		entries.clear();
	}

	/**
	 * タグ属性情報を追加します。<br>
	 * @param name 属性名
	 * @param value 属性値
	 */
	public void put(String name, Object value) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		if ("style".equalsIgnoreCase(key)) {
			/*
			 * スタイル属性直接設定時
			 */
			String string = value == null ? null : value.toString();
			if (StringUtil.isBlank(string)) {
				style.clear();
				return;
			}
			for (String token : StringUtil.trim(string).split(";")) {
				if (token.indexOf(":") <= 0) {
					continue;
				}
				String styleName = token.split(":")[0].trim();
				String styleValue = token.split(":")[1].trim();
				style.put(styleName, styleValue);
			}
		} else if ("class".equalsIgnoreCase(key)) {
			/*
			 * スタイルクラス属性直接設定時
			 */
			String string = value == null ? null : value.toString();
			if (StringUtil.isBlank(string)) {
				styleClasses.clear();
				return;
			}
			for (String token : StringUtil.trim(string).split(" ")) {
				if (styleClasses.contains(token.trim())) {
					continue;
				}
				styleClasses.add(token.trim());
			}
		} else {
			/*
			 * 通常属性設定時
			 */
			TagAttribute<Object> entry = new TagAttribute<>(key, new TagValue<>(value));
			entries.put(key, entry);
		}
	}

	/**
	 * タグ属性情報を追加します。<br>
	 * @param name 属性名
	 */
	public void put(String name) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		if ("style".equalsIgnoreCase(key)) {
			/*
			 * スタイル属性直接設定時
			 */
			style.clear();
		} else if ("class".equalsIgnoreCase(key)) {
			/*
			 * スタイルクラス属性直接設定時
			 */
			styleClasses.clear();
		} else {
			/*
			 * 通常属性設定時
			 */
			TagAttribute<Object> entry = new TagAttribute<Object>(key);
			entries.put(key, entry);
		}
	}

	/**
	 * スタイル及び、スタイルクラス以外の属性が存在するか判定します。<br>
	 * @param name 属性名
	 * @return タグ属性情報が存在する場合にtrueを返却
	 */
	public boolean containsAttr(String name) {
		if (StringUtil.isBlank(name)) {
			return false;
		}
		String key = StringUtil.trim(name);
		return entries.containsKey(key);
	}

	/**
	 * スタイル及び、スタイルクラス以外の属性から属性情報を除去します。<br>
	 * @param name 属性名
	 */
	public void removeAttr(String name) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		if ("style".equalsIgnoreCase(key)) {
			/*
			 * スタイル属性直接設定時
			 */
			style.clear();
		} else if ("class".equalsIgnoreCase(key)) {
			/*
			 * スタイルクラス属性直接設定時
			 */
			styleClasses.clear();
		} else {
			/*
			 * 通常属性設定時
			 */
			entries.remove(key);
		}
	}

	/**
	 * 属性情報を追加します。<br>
	 * このメソッドは他のメソッドとの一貫性の為に設置されていますが、{@link #put(String, Object)}と同一挙動を行います。<br>
	 * @param name 属性名
	 * @param value 属性値
	 * @see #put(String, Object)
	 */
	public void putAttr(String name, String value) {
		put(name, value);
	}

	/**
	 * 属性情報を追加します。<br>
	 * このメソッドは他のメソッドとの一貫性の為に設置されていますが、{@link #put(String)}と同一挙動を行います。<br>
	 * @param name 属性名
	 */
	public void putAttr(String name) {
		put(name);
	}

	/**
	 * 属性情報を設定します。<br>
	 * @param attribute 属性情報
	 */
	public void putAttr(TagAttribute<?> attribute) {
		if (attribute == null) {
			return;
		}
		String key = attribute.getName();
		if ("style".equalsIgnoreCase(key)) {
			putAttr(key, attribute == null ? null : attribute.getValue());
		} else if ("class".equalsIgnoreCase(key)) {
			putAttr(key, attribute == null ? null : attribute.getValue());
		} else {
			entries.put(key, attribute);
		}
	}

	/**
	 * 属性情報を設定します。<br>
	 * 既に追加されている属性の場合は上書きされます。<br>
	 * @param name 属性名
	 * @param value 属性値
	 * @param unit 値単位
	 */
	public void putAttr(String name, Object value, TagValueUnit unit) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		put(key, new TagAttribute<Object>(key, new TagValue<Object>(value, unit)));
	}

	/**
	 * 属性情報を設定します。<br>
	 * 既に追加されている属性の場合は上書きされます。<br>
	 * @param name 属性名
	 * @param value 属性値
	 */
	public void putAttr(String name, Object value) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		put(key, new TagAttribute<Object>(key, new TagValue<Object>(value)));
	}

	/**
	 * 管理されているタグ属性情報のうちスタイル属性数を提供します。<br>
	 * @return 管理されているスタイル属性数
	 */
	public int sizeOfStyle() {
		return style.size();
	}

	/**
	 * 管理されている属性情報のうちスタイル属性を全て削除します。<br>
	 */
	public void clearStyles() {
		style.clear();
	}

	/**
	 * スタイル属性情報が存在するか判定します。<br>
	 * @param name スタイル名
	 * @return スタイル属性情報が存在する場合にtrueを返却
	 */
	public boolean containsStyle(String name) {
		if (StringUtil.isBlank(name)) {
			return false;
		}
		String key = StringUtil.trim(name);
		return style.contains(key);
	}

	/**
	 * スタイル属性情報を除去します。<br>
	 * @param name スタイル名
	 */
	public void removeStyle(String name) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		style.remove(key);
	}

	/**
	 * スタイル属性情報を追加します。<br>
	 * @param name スタイル名
	 * @param value スタイル値
	 * @param important 優先指定スタイル
	 */
	public void putStyle(String name, Object value, boolean important) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		style.put(key, value, important);
	}

	/**
	 * スタイル属性情報を追加します。<br>
	 * @param name スタイル名
	 * @param value スタイル値
	 */
	public void putStyle(String name, Object value) {
		putStyle(name, value, false);
	}

	/**
	 * 管理されているタグ属性情報のうちスタイルクラス属性数を提供します。<br>
	 * @return 管理されているスタイルクラス属性数
	 */
	public int sizeOfStyleClass() {
		return styleClasses.size();
	}

	/**
	 * 管理されている属性情報のうちスタイルクラス属性を全て削除します。<br>
	 */
	public void clearStyleClasses() {
		styleClasses.clear();
	}

	/**
	 * スタイルクラス属性情報が存在するか判定します。<br>
	 * @param name スタイルクラス名
	 * @return スタイルクラス属性情報が存在する場合にtrueを返却
	 */
	public boolean containsClass(String name) {
		if (StringUtil.isBlank(name)) {
			return false;
		}
		String key = StringUtil.trim(name);
		return styleClasses.contains(key);
	}

	/**
	 * スタイルクラス属性情報を除去します。<br>
	 * @param name スタイルクラス名
	 */
	public void removeClass(String name) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		styleClasses.remove(key);
	}

	/**
	 * スタイルクラス属性情報を追加します。<br>
	 * @param name スタイルクラス名
	 */
	public void putClass(String name) {
		if (StringUtil.isBlank(name)) {
			return;
		}
		String key = StringUtil.trim(name);
		if (styleClasses.contains(key)) {
			return;
		}
		styleClasses.add(key);
	}
}
