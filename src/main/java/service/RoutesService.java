package service;

import java.util.List;
import java.util.Optional;

import dto.ReviewRequest;
import io.javalin.Javalin;
import repository.CardRepository;
import repository.DeckRepository;
import model.Card;
import model.Card.CardType;
import model.Card.Rating;
import model.Deck;

public class RoutesService {
	public static void init(Javalin app,DeckRepository deckrepo,CardRepository cardrepo,ReviewQueueService revQueueService) {
		// DECKS
		// getters
		app.get("/api/decks/{id}", ctx->{
			long id = Long.parseLong(ctx.pathParam("id"));
			Deck deck = deckrepo.findById(id).orElseThrow(()->new RuntimeException("Deck not found"));
			ctx.json(deck);
		});
		app.get("/api/decks", ctx->{
			List<Deck> decks = deckrepo.findAll();
			ctx.json(decks);
		});
		
		// setter
		app.post("/api/decks", ctx->{
			String name = ctx.formParam("name");
			Deck deck = new Deck(name);
			Deck saved = deckrepo.save(deck);
			ctx.status(201).json(saved);
		});
		
		// delete
		app.delete("/api/decks/delete/{id}", ctx->{
			Long id = ctx.pathParamAsClass("id", Long.class).get();
			deckrepo.deleteById(id);
			ctx.status(204);
		});
		app.post("/api/decks/delete/{id}", ctx -> {
			long id = ctx.pathParamAsClass("id", Long.class).get();
			deckrepo.deleteById(id);
			ctx.status(200);
		});
		
		
		// CARDS
		// criaçao
		app.post("/api/cards",ctx->{
			Long deck_id = Long.parseLong(ctx.formParam("deckId"));
			String front = ctx.formParam("front");
			String back = ctx.formParam("back");
			CardType type = CardType.valueOf(ctx.formParam("type"));
			Card card = new Card(deck_id,front, back, type);
			Card saved = cardrepo.save(card);
			ctx.status(201).json(saved);
		});
		
		// delete
		app.post("/api/cards/delete/{id}", ctx -> {
			long id = ctx.pathParamAsClass("id", Long.class).get();
			cardrepo.deleteById(id);
			ctx.status(200);
		});
		app.delete("/api/cards/delete/{id}", ctx -> {
			long id = ctx.pathParamAsClass("id", Long.class).get();
			cardrepo.deleteById(id);
			ctx.status(204);
		});
		
		
		// REVIEW QUEUES
		// cria fila
		app.get("/api/decks/{id}/review", ctx->{
			long deckId = ctx.pathParamAsClass("id", Long.class).get();
			Deck deck = deckrepo.findById(deckId).get();
			List<Card> queue = revQueueService.buildQueue(deck);
			ctx.json(queue);
		});
		
		// review card
		app.post("/api/cards/{id}/review", ctx->{
			ReviewRequest body = ctx.bodyAsClass(ReviewRequest.class);
			Rating rating = Rating.valueOf(body.rating());
			
			Optional<Card> c = cardrepo.findById(Long.parseLong(ctx.pathParam("id")));
			if(c.isPresent()) {
				Card card = c.get();
				Deck deck = deckrepo.findById(card.getDeckId()).orElseThrow(() -> new RuntimeException("Deck not found"));
				CardService.review(card, deck, rating);
				cardrepo.update(card);
				deckrepo.update(deck);
				ctx.status(200);
			}else {
				ctx.status(204);
			}
		});
	}
}
