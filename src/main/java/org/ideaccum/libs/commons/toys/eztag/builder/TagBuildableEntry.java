package org.ideaccum.libs.commons.toys.eztag.builder;

/**
 * タグ要素や属性要素のうち実際のソース構築を行う操作を持つエントリクラスの上位共通インタフェースを提供します。<br>
 * <p>
 * 要素情報管理だけではなく、実際に自身の情報をソース構築するタグ要素や属性要素クラスはこのクラスを継承したクラスとして設置され、インタフェースが標準化されます。<br>
 * </p>
 * 
 * @param <T> 自身のクラス型
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2018/07/13  Kitagawa         新規作成
 *-->
 */
public abstract class TagBuildableEntry<T extends TagBuildableEntry<T>> extends TagEntry<T> {

	/**
	 * コンストラクタ<br>
	 */
	protected TagBuildableEntry() {
		super();
	}

	/**
	 * 実際にソースとして出力される要素文字列を構築します。<br>
	 * @return 実際にソースとして出力される要素文字列
	 */
	public abstract String build();
}
