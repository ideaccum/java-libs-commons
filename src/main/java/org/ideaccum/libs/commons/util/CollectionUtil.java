package org.ideaccum.libs.commons.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * リスト及び、配列に対する操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * リスト及び、配列操作で利用頻度の高いリスト操作を提供します。<br>
 * </p>
 *
 * @author Kitagawa<br>
 *
 *<!--
 * 更新日		更新者			更新内容
 * 2018/05/02	Kitagawa		新規作成
 * 2018/05/24	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂に併せて、旧ListUtil、ArrayUtilを統合)
 *-->
 */
public final class CollectionUtil {

	/**
	 * コンストラクタ<br>
	 */
	private CollectionUtil() {
		super();
	}

	/**
	 * 要素配列をもとに{@link java.util.LinkedList}オブジェクトを生成して提供します。<br>
	 * @param <T> リスト要素タイプ
	 * @param type リスト要素クラス
	 * @param values 要素配列
	 * @return {@link java.util.LinkedList}オブジェクト
	 */
	public static <T extends Object> LinkedList<T> linkedList(@SuppressWarnings("unchecked") T... values) {
		LinkedList<T> list = new LinkedList<>();
		if (values == null) {
			return list;
		}
		for (T value : values) {
			list.add(value);
		}
		return list;
	}

	/**
	 * 要素配列をもとに{@link java.util.ArrayList}オブジェクトを生成して提供します。<br>
	 * @param <T> リスト要素タイプ
	 * @param type リスト要素クラス
	 * @param values 要素配列
	 * @return {@link java.util.ArrayList}オブジェクト
	 */
	public static <T extends Object> ArrayList<T> arrayList(@SuppressWarnings("unchecked") T... values) {
		ArrayList<T> list = new ArrayList<>();
		if (values == null) {
			return list;
		}
		for (T value : values) {
			list.add(value);
		}
		return list;
	}

	/**
	 * 要素配列をもとに{@link java.util.Vector}オブジェクトを生成して提供します。<br>
	 * @param <T> リスト要素タイプ
	 * @param type リスト要素クラス
	 * @param values 要素配列
	 * @return {@link java.util.Vector}オブジェクト
	 */
	public static <T extends Object> Vector<T> vector(@SuppressWarnings("unchecked") T... values) {
		Vector<T> list = new Vector<>();
		if (values == null) {
			return list;
		}
		for (T value : values) {
			list.add(value);
		}
		return list;
	}

	/**
	 * 真偽値のボクシング処理を行います。<br>
	 * @param values 真偽値配列
	 * @return 真偽値配列
	 */
	public static Boolean[] cast(boolean[] values) {
		if (values == null) {
			return null;
		}
		List<Boolean> list = new ArrayList<>();
		for (boolean value : values) {
			list.add(value);
		}
		return list.toArray(new Boolean[0]);
	}

	/**
	 * 文字配列のボクシング処理を行います。<br>
	 * @param values 文字配列
	 * @return 文字配列
	 */
	public static Character[] cast(char[] values) {
		if (values == null) {
			return null;
		}
		List<Character> list = new ArrayList<>();
		for (char value : values) {
			list.add(value);
		}
		return list.toArray(new Character[0]);
	}

	/**
	 * バイト配列のボクシング処理を行います。<br>
	 * @param values バイト配列
	 * @return バイト配列
	 */
	public static Byte[] cast(byte[] values) {
		if (values == null) {
			return null;
		}
		List<Byte> list = new ArrayList<>();
		for (byte value : values) {
			list.add(value);
		}
		return list.toArray(new Byte[0]);
	}

	/**
	 * Short配列のボクシング処理を行います。<br>
	 * @param values Short配列
	 * @return Short配列
	 */
	public static Short[] cast(short[] values) {
		if (values == null) {
			return null;
		}
		List<Short> list = new ArrayList<>();
		for (short value : values) {
			list.add(value);
		}
		return list.toArray(new Short[0]);
	}

	/**
	 * Ingeter配列のボクシング処理を行います。<br>
	 * @param values Ingeter配列
	 * @return Ingeter配列
	 */
	public static Integer[] cast(int[] values) {
		if (values == null) {
			return null;
		}
		List<Integer> list = new ArrayList<>();
		for (int value : values) {
			list.add(value);
		}
		return list.toArray(new Integer[0]);
	}

