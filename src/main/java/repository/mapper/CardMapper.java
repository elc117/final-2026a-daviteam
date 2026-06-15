package repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import model.Card;

public class CardMapper implements RowMapper<Card> {

	@Override
	public Card map(ResultSet rs, StatementContext ctx) throws SQLException {
		return new Card(
			rs.getLong("id"),
			rs.getLong("deck_id"),
			rs.getString("front"),
			rs.getString("back"),
			Card.CardType.valueOf(rs.getString("type")),
			Card.CardStatus.valueOf(rs.getString("status")),
			rs.getInt("score"),
			rs.getObject("next_review", LocalDate.class)
		);
	}

}
