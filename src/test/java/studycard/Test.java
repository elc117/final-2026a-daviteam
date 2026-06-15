package studycard;

import org.jdbi.v3.core.Jdbi;

import model.Card;
import model.Deck;
import repository.DeckRepository;
import model.Card.CardType;

public class Test {
	
	public static void main(String args[]) {
		Config.create();
		Jdbi jdbi = Config.getJdbi();
		DeckRepository deckRepo = new DeckRepository(jdbi);
		
		Deck d = new Deck("Inglês");
		deckRepo.save(d);
		d.add(new Card(d.getId(),"House","Casa",CardType.RECOGNITION));
		d.add(new Card(d.getId(),"Porta","Door",CardType.RECALL));
		System.out.println(deckRepo.findAll());
	}

}
