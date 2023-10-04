<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
				<img class="profileImg" src="${cpath }/profile/${dto.member_fileName }">
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
	<div id="review_input">
	
	
		<c:if test="${not empty login }">
			<div id="reviewWrite">
				<form method="POST" action="${cpath }/board/review/write/${dto.idx}">
					<p>
						<a href="${cpath }/board/like/${dto.idx}/${login.userid}">
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
			<div style="font-weight: bold; padding:10px 5px;">
				로그인 후 댓글 작성 가능합니다.
			</div>
		</c:if>
	</div>
	<c:forEach var="re_dto" items="${re_list }">
		<section id="review">
			<form>
			<div class="flex">
	<div>
			<a href="${cpath }/member/view/${re_dto.member_idx }">
				<img class="profileImg" src="${cpath }/profile/${re_dto.member_fileName }">
			</a>
				<div>${re_dto.member_userid }</div>
		</div>
				<input class="re_update" id="re_content_${re_dto.idx}" type="text" value="${re_dto.re_content }" readonly>
				 <input type="button" value="수정완료" style="display: none;" id="updateBtn_${re_dto.idx}" onclick="update('${re_dto.idx}')">
			</div>
			</form>
			<c:if test="${login.userid eq re_dto.member_userid }">
				<div id="review_up">
					<button onclick="modify('${re_dto.idx}')">수정</button>
					<button onclick="re_delete('${re_dto.idx}')">삭제</button>
				</div>
			</c:if>
		</section>
	</c:forEach>
</div><!-- boardView of end -->

<script>
    // 수정 버튼을 누르면 idx가 넘어오는 함수 만들기
function modify(seq) {
    // seq=${re_dto.idx}해당 댓글의 입력 필드를 선택
	const contentInput = 're_content_'+seq;
    // input의 속성 readonly를 없애는 작업
	document.getElementById(contentInput).readOnly = false;
    // 수정완료 버튼을 보이게 하기($() 함수 내에 선택자를 전달하여 요소를 선택, #는 id 선택자)
    $('#updateBtn_'+seq).show();
}
	// 수정완료키를 누르면 실행되는 함수
    function update(seq) {
    	// input태그 선택
    	const id = '#re_content_'+seq;  
		// 새로운 content 받아오기
    	const content = $(id).val();
		
        // 수정된 내용을 서버로 전송하고, 필요한 처리 수행
		$.ajax({
		    type : 'post',         						  // 타입 (get, post, put 등등)
		    url : '/recipe/board/review/modifyReview',    // 요청할 서버url
		    data : {content : content, 					  // 요청 데이터 , content를 맵의 형태로 담기
		    		idx : seq						 	  // idx에 seq를 담아서 보내기
		    		},
		    success : function(result) { 				  // ajax 요청 결과 
		    	if(result == 'success'){
		    		alert("댓글이 수정되었습니다.");
		    		location.reload();					// 새로고침
		    	}else{
		    		alert("수정에 실패하였습니다. \n관리자에게 문의하여주세요.")
		    	}
		        console.log(result);
		    } 
		})
        
    }
	
	function re_delete(seq){
		$.ajax({
		    type : 'post',         						  // 타입 (get, post, put 등등)
		    url : '/recipe/board/review/delete',	      // 요청할 서버url
		    data : {idx : seq						 	  // idx에 seq를 담아서 보내기
		    		},
		    success : function(result) { 				  // 결과 성공 콜백함수
		    	if(result == 'success'){
		    		alert("댓글이 삭제 되었습니다.");
		    		location.reload();					// 새로고침
		    	}else{
		    		alert("삭제에 실패하였습니다. \n관리자에게 문의하여주세요.")
		    	}
		        console.log(result);
		    } 
		})
		
	}
</script>






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