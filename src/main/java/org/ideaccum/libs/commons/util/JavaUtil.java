package org.ideaccum.libs.commons.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Javaランタイムに関するユーティリティインタフェースを提供します。<br>
 * <p>
 * ランタイム上から提供される各種情報に対する利用頻度の高い操作を提供します。<br>
 * </p>
 *
 * @author Kitagawa<br>
 *
 *<!--
 * 更新日		更新者			更新内容
 * 2007/05/17	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		新規作成(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class JavaUtil {

	/**
	 * コンストラクタ<br>
	 */
	private JavaUtil() {
		super();
	}

	/**
	 * ランタイムが保持しているシステムプロパティの内容を出力ストリームにトレースします。<br>
	 * @param stream 出力ストリーム
	 */
	public static void printSystemProperties(PrintStream stream) {
		if (stream == null) {
			return;
		}
		List<String> keys = new LinkedList<>();
		for (Iterator<Object> iterator = System.getProperties().keySet().iterator(); iterator.hasNext();) {
			keys.add((String) iterator.next());
		}
		Collections.sort(keys);
		for (String key : keys) {
			stream.println(key + " = " + System.getProperties().getProperty(key));
		}
	}

	/**
	 * ランタイムが保持しているシステムプロパティの内容を出力ストリームにトレースします。<br>
	 * @param writer 出力ストリーム
	 */
	public static void printSystemProperties(PrintWriter writer) {
		if (writer == null) {
			return;
		}
		List<String> keys = new LinkedList<>();
		for (Iterator<Object> iterator = System.getProperties().keySet().iterator(); iterator.hasNext();) {
			keys.add((String) iterator.next());
		}
		Collections.sort(keys);
		for (String key : keys) {
			writer.println(key + " = " + System.getProperties().getProperty(key));
		}
	}

	/**
	 * ランタイムが保持しているシステムプロパティの内容を標準ストリームにトレースします。<br>
	 */
	public static void printSystemProperties() {
		printSystemProperties(System.out);
	}

	/**
	 * Javaのバージョンをメジャー番号、マイナー番号、リビジョン番号、アップデート番号の順の配列情報として取得します。<br>
	 * @return Javaバージョン情報
	 */
	private static int[] getJavaVersion(String version) {
		version = StringUtil.nvl(version);
		StringSplitter splitter = new StringSplitter(version, new String[] { ".", "_" }, null, false, false);
		String major = splitter.nextElement();
		String minor = splitter.nextElement();
		String revision = splitter.nextElement();
		String update = splitter.nextElement();
		return new int[] { //
				!StringUtil.isEmpty(major) && StringUtil.isNumber(major) ? Integer.parseInt(major) : 0, //
				!StringUtil.isEmpty(minor) && StringUtil.isNumber(minor) ? Integer.parseInt(minor) : 0, //
				!StringUtil.isEmpty(revision) && StringUtil.isNumber(revision) ? Integer.parseInt(revision) : 0, //
				!StringUtil.isEmpty(update) && StringUtil.isNumber(update) ? Integer.parseInt(update) : 0, //
		};
	}

	/**
	 * Javaのメジャーバージョン番号を取得します。<br>
	 * @return Javaのメジャーバージョン番号
	 */
	public static int getJavaVersionOfMajor() {
		return getJavaVersion(System.getProperty("java.version"))[0];
	}

	/**
	 * Javaのマイナーバージョン番号を取得します。<br>
	 * @return Javaのマイナーバージョン番号
	 */
	public static int getJavaVersionOfMinor() {
		return getJavaVersion(System.getProperty("java.version"))[1];
	}

	/**
	 * Javaのリビジョン番号を取得します。<br>
	 * @return Javaのリビジョン番号
	 */
	public static int getJavaVersionOfRevision() {
		return getJavaVersion(System.getProperty("java.version"))[2];
	}

	/**
	 * Javaのアップデート番号を取得します。<br>
	 * @return Javaのアップデート番号
	 */
	public static int getJavaVersionOfUpdate() {
		return getJavaVersion(System.getProperty("java.version"))[3];
	}

	/**
	 * Javaバージョン文字列(XX.XX.XX_XXX)の比較を行います。<br>
	 * 第一引数のバージョン文字列のバージョンが大きい場合には1以上の値が返却され、小さい場合には負数が返却されます。<br>
	 * 同値のバージョン番号の場合には0が返却されます。<br>
	 * @param version1 Javaバージョン文字列(XX.XX.XX_XXX)
	 * @param version2 Javaバージョン文字列(XX.XX.XX_XXX)
	 * @return 比較結果
	 */
	public static int compareJavaVersion(String version1, String version2) {
		int[] v1 = getJavaVersion(version1);
		int[] v2 = getJavaVersion(version2);
		if (v1[0] > v2[0]) {
			return 1;
		} else if (v1[0] < v2[0]) {
			return -1;
		} else if (v1[1] > v2[1]) {
			return 1;
		} else if (v1[1] < v2[1]) {
			return -1;
		} else if (v1[2] > v2[2]) {
			return 1;
		} else if (v1[2] < v2[2]) {
			return -1;
		} else if (v1[3] > v2[3]) {
			return 1;
		} else if (v1[3] < v2[3]) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 実行されているJavaランタイムのJavaバージョンと指定されたバージョン文字列(XX.XX.XX_XXX)の比較を行います。<br>
	 * ランタイムバージョンが大きい場合には1以上の値が返却され、小さい場合には負数が返却されます。<br>
	 * 同値のバージョン番号の場合には0が返却されます。<br>
	 * @param version Javaバージョン文字列(XX.XX.XX_XXX)
	 * @return 比較結果
	 */
	public static int compareJavaVersion(String version) {
		return compareJavaVersion(System.getProperty("java.version"), version);
	}

	/**
	 * 指定されたメモリサイズ値を指定単位に換算して提供します。<br>
	 * @param size メモリサイズ値
	 * @param unit 換算単位
	 * @return 換算後メモリサイズ
	 */
	private static double getMemorySize(double size, MemorySizeUnit unit) {
		if (unit == null) {
			return size;
		} else if (unit == MemorySizeUnit.BYTE) {
			return size;
		} else if (unit == MemorySizeUnit.KBYTE) {
			return size / 1024D;
		} else if (unit == MemorySizeUnit.MBYTE) {
			return size / 1024D / 1024D;
		} else if (unit == MemorySizeUnit.GBYTE) {
			return size / 1024D / 1024D / 1024D;
		} else if (unit == MemorySizeUnit.TBYTE) {
			return size / 1024D / 1024D / 1024D / 1024D;
		} else {
			return size;
		}
	}

	/**
	 * Java仮想マシンが提供するトータルメモリサイズを取得します。<br>
	 * @param unit 取得時換算単位
	 * @return Java仮想マシンが提供するトータルメモリサイズ
	 */
	public static double getMemorySizeOfTotal(MemorySizeUnit unit) {
		double value = Runtime.getRuntime().totalMemory();
		return getMemorySize(value, unit);
	}

	/**
	 * Java仮想マシンに指定されている最大割り当て可能なメモリサイズを取得します。<br>
	 * @param unit 取得時換算単位
	 * @return Java仮想マシンに指定されている最大割り当て可能なメモリサイズ
	 */
	public static double getMemorySizeOfMax(MemorySizeUnit unit) {
		double value = Runtime.getRuntime().maxMemory();
		return getMemorySize(value, unit);
	}

	/**
	 * Java仮想マシンで確保されているメモリ領域のフリーサイズを取得します。<br>
	 * ここで提供されるフリーメモリサイズは割り当てられているメモリに対するフリー領域となります(最大メモリ領域(Xmx)から見たフリー容量は{@link #getMemorySizeOfUsable(MemorySizeUnit)}を利用します)。<br>
	 * @param unit 取得時換算単位
	 * @return Java仮想マシンで確保されているメモリ領域のフリーサイズ
	 */
	public static double getMemorySizeOfFree(MemorySizeUnit unit) {
		double value = Runtime.getRuntime().freeMemory();
		return getMemorySize(value, unit);
	}

	/**
	 * Java仮想マシン上で使用されているメモリサイズを取得します。<br>
	 * @param unit 取得時換算単位
	 * @return Java仮想マシンに指定されている最大割り当て可能なメモリサイズ
	 */
	public static double getMemorySizeOfUsed(MemorySizeUnit unit) {
		double value = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		return getMemorySize(value, unit);
	}

	/**
	 * Java仮想マシン上に設定されている最大割り当て可能メモリと比較した際の使用可能メモリサイズを取得します。<br>
	 * @param unit 取得時換算単位
	 * @return Java仮想マシン上に設定されている最大割り当て可能メモリと比較した際の使用可能メモリサイズ
	 */
	public static double getMemorySizeOfUsable(MemorySizeUnit unit) {
		return getMemorySizeOfMax(unit) - getMemorySizeOfUsed(unit);
	}

	/**
	 * メモリサイズを取得する際の単位を列挙型で提供します。<br>
	 * 
	 * @author Kitagawa<br>
	 * 
	 *<!--
	 * 更新日		更新者			更新内容
	 * 2018/05/02	Kitagawa		新規作成
	 *-->
	 */
	public enum MemorySizeUnit {

		/** バイト */
		BYTE,

		/** キロバイト */
		KBYTE,

		/** メガバイト */
		MBYTE,

		/** ギガバイト */
		GBYTE,

		/** テラバイト */
		TBYTE
	}
}
