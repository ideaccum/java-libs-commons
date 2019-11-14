package org.ideaccum.libs.commons.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 外部ライブラリやクラスファイルディレクトリ等をもとにクラスローダーを生成するためのインタフェースを提供します。<br>
 * <p>
 * このクラスでは外部配置されたjarファイルやclassファイルディレクトリパスを指定して独自のクラスローダーを生成する機能を提供します。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2019/11/14  Kitagawa         新規作成
 *-->
 */
public class ClassLoaderFactory {

	/** リソースURLリスト */
	private List<URL> urls;

	/** 親クラスローダー */
	private ClassLoader parent;

	/** ロード優先順序 */
	private List<Pattern> priorityPatterns;

	/**
	 * コンストラクタ<br>
	 */
	public ClassLoaderFactory() {
		super();
		this.urls = new LinkedList<>();
		this.priorityPatterns = new LinkedList<>();
	}

	/**
	 * クラスローダーを生成します。<br>
	 * @return クラスローダーインスタンス
	 */
	public URLClassLoader create() {
		/*
		 * 優先順位ソート
		 */
		List<URL> buffer = new LinkedList<>();
		for (Pattern priorityPattern : priorityPatterns) {
			for (URL url : urls) {
				String name = url.getFile();
				if (priorityPattern.matcher(name).find()) {
					buffer.add(url);
				}
			}
		}
		for (URL url : urls) {
			if (!buffer.contains(url)) {
				buffer.add(url);
			}
		}

		/*
		 * クラスローダー生成
		 */
		URLClassLoader classLoader;
		if (parent == null) {
			classLoader = new URLClassLoader(buffer.toArray(new URL[0]));
		} else {
			classLoader = new URLClassLoader(buffer.toArray(new URL[0]), parent);
		}

		return classLoader;
	}

	/**
	 * 親クラスローダーを設定します。<br>
	 * @param parent 親クラスローダー
	 * @return 自身のクラスインスタンス
	 */
	public ClassLoaderFactory setParent(ClassLoader parent) {
		this.parent = parent;
		return this;
	}

	/**
	 * 親クラスローダーとしてシステムクラスローダーを設定します。<br>
	 * @return 自身のクラスインスタンス
	 */
	public ClassLoaderFactory setParentBySystemClassLoader() {
		setParent(ClassLoader.getSystemClassLoader());
		return this;
	}

	/**
	 * 親クラスローダーとしてカレントスレッドのクラスローダーを設定します。<br>
	 * @return 自身のクラスインスタンス
	 */
	public ClassLoaderFactory setParentByCurrentClassLoader() {
		setParent(Thread.currentThread().getContextClassLoader());
		return this;
	}

	/**
	 * クラスロード時の優先順序を追加します。<br>
	 * ここで追加された優先順序パターンに合致するライブラリ名やクラス名のリソースは優先的にロードされます。<br>
	 * @param pattern リソース名パターン
	 * @return 自身のクラスインスタンス
	 */
	public ClassLoaderFactory addPriority(Pattern pattern) {
		if (pattern == null) {
			return this;
		}
		priorityPatterns.add(pattern);
		return this;
	}

	/**
	 * 外部定義された内容をもとにクラスロード時の優先順序を追加します。<br>
	 * 外部定義リソースは単純なパターン定義行の羅列テキストファイルを想定しています。<br>
	 * 尚、行頭に"#"が記載されている行及び、空行は読み込み時に無視されます。<br>
	 * @param patternFile パターン定義ファイル
	 * @param charset ファイルキャラクタセット
	 * @return 自身のクラスインスタンス
	 * @throws IOException パターン定義ファイルの読み込みに失敗した場合にスローされます
	 */
	public ClassLoaderFactory addPriority(File patternFile, String charset) throws IOException {
		if (patternFile == null) {
			return this;
		}
		if (!patternFile.exists()) {
			return this;
		}
		if (!patternFile.isFile()) {
			return this;
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(patternFile), charset));
			for (String line; (line = reader.readLine()) != null;) {
				if (line.length() <= 0 || line.startsWith("#")) {
					continue;
				}
				priorityPatterns.add(Pattern.compile(line));
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return this;
	}

