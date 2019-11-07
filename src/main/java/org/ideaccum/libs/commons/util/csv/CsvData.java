package org.ideaccum.libs.commons.util.csv;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.ideaccum.libs.commons.util.Loop;

/**
 * CSVレコード情報を一元的に管理するためのインタフェースを提供します。<br>
 * <p>
 * このクラスはCSVリソースが持つCSVレコード情報を一元的に管理するためのクラスです。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2007/02/16  Kitagawa         新規作成
 * 2018/05/02  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class CsvData implements List<CsvRecord> {

	/** レコードリスト */
	private List<CsvRecord> records;

	/**
	 * コンストラクタ<br>
	 */
	public CsvData() {
		super();
		this.records = new LinkedList<>();
	}

	/**
	 * クラス情報を文字列で取得します。<br>
	 * @return クラス情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return records.toString();
	}

	/**
	 * CSVレコード値としての文字列を提供します。<br>
	 * @return CSVレコード値としての文字列
	 */
	public String toCsvValue() {
		int maxColumnSize = getMaxColumnSize();
		StringBuilder builder = new StringBuilder();
		for (Loop<CsvRecord> loop : Loop.each(records)) {
			CsvRecord record = loop.value();
			builder.append(record.toCsvValue(maxColumnSize));
			builder.append(CsvColumn.LINEFEED);
		}
		return builder.toString();
	}

	/**
	 * クラスが保持しているCSVレコードで最大のカラム数を取得します。<br>
	 * @return クラスが保持しているCSVレコードで最大のカラム数
	 */
	public int getMaxColumnSize() {
		int size = 0;
		for (CsvRecord record : records) {
			if (record.size() > size) {
				size = record.size();
			}
		}
		return size;
	}

	/**
	 * 指定されたサイズまでCSVレコード情報保持サイズを拡張します。<br>
	 * @param size 拡張後のCSVレコード情報保持サイズ
	 */
	public void expand(int size) {
		while (records.size() <= size) {
			records.add(new CsvRecord());
		}
	}

	/**
	 * このクラスが保持しているCSVレコードの反復子を提供します。<br>
	 * @return CSVレコードの反復子
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<CsvRecord> iterator() {
		return records.iterator();
	}

	/**
	 * クラスが保持しているCSVレコード数を取得します。<br>
	 * @return クラスが保持しているCSVレコード数
	 * @see java.util.List#size()
	 */
	@Override
	public int size() {
		return records.size();
	}

	/**
	 * クラスが管理するCSVレコード情報が空であるか判定します。<br>
	 * @return クラスが管理するCSVレコード情報が空である場合にtrueを返却
	 * @see java.util.List#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return records.isEmpty();
	}

	/**
	 * 指定されたオブジェクトがCSVレコード内に含まれるか判定します。<br>
	 * 存在確認するオブジェクト型はあくまでも{@link org.ideaccum.libs.commons.util.csv.CsvRecord}となります。<br>
	 * レコード内のカラム値として設定されているオブジェクトが一致するカラム情報が存在した場合でもそれは判定対象にはなりません。<br>
	 * @return 指定されたオブジェクト(CSVレコード情報)がCSVレコード内に含まれる場合にtrueを返却
	 * @see java.util.List#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object record) {
		if (record == null) {
			return false;
		}
		return records.contains(record);
	}

	/**
	 * 保持されているCSVレコード情報を配列としてすべて提供します。<br>
	 * @return 保持されているCSVレコード情報配列
	 * @see java.util.List#toArray()
	 */
	@Override
	public CsvRecord[] toArray() {
		return records.toArray(new CsvRecord[0]);
	}

	/**
	 * 保持されているCSVレコード情報を配列としてすべて提供します。<br>
	 * @return 保持されているCSVレコード情報配列
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] array) {
		return records.toArray(array);
	}

	/**
	 * CSVレコード情報を末端に追加します。<br>
	 * CSVレコード情報にnullが指定された場合、{@link java.lang.NullPointerException}はスローせずに空のCSVレコード情報が内部的に追加されます。<br>
	 * @param record CSVレコード情報
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#add(java.lang.Object)
	 */
	@Override
	public boolean add(CsvRecord record) {
		if (record == null) {
			return records.add(new CsvRecord());
		}
		return records.add(record);
	}

	/**
	 * CSVレコード情報を削除します。<br>
	 * @param record CSVレコード情報
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object record) {
		if (records == null) {
			return false;
		}
		return records.remove(record);
	}

	/**
	 * CSVレコード情報コレクションがすべてここに含まれるか判定します。<br>
	 * @param collection 判定対象CSVレコード情報コレクション
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> collection) {
		if (collection == null) {
			return false;
		}
		return records.containsAll(collection);
	}

	/**
	 * CSVレコード情報コレクション内容をすべてここに追加します。<br>
	 * @param collection CSVレコード情報コレクション
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends CsvRecord> collection) {
		if (collection == null) {
			return false;
		}
		return records.addAll(collection);
	}

	/**
	 * CSVレコード情報コレクション内容をすべてここに追加します。<br>
	 * @param index 追加位置インデックス
	 * @param collection CSVレコード情報コレクション
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends CsvRecord> collection) {
		if (index < 0) {
			return false;
		}
		if (collection == null) {
			return false;
		}
		expand(index);
		return records.addAll(index, collection);
	}

	/**
	 * 指定されたCSVレコード情報コレクション要素に合致するCSVレコードをすべて削除します。<br>
	 * @param collection 削除対象CSVレコード情報コレクション
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> collection) {
		if (collection == null) {
			return false;
		}
		return records.removeAll(collection);
	}

	/**
	 * 指定されたCSVレコード情報コレクション要素に合致するCSVレコードのみを残してその他の要素をすべて削除します。<br>
	 * @param collection このインスタンス上に残すCSVレコード情報コレクション
	 * @return 処理の結果、リスト内容が変更された場合にtrueを返却します
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> collection) {
		if (collection == null) {
			return false;
		}
		return records.retainAll(collection);
	}

	/**
	 * クラスが保持しているCSVレコード情報をすべて削除します。<br>
	 * @see java.util.List#clear()
	 */
	@Override
	public void clear() {
		records.clear();
	}

	/**
	 * 指定されたレコード位置に存在するCSVレコード情報を取得します。<br>
	 * @param index レコード位置(0～)
	 * @return CSVレコード情報
	 * @see java.util.List#get(int)
	 */
	@Override
	public CsvRecord get(int index) {
		if (index < 0) {
			return null;
		}
		if (index > records.size() - 1) {
			return null;
		}
		return records.get(index);
	}

	/**
	 * 指定されたレコード位置にCSVレコード情報を設置します。<br>
	 * CSVレコード情報にnullが指定された場合、{@link java.lang.NullPointerException}はスローせずに空のCSVレコード情報が内部的に追加されます。<br>
	 * @param record CSVレコード情報
	 * @return 該当位置に元々設定されていたレコード情報が存在する場合そのレコード情報が返却されます
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public CsvRecord set(int index, CsvRecord record) {
		if (index < 0) {
			return null;
		}
		expand(index + 1);
		if (records == null) {
			return records.set(index, new CsvRecord());
		} else {
			return records.set(index, record);
		}
	}

	/**
	 * 指定されたレコード位置にCSVレコード情報を設置します。<br>
	 * CSVレコード情報にnullが指定された場合、{@link java.lang.NullPointerException}はスローせずに空のCSVレコード情報が内部的に追加されます。<br>
	 * @param record CSVレコード情報
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, CsvRecord record) {
		if (index < 0) {
			return;
		}
		expand(index);
		if (records == null) {
			records.add(index, new CsvRecord());
		} else {
			records.add(index, record);
		}
	}

	/**
	 * 指定されたレコード位置に存在するCSVレコード情報を削除します。<br>
	 * @param index 削除対象レコード位置
	 * @see java.util.List#remove(int)
	 */
	@Override
	public CsvRecord remove(int index) {
		if (index < 0) {
			return null;
		}
		if (index > records.size() - 1) {
			return null;
		}
		return records.remove(index);
	}

	/**
	 * 指定されたCSVレコード情報の自身のクラスで最初に出現する位置を提供します。<br>
	 * 自身のクラスで管理されているCSVレコード情報ではない場合は負数が返却されます。<br>
	 * @param record 判定対象CSVレコード情報
	 * @return クラスで管理されている位置
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf(Object record) {
		if (record == null) {
			return -1;
		}
		return records.indexOf(record);
	}

	/**
	 * 指定されたCSVレコード情報の自身のクラスで最後に出現する位置を提供します。<br>
	 * 自身のクラスで管理されているCSVレコード情報ではない場合は負数が返却されます。<br>
	 * @param record 判定対象CSVレコード情報
	 * @return クラスで管理されている位置
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf(Object record) {
		if (record == null) {
			return -1;
		}
		return records.lastIndexOf(record);
	}

	/**
	 * クラスが管理しているCSVレコード情報を反復するリストイテレータ(リスト内の指定された位置で始まる)を提供します。<br>
	 * @return CSVレコード情報を反復するリストイテレータ
	 * @see java.util.List#listIterator()
	 */
	@Override
	public ListIterator<CsvRecord> listIterator() {
		return records.listIterator();
	}

	/**
	 * クラスが管理しているCSVレコード情報を反復するリストイテレータ(リスト内の指定された位置で始まる)を提供します。<br>
	 * @param index リストイテレータから(next呼出しによって)返される最初の要素のインデックス
	 * @return CSVレコード情報を反復するリストイテレータ
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<CsvRecord> listIterator(int index) {
		index = index > records.size() - 1 ? records.size() - 1 : index;
		return records.listIterator(index);
	}

	/**
	 * クラスが管理しているCSVレコード情報における指定された開始位置(これを含む)から終了位置(これを含まない)までの部分のビューを提供します。<br>
	 * 開始位置と終了位置が等しい場合は、空のリストが返されます。<br>
	 * @param fromIndex 開始位置
	 * @param toIndex 終了位置
	 * @return 指定された範囲のCSVレコード情報ビュー
	 * @see java.util.List#subList(int, int)
	 */
	@Override
	public CsvData subList(int fromIndex, int toIndex) {
		fromIndex = fromIndex > records.size() - 1 ? records.size() - 1 : fromIndex;
		toIndex = toIndex > records.size() - 1 ? records.size() - 1 : toIndex;
		List<CsvRecord> record = records.subList(fromIndex, toIndex);
		CsvData result = new CsvData();
		for (Iterator<CsvRecord> iterator = record.iterator(); iterator.hasNext();) {
			result.add(iterator.next());
		}
		return result;
	}
}
