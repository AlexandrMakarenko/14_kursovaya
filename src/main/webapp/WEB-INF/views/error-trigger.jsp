<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP Error Trigger</title>
</head>
<body>
    <h1>Эта страница вызовет ошибку в JSP</h1>
    
    <%
        // Намеренная ошибка в JSP - деление на ноль
        int result = 100 / 0;
    %>
    
    <p>Этот текст не будет отображаться из-за ошибки деления на ноль выше.</p>
</body>
</html> 