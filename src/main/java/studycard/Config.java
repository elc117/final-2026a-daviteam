package studycard;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Config {
	private static Jdbi jdbi;
	
	public static void create() {
		HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getenv("DB_URL"));
        config.setUsername(System.getenv("DB_USER"));
        config.setPassword(System.getenv("DB_PASS"));
        
        config.setConnectionTimeout(30000);
        config.setKeepaliveTime(30000);
        config.setMaxLifetime(1800000);
        config.setIdleTimeout(600000);

        HikariDataSource dataSource = new HikariDataSource(config);

        jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
	}
	
	public static Jdbi getJdbi() {
		return jdbi;
	}
}
