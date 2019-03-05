package org.ideaccum.libs.commons.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

/**
 * リストに対する操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * リスト操作で利用頻度の高い文字列操作をメソッドとして提供します。<br>
 * </p>
 *
 * @author Kitagawa<br>
 *
 *<!--
 * 更新日		更新者			更新内容
 * 2018/05/02	Kitagawa		新規作成
 *-->
 */
public final class ListUtil {

	/**
	 * コンストラクタ<br>
	 */
	private ListUtil() {
		super();
	}

	/**
	 * スタティックイニシャライ	ザ<br>
	 */
	static {
		// JUnit+EclEmmaによるコードカバレッジの為のダミーコード
		new ListUtil();
	}

	/**
	 * 要素配列をもとに{@link java.util.LinkedList}オブジェクトを生成して提供します。<br>
	 * @param <T> リスト要素タイプ
	 * @param type リスト要素クラス
	 * @param values 要素配列
	 * @return {@link java.util.LinkedList}オブジェクト
	 */
	public static <T extends Object> LinkedList<T> linkedList(Class<T> type, @SuppressWarnings("unchecked") T... values) {
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
	public static <T extends Object> ArrayList<T> arrayList(Class<T> type, @SuppressWarnings("unchecked") T... values) {
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
	public static <T extends Object> Vector<T> vector(Class<T> type, @SuppressWarnings("unchecked") T... values) {
		Vector<T> list = new Vector<>();
		if (values == null) {
			return list;
		}
		for (T value : values) {
			list.add(value);
		}
		return list;
	}
}
