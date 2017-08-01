<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<!DOCTYPE html>

<html lang="en-US"
      class="csstransforms csstransforms3d csstransitions js_active  vc_desktop  vc_transform  vc_transform "
      id="ls-global">
<head>
    <title>Book lịch căn hộ - Duyệt Bàn Giao</title>
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
    <link rel="stylesheet" href="/rs/css/jquery-ui-1.10.1.css"  />
    <link rel="stylesheet" href="/rs/css/vigo.datepicker.css"  />
    <link rel="stylesheet" href="/rs/css/blch1.css"  />
    <script src="/rs/js/jquery.min.js"></script>
    <script src="/rs/js/jquery-ui.js"></script>
    <script src="/rs/bootstrap/js/bootstrap.min.js"></script>
    <script src="/rs/js/blch.js"></script>

    <script>
        $(function () {
        //csrf  setting
            //csrfSetting();

        //jquery func setting
        $('#datepicker').datepicker('hide');
        });

    	jQuery(document).ready(function($) {
    	var page = 1;
    	<c:if test="${page != null && limit != null}" >
    	    page = ${page};
            $("#rowlimit").val(${limit});
    	</c:if>
    	getCanHoBanGiaoAdmin(page);
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

        <div id="datepicker"></div>
        <table cellspacing='0' id ="canholist"  class="canho"> <!-- cellspacing='0' is important, must stay -->

        </table>

        <p/>
        <div class="pagingrow">
           <select id="rowlimit">
                       <option value="5">Hiển thị 5</option>
                       <option value="10">Hiển thị 10</option>
                       <option value="20">Hiển thị 20</option>
           </select>
            <div id="pagingDX">
            </div>
            <input type="hidden" id="currentpage" />
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