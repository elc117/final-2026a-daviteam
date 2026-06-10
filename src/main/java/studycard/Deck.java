package studycard;

import java.util.ArrayList;
import java.util.Optional;

public class Deck {
	public final long id;
	private ArrayList<Card> cards;

	public Deck() {
		this.id = 0; // mudar depois
		this.cards = new ArrayList<>();
	}

	public void add(Card card) {
		this.cards.add(card);
	}
	
	public void remove(Card card) {
		this.cards.remove(card);
	}
	
	public void remove(int index) {
		if(this.cards.size() < index) {			
			this.cards.remove(index);
		}
	}
	
	public Optional<Card> getCard(int index) {
		return Optional.ofNullable(this.cards.get(index));
	}
	
	public ArrayList<Card> getAllCards(){
		return this.cards;
	}
}
