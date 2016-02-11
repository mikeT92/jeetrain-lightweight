/*
 * lion-ejb
 * Created on 16.04.2015
 */

package edu.hm.cs.fwp.jeetrain.framework.core.test;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;

import edu.hm.cs.fwp.jeetrain.framework.core.persistence.GenericRepositoryBean;

/**
 * Gemeinsamer {@code Builder} für Komponententests auf Basis von Arquillian,
 * der vorkonfektionierte Archive erzeugt.
 * 
 * @author theism
 * @version 1.0
 * @since R2016.2 16.04.2015
 */
public final class ArquillianDeploymentBuilder {
	/**
	 * Liefert ein vorkonfektioniertes JavaArchive zurück, das alle gemeinsamen
	 * Klassen und Ressourcen enthält, die für die Ausführung aller
	 * Komponententest erforderlich sind.
	 * <p>
	 * Testklassen müssen in ihrer Deployment-Factory-Methode dann nur noch die
	 * spezifischen Testklassen hinzufügen.
	 * </p>
	 * 
	 * @return JavaArchive
	 */
	public static JavaArchive createJavaArchive() {
		return ShrinkWrap
				.create(JavaArchive.class, "lion-ejb-test.jar")
				.addClass(DateUtils.class)
				.addClass(StringUtils.class)
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsResource("test-beans.xml", "META-INF/beans.xml");
	}

	/**
	 * Liefert ein vorkonfektioniertes WebArchive zurück, das alle gemeinsamen
	 * Klassen, Ressource und Utility JARs enthält, die für die Ausführung aller
	 * Komponententest erforderlich sind.
	 * <p>
	 * Testklassen müssen in ihrer Deployment-Factory-Methode dann nur noch die
	 * spezifischen Testklassen hinzufügen.
	 * </p>
	 * 
	 * @return WebArchive
	 */
	public static WebArchive createWebArchive() {
		PomEquippedResolveStage resolver = Maven.configureResolver()
				.fromClassloaderResource("arquillian-maven-settings.xml")
				.loadPomFromClassLoaderResource("arquillian-maven-pom.xml");
		return ShrinkWrap
				.create(WebArchive.class, "jeetrain-arquillian-test.war")
				.addAsLibrary(
						resolver.resolve("org.slf4j:slf4j-api")
								.withoutTransitivity().asSingleFile())
				.addAsLibrary(
						resolver.resolve("org.slf4j:slf4j-jdk14")
								.withoutTransitivity().asSingleFile())
				.addAsLibrary(
						resolver.resolve("org.apache.commons:commons-lang3")
								.withoutTransitivity().asSingleFile())
				// Persistence
				//.addPackages(true, GenericRepositoryBean.class.getPackage())
				// Web Deployment Descriptors
				.addAsWebInfResource("arquillian-web.xml", "web.xml")
				// CDI
				.addAsWebInfResource("arquillian-beans.xml", "beans.xml")
				// JPA
				//.addAsManifestResource("arquillian-persistence.xml",
				//		"persistence.xml")
				// JSF
				.addAsWebInfResource("arquillian-faces-config.xml",
						"faces-config.xml");
	}
}
