package org.ideaccum.libs.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 例外情報に対する操作を行う際の支援的な操作メソッドを提供します。<br>
 * <p>
 * 例外情報操作で利用頻度の高いリスト操作を提供します。<br>
 * </p>
 *
 *<!--
 * 更新日      更新者           更新内容
 * 2005/11/22  Kitagawa         新規作成
 * 2018/05/24  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class ExceptionUtil {

	/**
	 * コンストラクタ<br>
	 */
	private ExceptionUtil() {
		super();
	}

	/**
	 * 例外オブジェクトのスタックトレース情報を文字列として取得します。<br>
	 * @param throwable 例外オブジェクト
	 * @return スタックトレース情報文字列
	 */
	public static String getStackTrace(Throwable throwable) {
		if (throwable == null) {
			return "";
		}
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		throwable.printStackTrace(p);
		String string = s.toString();
		p.close();
		return string;
	}
}
