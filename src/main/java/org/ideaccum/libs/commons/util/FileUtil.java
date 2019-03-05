package org.ideaccum.libs.commons.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * ファイルリソースに対する操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * システム開発時にファイルに対する各種操作処理実装上の判定や分岐処理実装を簡略化することを目的とした操作メソッドを提供します。<br>
 * </p>
 *
 * @author Kitagawa<br>
 *
 *<!--
 * 更新日		更新者			更新内容
 * 2005/05/24	Kitagawa		新規作成
 * 2018/05/24	Kitagawa		再構築(最低保証バージョンをJava8として全面改訂)
 *-->
 */
public final class FileUtil {

	/**
	 * コンストラクタ<br>
	 */
	private FileUtil() {
		super();
	}

	/**
	 * スタティックイニシャライザ<br>
	 */
	static {
		// JUnit+EclEmmaによるコードカバレッジの為のダミーコード
		new FileUtil();
	}

	/**
	 * 指定したパスのディレクトリを作成します。<br>
	 * {@link java.io.File}クラスが持つメソッドですが、mkdir(String)との一貫性のために設けられました。<br>
	 * @param dir 作成するディレクトリパス
	 */
	public static void mkdirs(File dir) {
		if (dir != null) {
			dir.mkdirs();
		}
	}

	/**
	 * 指定したパスのディレクトリを作成します。<br>
	 * @param dir 作成するディレクトリパス
	 */
	public static void mkdirs(String dir) {
		if (!StringUtil.isEmpty(dir)) {
			mkdirs(new File(dir));
		}
	}

	/**
	 * 指定されたファイルパスを削除します。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的に削除します。<br>
	 * @param path 削除対象ファイルパス
	 * @param filter 削除処理時のファイルフィルタオブジェクト
	 * @param mark VM終了時に削除するようにマークする場合にtrueを指定、即座に削除処理を呼び出す場合はfalseを指定
	 */
	public static void delete(File path, FileFilter filter, boolean mark) {
		if (path == null || !path.exists()) {
			return;
		}
		if (path.isFile()) {
			if (mark) {
				if (path.exists()) {
					path.deleteOnExit();
				}
			} else {
				if (path.exists() && !path.delete()) {
					path.deleteOnExit();
				}
			}
		} else {
			File[] list = null;
			if (filter != null) {
				list = path.listFiles(filter);
			} else {
				list = path.listFiles();
			}
			for (int i = 0; i < list.length; i++) {
				delete(list[i], filter, mark);
			}
			if (mark) {
				if (path.exists()) {
					path.deleteOnExit();
				}
			} else {
				if (path.exists() && !path.delete()) {
					path.deleteOnExit();
				}
			}
		}
	}

	/**
	 * 指定されたファイルパスを削除します。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的に削除します。<br>
	 * @param path 削除対象ファイルパス
	 * @param filter 削除処理時のファイルフィルタオブジェクト
	 */
	public static void delete(File path, FileFilter filter) {
		delete(path, filter, false);
	}

	/**
	 * 指定されたファイルパスを削除します。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的に削除します。<br>
	 * @param path 削除対象ファイルパス
	 * @param mark VM終了時に削除するようにマークする場合にtrueを指定、即座に削除処理を呼び出す場合はfalseを指定
	 */
	public static void delete(File path, boolean mark) {
		delete(path, null, mark);
	}

	/**
	 * 指定されたファイルパスを削除します。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的に削除します。<br>
	 * @param path 削除対象ファイルパス
	 */
	public static void delete(File path) {
		delete(path, null, false);
	}

	/**
	 * 指定されたファイルパスを削除します。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的に削除します。<br>
	 * @param path 削除対象ファイルパス
	 * @param mark VM終了時に削除するようにマークする場合にtrueを指定、即座に削除処理を呼び出す場合はfalseを指定
	 */
	public static void delete(String path, boolean mark) {
		if (!StringUtil.isEmpty(path)) {
			delete(new File(path), mark);
		}
	}

