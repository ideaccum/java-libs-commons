package org.ideaccum.libs.commons.toys.eztag.builder;

import java.io.Serializable;

/**
 * タグ要素や属性要素を管理するための上位共通インタフェースを提供します。<br>
 * <p>
 * 各種タグ要素や属性要素クラスはこのクラスを継承したクラスとして設置され、インタフェースが標準化されます。<br>
 * </p>
 * 
 * @param <T> 自身のクラス型
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/12	Kitagawa		新規作成
 *-->
 */
public abstract class TagEntry<T extends TagEntry<T>> implements Serializable, Cloneable {

	/**
	 * コンストラクタ<br>
	 */
	protected TagEntry() {
		super();
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 */
	@Override
	public abstract T clone();

	/**
	 * オブジェクト情報を文字列として提供します。<br>
	 * @return オブジェクト情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public abstract String toString();
}
