package org.ideaccum.libs.commons.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * 簡易的なZIPアーカイブリソースに対する操作インタフェースを提供します。<br>
 * <p>
 * このクラスにおけるZIPファイル操作では、2バイト文字を扱うためにApache Antが提供するorg.apache.tools.zipパッケージのクラスを利用しています。<br>
 * </p>
 *
 *<!--
 * 更新日      更新者           更新内容
 * 2007/01/16  Kitagawa         新規作成
 * 2007/05/24  Kitagawa         Zipファイル解凍インタフェースの追加
 * 2018/05/16  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 * 2019/04/28  Kitagawa         Zip操作ユーティリティを標準JavaAPI、Apache、Zip4jの3種類としていたが、Zip4jのみに限定
 *-->
 */
public final class ZipUtil {

	/** 入出力処理バイトサイズ */
	public static int BYTE_LENGTH = 1024;

	/** ディフォルトキャラクタセット */
	public static final String DEFAULT_ENTRY_CHARSET = "MS932";

	/**
	 * コンストラクタ<br>
	 */
	private ZipUtil() {
		super();
	}

	/**
	 * ファイルに対してZIPエントリ情報の内容に沿ったZIPアーカイブ情報を出力します。<br>
	 * @param file 出力先ZIPファイル
	 * @param entries ZIPエントリ情報
	 * @param password 圧縮パスワード
	 * @param charset ZIPエントリキャラクタセット
	 * @throws IOException 正常にZIPファイル作成が行えなかった場合に発生
	 */
	public static void create(File file, ZipEntries entries, String password, String charset) throws IOException {
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(file);
			create(stream, entries, password, charset);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * ファイルに対してZIPエントリ情報の内容に沿ったZIPアーカイブ情報を出力します。<br>
	 * @param file 出力先ZIPファイル
	 * @param entries ZIPエントリ情報
	 * @param charset ZIPエントリキャラクタセット
	 * @throws IOException 正常にZIPファイル作成が行えなかった場合に発生
	 */
	public static void create(File file, ZipEntries entries, String charset) throws IOException {
		create(file, entries, null, charset);
	}

	/**
	 * ファイルに対してZIPエントリ情報の内容に沿ったZIPアーカイブ情報を出力します。<br>
	 * @param file 出力先ZIPファイル
	 * @param entries ZIPエントリ情報
	 * @throws IOException 正常にZIPファイル作成が行えなかった場合に発生
	 */
	public static void create(File file, ZipEntries entries) throws IOException {
		create(file, entries, null, DEFAULT_ENTRY_CHARSET);
	}

	/**
	 * 出力ストリームに対してZIPエントリ情報の内容に沿ったZIPアーカイブ情報を出力します。<br>
	 * @param stream 出力ストリーム
	 * @param entries ZIPエントリ情報
	 * @param password 圧縮パスワード
	 * @param charset ZIPエントリキャラクタセット
	 * @throws IOException 正常にZIPファイル作成が行えなかった場合に発生
	 */
	@SuppressWarnings("resource")
	public static void create(OutputStream stream, ZipEntries entries, String password, String charset) throws IOException {
		//ZipOutputStream zos = null;
		//try {
		//	zos = new ZipOutputStream(stream);
		//	zos.setEncoding(charset);
		//	for (String name : entries.keySet()) {
		//		InputStream eis = entries.get(name);
		//		try {
		//			ZipEntry entry = new ZipEntry(name);
		//			zos.putNextEntry(entry);
		//			int count;
		//			byte[] bytes = new byte[BYTE_LENGTH];
		//			while ((count = eis.read(bytes, 0, BYTE_LENGTH)) != -1) {
		//				zos.write(bytes, 0, count);
		//			}
		//			zos.closeEntry();
		//		} finally {
		//			if (entries.needClose(name)) {
		//				if (eis != null) {
		//					eis.close();
		//				}
		//			}
		//		}
		//	}
		//} finally {
		//	//if (zos != null) {
		//	//	zos.close();
		//	//}
		//}
		ZipModel zmodel = new ZipModel();
		zmodel.setFileNameCharset(StringUtil.isEmpty(charset) ? DEFAULT_ENTRY_CHARSET : charset);
		ZipParameters baseParameters = new ZipParameters();
		baseParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		baseParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		if (!StringUtil.isEmpty(password)) {
			baseParameters.setEncryptFiles(true);
			baseParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			baseParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			baseParameters.setPassword(password);
		}
		ZipOutputStream zos = new ZipOutputStream(stream, zmodel);
		// 一時ディレクトリ作成(Zip4jでエントリ可能なパスはファイルのみの為、一時出力するディレクトリを作成)
		File tempDir = File.createTempFile(ZipUtil.class.getSimpleName(), "");
		tempDir.delete();
		tempDir.mkdirs();
		tempDir.deleteOnExit();
		try {
			for (String name : entries.keySet()) {
				InputStream eis = entries.get(name);
				try {
					// Zip4jエントリの為のファイルオブジェクトに一旦出力
					File tempFile = new File(FileUtil.connectPath(tempDir.getAbsolutePath(), name));
					FileOutputStream fos = new FileOutputStream(tempFile);
					StreamUtil.pipe(eis, fos);
					fos.flush();
					fos.close();
					zos.putNextEntry(tempFile, (ZipParameters) baseParameters.clone());
					int count;
					byte[] bytes = new byte[BYTE_LENGTH];
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(tempFile));
					while ((count = bis.read(bytes, 0, BYTE_LENGTH)) != -1) {
						zos.write(bytes, 0, count);
					}
					zos.closeEntry();
				} finally {
					if (entries.needClose(name)) {
						if (eis != null) {
							eis.close();
						}
					}
				}
			}
			zos.flush();
			zos.finish();
		} catch (Throwable e) {
			throw new IOException(e);
		}
	}

