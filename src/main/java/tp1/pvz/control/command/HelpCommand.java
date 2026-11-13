package tp1.pvz.control.command;

import tp1.pvz.logic.GameModel;
import tp1.pvz.view.GameView;
import tp1.pvz.view.Messages;

public class HelpCommand extends Command {

    public HelpCommand() {
        this.name = "help";
        this.shortcut = "h";
        this.details = "[h]elp";
        this.help = "print this help message";
    }

    @Override
    public void execute(GameModel game, GameView view) {
        view.showMessage(Command.commandsHelp());
    }
}