package by.panasenko.webproject.command.impl;

import by.panasenko.webproject.command.Command;
import by.panasenko.webproject.controller.Servlet;
import by.panasenko.webproject.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import by.panasenko.webproject.service.impl.UserServiceImpl;
import by.panasenko.webproject.validator.ValidateData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AgeCriteriaCommand implements Command {
    static final Logger logger = LogManager.getLogger(Servlet.class);
    private static final String AGE_FROM = "ageFrom";
    private static final String AGE_TO = "ageTo";
    private static final String MAIN_PAGE = "/pages/main.jsp";
    private static final String ERROR_PAGE = "/pages/error.jsp";
    private UserServiceImpl service;

    public AgeCriteriaCommand() {
        service = UserServiceImpl.getInstance();
    }

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String ageFromValue = req.getParameter(AGE_FROM);
        String ageToValue = req.getParameter(AGE_TO);
        if (ValidateData.validateAge(ageFromValue) && ValidateData.validateAge(ageToValue)) {
            logger.info("Age criteria search");
            List<User> resultList = service.filterByAge(Integer.parseInt(ageFromValue), Integer.parseInt(ageToValue));
            req.setAttribute("userList", resultList);
            page = MAIN_PAGE;
        } else {
            logger.info("Wrong value of age " + ageFromValue + " " + ageToValue);
            req.setAttribute("wrongValue", "Wrong value for age");
            page = ERROR_PAGE;
        }
        return page;
    }
}
