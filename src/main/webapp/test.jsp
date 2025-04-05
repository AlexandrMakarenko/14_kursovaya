<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.util.DatabaseConnection" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Тестовая страница - Проверка системы</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 20px;
        }
        .header {
            margin-bottom: 30px;
        }
        .status-box {
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 20px;
        }
        .status-success {
            background-color: #d4edda;
            border-color: #c3e6cb;
            color: #155724;
        }
        .status-error {
            background-color: #f8d7da;
            border-color: #f5c6cb;
            color: #721c24;
        }
        .footer {
            margin-top: 50px;
            padding: 20px 0;
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />
    <div class="container">
        <div class="header text-center">
            <h1>Тестовая страница</h1>
            <p class="lead">Проверка состояния системы и подключения к базе данных</p>
        </div>
        
        <!-- Информация о статусе базы данных -->
        <div class="row">
            <div class="col-md-12">
                <h2>Статус системы</h2>
                <%
                    // Проверяем статус подключения к базе данных
                    boolean dbConnected = false;
                    String dbVersion = "Неизвестно";
                    String dbError = (String) application.getAttribute("dbInitError");
                    
                    try (Connection conn = DatabaseConnection.getConnection()) {
                        dbConnected = true;
                        
                        // Получаем версию PostgreSQL
                        try (Statement stmt = conn.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT version()");
                            if (rs.next()) {
                                dbVersion = rs.getString(1);
                            }
                        }
                    } catch (Exception e) {
                        dbError = e.getMessage();
                    }
                %>
                
                <% if (dbConnected) { %>
                    <div class="status-box status-success">
                        <h4><i class="bi bi-check-circle"></i> База данных подключена</h4>
                        <p>Версия базы данных: <%= dbVersion %></p>
                    </div>
                <% } else { %>
                    <div class="status-box status-error">
                        <h4><i class="bi bi-exclamation-triangle"></i> Ошибка подключения к базе данных</h4>
                        <p>Детали ошибки: <%= dbError != null ? dbError : "Неизвестная ошибка" %></p>
                        <p>Пожалуйста, убедитесь, что сервер базы данных запущен и настройки подключения верны.</p>
                    </div>
                <% } %>
            </div>
        </div>
        
        <!-- Информация о системе -->
        <div class="row mt-4">
            <div class="col-md-12">
                <h2>Информация о системе</h2>
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Параметры сервера</h5>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Java Version: <%= System.getProperty("java.version") %></li>
                            <li class="list-group-item">Java Vendor: <%= System.getProperty("java.vendor") %></li>
                            <li class="list-group-item">OS Name: <%= System.getProperty("os.name") %></li>
                            <li class="list-group-item">OS Version: <%= System.getProperty("os.version") %></li>
                            <li class="list-group-item">Server Info: <%= application.getServerInfo() %></li>
                            <li class="list-group-item">Servlet API Version: <%= application.getMajorVersion() %>.<%= application.getMinorVersion() %></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row mt-4">
            <div class="col-12 text-center">
                <a href="<%=request.getContextPath()%>/" class="btn btn-primary">Вернуться на главную</a>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 