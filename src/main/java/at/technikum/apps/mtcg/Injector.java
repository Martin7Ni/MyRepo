package at.technikum.apps.mtcg;

import java.util.ArrayList;
import java.util.List;

import at.technikum.apps.mtcg.controller.BattlesController;
import at.technikum.apps.mtcg.controller.CardController;
import at.technikum.apps.mtcg.controller.Controller;
import at.technikum.apps.mtcg.controller.DeckController;
import at.technikum.apps.mtcg.controller.PackageController;
import at.technikum.apps.mtcg.controller.ScoreboardController;
import at.technikum.apps.mtcg.controller.SessionController;
import at.technikum.apps.mtcg.controller.StatsController;
import at.technikum.apps.mtcg.controller.TradingController;
import at.technikum.apps.mtcg.controller.TransactionController;
import at.technikum.apps.mtcg.controller.UserController;
import at.technikum.apps.mtcg.repository.BattlesRepository;
import at.technikum.apps.mtcg.repository.CardRepository;
import at.technikum.apps.mtcg.repository.TradingRepository;
import at.technikum.apps.mtcg.repository.UserRepository;
import at.technikum.apps.mtcg.service.BattleSingleton;
import at.technikum.apps.mtcg.service.BattlesService;
import at.technikum.apps.mtcg.service.CardService;
import at.technikum.apps.mtcg.service.DeckService;
import at.technikum.apps.mtcg.service.PackageService;
import at.technikum.apps.mtcg.service.ScoreboardService;
import at.technikum.apps.mtcg.service.SessionService;
import at.technikum.apps.mtcg.service.StatsService;
import at.technikum.apps.mtcg.service.TradingService;
import at.technikum.apps.mtcg.service.TransactionService;
import at.technikum.apps.mtcg.service.UserService;

public class Injector {

	public List<Controller> createController(){
		List<Controller> controllerList = new ArrayList<>();
		
		UserRepository userRepository = new UserRepository();
		UserService userService = new UserService(userRepository);
		UserController userController = new UserController(userService);
		
		controllerList.add(userController);
		
		SessionService sessionService = new SessionService(userRepository);
		SessionController sessionController = new SessionController(sessionService);
		
		controllerList.add(sessionController);
		
		CardRepository cardRepository = new CardRepository();
		PackageService packageService = new PackageService(cardRepository);
		PackageController packageController = new PackageController(packageService);
		
		controllerList.add(packageController);
		
		TransactionService transactionService = new TransactionService(userRepository, cardRepository);
		TransactionController transactionController = new TransactionController(transactionService);
		
		controllerList.add(transactionController);
		
		CardService cardService = new CardService(cardRepository, userRepository);
		CardController cardController = new CardController(cardService);
		
		controllerList.add(cardController);
		
		DeckService deckService = new DeckService(cardRepository, userRepository);
		DeckController deckController = new DeckController(deckService);
		
		controllerList.add(deckController);
		
		StatsService statsService = new StatsService(userRepository);
		StatsController statsController = new StatsController(statsService);
		
		controllerList.add(statsController);
		
		ScoreboardService scoreboardService = new ScoreboardService(userRepository);
		ScoreboardController scoreboardController = new ScoreboardController(scoreboardService);
		
		controllerList.add(scoreboardController);
		
		BattleSingleton battleSingleton = BattleSingleton.getInstance();
		BattlesRepository battlesRepository = new BattlesRepository();
		BattlesService battlesService = new BattlesService(userRepository, cardRepository,battleSingleton);
		BattlesController battlesController = new BattlesController(battlesService);
		
		controllerList.add(battlesController);
		
		TradingRepository tradeRepository = new TradingRepository();
		TradingService tradingService = new TradingService(userRepository, cardRepository, tradeRepository);
		TradingController tradingController = new TradingController(tradingService);
		
		controllerList.add(tradingController);
		
		return controllerList;
	}
}
