package tp1.pvz.control.command;

import java.util.Arrays;
import java.util.List;

import tp1.pvz.logic.GameModel;
import tp1.pvz.view.GameView;
import tp1.pvz.view.Messages;

public abstract class Command {

    // un ejemplar de cada comando, en el orden en que salen en la ayuda
    static final List<Command> AVAILABLE_COMMANDS = Arrays.asList(
        new AddPlantCommand(),
        new ListPlantsCommand(),
        new ResetCommand(),
        new HelpCommand(),
        new ExitCommand(),
        new UpdateCommand()
    );

    protected String name;
    protected String shortcut;
    protected String details;
    protected String help;

    public static Command parse(String[] words) {
        for (Command c : AVAILABLE_COMMANDS) {
            if (c.matchCommand(words[0])){
                return c.create(words);
            }
        }
        return null; 
    }

    public boolean matchCommand(String word) {
       return word.equalsIgnoreCase(name) || word.equalsIgnoreCase(shortcut);
    }
    

    public Command create(String[] words) {
        return this; // default: commands without parameters just return themselves
    }

    public String getName() { return name; }

    public String getShortcut() { return shortcut; }

    public String getDetails() { return details; }

    public String getHelp() { return help; }

    // monta el texto de ayuda recorriendo todos los comandos
    public static String commandsHelp() {
        StringBuilder sb = new StringBuilder(Messages.HELP_HEADER);
        for (Command c : AVAILABLE_COMMANDS) {
            sb.append(System.lineSeparator());
            sb.append(c.getDetails()).append(": ").append(c.getHelp());
        }
        return sb.toString();
    }

    public abstract void execute(GameModel game, GameView view);
}