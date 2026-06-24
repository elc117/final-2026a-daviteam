package repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import model.Card;
import model.Card.CardStatus;
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
			return handle.createQuery("""
			    INSERT INTO cards (deck_id, front, back, type, status, ease, successful_reviews, last_interval, next_review)
			    VALUES (:deckId, :front, :back, :type, :status, :ease, :successfulReviews, :lastInterval, :nextReview)
			    RETURNING *
			""")
			.bind("deckId", card.getDeckId())
			.bind("front", card.getFront())
			.bind("back", card.getBack())
			.bind("type", card.getType().name())
			.bind("status", card.getStatus().name())
			.bind("ease", card.getEase())
			.bind("successfulReviews", card.getSuccessfulReviews())
			.bind("lastInterval", card.getLastInterval())
			.bind("nextReview", card.getNextReview())
			.map(new CardMapper())
			.one();
		});
	}

	@Override
	public void update(Card card) {
		jdbi.useHandle(handle -> {
			handle.createUpdate("""
			    UPDATE cards 
			    SET deck_id = :deckId, front = :front, back = :back, type = :type, 
			        status = :status, ease = :ease, successful_reviews = :successfulReviews, 
			        last_interval = :lastInterval, next_review = :nextReview 
			    WHERE id = :id
			""")
			.bind("id", card.getId())
			.bind("deckId", card.getDeckId())
			.bind("front", card.getFront())
			.bind("back", card.getBack())
			.bind("type", card.getType().name())
			.bind("status", card.getStatus().name())
			.bind("ease", card.getEase())
			.bind("successfulReviews", card.getSuccessfulReviews())
			.bind("lastInterval", card.getLastInterval())
			.bind("nextReview", card.getNextReview())
			.execute();
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

	public List<Card> getCardsByLatest(Long deckId) {
		return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards WHERE deck_id = :id AND next_review <= CURRENT_DATE ORDER BY next_review")
				.bind("id", deckId)
				.map(new CardMapper())
				.list());
	}
	
	public List<Card> getCardsByLatest(Long deckId, CardStatus status) {
		return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cards WHERE deck_id = :id AND status = :status AND next_review <= CURRENT_DATE ORDER BY next_review")
				.bind("id", deckId)
				.bind("status",status.name())
				.map(new CardMapper())
				.list());
	}
	
}
