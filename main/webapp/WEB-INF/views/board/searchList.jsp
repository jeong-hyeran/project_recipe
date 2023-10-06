<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<div class="boardList">
<h3>글목록</h3>
	<!-- 처음 검색한 결과 리스트 위에 띄워지는 문구 -->
	<div class="keyword">
		<div class="include">
			<c:if test="${searchOption eq 'include'}">
				<div style="font-size: 0.7rem;">아래 재료가 포함된 레시피 입니다.</div>
				<span id="restore">${keyword }</span>
			</c:if>
		</div>
		<div class="exclude">
			<c:if test="${searchOption eq 'exclude'}">
				<div style="font-size: 0.7rem;">아래 재료가 제외 된 레시피 입니다.</div>
				<span id="restore">${keyword }</span>
			</c:if>
		</div>
	</div>
	
	<!-- 한번 더 제외 검색을 했을 경우 띄워지는 문구 -->
	<div class="keyword">	
		<c:if test="${not empty excludeKeyword}">
			<div class="include">
				<div style="font-size: 0.7rem;">아래 재료가 포함된 레시피 입니다.</div>
				<span id="restore">${keyword }</span>
			</div>
			<div class="exclude">
				<div style="font-size: 0.7rem;">아래 재료가 제외 된 레시피 입니다.</div>
				<span id="restore">${excludeKeyword }</span>
			</div>
		</c:if>
	</div>

	<!-- 처음 검색 결과 후 제외하고싶은 재료가 있으면 한번 더 검색하게 해주는 form-->
	<div class="boardSearch">
		<c:if test="${searchOption eq 'include'}">
			<form method="POST" action="${cpath }/board/searchList/${keyword}">
				 <p>
					<input type="search" name="excludeKeyword" placeholder="제외할 재료를 입력하세요" autofocus required>
					<input type="submit" value="제외">
				</p>
			</form>
		</c:if>
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
				<td>❤️${dto.likeCount }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<ul>
	<li><a href="${cpath }/board/list"><button>전체 목록</button></a></li>
	<li><a href="${cpath }/board/write"><button>글작성</button></a></li>
</ul>
</div>
<!-- 
<script>
    // "restore" div를 클릭하면 이전 페이지로 리다이렉트
    document.getElementById('restore').onclick = function () {
        window.location.href = document.referrer;
    };
</script>
// 포함하는 재료를 검색 후에, 제외하는 재료를 검색했을 경우에 
그 페이지에 뜬 포함하는 검색어를 지우면 포함하지 않는 재료로 조회된 리스트가 떠야하는데
지금은 단지 이전페이지로 이동하는 건만 됨... 그 것도 제대로 되지 않음.. 
우선 검색어를 띄우기만 하는 걸로 수정
 -->

</body>
</html>