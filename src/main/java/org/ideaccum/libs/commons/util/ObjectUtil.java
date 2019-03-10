package org.ideaccum.libs.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * オブジェクトに対する操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * システム開発時に利用するオブジェクト操作で利用頻度の高い操作を提供します。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2005/07/02	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		新規作成(SourceForge.jpからGitHubへの移行に併せて全面改訂)
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
	 * シリアライズ可能なオブジェクトを出力ストリームに対して出力します。<br>
	 * @param object シリアライズ可能なオブジェクト
	 * @param stream 出力ストリーム
	 * @throws IOException ストリーム出力時に入出力例外が発生した場合にスローされます
	 */
	public static void save(Serializable object, OutputStream stream) throws IOException {
		ObjectOutputStream os = new ObjectOutputStream(stream);
		os.writeObject(object);
		os.flush();
	}

	/**
	 * シリアライズ可能なオブジェクトをファイルに出力します。<br>
	 * @param object シリアライズ可能なオブジェクト
	 * @param file ファイルオブジェクト
	 * @throws IOException ファイル出力時に入出力例外が発生した場合にスローされます
	 */
	public static void save(Serializable object, File file) throws IOException {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(file);
			save(object, os);
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	/**
	 * シリアライズ可能なオブジェクトをファイルに出力します。<br>
	 * @param object シリアライズ可能なオブジェクト
	 * @param path ファイルパス
	 * @throws IOException ファイル出力時に入出力例外が発生した場合にスローされます
	 */
	public static void save(Serializable object, String path) throws IOException {
		save(object, ResourceUtil.getFile(path));
	}

	/**
	 * 入力ストリームからオブジェクトを読み込みます。<br>
	 * @param stream 入力ストリーム
	 * @param type 読み込む対象のオブジェクトクラス
	 * @param <T> 読み込む対象のオブジェクト型
	 * @return 読み込んだオブジェクト
	 * @throws IOException ストリーム読み込み時に入出力例外が発生した場合にスローされます
	 * @throws ClassNotFoundException 対象のオブジェクトのクラスが存在しない場合にスローされます
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T load(InputStream stream, Class<T> type) throws IOException, ClassNotFoundException {
		ObjectInputStream is = new ObjectInputStream(stream);
		Object object = is.readObject();
		return (T) object;
	}

	/**
	 * 入力ストリームからオブジェクトを読み込みます。<br>
	 * @param stream 入力ストリーム
	 * @return 読み込んだオブジェクト
	 * @throws IOException ストリーム読み込み時に入出力例外が発生した場合にスローされます
	 * @throws ClassNotFoundException 対象のオブジェクトのクラスが存在しない場合にスローされます
	 */
	public static Serializable load(InputStream stream) throws IOException, ClassNotFoundException {
		return load(stream, Serializable.class);
	}

	/**
	 * ファイルからオブジェクトを読み込みます。<br>
	 * @param file ファイルオブジェクト
	 * @param type 読み込む対象のオブジェクトクラス
	 * @param <T> 読み込む対象のオブジェクト型
	 * @return 読み込んだオブジェクト
	 * @throws IOException ファイル読み込み時に入出力例外が発生した場合にスローされます
	 * @throws ClassNotFoundException 対象のオブジェクトのクラスが存在しない場合にスローされます
	 */
	public static <T extends Serializable> T load(File file, Class<T> type) throws IOException, ClassNotFoundException {
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			T object = load(is, type);
			is.close();
			return object;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * ファイルからオブジェクトを読み込みます。<br>
	 * @param file ファイルオブジェクト
	 * @return 読み込んだオブジェクト
	 * @throws IOException ファイル読み込み時に入出力例外が発生した場合にスローされます
	 * @throws ClassNotFoundException 対象のオブジェクトのクラスが存在しない場合にスローされます
	 */
	public static Serializable load(File file) throws IOException, ClassNotFoundException {
		return load(file, Serializable.class);
	}

	/**
	 * ファイルからオブジェクトを読み込みます。<br>
	 * @param path ファイルパス
	 * @param type 読み込む対象のオブジェクトクラス
	 * @param <T> 読み込む対象のオブジェクト型
	 * @return 読み込んだオブジェクト
	 * @throws IOException ファイル読み込み時に入出力例外が発生した場合にスローされます
	 * @throws ClassNotFoundException 対象のオブジェクトのクラスが存在しない場合にスローされます
	 */
	public static <T extends Serializable> T load(String path, Class<T> type) throws IOException, ClassNotFoundException {
		return load(ResourceUtil.getFile(path), type);
	}

	/**
	 * ファイルからオブジェクトを読み込みます。<br>
	 * @param path ファイルパス
	 * @return 読み込んだオブジェクト
	 * @throws IOException ファイル読み込み時に入出力例外が発生した場合にスローされます
	 * @throws ClassNotFoundException 対象のオブジェクトのクラスが存在しない場合にスローされます
	 */
	public static Serializable load(String path) throws IOException, ClassNotFoundException {
		return load(ResourceUtil.getFile(path), Serializable.class);
	}

	/**
	 * 真偽値のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param values 真偽値配列
	 * @return 真偽値配列
	 */
	public static Boolean[] cast(boolean[] values) {
		if (values == null) {
			return null;
		}
		List<Boolean> list = new LinkedList<>();
		for (boolean value : values) {
			list.add(value);
		}
		return list.toArray(new Boolean[0]);
	}

	/**
	 * 真偽値のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
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
	 * 文字配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param values 文字配列
	 * @return 文字配列
	 */
	public static Character[] cast(char[] values) {
		if (values == null) {
			return null;
		}
		List<Character> list = new LinkedList<>();
		for (char value : values) {
			list.add(value);
		}
		return list.toArray(new Character[0]);
	}

	/**
	 * 文字配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
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
	 * バイト配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param values バイト配列
	 * @return バイト配列
	 */
	public static Byte[] cast(byte[] values) {
		if (values == null) {
			return null;
		}
		List<Byte> list = new LinkedList<>();
		for (byte value : values) {
			list.add(value);
		}
		return list.toArray(new Byte[0]);
	}

	/**
	 * バイト配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
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
	 * Short配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param values Short配列
	 * @return Short配列
	 */
	public static Short[] cast(short[] values) {
		if (values == null) {
			return null;
		}
		List<Short> list = new LinkedList<>();
		for (short value : values) {
			list.add(value);
		}
		return list.toArray(new Short[0]);
	}

	/**
	 * Short配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
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
	 * Ingeter配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param values Ingeter配列
	 * @return Ingeter配列
	 */
	public static Integer[] cast(int[] values) {
		if (values == null) {
			return null;
		}
		List<Integer> list = new LinkedList<>();
		for (int value : values) {
			list.add(value);
		}
		return list.toArray(new Integer[0]);
	}

	/**
	 * Ingeter配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
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
	 * Long配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param values Long配列
	 * @return Long配列
	 */
	public static Long[] cast(long[] values) {
		if (values == null) {
			return null;
		}
		List<Long> list = new LinkedList<>();
		for (long value : values) {
			list.add(value);
		}
		return list.toArray(new Long[0]);
	}

	/**
	 * Long配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
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
	 * 単精度浮動小数配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param values 単精度浮動小数配列
	 * @return 単精度浮動小数オブジェクト配列
	 */
	public static Float[] cast(float[] values) {
		if (values == null) {
			return null;
		}
		List<Float> list = new LinkedList<>();
		for (float value : values) {
			list.add(value);
		}
		return list.toArray(new Float[0]);
	}

	/**
	 * 単精度浮動小数配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
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
	 * 倍精度浮動小数配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param values 倍精度浮動小数配列
	 * @return 倍精度浮動小数オブジェクト配列
	 */
	public static Double[] cast(double[] values) {
		if (values == null) {
			return null;
		}
		List<Double> list = new LinkedList<>();
		for (double value : values) {
			list.add(value);
		}
		return list.toArray(new Double[0]);
	}

	/**
	 * 倍精度浮動小数配列のオブジェクト－プリミティブ型間のキャスト処理を行います。<br>
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
}
