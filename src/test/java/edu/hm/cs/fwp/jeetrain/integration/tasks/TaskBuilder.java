package edu.hm.cs.fwp.jeetrain.integration.tasks;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;

/**
 * {@code Builder} für {@link Task}s zum Testen.
 * 
 * @author theism
 *
 */
public final class TaskBuilder {

	private String subject = "(TEST) subject";
	private String description = "(TEST) description";
	
	public Task build() {
		Task result = new Task();
		result.setSubject(this.subject);
		result.setDescription(this.description);
		return result;
	}
}
