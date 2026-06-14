package service;

import java.util.List;
import java.util.Optional;

import io.javalin.Javalin;
import repository.DeckRepository;
import model.Card;
import model.Card.CardStatus;
import model.Card.CardType;
import model.Deck;

public class RoutesService {
	public static void init(Javalin app,DeckRepository deckrepo) {
		app.get("/api/decks/{id}", ctx->{
			long id = Long.parseLong(ctx.pathParam("id"));
			Deck deck = deckrepo.findById(id).orElseThrow(()->new RuntimeException("Deck not found"));
			ctx.json(deck);
		});
		app.get("/api/decks", ctx->{
			List<Deck> decks = deckrepo.findAll();
			ctx.json(decks);
		});
		
		app.post("/api/decks", ctx->{
			String name = ctx.formParam("name");
			Deck deck = new Deck(name);
			Deck saved = deckrepo.save(deck);
			ctx.status(201).json(saved);
		});
		app.post("/api/cards",ctx->{
			String front = ctx.formParam("front");
			String back = ctx.formParam("back");
			CardType type = CardType.valueOf(ctx.formParam("type"));
			Card card = new Card(front, back, type);
			
		});
	}
}
