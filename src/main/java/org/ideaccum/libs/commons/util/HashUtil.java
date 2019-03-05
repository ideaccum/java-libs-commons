package org.ideaccum.libs.commons.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

/**
 * ハッシュコード関係の操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * {@link java.security.MessageDigest}インスタンスを生成してハッシュ化するコードを簡略化する際などに利用することを想定して設置されました。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2005/07/02	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		新規作成(最低保証バージョンをJava8として全面改訂(旧StringUtilから分割))
 *-->
 */
public final class HashUtil {

	/**
	 * コンストラクタ<br>
	 */
	private HashUtil() {
		super();
	}

	/**
	 * スタティックイニシャライザ<br>
	 */
	static {
		// JUnit+EclEmmaによるコードカバレッジの為のダミーコード
		new HashUtil();
	}

	/**
	 * バイトデータから指定アルゴリズムで生成されたハッシュコードを16進文字列で提供します。<br>
	 * @param bytes 対象バイトデータ
	 * @param algorithm アルゴリズム名
	 * @return 16進ハッシュコード文字列
	 */
	public static String toDigestHash(byte[] bytes, String algorithm) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Unsupported Algorithm (" + algorithm + ")", e);
		}
		digest.update(bytes == null ? new byte[0] : bytes);
		return StringUtil.toHex(digest.digest());
	}

	/**
	 * バイトデータからMD2で生成されたハッシュコードを16進文字列で提供します。<br>
	 * @param bytes 対象バイトデータ
	 * @return 16進ハッシュコード文字列
	 */
	public static String toMD2Hash(byte[] bytes) {
		return toDigestHash(bytes, "MD2");
	}

	/**
	 * バイトデータからMD5で生成されたハッシュコードを16進文字列で提供します。<br>
	 * @param bytes 対象バイトデータ
	 * @return 16進ハッシュコード文字列
	 */
	public static String toMD5Hash(byte[] bytes) {
		return toDigestHash(bytes, "MD5");
	}

	/**
	 * バイトデータからSHA-1で生成されたハッシュコードを16進文字列で提供します。<br>
	 * @param bytes 対象バイトデータ
	 * @return 16進ハッシュコード文字列
	 */
	public static String toSHA1Hash(byte[] bytes) {
		return toDigestHash(bytes, "SHA-1");
	}

	/**
	 * バイトデータからSHA-256で生成されたハッシュコードを16進文字列で提供します。<br>
	 * @param bytes 対象バイトデータ
	 * @return 16進ハッシュコード文字列
	 */
	public static String toSHA256Hash(byte[] bytes) {
		return toDigestHash(bytes, "SHA-256");
	}

	/**
	 * バイトデータからSHA-384で生成されたハッシュコードを16進文字列で提供します。<br>
	 * @param bytes 対象バイトデータ
	 * @return 16進ハッシュコード文字列
	 */
	public static String toSHA384Hash(byte[] bytes) {
		return toDigestHash(bytes, "SHA-384");
	}

	/**
	 * バイトデータからSHA-512で生成されたハッシュコードを16進文字列で提供します。<br>
	 * @param bytes 対象バイトデータ
	 * @return 16進ハッシュコード文字列
	 */
	public static String toSHA512Hash(byte[] bytes) {
		return toDigestHash(bytes, "SHA-512");
	}

	/**
	 * バイトデータをBASE64エンコード文字列に変換して提供します。<br>
	 * @param bytes 対象バイトデータ
	 * @return BASE64エンコード文字列
	 */
	public static String toBase64(byte[] bytes) {
		if (bytes == null) {
			return StringUtil.EMPTY;
		}
		return DatatypeConverter.printBase64Binary(bytes);
	}
}
