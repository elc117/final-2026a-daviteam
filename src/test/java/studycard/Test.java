package studycard;

import model.Card;
import model.Deck;
import model.Card.CardType;

public class Test {

	public static void main(String args[]) {
		Deck d = new Deck();
		d.add(new Card("House","Casa",CardType.RECOGNITION));
		d.add(new Card("Porta","Door",CardType.RECALL));
		System.out.println(d.getAllCards());
	}

}
