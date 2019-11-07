package org.ideaccum.libs.commons.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * ファイルリソースに対する操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * システム開発時にファイルに対する各種操作処理実装上の判定や分岐処理実装を簡略化することを目的とした操作メソッドを提供します。<br>
 * </p>
 *
 *<!--
 * 更新日      更新者           更新内容
 * 2005/05/24  Kitagawa         新規作成
 * 2018/05/24  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
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
	 * 当クラスで指定されたパス文字列からファイルオブジェクトを生成する際の処理をここでラップします。<br>
	 * @param path ファイルパス
	 * @return ファイルパスから利用するファイルオブジェクト
	 */
	private static File file(String path) {
		return new File(path);
		//return ResourceUtil.getFile(path);
	}

	/**
	 * ディレクトリを作成します。<br>
	 * {@link java.io.File}クラスが持つメソッドですが、mkdir(String)との一貫性のために設けられました。<br>
	 * @param dir 作成するディレクトリパス
	 */
	public static void mkdirs(File dir) {
		if (dir != null) {
			dir.mkdirs();
		}
	}

	/**
	 * ディレクトリを作成します。<br>
	 * @param dir 作成するディレクトリパス
	 */
	public static void mkdirs(String dir) {
		if (StringUtil.isEmpty(dir)) {
			return;
		}
		mkdirs(file(dir));
	}

	/**
	 * ファイルを削除します。<br>
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
	 * ファイルを削除します。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的に削除します。<br>
	 * @param path 削除対象ファイルパス
	 * @param filter 削除処理時のファイルフィルタオブジェクト
	 */
	public static void delete(File path, FileFilter filter) {
		delete(path, filter, false);
	}

	/**
	 * ファイルを削除します。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的に削除します。<br>
	 * @param path 削除対象ファイルパス
	 * @param mark VM終了時に削除するようにマークする場合にtrueを指定、即座に削除処理を呼び出す場合はfalseを指定
	 */
	public static void delete(File path, boolean mark) {
		delete(path, null, mark);
	}

	/**
	 * ファイルを削除します。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的に削除します。<br>
	 * @param path 削除対象ファイルパス
	 */
	public static void delete(File path) {
		delete(path, null, false);
	}

	/**
	 * ファイルを削除します。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的に削除します。<br>
	 * @param path 削除対象ファイルパス
	 * @param mark VM終了時に削除するようにマークする場合にtrueを指定、即座に削除処理を呼び出す場合はfalseを指定
	 */
	public static void delete(String path, boolean mark) {
		if (!StringUtil.isEmpty(path)) {
			delete(file(path), mark);
		}
	}

	/**
	 * ファイルを削除します。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的に削除します。<br>
	 * @param path 削除対象ファイルパス
	 */
	public static void delete(String path) {
		if (!StringUtil.isEmpty(path)) {
			delete(file(path), false);
		}
	}

	/**
	 * ファイルを指定されたファイルに移動します。<br>
	 * @param src コピー元ファイルパス
	 * @param dst コピー先ファイルパス
	 * @throws IOException コピー中に入出力例外が発生した場合にスローされます
	 */
	public static void move(File src, File dst) throws IOException {
		if (src == null) {
			return;
		}
		if (dst == null) {
			dst = file("");
		}
		Files.move(src.toPath(), dst.toPath());
	}

	/**
	 * ファイルを指定されたファイルに移動します。<br>
	 * @param src コピー元ファイルパス
	 * @param dst コピー先ファイルパス
	 * @throws IOException コピー中に入出力例外が発生した場合にスローされます
	 */
	public static void move(String src, String dst) throws IOException {
		if (StringUtil.isEmpty(src)) {
			return;
		}
		if (StringUtil.isEmpty(dst)) {
			dst = "";
		}
		move(file(src), file(dst));
	}

	/**
	 * ファイルを指定されたファイルにコピーします。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的にコピーします。<br>
	 * @param src コピー元ファイルパス
	 * @param dst コピー先ファイルパス
	 * @throws IOException コピー中に入出力例外が発生した場合にスローされます
	 */
	public static void copy(File src, File dst) throws IOException {
		if (src == null) {
			return;
		}
		if (dst == null) {
			dst = file("");
		}
		if (src.isDirectory()) {
			if (!dst.exists()) {
				dst.mkdirs();
				dst.setLastModified(src.lastModified());
			}
			File[] files = src.listFiles();
			for (int i = 0; i <= files.length - 1; i++) {
				File file = files[i];
				String fileRelative = file.getAbsolutePath().substring(src.getAbsolutePath().length());
				String destAbsolute = connectPath(dst.getAbsolutePath(), fileRelative);
				File destFile = file(destAbsolute);
				if (file.isDirectory()) {
					destFile.mkdirs();
					destFile.setLastModified(file.lastModified());
				}
				copy(file, destFile);
			}
		} else {
			//FileChannel srcCh = null;
			//FileChannel dstCh = null;
			//try {
			//	srcCh = new FileInputStream(src).getChannel();
			//	dstCh = new FileOutputStream(dst).getChannel();
			//	dstCh.transferFrom(srcCh, 0, srcCh.size());
			//} catch (IOException e) {
			//	throw e;
			//} finally {
			//	if (src != null) {
			//		srcCh.close();
			//	}
			//	if (dst != null) {
			//		dstCh.close();
			//	}
			//	dst.setLastModified(src.lastModified());
			//}
			Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
		}
	}

	/**
	 * ファイルを指定されたファイルにコピーします。<br>
	 * ファイルパスがディレクトリの場合は、配下のリソースに対しても再帰的にコピーします。<br>
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
		copy(file(src), file(dst));
	}

	/**
	 * 基底パス配下のディレクトリを取得します。<br>
	 * 提供されるディレクトリ配列には基底パスとして指定されたディレクトリ自体も含まれます。<br>
	 * @param base 基底パス
	 * @param filter 検索フィルタ
	 * @param hierarchical サブディレクトリも含めて対象とする場合にtrueを指定
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
		List<File> result = new LinkedList<>();
		result.add(base);
		if (dirs != null) {
			result.addAll(Arrays.asList(dirs));
			if (hierarchical) {
				for (int i = 0; i <= dirs.length - 1; i++) {
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
	 * 基底パス配下のディレクトリを取得します。<br>
	 * 提供されるディレクトリ配列には基底パスとして指定されたディレクトリ自体も含まれます。<br>
	 * @param base 基底パス
	 * @param hierarchical サブディレクトリも含めて対象とする場合にtrueを指定
	 * @return 基底パス配下のディレクトリ
	 */
	public static File[] dirs(File base, boolean hierarchical) {
		return dirs(base, null, hierarchical);
	}

	/**
	 * 基底パス配下のファイルを取得します。<br>
	 * @param base 基底パス
	 * @param dirFilter 検索フィルタ(ディレクトリに対するフィルタ)
	 * @param fileFilter 検索フィルタ(ファイルに対するフィルタ)
	 * @param hierarchical サブディレクトリも含めて対象とする場合にtrueを指定
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
		List<File> list = new LinkedList<>();
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
	 * 基底パス配下のファイルを取得します。<br>
	 * @param base 基底パス
	 * @param filter 検索フィルタ(ファイルに対するフィルタ)
	 * @param hierarchical サブディレクトリも含めて対象とする場合にtrueを指定
	 * @return 基底パス配下のディレクトリ
	 */
	public static File[] files(File base, FileFilter filter, boolean hierarchical) {
		return files(base, null, filter, hierarchical);
	}

	/**
	 * 基底パス配下のファイル名一覧を取得します。<br>
	 * @param base 基底パス
	 * @param filter 検索フィルタ
	 * @param hierarchical サブディレクトリも含めて対象とする場合にtrueを指定
	 * @return 基底パス配下のディレクトリ
	 */
	public static String[] filenames(String base, FileFilter filter, boolean hierarchical) {
		List<String> list = new LinkedList<>();
		File[] files = files(file(base), filter, hierarchical);
		for (int i = 0; i <= files.length - 1; i++) {
			list.add(files[i].getAbsolutePath());
		}
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * 基底パス配下のファイル名一覧を取得します。<br>
	 * @param base 基底パス
	 * @param hierarchical サブディレクトリも含めて対象とする場合にtrueを指定
	 * @return 基底パス配下のディレクトリ
	 */
	public static String[] filenames(String base, boolean hierarchical) {
		return filenames(base, null, hierarchical);
	}

	/**
	 * 基底パス配下のファイルを取得します。<br>
	 * @param base 基底パス
	 * @param hierarchical サブディレクトリも含めて対象とする場合にtrueを指定
	 * @return 基底パス配下のディレクトリ
	 */
	public static File[] filenames(File base, boolean hierarchical) {
		return files(base, null, hierarchical);
	}

	/**
	 * ファイルがバイナリファイルであるか判定します。<br>
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
	 * ファイルパス同士を接続したパス文字列を提供します。<br>
	 * このメソッドはパス文字列の結合時のパスセパレータ補完処理を考慮せずに実装を行うために設けられました。<br>
	 * <br>
	 * 動作例：<br>
	 * <pre>
	 * connectPath("foo", "bar") -&gt; "foo/bar"
	 * connectPath("foo/", "bar") -&gt; "foo/bar"
	 * connectPath("foo/", "/bar") -&gt; "foo/bar"
	 * connectPath("foo/", "bar/") -&gt; "foo/bar"
	 * </pre>
	 * @param path1 接続元ディレクトリパス
	 * @param path2 接続対象ディレクトリパス
	 * @return 接続されたパス
	 */
	public static String connectPath(String path1, String path2) {
		if (path1 == null) {
			return file(path2).getPath();
		}
		if (path2 == null) {
			return file(path1).getPath();
		}

		File filepath1 = file(path1);
		File filepath2 = file(path2);

		if (filepath1.exists() && !filepath1.isDirectory()) {
			throw new RuntimeException("Cannot path(" + path2 + ") connect to filepath(" + path1 + ")");
		}

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
	 * パス文字絵r津配列を全て接続したパス文字列として提供します。<br>
	 * @param paths パス配列
	 * @return 接続されたパス
	 * @see #connectPath(String, String)
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
	 * ファイルパスのディレクトリパス部分を取得します。<br>
	 * 自身がディレクトパスの場合はそのままのパス情報として返却されます。<br>
	 * @param path ファイルパス
	 * @return ディレクトリパスを持つファイルオブジェクト
	 */
	public static File getDir(File path) {
		if (path.isDirectory()) {
			return file(path.getAbsolutePath());
		} else {
			String absolute = path.getAbsolutePath();
			absolute = absolute.substring(0, absolute.lastIndexOf(File.separator));
			return file(absolute);
		}
	}

	/**
	 * ファイルパスから拡張子を除いたファイル名を取得します。<br>
	 * @param path ファイルパス
	 * @return 拡張子を除いたファイル名(ディレクトリパスは含みません)
	 */
	public static String getBasename(File path) {
		if (path == null) {
			return "";
		}
		String name = path.getName();
		String base = name.indexOf(".") >= 0 ? name.substring(0, name.lastIndexOf(".")) : name;
		return base;
	}

	/**
	 * ファイルパスから拡張子を除いたファイル名を取得します。<br>
	 * @param path ファイルパス
	 * @return 拡張子を除いたファイル名(ディレクトリパスは含みません)
	 */
	public static String getBasename(String path) {
		if (StringUtil.isBlank(path)) {
			path = "";
		}
		return getBasename(file(path));
	}

	/**
	 * ファイルパスから拡張子部を取得します。<br>
	 * @param path 対象パスオブジェクト
	 * @return ファイル拡張子文字列
	 */
	public static String getExtension(File path) {
		if (path == null) {
			return "";
		}
		String name = path.getName();
		String ext = name.indexOf(".") >= 0 ? name.substring(name.lastIndexOf(".") + 1) : "";
		return ext;
	}

	/**
	 * ファイルパスから拡張子部を取得します。<br>
	 * @param path 対象パスオブジェクト
	 * @return ファイル拡張子文字列
	 */
	public static String getExtension(String path) {
		if (StringUtil.isBlank(path)) {
			path = "";
		}
		return getExtension(file(path));
	}

	/**
	 * ファイルパスに"(x)"を付加してインクリメントされたファイルパスの取得を行います。<br>
	 * 指定されたファイルパスが存在しない場合はそのまま返却します。<br>
	 * @param path ファイルパス
	 * @return インクリメントされたファイルパス
	 */
	public static String getIncrementPath(String path) {
		File file = file(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				int count = 1;
				while (true) {
					File check = file(path + " (" + count + ")");
					if (!check.exists()) {
						return check.getAbsolutePath();
					}
					count++;
				}
			} else {
				String dirpath = getDir(file).getAbsolutePath();
				String basename = getBasename(path);
				String extension = getExtension(path);
				int count = 1;
				while (true) {
					File check = file(connectPath(dirpath, basename + " (" + count + ")." + extension));
					if (!check.exists()) {
						return check.getAbsolutePath();
					}
					count++;
				}
			}
		}
		return file.getAbsolutePath();
	}
}
