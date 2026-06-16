package model;

import java.time.LocalDate;

public class Card {
	protected long id;
	protected Long deckId;
	protected String front;
	protected String back;
	protected CardType type;
	protected CardStatus status;
	protected int ease;
	protected int successfulReviews;
	protected LocalDate nextReview;
	
	public enum CardType{
		RECALL,
		RECOGNITION
	}
	
	public enum CardStatus{
		NEW,
		REVIEW,
		SUSPENDED
	}
	
	public Card(Long deckId,String front,String back,CardType type) {
		this.deckId = deckId;
		this.front = front;
		this.back = back;
		this.ease = 50;
		this.nextReview = LocalDate.now();
		this.status = CardStatus.NEW;
		this.type = type;
		this.successfulReviews = 0;
	}

	public Card(Long id, Long deckId, String front, String back, CardType type, CardStatus status, int ease, int successfulReviews, LocalDate nextReview) {
		this.id = id;
		this.deckId = deckId;
		this.front = front;
		this.back = back;
		this.type = type;
		this.status = status;
		this.ease = ease;
		this.successfulReviews = successfulReviews;
		this.nextReview = nextReview;
	}

	public Card(Long id, Card c) {
		this.id = id;
		this.deckId = c.getDeckId();
		this.front = c.getFront();
		this.back = c.getBack();
		this.ease = c.getEase();
		this.successfulReviews = c.getSuccessfulReviews();
		this.nextReview = c.getNextReview();
		this.status = c.getStatus();
		this.type = c.getType();
	}
	
	@Override
	public String toString() {
		return "['"+this.front+"';'"+this.back+"']";
	}
	
	public String getFront() {
		return front;
	}
	
	public String getBack() {
		return back;
	}

	public long getId() {
		return id;
	}

	public Long getDeckId() {
		return deckId;
	}

	public void setDeckId(Long deckId) {
		this.deckId = deckId;
	}

	public CardType getType() {
		return type;
	}

	public CardStatus getStatus() {
		return status;
	}

	public int getEase() {
		return ease;
	}

	public void setEase(int ease) {
		this.ease = ease;
	}

	public int getSuccessfulReviews() {
		return successfulReviews;
	}

	public void setSuccessfulReviews(int successfulReviews) {
		this.successfulReviews = successfulReviews;
	}

	public LocalDate getNextReview() {
		return nextReview;
	}
}
