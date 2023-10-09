<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<div id="login">
<h3>로그인</h3>

<form method="POST">
	<p><input type="text" name="userid"  placeholder="ID" required autofocus autocomplete="off"
	value="${cookie.storedId.value }"></p>
	<p><input type="password" name="userpw" placeholder="Password" required></p>
	<div class="flex">
		<p><input type="submit" value="로그인"></p>
		<p>
			<input type="checkbox" name="storedId"
				${not empty cookie.storedId.value ? 'checked' : ''}>
			ID저장
		</p>
	</div>
</form>
<hr>
<p><a href="${cpath }/member/join"><button>회원가입</button></a></p>
<p><a href="${cpath }/member/updatePW" style="font-size: 0.8rem">비밀번호 재발급</a></p>
</div>
</body>
</html>