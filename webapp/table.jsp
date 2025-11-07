<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@ page import="java.util.Collections" %> 
<%@ page import="java.util.List" %> 
<%@ page import="java.util.ArrayList" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page import="models.Point" %>

<a href="index.jsp" id="hidden-link" style="display: none;">Вернутсья для отправки формы</a>
<c:forEach var="point" items="${pointArr}">
  <tr>
    <td>${point.r}</td>
    <td>${point.x}</td>
    <td>${point.y}</td>
    <td>${point.result ? "Попадание" : "Промах"}</td>
    <td>${point.date}</td>
    <td>${point.nano}μs</td>
  </tr>
</c:forEach>
