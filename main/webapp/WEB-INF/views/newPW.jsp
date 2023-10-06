<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<script>
	const newPW = '${newPW}'
	alert(newPW)
	location.href='${cpath}/member/login'
</script>
</body>
</html>