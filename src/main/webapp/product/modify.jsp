<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../header.jsp"/>
<jsp:include page="../nav.jsp"/>


  <h2>Product Modify</h2>
  <form action="/prodCtrl/update" method="post" enctype="multipart/form-data">
  <input type="hidden" name="pno" value="${pvo.pno}">
<div class="mb-3">
    <label for="pno" class="form-label">PNO : </label>
    <input type="number" class="form-control" id="pno" name="pno" value="${pvo.pno}" readonly>
  </div>
<div class="mb-3 mt-3">
    <label for="pname" class="form-label">Product Name : </label>
    <input type="text" class="form-control" id="pname" name="pname" value="${pvo.pname}" >
  </div>
  <div class="mb-3">
    <label for="price" class="form-label">Price : </label>
    <input type="number" class="form-control" id="price" name="price" value="${pvo.price}" >
  </div>
   <div class="mb-3">
    <label for="madeBy" class="form-label">Made By : </label>
    <input type="text" class="form-control" id="madeBy" name="madeBy" value="${pvo.madeBy}" >
  </div>
  <div class="mb-3">
    <label for="regAt" class="form-label">Register At : </label>
    <input type="text" class="form-control" id="regAt" name="writer" value="${pvo.regAt}" readonly>
  </div>
  <div class="mb-3">
    <label for="writer" class="form-label">Writer : </label>
    <input type="text" class="form-control" id="writer" name="writer" value="${pvo.writer}" readonly>
  </div>
  <div class="mb-3">
    <label for="category" class="form-label">Category : </label>
    <select name="category" id="category" class="form-control"  value="${pvo.category}">
    <option value="clothes">clothes</option>
    <option value="food">food</option>
    <option value="shoes">shoes</option>
    <option value="vehicle">vehicle</option>
    <option value="books">books</option>
    </select>
  </div>
  <div class="mb-3">
    <label for="description" class="form-label">description : </label>
    <textarea class="form-control" name="description">${pvo.description }</textarea>
  </div>
  <div class="mb-3">
    <label for="modAt" class="form-label">Modify At : </label>
    <input type="text" class="form-control" id="modAt" name="modAt" value="${pvo.modAt}" readonly>
  </div>
  <div class="mb-3">
    <label for="readCount" class="form-label">ReadCount : </label>
    <input type="text" class="form-control" id="readCount" name="readCount" value="${pvo.readCount-1}" readonly>
  </div>
  <div class="mb-3">
    <label for="image" class="form-label">image : ${pvo.imageFile} </label>
    <input type="hidden" name="imageFile"  value="${pvo.imageFile}">
  </div>
  <div class="mb-3">
    <label for="file" class="form-label">mod image : </label>
    <input type="file" class="form-control" id="file" name="new_imageFile" accept="image/png, image/jpeg, image/jpg, image/gif">
  </div>
  <button type="submit" class="btn btn-danger">SUBMIT</button>
</form>

 <jsp:include page="../footer.jsp"/>