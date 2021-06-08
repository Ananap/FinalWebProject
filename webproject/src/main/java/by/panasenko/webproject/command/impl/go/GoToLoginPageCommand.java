package by.panasenko.webproject.command.impl.go;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.command.PagePath;
import by.panasenko.webproject.command.Router;
import by.panasenko.webproject.command.Router.RouterType;
import by.panasenko.webproject.util.RegexpPropertyUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToLoginPageCommand implements Command {
    private static final String ATTRIBUTE_ACTIVE_LOGIN = "activeLogin";
    private static final String ATTRIBUTE_REGEXP_USERNAME = "attribute_regexp_username";
    private static final String ATTRIBUTE_REGEXP_PASSWORD = "attribute_regexp_password";
    private static final String ATTRIBUTE_REGEXP_FIO = "attribute_regexp_fio";
    private static final String ATTRIBUTE_REGEXP_PHONE_NUMBER = "attribute_regexp_phone_number";
    private static final String ATTRIBUTE_REGEXP_EMAIL = "attribute_regexp_email";
    private static final String REGEXP_PROP_USERNAME = "regexp.username";
    private static final String REGEXP_PROP_PASSWORD = "regexp.password";
    private static final String REGEXP_PROP_FIO = "regexp.user_fio";
    private static final String REGEXP_PROP_PHONE_NUMBER = "regexp.phone_number";
    private static final String REGEXP_PROP_EMAIL = "regexp.email";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router;
        RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

        final String REGEXP_USERNAME = regexpPropertyUtil.getProperty(REGEXP_PROP_USERNAME);
        final String REGEXP_PASSWORD = regexpPropertyUtil.getProperty(REGEXP_PROP_PASSWORD);
        final String REGEXP_FIO = regexpPropertyUtil.getProperty(REGEXP_PROP_FIO);
        final String REGEXP_PHONE_NUMBER = regexpPropertyUtil.getProperty(REGEXP_PROP_PHONE_NUMBER);
        final String REGEXP_EMAIL = regexpPropertyUtil.getProperty(REGEXP_PROP_EMAIL);

        req.setAttribute(ATTRIBUTE_REGEXP_USERNAME, REGEXP_USERNAME);
        req.setAttribute(ATTRIBUTE_REGEXP_PASSWORD, REGEXP_PASSWORD);
        req.setAttribute(ATTRIBUTE_REGEXP_FIO, REGEXP_FIO);
        req.setAttribute(ATTRIBUTE_REGEXP_PHONE_NUMBER, REGEXP_PHONE_NUMBER);
        req.setAttribute(ATTRIBUTE_REGEXP_EMAIL, REGEXP_EMAIL);

        req.setAttribute(ATTRIBUTE_ACTIVE_LOGIN, true);
        router = new Router(PagePath.ACCOUNT_PAGE, RouterType.FORWARD);
        return router;
    }
}
