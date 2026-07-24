package in.maithilart.common.db.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import in.maithilart.common.db.DatabaseExecutor;



public class JdbcDatabaseExecutor implements DatabaseExecutor {

	private final JdbcTemplate jdbcTemplate;

	public JdbcDatabaseExecutor(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int update(String sql, Object... params) {
		return jdbcTemplate.update(sql, params);
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... params) {

		return jdbcTemplate.query(sql, rowMapper, params);
	}

	@Override
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... params) {

		return jdbcTemplate.queryForObject(sql, rowMapper, params);
	}
}