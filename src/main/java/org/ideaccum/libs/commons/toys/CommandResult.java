package org.ideaccum.libs.commons.toys;

import java.io.Serializable;

import org.ideaccum.libs.commons.util.StringUtil;

/**
 * コマンドを実行する際のコマンド実行結果情報を管理するインタフェースを提供します。<br>
 * <p>
 * このクラスは簡易的なコマンド実行を行うための{@link org.ideaccum.libs.commons.toys.CommandProcess}クラスでコマンドを実行した際の実行結果情報を管理します。<br>
 * </p>
 *
 * @author Kitagawa<br>
 *
 *<!--
 * 更新日		更新者			更新内容
 * 2008/11/05	Kitagawa		新規作成
 * 2018/05/02	Kitagawa		再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class CommandResult implements Serializable {

	/** リターンコード */
	private int returnCode;

	/** コンソール出力内容 */
	private String output;

	/** エラー出力内容 */
	private String error;

	/** コマンド実行時例外 */
	private Throwable exception;

	/**
	 * コンストラクタ<br>
	 */
	CommandResult() {
		super();
	}

	/**
	 * リターンコードを取得します。<br>
	 * @return リターンコード
	 */
	public int getReturnCode() {
		return returnCode;
	}

	/**
	 * リターンコードを設定します。<br>
	 * @param returnCode リターンコード
	 */
	void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	/**
	 * コンソール出力内容を取得します。<br>
	 * @return コンソール出力内容
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * コンソール出力内容を設定します。<br>
	 * @param output コンソール出力内容
	 */
	public void setOutput(String output) {
		this.output = output;
	}

	/**
	 * エラー出力内容を取得します。<br>
	 * @return エラー出力内容
	 */
	public String getError() {
		return error;
	}

	/**
	 * エラー出力内容を設定します。<br>
	 * @param error エラー出力内容
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * コマンド実行時例外を取得します。<br>
	 * @return コマンド実行時例外
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * コマンド実行時例外を設定します。<br>
	 * @param exception コマンド実行時例外
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

	/**
	 * コンソール出力内容が存在するか判定します。<br>
	 * @return コンソール出力内容が存在する場合にtrueを返却
	 */
	public boolean hasOutput() {
		return !StringUtil.isEmpty(output);
	}

	/**
	 * エラー出力内容が存在するか判定します。<br>
	 * @return エラー出力内容が存在する場合にtrueを返却
	 */
	public boolean hasError() {
		return !StringUtil.isEmpty(error);
	}

	/**
	 * コマンド実行時例外が存在するか判定します。<br>
	 * @return コマンド実行時例外が存在する場合にtrueを返却
	 */
	public boolean hasException() {
		return exception != null;
	}
}
