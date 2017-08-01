<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<!DOCTYPE html>

<html lang="en-US"
      class="csstransforms csstransforms3d csstransitions js_active  vc_desktop  vc_transform  vc_transform "
      id="ls-global">
<head>
    <title>Quản lý khách hàng</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="description"
          content="Khu đô thị Sala nằm trong lõi xanh của Khu đô thị mới Thủ Thiêm, được bao quanh bởi 128 ha Lâm viên sinh thái và sông Sài Gòn, Sala là khu đô thị cao cấp...">
    <meta name="keywords" content="khu do thi sala, khu đô thị sala, khu do thi moi sala, khu đô thị mới sala">

    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link rel="shortcut icon" href="/rs/img/sala-favicon.png">

    <link rel="stylesheet" href="/rs/bootstrap/css/bootstrap.css"  />
    <link rel="stylesheet" href="/rs/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet"href="/rs/css/old/style.css" />
    <link rel="stylesheet"  href="/rs/css/old/style(1).css" />
    <link rel="stylesheet" href="/rs/css/blch1.css"  />
    <script src="/rs/js/angular.js"></script>
    <script src="/rs/js/blch1.js"></script>


</head>


<body ng-app="myapp" ng-controller="adminkhachhang"
      ng-init="limitforonload=${limit==null ? 5:limit}; pageforonload=${page==null ? 1:page};"
 class="home page page-id-704 page-template page-template-templateshome-php wpex-theme wpex-responsive theme-base full-width-main-layout no-header-margin has-breadcrumbs sidebar-widget-icons wpb-js-composer js-comp-ver-4.2.3 vc_responsive"
      style="cursor: default;">

<c:url value="/logout" var="logoutUrl" />
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}"
        value="${_csrf.token}" />
</form>

<div id="wrap" class="clr">
    <%@ include file="./temp/top.jsp" %>


    <div id="main" class="site-main clr">

        <div id="content-wrap" class="clr right-sidebar">

        <div class='logoutpanel'>
            <a href="javascript:logOut()">Đăng xuất</a>
            <a>${pageContext.request.userPrincipal.name}</a>
            <div style="clear:both;"></div>
        </div>

        <div class="firstblock" ng-show="showdata" >
        <!--
        <div class="adduser">
            <a href="/admin/newuser" class="btn btn-info" >Thêm mới khách hàng  <span class="glyphicon glyphicon-plus-sign"></span></a>
        </div>
        -->
        <table cellspacing='0' id ="khachhanglist"  class="canho"> <!-- cellspacing='0' is important, must stay -->
            <tr>
                <th ng-click="sortkhachhang('tenkhachhang')" style="cursor: pointer;">Tên Khách Hàng</th>
                <th ng-click="sortkhachhang('tendangnhap')" style="cursor: pointer;">Tên Đăng Nhập</th>
                <th>Email</th>
                <th>Điện Thoại</th>
                <th>Chỉnh Sửa</th>
                <th ng-click="sortkhachhang('tinhtrang')" style="cursor: pointer;">Tình Trạng</th>
            </tr>
            <tr ng-repeat="x in khachhang ">
                <td>{{x[2]}}</td>
                <td>{{x[1]}}</td>
                <td>{{x[3]}}</td>
                <td>{{x[4]}}</td>
                <td><a class='glyphicon glyphicon-edit' title='Sửa thông tin khách hàng.' target='_blank' href={{"/admin/user2/"+x[0]}}></a></td>
                <td ng-if="x[5]==null"></td>
                <td ng-if="x[5]==true"><a ng-click="khoamotaikhoan(x[1],false)" class='fa fa-unlock' title='Khóa tài khoản.'></a></td>
                <td ng-if="x[5]==false"><a ng-click="khoamotaikhoan(x[1],true)" class='fa fa-lock' title='Mở khóa tài khoản.'></a></td>
            </tr>
        </table>

        <p/>
        <div class="pagingrow" ng-controller="paging" >
           <select ng-model="rowlimit" ng-options="x.value as x.name for x in rowlimitvalues" ng-init="rowlimit=${limit==null ? 5:limit}" ng-change="limitchange()" id="rowlimit">
           </select>
            <div id="pagingDX">
                <ul class='paginate pag2 clearfix'>
                    <li ng-if="currentpage>1"><a ng-click="page(currentpage-1)">prev</a></li>
                    <li ng-if="currentpage>4"><a ng-click="page(1)">1</a></li>
                    <li ng-if="currentpage>4" class='pagingdot'>...</li>
                    <li ng-if="currentpage>4"><a ng-click="page(currentpage-1)">{{currentpage-1}}</a></li>
                    <li ng-if="currentpage<=4 && currentpage>1" ng-repeat="n in prevarray"><a ng-click="page(n)">{{n}}</a></li>
                    <li class='current'  id='currentpaging' >{{currentpage}}</li>
                    <li ng-if="currentpage<lastpage-3"><a ng-click="page(currentpage+1)">{{currentpage+1}}</a></li>
                    <li ng-if="currentpage<lastpage-3" class='pagingdot'>...</li>
                    <li ng-if="currentpage<lastpage-3"><a ng-click="page(lastpage)">{{lastpage}}</a></li>
                    <li ng-if="currentpage>=lastpage-3 && currentpage<lastpage" ng-repeat="m in nextarray"><a ng-click="page(m)">{{m}}</a></li>
                    <li ng-if="currentpage<lastpage"><a ng-click="page(currentpage+1)">next</a></li>
                </ul>
            </div>
        </div>

        </div>
        <!-- end first block-->



        </div>
        <!-- #content-wrap -->

    </div>
    <!-- #main-content -->

    <%@ include file="./temp/footer.jsp" %>

    </div>
<!-- #wrap -->


</body>
</html>