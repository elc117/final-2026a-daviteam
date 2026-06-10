package studycard;

public class Card {
	private final int id;
	private String front;
	private String back;
	private CardType type;
	private int score;
	
	enum CardType{
		RECALL,
		RECOGNITION
	}
	
	public Card(String front,String back,CardType type) {
		this.id = 0; // mudar depois
		this.front = front;
		this.back = back;
		this.score = 50;
		this.type = type;
	}
	
	public String getFront() {
		return front;
	}
	
	public String getBack() {
		return back;
	}
	
	
}
