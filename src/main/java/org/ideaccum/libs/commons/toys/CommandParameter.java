package org.ideaccum.libs.commons.toys;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ideaccum.libs.commons.util.CollectionUtil;

/**
 * コマンドを実行する際のコマンド実行パラメータ情報を管理するインタフェースを提供します。<br>
 * <p>
 * このクラスは簡易的なコマンド実行を行うための{@link org.ideaccum.libs.commons.toys.CommandProcess}クラスでコマンドを実行する際のパラメータ情報を管理します。<br>
 * </p>
 * 
 *<!--
 * 更新日      更新者           更新内容
 * 2014/09/10  Kitagawa         新規作成
 * 2018/05/02  Kitagawa         再構築(SourceForge.jpからGitHubへの移行に併せて全面改訂)
 *-->
 */
public final class CommandParameter implements Serializable {

	/** 実行コマンド */
	private String command;

	/** オプション */
	private List<String> options;

	/** カレントパス */
	private File current;

	/** 環境変数 */
	private Map<String, String> environments;

	/**
	 * コンストラクタ<br>
	 * @param command 実行コマンド
	 * @param options オプション
	 * @param current カレントパス
	 */
	public CommandParameter(String command, List<String> options, File current) {
		super();
		this.command = command;
		this.options = options;
		this.current = current;
		this.environments = new HashMap<>();
	}

	/**
	 * コンストラクタ<br>
	 * @param command 実行コマンド
	 * @param options オプション
	 * @param current カレントパス
	 */
	public CommandParameter(String command, String[] options, File current) {
		super();
		this.command = command;
		this.options = options == null ? null : CollectionUtil.arrayList(options);
		this.current = current;
		this.environments = new HashMap<>();
	}

	/**
	 * コンストラクタ<br>
	 * @param command 実行コマンド
	 * @param options オプション
	 */
	public CommandParameter(String command, List<String> options) {
		this(command, options, null);
	}

	/**
	 * コンストラクタ<br>
	 * @param command 実行コマンド
	 * @param options オプション
	 */
	public CommandParameter(String command, String[] options) {
		this(command, options, null);
	}

	/**
	 * コンストラクタ<br>
	 * @param command 実行コマンド
	 */
	public CommandParameter(String command) {
		this(command, (String[]) null, null);
	}

	/**
	 * 実行コマンドを取得します。<br>
	 * @return 実行コマンド
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * 実行コマンドを設定します。<br>
	 * @param command 実行コマンド
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * オプションを取得します。<br>
	 * @return オプション
	 */
	public String[] getOptions() {
		return options == null ? null : options.size() <= 0 ? null : options.toArray(new String[0]);
	}

	/**
	 * オプションを設定します。<br>
	 * @param options オプション
	 */
	public void setOptions(String[] options) {
		this.options = options == null ? null : CollectionUtil.arrayList(options);
	}

	/**
	 * オプションを設定します。<br>
	 * @param options オプション
	 */
	public void setOptions(List<String> options) {
		this.options = options;
	}

	/**
	 * オプションを追加します。<br>
	 * @param option オプション
	 */
	public void addOption(String option) {
		if (options == null) {
			options = new LinkedList<>();
		}
		options.add(option);
	}

	/**
	 * オプションをすべてクリアします。<br>
	 */
	public void clearOptions() {
		options = null;
	}

	/**
	 * カレントパスを取得します。<br>
	 * @return カレントパス
	 */
	public File getCurrent() {
		return current;
	}

	/**
	 * カレントパスを設定します。<br>
	 * @param current カレントパス
	 */
	public void setCurrent(File current) {
		this.current = current;
	}

	/**
	 * 環境変数を設定します。<br>
	 * @param name 変数名
	 * @param value 変数値
	 */
	public void setEnvironment(String name, String value) {
		environments.put(name, value);
	}

	/**
	 * 環境変数を取得します。<br>
	 * @param name 変数名
	 * @return 変数値
	 */
	public String getEnvironment(String name) {
		return environments.get(name);
	}

	/**
	 * 環境変数を削除します。<br>
	 * @param name 変数名
	 */
	public void remvoeEnvironment(String name) {
		environments.remove(name);
	}

	/**
	 * 環境変数をクリアします。<br>
	 */
	public void clearEnvironments() {
		environments.clear();
	}

	/**
	 * 環境変数キーセットを取得思案す。<br>
	 * @return 環境変数キーセット
	 */
	public Set<String> getEnvironmentKeys() {
		return environments.keySet();
	}
}
