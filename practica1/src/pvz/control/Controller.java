package pvz.control;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.GamePrinter;
import pvz.view.GameView;
import pvz.view.Messages;

/**
 * Input/output coordinator of the game (the C in MVC).
 *
 * <p>Owns the game loop: reads a line from stdin, parses it into a
 * command, validates parameters (plant type, position), delegates
 * state changes to {@link Game}, and triggers a board reprint via
 * {@link GameView} when the cycle advances. It holds no game
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
			// Solo se avanza el ciclo y se repinta si el comando lo requiere
			if (execute(words)) {
				game.update();
				view.showGame();
			}
		}
		view.showEndMessage();
	}

	/**
	 * Ejecuta el comando escrito por el usuario.
	 *
	 * @param words palabras de la línea escrita, ya en minúsculas
	 * @return <code>true</code> si después hay que avanzar un ciclo de juego.
	 */
	private boolean execute(String[] words) {
		boolean advance = false;
		switch (words[0]) {
		case "a", "add" -> advance = executeAdd(words);
		case "l", "list" -> view.showMessage(Messages.LIST);
		case "r", "reset" -> {
			game.reset();
			view.showGame();
		}
		case "h", "help" -> view.showMessage(Messages.HELP);
		case "e", "exit" -> game.quit();
		case "", "n", "none" -> advance = true;
		default -> view.showError(Messages.UNKNOWN_COMMAND);
		}
		return advance;
	}

	/**
	 * Ejecuta el comando <code>add &lt;plant&gt; &lt;col&gt; &lt;row&gt;</code>,
	 * mostrando un error si algo no es correcto.
	 *
	 * @return <code>true</code> si la planta se ha añadido.
	 */
	private boolean executeAdd(String[] words) {
		if (words.length != 4) {
			view.showError(Messages.COMMAND_PARAMETERS_MISSING);
			return false;
		}
		String plant = words[1];
		if (!game.checkGameObject(plant)) {
			view.showError(Messages.INVALID_GAME_OBJECT);
			return false;
		}
		Position position;
		try {
			int col = Integer.parseInt(words[2]);
			int row = Integer.parseInt(words[3]);
			position = new Position(row, col);
		} catch (NumberFormatException e) {
			view.showError(Messages.INVALID_POSITION);
			return false;
		}
		if (!game.isInsideBoard(position) || !game.isEmpty(position)) {
			view.showError(Messages.INVALID_POSITION);
			return false;
		}
		if (!game.addGameObject(plant, position)) {
			view.showError(Messages.NOT_ENOUGH_COINS);
			return false;
		}
		return true;
	}

}