<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Страница не найдена - Система управления территориями</title>
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
        .robot-container {
            text-align: center;
        }
        .robot-container object {
            max-width: 100%;
            height: auto;
        }
        .magnifying-glass {
            position: absolute;
            top: 20px;
            right: 20px;
            transform: rotate(-20deg);
            font-size: 4rem;
            color: #0d6efd;
            opacity: 0.7;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />
    
    <div class="container">
        <div class="error-container">
            <div class="row">
                <div class="col-md-6 d-flex align-items-center justify-content-center">
                    <div class="text-center">
                        <div class="error-code">404</div>
                        <div class="error-message">Страница не найдена</div>
                        <p class="mb-4">
                            <%
                                String errorMsg = (String)request.getAttribute("jakarta.servlet.error.message");
                                if (errorMsg == null || errorMsg.isEmpty()) {
                                    out.print("Запрошенная страница не существует или была перемещена.");
                                } else {
                                    out.print(errorMsg);
                                }
                            %>
                        </p>
                        <div class="d-flex justify-content-center gap-2">
                            <a href="<%=request.getContextPath()%>/" class="btn btn-primary">Вернуться на главную</a>
                            <a href="<%=request.getContextPath()%>/test.jsp" class="btn btn-secondary">Страница тестов</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="robot-container position-relative">
                        <span class="magnifying-glass">🔍</span>
                        <object type="image/svg+xml" data="<%=request.getContextPath()%>/assets/images/error.svg" width="350" height="350">
                            <!-- Fallback если SVG не поддерживается -->
                            <img src="<%=request.getContextPath()%>/assets/images/error.svg" alt="Ошибка 404" width="350" height="350">
                        </object>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 