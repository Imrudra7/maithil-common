package in.maithilart.common.db;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public interface DatabaseExecutor {

	int update(String sql, Object... params);

	<T> List<T> query(String sql, RowMapper<T> rowMapper, Object... params);

	<T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... params);
}