	/**
	 * 指定されたファイルパスを削除します。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的に削除します。<br>
	 * @param path 削除対象ファイルパス
	 */
	public static void delete(String path) {
		if (!StringUtil.isEmpty(path)) {
			delete(new File(path), false);
		}
	}

	/**
	 * 指定されたファイルパスを指定されたファイルパスにコピーします。<br>
	 * @param src コピー元ファイルパス
	 * @param dst コピー先ファイルパス
	 * @param filter コピー処理時のファイルフィルタオブジェクト
	 * @throws IOException コピー中に入出力例外が発生した場合にスローされます
	 */
	@SuppressWarnings("resource")
	public static void copy(File src, File dst, FileFilter filter) throws IOException {
		if (src == null) {
			return;
		}
		if (dst == null) {
			dst = new File("");
		}
		if (src.isDirectory()) {
			File[] files = filter != null ? src.listFiles(filter) : src.listFiles();
			for (int i = 0; i <= files.length - 1; i++) {
				File file = files[i];
				String fileRelative = file.getAbsolutePath().substring(src.getAbsolutePath().length());
				String destAbsolute = connectPath(dst.getAbsolutePath(), fileRelative);
				File destFile = new File(destAbsolute);
				if (file.isDirectory()) {
					destFile.mkdirs();
				}
				copy(file, destFile, filter);
			}
		} else {
			if (filter == null || filter.accept(src)) {
				FileChannel srcCh = null;
				FileChannel dstCh = null;
				try {
					srcCh = new FileInputStream(src).getChannel();
					dstCh = new FileOutputStream(dst).getChannel();
					dstCh.transferFrom(srcCh, 0, srcCh.size());
				} catch (IOException e) {
					throw e;
				} finally {
					if (src != null) {
						srcCh.close();
					}
					if (dst != null) {
						dstCh.close();
					}
					dst.setLastModified(src.lastModified());
				}
			}
		}
	}

	/**
	 * 指定されたファイルパスを指定されたファイルパスにコピーします。<br>
	 * @param src コピー元ファイルパス
	 * @param dst コピー先ファイルパス
	 * @throws IOException コピー中に入出力例外が発生した場合にスローされます
	 */
	public static void copy(File src, File dst) throws IOException {
		copy(src, dst, null);
	}

	/**
	 * 指定されたファイルパスを指定されたファイルパスにコピーします。<br>
	 * @param src コピー元ファイルオブジェクト
	 * @param dst コピー先ファイルオブジェクト
	 * @throws IOException コピー中に入出力例外が発生した場合にスローされます
	 */
	public static void copy(String src, String dst) throws IOException {
		if (StringUtil.isEmpty(src)) {
			return;
		}
		if (StringUtil.isEmpty(dst)) {
			dst = "";
		}
		copy(new File(src), new File(dst));
	}

	/**
	 * 指定された基底パス配下のディレクトリを取得します。<br>
	 * 提供されるディレクトリ配列には基底パスとして指定されたディレクトリ自体も含まれます。<br>
	 * @param base 基底パス
	 * @param filter 検索フィルタ
	 * @param hierarchical 階層検索フラグ
	 * @return 基底パス配下のディレクトリ
	 */
	public static File[] dirs(File base, final FileFilter filter, boolean hierarchical) {
		if (base == null) {
			throw new NullPointerException("base");
		}
		File[] dirs = base.listFiles(new FileFilter() {

			public boolean accept(File target) {
				if (filter == null) {
					return target.isDirectory();
				} else {
					return target.isDirectory() && filter.accept(target);
				}
			}
		});
		List<File> result = new LinkedList<File>();
		result.add(base);
		if (dirs != null) {
			result.addAll(Arrays.asList(dirs));
			if (hierarchical) {
				for (int i = 0; i <= dirs.length - 1; i++) {
					//list.addAll(Arrays.asList(getDirectories(dirs[i], hierarchical)));
					for (Iterator<File> iterator = Arrays.asList(dirs(dirs[i], filter, hierarchical)).iterator(); iterator.hasNext();) {
						File file = (File) iterator.next();
						if (!result.contains(file)) {
							result.add(file);
						}
					}
				}
			}
		}
		return (File[]) result.toArray(new File[0]);
	}

