package org.ideaccum.libs.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * オブジェクトに対する操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * システム開発時に利用するオブジェクト操作で利用頻度の高い文字列操作をメソッドとして提供します。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2005/07/02	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		新規作成(最低保証バージョンをJava8として全面改訂)
 *-->
 */
public final class ObjectUtil {

	/**
	 * コンストラクタ<br>
	 */
	private ObjectUtil() {
		super();
	}

	/**
	 * スタティックイニシャライザ<br>
	 */
	static {
		// JUnit+EclEmmaによるコードカバレッジの為のダミーコード
		new ObjectUtil();
	}

	/**
	 * オブジェクト同士が同一オブジェクトであるか判定します。<br>
	 * このメソッドでは比較対象同士がnull同士である場合にも{@link java.lang.NullPointerException}はスローせずに同一として判定します。<br>
	 * 判定処理自体は指定されたオブジェクトの{@link java.lang.Object#equals(Object)}処理に委譲します。<br>
	 * @param <T> 比較対象オブジェクト型
	 * @param object1 比較対象オブジェクト
	 * @param object2 比較対象オブジェクト
	 * @return 同一オブジェクトである場合にtrueを返却
	 */
	public static <T> boolean equals(T object1, T object2) {
		if (object1 == null && object2 == null) {
			return true;
		} else if (object1 != null) {
			return object1.equals(object2);
		} else {
			return object2.equals(object1);
		}
	}

	/**
	 * オブジェクト同士を比較した結果を返却します。<br>
	 * このメソッドではnullが考慮された処理を行い、null同士の場合は0、インスタンス存在から見たnullは1として判定します。<br>
	 * @param <T> 比較対象オブジェクト型
	 * @param object1 比較元文字列
	 * @param object2 比較先文字列
	 * @return 比較元文字列が大きい場合に1以上、一致する場合は0、比較先文字列が大きい場合に-1以下を返却
	 */
	public static <T extends Comparable<T>> int compare(T object1, T object2) {
		if (object1 == null && object2 == null) {
			return 0;
		} else if (object1 != null) {
			if (object2 == null) {
				return 1;
			} else {
				return object1.compareTo(object2);
			}
		} else {
			return -1;
		}
	}

	/**
	 * シリアライズインタフェースを実装されているオブジェクトのディープコピーを行います。<br>
	 * @param <E> オブジェクトクラスタイプ
	 * @param object ディープコピー対象オブジェクト
	 * @return ディープコピーされたオブジェクト
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Serializable> E copy(E object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.flush();
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			E copy = (E) ois.readObject();
			oos.close();
			ois.close();
			return copy;
		} catch (Throwable e) {
			throw new RuntimeException("Failed to deep copy object", e);
		}
	}

	/**
	 * 16ビット整数配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param chars 16ビット整数配列
	 * @return 16ビット整数オブジェクト配列
	 */
	public static Character[] cast(char[] chars) {
		if (chars == null) {
			return null;
		}
		List<Character> list = new LinkedList<Character>();
		for (char c : chars) {
			list.add(c);
		}
		return list.toArray(new Character[0]);
	}

	/**
	 * 16ビット整数配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
	 * 尚、オブジェクト配列内にnullが存在する場合はnull16ビット整数が要素として設定されます。<br>
	 * @param chars 16ビット整数オブジェクト配列
	 * @return 16ビット整数配列
	 */
	public static char[] cast(Character[] chars) {
		if (chars == null) {
			return null;
		}
		char[] array = new char[chars.length];
		for (Loop<Character> loop : Loop.each(chars)) {
			int i = loop.index();
			Character c = loop.value();
			if (c == null) {
				array[i] = '\0';
			} else {
				array[i] = c;
			}
		}
		return array;
	}

	/**
	 * 8ビット整数配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param bytes 8ビット整数配列
	 * @return 8ビット整数オブジェクト配列
	 */
	public static Byte[] cast(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		List<Byte> list = new LinkedList<Byte>();
		for (byte c : bytes) {
			list.add(c);
		}
		return list.toArray(new Byte[0]);
	}

	/**
	 * 8ビット整数配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
	 * 尚、オブジェクト配列内にnullが存在する場合は0が要素として設定されます。<br>
	 * @param bytes 8ビット整数オブジェクト配列
	 * @return 8ビット整数配列
	 */
	public static byte[] cast(Byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		byte[] array = new byte[bytes.length];
		for (Loop<Byte> loop : Loop.each(bytes)) {
			int i = loop.index();
			Byte c = loop.value();
			if (c == null) {
				array[i] = 0x00;
			} else {
				array[i] = c;
			}
		}
		return array;
	}

	/**
	 * 16ビット整数配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param shorts 16ビット整数配列
	 * @return 16ビット整数オブジェクト配列
	 */
	public static Short[] cast(short[] shorts) {
		if (shorts == null) {
			return null;
		}
		List<Short> list = new LinkedList<Short>();
		for (short c : shorts) {
			list.add(c);
		}
		return list.toArray(new Short[0]);
	}

