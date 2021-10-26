<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" href="/resources/css/board/board.css"/>
</head>
<body>
<div class="content">

   <h2 class="tit">게시판</h2>
   <div class="info">
      <span>번호 : <c:out value="${board.bdIdx}"/></span>
      <span>제목 : <c:out value="${board.title}"/></span>
      <span>등록일 : <c:out value="${board.regDate}"/></span>
      <span>작성자 : <c:out value="${board.userId}"/></span>
   </div>
   <div class="info file_info">
      <c:if test="${not empty files}">
         <ol>
            <c:forEach items="${files}" var="file">
               <li onclick="downloadQueryString('${file.originFileName},${file.renameFileName},${file.savePath}')">
               <a>${file.originFileName}</a>
               </li>            
            </c:forEach>
         </ol>
      </c:if>
   </div>
   <div class="article_content">
      <pre><c:out value="${board.content}"/></pre>
   </div>
    <c:forEach items="${files}" var="file">
      <img src="${file.link}">   
    </c:forEach>
</div>


<script type="text/javascript">
	downloadQueryString = (originFileName, renameFileName, savePath) => {
		
		let paramObj = {'originFileName':originFileName
				,'renameFileName':renameFileName
				,'savePath':savePath};
		
		location.href = '/download?' + urlEncoder(paramObj);
	}
</script>

</body>
</html>