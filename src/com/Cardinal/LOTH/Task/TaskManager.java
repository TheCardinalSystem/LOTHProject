package com.Cardinal.LOTH.Task;

import java.lang.Thread.State;
import java.util.Arrays;

public class TaskManager {

	private static final TaskQueue QUEUE = new TaskQueue();
	public static Executor EXECUTOR = new Executor();

	public static synchronized void queue(ITask task, ITask... tasks) {
		try {
			QUEUE.add(task);
		} catch (NullPointerException e) {
			if (Arrays.asList(tasks).isEmpty())
				throw e;
		}
		Arrays.stream(tasks).forEach(QUEUE::add);

		if (EXECUTOR.getState().equals(State.TERMINATED)) {
			EXECUTOR = new Executor();
			EXECUTOR.start();
		} else if (!EXECUTOR.isAlive())
			EXECUTOR.start();
	}

	private static class Executor extends Thread {
		@Override
		public void run() {
			while (!QUEUE.isEmpty()) {
				try {
					ITask[] results = QUEUE.poll().runTask();

					if (results == null || results.length < 1)
						continue;

					queue(null, results);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
