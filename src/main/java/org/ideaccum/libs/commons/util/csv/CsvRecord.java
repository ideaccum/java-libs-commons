package org.ideaccum.libs.commons.util.csv;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.ideaccum.libs.commons.util.Loop;

/**
 * CSVレコードを管理するためのインタフェースを提供します。<br>
 * <p>
 * このクラスはCSVリソースにおける1行分の情報を管理するためのクラスです。<br>
 * クラスは{@link java.lang.Iterable}インタフェースを実装しますが、ここで反復される要素はCSVレコード内のカラム値の反復となります。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2007/02/16  Kitagawa         新規作成
 * 2018/05/02  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class CsvRecord implements List<CsvColumn> {

	/** カラムリスト */
	private List<CsvColumn> columns;

	/**
	 * コンストラクタ<br>
	 */
	public CsvRecord() {
		super();
		this.columns = new LinkedList<>();
	}

	/**
	 * クラス情報を文字列で取得します。<br>
	 * @return クラス情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return columns.toString();
	}

	/**
	 * CSVレコード値としての文字列を提供します。<br>
	 * @return CSVレコード値としての文字列
	 */
	public String toCsvValue() {
		StringBuilder builder = new StringBuilder();
		for (Loop<CsvColumn> loop : Loop.each(columns)) {
			CsvColumn column = loop.value();
			builder.append(column.toCsvValue());
			if (loop.hasNext()) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

	/**
	 * 指定カラム数に固定したCSVレコード値としての文字列を提供します。<br>
	 * @param columnSize CSVレコード値として出力する際のカラム数
	 * @return CSVレコード値としての文字列
	 */
	public String toCsvValue(int columnSize) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= columnSize - 1; i++) {
			CsvColumn column = get(i);
			if (column != null) {
				builder.append(column.toCsvValue());
			}
			if (i <= columnSize - 2) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

	/**
	 * 指定されたサイズまでCSVカラム情報保持サイズを拡張します。<br>
	 * @param size 拡張後のCSVカラム情報保持サイズ
	 */
	public void expand(int size) {
		while (columns.size() <= size) {
			columns.add(new CsvColumn());
		}
	}

	/**
	 * このクラスが保持しているCSVカラムの反復子を提供します。<br>
	 * @return CSVカラムの反復子
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<CsvColumn> iterator() {
		return columns.iterator();
	}

	/**
	 * クラスが保持しているCSVカラム数を取得します。<br>
	 * @return クラスが保持しているCSVカラム数
	 * @see java.util.List#size()
	 */
	@Override
	public int size() {
		return columns.size();
	}

	/**
	 * クラスが管理するCSVカラム情報が空であるか判定します。<br>
	 * @return クラスが管理するCSVカラム情報が空である場合にtrueを返却
	 * @see java.util.List#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return columns.isEmpty();
	}

	/**
	 * 指定されたオブジェクトがCSVレコード内に含まれるか判定します。<br>
	 * 存在確認するオブジェクト型はあくまでも{@link org.ideaccum.libs.commons.util.csv.CsvColumn}となります。<br>
	 * カラム値として設定されているオブジェクトが一致するカラム情報が存在した場合でもそれは判定対象にはなりません。<br>
	 * @return 指定されたオブジェクト(CSVカラム情報)がCSVレコード内に含まれる場合にtrueを返却
	 * @see java.util.List#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object column) {
		if (column == null) {
			return false;
		}
		return columns.contains(column);
	}

	/**
	 * 保持されているCSVカラム情報を配列としてすべて提供します。<br>
	 * @return 保持されているCSVカラム情報配列
	 * @see java.util.List#toArray()
	 */
	@Override
	public CsvColumn[] toArray() {
		return columns.toArray(new CsvColumn[0]);
	}

	/**
	 * 保持されているCSVカラム情報を配列としてすべて提供します。<br>
	 * @return 保持されているCSVカラム情報配列
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] array) {
		return columns.toArray(array);
	}

	/**
	 * CSVカラム情報を末端に追加します。<br>
	 * CSVカラム情報にnullが指定された場合、{@link java.lang.NullPointerException}はスローせずに空のCSVカラム情報が内部的に追加されます。<br>
	 * @param column CSVカラム情報
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#add(java.lang.Object)
	 */
	@Override
	public boolean add(CsvColumn column) {
		if (column == null) {
			return columns.add(new CsvColumn());
		}
		return columns.add(column);
	}

	/**
	 * CSVカラム情報を削除します。<br>
	 * @param column CSVカラム情報
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object column) {
		if (columns == null) {
			return false;
		}
		return columns.remove(column);
	}

	/**
	 * CSVカラム情報コレクションがすべてここに含まれるか判定します。<br>
	 * @param collection 判定対象CSVカラム情報コレクション
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> collection) {
		if (collection == null) {
			return false;
		}
		return columns.containsAll(collection);
	}

	/**
	 * CSVカラム情報コレクション内容をすべてここに追加します。<br>
	 * @param collection CSVカラム情報コレクション
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends CsvColumn> collection) {
		if (collection == null) {
			return false;
		}
		return columns.addAll(collection);
	}

	/**
	 * CSVカラム情報コレクション内容をすべてここに追加します。<br>
	 * @param index 追加位置インデックス
	 * @param collection CSVカラム情報コレクション
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends CsvColumn> collection) {
		if (index < 0) {
			return false;
		}
		if (collection == null) {
			return false;
		}
		expand(index);
		return columns.addAll(index, collection);
	}

	/**
	 * 指定されたCSVカラム情報コレクション要素に合致するCSVカラムをすべて削除します。<br>
	 * @param collection 削除対象CSVカラム情報コレクション
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> collection) {
		if (collection == null) {
			return false;
		}
		return columns.removeAll(collection);
	}

	/**
	 * 指定されたCSVカラム情報コレクション要素に合致するCSVカラムのみを残してその他の要素をすべて削除します。<br>
	 * @param collection このインスタンス上に残すCSVカラム情報コレクション
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> collection) {
		if (collection == null) {
			return false;
		}
		return columns.retainAll(collection);
	}

	/**
	 * クラスが保持しているCSVカラム情報をすべて削除します。<br>
	 * @see java.util.List#clear()
	 */
	@Override
	public void clear() {
		columns.clear();
	}

	/**
	 * 指定されたカラム位置に存在するCSVカラム情報を取得します。<br>
	 * @param index カラム位置(0～)
	 * @return CSVカラム情報
	 * @see java.util.List#get(int)
	 */
	@Override
	public CsvColumn get(int index) {
		if (index < 0) {
			return null;
		}
		if (index > columns.size() - 1) {
			return null;
		}
		return columns.get(index);
	}

	/**
	 * 指定されたカラム位置にCSVカラム情報を設置します。<br>
	 * CSVカラム情報にnullが指定された場合、{@link java.lang.NullPointerException}はスローせずに空のCSVカラム情報が内部的に追加されます。<br>
	 * @param column CSVカラム情報
	 * @return 該当位置に元々設定されていたカラム情報が存在する場合そのカラム情報が返却されます
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public CsvColumn set(int index, CsvColumn column) {
		if (index < 0) {
			return null;
		}
		expand(index + 1);
		if (columns == null) {
			return columns.set(index, new CsvColumn());
		} else {
			return columns.set(index, column);
		}
	}

	/**
	 * 指定されたカラム位置にCSVカラム情報を設置します。<br>
	 * CSVカラム情報にnullが指定された場合、{@link java.lang.NullPointerException}はスローせずに空のCSVカラム情報が内部的に追加されます。<br>
	 * @param column CSVカラム情報
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, CsvColumn column) {
		if (index < 0) {
			return;
		}
		expand(index);
		if (columns == null) {
			columns.add(index, new CsvColumn());
		} else {
			columns.add(index, column);
		}
	}

	/**
	 * 指定されたカラム位置に存在するCSVカラム情報を削除します。<br>
	 * @param index 削除対象カラム位置
	 * @see java.util.List#remove(int)
	 */
	@Override
	public CsvColumn remove(int index) {
		if (index < 0) {
			return null;
		}
		if (index > columns.size() - 1) {
			return null;
		}
		return columns.remove(index);
	}

	/**
	 * 指定されたCSVカラム情報の自身のクラスで最初に出現する位置を提供します。<br>
	 * 自身のクラスで管理されているCSVカラム情報ではない場合は負数が返却されます。<br>
	 * @param column 判定対象CSVカラム情報
	 * @return クラスで管理されている位置
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf(Object column) {
		if (column == null) {
			return -1;
		}
		return columns.indexOf(column);
	}

	/**
	 * 指定されたCSVカラム情報の自身のクラスで最後に出現する位置を提供します。<br>
	 * 自身のクラスで管理されているCSVカラム情報ではない場合は負数が返却されます。<br>
	 * @param column 判定対象CSVカラム情報
	 * @return クラスで管理されている位置
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf(Object column) {
		if (column == null) {
			return -1;
		}
		return columns.lastIndexOf(column);
	}

	/**
	 * クラスが管理しているCSVカラム情報を反復するリストイテレータ(リスト内の指定された位置で始まる)を提供します。<br>
	 * @return CSVカラム情報を反復するリストイテレータ
	 * @see java.util.List#listIterator()
	 */
	@Override
	public ListIterator<CsvColumn> listIterator() {
		return columns.listIterator();
	}

	/**
	 * クラスが管理しているCSVカラム情報を反復するリストイテレータ(リスト内の指定された位置で始まる)を提供します。<br>
	 * @param index リストイテレータから(next呼出しによって)返される最初の要素のインデックス
	 * @return CSVカラム情報を反復するリストイテレータ
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<CsvColumn> listIterator(int index) {
		index = index > columns.size() - 1 ? columns.size() - 1 : index;
		return columns.listIterator(index);
	}

	/**
	 * クラスが管理しているCSVカラム情報における指定された開始位置(これを含む)から終了位置(これを含まない)までの部分のビューを提供します。<br>
	 * 開始位置と終了位置が等しい場合は、空のリストが返されます。<br>
	 * @param fromIndex 開始位置
	 * @param toIndex 終了位置
	 * @return 指定された範囲のCSVカラム情報ビュー
	 * @see java.util.List#subList(int, int)
	 */
	@Override
	public CsvRecord subList(int fromIndex, int toIndex) {
		fromIndex = fromIndex > columns.size() - 1 ? columns.size() - 1 : fromIndex;
		toIndex = toIndex > columns.size() - 1 ? columns.size() - 1 : toIndex;
		List<CsvColumn> record = columns.subList(fromIndex, toIndex);
		CsvRecord result = new CsvRecord();
		for (Iterator<CsvColumn> iterator = record.iterator(); iterator.hasNext();) {
			result.add(iterator.next());
		}
		return result;
	}
}