	/**
	 * Long配列のボクシング処理を行います。<br>
	 * @param values Long配列
	 * @return Long配列
	 */
	public static Long[] cast(long[] values) {
		if (values == null) {
			return null;
		}
		List<Long> list = new ArrayList<>();
		for (long value : values) {
			list.add(value);
		}
		return list.toArray(new Long[0]);
	}

	/**
	 * 単精度浮動小数配列のボクシング処理を行います。<br>
	 * @param values 単精度浮動小数配列
	 * @return 単精度浮動小数オブジェクト配列
	 */
	public static Float[] cast(float[] values) {
		if (values == null) {
			return null;
		}
		List<Float> list = new ArrayList<>();
		for (float value : values) {
			list.add(value);
		}
		return list.toArray(new Float[0]);
	}

	/**
	 * 倍精度浮動小数配列のボクシング処理を行います。<br>
	 * @param values 倍精度浮動小数配列
	 * @return 倍精度浮動小数オブジェクト配列
	 */
	public static Double[] cast(double[] values) {
		if (values == null) {
			return null;
		}
		List<Double> list = new ArrayList<>();
		for (double value : values) {
			list.add(value);
		}
		return list.toArray(new Double[0]);
	}

	/**
	 * 真偽値のアンボクシング処理を行います。<br>
	 * 配列内にnull要素が含まれる場合、該当要素はfalseとして設定されます。<br>
	 * @param values 真偽値配列
	 * @return 真偽値配列
	 */
	public static boolean[] cast(Boolean[] values) {
		if (values == null) {
			return null;
		}
		boolean[] array = new boolean[values.length];
		for (Loop<Boolean> loop : Loop.each(values)) {
			int i = loop.index();
			Boolean value = loop.value();
			if (value == null) {
				array[i] = false;
			} else {
				array[i] = value;
			}
		}
		return array;
	}

	/**
	 * 文字配列のアンボクシング処理を行います。<br>
	 * 配列内にnull要素が含まれる場合、該当要素は\0として設定されます。<br>
	 * @param values 文字配列
	 * @return 文字配列
	 */
	public static char[] cast(Character[] values) {
		if (values == null) {
			return null;
		}
		char[] array = new char[values.length];
		for (Loop<Character> loop : Loop.each(values)) {
			int i = loop.index();
			Character value = loop.value();
			if (value == null) {
				array[i] = '\0';
			} else {
				array[i] = value;
			}
		}
		return array;
	}

	/**
	 * バイト配列のアンボクシング処理を行います。<br>
	 * 配列内にnull要素が含まれる場合、該当要素は0x00として設定されます。<br>
	 * @param values バイト配列
	 * @return バイト配列
	 */
	public static byte[] cast(Byte[] values) {
		if (values == null) {
			return null;
		}
		byte[] array = new byte[values.length];
		for (Loop<Byte> loop : Loop.each(values)) {
			int i = loop.index();
			Byte value = loop.value();
			if (value == null) {
				array[i] = 0x00;
			} else {
				array[i] = value;
			}
		}
		return array;
	}

	/**
	 * Short配列のアンボクシング処理を行います。<br>
	 * 配列内にnull要素が含まれる場合、該当要素は0として設定されます。<br>
	 * @param values Short配列
	 * @return Short配列
	 */
	public static short[] cast(Short[] values) {
		if (values == null) {
			return null;
		}
		short[] array = new short[values.length];
		for (Loop<Short> loop : Loop.each(values)) {
			int i = loop.index();
			Short value = loop.value();
			if (value == null) {
				array[i] = 0;
			} else {
				array[i] = value;
			}
		}
		return array;
	}

	/**
	 * Ingeter配列のアンボクシング処理を行います。<br>
	 * 配列内にnull要素が含まれる場合、該当要素は0として設定されます。<br>
	 * @param values Ingeter配列
	 * @return Ingeter配列
	 */
	public static int[] cast(Integer[] values) {
		if (values == null) {
			return null;
		}
		int[] array = new int[values.length];
		for (Loop<Integer> loop : Loop.each(values)) {
			int i = loop.index();
			Integer value = loop.value();
			if (value == null) {
				array[i] = 0;
			} else {
				array[i] = value;
			}
		}
		return array;
	}

