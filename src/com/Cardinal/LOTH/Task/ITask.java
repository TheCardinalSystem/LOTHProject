package com.Cardinal.LOTH.Task;

import java.util.Arrays;
import java.util.List;

/**
 * The template for queued tasks.
 * 
 * @author Cardinal System
 * @see TaskManager
 */
public interface ITask {

	/**
	 * Runs this task.
	 * 
	 * @return a list of tasks that must be executed to complete this one. (If this
	 *         task fails, it might need to pass new instructions to the
	 *         {@link TaskManager} in order to solve the issue)
	 */
	public ITask[] runTask();

	/**
	 * Creates an ordered array of {@link ITask} objects.
	 * 
	 * @param firstTask
	 *            the first element to place in the array.
	 * @param inOrderOfExecution
	 *            the tasks to add to the array. Pass in order of desired execution.
	 * @return the ordered array.
	 */
	public static ITask[] merge(ITask firstTask, ITask... inOrderOfExecution) {
		
		List<ITask> ordered;
		
		if (inOrderOfExecution.length == 1) {
			ordered = Arrays.asList(new ITask[] { firstTask, inOrderOfExecution[0] });
		} else {
			ordered = Arrays.asList(new ITask[] { firstTask });
			ordered.addAll(Arrays.asList(inOrderOfExecution));
		}
		
		return ordered.toArray(new ITask[ordered.size()]);
	}
}
