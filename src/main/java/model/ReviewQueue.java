package model;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Optional;

import service.ReviewQueueService;

public class ReviewQueue {
	private ArrayList<Card> cards;
	
	public ReviewQueue(Deck deck) {
		this.cards = ReviewQueueService.buildQueue(deck);
	}
	
}
