package com.Cardinal.LOTH.io;

import java.io.IOException;
import java.io.OutputStream;

public class StringOutputStream extends OutputStream {

	private String deString = "";

	@Override
	public void write(int b) throws IOException {
		deString += (char) b;
	}

	public String getString() {
		return deString;
	}

	public void purge() {
		deString = "";
	}
}
