package io.jutil.springeasy.core.io;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author Jin Zheng
 * @since 2024/7/13
 */
@Slf4j
public class ShellExecutor {
	private static final String BLANK = " ";

	private ShellExecutor() {
	}

	public static Result execute(String command) {
		return execute(null, command);
	}

	public static Result execute(String workDir, String command) {
		var commands = command.split(BLANK);
		var builder = new ProcessBuilder(commands);
		builder.redirectErrorStream(true);
		try {
			if (workDir != null && !workDir.isEmpty()) {
				builder.directory(new File(workDir));
			}
			var process = builder.start();
			int exitValue = process.waitFor();
			String output = null;
			try (var is = process.getInputStream()) {
				output = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			}
			return new Result(exitValue, output);
		} catch (InterruptedException e) {
			var output = "Interrupted";
			log.warn(output, e);
			Thread.currentThread().interrupt();
			return new Result(-2, output, e);
		} catch (Exception e) {
			var output = "Execute command: " + Arrays.toString(commands) + ", error";
			log.error(output, e);
			return new Result(-1, output, e);
		}
	}

	@Getter
	public static class Result {
		private final int exitValue;
		private final String output;
		private final Exception cause;

		Result(int exitValue, String output) {
			this(exitValue, output, null);
		}
		Result(int exitValue, String output, Exception cause) {
			this.exitValue = exitValue;
			this.output = output;
			this.cause = cause;
		}

		public boolean isSuccess() {
			return this.exitValue == 0;
		}
	}
}