	/**
	 * Long配列のアンボクシング処理を行います。<br>
	 * 配列内にnull要素が含まれる場合、該当要素は0として設定されます。<br>
	 * @param values Long配列
	 * @return Long配列
	 */
	public static long[] cast(Long[] values) {
		if (values == null) {
			return null;
		}
		long[] array = new long[values.length];
		for (Loop<Long> loop : Loop.each(values)) {
			int i = loop.index();
			Long value = loop.value();
			if (value == null) {
				array[i] = 0;
			} else {
				array[i] = value;
			}
		}
		return array;
	}

	/**
	 * 単精度浮動小数配列のアンボクシング処理を行います。<br>
	 * 配列内にnull要素が含まれる場合、該当要素は0として設定されます。<br>
	 * @param values 単精度浮動小数オブジェクト配列
	 * @return 単精度浮動小数配列
	 */
	public static float[] cast(Float[] values) {
		if (values == null) {
			return null;
		}
		float[] array = new float[values.length];
		for (Loop<Float> loop : Loop.each(values)) {
			int i = loop.index();
			Float value = loop.value();
			if (value == null) {
				array[i] = 0;
			} else {
				array[i] = value;
			}
		}
		return array;
	}

	/**
	 * 倍精度浮動小数配列のアンボクシング処理を行います。<br>
	 * 配列内にnull要素が含まれる場合、該当要素は0として設定されます。<br>
	 * @param values 倍精度浮動小数オブジェクト配列
	 * @return 倍精度浮動小数配列
	 */
	public static double[] cast(Double[] values) {
		if (values == null) {
			return null;
		}
		double[] array = new double[values.length];
		for (Loop<Double> loop : Loop.each(values)) {
			int i = loop.index();
			Double value = loop.value();
			if (value == null) {
				array[i] = 0;
			} else {
				array[i] = value;
			}
		}
		return array;
	}

	/**
	 * リスト内に対象の要素が含まれるか判定します。<br>
	 * このメソッドは他のcontainsメソッドとの一貫性のために単純に{@link java.util.List#contains(Object)}を呼び出すメソッドとして設置されました。<br>
	 * @param list 検索対象リスト
	 * @param e 判定対象要素
	 * @return 対象要素が配列に含まれる場合にtrueを返却します。検索対象リストがnullの場合は一律falseが返却されます。
	 */
	public static boolean contains(List<?> list, Object e) {
		if (list == null) {
			return false;
		}
		return list.contains(e);
	}

	/**
	 * コレクション内に対象の要素が含まれるか判定します。<br>
	 * @param collection 検索対象コレクション
	 * @param e 判定対象要素
	 * @return 対象要素が配列に含まれる場合にtrueを返却します。検索対象リストがnullの場合は一律falseが返却されます。
	 */
	public static boolean contains(Collection<?> collection, Object e) {
		if (collection == null) {
			return false;
		}
		List<?> list = new ArrayList<>(collection);
		return list.contains(e);
	}

	/**
	 * 配列要素に対象の要素が含まれるか判定します。<br>
	 * この処理は内部的に配列をリストオブジェクト化して要素存在を判定する処理として提供します。<br>
	 * {@link java.util.Arrays#sort(Object[])}、{@link java.util.Arrays#binarySearch(Object[], Object)}を利用しないため、配列内の要素に変更は発生しません。<br>
	 * @param array 検索対象配列
	 * @param e 判定対象要素
	 * @return 対象要素が配列に含まれる場合にtrueを返却します。検索対象リストがnullの場合は一律falseが返却されます。
	 */
	public static boolean contains(Object[] array, Object e) {
		if (array == null) {
			return false;
		}
		return arrayList(array).contains(e);
	}

