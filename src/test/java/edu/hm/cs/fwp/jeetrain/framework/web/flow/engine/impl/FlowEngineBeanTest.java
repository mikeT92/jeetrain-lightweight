/**
 * 
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.hm.cs.fwp.jeetrain.framework.core.test.ArquillianDeploymentBuilder;

/**
 * 
 * @author theism
 *
 */
@RunWith(Arquillian.class)
public class FlowEngineBeanTest {

	@Deployment
	public static WebArchive createDeployment() {
		return ArquillianDeploymentBuilder.createWebArchive()
						.addPackages(true, "edu.hm.cs.fwp.jeetrain.framework.web.flow");
	}

	@Inject
	FlowEngineBean underTest;

	@Test
	public void testLookup() {
		assertNotNull(underTest);
	}
}
