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

/**
 * オブジェクトに対する操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * システム開発時に利用するオブジェクト操作で利用頻度の高い操作を提供します。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2005/07/02  Kitagawa         新規作成
 * 2018/05/02  Kitagawa         新規作成(SourceForge.jpからGitHubへの移行に併せて全面改訂)
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
}
