package org.ideaccum.libs.commons.toys.eztag.builder.html;

/**
 * スタイルシートに特化したlinkタグを管理するインタフェースを提供します。<br>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/13	Kitagawa		新規作成
 *-->
 */
public class LinkStyleSheet extends AbstractLink<LinkStyleSheet> {

	/**
	 * コンストラクタ<br>
	 */
	public LinkStyleSheet() {
		super();
		setType("text/css");
		setRel("stylesheet");
	}

	/**
	 * コンストラクタ<br>
	 * @param href リソースパス
	 * @param charset キャラクタセット
	 * @param media メディアタイプ
	 */
	public LinkStyleSheet(String href, String charset, String media) {
		this();
		setHref(href);
		setCharset(charset);
		setMedia(media);
	}

	/**
	 * コンストラクタ<br>
	 * @param href リソースパス
	 * @param charset キャラクタセット
	 */
	public LinkStyleSheet(String href, String charset) {
		this();
		setHref(href);
		setCharset(charset);
	}

	/**
	 * コンストラクタ<br>
	 * @param href リソースパス
	 */
	public LinkStyleSheet(String href) {
		this();
		setHref(href);
	}

	/**
	 * リソースパスを取得します。<br>
	 * @return リソースパス
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.html.AbstractLink#getHref()
	 */
	@Override
	public String getHref() {
		return super.getHref();
	}

	/**
	 * リソースパスを設定します。<br>
	 * @param href リソースパス
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.html.AbstractLink#setHref(java.lang.String)
	 */
	@Override
	public void setHref(String href) {
		super.setHref(href);
	}

	/**
	 * キャラクタセットを取得します。<br>
	 * @return キャラクタセット
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.html.AbstractLink#getCharset()
	 */
	@Override
	public String getCharset() {
		return super.getCharset();
	}

	/**
	 * キャラクタセットを設定します。<br>
	 * @param charset キャラクタセット
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.html.AbstractLink#setCharset(java.lang.String)
	 */
	@Override
	public void setCharset(String charset) {
		super.setCharset(charset);
	}

	/**
	 * メディアタイプを取得します。<br>
	 * @return メディアタイプ
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.html.AbstractLink#getMedia()
	 */
	@Override
	public String getMedia() {
		// TODO メソッド
		return super.getMedia();
	}

	/**
	 * メディアタイプを設定します。<br>
	 * @param media メディアタイプ
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.html.AbstractLink#setMedia(java.lang.String)
	 */
	@Override
	public void setMedia(String media) {
		super.setMedia(media);
	}
}