	/**
	 * 出力ストリームに対してZIPエントリ情報の内容に沿ったZIPアーカイブ情報を出力します。<br>
	 * @param stream 出力ストリーム
	 * @param entries ZIPエントリ情報
	 * @param charset ZIPエントリキャラクタセット
	 * @throws IOException 正常にZIPファイル作成が行えなかった場合に発生
	 */
	public static void create(OutputStream stream, ZipEntries entries, String charset) throws IOException {
		create(stream, entries, null, charset);
	}

	/**
	 * 出力ストリームに対してZIPエントリ情報の内容に沿ったZIPアーカイブ情報を出力します。<br>
	 * @param stream 出力ストリーム
	 * @param entries ZIPエントリ情報
	 * @throws IOException 正常にZIPファイル作成が行えなかった場合に発生
	 */
	public static void create(OutputStream stream, ZipEntries entries) throws IOException {
		create(stream, entries, null, DEFAULT_ENTRY_CHARSET);
	}

	/**
	 * 指定されたZIPファイルを解凍します。<br>
	 * @param file ZIPファイル
	 * @param directory 解凍先ディレクトリ
	 * @param password 解凍パスワード
	 * @throws IOException 正常にZIPファイルの解凍が行えなかった場合に発生
	 */
	public static void unzip(File file, File directory, String password) throws IOException {
		//String target = directory == null ? "" : directory.getAbsolutePath().endsWith(File.separator) ? directory.getAbsolutePath() : directory.getAbsolutePath() + File.separator;
		//ZipFile zipFile = null;
		//try {
		//	zipFile = new ZipFile(file);
		//	Enumeration<?> enumeration = zipFile.getEntries();
		//	while (enumeration.hasMoreElements()) {
		//		ZipEntry entry = (ZipEntry) enumeration.nextElement();
		//		if (entry.isDirectory()) {
		//			new File(target + entry.getName()).mkdirs();
		//		} else {
		//			File parent = new File(target + entry.getName()).getParentFile();
		//			parent.mkdirs();
		//			FileOutputStream stream = null;
		//			try {
		//				stream = new FileOutputStream(target + entry.getName());
		//				InputStream in = zipFile.getInputStream(entry);
		//				byte[] buffer = new byte[BYTE_LENGTH];
		//				int size = 0;
		//				while ((size = in.read(buffer)) != -1) {
		//					stream.write(buffer, 0, size);
		//				}
		//			} finally {
		//				if (stream != null) {
		//					stream.close();
		//				}
		//			}
		//		}
		//	}
		//} finally {
		//	if (zipFile != null) {
		//		zipFile.close();
		//	}
		//}
		try {
			ZipFile zipFile = new ZipFile(file);
			if (zipFile.isEncrypted() && !StringUtil.isEmpty(password)) {
				zipFile.setPassword(password);
			}
			zipFile.extractAll(directory.getAbsolutePath());
		} catch (Throwable e) {
			throw new IOException(e);
		}
	}

	/**
	 * 指定されたZIPファイルを解凍します。<br>
	 * @param file ZIPファイル
	 * @param directory 解凍先ディレクトリ
	 * @throws IOException 正常にZIPファイルの解凍が行えなかった場合に発生
	 */
	public static void unzip(File file, File directory) throws IOException {
		unzip(file, directory, null);
	}

	/**
	 * 指定されたZIPファイルを解凍します。<br>
	 * このメソッドで解凍されるパスはカレントディレクトリとなります。<br>
	 * @param file ZIPファイル	
	 * @throws IOException 正常にZIPファイルの解凍が行えなかった場合に発生
	 */
	public static void unzip(File file) throws IOException {
		unzip(file, null);
	}
}
