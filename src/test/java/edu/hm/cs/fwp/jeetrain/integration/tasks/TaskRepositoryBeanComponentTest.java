package edu.hm.cs.fwp.jeetrain.integration.tasks;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity;

/**
 * Komponententest für {@link TaskRepositoryBean}
 * 
 * @author theism
 *
 */
@RunWith(Arquillian.class)
public class TaskRepositoryBeanComponentTest {

	private static final String DEFAULT_TEST_USER = "theism";

	/**
	 * Baut ein EJB-JAR mit den zu testenden Klassen.
	 * 
	 * @return EJB-Modul
	 */
	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive ejbModule = ShrinkWrap
				.create(JavaArchive.class)
				.addPackage(Task.class.getPackage())
				.addClass(AuditableEntity.class)
				.addClass(TaskRepositoryBean.class)
				.addClass(TaskBuilder.class)
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml");
		return ejbModule;
	}

	@Inject
	TaskRepositoryBean underTest;

	private final List<Task> trashBin = new ArrayList<Task>();

	@After
	public void onAfter() {
		for (Task current : this.trashBin) {
			try {
				this.underTest.removeTask(current);
			} catch (Exception ex) {
				System.err
						.println("Ingoriere Exception beim Löschen von Task ["
								+ current + "]");
				ex.printStackTrace();
			}
		}
		this.trashBin.clear();
	}

	@Test
	public void testAddTask() {
		Task newTask = new TaskBuilder().build();
		newTask.trackCreation(DEFAULT_TEST_USER, Calendar.getInstance()
				.getTime());
		this.underTest.addTask(newTask);
		this.trashBin.add(newTask);
		Task persistentTask = this.underTest.getTaskById(newTask.getId());
		assertNotNull("Neu angelegter Task muss gefunden werden",
				persistentTask);
	}

	private Task buildTask() {
		Task result = new Task();
		result.trackCreation("theism", Calendar.getInstance().getTime());
		return result;
	}
}
