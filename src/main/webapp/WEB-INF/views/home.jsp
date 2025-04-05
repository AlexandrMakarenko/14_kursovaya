<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Закомментируем JSTL пока не разберемся с проблемой --%>
<%-- <%@ taglib prefix="c" uri="jakarta.tags.core" %> --%>
<!DOCTYPE html>
<html>
<head>
    <title>Система управления территориями</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />
    
    <div class="container mt-5">
        <h1 class="mb-4">Система управления территориями</h1>
        <div class="row">
            <div class="col-md-4">
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">Регионы</h5>
                        <p class="card-text">Управление регионами и их описанием</p>
                        <a href="regions" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">Территории</h5>
                        <p class="card-text">Управление территориями и их привязкой к регионам</p>
                        <a href="territories" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">Сотрудники</h5>
                        <p class="card-text">Управление сотрудниками и их контактной информацией</p>
                        <a href="employees" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">Назначение территорий</h5>
                        <p class="card-text">Управление назначением территорий сотрудникам</p>
                        <a href="employee-territories" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 