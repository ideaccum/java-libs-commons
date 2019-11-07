package org.ideaccum.libs.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ideaccum.libs.commons.toys.SimpleEntry;

/**
 * 同一プロパティキーでの複数プロパティ定義を許容するプロパティリソースを扱うインタフェースを提供します。<br>
 * <p>
 * このクラスでは通常のプロパティリソースとは異なる同一プロパティキーで定義されているプロパティを持つリソースを読み込んだ場合に該当プロパティキーで定義分の値が定義されているものとして管理します。<br>
 * 尚、このクラスが扱うプロパティリソースは{@link java.util.Properties}と同様にユニコードエンコードされたプロパティリソースを前提とします。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2014/07/08  Kitagawa         新規作成
 * 2018/05/16  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class MultipleProperties implements Map<String, String[]>, Cloneable, Serializable {

	/** プロパティ情報 */
	private Map<String, List<String>> properties;

	/**
	 * コンストラクタ<br>
	 * @param path プロパティリソースパス
	 * @throws IOException プロパティファイル読み込み時に入出力例外が発生した場合にスローされます
	 */
	public MultipleProperties(String path) throws IOException {
		super();
		this.properties = new HashMap<String, List<String>>();
		if (!StringUtil.isEmpty(path)) {
			load(path);
		}
	}

	/**
	 * コンストラクタ<br>
	 */
	public MultipleProperties() {
		super();
		this.properties = new HashMap<String, List<String>>();
	}

	/**
	 * オブジェクトハッシュコードを取得します。<br>
	 * @return オブジェクトハッシュコード
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	/**
	 * オブジェクトの等価比較を行います。<br>
	 * @param object 比較対象オブジェクト
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		MultipleProperties other = (MultipleProperties) object;
		if (properties == null) {
			if (other.properties != null) {
				return false;
			}
		} else if (!properties.equals(other.properties)) {
			return false;
		}
		return true;
	}

	/**
	 * このクラスインスタンをクローンします。<br>
	 * @return クローンされたインスタンス
	 * @see java.lang.Object#clone()
	 */
	@Override
	public MultipleProperties clone() throws CloneNotSupportedException {
		MultipleProperties instance = new MultipleProperties();
		for (String key : properties.keySet()) {
			List<String> values = properties.get(key);
			for (String value : values) {
				instance.addProperty(key, value);
			}
		}
		return instance;
	}

	/**
	 * プロパティリソースファイルを読み込みクラス情報を初期化します。<br>
	 * @param path プロパティリソースパス
	 * @throws IOException プロパティファイル読み込み時に入出力例外が発生した場合にスローされます
	 */
	public void load(String path) throws IOException {
		properties = new HashMap<String, List<String>>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(ResourceUtil.getInputStream(path)));
			boolean lineContinue = false;
			String line = null;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				// Ascii2Native処理
				line = StringUtil.ascii2native(line);

				// コメント行除外
				if (!lineContinue && line.startsWith("#")) {
					continue;
				}

				// 継続行判定
				if (line.endsWith("\\")) {
					lineContinue = true;
				} else {
					lineContinue = false;
				}

				// バッファ追加
				buffer.append(line);

				// バッファコミット
				if (!lineContinue) {
					String render = buffer.toString();
					String name = "";
					String value = "";
					if (render.indexOf("=") >= 0) {
						name = render.substring(0, render.indexOf("="));
						value = render.substring(render.indexOf("=") + 1);
					} else {
						name = render;
						value = "";
					}

					// プロパティ設定
					if (!properties.containsKey(name)) {
						properties.put(name, new LinkedList<String>());
					}
					properties.get(name).add(value);

					// バッファクリア
					buffer = new StringBuffer();
				}
			}
		} catch (Throwable e) {
			throw new IOException("Failed to load properties (" + path + ")", e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	/**
	 * プロパティ定義キーセットを取得します。<br>
	 * @return プロパティ定義キーセット
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<String> keySet() {
		return properties.keySet();
	}

	/**
	 * プロパティ定義されている値コレクションを取得します。<br>
	 * @return プロパティ定義されている値コレクション
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<String[]> values() {
		List<String[]> values = new LinkedList<String[]>();
		for (List<String> strings : properties.values()) {
			values.add(strings.toArray(new String[0]));
		}
		return values;
	}

	/**
	 * プロパティ定義のキー値マッピングの数を提供します。<br>
	 * 同一キーでプロパティ定義値が複数定義されている場合でもその要素は1プロパティとなります。<br>
	 * @return プロパティ定義のキー値マッピングの数
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return properties.size();
	}

	/**
	 * プロパティ定義のキー値マッピングが存在しないかを判定します。<br>
	 * @return 1つもプロパティ定義が存在しない場合にtrueを返却
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return properties.isEmpty();
	}

	/**
	 * 指定されたキーでプロパティ定義が存在するか判定します。<br>
	 * @param key プロパティキー
	 * @return 指定されたキーでプロパティ定義が存在する場合にtrueを返却
	 */
	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}

	/**
	 * 指定されたキーでプロパティ定義が存在するか判定します。<br>
	 * @param key プロパティキー
	 * @return 指定されたキーでプロパティ定義が存在する場合にtrueを返却
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		if (key == null || !(key instanceof String)) {
			return false;
		}
		return containsKey((String) key);
	}

	/**
	 * 指定されたプロパティ定義値がいずれかの定義キーで定義されている値に含まれるか判定します。<br>
	 * @param value 判定対象プロパティ値
	 * @return いずれかの定義キーで定義されている値に含まれる場合にtrueを返却
	 */
	public boolean containsValue(String value) {
		for (List<String> entry : properties.values()) {
			if (entry.contains(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定されたプロパティ定義値がいずれかの定義キーで定義されている値に含まれるか判定します。<br>
	 * @param value 判定対象プロパティ値
	 * @return いずれかの定義キーで定義されている値に含まれる場合にtrueを返却
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		if (value == null || !(value instanceof String)) {
			return false;
		}
		return containsValue((String) value);
	}

	/**
	 * 指定されたプロパティキーの定義値を取得します。<br>
	 * @param key プロパティキー
	 * @return 指定されたプロパティキーの定義値
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public String[] get(Object key) {
		if (!containsKey(key)) {
			return new String[0];
		}
		return properties.get(key).toArray(new String[0]);
	}

	/**
	 * 指定されたプロパティキーでプロパティ値を設定します。<br>
	 * @param key プロパティキー
	 * @param values プロパティ値
	 * @return 元々設定されていたプロパティ値
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public String[] put(String key, String[] values) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		String[] old = get(key);
		if (!containsKey(key)) {
			properties.put(key, new LinkedList<>());
		}
		List<String> list = properties.get(key);
		list.clear();
		if (values == null) {
			return old;
		}
		for (String value : values) {
			list.add(value);
		}
		return old;
	}

	/**
	 * 指定されたプロパティキーで保持されているプロパティ値を削除します。<br>
	 * @param key プロパティキー
	 * @return 元々設定されていたプロパティ値
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public String[] remove(Object key) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		String[] old = get(key);
		if (!containsKey(key)) {
			return old;
		}
		properties.remove(key);
		return old;
	}

	/**
	 * キーと文字列配列のマップ情報をもとに自身のクラスにそれらを設定します。<br>
	 * @param map マップ情報
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends String, ? extends String[]> map) {
		if (map == null) {
			return;
		}
		for (String key : map.keySet()) {
			String[] values = map.get(key);
			put(key, values);
		}
	}

	/**
	 * 保持されているプロパティキー情報をクリアします。<br>
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		properties.clear();
	}

	/**
	 * 保持されているプロパティのエントリセットを提供します。<br>
	 * @return 保持されているプロパティのエントリセット
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<Entry<String, String[]>> entrySet() {
		Set<Entry<String, String[]>> set = new HashSet<>();
		for (String key : properties.keySet()) {
			String[] values = properties.get(key).toArray(new String[0]);
			SimpleEntry<String, String[]> entry = new SimpleEntry<String, String[]>(key, values);
			set.add(entry);
		}
		return set;
	}

	/**
	 * 指定されたプロパティキーの定義値を取得します。<br>
	 * @param key プロパティキー
	 * @return 指定されたプロパティキーの定義値
	 */
	public String[] getProperty(String key) {
		return get(key);
	}

	/**
	 * 指定されたプロパティキーの定義値を設定します。<br>
	 * このメソッドはプロパティキーで保持されているプロパティを上書き設定します。<br>
	 * すでに存在するプロパティ値に対して値を追加する場合は{@link #addProperty}を利用してください。<br>
	 * @param key プロパティキー
	 * @param values プロパティ値
	 * @return 元々設定されていたプロパティ値
	 */
	public String[] setProperty(String key, String[] values) {
		String[] old = get(key);
		if (!containsKey(key)) {
			properties.put(key, new LinkedList<>());
		}
		List<String> list = properties.get(key);
		list.clear();
		if (values == null) {
			return old;
		}
		for (String value : values) {
			list.add(value);
		}
		return old;
	}

	/**
	 * 指定されたプロパティキーの定義値に値を追加します。<br>
	 * @param key プロパティキー
	 * @param value プロパティ値
	 */
	public void addProperty(String key, String value) {
		if (!containsKey(key)) {
			properties.put(key, new LinkedList<>());
		}
		List<String> list = properties.get(key);
		list.add(value);
	}
}
