package by.panasenko.webproject.command;

import by.panasenko.webproject.command.impl.UserByNameCommand;
import by.panasenko.webproject.command.impl.UserListCommand;

import java.util.EnumMap;

public class CommandProvider {
    private static CommandProvider instance;
    private final EnumMap<CommandName, Command> commands = new EnumMap(CommandName.class);

    public CommandProvider() {
        commands.put(CommandName.USER_BY_NAME_COMMAND, new UserByNameCommand());
        commands.put(CommandName.USER_LIST_COMMAND, new UserListCommand());
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
