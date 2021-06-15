<%--
  Created by IntelliJ IDEA.
  User: panat
  Date: 12 июн. 2021 г.
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="main.add.flower" var="locale_add_flower"/>
<fmt:message key="admin.flower.name" var="locale_flower_name"/>
<fmt:message key="main.name" var="locale_main_name"/>
<fmt:message key="main.description" var="locale_main_description"/>
<fmt:message key="admin.description.item" var="locale_description_item"/>
<fmt:message key="main.category" var="locale_main_category"/>
<fmt:message key="main.category.enter" var="locale_category_enter"/>
<fmt:message key="main.soil" var="locale_main_soil"/>
<fmt:message key="main.soil.enter" var="locale_soil_enter"/>
<fmt:message key="admin.flower.watering" var="locale_flower_watering"/>
<fmt:message key="admin.flower.amount" var="locale_flower_amount"/>
<fmt:message key="admin.flower.country" var="locale_flower_country"/>
<fmt:message key="admin.country.name" var="locale_country_name"/>
<fmt:message key="admin.light.name" var="locale_light_name"/>
<fmt:message key="admin.light.select" var="locale_light_select"/>
<fmt:message key="admin.light.love" var="locale_light_love"/>
<fmt:message key="admin.dark.love" var="locale_dark_love"/>
<fmt:message key="admin.flower.price" var="locale_flower_price"/>
<fmt:message key="admin.price.name" var="locale_price_name"/>
<fmt:message key="admin.storage.name" var="locale_storage_name"/>
<fmt:message key="admin.storage.flower" var="locale_storage_flower"/>
<fmt:message key="admin.flower.image" var="locale_flower_image"/>
<fmt:message key="main.add.item" var="locale_add_item"/>
<fmt:message key="main.cancel" var="locale_main_cancel"/>

<fmt:message key="admin.soil.podzolic" var="locale_soil_podzolic"/>
<fmt:message key="admin.soil.sodpodzolic" var="locale_soil_sodpodzolic"/>
<fmt:message key="admin.soil.unpaved" var="locale_soil_unpaved"/>

<!doctype html>
<html>
<body>
<jsp:include page="../../header.jsp"/>
<div class="container">
    <div class="row">
        <c:if test="${wrongInput}">
            <div class="alert alert-danger">
                Oooops, some fields have incorrect input.
                Remember: watering, price and count in storage should have positive amount
            </div>
        </c:if>
        <form class="form-horizontal" action="Controller" method="post" enctype="multipart/form-data">
            <fieldset>
                <input type="hidden" name="command" value="add_item_command"/>
                <legend class="center-block">${locale_add_flower}</legend>
                <!--Name of Item-->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="name">${locale_flower_name}</label>
                    <div class="col-md-8">
                        <input required type="text" class="form-control" id="name" max="50"
                               name="name" placeholder="${locale_main_name}" pattern="${attribute_regexp_name}"/>
                        <span class="help-block">${locale_main_name}</span>
                    </div>
                </div>
                <!--Description-->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="description">${locale_main_description}</label>
                    <div class="col-md-8">
                    <textarea required rows="5" class="form-control"
                              id="description" name="description" maxlength="700" minlength="5"
                              placeholder="${locale_main_description}"></textarea>
                        <span class="help-block">${locale_description_item}</span>
                    </div>
                </div>
                <!--Category-->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="category">${locale_main_category}</label>
                    <div class="col-md-8">
                        <select required id="category" name="category"
                                class="form-control">
                            <option value="" selected="selected" disabled="disabled">${locale_category_enter}</option>
                            <c:forEach var="flowerType" items="${flowerTypeList}">
                                <option value="${flowerType.id}">${flowerType.description}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <!--Soil-->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="soil">${locale_main_soil}</label>
                    <div class="col-md-8">
                        <select required id="soil" name="soil"
                                class="form-control">
                            <option value="" selected disabled="disabled">${locale_soil_enter}</option>
                            <option value="PODZOLIC">${locale_soil_podzolic}</option>
                            <option value="SODPODZOLIC">${locale_soil_sodpodzolic}</option>
                            <option value="UNPAVED">${locale_soil_unpaved}</option>
                        </select>
                    </div>
                </div>
                <!--Watering-->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="watering">${locale_flower_watering}</label>
                    <div class="col-md-8">
                        <input required type="text" min="0"
                               class="form-control" id="watering" name="watering"
                               placeholder="${locale_flower_amount}" pattern="${attribute_regexp_water}"/>
                        <span class="help-block">${locale_flower_amount}</span>
                    </div>
                </div>
                <!--Country-->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="country">${locale_flower_country}</label>
                    <div class="col-md-8">
                        <input required type="text" class="form-control" id="country"
                               name="country" placeholder="${locale_country_name}"/>
                        <span class="help-block">${locale_flower_country}</span>
                    </div>
                </div>
                <!--Light-->
                <div class="form-group">
                    <label class="col-md-2 control-label">${locale_light_name}</label>
                    <div class="col-md-8">
                        <div>
                            <p><b>${locale_light_select}</b></p>
                            <p><input name="light" type="radio" value="true"/>${locale_light_love}</p>
                            <p><input name="light" type="radio" value="false"/>${locale_dark_love}</p>
                        </div>
                    </div>
                </div>
                <!--Price-->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="price">${locale_flower_price}</label>
                    <div class="col-md-8">
                        <div class="input-group">
                            <span class="input-group-addon">Br</span>
                            <input required type="text" step="0.1" min="1"
                                   class="form-control" id="price" name="price"
                                   placeholder="${locale_price_name}" pattern="${attribute_regexp_price}"/>
                        </div>
                        <span class="help-block">${locale_flower_price}</span>
                    </div>
                </div>
                <!--In storage-->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="count">${locale_storage_name}</label>
                    <div class="col-md-8">
                        <input required type="text" min="1" max="99"
                               class="form-control" id="count" name="count"
                               placeholder="${locale_storage_flower}" pattern="${attribute_regexp_count}"/>
                        <span class="help-block">${locale_storage_flower}</span>
                    </div>
                </div>
                <!--Upload image-->
                <div class="form-group">
                    <label class="col-md-2 control-label" for="image">${locale_flower_image}</label>
                    <div class="col-md-8">
                        <input id="image" type="file" name="image"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-2"></div>
                    <div class="col-md-8">
                        <button type="submit" class="btn btn-success">${locale_add_item}</button>
                        <a class="btn btn-danger"
                           href="Controller?command=go_to_item_page_command">${locale_main_cancel}</a>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>
<jsp:include page="../../footer.jsp"/>
</body>
</html>
