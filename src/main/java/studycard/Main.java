package studycard;

import io.javalin.Javalin;
import repository.CardRepository;
import repository.DeckRepository;
import service.RoutesService;

public class Main {

	public static void main(String[] args) {
		Config.create();
		DeckRepository deckrepo = new DeckRepository(Config.getJdbi());
		CardRepository cardrepo = new CardRepository(Config.getJdbi());
		Javalin app = Javalin.create(config->{
			config.staticFiles.add("/public");
		}).start(3000);
		
		// rotas
		RoutesService.init(app, deckrepo, cardrepo);
	}

}
