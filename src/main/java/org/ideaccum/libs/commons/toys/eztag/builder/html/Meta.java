package org.ideaccum.libs.commons.toys.eztag.builder.html;

import org.ideaccum.libs.commons.toys.eztag.builder.AbstractHtmlElement;
import org.ideaccum.libs.commons.toys.eztag.builder.ElementAttr;
import org.ideaccum.libs.commons.toys.eztag.builder.ElementTag;

/**
 * metaタグを管理するインタフェースを提供します。<br>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2018/07/11  Kitagawa         新規作成
 *-->
 */
@ElementTag(name = "meta", closable = false)
public class Meta extends AbstractHtmlElement<Meta> {

	/** メタデータ名 */
	@ElementAttr(name = "name")
	private String name;

	/** プラグマ指示子 */
	@ElementAttr(name = "http-equiv")
	private String httpEquiv;

	/** メタデータ値 */
	@ElementAttr(name = "content")
	private String content;

	/** エンコーディング */
	@ElementAttr(name = "charset")
	private String charset;

	/**
	 * コンストラクタ<br>
	 */
	public Meta() {
		super();
	}

	/**
	 * エンコーディング指定用のmeta情報を保持するクラスインスタンスを生成します。<br>
	 * @param charset エンコーディング
	 * @return クラスインスタンス
	 */
	public static Meta createCharset(String charset) {
		Meta instance = new Meta();
		instance.setCharset(charset);
		return instance;
	}

	/**
	 * プラグマ指示情報用のmeta情報を保持するクラスインスタンスを生成します。<br>
	 * @param httpEquiv プラグマ指示子
	 * @param content メタデータ値
	 * @return クラスインスタンス
	 */
	public static Meta createHttpEquiv(String httpEquiv, String content) {
		Meta instance = new Meta();
		instance.setHttpEquiv(httpEquiv);
		instance.setContent(content);
		return instance;
	}

	/**
	 * 汎用的なmeta情報を保持するクラスインスタンスを生成します。<br>
	 * @param name メタデータ名
	 * @param content メタデータ値
	 * @return クラスインスタンス
	 */
	public static Meta createMeta(String name, String content) {
		Meta instance = new Meta();
		instance.setName(name);
		instance.setContent(content);
		return instance;
	}

	/**
	 * コンストラクタ<br>
	 * @param charset キャラクタセット
	 */
	public Meta(String charset) {
		super();
		this.charset = charset;
	}

	/**
	 * メタデータ名を取得します。<br>
	 * @return メタデータ名
	 */
	public String getName() {
		return name;
	}

	/**
	 * メタデータ名を設定します。<br>
	 * @param name メタデータ名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * プラグマ指示子を取得します。<br>
	 * @return プラグマ指示子
	 */
	public String getHttpEquiv() {
		return httpEquiv;
	}

	/**
	 * プラグマ指示子を設定します。<br>
	 * @param httpEquiv プラグマ指示子
	 */
	public void setHttpEquiv(String httpEquiv) {
		this.httpEquiv = httpEquiv;
	}

	/**
	 * メタデータ値を取得します。<br>
	 * @return メタデータ値
	 */
	public String getContent() {
		return content;
	}

	/**
	 * メタデータ値を設定します。<br>
	 * @param content メタデータ値
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * エンコーディングを取得します。<br>
	 * @return エンコーディング
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * エンコーディングを設定します。<br>
	 * @param charset エンコーディング
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
}
