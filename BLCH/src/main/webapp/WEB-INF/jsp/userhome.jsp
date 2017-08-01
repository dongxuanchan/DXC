<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<!DOCTYPE html>

<html lang="en-US"
      class="csstransforms csstransforms3d csstransitions js_active  vc_desktop  vc_transform  vc_transform "
      id="ls-global">
<head>
    <title>Book lịch căn hộ - User Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="description"
          content="Khu đô thị Sala nằm trong lõi xanh của Khu đô thị mới Thủ Thiêm, được bao quanh bởi 128 ha Lâm viên sinh thái và sông Sài Gòn, Sala là khu đô thị cao cấp...">
    <meta name="keywords" content="khu do thi sala, khu đô thị sala, khu do thi moi sala, khu đô thị mới sala">

    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link rel="shortcut icon" href="/rs/img/sala-favicon.png">
    <link rel="stylesheet"href="/rs/css/old/style.css" />
    <link rel="stylesheet"  href="/rs/css/old/style(1).css" />
    <link rel="stylesheet" href="/rs/css/blch1.css"  />
    <link rel="stylesheet" href="/rs/css/jquery-ui-1.10.1.css"  />
    <link rel="stylesheet" href="/rs/css/vigo.datepicker.css"  />
    <script src="/rs/js/jquery-1.10.2.js"></script>
    <script src="/rs/js/jquery-ui.js"></script>
    <script src="/rs/js/blch.js"></script>

    <script>
    $(function () {
            //csrf  setting
            csrfSetting();

            //jquery func setting

            $('#datepicker').datepicker('hide');
            $("a.selectdate").click(function () {
                //$('#datepicker').datepicker('destroy');
                var idcanho = $(this).attr('id');
                var currentdate = $(this).attr('currentdate');
                /*if(currentdate!=null){
                    currentdate = $.datepicker.parseDate('dd/mm/yy', $(this).attr('currentdate'));
                }*/
                $('#datepicker').datepicker({
                    firstDay: 1,
                    //dateFormat: "dd/mm/yy",
                    onSelect: function () {
                        var cusdate = $(this).datepicker( 'getDate' );
                        var cusdatefm = $.datepicker.formatDate('dd/mm/yy',cusdate);
                        if(chonNgayTuDatePicker(cusdate, currentdate,5)==true){
                            chonNgayBanGiaoUser(idcanho, cusdatefm);
                        }
                    }
                })
            });
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
    <div class="page-slider clr"></div>
    <div id="site-header-sticky-wrapper" class="sticky-wrapper" >
        <div id="site-header" class="clr header-one fixed-scroll" role="banner">
            <div id="site-header-inner" class="container clr">
                <!-- option choose header two <span class="header-line"></span> -->
                <div id="site-logo" class="header-one-logo">
                    <a href="http://khudothisala.vn/" title="Khu đô thị Sala" rel="home">
                        <img src="/rs/img/logo-sala.png" alt="Khu đô thị Sala">
                    </a>
                </div>
                <!-- #site-logo -->
                <div id="site-navigation-wrap" class="clr navbar-style-one nav-custom-height">
                    <nav id="site-navigation" class="navigation main-navigation clr " role="navigation">
                        <div class="menu-menu-trang-chu-container">
                            <ul id="menu-menu-trang-chu" class="dropdown-menu sf-menu sf-js-enabled">
                                <li id="menu-item-1217"
                                    class="menu-item menu-item-type-custom menu-item-object-custom menu-item-1217"><a
                                        href="http://khudothisala.vn/#kdtsala">Giới Thiệu</a></li>
                                <li id="menu-item-642"
                                    class="menu-item menu-item-type-custom menu-item-object-custom menu-item-642"><a
                                        href="http://khudothisala.vn/#khudothisala">Khu Đô Thị Sala</a></li>
                                <li id="menu-item-643"
                                    class="menu-item menu-item-type-custom menu-item-object-custom menu-item-has-children dropdown menu-item-643">
                                    <a href="http://khudothisala.vn/#" class="sf-with-ul">Sản Phẩm</a>
                                    <ul class="sub-menu">
                                        <li id="menu-item-3997"
                                            class="menu-item menu-item-type-custom menu-item-object-custom menu-item-3997">
                                            <a href="http://khudothisala.vn/saroma-villa-khu-do-thi-sala/">Biệt thự</a>
                                        </li>
                                        <li id="menu-item-3998"
                                            class="menu-item menu-item-type-custom menu-item-object-custom menu-item-3998">
                                            <a href="http://khudothisala.vn/danh-sach-nha-pho-thuong-mai-khu-do-thi-sala/">Nhà
                                                phố</a></li>
                                        <li id="menu-item-3999"
                                            class="menu-item menu-item-type-custom menu-item-object-custom menu-item-3999">
                                            <a href="http://khudothisala.vn/can-ho-cao-cap-khu-do-thi-sala/">Căn hộ</a>
                                        </li>
                                    </ul>
                                </li>
                                <li id="menu-item-973"
                                    class="menu-item menu-item-type-custom menu-item-object-custom menu-item-973"><a
                                        href="http://khudothisala.vn/#chudautu">Chủ Đầu Tư</a></li>
                                <li id="menu-item-4006"
                                    class="menu-item menu-item-type-custom menu-item-object-custom current_page_item menu-item-home menu-item-has-children dropdown menu-item-4006">
                                    <a href="http://khudothisala.vn/#" class="sf-with-ul">Thư Viện</a>
                                    <ul class="sub-menu" style="display: none;">
                                        <li id="menu-item-4008"
                                            class="menu-item menu-item-type-post_type menu-item-object-page menu-item-4008">
                                            <a href="http://khudothisala.vn/hinh-anh/">Hình ảnh</a></li>
                                        <li id="menu-item-4003"
                                            class="menu-item menu-item-type-post_type menu-item-object-page menu-item-4003">
                                            <a href="http://khudothisala.vn/video-clips/">Video Clips</a></li>
                                    </ul>
                                </li>

                                <li id="menu-item-648"
                                    class="menu-item menu-item-type-custom menu-item-object-custom menu-item-648"><a
                                        href="http://khudothisala.vn/#lienhe">Liên Hệ</a></li>
                            </ul>
                        </div>
                    </nav>
                    <!-- #site-navigation -->
                </div>
                <!-- #site-navigation-wrap -->


                <div id="sidr-close"><a href="http://khudothisala.vn/#sidr-close" class="toggle-sidr-close"></a></div>
                <div id="mobile-menu" class="clr">
                    <a href="http://khudothisala.vn/#sidr" class="sidr-menu-toggle"><span class="fa fa-bars"></span></a>
                </div>
                <!-- #mobile-menu -->

            </div>
            <!-- #site-header-inner -->
        </div>
    </div>
    <!-- #header -->


    <div id="main" class="site-main clr">

        <div id="content-wrap" class="clr right-sidebar">

        <div class='logoutpanel'>
            <a href="javascript:logOut()">Đăng xuất</a>
            <a>${pageContext.request.userPrincipal.name}</a>
            <div style="clear:both;"></div>
        </div>

        <div class="firstblock">

       <div id="datepicker"></div>
       <table cellspacing='0' id ="canholist" class="canho"> <!-- cellspacing='0' is important, must stay -->
       <c:if test="${listcanho != null}" >
           <tr>
           <th>Tên Dự Án</th>
           <th>Mã Căn Hộ</th>
           <th>Tình Trạng Bàn Giao</th>
           <th>Ngày Bàn Giao</th>
           <th>Ngày Được Duyệt</th>
           </tr>
           <c:forEach var="canho" items="${listcanho}">
               <tr>
               <td>${canho[2]}</td>
               <td>${canho[1]}</td>
               <c:choose>
                   <c:when test = "${canho[5] == true}">
                        <td>Ðã sẵn sàng</td>
                        <c:if test="${canho[6] == null}" >
                            <td><a id='${canho[0]}' class="selectdate">Chọn ngày</a></td>
                            <td>--</td>
                        </c:if>
                        <c:if test="${canho[6] != null}" >
                            <td><span class="highlight">${canho[6]}</span> - <a id='${canho[0]}' currentdate="${canho[6]}" class="selectdate">Chọn ngày khác</a></td>
                            <c:if test="${canho[7] == null}" >
                                <td>Đang duyệt</td>
                            </c:if>
                            <c:if test="${canho[7] != null}" >
                                <td><span class="highlight">${canho[7]}</span></td>
                            </c:if>
                        </c:if>
                   </c:when>
                   <c:otherwise>
                       <td>Chưa sẵn sàng</td>
                       <td>--</td>
                       <td>--</td>
                   </c:otherwise>
               </c:choose>
               </tr>
          </c:forEach>
       </c:if>
       </table>

        </div>
        <!-- end first block-->

        <div class="secondblock">

        </div>
        <!-- end second block-->



        </div>
        <!-- #content-wrap -->

    </div>
    <!-- #main-content -->

    <footer id="footer" class="site-footer">
        <div id="footer-inner" class="clr">
            <div id="footer-row" class="wpex-row clr">

                <div id="footer-widgets" class="clr ">

                    <!-- FOOTER BOX 1 -->
                    <div class="span-4 footer-box span_1_of_2 col col-1">
                        <div class="footer-widget widget_text clr">
                            <div class="textwidget">
                                <div class="logofooter">
                                    <img class="alignright wp-image-1349 size-full" width="120" height="100"
                                         alt="logo-non-slogan" src="/rs/img/logo-non-slogan.png"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- .footer-one-box -->

                    <!-- FOOTER BOX 2 -->
                    <div class="span-8 footer-box span_1_of_2 col col-2">
                        <div class="footer-widget widget_text clr">
                            <div class="widget-title">CÔNG TY CỔ PHẦN ĐẠI QUANG MINH</div>
                            <div class="textwidget"><p style="color: #fff;">
                                10 Mai Chí Thọ, Đô thị mới Thủ Thiêm, Quận 2, Thành Phố Hồ Chí Minh<br>
                                Điện thoại: (+84-8) 3742 5566<br>
                                Fax: (+84-8) 3742 5578<br>
                                Đón khách tham quan dự án từ 8:00 - 21:00 hàng ngày
                            </p>

                                <p></p>

                                <p><strong><br>
                                    <strong><span style="color: #94153d;"><span
                                            style="font-size: 13px; font-family: arial,helvetica,sans-serif;">ĐĂNG KÝ MUA:</span><span
                                            style="font-size: 12px; font-family: arial,helvetica,sans-serif;"> Biệt thự-Nhà phố</span> <span
                                            style="font-size: 15px;"> <span
                                            style="font-family: arial,helvetica,sans-serif;"><span
                                            style="color: #ffffff;"> 0911 52 36 36 </span></span></span><span
                                            style="color: #d9a83d; "><span
                                            style="font-size: 16px; font-family: arial,helvetica,sans-serif;">|</span> <span
                                            style="color: #94153d;"><span
                                            style="font-size: 12px; font-family: arial,helvetica,sans-serif;"> Căn hộ </span> <span
                                            style="font-size: 15px;"> <span
                                            style="font-family: arial,helvetica,sans-serif;"><span
                                            style="color: #ffffff;">0911 52 15 15</span></span></span></span></span></span></strong></strong>
                                </p><strong><strong>
                                    <p><strong><span style="color: #94153d;"><span
                                            style="font-size: 13px; font-family: arial,helvetica,sans-serif;">ĐĂNG KÝ THUÊ:</span><span
                                            style="font-size: 12px; font-family: arial,helvetica,sans-serif;"> Thương mại</span> <span
                                            style="font-size: 15px;"> <span
                                            style="font-family: arial,helvetica,sans-serif;"><span
                                            style="color: #ffffff;"> 0919 79 89 01 </span></span></span><span
                                            style="color: #d9a83d; "><span
                                            style="font-size: 16px; font-family: arial,helvetica,sans-serif;">|</span> <span
                                            style="color: #94153d;"><span
                                            style="font-size: 12px; font-family: arial,helvetica,sans-serif;"> Căn hộ ở </span> <span
                                            style="font-size: 15px;"> <span
                                            style="font-family: arial,helvetica,sans-serif;"><span
                                            style="color: #ffffff;">0911 31 90 90</span></span></span></span></span></span></strong>
                                    </p><strong>
                                    <p><strong><span style="color: #94153d;"><span
                                            style="font-size: 13px; font-family: arial,helvetica,sans-serif;">HOTLINE:</span><span
                                            style="font-size: 16px;"> <span
                                            style="font-family: arial,helvetica,sans-serif;"><span
                                            style="color: #ffffff;">+848 3742 5579</span></span>
											<br>
										<strong><span style="color: #94153d;"><span
											style="font-size: 13px; font-family: arial,helvetica,sans-serif;">EMAIL:</span><span
											style="font-size: 12px;"> <span style="font-family: arial,helvetica,sans-serif;"><span style="color: #ffffff;">Chamsockhachhang@dqmcorp.vn</span></span></span></span></strong></span></span></strong>
                                    </p><strong><strong>
                                    <ul class="socialfooter">
                                        <li>
                                            <a target="_blank" href="https://www.facebook.com/SalaThuThiem"><img
                                                    src="/rs/img/facebook.png">
                                                <noscript>&lt;img
                                                    src="http://khudothisala.vn/wp-content/themes/websala/iconsocial/facebook.png"&gt;</noscript>
                                            </a>
                                        </li>
                                        <li>
                                            <a target="_blank" href="https://twitter.com/SalaThuThiem"><img
                                                    src="/rs/img/twitter.png">
                                                <noscript>&lt;img
                                                    src="http://khudothisala.vn/wp-content/themes/websala/iconsocial/twitter.png"&gt;</noscript>
                                            </a>
                                        </li>
                                        <li>
                                            <a target="_blank" href="https://google.com/+KhudothisalaVnPage"><img
                                                    src="/rs/img/googleplus.png">
                                                <noscript>&lt;img
                                                    src="http://khudothisala.vn/wp-content/themes/websala/iconsocial/googleplus.png"&gt;</noscript>
                                            </a>
                                        </li>
                                        <li>
                                            <a target="_blank" href="https://www.linkedin.com/in/SalaThuThiem"><img
                                                    src="/rs/img/linkedin.png">
                                                <noscript>&lt;img
                                                    src="http://khudothisala.vn/wp-content/themes/websala/iconsocial/linkedin.png"&gt;</noscript>
                                            </a>
                                        </li>
                                        <li>
                                            <a target="_blank" href="https://youtube.com/c/KhudothisalaVnPage"><img
                                                    src="/rs/img/youtube.png">
                                                <noscript>&lt;img
                                                    src="http://khudothisala.vn/wp-content/themes/websala/iconsocial/youtube.png"&gt;</noscript>
                                            </a>
                                        </li>
                                        <li>
                                            <a target="_blank" href="http://instagram.com/SalaThuThiem"><img
                                                    src="/rs/img/instagram.png"></a>
                                        </li>
                                        <li>
                                            <a target="_blank" href="https://www.pinterest.com/SalaThuThiem"><img
                                                    src="/rs/img/pinterest.png">
                                                <noscript>&lt;img
                                                    src="http://khudothisala.vn/wp-content/themes/websala/iconsocial/pinterest.png"&gt;</noscript>
                                            </a>
                                        </li>
                                    </ul>
                                    <div class="copyright">Copyright © 2014 DaiQuangMinh Corp. All rights reserved</div>
                                </strong></strong></strong></strong></strong></div>
                            <strong><strong><strong>
                            </strong></strong></strong></div>
                        <strong><strong><strong> </strong></strong></strong></div>
                    <!-- .footer-one-box --><strong><strong><strong>


                </strong></strong></strong></div>
                <!-- #footer-widgets --><strong><strong><strong>

            </strong></strong></strong></div>
            <!-- .wpex-row --><strong><strong><strong>
        </strong></strong></strong></div>
        <!-- #footer-widgets --><strong><strong><strong>
    </strong></strong></strong></footer>
    <!-- #footer -->

    <strong><strong><strong>

    </strong></strong></strong></div>
<!-- #wrap -->


</body>
</html>