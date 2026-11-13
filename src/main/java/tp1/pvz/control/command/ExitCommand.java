package tp1.pvz.control.command;

import tp1.pvz.view.GameView;
import tp1.pvz.logic.GameModel;

public class ExitCommand extends Command{

    public ExitCommand(){
        this.name = "exit";
        this.shortcut = "e";
        this.details = "[e]xit";
        this.help = "terminate the program";

    }
@Override 
    public void execute(GameModel game, GameView view){
        game.quit();
    }

}
