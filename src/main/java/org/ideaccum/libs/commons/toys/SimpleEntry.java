package org.ideaccum.libs.commons.toys;

import java.io.Serializable;

import org.ideaccum.libs.commons.util.ObjectUtil;

/**
 * キーと値をペアで管理する汎用的なインタフェースを提供します。<br>
 * <p>
 * 単純なキー／値管理を行うための汎用エントリクラスです。<br>
 * </p>	
 * 
 * @author Hisanori<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2018/02/22	Kitagawa		新規作成
 * 2018/05/16	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class SimpleEntry<K, V> implements Serializable, Cloneable {

	/** キー */
	private K key;

	/** 値 */
	private V value;

	/**
	 * コンストラクタ<br>
	 * @param key キー
	 * @param value 値
	 */
	public SimpleEntry(K key, V value) {
		super();
		set(key, value);
	}

	/**
	 * コンストラクタ<br>
	 */
	public SimpleEntry() {
		this(null, null);
	}

	/**
	 * オブジェクト情報を文字列として提供します。<br>
	 * @return オブジェクト情報文字列
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + key + "=" + value + "}";
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
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 * オブジェクト等価比較を行います。<br>
	 * @return 等価の場合にtrueを返却
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
		SimpleEntry<?, ?> other = (SimpleEntry<?, ?>) object;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	/**
	 * オブジェクトをクローンします。<br>
	 * @return クローンされたオブジェクト
	 * @see java.lang.Object#clone()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public SimpleEntry<K, V> clone() throws CloneNotSupportedException {
		try {
			SimpleEntry<K, V> clone = new SimpleEntry<K, V>();
			if (key == null) {
				clone.setKey(null);
			} else {
				if (key instanceof Cloneable) {
					clone.setKey((K) ((Cloneable) key));
				} else if (key instanceof Serializable) {
					clone.setKey((K) (ObjectUtil.copy((Serializable) key)));
				} else {
					clone.setKey(key);
				}
			}
			if (value == null) {
				clone.setValue(null);
			} else {
				if (value instanceof Cloneable) {
					clone.setValue((V) ((Cloneable) value));
				} else if (value instanceof Serializable) {
					clone.setValue((V) (ObjectUtil.copy((Serializable) value)));
				} else {
					clone.setValue(value);
				}
			}
			return clone;
		} catch (Throwable e) {
			throw new CloneNotSupportedException(e.getMessage());
		}
	}

	/**
	 * キーと値をセットで設定します。<br>
	 * @param key キー
	 * @param value 値
	 */
	public void set(K key, V value) {
		setKey(key);
		setValue(value);
	}

	/**
	 * キーを取得します。<br>
	 * @return キー
	 */
	public K getKey() {
		return key;
	}

	/**
	 * キーを設定します。<br>
	 * @param key キー
	 */
	public void setKey(K key) {
		this.key = key;
	}

	/**
	 * 値を取得します。<br>
	 * @return 値
	 */
	public V getValue() {
		return value;
	}

	/**
	 * 値を設定します。<br>
	 * @param value 値
	 */
	public void setValue(V value) {
		this.value = value;
	}
}
