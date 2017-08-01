<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<body>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
	    <c:redirect url="/admin/home"/>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_USER')">
	    <c:redirect url="/user/home"/>
    </sec:authorize>
</body>
</html>