	/**
	 * 外部定義された内容をもとにクラスロード時の優先順序を追加します。<br>
	 * 外部定義リソースは単純なパターン定義行の羅列テキストファイルを想定しています。<br>
	 * 尚、行頭に"#"が記載されている行及び、空行は読み込み時に無視されます。<br>
	 * @param patternFile パターン定義ファイル
	 * @return 自身のクラスインスタンス
	 * @throws IOException パターン定義ファイルの読み込みに失敗した場合にスローされます
	 */
	public ClassLoaderFactory addPriority(File patternFile) throws IOException {
		return addPriority(patternFile, "utf-8");
	}

	/**
	 * ライブラリパスを追加します。<br>
	 * このメソッドでは指定されたパスがライブラリファイル又は、ライブラリが配置されるディレクトリとして判断します。<br>
	 * これは指定されたパスがクラスファイル格納ディレクトリではないと判断することを意味し、指定パスがディレクトリである場合、そのパス自身は追加されません。<br>
	 * @param path ライブラリパス
	 * @param hierarchical サブディレクトリを含めて対象とする場合にtrueを指定
	 * @return 自身のクラスインスタンス
	 * @throws MalformedURLException 指定パスからURLへの変換に失敗した場合にスローされます
	 */
	public ClassLoaderFactory addLibraries(File path, boolean hierarchical) throws MalformedURLException {
		if (path == null) {
			return this;
		}
		if (!path.exists()) {
			return this;
		}
		if (path.isFile()) {
			urls.add(path.toURI().toURL());
		} else {
			for (File child : path.listFiles()) {
				if (child.isFile()) {
					urls.add(child.toURI().toURL());
				}
			}
			if (hierarchical) {
				for (File child : path.listFiles()) {
					if (child.isFile()) {
						continue;
					}
					addLibraries(child, hierarchical);
				}
			}
		}
		return this;
	}

	/**
	 * ライブラリパスを追加します。<br>
	 * このメソッドでは指定されたパスがライブラリファイル又は、ライブラリが配置されるディレクトリとして判断します。<br>
	 * これは指定されたパスがクラスファイル格納ディレクトリではないと判断することを意味し、指定パスがディレクトリである場合、そのパス自身は追加されません。<br>
	 * @param path ライブラリパス
	 * @param hierarchical サブディレクトリを含めて対象とする場合にtrueを指定
	 * @return 自身のクラスインスタンス
	 * @throws MalformedURLException 指定パスからURLへの変換に失敗した場合にスローされます
	 */
	public ClassLoaderFactory addLibraries(String path, boolean hierarchical) throws MalformedURLException {
		if (path == null) {
			return this;
		}
		//return addLibraries(new File(path), hierarchical);
		return addLibraries(Paths.get(path), hierarchical);
	}

	/**
	 * ライブラリパスを追加します。<br>
	 * このメソッドでは指定されたパスがライブラリファイル又は、ライブラリが配置されるディレクトリとして判断します。<br>
	 * これは指定されたパスがクラスファイル格納ディレクトリではないと判断することを意味し、指定パスがディレクトリである場合、そのパス自身は追加されません。<br>
	 * @param path ライブラリパス
	 * @param hierarchical サブディレクトリを含めて対象とする場合にtrueを指定
	 * @return 自身のクラスインスタンス
	 * @throws MalformedURLException 指定パスからURLへの変換に失敗した場合にスローされます
	 */
	public ClassLoaderFactory addLibraries(Path path, boolean hierarchical) throws MalformedURLException {
		if (path == null) {
			return this;
		}
		return addLibraries(path.toFile(), hierarchical);
	}

