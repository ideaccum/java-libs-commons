package org.ideaccum.libs.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * {@link java.lang.Iterable}に対する反復処理実装におけるインデックス値等の拡張情報を操作可能とするラッパー反復子インタフェースを提供します。<br>
 * <p>
 * Java5以降のfor文記述方法におけるイテレート処理時のインデックス値をループ内で利用したい場合にこのクラスでラップします。<br>
 * コード記載例は以下の通りです。<br>
 * </p>
 * <code>
 * List&lt;String&gt; list = new LinkedList();
 * for (Loop&lt;String&gt; loop : Loop.each(list)) {
 *     int index = loop.index(); // 0～のループインデックスが提供されます 
 *     int count = loop.count(); // 1～のループ回数が提供されます
 *     String value = loop.value(); // 実際の要素が提供されます
 * }
 * </code>
 * <p>
 * これは通常の実装コードの場合以下のような処理実装と同等となります。<br>
 * </p>
 * <code>
 * List&lt;String&gt; list = new LinkedList();
 * int index = 0;
 * for (String value : list) {
 *     int count = index + 1;
 *     String value = loop.value();
 *     index++;
 * }
 * </code>
 * <b>
 * 当該クラスで提供する逆順反復は指定された反復オブジェクトを一旦末端までイテレートするため、対象要素を保持するリストの場合にはレスポンスに注意して下さい。<br>
 * </b>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2016/09/14  Kitagawa         新規作成
 * 2018/06/29  Kitagawa         新規作成(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class Loop<T> {

	/** 反復インデックス */
	private int index;

	/** 反復要素 */
	private T value;

	/** 次反復要素存在フラグ */
	private boolean hasNext;

	/**
	 * コンストラクタ<br>
	 * @param index 反復インデックス
	 * @param value 反復要素
	 * @param hasNext 次反復要素存在フラグ
	 */
	private Loop(int index, T value, boolean hasNext) {
		this.index = index;
		this.value = value;
		this.hasNext = hasNext;
	}

	/**
	 * 指定された反復オブジェクトをラップした反復子を提供します。<br>
	 * @param iterable 反復オブジェクト 
	 * @param offset オフセットインデックス
	 * @param reverse 逆順処理する場合にtrueを指定
	 * @return ラップされた反復オブジェクト
	 */
	private static <T> Iterable<Loop<T>> each(final Iterable<T> iterable, final int offset, final boolean reverse) {
		final Iterator<Loop<T>> iterator = new Iterator<Loop<T>>() {

			private int count = 0;

			private Iterator<T> iterator = iterable == null ? null : reverse ? reverse(iterable).iterator() : iterable.iterator();

			@Override
			public boolean hasNext() {
				if (iterator == null) {
					return false;
				}
				return iterator.hasNext();
			}

			@Override
			public Loop<T> next() {
				if (iterator == null) {
					return new Loop<T>(0, null, false);
				}
				Loop<T> loop = null;
				if (!reverse) {
					loop = new Loop<T>(count++, iterator.next(), iterator.hasNext());
				} else {
					loop = new Loop<T>(count--, iterator.next(), iterator.hasNext());
				}
				return loop;
			}

			@Override
			public void remove() {
				if (iterator == null) {
					return;
				}
				iterator.remove();
			}

			private Iterable<T> reverse(final Iterable<T> iterable) {
				if (iterable == null) {
					return null;
				}
				List<T> list = new LinkedList<>();
				for (T e : iterable) {
					list.add(0, e);
				}
				count = list.size() - 1;
				return list;
			}
		};
		for (int i = 0; i <= offset - 1; i++) {
			iterator.next();
		}
		return new Iterable<Loop<T>>() {
			@Override
			public Iterator<Loop<T>> iterator() {
				return iterator;
			}
		};
	}

	/**
	 * 指定された反復オブジェクトをラップした反復子を提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param iterable 反復オブジェクト 
	 * @param offset オフセットインデックス
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> each(final Iterable<T> iterable, int offset) {
		return each(iterable == null ? new ArrayList<>() : iterable, offset, false);
	}

	/**
	 * 指定された反復オブジェクトをラップした反復子を提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param iterable 反復オブジェクト 
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> each(final Iterable<T> iterable) {
		return each(iterable == null ? new ArrayList<>() : iterable, 0, false);
	}

	/**
	 * 指定された反復オブジェクトをラップした反復子を提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param enumeration 反復オブジェクト 
	 * @param offset オフセットインデックス
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> each(final Enumeration<T> enumeration, int offset) {
		return each(enumeration == null ? new ArrayList<>() : Collections.list(enumeration), offset, false);
	}

	/**
	 * 指定された反復オブジェクトをラップした反復子を提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param enumeration 反復オブジェクト 
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> each(final Enumeration<T> enumeration) {
		return each(enumeration == null ? new ArrayList<>() : Collections.list(enumeration), 0, false);
	}

	/**
	 * 指定されたオブジェクト配列をラップした反復子を提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param array オブジェクト配列 
	 * @param offset オフセットインデックス
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> each(final T[] array, int offset) {
		return each(array == null ? new ArrayList<>() : Arrays.asList(array), offset, false);
	}

	/**
	 * 指定されたオブジェクト配列をラップした反復子を提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param array オブジェクト配列 
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> each(final T[] array) {
		return each(array == null ? new ArrayList<>() : Arrays.asList(array), 0, false);
	}

	/**
	 * 指定された反復オブジェクトをラップした反復子を提供します。<br>
	 * 当メソッドで提供される反復子は元の反復順序と逆順の反復子として提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param iterable 反復オブジェクト 
	 * @param offset オフセットインデックス
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> reverse(final Iterable<T> iterable, int offset) {
		return each(iterable == null ? new ArrayList<>() : iterable, offset, true);
	}

	/**
	 * 指定された反復オブジェクトをラップした反復子を提供します。<br>
	 * 当メソッドで提供される反復子は元の反復順序と逆順の反復子として提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param iterable 反復オブジェクト 
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> reverse(final Iterable<T> iterable) {
		return each(iterable == null ? new ArrayList<>() : iterable, 0, true);
	}

	/**
	 * 指定された反復オブジェクトをラップした反復子を提供します。<br>
	 * 当メソッドで提供される反復子は元の反復順序と逆順の反復子として提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param enumeration 反復オブジェクト 
	 * @param offset オフセットインデックス
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> reverse(final Enumeration<T> enumeration, int offset) {
		return each(Collections.list(enumeration), offset, true);
	}

	/**
	 * 指定された反復オブジェクトをラップした反復子を提供します。<br>
	 * 当メソッドで提供される反復子は元の反復順序と逆順の反復子として提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param enumeration 反復オブジェクト 
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> reverse(final Enumeration<T> enumeration) {
		return each(Collections.list(enumeration), 0, true);
	}

	/**
	 * 指定されたオブジェクト配列をラップした反復子を提供します。<br>
	 * 当メソッドで提供される反復子は元の反復順序と逆順の反復子として提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param array オブジェクト配列 
	 * @param offset オフセットインデックス
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> reverse(final T[] array, int offset) {
		return each(array == null ? new ArrayList<>() : Arrays.asList(array), offset, true);
	}

	/**
	 * 指定されたオブジェクト配列をラップした反復子を提供します。<br>
	 * 当メソッドで提供される反復子は元の反復順序と逆順の反復子として提供します。<br>
	 * @param <T> 反復要素タイプ
	 * @param array オブジェクト配列 
	 * @return ラップされた反復オブジェクト
	 */
	public static <T> Iterable<Loop<T>> reverse(final T[] array) {
		return each(array == null ? new ArrayList<>() : Arrays.asList(array), 0, true);
	}

	/**
	 * 反復インデックスを取得します。<br>
	 * @return 反復インデックス
	 */
	public int index() {
		return index;
	}

	/**
	 * 反復カウンタ(インデックス+1)を取得します。<br>
	 * @return 反復カウンタ(インデックス+1)
	 */
	public int count() {
		return index + 1;
	}

	/**
	 * 反復要素を取得します。<br>
	 * @return 反復要素
	 */
	public T value() {
		return value;
	}

	/**
	 * 次反復要素が存在するか判定します。<br>
	 * @return 次反復要素が存在する場合にtrueを返却
	 */
	public boolean hasNext() {
		return hasNext;
	}
}
