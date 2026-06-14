package studycard;

import org.jdbi.v3.core.Jdbi;

import model.Card;
import model.Deck;
import repository.DeckRepository;
import model.Card.CardType;

public class Test {
	
	public static void main(String args[]) {
		Deck d = new Deck("Inglês");
		d.add(new Card("House","Casa",CardType.RECOGNITION));
		d.add(new Card("Porta","Door",CardType.RECALL));
		Config.create();
		Jdbi jdbi = Config.getJdbi();
		DeckRepository deckRepo = new DeckRepository(jdbi);
		deckRepo.save(d);
		System.out.println(deckRepo.findAll());
	}

}
