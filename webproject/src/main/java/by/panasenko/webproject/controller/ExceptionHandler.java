package by.panasenko.webproject.controller;

import by.panasenko.webproject.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandler extends HttpServlet {

    private static final String ERROR_PAGE_COMMAND = "go_to_error_page_command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommandProvider.getInstance().getCommand(ERROR_PAGE_COMMAND).execute(req, resp);
    }

}
