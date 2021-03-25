package by.panasenko.webproject.command;

import by.panasenko.webproject.command.impl.AgeCriteriaCommand;
import by.panasenko.webproject.command.impl.GoToOriginalCommand;
import by.panasenko.webproject.command.impl.SortUserCommand;

import java.util.EnumMap;

public class CommandProvider {
    private static CommandProvider instance;
    private final EnumMap<CommandName, Command> commands = new EnumMap(CommandName.class);

    public CommandProvider() {
        commands.put(CommandName.GO_TO_ORIGINAL_COMMAND, new GoToOriginalCommand());
        commands.put(CommandName.SORT_USER_COMMAND, new SortUserCommand());
        commands.put(CommandName.AGE_CRITERIA_COMMAND, new AgeCriteriaCommand());
    }

    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }

    public Command getCommand(String commandName) {
        commandName = commandName.toUpperCase();
        CommandName enumName = CommandName.valueOf(commandName);
        return commands.get(enumName);
    }
}
