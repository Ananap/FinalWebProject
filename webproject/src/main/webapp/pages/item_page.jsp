<%--
  Created by IntelliJ IDEA.
  User: panat
  Date: 4 июн. 2021 г.
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="common.items" var="locale_common_items"/>
<fmt:message key="common.create.user" var="locale_create_user"/>
<fmt:message key="main.category" var="locale_main_category"/>
<fmt:message key="main.category.enter" var="locale_main_category_enter"/>
<fmt:message key="main.search" var="locale_main_search"/>
<fmt:message key="main.clear" var="locale_main_clear"/>
<fmt:message key="common.flower.detail" var="locale_flower_detail"/>


<!doctype html>
<html lang="en">
<body>
<jsp:include page="../header.jsp"/>
<div class="container">
    <div class="row" style="margin-bottom: -100px">
        <div class="col-xs-8">
            <h2 class="section-headline">
                <span>${locale_common_items}</span>
            </h2>
        </div>
        <div class="col-xs-4">
            <img src="static/image/logo1.jpg" class="img-responsive">
        </div>
    </div>
    <hr style="position: absolute; width: 75%; height: 6px; background-color: #1b6d85; z-index: -1; margin-top: -80px;"/>
    <img class="img-responsive" src="static/image/flower1.jpg" style="margin-top: -75px;">
    <div class="row" style="margin-top: 60px;">
        <div class="col-xs-2">
            <form action="Controller">
                <div>
                    <input type="hidden" name="command" value="find_product_by_category_command"/>
                    <div class="select-form">
                        <span>${locale_main_category}</span>
                        <select id="category" name="category"
                                class="form-control">
                            <c:if test="${flowerTypeSelected != null}">
                                <option selected disabled>${flowerTypeSelected.description}</option>
                            </c:if>
                            <c:if test="${flowerTypeSelected == null}">
                                <option selected disabled>${locale_main_category_enter}</option>
                            </c:if>
                            <c:forEach var="flowerType" items="${flowerTypeList}">
                                <option value="${flowerType.id}">${flowerType.description}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <input type="submit" value="${locale_main_search}"/>
                        &nbsp;
                        <input type="button" value="${locale_main_clear}" id="buttonClear"/>
                    </div>
                </div>
            </form>
        </div>
        <!--List of flowers-->
        <div class="col-xs-10">
            <c:if test="${emptyList}">
                <h5 style="font-style: italic">Ooops, no result is found. Try something else)</h5>
            </c:if>
            <table border="0" id="myTable">
                <tbody>
                <c:forEach var="flower" items="${flowerList}">
                    <c:if test="${flower != null}">
                        <tr class="flower-item">
                            <td>
                                <div class="row flower-item-custom">
                                    <div class="col-xs-3">
                                        <img class="img-responsive"
                                             src="static/image/flower/${flower.flowerImage}"/>
                                    </div>
                                    <div class="col-xs-9">
                                        <h4>${flower.name}</h4>
                                        <span style="font-size: x-large; color: darkorange">Br<span>${flower.price}</span></span>
                                        <p>${fn:substring(flower.description, 0, 500)}</p>
                                        <form action="Controller" method="post">
                                            <input type="hidden" name="command"
                                                   value="go_to_flower_detail_page_command"/>
                                            <input type="hidden" value="${flower.id}" name="flowerId">
                                            <button type="submit" class="btn btn-info">${locale_flower_detail}</button>
                                        </form>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('#buttonClear').click(function () {
            window.location = "Controller?command=go_to_item_page_command";
        })
    })
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>
