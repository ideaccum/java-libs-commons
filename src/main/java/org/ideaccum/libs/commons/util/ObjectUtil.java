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
	 * 16ビット整数配列のプリミティブ型－オブジェクト間のキャスト処理を行います。<br>
	 * @param chars 16ビット整数配列
	 * @return 16ビット整数オブジェクト配列
	 */
	public static Character[] cast(char[] chars) {
		if (chars == null) {
			return null;
		}
		List<Character> list = new LinkedList<>();
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
		List<Byte> list = new LinkedList<>();
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
		List<Short> list = new LinkedList<>();
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
		List<Integer> list = new LinkedList<>();
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
		List<Long> list = new LinkedList<>();
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
		List<Float> list = new LinkedList<>();
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
		List<Double> list = new LinkedList<>();
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
