package org.ideaccum.libs.commons.toys.eztag.builder.html;

import org.ideaccum.libs.commons.toys.eztag.builder.AbstractHtmlElement;
import org.ideaccum.libs.commons.toys.eztag.builder.ElementAttr;
import org.ideaccum.libs.commons.toys.eztag.builder.ElementTag;

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
@ElementTag(name = "script", closable = true)
public class AbstractScript<T extends AbstractScript<T>> extends AbstractHtmlElement<T> {

	/** スクリプトMIMEタイプ */
	@ElementAttr(name = "type")
	private String type;

	/** リソースパス */
	@ElementAttr(name = "src")
	private String src;

	/** リソースキャラクタセット */
	@ElementAttr(name = "charset")
	private String charset;

	/**
	 * コンストラクタ<br>
	 */
	public AbstractScript() {
		super();
	}

	/**
	 * スクリプトMIMEタイプを取得します。<br>
	 * @return スクリプトMIMEタイプ
	 */
	public String getType() {
		return type;
	}

	/**
	 * スクリプトMIMEタイプを設定します。<br>
	 * @param type スクリプトMIMEタイプ
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * リソースパスを取得します。<br>
	 * @return リソースパス
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * リソースパスを設定します。<br>
	 * @param src リソースパス
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * リソースキャラクタセットを取得します。<br>
	 * @return リソースキャラクタセット
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * リソースキャラクタセットを設定します。<br>
	 * @param charset リソースキャラクタセット
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
}
