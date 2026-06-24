package service;

import java.time.LocalDate;

import model.Card;
import model.Card.CardStatus;
import model.Card.Rating;
import model.Deck;

public class CardService {
	public static void review(Card card, Deck deck, Rating rating) {
		if(card.getStatus() == CardStatus.NEW) {
			card.setStatus(CardStatus.REVIEW);
			deck.incrementNewCard();
		}else {
			deck.incrementReviewed();
		}
		
		switch(rating) {
		case MISS:
			card.setNextReview(LocalDate.now().plusDays(1));
			card.setSuccessfulReviews(0);
			int newEase = card.getEase();
			card.setEase(newEase);
			break;
		case HARD:
			break;
		case EASY:
			break;
		default:
			break;
			
		}
	}
}
