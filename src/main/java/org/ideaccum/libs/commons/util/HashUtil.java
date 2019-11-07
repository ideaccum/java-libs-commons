package org.ideaccum.libs.commons.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

/**
 * ハッシュコード関係の操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * {@link java.security.MessageDigest}インスタンスを生成してハッシュ化するコードを簡略化する際などに利用することを想定して設置されました。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2005/07/02  Kitagawa         新規作成
 * 2018/05/02  Kitagawa         新規作成(SourceForge.jpからGitHubへの移行に併せて全面改訂(旧StringUtilから分割))
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

	/**
	 * ファイルのからハッシュコードを計算して16進文字列で提供します。<br>
	 * @param file 対象ファイル
	 * @param algorithm アルゴリズム名
	 * @return 16進ハッシュコード文字列
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static String calcDigestHash(File file, String algorithm) throws IOException {
		DigestInputStream stream = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			stream = new DigestInputStream(new BufferedInputStream(new FileInputStream(file)), md);

			while (stream.read() != -1)
				;

			return StringUtil.toHex(md.digest());
		} catch (IOException e) {
			throw e;
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Unsupported Algorithm (" + algorithm + ")", e);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * ファイルのからMD2ハッシュコードを計算して16進文字列で提供します。<br>
	 * @param file 対象ファイル
	 * @return 16進ハッシュコード文字列
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static String calcMD2Hash(File file) throws IOException {
		return calcDigestHash(file, "MD2");
	}

	/**
	 * ファイルのからMD5ハッシュコードを計算して16進文字列で提供します。<br>
	 * @param file 対象ファイル
	 * @return 16進ハッシュコード文字列
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static String calcMD5Hash(File file) throws IOException {
		return calcDigestHash(file, "MD5");
	}

	/**
	 * ファイルのからSHA-1ハッシュコードを計算して16進文字列で提供します。<br>
	 * @param file 対象ファイル
	 * @return 16進ハッシュコード文字列
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static String calcSHA1Hash(File file) throws IOException {
		return calcDigestHash(file, "SHA-1");
	}

	/**
	 * ファイルのからSHA-256ハッシュコードを計算して16進文字列で提供します。<br>
	 * @param file 対象ファイル
	 * @return 16進ハッシュコード文字列
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static String calcSHA256Hash(File file) throws IOException {
		return calcDigestHash(file, "SHA-256");
	}

	/**
	 * ファイルのからSHA-384ハッシュコードを計算して16進文字列で提供します。<br>
	 * @param file 対象ファイル
	 * @return 16進ハッシュコード文字列
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static String calcSHA384Hash(File file) throws IOException {
		return calcDigestHash(file, "SHA-384");
	}

	/**
	 * ファイルのからSHA-512ハッシュコードを計算して16進文字列で提供します。<br>
	 * @param file 対象ファイル
	 * @return 16進ハッシュコード文字列
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static String calcSHA512Hash(File file) throws IOException {
		return calcDigestHash(file, "SHA-512");
	}

	/**
	 * ファイルのからハッシュコードを計算してファイル同士のハッシュコードを比較します。<br>
	 * @param file1 対象ファイル
	 * @param file2 対象ファイル
	 * @param algorithm アルゴリズム名
	 * @return ファイル同士のハッシュコードが一致する場合にtrueを返却
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static boolean compareDigestHash(File file1, File file2, String algorithm) throws IOException {
		return calcDigestHash(file1, algorithm).equals(calcDigestHash(file2, algorithm));
	}

	/**
	 * ファイルのからMD2ハッシュコードを計算してファイル同士のハッシュコードを比較します。<br>
	 * @param file1 対象ファイル
	 * @param file2 対象ファイル
	 * @return ファイル同士のハッシュコードが一致する場合にtrueを返却
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static boolean compareMD2Hash(File file1, File file2) throws IOException {
		return calcMD2Hash(file1).equals(calcMD2Hash(file2));
	}

	/**
	 * ファイルのからMD5ハッシュコードを計算してファイル同士のハッシュコードを比較します。<br>
	 * @param file1 対象ファイル
	 * @param file2 対象ファイル
	 * @return ファイル同士のハッシュコードが一致する場合にtrueを返却
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static boolean compareMD5Hash(File file1, File file2) throws IOException {
		return calcMD5Hash(file1).equals(calcMD5Hash(file2));
	}

	/**
	 * ファイルのからSHA-1ハッシュコードを計算してファイル同士のハッシュコードを比較します。<br>
	 * @param file1 対象ファイル
	 * @param file2 対象ファイル
	 * @return ファイル同士のハッシュコードが一致する場合にtrueを返却
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static boolean compareSHA1Hash(File file1, File file2) throws IOException {
		return calcSHA1Hash(file1).equals(calcSHA1Hash(file2));
	}

	/**
	 * ファイルのからSHA-256ハッシュコードを計算してファイル同士のハッシュコードを比較します。<br>
	 * @param file1 対象ファイル
	 * @param file2 対象ファイル
	 * @return ファイル同士のハッシュコードが一致する場合にtrueを返却
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static boolean compareSHA256Hash(File file1, File file2) throws IOException {
		return calcSHA256Hash(file1).equals(calcSHA256Hash(file2));
	}

	/**
	 * ファイルのからSHA-384ハッシュコードを計算してファイル同士のハッシュコードを比較します。<br>
	 * @param file1 対象ファイル
	 * @param file2 対象ファイル
	 * @return ファイル同士のハッシュコードが一致する場合にtrueを返却
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static boolean compareSHA384Hash(File file1, File file2) throws IOException {
		return calcSHA384Hash(file1).equals(calcSHA384Hash(file2));
	}

	/**
	 * ファイルのからSHA-512ハッシュコードを計算してファイル同士のハッシュコードを比較します。<br>
	 * @param file1 対象ファイル
	 * @param file2 対象ファイル
	 * @return ファイル同士のハッシュコードが一致する場合にtrueを返却
	 * @throws IOException ファイルリソースに対する入出力処理でエラーが発生した場合にスローされます
	 */
	public static boolean compareSHA512Hash(File file1, File file2) throws IOException {
		return calcSHA512Hash(file1).equals(calcSHA512Hash(file2));
	}
}
