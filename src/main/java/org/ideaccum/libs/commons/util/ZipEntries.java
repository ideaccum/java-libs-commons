package org.ideaccum.libs.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * ZIPアーカイブリソース生成時のZIPエントリを一元管理するためのインタフェースを提供します。<br>
 * <p>
 * java.utilが提供するZIP操作クラスやApacheのZIP操作クラスによるZIPリソース生成を容易に操作する為に設けられたZIPエントリクラスです。<br>
 * ここで提供するエントリ操作インタフェースを利用してエントリを作成し、{@link ZipUtil}または、{@link ZipUtil}を利用してZIPリソースを生成します。<br>
 * </p>
 *
 * @author Kitagawa<br>
 *
 *<!--
 * 更新日		更新者			更新内容
 * 2007/01/16	Kitagawa		新規作成
 * 2007/01/16	Kitagawa		ストリームエントリー対応
 * 2018/05/16	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class ZipEntries {

	/** エントリ情報 */
	private Map<String, Object> entries;

	/**
	 * コンストラクタ<br>
	 */
	public ZipEntries() {
		super();
		this.entries = new LinkedHashMap<String, Object>();
	}

	/**
	 * 指定された名称でZIPエントリリソースを入力ストリームから追加します。<br>
	 * ZIP操作を行った場合にここで指定された入力ストリームはZIP操作側でクローズされないことに注意してください。<br>
	 * @param name エントリ名
	 * @param stream ZIPエントリとして追加するリソースの入力ストリーム
	 */
	public void add(String name, InputStream stream) {
		entries.put(name, stream);
	}

	/**
	 * 指定された名称でZIPエントリリソースをファイルから追加します。<br>
	 * @param name エントリファイル名
	 * @param file ZIPエントリとして追加するファイル
	 * @throws FileNotFoundException 指定されたファイルが存在しない場合にスローされます
	 */
	public void add(String name, File file) throws FileNotFoundException {
		entries.put(name, file);
	}

	/**
	 * 指定された名称でZIPエントリリソースをファイルから追加します。<br>
	 * @param name エントリファイル名
	 * @param filename ZIPエントリとして追加するファイル名
	 * @throws FileNotFoundException 指定されたファイルが存在しない場合にスローされます
	 */
	public void add(String name, String filename) throws FileNotFoundException {
		entries.put(name, filename);
	}

	/**
	 * 保持されているエントリのキーセットを取得します。<br>
	 * @return キーセット
	 */
	public Set<String> keySet() {
		return entries.keySet();
	}

	/**
	 * 指定されたエントリ名の入力ストリームオブジェクトを取得します。<br>
	 * @param name エントリ名
	 * @return 入力ストリーム
	 * @throws FileNotFoundException ファイルリソースとしてエントリ設定されている場合に該当のファイルが見つからなかった場合にスローされます
	 */
	public InputStream get(String name) throws FileNotFoundException {
		Object entry = entries.get(name);
		if (entry instanceof InputStream) {
			return (InputStream) entry;
		} else if (entry instanceof File) {
			return new FileInputStream((File) entry);
		} else if (entry instanceof String) {
			return new FileInputStream(new File((String) entry));
		} else {
			throw new IllegalArgumentException("Illegal entry type " + entry.getClass());
		}
	}

	/**
	 * 指定されたエントリ名称のエントリリソースストリームを外部から明示的にクローズする必要があるか判定します。<br>
	 * @param name エントリ名
	 * @return 外部から明示的にクローズする必要がある場合にtrueを返却
	 */
	boolean needClose(String name) {
		Object entry = entries.get(name);
		if (entry instanceof InputStream) {
			return false;
		} else if (entry instanceof File) {
			return true;
		} else if (entry instanceof String) {
			return true;
		} else {
			return false;
		}
	}
}
