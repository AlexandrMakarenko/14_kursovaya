<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Управление территориями и сотрудниками</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 20px;
        }
        .module-card {
            margin-bottom: 20px;
            transition: transform 0.3s;
        }
        .module-card:hover {
            transform: translateY(-5px);
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
    <jsp:include page="/WEB-INF/views/header.jsp" />
    <div class="container">
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
                        <a href="<%=request.getContextPath()%>/region" class="btn btn-primary">Перейти</a>
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
            <!-- Сотрудники -->
            <div class="col-md-6">
                <div class="card module-card">
                    <div class="card-body">
                        <h5 class="card-title">Сотрудники</h5>
                        <p class="card-text">Управление сотрудниками и их контактной информацией.</p>
                        <a href="<%=request.getContextPath()%>/employees" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
            
            <!-- Связи сотрудников и территорий -->
            <div class="col-md-6">
                <div class="card module-card">
                    <div class="card-body">
                        <h5 class="card-title">Назначения территорий</h5>
                        <p class="card-text">Управление связями между сотрудниками и территориями.</p>
                        <a href="<%=request.getContextPath()%>/employee-territories" class="btn btn-primary">Перейти</a>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row mt-4">
            <!-- Тестовая страница -->
            <div class="col-md-12">
                <div class="card module-card">
                    <div class="card-body">
                        <h5 class="card-title">Тестовая страница</h5>
                        <p class="card-text">Проверка состояния системы и подключения к базе данных.</p>
                        <a href="<%=request.getContextPath()%>/test.jsp" class="btn btn-secondary">Перейти</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 