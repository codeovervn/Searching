<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<style type="text/css">
    table thead tr th { border: solid #000 1px; background-color:#BDB76B}
    .table-striped>tbody>tr:nth-of-type(odd){background-color:#F5FFFA}
</style>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Searching Manager Page</title>
</head>
<body>
    <div align="center" class="search">
        <form:form action="saveSearchAnything" method="POST" modelAttribute="searchAnything">
        <table>
            <tr>
                <td>Contents : </td>
                <td><form:input path="searchContents" placeholder="Nhập nội dung ..."/></td>
                <td colspan="2" align="center"><input type="submit" value=" Search "></td>
            </tr>
        </table>
        </form:form>
    </div>

    <c:if test="${not empty listSearchAnything}">
    <div align="center" class="searchlist">
        <h1>Searching List</h1>
        <h3><a href=addSearch>Tìm kiếm</a></h3>
        <table border="1" bordercolor="red" cellspacing="0" cellpadding="2" class="table table-striped">
            <thead>
                <tr>
                    <th style="width: 3%">No</th>
                    <th style="width: 3%">ID</th>
                    <th style="width: 10%">Key</th>
                    <th style="width: 23%">Contents</th>
                    <th style="width: 4%">Count</th>
                    <th style="width: 18%">Create Date</th>
                    <th style="width: 15%">Update Date</th>
                    <th style="width: 7%">Del Flg</th>
                    <th style="width: 15%">Action</th>
                </tr>
            </thead>
            <c:forEach var="searchAnything" items="${listSearchAnything}" varStatus="status">
            <tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
                <td style="text-align: center;">${status.index + 1}</td>
                <td style="text-align: center;">${searchAnything.id}</td>
                <td>${searchAnything.searchKey}</td>
                <td>${searchAnything.searchContents}</td>
                <td style="text-align: center;">${searchAnything.searchNum}</td>
                <td>${searchAnything.dateCreateSearch}</td>
                <td>${searchAnything.dateUpdateSearch}</td>
                <td style="text-align: center;">${searchAnything.delFlg}</td>
                <td style="text-align: center;">
                    <a href="editSearchAnything?id=${searchAnything.id}">&nbsp;Edit</a>
                    &nbsp;&nbsp;||&nbsp;&nbsp;
                    <a href="skipSearchAnything?id=${searchAnything.id}">Skip</a>
                    &nbsp;&nbsp;||&nbsp;&nbsp;
                    <a href="copySearchAnything?id=${searchAnything.id}">Copy&nbsp;</a>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>
    </c:if>

    <c:if test="${not empty listSearchAnythingBk}">
    <div align="center" class="backlog">
        <h1>Back log</h1>
        <table border="1" class="table table-striped">
            <thead>
                <tr>
                    <th style="width: 3%">No</th>
                    <th style="width: 3%">ID</th>
                    <th style="width: 10%">Key</th>
                    <th style="width: 23%">Contents</th>
                    <th style="width: 4%">Count</th>
                    <th style="width: 18%">Create Date</th>
                    <th style="width: 15%">Update Date</th>
                    <th style="width: 7%">Del Flg</th>
                    <th style="width: 12%">Action</th>
                </tr>
            </thead>
            <c:forEach var="backLog" items="${listSearchAnythingBk}" varStatus="status">
            <tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
                <td style="text-align: center;">${status.index + 1}</td>
                <td style="text-align: center;">${backLog.id}</td>
                <td>${backLog.searchKey}</td>
                <td>${backLog.searchContents}</td>
                <td style="text-align: center;">${backLog.searchNum}</td>
                <td>${backLog.dateCreateSearch}</td>
                <td>${backLog.dateUpdateSearch}</td>
                <td style="text-align: center;">${backLog.delFlg}</td>
                <td style="text-align: center;">
                    <a href="revertSearchAnything?id=${backLog.id}">&nbsp;Revert</a>
                    &nbsp;&nbsp;||&nbsp;&nbsp;
                    <a href="deleteSearchAnything?id=${backLog.id}">Delete&nbsp;</a>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>
    </c:if>
</body>
</html>
