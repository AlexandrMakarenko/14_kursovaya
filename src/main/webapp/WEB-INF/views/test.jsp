<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Тестовая страница</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />
    
    <div class="container mt-5">
        <h1>Тестовая страница</h1>
        <p>Эта страница успешно загружена.</p>
        <p>Текущее время: <%= new java.util.Date() %></p>
        <a href="test-db" class="btn btn-primary">Проверить подключение к базе данных</a>
    </div>
    
    <jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html> 