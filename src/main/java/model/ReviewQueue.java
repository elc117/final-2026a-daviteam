package model;

import java.util.ArrayList;

import service.ReviewQueueService;

public class ReviewQueue {
	private ArrayList<Card> cards;
	
	public ReviewQueue(Deck deck) {
		this.cards = ReviewQueueService.buildQueue(deck);
	}
	
	
}
