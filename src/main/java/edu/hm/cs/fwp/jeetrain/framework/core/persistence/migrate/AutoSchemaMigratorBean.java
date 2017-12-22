package edu.hm.cs.fwp.jeetrain.framework.core.persistence.migrate;

import org.flywaydb.core.Flyway;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;

@Singleton
@Startup
public class AutoSchemaMigratorBean {

	@Resource(lookup = "jdbc/JEETRAIN_DATASOURCE")
	private DataSource dataSource;

	@PostConstruct
	public void onPostConstruct() {
		Flyway flyway = new Flyway();
		flyway.setDataSource(this.dataSource);
		flyway.migrate();
	}
}
