package studycard;

import io.javalin.Javalin;
import service.RoutesService;

public class Main {

	public static void main(String[] args) {
		Javalin app = Javalin.create(config->{
			config.staticFiles.add("/public");
		}).start(3000);
		RoutesService.init(app);
	}

}
