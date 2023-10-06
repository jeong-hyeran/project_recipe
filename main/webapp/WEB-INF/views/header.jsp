<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="cpath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>recipe</title>
<style>
	body{
		width: 1200px;
		margin: auto;
	}
	.flex{
		display: flex;
	}
	table{
		border : 2px solid black;
		border-collapse: collapse;
		margin: 10px auto;	
		padding: 10px;
	}
	th{
		background-color: #eee;
		border: 1px solid black;
		padding: 10px;
	}
	td{
		border: 1px solid black;
		padding: 10px;
	}
	#join, #login, #updatePW{
		border: 1px solid #eee;
		margin: 20px auto;
		padding: 20px 40px;
		width: fit-content;
		text-align: center;
		border-radius: 15px;
	}
	textarea {
		margin: 10px 5px;
	}
	#boardView{
		margin: auto;
		width:1000px;
		padding: 20px;
	}
	#boardIngr {
		box-sizing:border-box;
		border: 1px solid #eee;
		width: 800px;
		padding: 5px;
		margin: 5px auto;
		text-align: center;
	}

	#boardTitle{
		box-sizing:border-box;
		border: 1px solid #eee;
		width: 800px;
		padding: 10px 20px;
		height:80px;
		margin: 5px auto;
		text-align: center;
		display: flex;
	}
	
	#boardTitle > div:last-child {
		padding-left: 5px;
		font-weight: bold;
		font-size: 1.5rem;
		
	}
 	#boardTitle > div{ 
 		margin: auto; 
		padding: 0; 

 	} 
 	#boardTitle > div:first-child{
 		margin-right:2px; 
 	} 
 	#boardTitle > div:last-child{
 		margin-left:2px; 
 	} 
	#boardTitle > div > a {
		display: inline-block;
		height: 10px;
		font-size: 0.5rem;
	}

	.profileImg{
		border-radius:50%;
		width: 35px;
		text-align: center;
		font-size: 0.8rem;
	}
	#boardContent {
		box-sizing:border-box;
		border: 1px solid #eee;
		width: 800px;
		padding: 15px;
		line-height: 2rem;
		margin: 5px auto;
		text-align: center;
	}
	#boardContent > div > img{
		width: 400px;
	}
	#reviewWrite input[type="text"]{
		width: 710px;
		height: 80px;
		margin-right: 1px;
		border: 1px solid #eee;
	}
	#reviewWrite input[type="submit"]{
		height: 85px;
	}
	#reviewWrite a{
		font-size: 0.7rem;
	}
	#reviewWrite{
		width: 800px;
		margin:auto;
		box-sizing:border-box;
		padding: 0 3px;
	}
	#re_content{
		font-size: 1.2rem;
		padding-left: 5px;
	}
	#review{
		width: 800px;
		margin: auto;
		margin-top:10px;
		box-sizing:border-box;
		padding: 10px;
		border: 1px solid #eee;
	}
	#review_up{
		text-align: right;
		font-sixe:0.8rem;
		color : #dadada;
	}
	ul{
		list-style: none;
		display: flex;
		padding: 5px 20px;
		justify-content: space-around;
		margin: 5px;
	}
	li{
		margin: 5px;
		padding: 5px;
	}
	a{
		all : unset;
		cursor: pointer;
	}
	a:hover {
		text-decoration: underline;
	}
	#restore{
		font-size: 0.7rem;
		font-weight: bold;
	}
	.boardSearch{
		margin:5px 35px;
	}
	
	.keyword{
		margin:5px 35px;
		display: flex;
	}
	.include, .exclude{
		margin: 10px;
		padding: 5px;
		
	}
	.boardList{
		box-sizing: border-box;
		margin: auto;
		padding: 10px 100px; 
	}
	.boardList > table{
		width: 950px;
	}	
	.boardList > table > tbody > tr >  td:first-child{
		text-align: center;
	}

	.heart-icon {
		color: red; 
	}
	
	.re_update[type="text"]{
		margin-left: 5px;
	}
	.re_update{
		all:unset;
	}
	.re_update:not([readonly]) {
	    background-color: #fff;
	    color: #333;
	    border: 1px solid #ccc; 
	    cursor: auto;
	}
	#review_input{
		box-sizing: border-box;
	    width: 800px;
	    padding: 0;
	    margin: 5px auto;
	}
   .heart-icon {
      color: red; 
   }
</style>
</head>
<body>
<h1><a href="${cpath }">Recipe Blog</a></h1>
<p align="right">
<c:if test="${empty login }">
	로그인이 필요합니다.
</c:if>
<c:if test="${not empty login }">
	${login.userid } [${login.username }님]
</c:if>
</p>
<header>
	<nav>
		<ul>
			<c:if test="${empty login }">
				<li><a href="${cpath }/member/login">로그인</a></li>
				<li><a href="${cpath }/member/join">회원가입</a></li>
			</c:if>
			<c:if test="${not empty login }">
				<li><a href="${cpath }/member/logout">로그아웃</a></li>
				<li><a href="${cpath }/member/mypage">My Page</a></li>
			</c:if>
			<c:if test="${not empty login }">
				<li><a href="${cpath }/board/write">글작성</a></li>
				<!-- 드랍 메뉴 구현 할 수도? -->
			</c:if>
				<li><a href="${cpath }/board/list">레시피</a></li>
				<li><a href="${cpath }/board/review/wrte">댓글(구현 후 삭제)</a></li>
		</ul>
	</nav>
</header>
<hr>