package service;

import java.util.ArrayList;

import model.Card;
import model.Deck;

public class ReviewQueueService {
	public static ArrayList<Card> buildQueue(Deck deck){
		int newCards = deck.getNewCardLimit() - deck.getNewCardsToday();
		int reviewCards = deck.getReviewLimit() - deck.getReviewedToday();
		
		
		
		return null;	// placeholder
	}
}
