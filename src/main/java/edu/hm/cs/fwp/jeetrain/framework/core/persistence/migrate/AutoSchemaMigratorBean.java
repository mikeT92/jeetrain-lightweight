package edu.hm.cs.fwp.jeetrain.framework.core.persistence.migrate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class AutoSchemaMigratorBean {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(lookup = "jdbc/JEETRAIN_DATASOURCE")
	private DataSource dataSource;

	@PostConstruct
	public void onPostConstruct() {
		Flyway flyway = new Flyway();
		flyway.setDataSource(this.dataSource);
		try {
			flyway.migrate();
		} catch (FlywayException mex) {
			this.logger.error("Unable to migrate db schema: " + mex.getMessage(), mex);
			this.logger.info("Trying to repair...");
			try {
				flyway.repair();
				this.logger.info("Successfully repaired db schema!");
			} catch (FlywayException rex) {
				this.logger.error("Unable to repair db schema: " + rex.getMessage(), rex);
			}
		}
	}
}
