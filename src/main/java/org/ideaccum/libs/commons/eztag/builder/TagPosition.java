package org.ideaccum.libs.commons.eztag.builder;

/**
 * タグ要素属性における位置情報を管理するインタフェースを提供します。<br>
 * <p>
 * このクラスではタグ属性やスタイル属性における位置情報を一元的に管理するために設置されました。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
public class TagPosition extends TagEntry<TagPosition> {

	/** 要素位置(上) */
	private TagValue<Float> top;

	/** 要素位置(右) */
	private TagValue<Float> right;

	/** 要素位置(下) */
	private TagValue<Float> bottom;

	/** 要素位置(左) */
	private TagValue<Float> left;

	/**
	 * コンストラクタ<br>
	 * @param top 要素位置(上)
	 * @param right 要素位置(右)
	 * @param bottom 要素位置(下)
	 * @param left 要素位置(左)
	 */
	public TagPosition(TagValue<Float> top, TagValue<Float> right, TagValue<Float> bottom, TagValue<Float> left) {
		super();
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
	}

	/**
	 * コンストラクタ<br>
	 * @param top 要素位置(上)
	 * @param right 要素位置(右)
	 * @param bottom 要素位置(下)
	 * @param left 要素位置(左)
	 */
	public TagPosition(Float top, Float right, Float bottom, Float left) {
		super();
		this.top = new TagValue<>(top);
		this.right = new TagValue<>(right);
		this.bottom = new TagValue<>(bottom);
		this.left = new TagValue<>(left);
	}

	/**
	 * コンストラクタ<br>
	 */
	private TagPosition() {
		this((TagValue<Float>) null, (TagValue<Float>) null, (TagValue<Float>) null, (TagValue<Float>) null);
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 * @see org.ideaccum.libs.commons.eztag.builder.TagEntry#clone()
	 */
	@Override
	public TagPosition clone() {
		TagPosition clone = new TagPosition();
		clone.top = top == null ? null : top.clone();
		clone.right = right == null ? null : right.clone();
		clone.bottom = bottom == null ? null : bottom.clone();
		clone.left = left == null ? null : left.clone();
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
		return "{top=" + top + ", right=" + right + ", bottom=" + bottom + ", left=" + left + "}";
	}

	/**
	 * 要素位置(上)を取得します。<br>
	 * @return 要素位置(上)
	 */
	public TagValue<Float> getTop() {
		return top;
	}

	/**
	 * 要素位置(上)を設定します。<br>
	 * @param top 要素位置(上)
	 */
	public void setTop(TagValue<Float> top) {
		this.top = top;
	}

	/**
	 * 要素位置(上)を設定します。<br>
	 * @param top 要素位置(上)
	 */
	public void setTop(Float top) {
		setTop(new TagValue<>(top));
	}

	/**
	 * 要素位置(上)を設定します。<br>
	 * @param top 要素位置(上)
	 * @param unit 値単位
	 */
	public void setTop(Float top, TagValueUnit unit) {
		setTop(new TagValue<>(top, unit));
	}

	/**
	 * 要素位置(上)を設定します。<br>
	 * @param top 要素位置(上)
	 * @param unit 値単位
	 */
	public void setTop(Float top, String unit) {
		setTop(new TagValue<>(top, unit));
	}

	/**
	 * 要素位置(右)を取得します。<br>
	 * @return 要素位置(右)
	 */
	public TagValue<Float> getRight() {
		return right;
	}

	/**
	 * 要素位置(右)を設定します。<br>
	 * @param right 要素位置(右)
	 */
	public void setRight(TagValue<Float> right) {
		this.right = right;
	}

	/**
	 * 要素位置(右)を設定します。<br>
	 * @param right 要素位置(右)
	 */
	public void setRight(Float right) {
		setRight(new TagValue<>(right));
	}

	/**
	 * 要素位置(右)を設定します。<br>
	 * @param right 要素位置(右)
	 * @param unit 値単位
	 */
	public void setRight(Float right, TagValueUnit unit) {
		setRight(new TagValue<>(right, unit));
	}

	/**
	 * 要素位置(右)を設定します。<br>
	 * @param right 要素位置(右)
	 * @param unit 値単位
	 */
	public void setRight(Float right, String unit) {
		setRight(new TagValue<>(right, unit));
	}

	/**
	 * 要素位置(下)を取得します。<br>
	 * @return 要素位置(下)
	 */
	public TagValue<Float> getBottom() {
		return bottom;
	}

	/**
	 * 要素位置(下)を設定します。<br>
	 * @param bottom 要素位置(下)
	 */
	public void setBottom(TagValue<Float> bottom) {
		this.bottom = bottom;
	}

	/**
	 * 要素位置(下)を設定します。<br>
	 * @param bottom 要素位置(下)
	 */
	public void setBottom(Float bottom) {
		setBottom(new TagValue<>(bottom));
	}

	/**
	 * 要素位置(下)を設定します。<br>
	 * @param bottom 要素位置(下)
	 * @param unit 値単位
	 */
	public void setBottom(Float bottom, TagValueUnit unit) {
		setBottom(new TagValue<>(bottom, unit));
	}

	/**
	 * 要素位置(下)を設定します。<br>
	 * @param bottom 要素位置(下)
	 * @param unit 値単位
	 */
	public void setBottom(Float bottom, String unit) {
		setBottom(new TagValue<>(bottom, unit));
	}

	/**
	 * 要素位置(左)を取得します。<br>
	 * @return 要素位置(左)
	 */
	public TagValue<Float> getLeft() {
		return left;
	}

	/**
	 * 要素位置(左)を設定します。<br>
	 * @param left 要素位置(左)
	 */
	public void setLeft(TagValue<Float> left) {
		this.left = left;
	}

	/**
	 * 要素位置(左)を設定します。<br>
	 * @param left 要素位置(左)
	 */
	public void setLeft(Float left) {
		setLeft(new TagValue<>(left));
	}

	/**
	 * 要素位置(左)を設定します。<br>
	 * @param left 要素位置(左)
	 * @param unit 値単位
	 */
	public void setLeft(Float left, TagValueUnit unit) {
		setLeft(new TagValue<>(left, unit));
	}

	/**
	 * 要素位置(左)を設定します。<br>
	 * @param left 要素位置(左)
	 * @param unit 値単位
	 */
	public void setLeft(Float left, String unit) {
		setLeft(new TagValue<>(left, unit));
	}
}
