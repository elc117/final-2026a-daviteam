package service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Card;
import model.Card.CardStatus;
import model.Deck;
import repository.CardRepository;

public class ReviewQueueService {
	private final CardRepository cardrepo;
	
	public ReviewQueueService(CardRepository cardrepo) {
		this.cardrepo = cardrepo;
	}
	
	public ArrayList<Card> buildQueue(Deck deck){
		int newCardNum = Math.max(0, deck.getNewCardLimit() - deck.getNewCardsToday());
		int reviewCardNum = Math.max(0, deck.getReviewLimit() - deck.getReviewedToday());
		
		List<Card> allNewCards = cardrepo.getCardsByLatest(deck.getId(), CardStatus.NEW);
		List<Card> newCards = allNewCards.subList(0, Math.min(newCardNum, allNewCards.size()));
		
		List<Card> allReviewCards = cardrepo.getCardsByLatest(deck.getId(), CardStatus.REVIEW);
		List<Card> reviewCards = allReviewCards.subList(0, Math.min(reviewCardNum, allReviewCards.size()));
		
		Iterator<Card> newIt = newCards.iterator();
		Iterator<Card> reviewIt = reviewCards.iterator();
		
		ArrayList<Card> queue = new ArrayList<>();
		while (newIt.hasNext() || reviewIt.hasNext()) {
			if(newIt.hasNext()) queue.add(newIt.next());
			if(reviewIt.hasNext()) queue.add(reviewIt.next());
		}
		
		return queue;
	}
}
