package studycard;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Config {
	private final Jdbi jdbi;
	
	public Config() {
		HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getenv("DB_URL"));
        config.setUsername(System.getenv("DB_USER"));
        config.setPassword(System.getenv("DB_PASS"));

        HikariDataSource dataSource = new HikariDataSource(config);

        this.jdbi = Jdbi.create(dataSource);
        this.jdbi.installPlugin(new SqlObjectPlugin());
	}
	
	public Jdbi getJdbi() {
		return jdbi;
	}
}
