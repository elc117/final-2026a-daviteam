package studycard;

import java.time.LocalDate;

public class Card {
	protected final int id;
	protected String front;
	protected String back;
	protected CardType type;
	protected CardStatus status;
	protected int score;
	protected LocalDate nextReview;
	
	enum CardType{
		RECALL,
		RECOGNITION
	}
	
	enum CardStatus{
		NEW,
		REVIEW,
		SUSPENDED
	}
	
	public Card(String front,String back,CardType type) {
		this.id = 0; // mudar depois
		this.front = front;
		this.back = back;
		this.score = 50;
		this.nextReview = LocalDate.now();
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
	
	
}
