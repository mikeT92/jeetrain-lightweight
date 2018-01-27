/*
 * jeetrain-lightweight:TaskProcessorComponentTest.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.business.tasks.boundary;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.business.tasks.entity.TaskLifeCycleState;
import edu.hm.cs.fwp.jeetrain.common.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.jeetrain.common.test.PersistenceTestArtifacts;

/**
 * {@code Component Test} on {@link TaskProcessorBean}.
 * 
 * @author mikeT92
 * @version 1.0
 * @since 23.01.2018
 */
@RunWith(Arquillian.class)
public class TaskProcessorComponentTest {

	/**
	 * Creates a WAR module containing classes to test plus test classes plus all
	 * required classed.
	 */
	@Deployment
	public static WebArchive createDeployment() {
		WebArchive webModule = ShrinkWrap.create(WebArchive.class).addPackage(Task.class.getPackage())
				.addClass(TaskProcessorBean.class).addClass(TraceInterceptor.class).addClass(TaskBuilder.class);
		PersistenceTestArtifacts.attachToDeployment(webModule);
		return webModule;
	}

	@Inject
	private TaskProcessorBean underTest;

	private List<Long> taskIdTrashBin = new ArrayList<>();

	@After
	public void onAfter() {
		for (Long currentTaskId : taskIdTrashBin) {
			try {
				this.underTest.removeTaskById(currentTaskId);
			} catch (Exception ex) {
				System.err.println(String.format("Unable to clean up test task [%s]", currentTaskId));
			}
		}
		this.taskIdTrashBin.clear();
	}

	@Test
	public void testIfInjectionWorks() {
		assertNotNull("underTest must not be null", this.underTest);
	}

	@Test
	public void submitTaskStoresNewTaskWithValidState() {
		Task newTask = new TaskBuilder().build();
		this.underTest.submitTask(newTask);
		this.taskIdTrashBin.add(newTask.getId());
		Task submittedTask = this.underTest.retrieveTaskById(newTask.getId());
		assertNotNull("submitTask must add task to datastore", submittedTask);
		assertNotNull("Task.submitterUserId must not be null", submittedTask.getSubmitterUserId());
		assertNotNull("Task.submissionDate must not be null", submittedTask.getSubmissionDate());
		assertEquals("Task.lifeCycleState must be OPEN_RUNNING", TaskLifeCycleState.OPEN_RUNNING,
				submittedTask.getLifeCycleState());
	}
}
