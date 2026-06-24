package studycard;

import org.jdbi.v3.core.Jdbi;

import controller.Router;
import io.javalin.Javalin;
import repository.CardRepository;
import repository.DeckRepository;
import service.ReviewQueueService;

public class Main {

	public static void main(String[] args) {
		Config.create();
		Jdbi jdbi = Config.getJdbi();
		DeckRepository deckrepo = new DeckRepository(jdbi);
		CardRepository cardrepo = new CardRepository(jdbi);
		ReviewQueueService revqueue = new ReviewQueueService(cardrepo);
		Javalin app = Javalin.create(config->{
			config.staticFiles.add("/public");
		}).start(3000);
		
		// rotas
		Router.init(app, deckrepo, cardrepo, revqueue);
	}

}
