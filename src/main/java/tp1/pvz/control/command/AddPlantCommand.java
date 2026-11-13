package tp1.pvz.control.command;

import tp1.pvz.view.GameView;
import tp1.pvz.view.Messages;
import tp1.pvz.logic.GameModel;




public class AddPlantCommand extends Command {

    private String plantName;
    private int col;
    private int row;
    private String error;

    public AddPlantCommand() {
        this.name = "Add";
        this.shortcut = "a";
        this.details = "[a]dd <plant> <col> <row>";
        this.help = "add a plant in position (col, row)";

    }


    public AddPlantCommand(String plantName, int col, int row) {
        this(); 
        this.plantName = plantName;
        this.col = col;
        this.row = row;
    }
   @Override
    public Command create(String[] words) {

        if (words.length < 4) {
            AddPlantCommand cmd = new AddPlantCommand();
            cmd.error = Messages.COMMAND_PARAMETERS_MISSING;
            return cmd;
    }

        // 2. Try to convert the column and row to numbers
        try {
            int col = Integer.parseInt(words[2]);
            int row = Integer.parseInt(words[3]);
            return new AddPlantCommand(words[1],col,row);
    }   catch (NumberFormatException e) {

            AddPlantCommand cmd = new AddPlantCommand();
            cmd.error = Messages.INVALID_POSITION;
            return cmd;
    }
}


    @Override
    public void execute(GameModel game, GameView view) {
        // si los parámetros estaban mal, solo mostramos el error
        if (error != null) {
            view.showError(error);
            return;
        }
        try {
            game.tryAddPlant(plantName, col, row);
            // añadir una planta gasta un turno
            game.update();
            view.showGame();
        } catch (IllegalArgumentException e) {
            // posición ocupada, planta que no existe o sin monedas
            view.showError(e.getMessage());
        }
    }

}