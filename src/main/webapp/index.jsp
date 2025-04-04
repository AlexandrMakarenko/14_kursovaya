<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.util.DatabaseConnection" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Управление территориями и представителями</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 20px;
        }
        .header {
            margin-bottom: 30px;
        }
        .module-card {
            margin-bottom: 20px;
            transition: transform 0.3s;
        }
        .module-card:hover {
            transform: translateY(-5px);
        }
        .footer {
            margin-top: 50px;
            padding: 20px 0;
            background-color: #f8f9fa;
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
    </style>
</head>
<body>
    <div class="container">
        <div class="header text-center">
            <h1>Система управления территориями и представителями</h1>
            <p class="lead">Добро пожаловать в систему управления территориальной структурой</p>
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
        
        <!-- Модули системы -->
        <div class="row">
            <div class="col-md-12">
                <h2>Модули системы</h2>
            </div>
        </div>
        
        <div class="row">
            <!-- Регионы -->
            <div class="col-md-6">
                <div class="card module-card">
                    <div class="card-body">
                        <h5 class="card-title">Регионы</h5>
                        <p class="card-text">Управление регионами и их иерархической структурой.</p>
                        <a href="<%=request.getContextPath()%>/regions" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
            
            <!-- Территории -->
            <div class="col-md-6">
                <div class="card module-card">
                    <div class="card-body">
                        <h5 class="card-title">Территории</h5>
                        <p class="card-text">Управление территориями и их связями с регионами.</p>
                        <a href="<%=request.getContextPath()%>/territories" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row mt-4">
            <!-- Представители -->
            <div class="col-md-6">
                <div class="card module-card">
                    <div class="card-body">
                        <h5 class="card-title">Представители</h5>
                        <p class="card-text">Управление представителями и их контактной информацией.</p>
                        <a href="<%=request.getContextPath()%>/representatives" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
            
            <!-- Связи представителей и территорий -->
            <div class="col-md-6">
                <div class="card module-card">
                    <div class="card-body">
                        <h5 class="card-title">Назначения территорий</h5>
                        <p class="card-text">Управление связями между представителями и территориями.</p>
                        <a href="<%=request.getContextPath()%>/representative-territories" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="footer text-center">
            <p>&copy; 2025 Система управления территориями и представителями</p>
        </div>
    </div>
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 