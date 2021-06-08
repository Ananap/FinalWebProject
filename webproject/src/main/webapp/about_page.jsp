<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="common.index.image1" var="locale_image1"/>
<fmt:message key="common.index.image2" var="locale_image2"/>
<fmt:message key="common.index.image3" var="locale_image3"/>

<!doctype html>
<html lang="en">
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="row">
        <!-- Carousel -->
        <div class="col-xs-8">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="carousel slide" id="slider" data-ride="carousel">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li class="active" data-slide-to="0" data-target="#slider"></li>
                            <li data-slide-to="1" data-target="#slider"></li>
                            <li data-slide-to="2" data-target="#slider"></li>
                        </ol>
                        <!-- Wrapper for slides -->
                        <div class="carousel-inner">
                            <div class="item active">
                                <img src="static/image/im.jpg" alt="...">
                                <div class="carousel-caption">
                                    <h3 style="color: darkblue">${locale_image1}</h3>
                                </div>
                            </div>
                            <div class="item">
                                <img src="static/image/tulpany.jpg" alt="...">
                                <div class="carousel-caption">
                                    <h3 style="color: darkblue"><span
                                            style="background-color: white">${locale_image2}</span></h3>
                                </div>
                            </div>
                            <div class="item">
                                <img src="static/image/im2.jpg" alt="...">
                                <div class="carousel-caption">
                                    <h3 style="color: darkblue"><span
                                            style="background-color: white">${locale_image3}</span></h3>
                                </div>
                            </div>
                        </div>

                        <!-- Controls -->
                        <a class="left carousel-control" href="#slider" data-slide="prev" role="button">
                            <span class="icon-prev"></span>
                        </a>
                        <a class="right carousel-control" href="#slider" data-slide="next" role="button">
                            <span class="icon-next"></span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-4">
            <img src="static/image/logo1.jpg" class="img-responsive"/>
        </div>
    </div>
    <hr/>
    <div class="row">
        <div class="col-xs-4">
            <div class="panel panel-default">
                <div class="panel-body">
                    <img class="img-responsive" src="static/image/cvetok1.jpg">
                </div>
            </div>
        </div>
        <div class="col-xs-4">
            <div class="panel panel-default">
                <div class="panel-body">
                    <img class="img-responsive" src="static/image/cvetok2.png">
                </div>
            </div>
        </div>
        <div class="col-xs-4">
            <div class="panel panel-default">
                <div class="panel-body">
                    <img class="img-responsive" src="static/image/cvetok3.jpg">
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
</html>