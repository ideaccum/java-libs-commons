package org.ideaccum.libs.commons.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 複数の値をセット値として扱うためのインタフェースを提供します。<br>
 * <p>
 * このクラスは任意の個数で構成されるセット値を単一オブジェクトとして簡易的に集約管理することを目的として設置されました。<br>
 * このクラスで管理される値を{@link java.lang.Object#equals(Object)}比較する際、保持される値要素のすべてが同一である場合に、それは同一として判断します。<br>
 * </p>
 *
 * @author Kitagawa<br>
 *
 *<!--
 * 更新日		更新者			更新内容
 * 2012/10/23	Kitagawa		新規作成
 * 2018/05/24	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂、旧PairedValue)
 *-->
 */
public class Valueset<T> {

	/** 値要素リスト */
	private List<T> values;

	/**
	 * コンストラクタ<br>
	 * @param values 値要素リスト
	 */
	@SafeVarargs
	public Valueset(T... values) {
		super();
		this.values = new ArrayList<T>();
		if (values != null) {
			for (T value : values) {
				this.values.add(value);
			}
		}
	}

	/**
	 * 保持されている値要素セットを提供します。<br>
	 * @return 保持されている値要素セット
	 */
	public final Set<T> values() {
		Set<T> set = new HashSet<>();
		for (T e : values) {
			set.add(e);
		}
		return set;
	}

	/**
	 * 指定インデックスに保持されている値要素を取得します。<br>
	 * @param index インデックス
	 * @return 値要素
	 */
	public final T get(int index) {
		return values.get(index);
	}

	/**
	 * 保持されている値要素の数を取得します。<br>
	 * @return 保持されている値要素の数
	 */
	public final int size() {
		return values.size();
	}

	/**
	 * ハッシュコードを取得します。<br>
	 * 設定されている全ての値要素のハッシュコードが利用されます。
	 * @return ハッシュコード
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		StringBuilder builder = new StringBuilder();
		for (T value : values) {
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			builder.append(value);
		}
		result = prime * result + builder.toString().hashCode();
		return result;
	}

	/**
	 * オブジェクトの等価比較を行います。<br>
	 * @return オブジェクトが等価である場合にtrueを返却
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		Valueset<?> other = (Valueset<?>) object;
		if (values == null) {
			if (other.values != null) {
				return false;
			}
		} else if (values.size() != other.values.size()) {
			return false;
		} else {
			for (int i = 0; i <= values.size() - 1; i++) {
				Object mvalue = values.get(i);
				Object ovalue = other.values.get(i);
				if (mvalue == null && ovalue != null) {
					return false;
				}
				if (mvalue != null && ovalue == null) {
					return false;
				}
				if (mvalue != null && !mvalue.equals(ovalue)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * クラス情報を文字列で取得します。<br>
	 * @return クラス情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return values.toString();
	}
}
