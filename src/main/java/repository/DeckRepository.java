package repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import model.Card;
import model.Deck;
import repository.mapper.DeckMapper;

public class DeckRepository implements Repository<Deck,Long>{
	private Jdbi jdbi;
	private CardRepository cardrepo;
	
	public DeckRepository(Jdbi conn) {
		this.jdbi = conn;
		this.cardrepo = new CardRepository(conn);
	}
	
	@Override
	public Deck save(Deck deck) {
		return jdbi.withHandle(handle -> {
			return handle.createQuery("""
			    INSERT INTO decks (name, daily_new_limit, daily_review_limit, new_today, review_today, last_session_date)
			    VALUES (:name, :dailyNewLimit, :dailyReviewLimit, :newToday, :reviewToday, :lastSessionDate)
			    RETURNING *
			""")
			.bind("name", deck.getName())
			.bind("dailyNewLimit", deck.getNewCardLimit())
			.bind("dailyReviewLimit", deck.getReviewLimit())
			.bind("newToday", deck.getNewCardsToday())
	     	.bind("reviewToday", deck.getReviewedToday())
	     	.bind("lastSessionDate", LocalDate.now())
	     	.map(new DeckMapper())
	     	.one();
		});
	}

	@Override
	public void update(Deck deck) {
		jdbi.useHandle(handle -> {
			handle.createUpdate("""
			    UPDATE decks 
			    SET name = :name, daily_new_limit = :dailyNewLimit, daily_review_limit = :dailyReviewLimit, 
			        new_today = :newToday, review_today = :reviewToday, last_session_date = :lastSessionDate 
			    WHERE id = :id
			""")
			.bind("id", deck.getId())
			.bind("name", deck.getName())
			.bind("dailyNewLimit", deck.getNewCardLimit())
			.bind("dailyReviewLimit", deck.getReviewLimit())
			.bind("newToday", deck.getNewCardsToday())
			.bind("reviewToday", deck.getReviewedToday())
			.bind("lastSessionDate", deck.getLastSession())
			.execute();
		});
	}

	@Override
	public Optional<Deck> findById(Long id) {
		Optional<Deck> deck = jdbi.withHandle(handle->{
			Optional<Deck> d = handle.createQuery("SELECT * from decks where id = :id")
					.bind("id",id)
					.map(new DeckMapper())
					.findFirst();
			
			return d;
		});
		if(deck.isPresent()) {
			List<Card> cards = cardrepo.findByDeck(id);
			deck.get().getAllCards().addAll(cards);
		}
		return deck;
	}

	@Override
	public List<Deck> findAll() {
		List<Deck> decks = jdbi.withHandle(handle->{
			List<Deck> d = handle.createQuery("SELECT * from decks")
					.map(new DeckMapper())
					.list();
			return d;
		});
		decks.forEach(deck->{
			List<Card> cards = cardrepo.findByDeck(deck.getId());
			deck.getAllCards().addAll(cards);
		});
		return decks;
	}

	@Override
	public void deleteById(Long id) {
		jdbi.withHandle(handle->{
			return handle.createUpdate("DELETE FROM decks WHERE id = :id")
				.bind("id",id)
				.execute();
		});
	}
}
