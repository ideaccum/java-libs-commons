package org.ideaccum.libs.commons.toys.eztag.builder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * タグクラスに対する各種属性情報を管理するためのアノテーションです。<br>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/07/11	Kitagawa		新規作成
 *-->
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
public @interface ElementTag {

	/**
	 * タグ要素名を提供します。<br>
	 * @return タグ要素名
	 */
	public String name();

	/**
	 * タグ要素が閉じタグを利用して出力するかを提供します。<br>
	 * @return 閉じタグを利用して出力する場合にtrueを返却
	 */
	public boolean closable();
}
