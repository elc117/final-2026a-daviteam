package repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import model.Card;
import repository.mapper.CardMapper;

public class CardRepository implements Repository<Card, Long> {
	private final Jdbi jdbi;
	
	public CardRepository(Jdbi conn) {
		this.jdbi = conn;
	}
	
	@Override
	public Card save(Card card) {
		if (card.getDeckId() == null) {
			throw new IllegalStateException("Card deckId is required");
		}

		return jdbi.withHandle(handle -> {
			handle.createUpdate("""
			    INSERT INTO cards (deck_id, front, back, type, status, score, next_review)
			    VALUES (:deckId, :front, :back, :type, :status, :score, :nextReview)
			""")
			.bind("deckId", card.getDeckId())
			.bind("front", card.getFront())
			.bind("back", card.getBack())
			.bind("type", card.getType().name())
			.bind("status", card.getStatus().name())
			.bind("score", card.getScore())
			.bind("nextReview", card.getNextReview())
			.execute();
			return card;
		});
	}

	@Override
	public Optional<Card> findById(Long id) {
		return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards WHERE id = :id")
			.bind("id", id)
			.map(new CardMapper())
			.findFirst());
	}

	@Override
	public List<Card> findAll() {
		return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards")
			.map(new CardMapper())
			.list());
	}

	@Override
	public void deleteById(Long id) {
		jdbi.withHandle(handle -> {
			return handle.createUpdate("DELETE FROM cards WHERE id = :id")
				.bind("id", id)
				.execute();
		});
	}

	public List<Card> findByDeck(Long id) {
		return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards WHERE deck_id = :id")
			.bind("id", id)
			.map(new CardMapper())
			.list());
	}
	
}
