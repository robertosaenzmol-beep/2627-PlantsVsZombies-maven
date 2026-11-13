package tp1.pvz.control.command;

import tp1.pvz.logic.GameModel;
import tp1.pvz.view.GameView;
import tp1.pvz.view.Messages;

public class ListPlantsCommand extends Command {

    public ListPlantsCommand() {
        this.name = "list";
        this.shortcut = "l";
        this.details = "[l]ist";
        this.help = "print the list of available plants";
    }

    @Override
    public void execute(GameModel game, GameView view) {
        view.showMessage(Messages.LIST);
    }
}