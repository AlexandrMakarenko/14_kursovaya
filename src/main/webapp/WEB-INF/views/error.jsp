<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ошибка - Система управления территориями</title>
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
            font-size: 3.5rem;
            font-weight: bold;
            color: #dc3545;
        }
        .error-message {
            font-size: 1.2rem;
            color: #343a40;
            margin-bottom: 20px;
        }
        .error-details {
            background-color: #f1f1f1;
            border-radius: 5px;
            padding: 15px;
            max-height: 300px;
            overflow-y: auto;
            font-family: monospace;
        }
        .robot-container {
            text-align: center;
            margin-bottom: 20px;
        }
        .robot-container object {
            max-width: 100%;
            height: auto;
            max-height: 300px;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />
    
    <div class="container">
        <div class="error-container">
            <div class="row">
                <div class="col-md-5 d-flex align-items-center">
                    <div>
                        <div class="error-code text-center text-md-start">
                            <%
                                Integer statusCode = (Integer)request.getAttribute("jakarta.servlet.error.status_code");
                                if (statusCode == null) {
                                    out.print("Ошибка");
                                } else {
                                    out.print(statusCode);
                                }
                            %>
                        </div>
                        <h1 class="mt-3 text-center text-md-start">Что-то пошло не так</h1>
                        <div class="error-message text-center text-md-start">
                            <%
                                if (statusCode != null) {
                                    if (statusCode == 404) {
                                        out.print("Запрашиваемая страница не найдена");
                                    } else if (statusCode == 403) {
                                        out.print("Доступ запрещен");
                                    } else if (statusCode == 500) {
                                        out.print("Внутренняя ошибка сервера");
                                    } else {
                                        String errorMessage = (String)request.getAttribute("jakarta.servlet.error.message");
                                        if (errorMessage != null && !errorMessage.isEmpty()) {
                                            out.print(errorMessage);
                                        } else {
                                            out.print("Произошла ошибка при обработке запроса");
                                        }
                                    }
                                } else {
                                    String errorMessage = (String)request.getAttribute("jakarta.servlet.error.message");
                                    if (errorMessage != null && !errorMessage.isEmpty()) {
                                        out.print(errorMessage);
                                    } else {
                                        out.print("Произошла ошибка при обработке запроса");
                                    }
                                }
                            %>
                        </div>
                    </div>
                </div>
                <div class="col-md-7">
                    <div class="robot-container">
                        <object type="image/svg+xml" data="<%=request.getContextPath()%>/assets/images/error.svg" width="300" height="300">
                            <!-- Fallback если SVG не поддерживается -->
                            <img src="<%=request.getContextPath()%>/assets/images/error.svg" alt="Ошибка" width="300" height="300">
                        </object>
                    </div>
                </div>
            </div>
            
            <div class="row mt-4">
                <div class="col-md-12">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="mb-0">Детали ошибки</h5>
                        </div>
                        <div class="card-body">
                            <dl class="row">
                                <dt class="col-md-3">Код ошибки:</dt>
                                <dd class="col-md-9">
                                    <%
                                        if (statusCode != null) {
                                            out.print(statusCode);
                                        } else {
                                            out.print("Неизвестно");
                                        }
                                    %>
                                </dd>
                                
                                <dt class="col-md-3">URL запроса:</dt>
                                <dd class="col-md-9">
                                    <%
                                        String requestUri = (String)request.getAttribute("jakarta.servlet.error.request_uri");
                                        if (requestUri != null) {
                                            out.print(requestUri);
                                        } else {
                                            out.print("Неизвестно");
                                        }
                                    %>
                                </dd>
                                
                                <dt class="col-md-3">Сервлет:</dt>
                                <dd class="col-md-9">
                                    <%
                                        String servletName = (String)request.getAttribute("jakarta.servlet.error.servlet_name");
                                        if (servletName != null) {
                                            out.print(servletName);
                                        } else {
                                            out.print("Неизвестно");
                                        }
                                    %>
                                </dd>
                                
                                <%
                                    String errorMessage = (String)request.getAttribute("errorMessage");
                                    if (errorMessage != null && !errorMessage.isEmpty()) {
                                %>
                                <dt class="col-md-3">Сообщение:</dt>
                                <dd class="col-md-9"><%= errorMessage %></dd>
                                <% } %>
                                
                                <%
                                    Throwable throwable = (Throwable)request.getAttribute("jakarta.servlet.error.exception");
                                    if (throwable != null) {
                                %>
                                <dt class="col-md-3">Тип исключения:</dt>
                                <dd class="col-md-9"><%= throwable.getClass().getName() %></dd>
                                
                                <dt class="col-md-3">Сообщение:</dt>
                                <dd class="col-md-9"><%= throwable.getMessage() %></dd>
                                
                                <dt class="col-md-3">Стек вызовов:</dt>
                                <dd class="col-md-9">
                                    <div class="error-details">
                                        <% 
                                            for(StackTraceElement element : throwable.getStackTrace()) { 
                                                out.println(element + "<br>");
                                            }
                                        %>
                                    </div>
                                </dd>
                                <% } %>
                            </dl>
                        </div>
                    </div>
                    
                    <div class="text-center">
                        <a href="<%=request.getContextPath()%>/" class="btn btn-primary">Вернуться на главную</a>
                        <a href="<%=request.getContextPath()%>/test.jsp" class="btn btn-secondary ms-2">Вернуться на страницу тестов</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 