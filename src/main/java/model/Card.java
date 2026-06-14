package model;

import java.time.LocalDate;

public class Card {
	protected final int id;
	protected Long deckId;
	protected String front;
	protected String back;
	protected CardType type;
	protected CardStatus status;
	protected int score;
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
		this.score = 50;
		this.nextReview = LocalDate.now();
		this.status = CardStatus.NEW;
		this.type = type;
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

	public int getScore() {
		return score;
	}

	public LocalDate getNextReview() {
		return nextReview;
	}
	
	
}
