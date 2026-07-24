package in.maithilart.common.db.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import in.maithilart.common.db.DatabaseExecutor;
import in.maithilart.common.db.impl.JdbcDatabaseExecutor;

@ConditionalOnProperty(name = "maithil.event.enabled", havingValue = "true")
@Configuration
public class DBConfig {

	public DBConfig() {
		System.out.println("######## DBConfig Loaded ########");
	}

	@Bean
	DatabaseExecutor databaseExecutor(JdbcTemplate jdbcTemplate) {
		System.out.println("######## DatabaseExecutor Bean Created ########");
		return new JdbcDatabaseExecutor(jdbcTemplate);
	}
}
