<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Внутренняя ошибка сервера - Система управления территориями</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error-container {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 20px;
            margin-top: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .error-code {
            font-size: 6rem;
            font-weight: bold;
            color: #dc3545;
        }
        .error-message {
            font-size: 1.5rem;
            color: #343a40;
            margin-bottom: 20px;
        }
        .error-details {
            background-color: #f1f1f1;
            border-radius: 5px;
            padding: 15px;
            max-height: 250px;
            overflow-y: auto;
            font-family: monospace;
            font-size: 0.9rem;
        }
        .robot-container {
            text-align: center;
            margin: 20px 0;
        }
        .robot-container object {
            max-width: 100%;
            height: auto;
        }
        .robot-sad {
            filter: hue-rotate(180deg);
        }
        .smoke-effect {
            position: absolute;
            top: -20px;
            left: 50%;
            transform: translateX(-50%);
            font-size: 2rem;
            animation: smokeAnimation 2s infinite;
        }
        @keyframes smokeAnimation {
            0% { opacity: 1; transform: translateY(0) translateX(-50%); }
            100% { opacity: 0; transform: translateY(-20px) translateX(-50%); }
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />
    
    <div class="container">
        <div class="error-container">
            <div class="row mb-3">
                <div class="col-md-6 d-flex align-items-center">
                    <div>
                        <div class="error-code text-center text-md-start">500</div>
                        <div class="error-message text-center text-md-start">Внутренняя ошибка сервера</div>
                        <p class="mb-4 text-center text-md-start">
                            <%
                                String errorMsg = (String)request.getAttribute("jakarta.servlet.error.message");
                                if (errorMsg == null) {
                                    errorMsg = "Произошла неожиданная ошибка при обработке вашего запроса.";
                                }
                                out.print(errorMsg);
                            %>
                        </p>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="robot-container position-relative">
                        <div class="smoke-effect">💨</div>
                        <object type="image/svg+xml" class="robot-sad" data="<%=request.getContextPath()%>/assets/images/error.svg" width="300" height="300">
                            <!-- Fallback если SVG не поддерживается -->
                            <img src="<%=request.getContextPath()%>/assets/images/error.svg" alt="Ошибка 500" width="300" height="300">
                        </object>
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="mb-0">Детали ошибки</h5>
                        </div>
                        <div class="card-body">
                            <p>Время: <%= new java.util.Date() %></p>
                            <p>URL запроса: 
                                <%
                                    String requestUri = (String)request.getAttribute("jakarta.servlet.error.request_uri");
                                    if (requestUri == null) {
                                        requestUri = "Неизвестно";
                                    }
                                    out.print(requestUri);
                                %>
                            </p>
                            
                            <%
                                Throwable throwable = (Throwable)request.getAttribute("jakarta.servlet.error.exception");
                                if (throwable != null) {
                            %>
                                <h6 class="mt-3">Исключение:</h6>
                                <p><%= throwable.getClass().getName() %>: <%= throwable.getMessage() %></p>
                                
                                <h6 class="mt-3">Стек вызовов:</h6>
                                <div class="error-details">
                                    <% 
                                        for(StackTraceElement element : throwable.getStackTrace()) { 
                                            out.println(element + "<br>");
                                        }
                                    %>
                                </div>
                            <% } %>
                        </div>
                    </div>
                    
                    <div class="alert alert-info" role="alert">
                        <p class="mb-0">Пожалуйста, попробуйте обновить страницу или вернитесь на главную страницу.</p>
                    </div>
                    
                    <div class="text-center">
                        <a href="<%=request.getContextPath()%>/" class="btn btn-primary me-2">Вернуться на главную</a>
                        <a href="<%=request.getContextPath()%>/test.jsp" class="btn btn-secondary me-2">Страница тестов</a>
                        <button class="btn btn-outline-primary" onclick="window.location.reload()">Обновить страницу</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 