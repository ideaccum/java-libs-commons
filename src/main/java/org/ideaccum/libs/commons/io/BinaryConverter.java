package org.ideaccum.libs.commons.io;

import java.io.UnsupportedEncodingException;

/**
 * Javaプリミティブ型とバイナリバイトデータとの相互変換インタフェースを提供します。<br>
 * <p>
 * バイナリリソースを扱う際のバイトデータとバイナリデータの単純な相互変換を行うための操作インタフェースが提供されます。<br>
 * このクラスでは単純に単一の項目情報(文字列や数値といった項目扱う際の単位)の相互変換を行います。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2014/02/14	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class BinaryConverter {

	/** バイナリバイトデータ */
	private byte[] data;

	/**
	 * コンストラクタ<br>
	 */
	public BinaryConverter() {
		super();
		this.data = null;
	}

	/**
	 * バイトデータ配列を取得します。<br>
	 * @return バイトデータ配列
	 */
	public byte[] getBytes() {
		return data;
	}

	/**
	 * バイトデータ配列を設定します。<br>
	 * @param data バイトデータ配列
	 */
	public void setBytes(byte[] data) {
		this.data = data;
	}

	/**
	 * Short値を設定します。<br>
	 * @param value Short値
	 */
	public void setShort(Short value) {
		data = null;
		if (value == null) {
			return;
		}

		int size = Short.SIZE / Byte.SIZE;
		data = new byte[size];

		for (int i = 0; i <= size - 1; i++) {
			data[i] = Integer.valueOf(value >> (Byte.SIZE * (size - 1 - i))).byteValue();
		}
	}

	/**
	 * Character値を設定します。<br>
	 * @param value Character値
	 */
	public void setCharacter(Character value) {
		data = null;
		if (value == null) {
			return;
		}

		int size = Character.SIZE / Byte.SIZE;
		data = new byte[size];

		for (int i = 0; i <= size - 1; i++) {
			data[i] = Integer.valueOf(value >> (Byte.SIZE * (size - 1 - i))).byteValue();
		}
	}

	/**
	 * Integer値を設定します。<br>
	 * @param value Integer値
	 */
	public void setInteger(Integer value) {
		data = null;
		if (value == null) {
			return;
		}

		int size = Integer.SIZE / Byte.SIZE;
		data = new byte[size];

		for (int i = 0; i <= size - 1; i++) {
			data[i] = Integer.valueOf(value >> (Byte.SIZE * (size - 1 - i))).byteValue();
		}
	}

	/**
	 * Long値を設定します。<br>
	 * @param value Long値
	 */
	public void setLong(Long value) {
		data = null;
		if (value == null) {
			return;
		}

		int size = Long.SIZE / Byte.SIZE;
		data = new byte[size];

		for (int i = 0; i <= size - 1; i++) {
			data[i] = Long.valueOf(value >> (Byte.SIZE * (size - 1 - i))).byteValue();
		}
	}

	/**
	 * Float値を設定します。<br>
	 * @param value Float値
	 */
	public void setFloat(Float value) {
		data = null;
		if (value == null) {
			return;
		}

		setInteger(Float.floatToIntBits(value));
	}

	/**
	 * Double値を設定します。<br>
	 * @param value Double値
	 */
	public void setDouble(Double value) {
		data = null;
		if (value == null) {
			return;
		}

		setLong(Double.doubleToLongBits(value));
	}

	/**
	 * String値を設定します。<br>
	 * @param value String値
	 * @param charset キャラクタセット
	 */
	public void setString(String value, String charset) {
		data = null;
		if (value == null) {
			return;
		}

		byte[] b;
		try {
			if (charset != null) {
				b = value.getBytes(charset);
			} else {
				b = value.getBytes();
			}
		} catch (UnsupportedEncodingException e) {
			b = value.getBytes();
		}

		data = new byte[b.length];
		System.arraycopy(b, 0, data, 0, b.length);
	}

	/**
	 * String値を設定します。<br>
	 * @param value String値
	 */
	public void setString(String value) {
		setString(value, null);
	}

	/**
	 * Short値を取得します。<br>
	 * @return Short値
	 */
	public Short getShort() {
		if (data == null) {
			return null;
		}

		short result = 0;
		int size = Short.SIZE / Byte.SIZE;

		for (int i = 0; i <= size - 1; i++) {
			byte b = i <= data.length - 1 ? data[i] : 0x00;
			result |= Integer.valueOf(b & 0xff).shortValue() << (Byte.SIZE * (size - 1 - i));
		}

		return result;
	}

	/**
	 * Character値を取得します。<br>
	 * @return Character値
	 */
	public Character getCharacter() {
		if (data == null) {
			return null;
		}

		char result = 0;
		int size = Character.SIZE / Byte.SIZE;

		for (int i = 0; i <= size - 1; i++) {
			byte b = i <= data.length - 1 ? data[i] : 0x00;
			result |= Integer.valueOf(b & 0xff).shortValue() << (Byte.SIZE * (size - 1 - i));
		}

		return result;
	}

	/**
	 * Integer値を取得します。<br>
	 * @return Integer値
	 */
	public Integer getInteger() {
		if (data == null) {
			return null;
		}

		int result = 0;
		int size = Integer.SIZE / Byte.SIZE;

		for (int i = 0; i <= size - 1; i++) {
			byte b = i <= data.length - 1 ? data[i] : 0x00;
			result |= Integer.valueOf(b & 0xff).intValue() << (Byte.SIZE * (size - 1 - i));
		}

		return result;
	}

	/**
	 * Long値を取得します。<br>
	 * @return Long値
	 */
	public Long getLong() {
		if (data == null) {
			return null;
		}

		long result = 0;
		int size = Long.SIZE / Byte.SIZE;

		for (int i = 0; i <= size - 1; i++) {
			byte b = i <= data.length - 1 ? data[i] : 0x00;
			result |= Integer.valueOf(b & 0xff).longValue() << (Byte.SIZE * (size - 1 - i));
		}

		return result;
	}

	/**
	 * Float値を取得します。<br>
	 * @return Float値
	 */
	public Float getFloat() {
		if (data == null) {
			return null;
		}

		Integer value = getInteger();
		if (value == null) {
			return null;
		}

		return Float.intBitsToFloat(value);
	}

	/**
	 * Double値を取得します。<br>
	 * @return Double値
	 */
	public Double getDouble() {
		if (data == null) {
			return null;
		}

		Long value = getLong();
		if (value == null) {
			return null;
		}

		return Double.longBitsToDouble(value);
	}

	/**
	 * String値を取得します。<br>
	 * @param charset キャラクタセット
	 * @return String値
	 */
	public String getString(String charset) {
		if (data == null) {
			return null;
		}

		byte[] buffer = new byte[data.length];
		System.arraycopy(data, 0, buffer, 0, buffer.length);

		String result;
		try {
			if (charset != null) {
				result = new String(buffer, charset);
			} else {
				result = new String(buffer);
			}
		} catch (UnsupportedEncodingException e) {
			result = new String(buffer);
		}

		return result;
	}

	/**
	 * String値を取得します。<br>
	 * @return String値
	 */
	public String getString() {
		return getString(null);
	}
}
