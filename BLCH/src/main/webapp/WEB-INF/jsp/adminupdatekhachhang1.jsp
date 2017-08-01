<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<!DOCTYPE html>

<html lang="en-US"
      class="csstransforms csstransforms3d csstransitions js_active  vc_desktop  vc_transform  vc_transform "
      id="ls-global">
<head>
    <title>Cập nhật khách hàng - Admin Home</title>
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
    <link rel="stylesheet"href="/rs/css/old/style.css" />
    <link rel="stylesheet"  href="/rs/css/old/style(1).css" />
    <link rel="stylesheet" href="/rs/css/blch1.css"  />
    <script src="/rs/js/angular.js"></script>
    <script src="/rs/js/blch1.js"></script>

</head>


<body   ng-app="myapp" ng-controller="adminupdatekhachhang"
        ng-init="idkh=${idkhachhang};"
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

        <div class="firstblock">

        <div class="container">

            <form name="updateform" class="well form-horizontal" id="contact_form">
            <fieldset>
            <legend ng-show="!showdata">Tài khoản không tồn tại !</legend>

            <div ng-show="showdata" id="formblock">

                 <!-- Form Name -->
                 <legend>Thông tin tài khoản !</legend>

                <!-- Text input-->

                <div class="form-group">
                    <label class="col-md-4 control-label">Tên đăng nhập</label>
                    <div class="col-md-4 inputGroupContainer">
                      <div class="input-group">
                          <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                          <input ng-if="insertflag"  name="username" class="form-control"  type="text" ng-model="khachhang.tendangnhap" ng-pattern="regex.username" required />
                          <input ng-if="!insertflag" readonly  name="username" ng-model="khachhang.tendangnhap" class="form-control"  type="text"  />
                      </div>
                    </div>
                </div>

                <!-- Text input-->

                <div class="form-group">
                  <label class="col-md-4 control-label" >Họ Tên</label>
                  <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input name="fullname" ng-model="khachhang.tenkhachhang" class="form-control"  type="text" required ng-minlength="6" ng-maxlength="30" >
                    </div>
                  </div>
                </div>

               <!-- Text input-->
              <div class="form-group">
                 <label class="col-md-4 control-label">Mật khẩu mới</label>
                 <div class="col-md-4 inputGroupContainer">
                   <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input  ng-if="insertflag"  ng-model="khachhang.newpass1" name="newpass1" type="password" class="form-control"  type="text" required ng-pattern="regex.password" >
                        <input  ng-if="!insertflag" ng-model="khachhang.newpass1" name="newpass1" type="password" class="form-control"  type="text" ng-pattern="regex.password" >

                   </div>
                 </div>
               </div>

              <!-- Text input-->
               <div class="form-group">
                  <label class="col-md-4 control-label">Nhập lại mật khẩu mới</label>
                  <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input ng-if="insertflag"  ng-model="khachhang.newpass2" name="newpass2" type="password" class="form-control"  type="text" type="text" required ng-pattern="regex.password" >
                            <input ng-if="!insertflag" ng-model="khachhang.newpass2" name="newpass2" type="password" class="form-control"  type="text" ng-pattern="regex.password" >
                        </div>
                  </div>
                </div>

                <!-- Text input-->
               <div class="form-group">
                  <label class="col-md-4 control-label">Địa chỉ mail</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                            <input ng-model="khachhang.email" name="email" class="form-control"  type="email" required>
                        </div>
                  </div>
                </div>


                <!-- Text input-->

                <div class="form-group">
                  <label class="col-md-4 control-label">Số điện thoại</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
                            <input ng-model="khachhang.dienthoai" name="phone" class="form-control" type="text" ng-minlength="6" ng-maxlength="20" >
                        </div>
                  </div>
                </div>


                <!-- Alert messages -->
                <div ng-show="successupdate" class="alert alert-success" role="alert" id="success_message" style="display: block;" >Cập nhật thành công !</div>
                <div ng-show="failureupdate" class="alert alert-danger" role="alert" style="display: block;" >{{errormsg}}</div>
                <div
                    class="alert alert-danger" role="alert" id="danger_message" style="display: block;">
                    <p ng-show="updateform.username.$invalid">Tên đăng nhập không hợp lệ.</p>
                    <p ng-show="updateform.fullname.$invalid">Tên khách hàng không hợp lệ.</p>
                    <p ng-show="updateform.newpass1.$invalid">Mật khẩu không hợp lệ.</p>
                    <p ng-if="updateform.newpass2.$invalid || (khachhang.newpass1 != khachhang.newpass2)">Mật khẩu nhập lại không hợp lệ.</p>
                    <p ng-show="updateform.email.$invalid">Email không hợp lệ.</p>
                    <p ng-show="updateform.phone.$invalid">Số điện thoại không hợp lệ.</p>
                </div>

                <!-- Button -->
                <div    ng-if="updateform.username.$valid && updateform.fullname.$valid && updateform.newpass1.$valid && updateform.newpass2.$valid && updateform.email.$valid && updateform.phone.$valid && (khachhang.newpass1 == khachhang.newpass2)  "
                        class="form-group">
                  <label class="col-md-4 control-label"></label>
                  <div class="col-md-4">
                    <span class="btn btn-warning"  ng-click="capnhattaikhoan()" >Cập nhật  <span class="glyphicon glyphicon-send" ></span></span>
                  </div>
                </div>
            </div>

        </fieldset>
        </form>
        </div>

        <p/>


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