	/**
	 * 16ビット整数配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
	 * 尚、オブジェクト配列内にnullが存在する場合は0が要素として設定されます。<br>
	 * @param shorts 16ビット整数オブジェクト配列
	 * @return 16ビット整数配列
	 */
	public static short[] cast(Short[] shorts) {
		if (shorts == null) {
			return null;
		}
		short[] array = new short[shorts.length];
		for (Loop<Short> loop : Loop.each(shorts)) {
			int i = loop.index();
			Short c = loop.value();
			if (c == null) {
				array[i] = 0;
			} else {
				array[i] = c;
			}
		}
		return array;
	}

	/**
	 * 32ビット整数配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param ints 32ビット整数配列
	 * @return 32ビット整数オブジェクト配列
	 */
	public static Integer[] cast(int[] ints) {
		if (ints == null) {
			return null;
		}
		List<Integer> list = new LinkedList<Integer>();
		for (int c : ints) {
			list.add(c);
		}
		return list.toArray(new Integer[0]);
	}

	/**
	 * 32ビット整数配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
	 * 尚、オブジェクト配列内にnullが存在する場合は0が要素として設定されます。<br>
	 * @param ints 32ビット整数オブジェクト配列
	 * @return 32ビット整数配列
	 */
	public static int[] cast(Integer[] ints) {
		if (ints == null) {
			return null;
		}
		int[] array = new int[ints.length];
		for (Loop<Integer> loop : Loop.each(ints)) {
			int i = loop.index();
			Integer c = loop.value();
			if (c == null) {
				array[i] = 0;
			} else {
				array[i] = c;
			}
		}
		return array;
	}

	/**
	 * 64ビット整数配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param longs 64ビット整数配列
	 * @return 64ビット整数オブジェクト配列
	 */
	public static Long[] cast(long[] longs) {
		if (longs == null) {
			return null;
		}
		List<Long> list = new LinkedList<Long>();
		for (long c : longs) {
			list.add(c);
		}
		return list.toArray(new Long[0]);
	}

	/**
	 * 64ビット整数配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
	 * 尚、オブジェクト配列内にnullが存在する場合は0が要素として設定されます。<br>
	 * @param longs 64ビット整数オブジェクト配列
	 * @return 64ビット整数配列
	 */
	public static long[] cast(Long[] longs) {
		if (longs == null) {
			return null;
		}
		long[] array = new long[longs.length];
		for (Loop<Long> loop : Loop.each(longs)) {
			int i = loop.index();
			Long c = loop.value();
			if (c == null) {
				array[i] = 0;
			} else {
				array[i] = c;
			}
		}
		return array;
	}

	/**
	 * 単精度浮動小数配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param floats 単精度浮動小数配列
	 * @return 単精度浮動小数オブジェクト配列
	 */
	public static Float[] cast(float[] floats) {
		if (floats == null) {
			return null;
		}
		List<Float> list = new LinkedList<Float>();
		for (float c : floats) {
			list.add(c);
		}
		return list.toArray(new Float[0]);
	}

	/**
	 * 単精度浮動小数配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
	 * 尚、オブジェクト配列内にnullが存在する場合は0が要素として設定されます。<br>
	 * @param floats 単精度浮動小数オブジェクト配列
	 * @return 単精度浮動小数配列
	 */
	public static float[] cast(Float[] floats) {
		if (floats == null) {
			return null;
		}
		float[] array = new float[floats.length];
		for (Loop<Float> loop : Loop.each(floats)) {
			int i = loop.index();
			Float c = loop.value();
			if (c == null) {
				array[i] = 0;
			} else {
				array[i] = c;
			}
		}
		return array;
	}

	/**
	 * 倍精度浮動小数配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param doubles 倍精度浮動小数配列
	 * @return 倍精度浮動小数オブジェクト配列
	 */
	public static Double[] cast(double[] doubles) {
		if (doubles == null) {
			return null;
		}
		List<Double> list = new LinkedList<Double>();
		for (double c : doubles) {
			list.add(c);
		}
		return list.toArray(new Double[0]);
	}

	/**
	 * 倍精度浮動小数配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
	 * 尚、オブジェクト配列内にnullが存在する場合は0が要素として設定されます。<br>
	 * @param doubles 倍精度浮動小数オブジェクト配列
	 * @return 倍精度浮動小数配列
	 */
	public static double[] cast(Double[] doubles) {
		if (doubles == null) {
			return null;
		}
		double[] array = new double[doubles.length];
		for (Loop<Double> loop : Loop.each(doubles)) {
			int i = loop.index();
			Double c = loop.value();
			if (c == null) {
				array[i] = 0;
			} else {
				array[i] = c;
			}
		}
		return array;
	}
}
