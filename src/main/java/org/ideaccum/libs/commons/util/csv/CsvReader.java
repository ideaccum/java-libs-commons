package org.ideaccum.libs.commons.util.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * CSVファイルをレコード単位で読み込むためのインタフェースを提供します。<br>
 * <p>
 * このクラスはクラス名にReaderの名称を持ちますが、{@link java.io.Reader}のインタフェースを継承したクラスではありません。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2019/08/30  Kitagawa         新規作成
 *-->
 */
public class CsvReader {

	/** 親リーダーオブジェクト */
	private BufferedReader reader;

	/**
	 * コンストラクタ<br>
	 */
	public CsvReader(Reader reader) {
		super();
		if (reader == null) {
			throw new NullPointerException();
		} else if (reader instanceof BufferedReader) {
			this.reader = (BufferedReader) reader;
		} else {
			this.reader = new BufferedReader(reader);
		}
	}

	/**
	 * ストリームを閉じて、それに関連するすべてのシステム・リソースを解放します。 <br>
	 * ストリームが閉じられたあとに{@link #readRecord()}を呼び出すと、{@link java.io.IOException}がスローされます。 <br>
	 * すでに閉じられているストリームを閉じても、何の影響もありません。<br>
	 * @throws IOException 入出力エラーが発生した場合にスローされます
	 */
	public void close() throws IOException {
		reader.close();
	}

	/**
	 * CSVレコードを読み込みます。<br>
	 * CSV形式のレコードを読み込む為、リーダーから見た読み込まれる行数は複数行になることがあります。<br>
	 * @return CSVレコード
	 * @throws IOException 入出力エラーが発生した場合にスローされます
	 */
	public CsvRecord readRecord() throws IOException {
		String line = reader.readLine();
		if (line == null) {
			return null;
		}

		boolean quoting = false;
		CsvRecord record = new CsvRecord();
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i <= line.length() - 1; i++) {
			String s1 = String.valueOf(line.charAt(i));
			String s2 = i < line.length() - 1 ? String.valueOf(line.charAt(i + 1)) : "";
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
					if (s2.length() > 0) {
						throw new RuntimeException("予期せぬ改行");
					}
					break;
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

			/*
			 * クォート状態で行終了の場合は次行を結合して処理再開
			 */
			if (s2.length() <= 0 && quoting) {
				String next = reader.readLine();
				if (next == null) {
					break;
				}
				line += CsvColumn.LINEFEED + next;
			}
		}

		/*
		 * CSV形式が例外の場合に読み込み処理バッファに残った文字列はトークンとして末端に追加
		 */
		if (buffer.length() > 0) {
			record.add(new CsvColumn(CsvColumn.decode(buffer.toString())));
			buffer = new StringBuilder();
		}

		return record;
	}
}
