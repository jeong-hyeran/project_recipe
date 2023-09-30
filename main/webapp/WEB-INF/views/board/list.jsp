<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<div class="boardList">
<h3>글목록</h3>

<div class="boardSearch">
	<form method="POST" action="${cpath}/board/search">
	    <p>
	        <select name="searchOption">
	            <option value="keyword">포함 할</option>
	            <option value="excludeKeyword">제외 할</option>
	        </select>
	        <input type="search" name="keyword" placeholder="재료를 입력하세요" autofocus required>
	        <input type="submit" value="검색">
	    </p>
	</form>
</div>

<table>
	<thead>
		<tr>
			<th>글번호</th>
			<th>글제목</th>
			<th>재료</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
			<th>좋아요</th>
		</tr>
	</thead>
	
	<tbody>
			<tr>
				<c:if test="${empty list }">
					<td colspan="6" style="text-align: center;">
						작성 된 글이 없습니다.
					</td>
				</c:if>
			</tr>
			<c:forEach var="dto" items="${list }">
			<tr>
				<td>${dto.idx }</td>
				<td>
					<a href="${cpath }/board/view/${dto.idx}">${dto.title }</a>
				</td>
				<td>${dto.ingr }</td>
				<td>${dto.member_userid }</td>
				<td>${dto.wdate }</td>
				<td>👁️‍🗨️${dto.viewCount }</td>
				<td>
					<a href="${cpath }/board/like/${dto.idx}/${dto.like_status}" class="switch">
						<span class="heart-icon">
						<c:if test="${dto.like_status == 'dislike' }">
							&#x2661;
						</c:if>
						<c:if test="${dto.like_status == 'like' }">
							&#x2764;
						</c:if>
						</span>${dto.likeCount}</a>		<!-- 빨간색 빈 하트 : &#x2661;(U+2661) -->
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<ul>
	<li><a href="${cpath }/board/write"><button>글작성</button></a></li>
</ul>
</div>

<script>
	const links = document.querySelectorAll('.switch');
	
	links.forEach(function(link) {
	  link.addEventListener('click', function(event) {
	    event.preventDefault();
	
	    // 클릭 이벤트가 발생한 링크에서 idx와 like_status를 가져옵니다.
	    const href = link.getAttribute('href');
	    const parts = href.split('/');
	    let idx = parts[4];
	    const span = link.querySelector('span');
	
	    // like_status 변경
	    let like_status = parts[5]; // 혹은 직접 변수에 할당하는 방식으로 가져올 수 있음
	    like_status = like_status === 'like' ? 'dislike' : 'like';
	    console.log(like_status)
	    
	    
	    if (like_status === 'like') {
// 	      span.innerHTML = '&#x2764;'; // 하트 아이콘 변경
	      location.href = '${cpath}/board/like/' + idx + '/' +  like_status;
	    } else {
// 	      span.innerHTML = '&#x2661;'; // 하트 아이콘 변경
	      location.href = '${cpath}/board/dislike/' + idx + '/' + like_status;
	    }
	  });
	});
</script>

</body>
</html>