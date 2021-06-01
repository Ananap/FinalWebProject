package by.panasenko.webproject.controller.command.impl;

import by.panasenko.webproject.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutCommand implements Command {

    private static final String GO_TO_ABOUT_PAGE_COMMAND_URL = "Controller?command=go_to_about_page_command";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(GO_TO_ABOUT_PAGE_COMMAND_URL);
    }
}
