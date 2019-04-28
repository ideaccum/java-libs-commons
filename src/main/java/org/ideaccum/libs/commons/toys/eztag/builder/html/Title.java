package org.ideaccum.libs.commons.toys.eztag.builder.html;

import org.ideaccum.libs.commons.toys.eztag.builder.AbstractHtmlElement;
import org.ideaccum.libs.commons.toys.eztag.builder.ElementTag;
import org.ideaccum.libs.commons.toys.eztag.builder.ElementText;

/**
 * titleタグを管理するインタフェースを提供します。<br>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/13	Kitagawa		新規作成
 *-->
 */
@ElementTag(name = "title", closable = true)
public class Title extends AbstractHtmlElement<Title> {

	/** タイトル文字列 */
	@ElementText(escape = true)
	private String title;

	/**
	 * コンストラクタ<br>
	 * @param title タイトル文字列
	 */
	public Title(String title) {
		super();
		this.title = title;
	}

	/**
	 * コンストラクタ<br>
	 */
	public Title() {
		this(null);
	}

	/**
	 * タイトル文字列を取得します。<br>
	 * @return タイトル文字列
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * タイトル文字列を設定します。<br>
	 * @param title タイトル文字列
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
