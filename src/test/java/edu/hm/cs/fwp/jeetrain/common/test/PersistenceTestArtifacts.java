/*
 * jeetrain-lightweight:PersistenceTestArtifacts.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.common.test;

import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * Represents all artifacts required to run Arquillian component tests covering
 * JPA persistence.
 * 
 * @author mikeT92
 * @version 1.0
 * @since 27.01.2018
 */
public final class PersistenceTestArtifacts {

	public static void attachToDeployment(WebArchive deployment) {
		deployment.addPackages(true, "edu.hm.cs.fwp.jeetrain.common.core.persistence") //
				.addClass(ComponentTestAuthenticationFilter.class)
				.addAsResource("arquillian-persistence.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml") // Resource(EmptyAsset.INSTANCE,
																			// "META-INF/beans.xml")
				.addAsWebInfResource("arquillian-web.xml", "web.xml")
				.addAsWebInfResource("arquillian-glassfish-web.xml", "glassfish-web.xml");
	}
}
