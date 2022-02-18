<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../header.jsp"/>
<jsp:include page="../nav.jsp"/>

<form action="/prodCtrl/insert" method="post" enctype="multipart/form-data">
  <div class="mb-3 mt-3">
    <label for="pname" class="form-label">Product Name : </label>
    <input type="text" class="form-control" placeholder="Enter pname" name="pname">
  </div>
  <div class="mb-3">
    <label for="price" class="form-label">Price : </label>
    <input type="number" class="form-control" placeholder="Enter price" name="price">
  </div>
   <div class="mb-3">
    <label for="madeBy" class="form-label">Made By : </label>
    <input type="text" class="form-control"  placeholder="Enter made by" name="madeBy">
  </div>
  <div class="mb-3">
    <label for="writer" class="form-label">Writer : </label>
    <input type="text" class="form-control" value="${ses.email}" name="writer" readonly>
  </div>
  <div class="mb-3">
    <label for="category" class="form-label">Category : </label>
    <select name="category" class="form-control">
    <option value="clothes">clothes</option>
    <option value="food">food</option>
    <option value="shoes">shoes</option>
    <option value="vehicle">vehicle</option>
    <option value="books">books</option>
    </select>
  </div>
  <div class="mb-3">
    <label for="description" class="form-label">description : </label>
    <textarea class="form-control" placeholder="Enter description" name="description">
  </textarea>
  </div>
  <div class="mb-3">
    <label for="file" class="form-label">image : </label>
    <input type="file" class="form-control" name="imageFile" accept="image/png, image/jpeg, image/jpg, image/gif">
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
</form>


 <jsp:include page="../footer.jsp"/>