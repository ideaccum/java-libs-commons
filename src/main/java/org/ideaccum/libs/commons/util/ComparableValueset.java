package org.ideaccum.libs.commons.util;

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
public class ComparableValueset<T extends Comparable<T>> extends Valueset<T> implements Comparable<ComparableValueset<T>> {

	/**
	 * コンストラクタ<br>
	 * @param values 値要素リスト
	 */
	@SafeVarargs
	public ComparableValueset(T... values) {
		super(values);
	}

	/**
	 * 指定された値セットオブジェクトと比較を行います。<br>
	 * 先頭値から順に比較を行い、等価でない場合にその結果が返却されます。<br>
	 * 尚、指定された値がnullの場合は自身が大きいと判断して結果が返却されます。<br>
	 * @param compare 比較対象オブジェクト
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public final int compareTo(ComparableValueset<T> compare) {
		if (compare == null) {
			return 1;
		}
		int result = 0;
		for (int i = 0; i <= size() - 1; i++) {
			if (compare.size() - 1 < i) {
				return 1;
			}
			T v1 = get(i);
			T v2 = compare.get(i);
			if (v1 == null && v2 == null) {
				result = 0;
			} else if (v1 != null && v2 == null) {
				result = 1;
			} else if (v1 == null && v2 != null) {
				result = -1;
			} else {
				result = v1.compareTo(v2);
			}
			if (result != 0) {
				return result;
			}
		}
		if (compare.size() > size()) {
			return -1;
		}
		return 0;
	}
}
