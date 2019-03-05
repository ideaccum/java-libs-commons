package org.ideaccum.libs.commons.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * クラスに対する操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * リフレクションを利用したシステム実装基板開発等でクラスに対する各種操作の簡略化目的とした操作メソッドを提供します。<br>
 * </p>
 *
 * @author Kitagawa<br>
 *
 *<!--
 * 更新日		更新者			更新内容
 * 2012/10/02	Kitagawa		新規作成
 * 2018/05/24	Kitagawa		再構築(最低保証バージョンをJava8として全面改訂)
 *-->
 */
public final class ClassUtil {

	/**
	 * コンストラクタ<br>
	 */
	private ClassUtil() {
		super();
	}

	/**
	 * スタティックイニシャライザ<br>
	 */
	static {
		// JUnit+EclEmmaによるコードカバレッジの為のダミーコード
		new ClassUtil();
	}

	/**
	 * クラスが継承する親クラス情報を再上位まで再帰的に検索して提供します。<br>
	 * 提供される順序は対象としたクラスの直上のクラスを先頭にした最上位に向かったクラス継承順となります。<br>
	 * @param clazz 対象クラス
	 * @return クラスが継承する親クラス配列
	 */
	public static Class<?>[] getSuperClasses(Class<?> clazz) {
		if (clazz == null) {
			return new Class[0];
		}
		if (clazz.getSuperclass() == null) {
			return new Class[0];
		}
		List<Class<?>> list = new LinkedList<Class<?>>();
		for (Class<?> parent = clazz.getSuperclass(); parent != null; parent = parent.getSuperclass()) {
			if (!list.contains(parent)) {
				list.add(parent);
			}
		}
		return list.toArray(new Class[0]);
	}

	/**
	 * オブジェクトのクラスが継承する親クラス情報を再上位まで再帰的に検索して提供します。<br>
	 * 提供される順序は対象としたオブジェクトクラスの直上のクラスを先頭にした最上位に向かったクラス継承順となります。<br>
	 * オブジェクトがnullの場合は空の情報が提供されることに注意して下さい。<br>
	 * @param object 対象オブジェクト
	 * @return オブジェクトのクラスが継承する親クラス配列
	 */
	public static Class<?>[] getSuperClasses(Object object) {
		if (object == null) {
			return new Class[0];
		}
		return getSuperClasses(object.getClass());
	}

