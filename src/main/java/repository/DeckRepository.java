package repository;

import java.util.List;
import java.util.Optional;
import org.jdbi.v3.core.Handle;

import org.jdbi.v3.core.Jdbi;

import model.Deck;

public class DeckRepository implements Repository<Deck,Long>{
	private Jdbi jdbi;
	
	@Override
	public Deck save(Deck obj) {
		
		return null;
	}

	@Override
	public Optional<Deck> findById(Long id) {
		return jdbi.withHandle(handle->{
			Optional<Deck> deck = handle.createQuery("SELECT * from decks where id = :id")
					.bind("id",id)
					.mapToBean(Deck.class)
					.findFirst();
			
			return deck;
		});
	}

	@Override
	public List<Deck> findAll() {
		return jdbi.withHandle(handle->{
			List<Deck> decks = handle.createQuery("SELECT * from decks")
					.mapToBean(Deck.class)
					.list();
			return decks;
		});
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
