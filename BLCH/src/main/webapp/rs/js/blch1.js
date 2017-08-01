function logOut() {
    document.getElementById("logoutForm").submit();
}

/*function csrfSetting(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}*/

var myApp = angular.module('myapp', []);

myApp.service('myfunc', function() {
    this.createNumbArray = function (x,y) {
        var array = [];
        for(var i=x;i<=y;i++){
            array.push(i);
        };
        return array;
    }
});

myApp.controller('andminhome', function($scope, $http,$filter,$window,$timeout) {
    //onload funcs for the first time get data
    angular.element(document).ready(function () {
        $scope.getCanHoAdminHome($scope.pageforonload,$scope.limitforonload);
    });

    $scope.getCanHoAdminHome = function(page, limit) {
        $scope.pageforonload = page;
        $scope.limitforonload = limit;
        var search = {}
        search.page = page;
        search.numb = limit;
        $http({
            method : "POST",
            url : "/admin/canho",
            params: search
        }).then(function mySuccess(response) {
            $scope.canho = response.data.data;
            $scope.showdata = response.data.data.length==0 ? false:true;
            if(!$scope.showdata){return;}
            $scope.chitietthanhtoan=null;
            $scope.$broadcast('paging', {currentpage:response.data.currentpage,lastpage:response.data.lastpage});
        }, function myError(response) {
            //
        });
    };
    $scope.duyetbangiao = function(item) {
        //console.log(item);
        var tencanho = item[2].tenduan + " - " + item[2].macanho;
        if (confirm(tencanho + " sẽ được chuyển sang trạng thái sẵn sàng bàn giao. \nBạn có chắc ?") == false) {
            return;
        }
        var paras = {}
        paras.idcanho = item[2].id;
        $http({
            method : "POST",
            url : "/admin/duyetbangiao",
            params: paras
        }).then(function successCallback(response) {
            if(response.data==true){
                $timeout(function (){
                    $window.alert(tencanho+ " đã sẵn sàng bàn giao !");
                    $window.location.href = "/admin/home/limit/"+$scope.limitforonload+"/page/"+$scope.pageforonload;
                });
            }else{
                $timeout(function (){ $window.alert("Có lỗi xảy ra !");});
            }
        }, function errorCallback(response) {
            //
        });
    };
    $scope.getchitietthanhtoan = function(item) {
        var paras = {}
        paras.idkhachhang = item[0];
        paras.idcanho = item[2].id;
        $http({
            method : "POST",
            url : "/admin/chitietthanhtoan",
            params: paras
        }).then(function successCallback(response) {
            $scope.chitietthanhtoan = response.data;
        }, function errorCallback(response) {
            //
        });
    };
    //catch even from paging
    $scope.$on('pagingrequest', function(ev, args){
        $scope.getCanHoAdminHome(args.page,args.limit);
    });

    //order setting with customer orderby funcs base on data structure of server response (canho list)
    $scope.orderbytenkhachhangflag = true;
    $scope.orderbybangiaoflag = true;
    $scope.orderbymacanhoflag = true;
    $scope.sortcanho= function(type){
        switch(type) {
            case "tenkhachhang":
                $scope.orderbytenkhachhangflag = !$scope.orderbytenkhachhangflag;
                $scope.canho=$filter('orderBy')($scope.canho, $scope.orderbytenkhachhang, $scope.orderbytenkhachhangflag);
                break;
            case "bangiao":
                $scope.orderbybangiaoflag = !$scope.orderbybangiaoflag
                $scope.canho=$filter('orderBy')($scope.canho, $scope.orderbybangiao,$scope.orderbybangiaoflag);
                break;
            case "macanho":
                $scope.orderbymacanhoflag = !$scope.orderbymacanhoflag
                $scope.canho=$filter('orderBy')($scope.canho, $scope.orderbymacanho,$scope.orderbymacanhoflag);
                break;
            default:
                break;
        }
    };
    $scope.orderbytenkhachhang = function(item) {
        return item[1];
    };
    $scope.orderbybangiao = function(item) {
        return item[2].bangiao;
    };
    $scope.orderbymacanho = function(item) {
        return item[2].macanho;
    };
});

myApp.controller('paging', function($scope, myfunc) {
    $scope.rowlimitvalues = [
        {name : "Hiển thị 5", value : 5 },
        {name : "Hiển thị 10", value : 10},
        {name : "Hiển thị 20", value : 20},
    ];

    //ng-init="rowlimit=${limit==null ? 5:limit}"
    $scope.rowlimit = $scope.rowlimitvalues[0].value;

    $scope.page = function(x){
        $scope.$emit('pagingrequest', {page:x,limit:$scope.rowlimit});
    };
    $scope.limitchange = function(){
        $scope.$emit('pagingrequest', {page:1,limit:$scope.rowlimit});
    };
    $scope.$on('paging', function(ev, args){
        //console.log('Controller2 source: ' + args.currentpage + " - "+args.lastpage);
        //re-paint the paging object
        $scope.currentpage=args.currentpage;
        $scope.lastpage=args.lastpage;
        if($scope.currentpage<=4 && $scope.currentpage>1){
            $scope.prevarray = myfunc.createNumbArray(1,$scope.currentpage-1);
        }
        if($scope.currentpage>=$scope.lastpage-3 && $scope.currentpage<$scope.lastpage){
            $scope.nextarray = myfunc.createNumbArray($scope.currentpage+1,$scope.lastpage);
        }
    });
});

