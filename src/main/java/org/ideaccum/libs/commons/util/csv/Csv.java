package org.ideaccum.libs.commons.util.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.ideaccum.libs.commons.util.Loop;
import org.ideaccum.libs.commons.util.StreamUtil;

/**
 * CSVリソースに対する入出力を行うためのインタフェースを提供します。<br>
 * <p>
 * このクラスではCSV固有のカラム値エスケープ処理を考慮した形での入出力処理を提供します。<br>
 * また、標準のキャラクタセットはWindows-31Jとなります(一般的なCSV編集はMicrosoft Officeによる編集が想定されるため)。<br>
 * </p>
 * <p>
 * CSVリソースの読み込みは指定され入力リソースを一括で読み込む動きとして実装が提供されています(改行を含むカラム値等を判定するために全量情報を一括読み込み)。<br>
 * そのため、巨大なCSVリソースを扱う場合、大量のヒープサイズが消費されることがあるため、注意してください。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2007/02/16	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public class Csv {

	/** ディフォルトキャラクタセット */
	public static final String DEFAULE_CHARSET = "Windows-31J";

	/** ディフォルト改行コード */
	public static final String DEFAULE_LINEFEED = new String(new byte[] { 0x0D, 0x0A });

	/**
	 * コンストラクタ<br>
	 */
	private Csv() {
		super();
	}

	/**
	 * CSV文字列ソースをもとにCSVデータ情報として読み込みます。<br>
	 * @param csvSource CSV文字列ソース
	 * @return CSVデータ情報
	 */
	public static CsvData load(String csvSource) {
		CsvData data = new CsvData();

		boolean quoting = false;
		CsvRecord record = new CsvRecord();
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i <= csvSource.length() - 1; i++) {
			String s1 = String.valueOf(csvSource.charAt(i));
			String s2 = i < csvSource.length() - 1 ? String.valueOf(csvSource.charAt(i + 1)) : "";
			if (!quoting) {
				/*
				 * クォートトークン解析中ではない場合
				 */
				if (CsvColumn.SEPARATOR.equals(s1)) {
					// 解析文字がカンマである場合はトークン解析を終了
					record.add(new CsvColumn(CsvColumn.decode(buffer.toString())));
					buffer = new StringBuilder();
				} else if (CsvColumn.LINEFEED.equals(s1)) {
					// 解析文字が改行である場合はレコード解析を終了
					record.add(new CsvColumn(CsvColumn.decode(buffer.toString())));
					data.add(record);
					buffer = new StringBuilder();
					record = new CsvRecord();
				} else if (CsvColumn.QUOTE.equals(s1)) {
					// 解析文字がクォートである場合はクォート解析フラグを立てる
					buffer.append(s1);
					quoting = true;
				} else {
					// 通常文字の場合はバッファに文字を追加
					buffer.append(s1);
				}
			} else {
				/*
				 * クォートトークン解析中での場合
				 */
				if (CsvColumn.SEPARATOR.equals(s1)) {
					// 解析文字がカンマだとしても文字として追加
					buffer.append(s1);
				} else if (CsvColumn.LINEFEED.equals(s1)) {
					// 解析文字が改行だとしても文字として追加
					buffer.append(s1);
				} else if (CsvColumn.QUOTE.equals(s1) && CsvColumn.QUOTE.equals(s2)) {
					// 解析文字においてクォートが連続で存在する場合は文字を追加して添字をインクリメント
					buffer.append(s1);
					buffer.append(s2);
					i++;
				} else if (CsvColumn.QUOTE.equals(s1) && !CsvColumn.QUOTE.equals(s2)) {
					// 解析文字においてクォートが単独で存在する場合は文字を追加してクォート解析フラグを解除
					buffer.append(s1);
					quoting = false;
				} else {
					// 通常文字の場合はバッファに文字を追加
					buffer.append(s1);
				}
			}
		}
		if (buffer.length() > 0) {
			record.add(new CsvColumn(CsvColumn.decode(buffer.toString())));
			buffer = new StringBuilder();
		}
		if (record.size() > 0) {
			data.add(record);
		}
		return data;
	}

	/**
	 * 入力ストリームからCSV形式の文字列を読み込みCSVレコード情報として提供します。<br>
	 * @param stream 入力ストリーム
	 * @param charset キャラクタセット
	 * @return CSVレコード情報
	 * @throws IOException InputStreamオブジェクトからの読み込み中に入出力例外がスローされた場合に発生
	 */
	public static CsvData load(InputStream stream, String charset) throws IOException {
		if (stream == null) {
			return new CsvData();
		}
		String csvSource = StreamUtil.readString(stream, charset == null ? DEFAULE_CHARSET : charset);
		return load(csvSource);
	}

	/**
	 * 入力ストリームからCSV形式の文字列を読み込みCSVレコード情報として提供します。<br>
	 * このメソッドによる入力処理時のキャラクタセットはWindows-31Jとなります。<br>
	 * @param stream 入力ストリーム
	 * @return CSVレコード情報
	 * @throws IOException InputStreamオブジェクトからの読み込み中に入出力例外がスローされた場合に発生
	 */
	public static CsvData load(InputStream stream) throws IOException {
		return load(stream, DEFAULE_CHARSET);
	}

	/**
	 * ファイルからCSV形式の文字列を読み込みCSVレコード情報として提供します。<br>
	 * @param file CSVファイル
	 * @param charset キャラクタセット
	 * @return CSVレコード情報
	 * @throws IOException Fileオブジェクトからの読み込み中に入出力例外がスローされた場合に発生
	 */
	public static CsvData load(File file, String charset) throws IOException {
		if (file == null) {
			return new CsvData();
		}
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
			String csvSource = StreamUtil.readString(stream, charset == null ? DEFAULE_CHARSET : charset);
			return load(csvSource);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * ファイルからCSV形式の文字列を読み込みCSVレコード情報として提供します。<br>
	 * このメソッドによる入力処理時のキャラクタセットはWindows-31Jとなります。<br>
	 * @param file CSVファイル
	 * @return CSVレコード情報
	 * @throws IOException Fileオブジェクトからの読み込み中に入出力例外がスローされた場合に発生
	 */
	public static CsvData load(File file) throws IOException {
		return load(file, DEFAULE_CHARSET);
	}

	/**
	 * CSVデータ情報をストリームに対して出力します。<br>
	 * @param csvData CSVデータ情報
	 * @param stream 出力ストリーム
	 * @param charset キャラクタセット
	 * @param linefeed 改行コード
	 * @throws UnsupportedEncodingException サポートされないキャラクタセットが指定された場合にスローされます
	 */
	public static void save(CsvData csvData, OutputStream stream, String charset, String linefeed) throws UnsupportedEncodingException {
		final int FLUSH_RECORD_COUNT = 100;
		if (csvData == null) {
			return;
		}
		charset = charset == null ? DEFAULE_CHARSET : charset;
		linefeed = linefeed == null ? DEFAULE_LINEFEED : linefeed;
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(stream, charset));
		int count = 0;
		int maxColumnSize = csvData.getMaxColumnSize();
		for (Loop<CsvRecord> loop : Loop.each(csvData)) {
			CsvRecord record = loop.value();
			writer.print(record.toCsvValue(maxColumnSize));
			writer.print(linefeed);
			if (count++ >= FLUSH_RECORD_COUNT) {
				writer.flush();
				count = 0;
			}
		}
		writer.flush();
	}

	/**
	 * CSVデータ情報をストリームに対して出力します。<br>
	 * このメソッドによる出力のキャラクタセットはWindows-31J、改行コードはCR+LFにて出力されます。<br>
	 * @param csvData CSVデータ情報
	 * @param stream 出力ストリーム
	 */
	public static void save(CsvData csvData, OutputStream stream) {
		try {
			save(csvData, stream, DEFAULE_CHARSET, DEFAULE_LINEFEED);
		} catch (UnsupportedEncodingException e) {
			// Ignore UnsupportedEncodingException on default charset
		}
	}

	/**
	 * CSVデータ情報をファイルに出力します。<br>
	 * @param csvData CSVデータ情報
	 * @param file 出力ファイル
	 * @param charset キャラクタセット
	 * @param linefeed 改行コード
	 * @param append ファイルに対して追加書き込みを行う場合にtrueを返却
	 * @throws IOException ファイルに対する入出力例外が発生した場合にスローされます
	 */
	public static void save(CsvData csvData, File file, String charset, String linefeed, boolean append) throws IOException {
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(file, append);
			save(csvData, file, charset, linefeed, append);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * CSVデータ情報をファイルに出力します。<br>
	 * このメソッドによる出力のキャラクタセットはWindows-31J、改行コードはCR+LFにて出力されます。<br>
	 * @param csvData CSVデータ情報
	 * @param file 出力ファイル
	 * @param append ファイルに対して追加書き込みを行う場合にtrueを返却
	 * @throws IOException ファイルに対する入出力例外が発生した場合にスローされます
	 */
	public static void save(CsvData csvData, File file, boolean append) throws IOException {
		save(csvData, file, DEFAULE_CHARSET, DEFAULE_LINEFEED, append);
	}

	/**
	 * CSVデータ情報をファイルに出力します。<br>
	 * このメソッドによる出力のキャラクタセットはWindows-31J、改行コードはCR+LFにて出力されます。<br>
	 * @param csvData CSVデータ情報
	 * @param file 出力ファイル
	 * @throws IOException ファイルに対する入出力例外が発生した場合にスローされます
	 */
	public static void save(CsvData csvData, File file) throws IOException {
		save(csvData, file, DEFAULE_CHARSET, DEFAULE_LINEFEED, false);
	}
}
