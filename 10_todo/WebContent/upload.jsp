<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TODOタスクのアップロード画面</title>
<script type="text/javascript">
</script>
</head>
<body>
アップロードするファイルを選択し、[アップロード]ボタンを押してください。
<form name="upload" action="../todo/upload" method="post" enctype="multipart/form-data">	<!-- 1 -->
<input type="file" name="uploadfile" />
<input type="submit" value="アップロード" />
<input type="hidden" name="id" value="<c:out value="${id}" />" />
</form>
・<a href="search">タスクの一覧へ戻る</a>
</body>
</html>