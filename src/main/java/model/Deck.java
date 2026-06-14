package model;

import java.util.ArrayList;
import java.util.Optional;

public class Deck {
	public final long id;
	private ArrayList<Card> cards;
	private int dailyNewCardLimit = 10;
	private int dailyReviewLimit = 30;
	private int newCardsToday = 0;
	private int reviewedToday = 0;

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
	
	public void changeNewCardLimit(int limit) {
		this.dailyNewCardLimit = limit;
	}
	
	public void changeReviewLimit(int limit) {
		this.dailyReviewLimit = limit;
	}
	
	public Optional<Card> getCard(int index) {
		return Optional.ofNullable(this.cards.get(index));
	}
	
	public ArrayList<Card> getAllCards(){
		return this.cards;
	}
	
	public int getNewCardLimit() {
		return this.dailyNewCardLimit;
	}
	
	public int getReviewLimit() {
		return this.dailyReviewLimit;
	}
	
	public int getNewCardsToday() {
		return newCardsToday;
	}

	public void incrementNewCard() {
		this.newCardsToday++;
	}

	public int getReviewedToday() {
		return reviewedToday;
	}

	public void incrementReviewed() {
		this.reviewedToday++;
	}
	
	public ReviewQueue startReview() {
		return new ReviewQueue(this);
	}
}
