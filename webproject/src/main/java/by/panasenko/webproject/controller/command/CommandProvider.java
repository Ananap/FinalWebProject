package by.panasenko.webproject.controller.command;

import by.panasenko.webproject.controller.command.impl.ChangeLocaleCommand;
import by.panasenko.webproject.controller.command.impl.SignInCommand;
import by.panasenko.webproject.controller.command.impl.go.GoToAboutPageCommand;
import by.panasenko.webproject.controller.command.impl.go.GoToErrorPageCommand;
import by.panasenko.webproject.controller.command.impl.go.GoToLoginPageCommand;
import by.panasenko.webproject.controller.command.impl.go.GoToSuccessPageCommand;

import java.util.EnumMap;

public class CommandProvider {
    private static CommandProvider instance;
    private final EnumMap<CommandName, Command> commands = new EnumMap(CommandName.class);

    public CommandProvider() {
        commands.put(CommandName.GO_TO_ABOUT_PAGE_COMMAND, new GoToAboutPageCommand());
        commands.put(CommandName.GO_TO_ERROR_PAGE_COMMAND, new GoToErrorPageCommand());
        commands.put(CommandName.GO_TO_LOGIN_PAGE_COMMAND, new GoToLoginPageCommand());
        commands.put(CommandName.CHANGE_LOCALE_COMMAND, new ChangeLocaleCommand());
        commands.put(CommandName.SIGN_IN_COMMAND, new SignInCommand());
        commands.put(CommandName.GO_TO_SUCCESS_PAGE_COMMAND, new GoToSuccessPageCommand());
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
