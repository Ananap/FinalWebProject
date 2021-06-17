package by.panasenko.webproject.command;

public final class PagePath {
    public static final String ABOUT_PAGE = "about_page.jsp";
    public static final String ACCOUNT_PAGE = "pages/my_account.jsp";
    public static final String PROFILE_PAGE = "pages/user/my_profile.jsp";
    public static final String ITEM_PAGE = "pages/item_page.jsp";
    public static final String ITEM_DETAIL_PAGE = "pages/item_detail_page.jsp";
    public static final String BASKET_PAGE = "pages/user/basket.jsp";
    public static final String CHECKOUT_PAGE = "pages/user/checkout.jsp";
    public static final String ADD_ITEM_PAGE = "pages/admin/add_item.jsp";
    public static final String UPDATE_ITEM_PAGE = "pages/admin/update_item.jsp";
    public static final String FLOWER_LIST_PAGE = "pages/admin/flower_list.jsp";
    public static final String ORDER_ALL_INFO_PAGE = "pages/admin/order_all_info.jsp";
    public static final String ORDER_DETAIL_PAGE = "pages/admin/order_detail.jsp";
    public static final String ORDER_SUBMIT = "order_submitted.jsp";
    public static final String ERROR_404_PAGE = "404.jsp";
    public static final String ERROR_PAGE = "error.jsp";

    public static final String GO_TO_LOGIN_PAGE = "Controller?command=go_to_login_page_command";
    public static final String GO_TO_PROFILE_PAGE = "Controller?command=go_to_profile_page_command";
    public static final String GO_TO_ITEM_DETAIL = "Controller?command=go_to_flower_detail_page_command";
    public static final String GO_TO_BASKET_PAGE = "Controller?command=go_to_basket_page_command";
    public static final String GO_TO_CHECKOUT_PAGE = "Controller?command=go_to_check_out_page_command";
    public static final String GO_TO_FLOWER_LIST = "Controller?command=go_to_flower_list_page_command";
    public static final String GO_TO_ORDER_LIST = "Controller?command=go_to_orders_page_command";

    private PagePath() {
    }
}
