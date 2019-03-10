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
 * 2018/05/24	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
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
	 * クラスが継承する親クラス情報を再上位まで再帰的に検索して提供します。<br>
	 * 提供されるクラス順序は対象としたクラスが直接継承するクラスを先頭にした最上位に向かったクラス継承順となります。<br>
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
		List<Class<?>> classes = new LinkedList<>();
		for (Class<?> parent = clazz.getSuperclass(); parent != null; parent = parent.getSuperclass()) {
			if (!classes.contains(parent)) {
				classes.add(parent);
			}
		}
		return classes.toArray(new Class[0]);
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
		List<Class<?>> classes = new LinkedList<Class<?>>();
		for (Class<?> type : clazz.getInterfaces()) {
			if (!classes.contains(type)) {
				classes.add(type);
				for (Class<?> parent : getSuperClasses(type)) {
					if (!classes.contains(parent)) {
						classes.add(parent);
					}
				}
			}
		}
		for (Class<?> parent : getSuperClasses(clazz)) {
			for (Class<?> type : getInterfaces(parent)) {
				if (!classes.contains(type)) {
					classes.add(type);
				}
			}
		}
		return classes.toArray(new Class[0]);
	}

	/**
	 * クラスが提供するアノテーションを取得します。<br>
	 * アノテーションにおいてInheritが指定されていない場合でもクラスが継承する親クラスが持つアノテーションを検索します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param clazz 対象クラス
	 * @param type 取得アノテーションクラス
	 * @return アノテーションクラス
	 */
	public static <A extends Annotation> A getAnnotation(Class<?> clazz, Class<A> type) {
		if (clazz == null) {
			return null;
		}
		if (type == null) {
			return null;
		}
		List<Class<?>> classes = new LinkedList<>();
		classes.add(clazz);
		classes.addAll(Arrays.asList(getSuperClasses(clazz)));
		for (Class<?> e : classes) {
			try {
				A annotation = e.getDeclaredAnnotation(type);
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
	 * メソッドが提供するアノテーションを取得します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param method 対象メソッド
	 * @param type 取得アノテーションクラス
	 * @return アノテーションクラス
	 */
	public static <A extends Annotation> A getAnnotation(Method method, Class<A> type) {
		if (method == null) {
			return null;
		}
		method.setAccessible(true);
		return method.getAnnotation(type);
	}

	/**
	 * フィールドが提供するアノテーションを取得します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param field 対象フィールド
	 * @param type 取得アノテーションクラス
	 * @return アノテーションクラス
	 */
	public static <A extends Annotation> A getAnnotation(Field field, Class<A> type) {
		if (field == null) {
			return null;
		}
		field.setAccessible(true);
		return field.getAnnotation(type);
	}

	/**
	 * クラスが指定されたインタフェースを実装するクラスであるか判定します。<br>
	 * @param clazz 対象クラス
	 * @param type 判定対象インタフェースクラス
	 * @return クラスが指定されたインタフェースを実装するクラスである場合にtrueを返却
	 */
	public static boolean isImplemented(Class<?> clazz, Class<?> type) {
		if (clazz == null) {
			return false;
		}
		if (type == null) {
			return false;
		}
		if (clazz.isInterface() && clazz.equals(type)) {
			return true;
		}
		for (Class<?> c : getInterfaces(clazz)) {
			if (type.equals(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * クラスが指定されたクラスを継承するクラスであるか判定します。<br>
	 * @param clazz 対象クラス
	 * @param type 判定対象親クラス
	 * @return クラスが指定されたクラスを継承するクラスである場合にtrueを返却
	 */
	public static boolean isInherited(Class<?> clazz, Class<?> type) {
		if (clazz == null) {
			return false;
		}
		if (type == null) {
			return false;
		}
		if (type.equals(clazz)) {
			return true;
		}
		for (Class<?> c : getSuperClasses(clazz)) {
			if (type.equals(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * クラスがアノテーションを提供するか判定します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param clazz 対象クラス
	 * @param type 判定対象アノテーションクラス
	 * @return クラスがアノテーションを提供する場合にtrueを返却します
	 */
	public static <A extends Annotation> boolean isAnnotated(Class<?> clazz, Class<A> type) {
		return getAnnotation(clazz, type) != null;
	}

	/**
	 * メソッドがアノテーションを提供するか判定します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param method 対象メソッド
	 * @param type 判定対象アノテーションクラス
	 * @return メソッドがアノテーションを提供する場合にtrueを返却します
	 */
	public static <A extends Annotation> boolean isAnnotated(Method method, Class<A> type) {
		if (method == null) {
			return false;
		}
		return getAnnotation(method, type) != null;
	}

	/**
	 * フィールドがアノテーションを提供するか判定します。<br>
	 * @param <A> アノテーションクラスタイプ
	 * @param method 対象メソッド
	 * @param type 判定対象アノテーションクラス
	 * @return フィールドがアノテーションを提供する場合にtrueを返却します
	 */
	public static <A extends Annotation> boolean isAnnotated(Field method, Class<A> type) {
		if (method == null) {
			return false;
		}
		return getAnnotation(method, type) != null;
	}

	/**
	 * クラスが提供するフィールドを親クラスまで再帰的に検索して名称に合致するフィールドを取得します。<br>
	 * 継承している親クラスまでのフィールドを再帰検索する際に同一名称のフィールドが存在する場合は、自身から見て一番近いクラスのフィールドが提供されます。<br>
	 * 尚、ここで提供されるフィールドアクセス修飾子はpublicに限定しない全てのフィールドとなります。<br>
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
		List<Class<?>> classes = new LinkedList<>();
		classes.add(clazz);
		classes.addAll(Arrays.asList(getSuperClasses(clazz)));
		for (Class<?> e : classes) {
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
	 * クラスが提供するフィールドを親クラスまで再帰的に検索して取得します。<br>
	 * 尚、ここで提供されるフィールドアクセス修飾子はpublicに限定しない全てのフィールドとなります。<br>
	 * @param clazz 対象クラス
	 * @return クラスが提供するフィールド配列
	 */
	public static Field[] getFields(Class<?> clazz) {
		if (clazz == null) {
			return new Field[0];
		}
		List<Field> fields = new LinkedList<>();
		List<Class<?>> classes = new LinkedList<>();
		classes.add(clazz);
		classes.addAll(Arrays.asList(getSuperClasses(clazz)));
		for (Class<?> e : classes) {
			try {
				fields.addAll(Arrays.asList(e.getDeclaredFields()));
			} catch (SecurityException ex) {
			}
		}
		for (Field field : fields) {
			field.setAccessible(true);
		}
		return fields.toArray(new Field[0]);
	}

	/**
	 * クラスが提供するメソッドを親クラスまで再帰的に検索して取得します。<br>
	 * 継承している親クラスまでのメソッドを再帰検索する際に同一名称のメソッドが存在する場合は、自身から見て一番近いクラスのメソッドが提供されます。<br>
	 * 尚、ここで提供されるメソッドアクセス修飾子はpublicに限定しない全てのメソッドとなります。<br>
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
		List<Class<?>> classes = new LinkedList<>();
		classes.add(clazz);
		classes.addAll(Arrays.asList(getSuperClasses(clazz)));
		for (Class<?> e : classes) {
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
	 * クラスが提供するメソッドを親クラスまで再帰的に検索して取得します。<br>
	 * 尚、ここで提供されるメソッドアクセス修飾子はpublicに限定しない全てのメソッドとなります。<br>
	 * @param clazz 対象クラス
	 * @return クラスが提供するメソッド配列
	 */
	public static Method[] getMethods(Class<?> clazz) {
		if (clazz == null) {
			return new Method[0];
		}
		List<Method> fields = new LinkedList<>();
		List<Class<?>> classes = new LinkedList<>();
		classes.add(clazz);
		classes.addAll(Arrays.asList(getSuperClasses(clazz)));
		for (Class<?> e : classes) {
			try {
				fields.addAll(Arrays.asList(e.getDeclaredMethods()));
			} catch (SecurityException ex) {
			}
		}
		for (Method field : fields) {
			field.setAccessible(true);
		}
		return fields.toArray(new Method[0]);
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
	public static Class<?> getArrayComponent(Class<?> clazz) {
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
	public static Class<?> getArrayComponent(Field field) {
		if (field == null) {
			return null;
		}
		return getArrayComponent(field.getType());
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
	 * フィールド検索挙動仕様は{@link #getField(Class, String)}に準拠します。<br>
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
	 * フィールド検索挙動仕様は{@link #getField(Class, String)}に準拠します。<br>
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
	 * フィールド検索挙動仕様は{@link #getField(Class, String)}に準拠します。<br>
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
	 * フィールド検索挙動仕様は{@link #getField(Class, String)}に準拠します。<br>
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
		StringBuilder info = new StringBuilder();
		info.append(object.getClass().getName());
		info.append("#");
		info.append(name.trim());
		info.append("(");
		for (Loop<Class<?>> loop : Loop.each(parameterTypes)) {
			info.append(loop.value());
			info.append(loop.hasNext() ? ", " : "");
		}
		info.append(")");
		Method method = getMethod(object.getClass(), name, parameterTypes);
		if (method == null) {
			throw new NoSuchMethodError(info.toString());
		}
		try {
			return method.invoke(object, parameters);
		} catch (Throwable e) {
			throw new RuntimeException("Failed to invoke method(" + info.toString() + ")", e);
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
		StringBuilder info = new StringBuilder();
		info.append(clazz.getName());
		info.append("#");
		info.append(name.trim());
		info.append("(");
		for (Loop<Class<?>> loop : Loop.each(parameterTypes)) {
			info.append(loop.value());
			info.append(loop.hasNext() ? ", " : "");
		}
		info.append(")");
		Method method = getMethod(clazz, name, parameterTypes);
		if (method == null) {
			throw new NoSuchMethodError(info.toString());
		}
		try {
			return method.invoke(null, parameters);
		} catch (Throwable e) {
			throw new RuntimeException("Failed to invoke method(" + info.toString() + ")", e);
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
			throw new RuntimeException("Failed to create instance(" + clazz.getName() + ")", e);
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
			throw new RuntimeException("Failed to create instance(" + clazz.getName() + ")", e);
		}
	}
}
