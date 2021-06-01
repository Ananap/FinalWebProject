package by.panasenko.webproject.controller.command.impl.go;

import by.panasenko.webproject.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToSuccessPageCommand implements Command {
    private static final String SUCCESS_PAGE_URL = "pages/my_profile.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(SUCCESS_PAGE_URL).forward(req, resp);
    }
}

