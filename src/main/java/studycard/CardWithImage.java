package studycard;

import java.awt.image.BufferedImage;

public class CardWithImage extends Card {
	private BufferedImage image;

	public CardWithImage(String front, String back, CardType type, BufferedImage image) {
		super(front, back, type);
		this.image = image;
	}

	
}
