package by.panasenko.webproject.command;

import by.panasenko.webproject.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest req) throws ServiceException;
}