	/**
	 * 指定された基底パス配下のディレクトリを取得します。<br>
	 * 提供されるディレクトリ配列には基底パスとして指定されたディレクトリ自体も含まれます。<br>
	 * @param base 基底パス
	 * @param hierarchical 階層検索フラグ
	 * @return 基底パス配下のディレクトリ
	 */
	public static File[] dirs(File base, boolean hierarchical) {
		return dirs(base, null, hierarchical);
	}

	/**
	 * 指定された基底パス配下のファイルを取得します。<br>
	 * @param base 基底パス
	 * @param dirFilter 検索フィルタ(ディレクトリに対するフィルタ)
	 * @param fileFilter 検索フィルタ(ファイルに対するフィルタ)
	 * @param hierarchical 階層検索フラグ
	 * @return 基底パス配下のディレクトリ
	 */
	public static File[] files(File base, FileFilter dirFilter, FileFilter fileFilter, boolean hierarchical) {
		if (base == null) {
			throw new NullPointerException("base");
		}
		if (base.isFile()) {
			return new File[] { base };
		}
		File[] files = base.listFiles(fileFilter);
		if (files == null) {
			files = new File[0];
		}
		if (!hierarchical) {
			return files;
		}
		List<File> list = new LinkedList<File>();
		File[] dirs = dirs(base, dirFilter, true);
		if (dirs != null) {
			for (int i = 0; i <= dirs.length - 1; i++) {
				File[] childs = files(dirs[i], fileFilter, false);
				if (childs != null) {
					for (int j = 0; j <= childs.length - 1; j++) {
						File child = childs[j];
						if (child.isFile() && !list.contains(child)) {
							list.add(child);
						}
					}
				}
			}
		}
		return (File[]) list.toArray(new File[0]);
	}

	/**
	 * 指定された基底パス配下のファイルを取得します。<br>
	 * @param base 基底パス
	 * @param filter 検索フィルタ(ファイルに対するフィルタ)
	 * @param hierarchical 階層検索フラグ
	 * @return 基底パス配下のディレクトリ
	 */
	public static File[] files(File base, FileFilter filter, boolean hierarchical) {
		return files(base, null, filter, hierarchical);
	}

