package org.ideaccum.libs.commons.eztag.builder.html;

/**
 * scriptタグを管理するインタフェースを提供します。<br>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
public class Script extends AbstractScript<Script> {

	/**
	 * コンストラクタ<br>
	 */
	public Script() {
		super();
	}

	/**
	 * スクリプトMIMEタイプを取得します。<br>
	 * @return スクリプトMIMEタイプ
	 * @see org.ideaccum.libs.commons.eztag.builder.html.AbstractScript#getType()
	 */
	@Override
	public String getType() {
		return super.getType();
	}

	/**
	 * スクリプトMIMEタイプを設定します。<br>
	 * @param type スクリプトMIMEタイプ
	 * @see org.ideaccum.libs.commons.eztag.builder.html.AbstractScript#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		super.setType(type);
	}

	/**
	 * リソースパスを取得します。<br>
	 * @return リソースパス
	 * @see org.ideaccum.libs.commons.eztag.builder.html.AbstractScript#getSrc()
	 */
	@Override
	public String getSrc() {
		return super.getSrc();
	}

	/**
	 * リソースパスを設定します。<br>
	 * @param src リソースパス
	 * @see org.ideaccum.libs.commons.eztag.builder.html.AbstractScript#setSrc(java.lang.String)
	 */
	@Override
	public void setSrc(String src) {
		super.setSrc(src);
	}

	/**
	 * リソースキャラクタセットを取得します。<br>
	 * @return リソースキャラクタセット
	 * @see org.ideaccum.libs.commons.eztag.builder.html.AbstractScript#getCharset()
	 */
	@Override
	public String getCharset() {
		return super.getCharset();
	}

	/**
	 * リソースキャラクタセットを設定します。<br>
	 * @param charset リソースキャラクタセット
	 * @see org.ideaccum.libs.commons.eztag.builder.html.AbstractScript#setCharset(java.lang.String)
	 */
	@Override
	public void setCharset(String charset) {
		super.setCharset(charset);
	}
}
