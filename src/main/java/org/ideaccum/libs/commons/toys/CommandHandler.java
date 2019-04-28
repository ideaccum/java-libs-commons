package org.ideaccum.libs.commons.toys;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * コマンドを実行する際のプロセス実行ハンドラ処理を提供するためのインタフェースを提供します。<br>
 * <p>
 * このクラスは簡易的なコマンド実行を行うための{@link org.ideaccum.libs.commons.toys.CommandProcess}クラスでコマンド実行を行った際のプロセスに対するハンドラ処理インタフェースを提供します。<br>
 * </p>
 * 
 * @author Kitagawa<br>
 * 
 *<!--
 * 更新日		更新者			更新内容
 * 2014/09/10	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public abstract class CommandHandler {

	/**
	 * コンストラクタ<br>
	 */
	public CommandHandler() {
		super();
	}

	/**
	 * コマンド入出力ストリーム処理スレッドを開始します。<br>
	 * @param process コマンド実行プロセスオブジェクト
	 */
	final void execute(final Process process) {
		// ユーザー入力処理
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					handleInput(process.getOutputStream());
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
		// コマンド出力処理
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					handleOutput(process.getInputStream());
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
		// コマンドエラー出力処理
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					handleError(process.getErrorStream());
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
	}

	/**
	 * コマンド実行時のユーザー入力が発生した際の処理を行います。<br>
	 * @param stream コマンドに対して入力を提供する出力ストリームオブジェクト
	 * @throws Throwable 予期せぬ処理例外が発生した場合にスローされます
	 */
	public abstract void handleInput(OutputStream stream) throws Throwable;

	/**
	 * コマンド実行時の出力が発生した際の処理を行います。<br>
	 * @param stream コマンドから提供される出力内容を入力するストリームオブジェクト
	 * @throws Throwable 予期せぬ処理例外が発生した場合にスローされます
	 */
	public abstract void handleOutput(InputStream stream) throws Throwable;

	/**
	 * コマンド実行時のエラー出力が発生した際の処理を行います。<br>
	 * @param stream コマンドから提供されるエラー出力内容を入力するストリームオブジェクト
	 * @throws Throwable 予期せぬ処理例外が発生した場合にスローされます
	 */
	public abstract void handleError(InputStream stream) throws Throwable;

	/**
	 * ディフォルトのコマンドプロセスハンドル処理を提供するクラスです。<br>
	 * <p>
	 * このクラスでは、入力された内容を切り捨てるダミーのコマンド実行時の入出力ハンドル処理を提供します。<br>
	 * </p>
	 * 
	 * @author Kitagawa<br>
	 * 
	 *<!--
	 * 更新日		更新者			更新内容
	 * 2014/09/10	Kitagawa		新規作成
	 *-->
	 */
	public static final class CommandDefaultHandler extends CommandHandler {

		/** コマンド出力内容 */
		private String output;

		/** エラー出力内容 */
		private String error;

		/**
		 * コンストラクタ<br>
		 */
		public CommandDefaultHandler() {
			this.output = "";
			this.error = "";
		}

		/**
		 * コマンド出力内容を取得します。<br>
		 * @return コマンド出力内容
		 */
		public String getOutput() {
			return output;
		}

		/**
		 * エラー出力内容を取得します。<br>
		 * @return エラー出力内容
		 */
		public String getError() {
			return error;
		}

		/**
		 * コマンド実行時の出力が発生した際の処理を行います。<br>
		 * @param stream コマンドから提供される出力内容を入力するストリームオブジェクト
		 * @throws Throwable 予期せぬ処理例外が発生した場合にスローされます
		 * @see org.ideaccum.libs.commons.toys.CommandHandler#handleOutput(java.io.InputStream)
		 */
		@Override
		public void handleOutput(InputStream stream) throws Throwable {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = reader.readLine()) != null) {
				output += line + "\n";
			}
		}

		/**
		 * コマンド実行時のエラー出力が発生した際の処理を行います。<br>
		 * @param stream コマンドから提供されるエラー出力内容を入力するストリームオブジェクト
		 * @throws Throwable 予期せぬ処理例外が発生した場合にスローされます
		 * @see org.ideaccum.libs.commons.toys.CommandHandler#handleError(java.io.InputStream)
		 */
		@Override
		public void handleError(InputStream stream) throws Throwable {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = reader.readLine()) != null) {
				error += line + "\n";
			}
		}

		/**
		 * コマンド実行時のユーザー入力が発生した際の処理を行います。<br>
		 * @param stream コマンドに対して入力を提供する出力ストリームオブジェクト
		 * @throws Throwable 予期せぬ処理例外が発生した場合にスローされます
		 * @see org.ideaccum.libs.commons.toys.CommandHandler#handleInput(java.io.OutputStream)
		 */
		@Override
		public void handleInput(OutputStream stream) throws Throwable {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			while ((reader.readLine()) != null) {
			}
		}
	}
}
