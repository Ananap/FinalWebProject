package by.panasenko.webproject.command;

import by.panasenko.webproject.command.impl.*;
import by.panasenko.webproject.command.impl.auth.impl.AddItemToBasketCommand;
import by.panasenko.webproject.command.impl.auth.impl.PersonalEditCommand;
import by.panasenko.webproject.command.impl.auth.impl.go.GoToBasketPageCommand;
import by.panasenko.webproject.command.impl.auth.impl.go.GoToProfilePageCommand;
import by.panasenko.webproject.command.impl.go.GoToAboutPageCommand;
import by.panasenko.webproject.command.impl.go.GoToErrorPageCommand;
import by.panasenko.webproject.command.impl.go.GoToItemPageCommand;
import by.panasenko.webproject.command.impl.go.GoToLoginPageCommand;

import java.util.EnumMap;

public class CommandProvider {
    private static CommandProvider instance;
    private final EnumMap<CommandType, Command> commands = new EnumMap(CommandType.class);

    public CommandProvider() {
        commands.put(CommandType.GO_TO_ABOUT_PAGE_COMMAND, new GoToAboutPageCommand());
        commands.put(CommandType.GO_TO_ERROR_PAGE_COMMAND, new GoToErrorPageCommand());
        commands.put(CommandType.GO_TO_LOGIN_PAGE_COMMAND, new GoToLoginPageCommand());
        commands.put(CommandType.GO_TO_ITEM_PAGE_COMMAND, new GoToItemPageCommand());
        commands.put(CommandType.GO_TO_BASKET_PAGE_COMMAND, new GoToBasketPageCommand());
        commands.put(CommandType.GO_TO_PROFILE_PAGE_COMMAND, new GoToProfilePageCommand());
        commands.put(CommandType.CHANGE_LOCALE_COMMAND, new ChangeLocaleCommand());
        commands.put(CommandType.SIGN_IN_COMMAND, new SignInCommand());
        commands.put(CommandType.SIGN_UP_COMMAND, new SignUpCommand());
        commands.put(CommandType.DEFAULT, new DefaultCommand());
        commands.put(CommandType.FORGET_PASSWORD_COMMAND, new ForgetPasswordCommand());
        commands.put(CommandType.LOG_OUT_COMMAND, new LogOutCommand());
        commands.put(CommandType.PERSONAL_EDIT_COMMAND, new PersonalEditCommand());
        commands.put(CommandType.FIND_PRODUCT_BY_CATEGORY_COMMAND, new FindProductByCategoryCommand());
        commands.put(CommandType.FLOWER_DETAIL_COMMAND, new FlowerDetailCommand());
        commands.put(CommandType.ADD_ITEM_TO_BASKET_COMMAND, new AddItemToBasketCommand());
    }

    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }

    public Command getCommand(String commandName) {
        if (commandName == null) {
            return commands.get(CommandType.DEFAULT);
        }
        CommandType commandType;
        try {
            commandType = CommandType.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = CommandType.DEFAULT;
        }
        return commands.get(commandType);
    }
}
