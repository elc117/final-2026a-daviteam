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
		
		int newEase, interval;
		int ease = card.getEase();
		int success = card.getSuccessfulReviews();
		float easeRate = (float) (card.getEase() / 100.0f);
		switch(rating) {
		case MISS:
			card.setNextReview(LocalDate.now().plusDays(1));
			card.setLastInterval(1);
			card.setSuccessfulReviews(0);
			
			newEase = Math.max(ease - 50, 130);
			card.setEase(newEase);
			break;
			
		case HARD:
			if(success == 0) { 
				interval = 1;
			}else if(success == 1) { 
				interval = 3;
			}else {
				interval = Math.round(card.getLastInterval() * easeRate);
			}
			card.setLastInterval(interval);
			card.setNextReview(LocalDate.now().plusDays(interval));
			card.setSuccessfulReviews(card.getSuccessfulReviews()+1);
			
			newEase = Math.max(ease - 15, 130);
			card.setEase(newEase);
			break;
			
		case EASY:
			if(success == 0) { 
				interval = 1;
			}else if(success == 1) { 
				interval = 6;
			}else {
				interval = Math.round(card.getLastInterval() * easeRate);
			}
			card.setLastInterval(interval);
			card.setNextReview(LocalDate.now().plusDays(interval));
			card.setSuccessfulReviews(card.getSuccessfulReviews()+1);
			
			newEase = Math.min(ease + 15, 500);
			card.setEase(newEase);
			break;
		}
	}
}
