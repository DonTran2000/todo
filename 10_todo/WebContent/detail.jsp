<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta content="text/html" charset="utf-8">
<title>TODOタスクの詳細画面</title>
<script type="text/javascript">
	window.onload = function() {
		var status = document.getElementById("status");
		status.selectedIndex = $
		{
			vo.status
		}
		;
	};
</script>
</head>
<body>
	<form>
		<table border="1">
			<tr>
				<th>番号</th>
				<td width="60"><c:choose>
						<c:when test="${vo.id > 0 }">
							<c:out value="${vo.id }" />
						</c:when>
						<c:otherwise>
						(新規)
					</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<th>タイトル</th>
				<td><input type="text" name="title"
					value="<c:out value="${vo.title }" />" size="20" /></td>
			</tr>
			<tr>
				<th>タスク内容</th>
				<td><input type="text" name="task"
					value="<c:out value="${vo.task }" />" maxlength="128" size="60" /></td>
			</tr>
			<tr>
				<th>期限</th>
				<td>
				<c:choose>
					<c:when test="${vo.limitdate != null }">
						<input type="text" name="limit" value="<c:out value="${vo.limitdate }" />" size="10">
					</c:when>
					
					<c:otherwise>
						<input type="text" name="limit" value="<fmt:formatDate value="${vo.limitdate }" pattern="yyyy-MM-dd" />" size="10" />
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<tr>
				<th>ユーザID</th>
				<td><input type="text" name="userid"
					value="<c:out value="${vo.userid }" />" size="16" /></td>
			</tr>
			<tr>
				<th>状況</th>
				<td><select name="status" id="status">
						<option value="0">未着手</option>
						<option value="1">着手</option>
						<option value="2">完了</option>
						<option value="3">凍結</option>
				</select></td>
			</tr>

			<c:if test="${vo.id != 0}">
				<tr>
					<th>添付ファイル</th>
					<td><a href="preUpload?id=<c:out value="${vo.id}" />">アップロード</a>
						<c:choose>
							<c:when test="${vo.filename != null}">
								<c:out value="${vo.filename}" />
							</c:when>
							<c:otherwise>
								添付ファイルはありません
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:if>
		</table>
		<input type="hidden" name="id" value="<c:out value="${ vo.id }" />" />
		<input type="hidden" name="token" value="<c:out value="${ token }" />"/>
		<input type="submit" value="登録する" formaction="register"
			formmethod="POST" /> <br />
		<c:if test="${vo.id > 0 }">
			<input type="submit" value="削除する" formaction="delete"
				formmethod="POST" />
			<input type="hidden" name="token" value="<c:out value="${ token }" />" />
		</c:if>
	</form>
	<br />
	<div style="color:red;">
	<c:forEach items="${errorMessages }" var="errorMessage">
		${errorMessage } <br/>
	</c:forEach>
	</div>
	•<a href="search">タスク一覧に戻る</a>
	<hr />
	<c:out value="${message}"></c:out>
</body>
</html>