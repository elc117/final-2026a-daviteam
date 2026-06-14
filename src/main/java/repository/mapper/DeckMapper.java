package repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import model.Deck;

public class DeckMapper implements RowMapper<Deck> {

	@Override
	public Deck map(ResultSet rs, StatementContext ctx) throws SQLException {
		return new Deck(
			rs.getLong("id"),
			rs.getString("name"),
			rs.getInt("daily_new_limit"),
			rs.getInt("daily_review_limit"),
            rs.getInt("new_today"),
            rs.getInt("review_today"),
            rs.getObject("last_session_date", LocalDate.class)
		);
	}

}
