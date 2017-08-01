<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<!DOCTYPE html>

<html lang="en-US"
      class="csstransforms csstransforms3d csstransitions js_active  vc_desktop  vc_transform  vc_transform "
      id="ls-global">
<head>
    <title>Tạo mới khách hàng - Admin Home</title>
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
    <script src="/rs/js/jquery.min.js"></script>
    <script src="/rs/bootstrap/js/bootstrap.min.js"></script>
    <script src="/rs/js/blch.js"></script>

    <script>
        $(function () {
            //csrf  setting
            //csrfSetting();
        });
    </script>

</head>


<body class="home page page-id-704 page-template page-template-templateshome-php wpex-theme wpex-responsive theme-base full-width-main-layout no-header-margin has-breadcrumbs sidebar-widget-icons wpb-js-composer js-comp-ver-4.2.3 vc_responsive"
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


            <form class="well form-horizontal" id="contact_form">
            <fieldset>

             <!-- Form Name -->
             <legend>Thông tin tài khoản !</legend>

            <!-- Text input-->

            <div class="form-group">
              <label class="col-md-4 control-label">Tên đăng nhập</label>
              <div class="col-md-4 inputGroupContainer">
              <div class="input-group">
              <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
              <input  name="username"  class="form-control"  type="text">
                </div>
              </div>
            </div>

            <!-- Text input-->

            <div class="form-group">
              <label class="col-md-4 control-label" >Họ Tên</label>
                <div class="col-md-4 inputGroupContainer">
                <div class="input-group">
              <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
              <input name="fullname" class="form-control"  type="text">
                </div>
              </div>
            </div>

           <!-- Text input-->
          <div class="form-group">
             <label class="col-md-4 control-label">Mật khẩu</label>
               <div class="col-md-4 inputGroupContainer">
               <div class="input-group">
                   <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
             <input name="newpass1" type="password" class="form-control"  type="text">
               </div>
             </div>
           </div>

          <!-- Text input-->
           <div class="form-group">
              <label class="col-md-4 control-label">Nhập lại mật khẩu</label>
                <div class="col-md-4 inputGroupContainer">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
              <input name="newpass2" type="password" class="form-control"  type="text">
                </div>
              </div>
            </div>

            <!-- Text input-->
           <div class="form-group">
              <label class="col-md-4 control-label">Địa chỉ mail</label>
                <div class="col-md-4 inputGroupContainer">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
              <input name="email" class="form-control"  type="text">
                </div>
              </div>
            </div>


            <!-- Text input-->

            <div class="form-group">
              <label class="col-md-4 control-label">Số điện thoại</label>
                <div class="col-md-4 inputGroupContainer">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
              <input name="phone" class="form-control" type="text">
                </div>
              </div>
            </div>


            <!-- Alert messages -->
            <div class="alert alert-success" role="alert" id="success_message">Thêm mới khách hàng thành công !</div>
            <div class="alert alert-danger" role="alert" id="danger_message"></div>

            <!-- Button -->
            <div class="form-group">
              <label class="col-md-4 control-label"></label>
              <div class="col-md-4">
                <span class="btn btn-warning" onClick="taomoiKhachhang()" >Tạo mới  <span class="glyphicon glyphicon-send"></span></span>
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