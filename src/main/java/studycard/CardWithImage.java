package studycard;

import java.awt.image.BufferedImage;

public class CardWithImage extends Card {
	private BufferedImage image;

	public CardWithImage(String front, String back, CardType type, BufferedImage image) {
		super(front, back, type);
		this.image = image;
	}

	@Override
	public String getFront() {
		if(this.type == CardType.RECALL) {
			// return this.image; descobrir como fazer isso
		}
	}
	
}
