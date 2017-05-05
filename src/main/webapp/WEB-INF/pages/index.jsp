<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="styles/index.css" type="text/css" rel="stylesheet">
	<link href="styles/all.css" type="text/css" rel="stylesheet">
	<script src="js/criterii.js" type="text/javascript" ></script>
	<title>Spring Shop</title>
</head>
<body class="index-page">

	<jsp:include page="header.jsp" />
	
	<jsp:include page="menu.jsp" />
	
	<jsp:include page="criterii.jsp" />
	
	<table>
       <tr>
           <th>Nume produs</th>
           <th>Pret</th>
           <th></th>
       </tr>
       <c:forEach items="${paginationProducts.list}" var="products">
           <tr>
	           <td>${products.name}</td>
	           <td>
	              ${products.price} lei
	           </td>
	           <td>
	           		<a href="./detaliiProdus/${products.id}">
	           			<button>Vezi detalii</button>
	           		</a>
	           </td>
           </tr>
		</c:forEach>
   </table>
	
	<c:if test="${paginationProducts.totalPages > 1}">
       <div class="page-navigator">
          <c:forEach items="${paginationProducts.navigationPages}" var = "page">
              <c:if test="${page != -1 }">
                <a href="index?page=${page}" class="nav-item">${page}</a>
              </c:if>
              <c:if test="${page == -1 }">
                <span class="nav-item"> ... </span>
              </c:if>
          </c:forEach>
          
       </div>
   </c:if>
</body>
</html>