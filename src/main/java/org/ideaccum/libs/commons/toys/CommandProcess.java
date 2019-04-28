package org.ideaccum.libs.commons.toys;

import java.util.LinkedList;
import java.util.List;

import org.ideaccum.libs.commons.util.StreamUtil;

/**
 * コマンドを簡易的に実行するための管理するインタフェースを提供します。<br>
 * <p>
 * コマンド実行用のコンテキスト情報(コマンド、パラメータや環境変数情報など)は{@link org.ideaccum.libs.commons.toys.CommandParameter}クラスを利用します。<br>
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
public final class CommandProcess {

	/**
	 * コンストラクタ<br>
	 */
	private CommandProcess() {
		super();
	}

	/**
	 * コマンドを実行します。<br>
	 * @param parameter コマンド実行パラメータ
	 * @param handler コマンド実行プロセスハンドラ
	 * @return コマンド実行結果
	 */
	public static CommandResult execAndWait(CommandParameter parameter, CommandHandler handler) {
		Process process = null;
		CommandResult result = new CommandResult();
		try {
			List<String> commands = new LinkedList<>();
			commands.add(parameter.getCommand());
			if (parameter.getOptions() != null) {
				for (String option : parameter.getOptions()) {
					commands.add(option);
				}
			}
			ProcessBuilder builder = new ProcessBuilder(commands);
			if (parameter.getCurrent() != null) {
				builder.directory(parameter.getCurrent());
			}
			for (String env : parameter.getEnvironmentKeys()) {
				builder.environment().put(env, parameter.getEnvironment(env));
			}
			process = builder.start();
			if (handler != null) {
				handler.execute(process);
				int retval = process.waitFor();
				result.setReturnCode(retval);
				result.setOutput(StreamUtil.readString(process.getInputStream()));
				result.setError(StreamUtil.readString(process.getErrorStream()));
			} else {
				CommandHandler.CommandDefaultHandler defaultHandler = new CommandHandler.CommandDefaultHandler();
				defaultHandler.execute(process);
				int retval = process.waitFor();
				result.setReturnCode(retval);
				result.setOutput(defaultHandler.getOutput());
				result.setError(defaultHandler.getError());
			}
		} catch (Throwable e) {
			result.setException(e);
		} finally {
			process.destroy();
		}
		return result;
	}

	/**
	 * コマンドを実行します。<br>
	 * @param parameter コマンド実行パラメータ
	 * @return コマンド実行結果
	 */
	public static CommandResult execAndWait(CommandParameter parameter) {
		return execAndWait(parameter, null);
	}
}
