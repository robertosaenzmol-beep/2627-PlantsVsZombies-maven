package tp1.pvz.control.command;

import tp1.pvz.view.GameView;
import tp1.pvz.logic.GameModel;


public class ResetCommand extends Command{


    public ResetCommand(){
        this.name = "reset";
        this.shortcut = "r";
        this.details = "[r]eset";
        this.help = "start a new game";

    }

    @Override
    public void execute(GameModel game, GameView view) {
        game.reset();
        view.showGame();
    }

}