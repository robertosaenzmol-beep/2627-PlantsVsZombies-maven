package tp1.pvz.control.command;

import tp1.pvz.view.GameView;
import tp1.pvz.logic.GameModel;


public class UpdateCommand extends Command{

@Override
public boolean matchCommand(String word) {
    return word.isEmpty() || super.matchCommand(word);
}
    public UpdateCommand(){
        this.name = "none";
        this.shortcut = "n";
        this.details = "[n]one | \"\"";
        this.help = "skips cycle";

    }

    @Override
    public void execute(GameModel game, GameView view) {
        game.update();
        view.showGame();
    }

}