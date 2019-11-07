package org.ideaccum.libs.commons.toys.eztag.builder;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.ideaccum.libs.commons.util.ClassUtil;
import org.ideaccum.libs.commons.util.Loop;
import org.ideaccum.libs.commons.util.ObjectUtil;

/**
 * 各種タグクラスにおける共通的なインタフェースを提供します。<br>
 * <p>
 * 各タグクラスはこのクラスを継承したクラスとして提供されます。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2018/07/11  Kitagawa         新規作成
 *-->
 */
public abstract class AbstractHtmlElement<T extends AbstractHtmlElement<T>> implements Serializable, Cloneable {

	/**
	 * コンストラクタ<br>
	 */
	public AbstractHtmlElement() {
		super();
	}

	/**
	 * オブジェクト情報を文字列として提供します。<br>
	 * @return オブジェクト情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		for (Loop<Field> loop : Loop.each(ClassUtil.getFields(getClass()))) {
			Field field = loop.value();
			String name = field.getName();
			ElementText text = ClassUtil.getAnnotation(field, ElementText.class);
			ElementAttr attr = ClassUtil.getAnnotation(field, ElementAttr.class);
			if (attr == null && text == null) {
				continue;
			}
			if (text != null) {
				builder.append("text");
				builder.append("=");
				builder.append(ClassUtil.getFieldValue(this, field));
			}
			if (attr != null) {
				builder.append(name);
				builder.append("=");
				builder.append(ClassUtil.getFieldValue(this, field));
			}
			if (loop.hasNext()) {
				builder.append(", ");
			}
		}
		builder.append("}");
		return builder.toString();
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final T clone() {
		return (T) ObjectUtil.copy(this);
	}

	/**
	 * タグ要素名を取得します。<br>
	 * @return タグ要素名
	 */
	public final String getTagName() {
		ElementTag elementTag = ClassUtil.getAnnotation(getClass(), ElementTag.class);
		return elementTag.name();
	}

	/**
	 * タグ要素が閉じタグを出力するタグであるか判定します。<br>
	 * @return 閉じタグを出力するタグである場合にtrueを返却
	 */
	public final boolean isTagClosable() {
		ElementTag elementTag = ClassUtil.getAnnotation(getClass(), ElementTag.class);
		return elementTag.closable();
	}

	/**
	 * クラス内容から要素ビルダーオブジェクトを生成して提供します。<br>
	 * @return 要素ビルダーオブジェクト
	 */
	public final TagBuilder builder() {
		TagBuilder builder = new TagBuilder(getTagName());
		builder.setClosable(isTagClosable());
		for (Field field : ClassUtil.getFields(getClass())) {
			if (ClassUtil.isAnnotated(field, ElementAttr.class)) {
				ElementAttr define = ClassUtil.getAnnotation(field, ElementAttr.class);
				String name = define.name();
				Object value = ClassUtil.getFieldValue(this, field);
				if (value != null) {
					builder.getAttribute().put(name, value);
				}
			}
			if (ClassUtil.isAnnotated(field, ElementText.class)) {
				ElementText define = ClassUtil.getAnnotation(field, ElementText.class);
				Object value = ClassUtil.getFieldValue(this, field);
				if (value == null) {
					builder.removeText();
				} else {
					builder.setEscapeText(define.escape());
					builder.putText(value.toString());
				}
			}
		}
		return builder;
	}
}
