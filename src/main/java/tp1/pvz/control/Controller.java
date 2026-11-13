package tp1.pvz.control;

import tp1.pvz.control.command.Command;
import tp1.pvz.logic.Game;
import tp1.pvz.logic.GameModel;
import tp1.pvz.view.GamePrinter;
import tp1.pvz.view.GameView;
import tp1.pvz.view.Messages;

/**
 * Input/output coordinator of the game (the C in MVC).
 *
 * <p>Owns the game loop: reads a line from stdin, parses it into a
 * command, validates parameters (plant type, position), delegates
 * state changes to {@link Game}, and triggers a board reprint via
 * {@link tp1.pvz.view.GameView>} when the cycle advances. It holds no game
 * state of its own; the source of truth is always {@link Game}.
 */
public class Controller {

	private final GameModel game;
	private final GameView view;

	public Controller(Game game) {
		this.game = game;
		this.view = new GamePrinter(game);
	}

	/**
	 * Runs the game logic.
	 */
	public void run() {
		view.showGame();

		while (!game.hasGameFinished()) {
			String[] words = view.getPrompt();
			// buscamos qué comando ha escrito el usuario
			Command command = Command.parse(words);
			if (command != null) {
				// cada comando sabe qué hacer y si tiene que repintar
				command.execute(game, view);
			} else {
				view.showError(Messages.UNKNOWN_COMMAND);
			}
		}

		view.showEndMessage();
	}

}