myApp.controller('adminkhachhang', function($scope, $http,$filter,$window,$timeout) {
    //onload funcs for the first time get data
    angular.element(document).ready(function () {
        $scope.getkhachhangadmin($scope.pageforonload,$scope.limitforonload);
    });

    $scope.getkhachhangadmin = function(page, limit) {
        $scope.pageforonload = page;
        $scope.limitforonload = limit;
        var search = {}
        search.page = page;
        search.numb = limit;
        $http({
            method : "POST",
            url : "/admin/khachhang",
            params: search
        }).then(function mySuccess(response) {
            $scope.khachhang = response.data.data;
            $scope.showdata = response.data.data.length==0 ? false:true;
            if(!$scope.showdata){return;}
            $scope.$broadcast('paging', {currentpage:response.data.currentpage,lastpage:response.data.lastpage});
        }, function myError(response) {
            //
        });
    };
    $scope.khoamotaikhoan = function(user,type) {
        var typecm = type==true ? "được mở khóa.":"bị khóa.";
        if (confirm("Tài khoản "+ user +" sẽ "+typecm+" \nBạn có chắc ?") == false) {
            return;
        }
        var paras = {};
        paras.user = user;
        paras.type = type;
        var acc = type==true ? "Mở khóa ":"Khóa ";
        $http({
            method : "POST",
            url : "/admin/khoamotaikhoankhachhang",
            params: paras
        }).then(function successCallback(response) {
            if(response.data==true){
                $timeout(function (){
                    $window.alert(acc+" tài khoản thành công !");
                    $window.location.href = "/admin/users/limit/"+$scope.limitforonload+"/page/"+$scope.pageforonload;
                });
            }else{
                $timeout(function (){ $window.alert("Có lỗi xảy ra !");});
            }
        }, function errorCallback(response) {
            //
        });
    };
    //catch even from paging
    $scope.$on('pagingrequest', function(ev, args){
        $scope.getkhachhangadmin(args.page,args.limit);
    });

    //order setting with customer orderby funcs base on data structure of server response (khachhang list)
    $scope.orderbytenkhachhangflag = true;
    $scope.orderbytendangnhapflag = true;
    $scope.orderbytinhtrangflag = true;
    $scope.sortkhachhang= function(type){
        switch(type) {
            case "tenkhachhang":
                $scope.orderbytenkhachhangflag = !$scope.orderbytenkhachhangflag;
                $scope.khachhang=$filter('orderBy')($scope.khachhang, $scope.orderbytenkhachhang, $scope.orderbytenkhachhangflag);
                break;
            case "tendangnhap":
                $scope.orderbytendangnhapflag = !$scope.orderbytendangnhapflag
                $scope.khachhang=$filter('orderBy')($scope.khachhang, $scope.orderbytendangnhap,$scope.orderbytendangnhapflag);
                break;
            case "tinhtrang":
                $scope.orderbytinhtrangflag = !$scope.orderbytinhtrangflag
                $scope.khachhang=$filter('orderBy')($scope.khachhang, $scope.orderbytinhtrang,$scope.orderbytinhtrangflag);
                break;
            default:
                break;
        }
    };
    $scope.orderbytenkhachhang = function(item) {
        return item[2];
    };
    $scope.orderbytendangnhap = function(item) {
        return item[1];
    };
    $scope.orderbytinhtrang = function(item) {
        return item[5];
    };
});

myApp.controller('adminupdatekhachhang', function($scope, $http) {
    $scope.showdata=true;
    $scope.successupdate=false;
    $scope.failureupdate=false;
    $scope.regex = {};
    $scope.regex.username = /^[A-Za-z0-9_]{5,20}$/;
    $scope.regex.password =  /^[A-Za-z0-9!@#$%^&*()_]{6,20}$/;
    angular.element(document).ready(function () {
        var paras = {}
        paras.idkh = $scope.idkh;
        $http({
            method : "POST",
            url : "/admin/getdetailkhachhang",
            params: paras
        }).then(function mySuccess(response) {
            //console.log(response.data);
            if(response.data==""){
                $scope.showdata=false;
                return;
            }
            $scope.insertflag = (response.data.tendangnhap==null) ? true:false;
            $scope.khachhang = response.data;
            //$scope.showdata = response.data.data.length==0 ? false:true;
        }, function myError(response) {
            //
        });
    });
    $scope.capnhattaikhoan = function() {
        //check if the data is changed really ?
        if( !$scope.updateform.$dirty ){
            alert("Bạn chưa cập nhật dữ liệu !");
            return;
        }
        var paras = {}
        paras.idkh = $scope.idkh;
        paras.loginname = $scope.khachhang.tendangnhap;
        paras.name = $scope.khachhang.tenkhachhang;
        paras.pass1 = $scope.khachhang.newpass1==null ? "" : $scope.khachhang.newpass1;
        paras.pass2 = $scope.khachhang.newpass2==null ? "" : $scope.khachhang.newpass2;
        paras.email = $scope.khachhang.email;
        paras.phone = $scope.khachhang.dienthoai==null ? "" : $scope.khachhang.dienthoai;
        var path = $scope.insertflag ? "/admin/themmoitaikhoan" : "/admin/capnhatkhachhang" ;
        $http({
            method : "POST",
            url : path,
            params: paras
        }).then(function mySuccess(response) {
            console.log(response)
            if(response.data.code==0){
                $scope.successupdate=true;
                $scope.failureupdate = false;
            }else if(response.data.code==1){
                $scope.failureupdate = true;
                $scope.successupdate=false;
                $scope.errormsg = response.data.msg
            }else{
                $scope.failureupdate = true;
                $scope.successupdate=false;
                $scope.errormsg = "Lỗi kỹ thuật !" ;
            }
        }, function myError(response) {
            //
        });
    };
});

