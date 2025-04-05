<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<html>
<head>
    <title>Перенаправление</title>
</head>
<body>
    <%
    // Если страница открыта напрямую или необработанная ошибка, перенаправляем на обработчик ошибок с кодом 404
    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Страница не найдена");
    %>
</body>
</html> 