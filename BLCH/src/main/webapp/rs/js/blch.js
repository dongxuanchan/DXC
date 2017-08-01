function logOut() {
    document.getElementById("logoutForm").submit();
}

function csrfSetting(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

function getCanHoAdminHome(page) {
		var search = {}
		search["page"] = page;
		search["numb"] = $("#rowlimit").val();

		$.ajax({
			type : "POST",
			url : "/admin/canho",
			data : search,
			timeout : 100000,
			success : function(data) {
			    //console.log(data);
			    if(data['data'].length==0){
                 //window.location.href = "/";
                 //hide the paging select box...
                 return;
			    }
				$("input[id=currentpage]").val(data['currentpage']);
				drawCanhoTable(data['data']);
				drawPagination("pagingDX","paginate pag2 clearfix",data['currentpage'],data['lastpage'],null,null,getCanHoAdminHome);
			},
			error : function(e) {
				//console.log("ERROR: ", e);
			},
			done : function(e) {
				//console.log("DONE");
			}
		});
}

function drawCanhoTable(canholist){
 var table = $("#canholist");
 table.html("");
 var tr = $("<tr />").appendTo(table);
 tr.append($("<th />").html("Tên Dự Án"));
 tr.append($("<th />").html("Mã Sản Phẩm"));
 tr.append($("<th />").html("Tên Khách Hàng"));
 tr.append($("<th />").html("Tình Trạng Căn Hộ"));
 tr.append($("<th />").html("Biên Bản Nghiệm Thu"));
 tr.append($("<th />").html("Tình Trạng Bàn Giao"));
 tr.append($("<th />").html("Thanh Toán"));
 for(var i=0;i<canholist.length;i++) {
   var tr = $("<tr />");
   tr.append($("<td />").html(canholist[i][2]['tenduan']));
   tr.append($("<td />").html(canholist[i][2]['macanho']));
   tr.append($("<td />").html(canholist[i][1]));
   tr.append($("<td />").html(canholist[i][2]['tinhtrang']));
   tr.append($("<td />").html(canholist[i][2]['filedinhkem']));
   if(canholist[i][2]['bangiao']==true){
    tr.append($("<td />").append($("<a />").addClass("glyphicon glyphicon-ok")));
   }else{
    tr.append($("<td />").append($("<a />").addClass("glyphicon glyphicon-remove").attr("title","Duyệt bàn giao").attr("id",canholist[i][2]['id'])));
   }
   //tr.append($("<td />").html(canholist[i][2]['bangiao']));
   tr.append($("<td />").append($("<a />").attr("href","#thanhtoan").attr("id",canholist[i][0]+"/"+canholist[i][2]['id']).html("chi tiết").bind('click',getChitietThanhtoan)));
   if(i%2!=0){
    tr.addClass("even");
   }
   tr.appendTo(table);
 }

 //addClickFuncOn Duyệt bàn giao icon
  $("a.glyphicon-remove").click(function(){
      //console.log($(this).attr("id"));
      var tdList = $(this).parent().parent().find("td");
      var tencanho = tdList.eq(0).html() + " - " + tdList.eq(1).html();
      //console.log(tencanho);
      if (confirm(tencanho + " sẽ được chuyển sang trạng thái sẵn sàng bàn giao. \nBạn có chắc ?") == true) {
          updateTrangthaibangiaoCanho($(this).attr("id"), tencanho);
      }
   });
}

function updateTrangthaibangiaoCanho(idcanho, tencanho){
    var paras = {}
    paras["idcanho"] = idcanho;
    $.ajax({
    			type : "POST",
    			url : "/admin/duyetbangiao",
    			data : paras,
    			timeout : 100000,
    			success : function(data) {
    			    //console.log(data);
    			    if(data==true){
    			        alert(tencanho+ " đã sẵn sàng bàn giao !");
    				    window.location.href = "/admin/home/limit/"+$("#rowlimit").val()+"/page/"+$("input[id=currentpage]").val();
    				}else{
    				    alert("Có lỗi xảy ra !");
    				}
    			},
    			error : function(e) {
    				//console.log("ERROR: ", e);
    			},
    			done : function(e) {
    				//console.log("DONE");
    			}
    		});
}

function drawPagination(idPaging, classUl, currentPage, lastPage, numbPerPage, numbPerPageId, targetfunction){
 var ul = $('<ul />').addClass(classUl);
 //print prev
 if(currentPage>1){
  $("<li />").append($("<a />").html("prev")).appendTo(ul);
 }
 //print first to current
 if(currentPage-1>3){
  $("<li />").append($("<a />").html("1")).appendTo(ul);
  //$("<li />").append($("<a />").html("...")).appendTo(ul);
  $("<li />").addClass("pagingdot").html("...").appendTo(ul);
  $("<li />").append($("<a />").html(currentPage-1)).appendTo(ul);
 }else {
 if(currentPage-1>0){
  for(var i=1;i<=currentPage-1;i++){
       $("<li />").append($("<a />").html(i)).appendTo(ul);
   }
  }
 }
 //print current
 $("<li />").addClass("current").attr("id","currentpaging").html(currentPage).appendTo(ul);

//print current to last
 if(currentPage+1<lastPage-2){
  $("<li />").append($("<a />").html(currentPage+1)).appendTo(ul);
  //$("<li />").append($("<a />").html("...")).appendTo(ul);
  $("<li />").addClass("pagingdot").html("...").appendTo(ul);
  $("<li />").append($("<a />").html(lastPage)).appendTo(ul);
 }else{
  if(currentPage<lastPage){
   for(var j=currentPage+1;j<=lastPage;j++){
         $("<li />").append($("<a />").html(j)).appendTo(ul);
     }
  }
 }
 //print next
 if(currentPage<lastPage) {
  $("<li />").append($("<a />").html("next")).appendTo(ul);
 }
 //add to paging tag
 $("#"+idPaging).html("");
 $("#"+idPaging).append(ul).append($("<p />"));

 //addClickFuncOnPagination
 $("#"+idPaging+" a").click(function(){
     if($(this).html()=='next'){
      targetfunction(parseInt($("input[id=currentpage]").val())+1);
      return;
     }
     if($(this).html()=='prev'){
      targetfunction(parseInt($("input[id=currentpage]").val())-1);
      return;
     }
     targetfunction($(this).html());
  });
}



function getChitietThanhtoan(){
var prs = $(this).attr("id").split("/");
var search = {}
 		search["idkhachhang"] = parseInt(prs[0]);
 		search["idcanho"] = parseInt(prs[1]);

 		$.ajax({
 			type : "POST",
 			url : "/admin/chitietthanhtoan",
 			data : search,
 			timeout : 100000,
 			success : function(data) {
 				drawChitietThanhtoan(data);
 			},
 			error : function(e) {
 				//console.log("ERROR: ", e);
 			},
 			done : function(e) {
 				//console.log("DONE");
 			}
		});
}

function drawChitietThanhtoan(thanhtoan){
  var table = $("#thanhtoan");
  table.html("");
  var tr = $("<tr />").appendTo(table);
  tr.append($("<th />").html("Tên Khách Hàng"));
  tr.append($("<th />").html("Mã Căn Hộ"));
  tr.append($("<th />").html("Đợt Thanh Toán"));
  tr.append($("<th />").html("Ngày Thanh Toán"));
  tr.append($("<th />").html("Số Tiền"));
  tr.append($("<th />").html("Ghi Chú"));
  for(var i=0;i<thanhtoan.length;i++) {
    var tr = $("<tr />");
    tr.append($("<td />").html(thanhtoan[i][0]));
    tr.append($("<td />").html(thanhtoan[i][1]));
    tr.append($("<td />").html(thanhtoan[i][2]['dotthanhtoan']));
    tr.append($("<td />").html(thanhtoan[i][2]['ngaythanhtoan']));
    tr.append($("<td />").html(thanhtoan[i][2]['sotien'].toLocaleString('en-US', {minimumFractionDigits: 0})));
    tr.append($("<td />").html(thanhtoan[i][2]['ghichu']));
    if(i%2!=0){
     tr.addClass("even");
    }
    tr.appendTo(table);
  }
}

function chonNgayBanGiaoUser(idcanho, ngaychon){
    var paras = {} ;
    paras["idcanho"] = idcanho;
    paras["date"] = ngaychon;

    $.ajax({
        type : "POST",
        url : "/user/chonngaybangiao",
        data : paras,
        timeout : 100000,
        success : function(data) {
            if(data==true){
                window.location.href = "/";
            }else{
                alert("Có lỗi xảy ra !");
            }
        },
        error : function(e) {
            //console.log("ERROR: ", e);
        },
        done : function(e) {
            //console.log("DONE");
        }
    });
}

function getCanHoBanGiaoAdmin(page) {
		var search = {}
		search["page"] = page;
		search["numb"] = $("#rowlimit").val();

		$.ajax({
			type : "POST",
			url : "/admin/canhobangiao",
			data : search,
			timeout : 100000,
			success : function(data) {
			    //console.log(data);
			    if(data['data'].length==0){
                 //window.location.href = "/";
                 //hide the paging select box...
                 return;
			    }
				$("input[id=currentpage]").val(data['currentpage']);
				drawCanhoBanGiaoTable(data['data']);
				drawPagination("pagingDX","paginate pag2 clearfix",data['currentpage'],data['lastpage'],null,null,getCanHoBanGiaoAdmin);
			},
			error : function(e) {
				//console.log("ERROR: ", e);
			},
			done : function(e) {
				//console.log("DONE");
			}
		});
}

function drawCanhoBanGiaoTable(canholist){
     var table = $("#canholist");
     table.html("");
     var tr = $("<tr />").appendTo(table);
     tr.append($("<th />").html("Tên Dự Án"));
     tr.append($("<th />").html("Mã Sản Phẩm"));
     tr.append($("<th />").html("Tên Khách Hàng"));
     tr.append($("<th />").html("Biên Bản Nghiệm Thu"));
     tr.append($("<th />").html("Ngày Đăng Ký"));
     tr.append($("<th />").html("Ngày Duyệt"));
     for(var i=0;i<canholist.length;i++) {
       var tr = $("<tr />");
       tr.append($("<td />").html(canholist[i][1]));
       tr.append($("<td />").html(canholist[i][0]));
       tr.append($("<td />").html(canholist[i][3]));
       tr.append($("<td />").html(canholist[i][2]));
       //console.log($.datepicker.formatDate('dd/mm/yy',new Date(canholist[i][4]['ngaydangky'])));
       tr.append($("<td />").html($.datepicker.formatDate('dd/mm/yy',new Date(canholist[i][4]['ngaydangky']))));
       if(canholist[i][4]['ngayduyet']==null){
        tr.append($("<td />").append($("<a />").attr("id",canholist[i][4]['idkhachhang']+"/"+canholist[i][4]['idcanho']).html("Duyệt").bind('click',duyetNgayBanGiaoAdmin)));
       }else{
       var currentdate = $.datepicker.formatDate('dd/mm/yy',new Date(canholist[i][4]['ngayduyet']));
        tr.append($("<td />").append($("<span />").addClass("highlight").html(currentdate))
        .append(" - ")
        .append($("<a />").attr("currentdate",currentdate).attr("id",canholist[i][4]['idkhachhang']+"/"+canholist[i][4]['idcanho']).html("Chọn ngày khác").bind('click',chonNgayBanGiaoKhacAdmin)));
       }

       if(i%2!=0){
        tr.addClass("even");
       }
       tr.appendTo(table);
     }
 }

 function duyetNgayBanGiaoAdmin(){
 if (confirm("Chọn ngày bàn giao theo như ngày khách hàng đăng ký ?") == false) {
    return;
 }
 var prs = $(this).attr("id").split("/");
 var ids = {}
  		ids["idkhachhang"] = parseInt(prs[0]);
  		ids["idcanho"] = parseInt(prs[1]);

  		$.ajax({
  			type : "POST",
  			url : "/admin/duyetngaybangiao",
  			data : ids,
  			timeout : 100000,
  			success : function(data) {
  				if(data==true){
                 window.location.href = "/admin/bangiao";
                }else{
                    alert("Có lỗi xảy ra !");
                }
  			},
  			error : function(e) {
  				//console.log("ERROR: ", e);
  			},
  			done : function(e) {
  				//console.log("DONE");
  			}
 		});
 }

 function chonNgayBanGiaoKhacAdmin(){
 var ids = $(this).attr('id');
 var currentdate = $(this).attr('currentdate');
 $('#datepicker').datepicker({
     firstDay: 1,
     onSelect: function () {
         var cusdate = $(this).datepicker( 'getDate' );
         var cusdatefm = $.datepicker.formatDate('dd/mm/yy',cusdate);
         if(chonNgayTuDatePicker(cusdate, currentdate,1)==true){
             chonNgayBanGiaoAdmin(ids, cusdatefm);
         }
     }
 })
 }

 function chonNgayTuDatePicker(cusdate, currentdate, limitdate){
     var cusdatefm = $.datepicker.formatDate('dd/mm/yy',cusdate);

     $('#datepicker').datepicker('destroy');

     if(currentdate!=null && currentdate==cusdatefm){
         alert("Bạn chọn trùng với ngày hiện tại !");
         return false;
     }
     var today = new Date();
     today.setHours(0);
     today.setMinutes(0);
     today.setSeconds(0);
     today.setMilliseconds(0);
     today.setDate(today.getDate()+limitdate);
     if(cusdate<today){
         today = $.datepicker.formatDate('dd/mm/yy',today);
         alert("Ngày bàn giao sớm nhất có thể: "+ today);
         return false;
     }
     return confirm("Bạn chọn ngày bàn giao: "+cusdatefm+" ?");
 }

 function chonNgayBanGiaoAdmin(id, ngaychon){
     var ids = id.split("/");
     var paras = {};
     paras["idkhachhang"] = parseInt(ids[0]);
     paras["idcanho"] = parseInt(ids[1]);
     paras["date"] = ngaychon;

     $.ajax({
         type : "POST",
         url : "/admin/chonngaybangiao",
         data : paras,
         timeout : 100000,
         success : function(data) {
             if(data==true){
                 window.location.href = "/admin/bangiao";
             }else{
                 alert("Có lỗi xảy ra !");
             }
         },
         error : function(e) {
             //console.log("ERROR: ", e);
         },
         done : function(e) {
             //console.log("DONE");
         }
     });
 }

function getKhachHangAdmin(page) {
		var search = {}
		search["page"] = page;
		search["numb"] = $("#rowlimit").val();

		//console.log(search);
		$.ajax({
			type : 'POST',
			url : '/admin/khachhang',
			data : search,
			timeout : 100000,
			success : function(data) {
			//console.log(data);
			    if(data['data'].length==0){
                 return;
			    }
				$("input[id=currentpage]").val(data['currentpage']);
				drawKhachHangTable(data['data']);
				drawPagination("pagingDX","paginate pag2 clearfix",data['currentpage'],data['lastpage'],null,null,getKhachHangAdmin);

			},
            error : function(e) {
                console.log("ERROR: ", e);
            },
            done : function(e) {
                console.log("DONE");
            }
		});
}

function drawKhachHangTable(khlist){
 var table = $("#khachhanglist");
 table.html("");
 var tr = $("<tr />").appendTo(table);
 tr.append($("<th />").html("Tên Khách Hàng"));
 tr.append($("<th />").html("Tên Đăng Nhập"));
 tr.append($("<th />").html("Email"));
 tr.append($("<th />").html("Điện Thoại"));
 tr.append($("<th />").html("Chỉnh Sửa"));
 tr.append($("<th />").html("Tình Trạng"));

 for(var i=0;i<khlist.length;i++) {
   var tr = $("<tr />");
   tr.append($("<td />").html(khlist[i][2]));
   tr.append($("<td />").html(khlist[i][1]));
   tr.append($("<td />").html(khlist[i][3]));
   tr.append($("<td />").html(khlist[i][4]));
   tr.append($("<td />").append($("<a />").addClass("glyphicon glyphicon-edit").attr("title","Sửa thông tin khách hàng.").attr("target","_blank").attr("href","/admin/user/"+khlist[i][0])));

   if(khlist[i][5]==null){
    tr.append($("<td />"));
   }else{
       if(khlist[i][5]==true)
           tr.append($("<td />").append($("<a />").addClass("fa fa-unlock").attr("title","Khóa tài khoản.").attr("id",khlist[i][1])));
       else
            tr.append($("<td />").append($("<a />").addClass("fa fa-lock").attr("title","Mở khóa tài khoản.").attr("id",khlist[i][1])));
   }

   if(i%2!=0){
    tr.addClass("even");
   }
   tr.appendTo(table);
 }

   //addClickFuncOn unlock icon
  $("a.fa-unlock").click(function(){
      var user = $(this).attr("id");
      if (confirm("Tài khoản "+ user +" sẽ bị khóa. \nBạn có chắc ?") == true) {
          khoamoTaiKhoan(false, user);
      }
   });

    //addClickFuncOn unlock icon
     $("a.fa-lock").click(function(){
         var user = $(this).attr("id");
         if (confirm("Tài khoản "+ user +" sẽ được mở khóa. \nBạn có chắc ?") == true) {
             khoamoTaiKhoan(true, user);
         }
      });
}

function khoamoTaiKhoan(type, user){
    var paras = {}
    paras["user"] = user;
    paras["type"] = type;
    var acc = type==true ? "Mở khóa ":"Khóa ";
    $.ajax({
        type : "POST",
        url : "/admin/khoamotaikhoankhachhang",
        data : paras,
        timeout : 100000,
        success : function(data) {
            if(data==true){
                alert(acc+" tài khoản thành công !");
                window.location.href = "/admin/users";
            }else{
                alert("Xảy ra lỗi !");
            }
        }
    });
}

function capnhatKhachhang(){
    $(".alert").css("display","none");

    var loginname = $("input[name=username]").val();
    var name = $("input[name=fullname]").val();
    var pass1 = $("input[name=newpass1]").val();
    var pass2 = $("input[name=newpass2]").val();
    var email = $("input[name=email]").val();
    var phone = $("input[name=phone]").val();
    var error, path;
    if($("input[name=username]").is('[readonly]')){
        error = validateInputCapnhatKhachhang(name,pass1, pass2, email, phone);
        path = "/admin/capnhatkhachhang";
    }else{
        error = validateInputThemmoiKhachhang(loginname, name,pass1, pass2, email, phone);
        path = "/admin/themmoitaikhoan";
    }
    if(error!="none"){
        $("#danger_message").html(error);
        $("#danger_message").css("display","block");
        return;
    }

    var paras = {}
    paras["idkh"] = $("input[id=idkhachhang]").val();
    paras["loginname"] = loginname;
    paras["name"] = name;
    paras["pass1"] = pass1;
    paras["pass2"] = pass2;
    paras["email"] = email;
    paras["phone"] = phone;


   $.ajax({
        type : "POST",
        url : path,
        data : paras,
        timeout : 100000,
        success : function(data) {
            if(data["code"]==0){
                $("#success_message").css("display","block");
            }else if(data["code"]==1){
                $("#danger_message").html(data["msg"]);
                $("#danger_message").css("display","block");
            }else{
                $("#danger_message").html("Lỗi kỹ thuật !");
                $("#danger_message").css("display","block");
            }
        }
    });
}

function validateInputCapnhatKhachhang(name, pass1 ,pass2, email, phone){
    var ck_name = /^.{3,30}$/;
    var ck_email = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
    //var ck_username = /^[A-Za-z0-9_]{5,20}$/;
    var ck_password =  /^[A-Za-z0-9!@#$%^&*()_]{6,20}$/;
    var ck_phone = /^[+()0-9 ]{6,20}$/;

    var errors = [];
        if(!ck_name.test(name)){
            errors[errors.length] = "Họ tên không hợp lệ.";
        }
        if(pass1!=null && pass1!=""){
            if(!ck_password.test(pass1)){
                errors[errors.length] = "Mật khẩu không hợp lệ.";
            }
            if(pass1!=pass2){
                errors[errors.length] = "Mật khẩu xác nhận không khớp.";
            }
        }
        if(!ck_email.test(email)){
            errors[errors.length] = "Email không hợp lệ.";
        }
        if(phone!=null && phone!="" && !ck_phone.test(phone)){
            errors[errors.length] = "Số điện thoại không hợp lệ.";
        }

        if(errors.length>0){
            var msgError = "";
            for (var i = 0; i<errors.length; i++) {
             var numError = i + 1;
              msgError += numError + ". " + errors[i]+"<br />";
            }
            return msgError;
        }
    return "none";

}

function validateInputThemmoiKhachhang(loginname, name, pass1 ,pass2, email, phone){
    var ck_name = /^.{3,30}$/;
    var ck_email = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
    var ck_username = /^[A-Za-z0-9_]{5,20}$/;
    var ck_password =  /^[A-Za-z0-9!@#$%^&*()_]{6,20}$/;
    var ck_phone = /^[+()0-9 ]{6,20}$/;

    var errors = [];
        if(!ck_username.test(loginname)){
            errors[errors.length] = "Tên đăng nhập không hợp lệ.";
        }
        if(!ck_name.test(name)){
            errors[errors.length] = "Họ tên không hợp lệ.";
        }
        if(!ck_password.test(pass1)){
            errors[errors.length] = "Mật khẩu không hợp lệ.";
        }
        if(pass1!=pass2){
            errors[errors.length] = "Mật khẩu xác nhận không khớp.";
        }
        if(!ck_email.test(email)){
            errors[errors.length] = "Email không hợp lệ.";
        }
        if(phone!=null && phone!="" && !ck_phone.test(phone)){
            errors[errors.length] = "Số điện thoại không hợp lệ.";
        }

        if(errors.length>0){
            var msgError = "";
            for (var i = 0; i<errors.length; i++) {
             var numError = i + 1;
              msgError += numError + ". " + errors[i]+"<br />";
            }
            return msgError;
        }
    return "none";

}

/*
function taomoiKhachhang(){
    $(".alert").css("display","none");

    var loginname = $("input[name=username]").val();
    var name = $("input[name=fullname]").val();
    var pass1 = $("input[name=newpass1]").val();
    var pass2 = $("input[name=newpass2]").val();
    var email = $("input[name=email]").val();
    var phone = $("input[name=phone]").val();
    var error = validateInputThemmoiKhachhang(loginname, name,pass1, pass2, email, phone)
    if(error!="none"){
        $("#danger_message").html(error);
        $("#danger_message").css("display","block");
        return;
    }

    var paras = {}
    paras["loginname"] = loginname;
    paras["name"] = name;
    paras["pass1"] = pass1;
    paras["pass2"] = pass2;
    paras["email"] = email;
    paras["phone"] = phone;


    $.ajax({
        type : "POST",
        url : "/admin/themmoikhachhang",
        data : paras,
        timeout : 100000,
        success : function(data) {
            if(data["code"]==0){
                $("#success_message").css("display","block");
            }else if(data["code"]==1){
                $("#danger_message").html(data["msg"]);
                $("#danger_message").css("display","block");
            }else{
                $("#danger_message").html("Lỗi kỹ thuật !");
                $("#danger_message").css("display","block");
            }
        }
    });
}*/