	/**
	 * クラスが実装するインタフェースを親クラスまで再帰的に検索して提供します。<br>
	 * @param clazz 対象クラス
	 * @return クラスが実装するインタフェースクラス配列
	 */
	public static Class<?>[] getInterfaces(Class<?> clazz) {
		if (clazz == null) {
			return new Class[0];
		}
		if (clazz.getInterfaces() == null) {
			return new Class[0];
		}
		List<Class<?>> list = new LinkedList<Class<?>>();
		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			if (!list.contains(interfaceClass)) {
				list.add(interfaceClass);
				for (Class<?> extendedInterface : getSuperClasses(interfaceClass)) {
					if (!list.contains(extendedInterface)) {
						list.add(extendedInterface);
					}
				}
			}
		}
		for (Class<?> superClass : getSuperClasses(clazz)) {
			for (Class<?> superInterface : getInterfaces(superClass)) {
				if (!list.contains(superInterface)) {
					list.add(superInterface);
				}
			}
		}
		return list.toArray(new Class[0]);
	}

	/**
	 * オブジェクトのクラスが実装するインタフェースを親クラスまで再帰的に検索して提供します。<br>
	 * オブジェクトがnullの場合は空の情報が提供されることに注意して下さい。<br>
	 * @param object 対象オブジェクト
	 * @return オブジェクトのクラスが実装するインタフェースクラス配列
	 */
	public static Class<?>[] getInterfaces(Object object) {
		if (object == null) {
			return new Class[0];
		}
		return getInterfaces(object.getClass());
	}

	/**
	 * クラスが提供するアノテーションを取得します。<br>
	 * アノテーションにおいてInheritが指定されていない場合でもクラスが継承する親クラスが持つアノテーションを検索します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param clazz 対象クラス
	 * @param annotationClass 取得アノテーションクラス
	 * @return アノテーションクラス
	 */
	public static <A extends Annotation> A getAnnotation(Class<?> clazz, Class<A> annotationClass) {
		if (clazz == null) {
			return null;
		}
		if (annotationClass == null) {
			return null;
		}
		List<Class<?>> classList = new LinkedList<>();
		classList.add(clazz);
		classList.addAll(Arrays.asList(getSuperClasses(clazz)));
		for (Class<?> e : classList) {
			try {
				A annotation = e.getDeclaredAnnotation(annotationClass);
				if (annotation == null) {
					continue;
				}
				return annotation;
			} catch (SecurityException ex) {
			}
		}
		return null;
	}

	/**
	 * オブジェクトのクラスが提供するアノテーションを取得します。<br>
	 * アノテーションにおいてInheritが指定されていない場合でもクラスが継承する親クラスが持つアノテーションを検索します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param object 対象オブジェクト
	 * @param annotationClass 取得アノテーションクラス
	 * @return オブジェクトのアノテーションクラス
	 */
	public static <A extends Annotation> A getAnnotation(Object object, Class<A> annotationClass) {
		if (object == null) {
			return null;
		}
		return getAnnotation(object.getClass(), annotationClass);
	}

	/**
	 * メソッドが提供するアノテーションを取得します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param method 対象メソッド
	 * @param annotationClass 取得アノテーションクラス
	 * @return アノテーションクラス
	 */
	public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationClass) {
		if (method == null) {
			return null;
		}
		method.setAccessible(true);
		return method.getAnnotation(annotationClass);
	}

	/**
	 * フィールドが提供するアノテーションを取得します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param field 対象フィールド
	 * @param annotationClass 取得アノテーションクラス
	 * @return アノテーションクラス
	 */
	public static <A extends Annotation> A getAnnotation(Field field, Class<A> annotationClass) {
		if (field == null) {
			return null;
		}
		field.setAccessible(true);
		return field.getAnnotation(annotationClass);
	}

	/**
	 * クラスが指定されたインタフェースを実装するクラスであるか判定します。<br>
	 * 尚、自分自身のクラスが指定された場合も実装クラスとして判定されることに注意して下さい。<br>
	 * これは利用者が該当クラスに対してインタフェースが利用可能かの判定の為に利用することを目的に利用する事を想定した仕様です。<br>
	 * @param clazz 対象クラス
	 * @param interfaceClass 判定対象インタフェースクラス
	 * @return クラスが指定されたインタフェースを実装するクラスである場合にtrueを返却
	 */
	public static boolean isImplemented(Class<?> clazz, Class<?> interfaceClass) {
		if (clazz == null) {
			return false;
		}
		if (interfaceClass == null) {
			return false;
		}
		if (clazz.isInterface() && clazz.equals(interfaceClass)) {
			return true;
		}
		for (Class<?> implemented : getInterfaces(clazz)) {
			if (interfaceClass.equals(implemented)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * オブジェクトのクラスが指定されたインタフェースを実装するクラスであるか判定します。<br>
	 * 尚、自分自身のクラスが指定された場合も実装クラスとして判定されることに注意して下さい。<br>
	 * これは利用者が該当クラスに対してインタフェースが利用可能かの判定の為に利用することを目的に利用する事を想定した仕様です。<br>
	 * @param object 対象オブジェクト
	 * @param interfaceClass 判定対象インタフェースクラス
	 * @return オブジェクトのクラスが指定されたインタフェースを実装するクラスである場合にtrueを返却
	 */
	public static boolean isImplemented(Object object, Class<?> interfaceClass) {
		if (object == null) {
			return false;
		}
		return isImplemented(object.getClass(), interfaceClass);
	}

	/**
	 * クラスが指定されたクラスを継承するクラスであるか判定します。<br>
	 * 尚、自分自身のクラスが指定された場合も継承クラスとして判定されることに注意して下さい。<br>
	 * これは利用者が該当クラスに対して継承元のクラスが利用可能かの判定の為に利用することを目的に利用する事を想定した仕様です。<br>
	 * @param clazz 対象クラス
	 * @param superClass 判定対象親クラス
	 * @return クラスが指定されたクラスを継承するクラスである場合にtrueを返却
	 */
	public static boolean isInherited(Class<?> clazz, Class<?> superClass) {
		if (clazz == null) {
			return false;
		}
		if (superClass == null) {
			return false;
		}
		if (superClass.equals(clazz)) {
			return true;
		}
		for (Class<?> inherited : getSuperClasses(clazz)) {
			if (superClass.equals(inherited)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * オブジェクトのクラスが指定されたクラスを継承するクラスであるか判定します。<br>
	 * 尚、自分自身のクラスが指定された場合も継承クラスとして判定されることに注意して下さい。<br>
	 * これは利用者が該当クラスに対して継承元のクラスが利用可能かの判定の為に利用することを目的に利用する事を想定した仕様です。<br>
	 * @param object 対象オブジェクト
	 * @param superClass 判定対象親クラス
	 * @return オブジェクトのクラスが指定されたクラスを継承するクラスである場合にtrueを返却
	 */
	public static boolean isInherited(Object object, Class<?> superClass) {
		if (object == null) {
			return false;
		}
		return isInherited(object.getClass(), superClass);
	}

	/**
	 * クラスがアノテーションを提供するか判定します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param clazz 対象クラス
	 * @param annotationClass 判定対象アノテーションクラス
	 * @return クラスがアノテーションを提供する場合にtrueを返却します
	 */
	public static <A extends Annotation> boolean isAnnotated(Class<?> clazz, Class<A> annotationClass) {
		return getAnnotation(clazz, annotationClass) != null;
	}

	/**
	 * オブジェクトのクラスがアノテーションを提供するか判定します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param object 対象オブジェクト
	 * @param annotationClass 判定対象アノテーションクラス
	 * @return オブジェクトのクラスがアノテーションを提供する場合にtrueを返却します
	 */
	public static <A extends Annotation> boolean isAnnotated(Object object, Class<A> annotationClass) {
		if (object == null) {
			return false;
		}
		return isAnnotated(object.getClass(), annotationClass);
	}

	/**
	 * メソッドがアノテーションを提供するか判定します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param method 対象メソッド
	 * @param annotationClass 判定対象アノテーションクラス
	 * @return オブジェクトのクラスがアノテーションを提供する場合にtrueを返却します
	 */
	public static <A extends Annotation> boolean isAnnotated(Method method, Class<A> annotationClass) {
		if (method == null) {
			return false;
		}
		return getAnnotation(method, annotationClass) != null;
	}

	/**
	 * フィールドがアノテーションを提供するか判定します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param method 対象メソッド
	 * @param annotationClass 判定対象アノテーションクラス
	 * @return オブジェクトのクラスがアノテーションを提供する場合にtrueを返却します
	 */
	public static <A extends Annotation> boolean isAnnotated(Field method, Class<A> annotationClass) {
		if (method == null) {
			return false;
		}
		return getAnnotation(method, annotationClass) != null;
	}

	/**
	 * クラスが提供するフィールドを親クラスまで再帰的に検索して名称に合致するフィールドを取得します。<br>
	 * 尚、ここで提供されるフィールドアクセス修飾子はpublicに限定しない全てのフィールドとなります。<br>
	 * また、継承している親クラスまでのフィールドを再帰検索する際に同一名称のフィールドが存在する場合は、自身から見て一番近いクラスのフィールドが提供されます。<br>
	 * @param clazz 対象クラス
	 * @param name フィールド名
	 * @return クラスが提供するフィールド
	 */
	public static Field getField(Class<?> clazz, String name) {
		if (clazz == null) {
			return null;
		}
		if (StringUtil.isBlank(name)) {
			return null;
		}
		List<Class<?>> classList = new LinkedList<>();
		classList.add(clazz);
		classList.addAll(Arrays.asList(getSuperClasses(clazz)));
		for (Class<?> e : classList) {
			try {
				Field field = e.getDeclaredField(name.trim());
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException ex) {
			} catch (SecurityException ex) {
			}
		}
		return null;
	}

	/**
	 * オブジェクトのクラスが提供するフィールドを親クラスまで再帰的に検索して名称に合致するフィールドを取得します。<br>
	 * 尚、ここで提供されるフィールドアクセス修飾子はpublicに限定しない全てのフィールドとなります。<br>
	 * また、継承している親クラスまでのフィールドを再帰検索する際に同一名称のフィールドが存在する場合は、自身から見て一番近いクラスのフィールドが提供されます。<br>
	 * オブジェクトがnullの場合は空の情報が提供されることに注意して下さい。<br>
	 * @param object 対象オブジェクト
	 * @param name フィールド名
	 * @return オブジェクトのクラスが提供するフィールド
	 */
	public static Field getField(Object object, String name) {
		if (object == null) {
			return null;
		}
		return getField(object.getClass(), name);
	}

	/**
	 * クラスが提供するフィールドを親クラスまで再帰的に検索して取得します。<br>
	 * 尚、ここで提供されるフィールドアクセス修飾子はpublicに限定しない全てのフィールドとなります。<br>
	 * @param clazz 対象クラス
	 * @return クラスが提供するフィールド配列
	 */
	public static Field[] getFields(Class<?> clazz) {
		if (clazz == null) {
			return new Field[0];
		}
		List<Field> fieldList = new LinkedList<>();
		List<Class<?>> classList = new LinkedList<>();
		classList.add(clazz);
		classList.addAll(Arrays.asList(getSuperClasses(clazz)));
		for (Class<?> e : classList) {
			try {
				fieldList.addAll(Arrays.asList(e.getDeclaredFields()));
			} catch (SecurityException ex) {
			}
		}
		for (Field field : fieldList) {
			field.setAccessible(true);
		}
		return fieldList.toArray(new Field[0]);
	}

	/**
	 * オブジェクトのクラスが提供するフィールドを親クラスまで再帰的に検索して取得します。<br>
	 * 尚、ここで提供されるフィールドアクセス修飾子はpublicに限定しない全てのフィールドとなります。<br>
	 * オブジェクトがnullの場合は空の情報が提供されることに注意して下さい。<br>
	 * @param object 対象オブジェクト
	 * @return オブジェクトのクラスが提供するフィールド配列
	 */
	public static Field[] getFields(Object object) {
		if (object == null) {
			return new Field[0];
		}
		return getFields(object.getClass());
	}

	/**
	 * クラスが提供するメソッドを親クラスまで再帰的に検索して取得します。<br>
	 * 尚、ここで提供されるメソッドアクセス修飾子はpublicに限定しない全てのメソッドとなります。<br>
	 * また、継承している親クラスまでのメソッドを再帰検索する際に同一名称のメソッドが存在する場合は、自身から見て一番近いクラスのメソッドが提供されます。<br>
	 * @param clazz 対象クラス
	 * @param name メソッド名
	 * @param parameterTypes パラメータクラス
	 * @return クラスが提供するメソッド
	 */
	public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
		if (clazz == null) {
			return null;
		}
		if (StringUtil.isBlank(name)) {
			return null;
		}
		List<Class<?>> classList = new LinkedList<>();
		classList.add(clazz);
		classList.addAll(Arrays.asList(getSuperClasses(clazz)));
		for (Class<?> e : classList) {
			try {
				Method method = e.getDeclaredMethod(name.trim(), parameterTypes);
				method.setAccessible(true);
				return method;
			} catch (NoSuchMethodException ex) {
			} catch (SecurityException ex) {
			}
		}
		return null;
	}

	/**
	 * オブジェクトのクラスが提供するメソッドを親クラスまで再帰的に検索して取得します。<br>
	 * 尚、ここで提供されるメソッドアクセス修飾子はpublicに限定しない全てのメソッドとなります。<br>
	 * また、継承している親クラスまでのメソッドを再帰検索する際に同一名称のメソッドが存在する場合は、自身から見て一番近いクラスのメソッドが提供されます。<br>
	 * @param object 対象オブジェクト
	 * @param name メソッド名
	 * @param parameterTypes パラメータクラス
	 * @return オブジェクトのクラスが提供するメソッド
	 */
	public static Method getMethod(Object object, String name, Class<?>... parameterTypes) {
		if (object == null) {
			return null;
		}
		return getMethod(object.getClass(), name, parameterTypes);
	}

	/**
	 * クラスが提供するメソッドを親クラスまで再帰的に検索して取得します。<br>
	 * 尚、ここで提供されるメソッドアクセス修飾子はpublicに限定しない全てのメソッドとなります。<br>
	 * @param clazz 対象クラス
	 * @return クラスが提供するメソッド配列
	 */
	public static Method[] getMethods(Class<?> clazz) {
		if (clazz == null) {
			return new Method[0];
		}
		List<Method> fieldList = new LinkedList<>();
		List<Class<?>> classList = new LinkedList<>();
		classList.add(clazz);
		classList.addAll(Arrays.asList(getSuperClasses(clazz)));
		for (Class<?> e : classList) {
			try {
				fieldList.addAll(Arrays.asList(e.getDeclaredMethods()));
			} catch (SecurityException ex) {
			}
		}
		for (Method field : fieldList) {
			field.setAccessible(true);
		}
		return fieldList.toArray(new Method[0]);
	}

	/**
	 * オブジェクトのクラスが提供するメソッドを親クラスまで再帰的に検索して取得します。<br>
	 * 尚、ここで提供されるメソッドアクセス修飾子はpublicに限定しない全てのメソッドとなります。<br>
	 * オブジェクトがnullの場合は空の情報が提供されることに注意して下さい。<br>
	 * @param object 対象オブジェクト
	 * @return オブジェクトのクラスが提供するメソッド配列
	 */
	public static Method[] getMethods(Object object) {
		if (object == null) {
			return new Method[0];
		}
		return getMethods(object.getClass());
	}

	/**
	 * クラスが配列であるか判定します。<br>
	 * @param clazz 対象クラス
	 * @return 指定されたクラスが配列である場合にtrueを返却
	 */
	public static boolean isArray(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return clazz.isArray();
	}

	/**
	 * フィールドが配列であるか判定します。<br>
	 * @param field 対象フィールド
	 * @return 指定されたフィールドが配列である場合にtrueを返却
	 */
	public static boolean isArray(Field field) {
		if (field == null) {
			return false;
		}
		return isArray(field.getType());
	}

	/**
	 * クラスの配列要素型を取得します。<br>
	 * クラスが配列でない場合は、自身のクラス型が提供されます。<br>
	 * @param clazz 対象クラス
	 * @return 配列の型を返却します
	 */
	public static Class<?> getComponentType(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		if (!isArray(clazz)) {
			return clazz;
		} else {
			return clazz.getComponentType();
		}
	}

	/**
	 * フィールドの配列要素型を取得します。<br>
	 * フィールドが配列でない場合は、自身のフィールド型が提供されます。<br>
	 * @param field 対象フィールド
	 * @return 配列の型を返却します
	 */
	public static Class<?> getComponentType(Field field) {
		if (field == null) {
			return null;
		}
		return getComponentType(field.getType());
	}

	/**
	 * フィールドに設定されている値をリフレクションを利用して取得します。<br>
	 * このメソッドはフィールドに対するアクセス時の例外をランタイム例外にラップしたメソッドです。<br>
	 * @param object 対象オブジェクト
	 * @param field フィールドオブジェクト
	 * @return オブジェクトのクラスが提供するフィールド
	 */
	public static Object getFieldValue(Object object, Field field) {
		if (field == null) {
			return null;
		}
		try {
			Object value = field.get(object);
			return value;
		} catch (Throwable e) {
			throw new RuntimeException("Failed access field value", e);
		}
	}

	/**
	 * 親クラスまで再帰的に検索して名称に合致するフィールド値を取得します。<br>
	 * フィールド検索挙動仕様は{@link #getField(Object, String)}に準拠します。<br>
	 * @param object 対象オブジェクト
	 * @param name フィールド名
	 * @return オブジェクトのクラスが提供するフィールド
	 */
	public static Object getFieldValue(Object object, String name) {
		if (object == null) {
			return null;
		}
		Field field = getField(object.getClass(), name);
		return getFieldValue(object, field);
	}

	/**
	 * フィールドに対してリフレクションを利用してフィールド値を設定します。<br>
	 * このメソッドはフィールドに対するアクセス時の例外をランタイム例外にラップしたメソッドです。<br>
	 * @param object 対象オブジェクト
	 * @param field フィールドオブジェクト
	 * @param value フィールド値
	 */
	public static void setFieldValue(Object object, Field field, Object value) {
		if (object == null) {
			return;
		}
		try {
			field.set(object, value);
		} catch (Throwable e) {
			throw new RuntimeException("Failed access field value", e);
		}
	}

	/**
	 * 親クラスまで再帰的に検索して名称に合致するフィールド値を設定します。<br>
	 * フィールド検索挙動仕様は{@link #getField(Object, String)}に準拠します。<br>
	 * @param object 対象オブジェクト
	 * @param name フィールド名
	 * @param value フィールド値
	 */
	public static void setFieldValue(Object object, String name, Object value) {
		if (object == null) {
			return;
		}
		Field field = getField(object.getClass(), name);
		setFieldValue(object, field, value);
	}

	/**
	 * フィールドに設定されている値をリフレクションを利用して取得します。<br>
	 * このメソッドはフィールドに対するアクセス時の例外をランタイム例外にラップしたメソッドです。<br>
	 * @param field フィールドオブジェクト
	 * @return オブジェクトのクラスが提供するフィールド
	 */
	public static Object getStaticFieldValue(Field field) {
		if (field == null) {
			return null;
		}
		try {
			Object value = field.get(null);
			return value;
		} catch (Throwable e) {
			throw new RuntimeException("Failed access field value", e);
		}
	}

	/**
	 * 親クラスまで再帰的に検索して名称に合致するフィールド値を取得します。<br>
	 * フィールド検索挙動仕様は{@link #getField(Object, String)}に準拠します。<br>
	 * @param clazz 対象クラス
	 * @param name フィールド名
	 * @return オブジェクトのクラスが提供するフィールド
	 */
	public static Object getStaticFieldValue(Class<?> clazz, String name) {
		if (clazz == null) {
			return null;
		}
		Field field = getField(clazz, name);
		return getStaticFieldValue(field);
	}

	/**
	 * フィールドに対してリフレクションを利用してフィールド値を設定します。<br>
	 * このメソッドはフィールドに対するアクセス時の例外をランタイム例外にラップしたメソッドです。<br>
	 * @param field フィールドオブジェクト
	 * @param value フィールド値
	 */
	public static void setStaticFieldValue(Field field, Object value) {
		if (field == null) {
			return;
		}
		try {
			field.set(null, value);
		} catch (Throwable e) {
			throw new RuntimeException("Failed access field value", e);
		}
	}

	/**
	 * 親クラスまで再帰的に検索して名称に合致するフィールド値を設定します。<br>
	 * フィールド検索挙動仕様は{@link #getField(Object, String)}に準拠します。<br>
	 * @param clazz 対象クラス
	 * @param name フィールド名
	 * @param value フィールド値
	 */
	public static void setStaticFieldValue(Class<?> clazz, String name, Object value) {
		if (clazz == null) {
			return;
		}
		Field field = getField(clazz, name);
		setStaticFieldValue(field, value);
	}

	/**
	 * オブジェクトメソッドを実行します。<br>
	 * オブジェクトがnullの場合は例外はスローせずにnull結果を返却します。<br>
	 * @param object 対象オブジェクト
	 * @param name メソッド名
	 * @param parameterTypes メソッドパラメータタイプ配列
	 * @param parameters メソッドパラメータ配列
	 * @return メソッド実行返却値
	 */
	public static Object invokeMethod(Object object, String name, Class<?>[] parameterTypes, Object[] parameters) {
		if (object == null) {
			return null;
		}
		if (StringUtil.isBlank(name)) {
			return null;
		}
		StringBuilder methodInfo = new StringBuilder();
		methodInfo.append(object.getClass().getName());
		methodInfo.append("#");
		methodInfo.append(name.trim());
		methodInfo.append("(");
		for (Loop<Class<?>> loop : Loop.each(parameterTypes)) {
			methodInfo.append(loop.value());
			methodInfo.append(loop.hasNext() ? ", " : "");
		}
		methodInfo.append(")");
		Method method = getMethod(object.getClass(), name, parameterTypes);
		if (method == null) {
			throw new NoSuchMethodError(methodInfo.toString());
		}
		try {
			return method.invoke(object, parameters);
		} catch (Throwable e) {
			throw new RuntimeException("Failed to invoke method(" + methodInfo.toString() + ")", e);
		}
	}

	/**
	 * パラメータを持たないオブジェクトメソッドを実行します。<br>
	 * オブジェクトがnullの場合は例外はスローせずにnull結果を返却します。<br>
	 * @param object 対象オブジェクト
	 * @param name メソッド名
	 * @return メソッド実行結果
	 */
	public static Object invokeMethod(Object object, String name) {
		return invokeMethod(object, name, new Class[0], new Object[0]);
	}

	/**
	 * クラスメソッドを実行します。<br>
	 * @param clazz クラス
	 * @param name メソッド名
	 * @param parameterTypes メソッドパラメータタイプ配列
	 * @param parameters メソッドパラメータ配列
	 * @return メソッド実行返却値
	 */
	public static Object invokeMethod(Class<?> clazz, String name, Class<?>[] parameterTypes, Object[] parameters) {
		if (clazz == null) {
			return null;
		}
		if (StringUtil.isBlank(name)) {
			return null;
		}
		StringBuilder methodInfo = new StringBuilder();
		methodInfo.append(clazz.getName());
		methodInfo.append("#");
		methodInfo.append(name.trim());
		methodInfo.append("(");
		for (Loop<Class<?>> loop : Loop.each(parameterTypes)) {
			methodInfo.append(loop.value());
			methodInfo.append(loop.hasNext() ? ", " : "");
		}
		methodInfo.append(")");
		Method method = getMethod(clazz, name, parameterTypes);
		if (method == null) {
			throw new NoSuchMethodError(methodInfo.toString());
		}
		try {
			return method.invoke(null, parameters);
		} catch (Throwable e) {
			throw new RuntimeException("Failed to invoke method(" + methodInfo.toString() + ")", e);
		}
	}

	/**
	 * パラメータを持たないクラスメソッドを実行します。<br>
	 * @param clazz クラス
	 * @param name メソッド名
	 * @return メソッド実行結果
	 */
	public static Object invokeMethod(Class<?> clazz, String name) {
		return invokeMethod(clazz, name, new Class[0], new Object[0]);
	}

	/**
	 * パラメータ無しのコンストラクタでクラスインスタンスを生成します。<br>
	 * @param <T> クラスタイプ
	 * @param clazz 対象クラス
	 * @return クラスインスタンス
	 */
	public static <T> T createInstance(Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		try {
			Constructor<T> constructor = clazz.getConstructor();
			T instance = constructor.newInstance();
			return instance;
		} catch (Throwable e) {
			throw new RuntimeException("Failed to create instance(" + clazz.getName() + ")");
		}
	}

	/**
	 * パラメータ付きのコンストラクタでクラスインスタンスを生成します。<br>
	 * @param <T> クラスタイプ
	 * @param clazz 対象クラス
	 * @param parameterTypes コンストラクタパラメータタイプ
	 * @param parameters コンストラクタパラメータオブジェクト
	 * @return クラスインスタンス
	 */
	public static <T> T createInstance(Class<T> clazz, Class<?>[] parameterTypes, Object[] parameters) {
		if (clazz == null) {
			return null;
		}
		try {
			Constructor<T> constructor = clazz.getConstructor(parameterTypes);
			T instance = constructor.newInstance(parameters);
			return instance;
		} catch (Throwable e) {
			throw new RuntimeException("Failed to create instance(" + clazz.getName() + ")");
		}
	}
}