	/**
	 * ライブラリパスを追加します。<br>
	 * このメソッドでは指定されたパスがライブラリファイル又は、ライブラリが配置されるディレクトリとして判断します。<br>
	 * これは指定されたパスがクラスファイル格納ディレクトリではないと判断することを意味し、指定パスがディレクトリである場合、そのパス自身は追加されません。<br>
	 * @param path ライブラリパス
	 * @return 自身のクラスインスタンス
	 * @throws MalformedURLException 指定パスからURLへの変換に失敗した場合にスローされます
	 */
	public ClassLoaderFactory addLibraries(File path) throws MalformedURLException {
		return addLibraries(path, false);
	}

	/**
	 * ライブラリパスを追加します。<br>
	 * このメソッドでは指定されたパスがライブラリファイル又は、ライブラリが配置されるディレクトリとして判断します。<br>
	 * これは指定されたパスがクラスファイル格納ディレクトリではないと判断することを意味し、指定パスがディレクトリである場合、そのパス自身は追加されません。<br>
	 * @param path ライブラリパス
	 * @return 自身のクラスインスタンス
	 * @throws MalformedURLException 指定パスからURLへの変換に失敗した場合にスローされます
	 */
	public ClassLoaderFactory addLibraries(String path) throws MalformedURLException {
		return addLibraries(path, false);
	}

	/**
	 * ライブラリパスを追加します。<br>
	 * このメソッドでは指定されたパスがライブラリファイル又は、ライブラリが配置されるディレクトリとして判断します。<br>
	 * これは指定されたパスがクラスファイル格納ディレクトリではないと判断することを意味し、指定パスがディレクトリである場合、そのパス自身は追加されません。<br>
	 * @param path ライブラリパス
	 * @return 自身のクラスインスタンス
	 * @throws MalformedURLException 指定パスからURLへの変換に失敗した場合にスローされます
	 */
	public ClassLoaderFactory addLibraries(Path path) throws MalformedURLException {
		return addLibraries(path, false);
	}

	/**
	 * クラスファイルディレクトリパスを追加します。<br>
	 * このメソッドでは指定されたパスがクラスファイル格納ディレクトリであるものとして判断します。<br>
	 * これは指定されたパスがライブラリ格納パスではないと判断するため、jarファイルが格納されていてもこれはロード対象とはなりません。<br>
	 * @param path クラスファイルディレクトリパス
	 * @return 自身のクラスインスタンス
	 * @throws MalformedURLException 指定パスからURLへの変換に失敗した場合にスローされます
	 */
	public ClassLoaderFactory addClassesDirectory(File path) throws MalformedURLException {
		if (path == null) {
			return this;
		}
		if (path.isDirectory()) {
			urls.add(path.toURI().toURL());
		}
		return this;
	}

	/**
	 * クラスファイルディレクトリパスを追加します。<br>
	 * このメソッドでは指定されたパスがクラスファイル格納ディレクトリであるものとして判断します。<br>
	 * これは指定されたパスがライブラリ格納パスではないと判断するため、jarファイルが格納されていてもこれはロード対象とはなりません。<br>
	 * @param path クラスファイルディレクトリパス
	 * @return 自身のクラスインスタンス
	 * @throws MalformedURLException 指定パスからURLへの変換に失敗した場合にスローされます
	 */
	public ClassLoaderFactory addClassesDirectory(String path) throws MalformedURLException {
		if (path == null) {
			return this;
		}
		//return addClassesDirectory(new File(path));
		return addClassesDirectory(Paths.get(path));
	}

	/**
	 * クラスファイルディレクトリパスを追加します。<br>
	 * このメソッドでは指定されたパスがクラスファイル格納ディレクトリであるものとして判断します。<br>
	 * これは指定されたパスがライブラリ格納パスではないと判断するため、jarファイルが格納されていてもこれはロード対象とはなりません。<br>
	 * @param path クラスファイルディレクトリパス
	 * @return 自身のクラスインスタンス
	 * @throws MalformedURLException 指定パスからURLへの変換に失敗した場合にスローされます
	 */
	public ClassLoaderFactory addClassesDirectory(Path path) throws MalformedURLException {
		if (path == null) {
			return this;
		}
		return addClassesDirectory(path.toFile());
	}
}