	/**
	 * プリミティブ配列要素に対象の要素が含まれるか判定します。<br>
	 * この処理は内部的に配列をボクシング化したリストに対して要素存在を判定する処理として提供します。<br>
	 * {@link java.util.Arrays#sort(Object[])}、{@link java.util.Arrays#binarySearch(Object[], Object)}を利用しないため、配列内の要素に変更は発生しません。<br>
	 * @param array 検索対象配列
	 * @param e 判定対象要素
	 * @return 対象要素が配列に含まれる場合にtrueを返却します。検索対象リストがnullの場合は一律falseが返却されます。
	 */
	public static boolean contains(boolean[] array, boolean e) {
		if (array == null) {
			return false;
		}
		return arrayList(cast(array)).contains(e);
	}

	/**
	 * プリミティブ配列要素に対象の要素が含まれるか判定します。<br>
	 * この処理は内部的に配列をボクシング化したリストに対して要素存在を判定する処理として提供します。<br>
	 * {@link java.util.Arrays#sort(Object[])}、{@link java.util.Arrays#binarySearch(Object[], Object)}を利用しないため、配列内の要素に変更は発生しません。<br>
	 * @param array 検索対象配列
	 * @param e 判定対象要素
	 * @return 対象要素が配列に含まれる場合にtrueを返却します。検索対象リストがnullの場合は一律falseが返却されます。
	 */
	public static boolean contains(char[] array, char e) {
		if (array == null) {
			return false;
		}
		return arrayList(cast(array)).contains(e);
	}

	/**
	 * プリミティブ配列要素に対象の要素が含まれるか判定します。<br>
	 * この処理は内部的に配列をボクシング化したリストに対して要素存在を判定する処理として提供します。<br>
	 * {@link java.util.Arrays#sort(Object[])}、{@link java.util.Arrays#binarySearch(Object[], Object)}を利用しないため、配列内の要素に変更は発生しません。<br>
	 * @param array 検索対象配列
	 * @param e 判定対象要素
	 * @return 対象要素が配列に含まれる場合にtrueを返却します。検索対象リストがnullの場合は一律falseが返却されます。
	 */
	public static boolean contains(byte[] array, byte e) {
		if (array == null) {
			return false;
		}
		return arrayList(cast(array)).contains(e);
	}

	/**
	 * プリミティブ配列要素に対象の要素が含まれるか判定します。<br>
	 * この処理は内部的に配列をボクシング化したリストに対して要素存在を判定する処理として提供します。<br>
	 * {@link java.util.Arrays#sort(Object[])}、{@link java.util.Arrays#binarySearch(Object[], Object)}を利用しないため、配列内の要素に変更は発生しません。<br>
	 * @param arrays オブジェクト配列
	 * @param e 判定対象要素
	 * @return 対象要素が配列に含まれる場合にtrueを返却します。検索対象リストがnullの場合は一律falseが返却されます。
	 */
	public static boolean contains(short[] arrays, short e) {
		if (arrays == null) {
			return false;
		}
		return arrayList(cast(arrays)).contains(e);
	}

	/**
	 * プリミティブ配列要素に対象の要素が含まれるか判定します。<br>
	 * この処理は内部的に配列をボクシング化したリストに対して要素存在を判定する処理として提供します。<br>
	 * {@link java.util.Arrays#sort(Object[])}、{@link java.util.Arrays#binarySearch(Object[], Object)}を利用しないため、配列内の要素に変更は発生しません。<br>
	 * @param arrays オブジェクト配列
	 * @param e 判定対象要素
	 * @return 対象要素が配列に含まれる場合にtrueを返却します。検索対象リストがnullの場合は一律falseが返却されます。
	 */
	public static boolean contains(int[] arrays, int e) {
		if (arrays == null) {
			return false;
		}
		return arrayList(cast(arrays)).contains(e);
	}

	/**
	 * プリミティブ配列要素に対象の要素が含まれるか判定します。<br>
	 * この処理は内部的に配列をボクシング化したリストに対して要素存在を判定する処理として提供します。<br>
	 * {@link java.util.Arrays#sort(Object[])}、{@link java.util.Arrays#binarySearch(Object[], Object)}を利用しないため、配列内の要素に変更は発生しません。<br>
	 * @param arrays オブジェクト配列
	 * @param e 判定対象要素
	 * @return 対象要素が配列に含まれる場合にtrueを返却します。検索対象リストがnullの場合は一律falseが返却されます。
	 */
	public static boolean contains(long[] arrays, long e) {
		if (arrays == null) {
			return false;
		}
		return arrayList(cast(arrays)).contains(e);
	}

