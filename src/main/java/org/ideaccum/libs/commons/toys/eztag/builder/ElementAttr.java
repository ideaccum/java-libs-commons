package org.ideaccum.libs.commons.toys.eztag.builder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * タグ属性フィールドに対する各種属性情報を管理するためのアノテーションです。<br>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2018/07/11  Kitagawa         新規作成
 *-->
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Documented
public @interface ElementAttr {

	/**
	 * 属性名を提供します。<br>
	 * @return 属性名
	 */
	public String name();
}
