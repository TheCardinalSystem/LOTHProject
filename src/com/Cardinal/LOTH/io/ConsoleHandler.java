package com.Cardinal.LOTH.io;

import java.io.PrintStream;

public class ConsoleHandler {

	private static StringOutputStream stringStream;
	private static SharedOutputStream stream;
	private static PrintStream printStream;

	public static void init() {
		stringStream = new StringOutputStream();
		stream = new SharedOutputStream(stringStream, System.out);
		printStream = new PrintStream(stream);
		System.setOut(printStream);
	}

	public static StringOutputStream getStringOutputStream() {
		return stringStream;
	}
}
