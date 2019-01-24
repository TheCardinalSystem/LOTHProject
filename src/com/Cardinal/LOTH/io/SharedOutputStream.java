package com.Cardinal.LOTH.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;

public class SharedOutputStream extends OutputStream {

	private HashSet<OutputStream> streams = new HashSet<OutputStream>();

	public SharedOutputStream(OutputStream... streams) {
		this.streams.addAll(Arrays.asList(streams));
	}

	public void addOutputStream(OutputStream stream) {
		streams.add(stream);
	}

	@Override
	public void write(int b) throws IOException {
		for (OutputStream stream : streams) {
			stream.write(b);
		}
	}

}
