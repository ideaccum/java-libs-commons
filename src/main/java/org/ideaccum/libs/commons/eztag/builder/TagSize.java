package org.ideaccum.libs.commons.eztag.builder;

/**
 * タグ要素属性におけるサイズ情報を管理するインタフェースを提供します。<br>
 * <p>
 * このクラスではタグ属性やスタイル属性におけるサイズ情報を一元的に管理するために設置されました。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
public class TagSize extends TagEntry<TagSize> {

	/** 要素サイズ(横幅) */
	private TagValue<Float> width;

	/** 要素サイズ(縦幅) */
	private TagValue<Float> height;

	/**
	 * コンストラクタ<br>
	 * @param width 要素サイズ(横幅)
	 * @param heigth 要素サイズ(縦幅)
	 */
	public TagSize(TagValue<Float> width, TagValue<Float> heigth) {
		super();
		this.width = width;
		this.height = heigth;
	}

	/**
	 * コンストラクタ<br>
	 * @param width 要素サイズ(横幅)
	 * @param heigth 要素サイズ(縦幅)
	 */
	public TagSize(Float width, Float heigth) {
		this(new TagValue<>(width), new TagValue<>(heigth));
	}

	/**
	 * コンストラクタ<br>
	 */
	private TagSize() {
		this((TagValue<Float>) null, (TagValue<Float>) null);
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 * @see org.ideaccum.libs.commons.eztag.builder.TagEntry#clone()
	 */
	@Override
	public TagSize clone() {
		TagSize clone = new TagSize();
		clone.width = width == null ? null : width.clone();
		clone.height = height == null ? null : height.clone();
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
		return "{width=" + width + ", height=" + height + "}";
	}

	/**
	 * 要素サイズ(横幅)を取得します。<br>
	 * @return 要素サイズ(横幅)
	 */
	public TagValue<Float> getWidth() {
		return width;
	}

	/**
	 * 要素サイズ(横幅)を設定します。<br>
	 * @param width 要素サイズ(横幅)
	 */
	public void setWidth(TagValue<Float> width) {
		this.width = width;
	}

	/**
	 * 要素サイズ(横幅)を設定します。<br>
	 * @param width 要素サイズ(横幅)
	 */
	public void setWidth(Float width) {
		setWidth(new TagValue<>(width));
	}

	/**
	 * 要素サイズ(横幅)を設定します。<br>
	 * @param width 要素サイズ(横幅)
	 * @param unit 値単位
	 */
	public void setWidth(Float width, TagValueUnit unit) {
		setWidth(new TagValue<>(width, unit));
	}

	/**
	 * 要素サイズ(横幅)を設定します。<br>
	 * @param width 要素サイズ(横幅)
	 * @param unit 値単位
	 */
	public void setWidth(Float width, String unit) {
		setWidth(new TagValue<>(width, unit));
	}

	/**
	 * 要素サイズ(縦幅)を取得します。<br>
	 * @return 要素サイズ(縦幅)
	 */
	public TagValue<Float> getHeight() {
		return height;
	}

	/**
	 * 要素サイズ(縦幅)を設定します。<br>
	 * @param height 要素サイズ(縦幅)
	 */
	public void setHeight(TagValue<Float> height) {
		this.height = height;
	}

	/**
	 * 要素サイズ(縦幅)を設定します。<br>
	 * @param height 要素サイズ(縦幅)
	 */
	public void setHeight(Float height) {
		setHeight(new TagValue<>(height));
	}

	/**
	 * 要素サイズ(縦幅)を設定します。<br>
	 * @param height 要素サイズ(縦幅)
	 * @param unit 値単位
	 */
	public void setHeight(Float height, TagValueUnit unit) {
		setHeight(new TagValue<>(height, unit));
	}

	/**
	 * 要素サイズ(縦幅)を設定します。<br>
	 * @param height 要素サイズ(縦幅)
	 * @param unit 値単位
	 */
	public void setHeight(Float height, String unit) {
		setHeight(new TagValue<>(height, unit));
	}
}
