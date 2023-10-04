<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<div id="boardView">
<h3>단일 조회</h3>
<p>
	<a href="${cpath }/board/list"><button>글 목록</button></a>
	<a href="${cpath }/board/update/${dto.idx }"><button id="updateBtn">수정</button></a>
	<a href="${cpath }/board/delete/${dto.idx }"><button id="deleteBtn">삭제</button></a>
</p>
	<div id="boardTitle">
		<div>
			<a href="${cpath }/member/view/${dto.member_idx }">
				<img src="${cpath }/profile/${dto.member_fileName }">
			</a>
				<div>${dto.member_userid }</div>
		</div>
		<div>${dto.title }</div>
	</div>

	<div id="boardIngr">
		<div>${dto.ingr }</div>
	</div>

	<div id="boardContent">
		<c:forEach var="content" items="${contentList}" varStatus="status">
			<div>
				<img src="${cpath }/recipe/${fileNameList[status.index] }">
			</div>
			<div>${content }</div>
		</c:forEach>
	</div>
	
	<!-- 댓글 작성 form -->
	<c:if test="${not empty login && login.userid != dto.member_userid}">
		<div id="reviewWrite">
			<form method="POST" action="${cpath}/board/review/write/${dto.idx}">
			<p>
				<a href="${cpath }/board/like/${dto.idx}/${login.userid}" class="switch">
					<span class="heart-icon">
					<c:if test="${like_status == 'false' or empty like_status}">
						&#x2661;								<!-- 빨간색 빈 하트 : &#x2661;(U+2661) -->
					</c:if>
					<c:if test="${like_status == 'true' }">
						&#x2764;				
					</c:if>
					</span>${dto.likeCount}
				</a>		
			</p>
			<p>
				<input type="text" name="re_content" placeholder="댓글을 적어주세요" width="700" height="50">
				<input type="submit" value="작성완료"><br>
			</p>		
			</form>
		</div>
	</c:if>
	<c:if test="${empty login }">
		로그인 후 댓글 작성 가능합니다.
	</c:if>
	
	<!-- 작성된 댓글 목록 -->
	<c:forEach var="re_dto" items="${re_list }">
		<section id="review">
			<div class="flex">
				<div id="re_write">${re_dto.member_userid}</div>
				<div id="re_content">${re_dto.re_content }</div>
			</div>
			<c:if test="${login.userid eq re_dto.member_userid }">
				<div id="review_up">
					<a href="${cpath }/review/update">수정</a> 
					<a href="${cpath }/review/delete">삭제</a>
				</div>
			</c:if>
		</section>
	</c:forEach>
</div><!-- boardView of end -->


<script>
	const updateBtn = document.getElementById('updateBtn')
	const deleteBtn = document.getElementById('deleteBtn')
	
	const Handler = function(event) {
	    event.preventDefault()
	    if ('${login.userid}' != '${dto.member_userid}') {
	        alert('본인 글만 수정/삭제할 수 있습니다')
	        return
	    }
	    
	    if (event.target === deleteBtn) {
	        if (!confirm('정말 삭제하시겠습니까?')) {
	            return
	        }
	    }
        const url = event.target.parentNode.href
        location.href = url
	}
	
	updateBtn.addEventListener('click', Handler)
	deleteBtn.addEventListener('click', Handler)
	

</script>


</body>
</html>