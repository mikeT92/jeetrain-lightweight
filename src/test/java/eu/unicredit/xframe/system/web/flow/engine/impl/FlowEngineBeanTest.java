/**
 * 
 */
package eu.unicredit.xframe.system.web.flow.engine.impl;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl.FlowEngineBean;

/**
 * 
 * @author theism
 *
 */
@RunWith(Arquillian.class)
public class FlowEngineBeanTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addPackages(true,
				"edu.hm.cs.fwp.jeetrain.framework.web.flow");
	}

	@Inject
	FlowEngineBean underTest;

	@Test
	public void testLookup() {
		assertNotNull(underTest);
	}
}
