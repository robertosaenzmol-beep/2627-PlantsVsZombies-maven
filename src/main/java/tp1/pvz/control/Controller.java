package tp1.pvz.control;

import tp1.pvz.logic.Game;
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

	private final Game game;
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
			String command = words[0];
			boolean cycleAdvanced = false;

			if (command.equals("help") || command.equals("h")) {
				view.showMessage(Messages.HELP);
			} else if (command.equals("list") || command.equals("l")) {
				view.showMessage(Messages.LIST);
			} else if (command.equals("reset") || command.equals("r")) {
				game.reset();
				cycleAdvanced = true;
			} else if (command.equals("exit") || command.equals("e")) {
				game.quit();
			} else if (command.equals("none") || command.equals("n") || command.equals("")) {
				game.update();
				cycleAdvanced = true;
			} else if (command.equals("add") || command.equals("a")) {
				if (words.length < 4) {
					view.showError(Messages.COMMAND_PARAMETERS_MISSING);
				} else {
					String plantName = words[1];
					try {
						int col = Integer.parseInt(words[2]);
						int row = Integer.parseInt(words[3]);
						game.tryAddPlant(plantName, col, row);
						game.update();
						cycleAdvanced = true;
					} catch (NumberFormatException e) {
						view.showError(Messages.INVALID_POSITION);
					} catch (IllegalArgumentException e) {
						view.showError(e.getMessage());
					}
				}
			} else {
				view.showError(Messages.UNKNOWN_COMMAND);
			}

			if (cycleAdvanced) {
				view.showGame();
			}
		}

		view.showEndMessage();
	}

}
