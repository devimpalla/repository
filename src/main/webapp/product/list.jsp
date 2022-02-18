<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../header.jsp"/>
<jsp:include page="../nav.jsp"/>

<c:choose>
<c:when test="${ses.email ne '' && ses.email ne null}">
<a href="/prodCtrl/create" class="btn btn-outline-warning">상품등록</a>
</c:when>
</c:choose>

<table class="table table-hover">
    <thead>
      <tr>
        <th>PNO</th>
        <th>PNAME</th>
        <th>PRICE</th>
        <th>WRITER</th>
        <th>MOD AT</th>
        <th>READCOUNT</th>
        <th>IMAGE</th>
        <th>디테일</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="pvo" items="${list}">
      <tr>
        <td>${pvo.pno}</td>
        <td>${pvo.pname}</td>
        <td>${pvo.price}</td>
        <td>${pvo.writer}</td>
        <td>${pvo.modAt}</td>
        <td>${pvo.readCount}</td>
        <td>
        <c:if test="${pvo.imageFile ne '' && pvo.imageFile ne null }">
        <img src="../_fileUpload/th_${pvo.imageFile}">
        </c:if>
        </td>
        
        <td>
        <a href="/prodCtrl/detail?pno=${pvo.pno}" type="button" class="btn btn-sm btn btn-outline-warning btn-sm py-0">
        자세히</a></td>
      </tr>
     </c:forEach>
    </tbody>
  </table>
<script>
let msg_reg = '<c:out value="${msg_reg}"/>';
let msg_remove = '<c:out value="${msg_remove}"/>';
console.log(msg_reg);
if(msg_reg.length > 0){
	alert("상품등록이 " + (parseInt(msg_reg) > 0 ?  "완료되었습니다" : "실패하였습니다"));
}else if (msg_remove.length > 0){
	alert("상품삭제" + (parseInt(msg_remove) > 0 ?  "가 완료되었습니다" : "에 실패하였습니다"));
}
</script>

 <jsp:include page="../footer.jsp"/>