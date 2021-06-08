package by.panasenko.webproject.command;

public final class PagePath {
    public static final String ABOUT_PAGE = "about_page.jsp";
    public static final String ACCOUNT_PAGE = "pages/my_account.jsp";
    public static final String PROFILE_PAGE = "pages/my_profile.jsp";
    public static final String ITEM_PAGE = "pages/item_page.jsp";
    public static final String ITEM_DETAIL_PAGE = "pages/item_detail_page.jsp";
    public static final String BASKET_PAGE = "pages/basket.jsp";
    public static final String ERROR_404_PAGE = "404.jsp";
    public static final String ERROR_PAGE = "error.jsp";

    public static final String GO_TO_LOGIN_PAGE = "Controller?command=go_to_login_page_command";
    public static final String GO_TO_PROFILE_PAGE = "Controller?command=go_to_profile_page_command";
    public static final String GO_TO_ITEM_DETAIL_COMMAND = "Controller?command=flower_detail_command";

    private PagePath() {
    }
}