	/**
	 * プリミティブ配列要素に対象の要素が含まれるか判定します。<br>
	 * この処理は内部的に配列をボクシング化したリストに対して要素存在を判定する処理として提供します。<br>
	 * {@link java.util.Arrays#sort(Object[])}、{@link java.util.Arrays#binarySearch(Object[], Object)}を利用しないため、配列内の要素に変更は発生しません。<br>
	 * @param array 検索対象配列
	 * @param e 判定対象要素
	 * @return 対象要素が配列に含まれる場合にtrueを返却します。検索対象リストがnullの場合は一律falseが返却されます。
	 */
	public static boolean contains(float[] array, float e) {
		if (array == null) {
			return false;
		}
		return arrayList(cast(array)).contains(e);
	}

	/**
	 * プリミティブ配列要素に対象の要素が含まれるか判定します。<br>
	 * この処理は内部的に配列をボクシング化したリストに対して要素存在を判定する処理として提供します。<br>
	 * {@link java.util.Arrays#sort(Object[])}、{@link java.util.Arrays#binarySearch(Object[], Object)}を利用しないため、配列内の要素に変更は発生しません。<br>
	 * @param array 検索対象配列
	 * @param e 判定対象要素
	 * @return 対象要素が配列に含まれる場合にtrueを返却します。検索対象リストがnullの場合は一律falseが返却されます。
	 */
	public static boolean contains(double[] array, double e) {
		if (array == null) {
			return false;
		}
		return arrayList(cast(array)).contains(e);
	}

