package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class Deck {
	private long id;
	private String name;
	private ArrayList<Card> cards = new ArrayList<>();
	private int dailyNewCardLimit = 10;
	private int dailyReviewLimit = 30;
	private int newCardsToday = 0;
	private int reviewedToday = 0;
	
	public Deck(String name) {
		this.name = name;
	}
	
	public Deck(long id, String name, int dailyNew, int dailyReview, int newToday, int reviewToday, LocalDate data) {
		this.id = id;
		this.name = name;
		this.dailyNewCardLimit = dailyNew;
		this.dailyReviewLimit = dailyReview;
		if(data.isEqual(LocalDate.now())) {			
			this.newCardsToday = newToday;
			this.reviewedToday = reviewToday;
		}
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

	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
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
