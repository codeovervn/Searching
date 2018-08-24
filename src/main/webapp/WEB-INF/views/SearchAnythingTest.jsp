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
    .addPersonInfoDetail>table tr th {text-align: right;}
</style>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Test Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript">
        function createPerson() {
            $("#AddPersonInfo").show();
            $("#testthu").hide();
            $("#formMainData").hide();
            $("#exportData").hide();
        }
    </script>
    <script type="text/javascript">
    //$(document).ready(function() {
    //    $("#AddPersonInfo").hide();
    //    $("#AddPerson").click(function(e) {
    //        $("#AddPersonInfo").show();
    //        e.preventDefault();
    //    });
    //});
    </script>
      <script>
      $( function() {
          $( "#datepicker" ).datepicker();
             });
      </script>
</head>
<body>
    <%-- <div align="center" class="search">
        <form:form action="saveSearchAnythingTest" method="POST" modelAttribute="searchAnythingTest">
        <table>
            <tr>
                <td>Test Contents : </td>
                <td><form:input path="searchContents" placeholder="Nhập nội dung ..."/></td>
                <td colspan="2" align="center"><input type="submit" value="Collect Search Data"></td>
            </tr>
        </table>
        </form:form>
    </div> --%>
    <br><br>
    <a href="getJsondata">Get JSON Data</a>
    <br><br>
    <div id="testthu"><input type="submit" id ="AddPerson" name="AddPerson" value="Add Person" onclick="createPerson()"/></div>
    <br>
    <form id="AddPersonInfo" name="AddPersonInfo" action="/Searching/getJsondata" style="display:none;">
        <div class="addPersonInfoDetail">
            <table>
                <tr>
                    <th>ID</th>
                    <td><input></td>
                </tr>
                <tr>
                    <th>NAME</th>
                    <td><input></td>
                </tr>
                <tr>
                    <th>EMAIL</th>
                    <td><input></td>
                </tr>
                <tr>
                    <th>BIRTHDAY</th>
                    <td><input type="text" id="datepicker"></td>
                </tr>
                <tr>
                    <th>YEAR_OLD</th>
                    <td><input></td>
                </tr>
                <tr>
                    <th>JOB</th>
                    <td><input></td>
                </tr>
                <tr>
                    <th>COUNTRY</th>
                    <td><input></td>
                </tr>
                <tr>
                    <th>REMARKS</th>
                    <td><input></td>
                </tr>
                <tr>
                    <td colspan="2" style="padding-left: 160px">
                        <input type="submit" value="SUBMIT"  id ="AddPerson"/>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    <br>

    <c:if test="${not empty jsondataList}">
        <form id="formMainData" action="exportDataCSV">
            <table border ="1" style="border-collapse: collapse;">
                <thead>
                    <tr>
                        <th>_ID</th>
                        <th>_NAME</th>
                        <th>_EMAIL</th>
                        <th>_BIRTHDAY</th>
                        <th>_YEAR_OLD</th>
                        <th>_JOB</th>
                        <th>_COUNTRY</th>
                        <th>_REMARKS</th>
                        <th>_ACTIONS</th>
                    </tr>
                </thead>
                <c:forEach var="jsondataItem" items="${jsondataList}" varStatus="status">
                    <tr>
                        <td style="text-align: center">${jsondataItem.ID}</td>
                        <td>${jsondataItem.NAME}</td>
                        <td>${jsondataItem.EMAIL}</td>
                        <td>${jsondataItem.BIRTHDAY}</td>
                        <td style="text-align: center">${jsondataItem.YEAR_OLD}</td>
                        <td>${jsondataItem.JOB}</td>
                        <td>${jsondataItem.COUNTRY}</td>
                        <td>${jsondataItem.REMARKS}</td>
                        <td><a href="#">&nbsp;EDIT</a>&nbsp;&nbsp;||&nbsp;&nbsp;<a href="#">DELETE&nbsp;</a></td>
                    </tr>
                </c:forEach>
            </table>
            <br>
            <div id="exportData"><input type="submit" name="ExportData" value="Export Data CSV"></div>
        </form>
    </c:if>
    <br><br>
    <div>
        <a href="exportDataPdf"> Export Data PDF</a>
    </div>
    <%-- <br><br>
    <c:if test="${not empty searchAnythingTestList}">
    <div class="dung1">
        <table border ="1" style="border-collapse: collapse;">
            <thead>
                <tr>
                    <th>_ID</th>
                    <th>_KEY</th>
                    <th>_CONTENTS</th>
                    <th>_NUM</th>
                    <th>_DATE_CREATE</th>
                    <th>_DATE_UPDATE</th>
                    <th>_DEL_FLG</th>
                    <th>_PERSON_CD</th>
                    <th>_PERSON_NAME</th>
                </tr>
            </thead>
            <c:forEach var="searchAnythingTest" items="${searchAnythingTestList}" varStatus="status">
            <tr>
                <td>${searchAnythingTest.id}</td>
                <td>${searchAnythingTest.searchKey}</td>
                <td>${searchAnythingTest.searchContents}</td>
                <td>${searchAnythingTest.searchNum}</td>
                <td>${searchAnythingTest.dateCreateSearch}</td>
                <td>${searchAnythingTest.dateUpdateSearch}</td>
                <td>${searchAnythingTest.delFlg}</td>
                <td>${searchAnythingTest.searchPersonCd}</td>
                <td>${searchAnythingTest.searchPersonName}</td>
            </tr>
            </c:forEach>
        </table>
    </div>
    </c:if>

    <c:if test="${not empty listSearchAnythingTest}">
    <div align="center" class="searchlist">
        <h1>List Search Data</h1>
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
            <c:forEach var="searchAnythingTest" items="${listSearchAnythingTest}" varStatus="status">
            <tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
                <td style="text-align: center;">${status.index + 1}</td>
                <td style="text-align: center;">${searchAnythingTest.id}</td>
                <td>${searchAnythingTest.searchKey}</td>
                <td>${searchAnythingTest.searchContents}</td>
                <td style="text-align: center;">${searchAnythingTest.searchNum}</td>
                <td>${searchAnythingTest.dateCreateSearch}</td>
                <td>${searchAnythingTest.dateUpdateSearch}</td>
                <td style="text-align: center;">${searchAnythingTest.delFlg}</td>
                <td style="text-align: center;">
                    <a href="editSearchAnythingTest?id=${searchAnythingTest.id}">&nbsp;Edit</a>
                    &nbsp;&nbsp;||&nbsp;&nbsp;
                    <a href="skipSearchAnythingTest?id=${searchAnythingTest.id}">Skip</a>
                    &nbsp;&nbsp;||&nbsp;&nbsp;
                    <a href="copySearchAnythingTest?id=${searchAnythingTest.id}">Copy&nbsp;</a>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>
    </c:if>

    <c:if test="${not empty listSearchAnythingTestBk}">
    <div align="center" class="backlog">
        <h1>List BackLog Data</h1>
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
            <c:forEach var="backLog" items="${listSearchAnythingTestBk}" varStatus="status">
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
                    <a href="revertSearchAnythingTest?id=${backLog.id}">&nbsp;Revert</a>
                    &nbsp;&nbsp;||&nbsp;&nbsp;
                    <a href="deleteSearchAnythingTest?id=${backLog.id}">Delete&nbsp;</a>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>
    </c:if> --%>
</body>
</html>