	/**
	 * 指定された基底パス配下のファイル名一覧を取得します。<br>
	 * @param base 基底パス
	 * @param filter 検索フィルタ
	 * @param hierarchical 階層検索フラグ
	 * @return 基底パス配下のディレクトリ
	 */
	public static String[] filenames(String base, FileFilter filter, boolean hierarchical) {
		List<String> list = new LinkedList<String>();
		File[] files = files(new File(base), filter, hierarchical);
		for (int i = 0; i <= files.length - 1; i++) {
			list.add(files[i].getAbsolutePath());
		}
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * 指定された基底パス配下のファイル名一覧を取得します。<br>
	 * @param base 基底パス
	 * @param hierarchical 階層検索フラグ
	 * @return 基底パス配下のディレクトリ
	 */
	public static String[] filenames(String base, boolean hierarchical) {
		return filenames(base, null, hierarchical);
	}

	/**
	 * 指定された基底パス配下のファイルを取得します。<br>
	 * @param base 基底パス
	 * @param hierarchical 階層検索フラグ
	 * @return 基底パス配下のディレクトリ
	 */
	public static File[] filenames(File base, boolean hierarchical) {
		return files(base, null, hierarchical);
	}

	/**
	 * 指定されたファイルがバイナリファイルであるか判定します。<br>
	 * このメソッドは\0を含む場合にバイナリファイルであると判断します。<br>
	 * @param file 判定対象ファイル
	 * @return バイナリファイルである場合にtrueを返却
	 */
	public static boolean isBinaryFile(File file) {
		if (file == null) {
			return false;
		}
		//FileInputStream is = null;
		BufferedInputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			byte[] b = new byte[1];
			while (is.read(b, 0, 1) > 0) {
				if (b[0] == 0) {
					return true;
				}
			}
			return false;
		} catch (IOException e) {
			return true;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
				is = null;
			}
		}
	}

	/**
	 * 指定されたディレクトリパスに対して対象のパス(ファイルパス、ディレクトリパス)を接続します。<br>
	 * 但し、接続対象パスがnullの場合は元のディレクトリパス、元のディレクトリパスがnullの場合はユーザーが指定した接続対象パスがそのまま返却されます。<br>
	 * また、接続対象パスが{@link java.io.File#separator}から始まるルートを示すパスである場合、接続元のパスは無視された接続対象のパスがそのまま返却されます。<br>
	 * 当メソッドはパス接続時のファイルセパレータの補完処理を都度考慮せずに作業を行うために設けられました。<br>
	 * また、ファイルセパレータに関しては当メソッドを介すことで、プラットフォームに依存したパスに変換されます。即ちUnix環境にてパス指定が\を使用している場合、提供されるパスセパレータは/となって返却されます。<br>
	 * <br>
	 * 動作例：<br>
	 * <pre>
	 * connectPath("foo", "bar") -&gt; "foo/bar"
	 * connectPath("foo/", "bar") -&gt; "foo/bar"
	 * connectPath("foo/", "/bar") -&gt; "foo/bar"
	 * connectPath("foo/", "bar/") -&gt; "foo/bar"
	 * </pre>
	 * @param path1 接続元ディレクトリ
	 * @param path2 接続対象パス
	 * @return 接続されたパス
	 */
	public static String connectPath(String path1, String path2) {
		if (path1 == null) {
			return new File(path2).getPath();
		}
		if (path2 == null) {
			return new File(path1).getPath();
		}

		File filepath1 = new File(path1);
		File filepath2 = new File(path2);
		String spath1 = filepath1.getPath();
		String spath2 = filepath2.getPath();

		if (path1.endsWith(File.separator) && spath2.startsWith(File.separator)) {
			spath2 = spath2.substring(1);
		}

		StringBuilder builder = new StringBuilder();
		builder.append(spath1);
		if (builder.length() > 0 && !builder.toString().endsWith(File.separator) && !spath2.startsWith(File.separator)) {
			builder.append(File.separator);
		}
		builder.append(spath2);

		return builder.toString();
	}

	/**
	 * 指定されたパスが定義されているパス配列を全て接続して提供します。<br>
	 * 動作仕様についてはconnectPath(String, String)に依存します。<br>
	 * @param paths パス配列
	 * @return 接続されたパス
	 */
	public static String connectPath(String... paths) {
		if (paths == null || paths.length == 0) {
			return "";
		}
		String render = "";
		for (int i = 0; i <= paths.length - 1; i++) {
			render = connectPath(render, paths[i]);
		}
		return render;
	}

	/**
	 * 指定されたファイルパスのディレクトリパスを取得します。<br>
	 * 自身がディレクトパスの場合はそのままのパス情報として返却されます。<br>
	 * @param path ファイルパス
	 * @return ディレクトリパスを持つファイルオブジェクト
	 */
	public static File getDir(File path) {
		if (path.isDirectory()) {
			return new File(path.getAbsolutePath());
		} else {
			String absolute = path.getAbsolutePath();
			absolute = absolute.substring(0, absolute.lastIndexOf(File.separator));
			return new File(absolute);
		}
	}

	/**
	 * 指定されたパスからファイルオブジェクトを取得します。<br>
	 * ここで指定できるパスはシステムパス又は、クラスリソースパスとなります。<br>
	 * @param path システムパス又は、クラスリソースパス
	 * @return ファイルオブジェクト
	 * @throws IOException 指定されたパスからファイルオブジェクト生成が行えなかった場合にスローされます
	 */
	public static File getFile(String path) throws IOException {
		try {
			//return new File(new File(ResourceUtil.getURL(path).toURI()).getAbsolutePath()); // <- after JDK1.5
			return new File(new File(new URI(ResourceUtil.getURL(path).toString())).getAbsolutePath());
		} catch (Throwable e) {
			if (e instanceof IOException) {
				throw (IOException) e;
			} else {
				throw new IOException("failed to create File object");
			}
		}
	}

	/**
	 * 指定されたパスオブジェクトから拡張子を除いたファイル名を取得します。<br>
	 * @param path 対象パスオブジェクト
	 * @return ディレクトリパスオブジェクト
	 */
	public static String getBasename(File path) {
		/*
		 * パスが未指定の場合は処理中止
		 */
		if (path == null) {
			return null;
		}

		/*
		 * 拡張子分割
		 */
		String name = path.getName();
		String base = name.indexOf(".") >= 0 ? name.substring(0, name.lastIndexOf(".")) : name;

		return base;
	}

	/**
	 * 指定されたパスオブジェクトから拡張子を除いたファイル名を取得します。<br>
	 * @param path 対象パス
	 * @return ディレクトリパス
	 */
	public static String getBasename(String path) {
		/*
		 * パスが未指定の場合は処理中止
		 */
		if (StringUtil.isBlank(path)) {
			path = "";
		}

		return getBasename(new File(path));
	}

	/**
	 * 指定されたパスオブジェクトから拡張子名を取得します。<br>
	 * @param path 対象パスオブジェクト
	 * @return ディレクトリパスオブジェクト
	 */
	public static String getExtension(File path) {
		/*
		 * パスが未指定の場合は処理中止
		 */
		if (path == null) {
			return null;
		}

		/*
		 * 拡張子抽出
		 */
		String name = path.getName();
		String ext = name.indexOf(".") >= 0 ? name.substring(name.lastIndexOf(".") + 1) : "";

		return ext;
	}

	/**
	 * 指定されたパスオブジェクトから拡張子名を取得します。<br>
	 * @param path 対象パス
	 * @return ディレクトリパス
	 */
	public static String getExtension(String path) {
		/*
		 * パスが未指定の場合は処理中止
		 */
		if (StringUtil.isBlank(path)) {
			path = "";
		}

		return getExtension(new File(path));
	}

	/**
	 * 指定されたファイルオブジェクトのURI文字列を取得します。<br>
	 * 当メソッドが提供するURI文字列はプロトコル文字列(file:)は含まれません。<br>
	 * @param file ファイルオブジェクト
	 * @return プロトコル文字列を除くURI文字列
	 */
	public static String getSimpleURI(File file) {
		if (file == null) {
			return "/";
		}
		return file.toURI().toString().replaceAll("file:", "");
	}

	/**
	 * ファイルのハッシュ値(MD5)を取得します。<br>
	 * @param file ファイルオブジェクト
	 * @return ファイルハッシュ値
	 * @throws IOException ファイルに対する入出力例外が発生した場合にスローされます
	 * @throws NoSuchAlgorithmException ハッシュアルゴリズムが利用できない場合にスローされます
	 */
	public static String getMD5Hash(File file) throws IOException, NoSuchAlgorithmException {
		DigestInputStream stream = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			stream = new DigestInputStream(new BufferedInputStream(new FileInputStream(file)), md);

			while (stream.read() != -1)
				;

			return StringUtil.toHex(md.digest());
		} catch (IOException e) {
			throw e;
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * ファイルハッシュ値(MD5)でファイルの比較を行います。<br>
	 * @param file1 ファイルオブジェクト
	 * @param file2 ファイルオブジェクト
	 * @return ファイルがハッシュ値レベルで一致する場合にtrueを返却
	 * @throws IOException ファイルに対する入出力例外が発生した場合にスローされます
	 * @throws NoSuchAlgorithmException ハッシュアルゴリズムが利用できない場合にスローされます
	 */
	public static boolean compareByMD5Hash(File file1, File file2) throws IOException, NoSuchAlgorithmException {
		return getMD5Hash(file1).equals(getMD5Hash(file2));
	}

	/**
	 * 指定されたファイル名に[x]を付加してファイル名のインクリメントを行います。<br>
	 * 指定されたファイル名が存在しない場合はそのまま返却します。<br>
	 * @param filename ファイル名
	 * @return インクリメント処理をしたファイル名
	 */
	public static String getAutoIncrementFilename(String filename) {
		File file = new File(filename);
		String incrementedFilename = filename;

		if (file.exists()) {
			if (file.isDirectory()) {
				int counter = 1;
				for (File check = new File(filename + " [" + counter + "]"); check.exists(); counter++)
					;
				incrementedFilename = filename + " [" + counter + "]";
			} else {
				//String basename = filename;
				String extension = "";

				if (filename.lastIndexOf('.') > 0) {
					//basename = filename.substring(0, filename.lastIndexOf('.'));
					extension = filename.substring(filename.lastIndexOf('.'));
				}

				int counter = 1;
				for (File check = new File(filename + " [" + counter + "]" + extension); check.exists(); counter++)
					;
				incrementedFilename = filename + " [" + counter + "]" + extension;
			}
		}

		return incrementedFilename;
	}

	/**
	 * 指定されたファイルが存在する場合、日付をポストフィクスとして付加し退避します。<br>
	 * @param filename 退避対象ファイル名
	 */
	public static void renameWithDatePostfix(String filename) {
		File file = new File(filename);

		if (file.exists()) {
			if (file.isDirectory()) {
				int counter = 1;
				String rename = filename + new SimpleDateFormat(".yyyyMMdd.hhmmss").format(new Date());
				for (File check = new File(rename); check.exists(); counter++) {
					rename = rename + " [" + counter + "]";
					check = new File(rename);
				}

				file.renameTo(new File(rename));
			} else {
				String basename = filename;
				String extension = "";

				if (filename.lastIndexOf('.') > 0) {
					basename = filename.substring(0, filename.lastIndexOf('.'));
					extension = filename.substring(filename.lastIndexOf('.'));
				}

				int counter = 1;
				String head = basename + new SimpleDateFormat(".yyyyMMdd.hhmmss").format(new Date());
				String rename = head;
				for (File check = new File(rename + extension); check.exists(); counter++) {
					rename = head + " [" + counter + "]";
					check = new File(rename + extension);
				}

				file.renameTo(new File(rename + extension));
			}
		}
	}

	/**
	 * 指定されたファイル名から不正な文字を除去して返却します。<br>
	 * @param filename 補正対象ファイル名
	 * @param replace 置換文字列
	 * @return 補正後ファイル名
	 */
	public static String replacaInvalidateChar(String filename, String replace) {
		String buffer = filename;
		buffer = buffer.replaceAll("[\"]", replace);
		//buffer = buffer.replaceAll("[=]", replace);
		buffer = buffer.replaceAll("[|]", replace);
		buffer = buffer.replaceAll("[\\\\]", replace);
		//buffer = buffer.replaceAll("[;]", replace);
		buffer = buffer.replaceAll("[:]", replace);
		//buffer = buffer.replaceAll("[+]", replace);
		buffer = buffer.replaceAll("[*]", replace);
		buffer = buffer.replaceAll("[<]", replace);
		buffer = buffer.replaceAll("[>]", replace);
		//buffer = buffer.replaceAll("[.]", replace);
		//buffer = buffer.replaceAll("[,]", replace);
		buffer = buffer.replaceAll("[/]", replace);
		buffer = buffer.replaceAll("[?]", replace);
		return buffer;
	}

	/**
	 * 指定されたファイル名から不正な文字を除去して返却します。<br>
	 * @param filename 補正対象ファイル名
	 * @return 補正後ファイル名
	 */
	public static String replacaInvalidateChar(String filename) {
		return replacaInvalidateChar(filename, "");
	}
}
