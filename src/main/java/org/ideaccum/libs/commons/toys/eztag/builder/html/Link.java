package org.ideaccum.libs.commons.toys.eztag.builder.html;

/**
 * 汎用的なlinkタグを管理するインタフェースを提供します。<br>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2018/07/13  Kitagawa         新規作成
 *-->
 */
public class Link extends AbstractLink<Link> {

	/**
	 * コンストラクタ<br>
	 */
	public Link() {
		super();
	}

	/**
	 * リンクタイプを取得します。<br>
	 * @return リンクタイプ
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.html.AbstractLink#getRel()
	 */
	@Override
	public String getRel() {
		return super.getRel();
	}

	/**
	 * リンクタイプを設定します。<br>
	 * @param rel リンクタイプ
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.html.AbstractLink#setRel(java.lang.String)
	 */
	@Override
	public void setRel(String rel) {
		super.setRel(rel);
	}

	/**
	 * MIMEタイプを取得します。<br>
	 * @return MIMEタイプ
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.html.AbstractLink#getType()
	 */
	@Override
	public String getType() {
		return super.getType();
	}

	/**
	 * MIMEタイプを設定します。<br>
	 * @param type MIMEタイプ
	 * @see org.ideaccum.libs.commons.toys.eztag.builder.html.AbstractLink#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		super.setType(type);
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