	/**
	 * 配列をコピーして別の配列インスタンスとして返却します。<br>
	 * 配列内要素自体はシャローコピーとなります。<br>
	 * @param array コピー対象配列
	 * @return コピーされた配列
	 */
	public static <E extends Object> E[] copy(E[] array) {
		if (array == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		E[] dest = (E[]) Array.newInstance(array.getClass().getComponentType(), array.length);
		int length = array.length;
		System.arraycopy(array, 0, dest, 0, length);
		return dest;
	}

	/**
	 * プリミティブ配列をコピーして別のプリミティブ配列インスタンスとして返却します。<br>
	 * @param array コピー対象プリミティブ配列
	 * @return コピーされたプリミティブ配列
	 */
	public static boolean[] copy(boolean[] array) {
		if (array == null) {
			return null;
		}
		boolean[] dest = new boolean[array.length];
		int length = array.length;
		System.arraycopy(array, 0, dest, 0, length);
		return dest;
	}

	/**
	 * プリミティブ配列をコピーして別のプリミティブ配列インスタンスとして返却します。<br>
	 * @param array コピー対象プリミティブ配列
	 * @return コピーされたプリミティブ配列
	 */
	public static char[] copy(char[] array) {
		if (array == null) {
			return null;
		}
		char[] dest = new char[array.length];
		int length = array.length;
		System.arraycopy(array, 0, dest, 0, length);
		return dest;
	}

	/**
	 * プリミティブ配列をコピーして別のプリミティブ配列インスタンスとして返却します。<br>
	 * @param array コピー対象プリミティブ配列
	 * @return コピーされたプリミティブ配列
	 */
	public static byte[] copy(byte[] array) {
		if (array == null) {
			return null;
		}
		byte[] dest = new byte[array.length];
		int length = array.length;
		System.arraycopy(array, 0, dest, 0, length);
		return dest;
	}

	/**
	 * プリミティブ配列をコピーして別のプリミティブ配列インスタンスとして返却します。<br>
	 * @param array コピー対象プリミティブ配列
	 * @return コピーされたプリミティブ配列
	 */
	public static short[] copy(short[] array) {
		if (array == null) {
			return null;
		}
		short[] dest = new short[array.length];
		int length = array.length;
		System.arraycopy(array, 0, dest, 0, length);
		return dest;
	}

	/**
	 * プリミティブ配列をコピーして別のプリミティブ配列インスタンスとして返却します。<br>
	 * @param array コピー対象プリミティブ配列
	 * @return コピーされたプリミティブ配列
	 */
	public static int[] copy(int[] array) {
		if (array == null) {
			return null;
		}
		int[] dest = new int[array.length];
		int length = array.length;
		System.arraycopy(array, 0, dest, 0, length);
		return dest;
	}

	/**
	 * プリミティブ配列をコピーして別のプリミティブ配列インスタンスとして返却します。<br>
	 * @param array コピー対象プリミティブ配列
	 * @return コピーされたプリミティブ配列
	 */
	public static long[] copy(long[] array) {
		if (array == null) {
			return null;
		}
		long[] dest = new long[array.length];
		int length = array.length;
		System.arraycopy(array, 0, dest, 0, length);
		return dest;
	}

	/**
	 * プリミティブ配列をコピーして別のプリミティブ配列インスタンスとして返却します。<br>
	 * @param array コピー対象プリミティブ配列
	 * @return コピーされたプリミティブ配列
	 */
	public static float[] copy(float[] array) {
		if (array == null) {
			return null;
		}
		float[] dest = new float[array.length];
		int length = array.length;
		System.arraycopy(array, 0, dest, 0, length);
		return dest;
	}

	/**
	 * プリミティブ配列をコピーして別のプリミティブ配列インスタンスとして返却します。<br>
	 * @param array コピー対象プリミティブ配列
	 * @return コピーされたプリミティブ配列
	 */
	public static double[] copy(double[] array) {
		if (array == null) {
			return null;
		}
		double[] dest = new double[array.length];
		int length = array.length;
		System.arraycopy(array, 0, dest, 0, length);
		return dest;
	}

	/**
	 * 配列要素の順序を逆順にした別インスタンスとしての配列を返却します。<br>
	 * このメソッドの実行による元の配列内容は変更されません。<br>
	 * @param array 処理対象配列
	 * @return 要素順序が変更された別インスタンス配列
	 */
	public static <E extends Object> E[] reverse(E[] array) {
		if (array == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		E[] dest = (E[]) Array.newInstance(array.getClass().getComponentType(), array.length);
		for (int i = 0; i <= array.length - 1; i++) {
			dest[array.length - 1 - i] = array[i];
		}
		return dest;
	}

	/**
	 * 配列同士を結合して別の配列インスタンスとして提供します。<br>
	 * @param array1 結合対象配列
	 * @param array2 結合対象配列
	 * @return 結合された配列
	 */
	public static <E extends Object> E[] join(E[] array1, E[] array2) {
		if (array1 == null) {
			return copy(array2);
		} else if (array2 == null) {
			return copy(array1);
		}
		@SuppressWarnings("unchecked")
		E[] dest = (E[]) Array.newInstance(array1.getClass().getComponentType(), array1.length + array2.length);
		System.arraycopy(array1, 0, dest, 0, array1.length);
		System.arraycopy(array2, 0, dest, array1.length, array2.length);
		return dest;
	}

	/**
	 * 配列内に出現する要素の位置を返却します。<br>
	 * @param array 対象配列
	 * @param e 判定対象要素
	 * @return 配列内に出現するオブジェクトの位置
	 */
	public static int indexOf(Object[] array, Object e) {
		if (array == null) {
			return -1;
		}
		return Arrays.asList(array).indexOf(e);
	}

	/**
	 * 配列内で最後に出現する要素の位置を返却します。<br>
	 * @param array 対象配列
	 * @param e 判定対象
	 * @return 配列内に出現するオブジェクトの位置
	 */
	public static int lastIndexOf(Object[] array, Object e) {
		if (array == null) {
			return -1;
		}
		return Arrays.asList(array).lastIndexOf(e);
	}

	/**
	 * 配列の末端に新たな要素を追加した配列を提供します。<br>
	 * この処理によって元の配列自体への変更は発生しないことに注意してください。<br>
	 * @param arrays 対象配列
	 * @param e 追加要素
	 * @return 新たな要素が追加された配列
	 */
	public static <E extends Object> E[] add(E[] array, E e) {
		if (array == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		E[] dest = (E[]) Array.newInstance(array.getClass().getComponentType(), array.length + 1);
		System.arraycopy(array, 0, dest, 0, array.length);
		dest[dest.length - 1] = e;
		return dest;
	}
}
