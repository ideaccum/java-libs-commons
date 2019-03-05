package org.ideaccum.libs.commons.eztag.builder;

import java.util.LinkedList;
import java.util.List;

import org.ideaccum.libs.commons.util.StringUtil;

/**
 * タグ要素を構築するためのインタフェースを提供します。<br>
 * <p>
 * タグ文字列構築の際の構築実装を簡略化するためのインタフェースを提供します。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
public class TagBuilder extends TagBuildableEntry<TagBuilder> {

	/** タグ名称 */
	private String name;

	/** タグテキストノード */
	private String text;

	/** 閉じタグを要するタグフラグ */
	private boolean closable;

	/** タグテキストをエスケープ出力するかのフラグ */
	private boolean escapeText;

	/** 子タグビルダーオブジェクト */
	private List<TagBuilder> children;

	/** タグ属性ビルダーオブジェクト */
	private TagAttributeBuilder attrBuilder;

	/**
	 * コンストラクタ<br>
	 * @param name タグ名称
	 */
	public TagBuilder(String name) {
		super();
		if (name == null) {
			throw new NullPointerException("name");
		}
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("name is empty");
		}
		this.name = StringUtil.trim(name);
		this.text = null;
		this.closable = true;
		this.escapeText = true;
		this.children = new LinkedList<>();
		this.attrBuilder = new TagAttributeBuilder();
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 * @see org.ideaccum.libs.commons.eztag.builder.TagEntry#clone()
	 */
	@Override
	public TagBuilder clone() {
		TagBuilder clone = new TagBuilder(name);
		clone.attrBuilder = attrBuilder.clone();
		clone.text = text;
		clone.closable = closable;
		clone.escapeText = escapeText;
		clone.children = new LinkedList<TagBuilder>();
		for (TagBuilder child : children) {
			clone.children.add(child.clone());
		}
		return clone;
	}

	/**
	 * クラス情報文字列を提供します。<br>
	 * @return クラス情報文字列
	 * @see java.lang.Object#toString()
	 * @see org.ideaccum.libs.commons.eztag.builder.TagEntry#toString()
	 */
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
		builder.append(buildStart());
		builder.append(buildEnd());
		return builder.toString();
	}

	/**
	 * 管理されている情報から開始タグのみ(子要素含む)のタグ文字列を構築します。<br>
	 * 利用者側で開始/終了を個別に出力する必要がある場合に利用します。<br>
	 * 開始/終了共に一括で出力する場合は{@link #build()}を利用します。<br>
	 * @return タグ文字列
	 */
	public String buildStart() {
		String attr = attrBuilder.build();
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		builder.append(name);
		if (!StringUtil.isBlank(attr)) {
			builder.append(" ");
			builder.append(attr.trim());
		}
		builder.append(">");
		if (!StringUtil.isEmpty(text)) {
			if (escapeText) {
				builder.append(TagOperateUtil.encodeHTML(text));
			} else {
				builder.append(text);
			}
		}
		for (TagBuilder child : children) {
			builder.append(child.build());
		}
		return builder.toString();
	}

	/**
	 * 管理されている情報から終了タグのみのタグ文字列を構築します。<br>
	 * 利用者側で開始/終了を個別に出力する必要がある場合に利用します。<br>
	 * 開始/終了共に一括で出力する場合は{@link #build()}を利用します。<br>
	 * @return タグ文字列
	 */
	public String buildEnd() {
		StringBuilder builder = new StringBuilder();
		if (closable) {
			builder.append("</");
			builder.append(name);
			builder.append(">");
		}
		return builder.toString();
	}

	/**
	 * タグ名称を取得します。<br>
	 * @return タグ名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * タグテキストノードを設定します。<br>
	 * @param text タグテキストノード
	 */
	public void putText(String text) {
		this.text = text;
	}

	/**
	 * タグテキストノードを取得します。<br>
	 * @return タグテキストノード
	 */
	public String getText() {
		return text;
	}

	/**
	 * タグテキストノードを削除します。<br>
	 */
	public void removeText() {
		text = null;
	}

	/**
	 * 閉じタグを要するタグフラグを取得します。<br>
	 * @return 閉じタグを要するタグフラグ
	 */
	public boolean isClosable() {
		return closable;
	}

	/**
	 * 閉じタグを要するタグフラグを設定します。<br>
	 * @param closable 閉じタグを要するタグフラグ
	 */
	public void setClosable(boolean closable) {
		this.closable = closable;
	}

	/**
	 * タグテキストをエスケープ出力するかのフラグを取得します。<br>
	 * @return タグテキストをエスケープ出力するかのフラグ
	 */
	public boolean isEscapeText() {
		return escapeText;
	}

	/**
	 * タグテキストをエスケープ出力するかのフラグを設定します。<br>
	 * @param escapeText タグテキストをエスケープ出力するかのフラグ
	 */
	public void setEscapeText(boolean escapeText) {
		this.escapeText = escapeText;
	}

	/**
	 * 子要素を追加します。<br>
	 * @param builder 子要素ビルダーオブジェクト
	 */
	public void addChild(TagBuilder builder) {
		children.add(builder);
	}

	/**
	 * タグ属性情報を取得します。<br>
	 * @return タグ属性情報
	 */
	public TagAttributeBuilder getAttribute() {
		return attrBuilder;
	}
}
