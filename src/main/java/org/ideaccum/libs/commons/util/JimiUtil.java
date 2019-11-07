package org.ideaccum.libs.commons.util;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.sun.jimi.core.Jimi;

/**
 * Sun Java JIMIイメージライブラリを利用したイメージリソースを操作するインタフェースを提供します。<br>
 * <p>
 * このクラスではイメージ変換などの操作を行う際の利用頻度の高い処理をラップしたインタフェースを提供します。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2013/08/29  Kitagawa         新規作成
 * 2018/05/24  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class JimiUtil {

	/**
	 * コンストラクタ<br>
	 */
	private JimiUtil() {
		super();
	}

	/**
	 * 指定されたMIMEタイプがエンコード時にサポートされる形式であるか判定します。<br>
	 * @param mimeType MIMEタイプ文字列
	 * @return サポートされる形式である場合にtrueを返却
	 */
	public static boolean isSupportedEncodeMIME(String mimeType) {
		if (mimeType == null) {
			return false;
		}
		for (String type : Jimi.getEncoderTypes()) {
			if (mimeType.equalsIgnoreCase(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定されたMIMEタイプがデコード時にサポートされる形式であるか判定します。<br>
	 * @param mime MIMEタイプ文字列
	 * @return サポートされる形式である場合にtrueを返却
	 */
	public static boolean isSupportedDecodeMIME(String mime) {
		if (mime == null) {
			return false;
		}
		for (String type : Jimi.getDecoderTypes()) {
			if (mime.equalsIgnoreCase(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定されたイメージの処理を排他します。<br>
	 * @param image イメージオブジェクト
	 * @throws InterruptedException 割り込み例外が発生した場合にスローされます
	 */
	private static void lockImage(Image image) throws InterruptedException {
		if (image == null) {
			return;
		}
		MediaTracker tracker = new MediaTracker(new Component() {
			private static final long serialVersionUID = -3064333293496151103L;
		});
		int imageId = image.hashCode();
		tracker.addImage(image, imageId);
		tracker.waitForID(imageId);
	}

	/**
	 * 入力ストリームからイメージをロードします。<br>
	 * @param mimeType MIMEタイプ文字列
	 * @param stream 入力ストリーム
	 * @return イメージオブジェクト
	 */
	public static Image loadImage(String mimeType, InputStream stream) {
		try {
			Image image = Jimi.getImage(stream, mimeType, Jimi.VIRTUAL_MEMORY);
			lockImage(image);
			image.flush();
			return image;
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted on image loading", e);
		} catch (Throwable e) {
			throw new RuntimeException("Failed to load image", e);
		}
	}

	/**
	 * 指定されたURLからイメージをロードします。<br>
	 * @param mimeType MIMEタイプ文字列
	 * @param url イメージURL
	 * @return イメージオブジェクト
	 */
	public static Image loadImage(String mimeType, URL url) {
		try {
			Image image = Jimi.getImage(url, mimeType);
			lockImage(image);
			image.flush();
			return image;
		} catch (InterruptedException e) {
			throw new RuntimeException("interrupted on image loading", e);
		} catch (Throwable e) {
			throw new RuntimeException("failed to load image", e);
		}
	}

	/**
	 * ファイルからイメージをロードします。<br>
	 * @param mimeType MIMEタイプ文字列
	 * @param file イメージファイル
	 * @return イメージオブジェクト
	 * @throws IOException 処理中に入出力例外が発生した場合にスローされます
	 */
	public static Image loadImage(String mimeType, File file) throws IOException {
		FileInputStream stream = null;
		Image image = null;
		try {
			stream = new FileInputStream(file);
			image = loadImage(mimeType, stream);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
		return image;
	}

	/**
	 * 指定されたファイルパスからイメージをロードします。<br>
	 * @param mimeType MIMEタイプ文字列
	 * @param path イメージファイルパス
	 * @return イメージオブジェクト
	 * @throws IOException 処理中に入出力例外が発生した場合にスローされます
	 */
	public static Image loadImage(String mimeType, String path) throws IOException {
		return loadImage(mimeType, new File(path));
	}

	/**
	 * ストリームにイメージを出力します。
	 * @param mimeType MIMEタイプ文字列
	 * @param stream ストリームオブジェクト
	 * @param image イメージオブジェクト
	 */
	public static void writeImage(String mimeType, OutputStream stream, Image image) {
		try {
			lockImage(image);
			Jimi.putImage(mimeType, image, stream);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted on image writing", e);
		} catch (Throwable e) {
			throw new RuntimeException("Failed to write image", e);
		}
	}

	/**
	 * ファイルにイメージを出力します。
	 * @param mimeType MIMEタイプ文字列
	 * @param file 出力先ファイル
	 * @param image イメージオブジェクト
	 * @throws IOException 処理中に入出力例外が発生した場合にスローされます
	 */
	public static void writeImage(String mimeType, File file, Image image) throws IOException {
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(file);
			writeImage(mimeType, stream, image);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * 指定されたファイルパスにイメージを出力します。
	 * @param mimeType MIMEタイプ文字列
	 * @param path 出力先ファイルパス
	 * @param image イメージオブジェクト
	 * @throws IOException 処理中に入出力例外が発生した場合にスローされます
	 */
	public static void writeImage(String mimeType, String path, Image image) throws IOException {
		writeImage(mimeType, new File(path), image);
	}

	/**
	 * 指定されたストリーム間でイメージの変換を行います。
	 * @param inputMimeType 入力側MIMEタイプ文字列
	 * @param inputStream 入力ストリームオブジェクト
	 * @param outputMimiType 出力側MIMEタイプ文字列
	 * @param outputStream 出力ストリームオブジェクト
	 */
	public static void convertImage(String inputMimeType, InputStream inputStream, String outputMimiType, OutputStream outputStream) {
		Image image = JimiUtil.loadImage(inputMimeType, inputStream);
		JimiUtil.writeImage(outputMimiType, outputStream, image);
	}

	/**
	 * 指定されたファイル間でイメージの変換を行います。
	 * @param inputMimeType 入力側MIMEタイプ文字列
	 * @param inputFile 入力ファイル
	 * @param outputMimiType 出力側MIMEタイプ文字列
	 * @param outputFile 出力ファイル
	 * @throws IOException 処理中に入出力例外が発生した場合にスローされます
	 */
	public static void convertImage(String inputMimeType, File inputFile, String outputMimiType, File outputFile) throws IOException {
		Image image = JimiUtil.loadImage(inputMimeType, inputFile);
		JimiUtil.writeImage(outputMimiType, outputFile, image);
	}

	/**
	 * 指定されたファイルパス間でイメージの変換を行います。
	 * @param inputMimeType 入力側MIMEタイプ文字列
	 * @param inputPath 入力ファイルパス
	 * @param outputMimiType 出力側MIMEタイプ文字列
	 * @param outputPath 出力ファイルパス
	 * @throws IOException 処理中に入出力例外が発生した場合にスローされます
	 */
	public static void convertImage(String inputMimeType, String inputPath, String outputMimiType, String outputPath) throws IOException {
		Image image = JimiUtil.loadImage(inputMimeType, inputPath);
		JimiUtil.writeImage(outputMimiType, outputPath, image);
	}
}
