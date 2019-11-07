package org.ideaccum.libs.commons.toys.eztag.builder.html;

import org.ideaccum.libs.commons.toys.eztag.builder.AbstractHtmlElement;
import org.ideaccum.libs.commons.toys.eztag.builder.ElementAttr;
import org.ideaccum.libs.commons.toys.eztag.builder.ElementTag;

/**
 * linkタグを管理するインタフェースを提供します。<br>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2018/07/13  Kitagawa         新規作成
 *-->
 */
@ElementTag(name = "link", closable = false)
public abstract class AbstractLink<T extends AbstractLink<T>> extends AbstractHtmlElement<T> {

	/** リンクタイプ */
	@ElementAttr(name = "rel")
	private String rel;

	/** MIMEタイプ */
	@ElementAttr(name = "type")
	private String type;

	/** リソースパス */
	@ElementAttr(name = "href")
	private String href;

	/** キャラクタセット */
	@ElementAttr(name = "charset")
	private String charset;

	/** メディアタイプ */
	@ElementAttr(name = "media")
	private String media;

	/**
	 * コンストラクタ<br>
	 */
	public AbstractLink() {
		super();
	}

	/**
	 * リンクタイプを取得します。<br>
	 * @return リンクタイプ
	 */
	protected String getRel() {
		return rel;
	}

	/**
	 * リンクタイプを設定します。<br>
	 * @param rel リンクタイプ
	 */
	protected void setRel(String rel) {
		this.rel = rel;
	}

	/**
	 * MIMEタイプを取得します。<br>
	 * @return MIMEタイプ
	 */
	protected String getType() {
		return type;
	}

	/**
	 * MIMEタイプを設定します。<br>
	 * @param type MIMEタイプ
	 */
	protected void setType(String type) {
		this.type = type;
	}

	/**
	 * リソースパスを取得します。<br>
	 * @return リソースパス
	 */
	protected String getHref() {
		return href;
	}

	/**
	 * リソースパスを設定します。<br>
	 * @param href リソースパス
	 */
	protected void setHref(String href) {
		this.href = href;
	}

	/**
	 * キャラクタセットを取得します。<br>
	 * @return キャラクタセット
	 */
	protected String getCharset() {
		return charset;
	}

	/**
	 * キャラクタセットを設定します。<br>
	 * @param charset キャラクタセット
	 */
	protected void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * メディアタイプを取得します。<br>
	 * @return メディアタイプ
	 */
	protected String getMedia() {
		return media;
	}

	/**
	 * メディアタイプを設定します。<br>
	 * @param media メディアタイプ
	 */
	protected void setMedia(String media) {
		this.media = media;
	}
}
