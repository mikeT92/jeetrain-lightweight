/*
 * jeetrain-lightweight:TaskManagerBeanComponentTest.java
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.business.users.boundary.AuthenticatedUserComponentTestFixture;
import edu.hm.cs.fwp.jeetrain.framework.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.GenericRepositoryBean;

/**
 * {@code Component Test} for {@link TaskManagerBean}.
 * 
 * @author theism
 * @version 1.0
 * @since 06.06.2017
 */
@RunWith(Arquillian.class)
public class TaskManagerBeanComponentTest {

	/**
	 * Baut ein WAR-Modul mit den zu testenden Klassen.
	 * 
	 * @return WAR-Modul
	 */
	@Deployment
	public static WebArchive createDeployment() {
		WebArchive webModule = ShrinkWrap.create(WebArchive.class).addPackage(Task.class.getPackage())
				.addClass(TaskManagerBean.class).addClass(TraceInterceptor.class)
				.addPackage(GenericRepositoryBean.class.getPackage()).addClass(TaskBuilder.class);
		AuthenticatedUserComponentTestFixture.attachToDeployment(webModule);
		return webModule;
	}

	@Inject
	private TaskManagerBean underTest;

	@Inject
	private AuthenticatedUserComponentTestFixture fixture;

	private final List<Task> trashBin = new ArrayList<>();

	@Before
	public void onBefore() {
		this.fixture.onBefore();
	}

	@After
	public void onAfter() {
		for (Task current : this.trashBin) {
			try {
				this.underTest.removeTask(current);
			} catch (Exception ex) {
				System.out.println(String.format("Unable to delete task [%s] due to exception [%s]; continue anyway...",
						current, ex.getMessage()));
			}
		}
		this.trashBin.clear();
		this.fixture.onAfter();
	}

	@Test
	public void retrieveAllTasksReturnsNotNull() {
		List<Task> found = this.underTest.retrieveAllTasks();
		assertNotNull("TaskManagerBean.retrieveAllTasks always return a list", found);
	}
}
