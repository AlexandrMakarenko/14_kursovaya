<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Управление назначениями территорий</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />
    
    <div class="container-fluid mt-4">
        <h2>Управление назначениями территорий</h2>
        <p>На этой странице вы можете управлять связями между сотрудниками и территориями.</p>
        
        <div class="row">
            <!-- Таблица связей (70% ширины) -->
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Список связей</h5>
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Сотрудник</th>
                                    <th>Территория</th>
                                    <th>Действия</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${employeeTerritories}" var="et">
                                    <tr>
                                        <td>
                                            <c:forEach items="${employees}" var="employee">
                                                <c:if test="${employee.id == et.employeeId}">
                                                    ${employee.lastName} ${employee.firstName} ${employee.secondName} - ${employee.title}
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach items="${territories}" var="territory">
                                                <c:if test="${territory.id == et.territoryId}">
                                                    ${territory.description}
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <button class="btn btn-sm btn-primary" onclick="editEmployeeTerritory(${et.employeeId}, ${et.territoryId})">
                                                Редактировать
                                            </button>
                                            <button class="btn btn-sm btn-danger" onclick="deleteEmployeeTerritory(${et.employeeId}, ${et.territoryId})">
                                                Удалить
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            
            <!-- Форма добавления связи (30% ширины) -->
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Добавить новую связь</h5>
                    </div>
                    <div class="card-body">
                        <form action="<c:url value='/employee-territories'/>" method="post">
                            <input type="hidden" name="action" value="add">
                            <div class="form-group mb-3">
                                <label for="employeeId">Сотрудник</label>
                                <select class="form-control" id="employeeId" name="employeeId" required>
                                    <c:forEach items="${employees}" var="employee">
                                        <option value="${employee.id}">${employee.lastName} ${employee.firstName} ${employee.secondName} - ${employee.title}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group mb-3">
                                <label for="territoryId">Территория</label>
                                <select class="form-control" id="territoryId" name="territoryId" required>
                                    <c:forEach items="${territories}" var="territory">
                                        <option value="${territory.id}">${territory.description}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Добавить</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Модальное окно для редактирования -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Редактировать назначение</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="<c:url value='/employee-territories'/>" method="post" id="editForm">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="oldEmployeeId" id="oldEmployeeId">
                        <input type="hidden" name="oldTerritoryId" id="oldTerritoryId">
                        
                        <div class="form-group mb-3">
                            <label for="editEmployeeId">Сотрудник</label>
                            <select class="form-control" id="editEmployeeId" name="employeeId" required>
                                <c:forEach items="${employees}" var="employee">
                                    <option value="${employee.id}">${employee.lastName} ${employee.firstName} ${employee.secondName} - ${employee.title}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group mb-3">
                            <label for="editTerritoryId">Территория</label>
                            <select class="form-control" id="editTerritoryId" name="territoryId" required>
                                <c:forEach items="${territories}" var="territory">
                                    <option value="${territory.id}">${territory.description}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="text-end">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отменить</button>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Форма для удаления (скрытая) -->
    <form id="deleteForm" action="<c:url value='/employee-territories'/>" method="post" style="display: none;">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" id="deleteEmployeeId" name="employeeId">
        <input type="hidden" id="deleteTerritoryId" name="territoryId">
    </form>
    
    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
    <!-- Bootstrap JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        // Глобальная переменная для модального окна
        let editModalInstance;
        
        // При загрузке страницы инициализируем модальное окно
        document.addEventListener('DOMContentLoaded', function() {
            const editModalEl = document.getElementById('editModal');
            editModalInstance = new bootstrap.Modal(editModalEl);
            
            // Добавляем обработчик события, который сбрасывает форму при закрытии модального окна
            editModalEl.addEventListener('hidden.bs.modal', function () {
                document.getElementById('editForm').reset();
            });
        });
        
        // Функция для редактирования связи сотрудник-территория
        function editEmployeeTerritory(employeeId, territoryId) {
            // Загружаем данные связи через AJAX
            fetch('${pageContext.request.contextPath}/employeeterritory-edit?employeeId=' + employeeId + '&territoryId=' + territoryId)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Ошибка при загрузке данных назначения');
                    }
                    return response.json();
                })
                .then(data => {
                    // Заполняем форму данными
                    document.getElementById('oldEmployeeId').value = data.employeeId;
                    document.getElementById('oldTerritoryId').value = data.territoryId;
                    document.getElementById('editEmployeeId').value = data.employeeId;
                    document.getElementById('editTerritoryId').value = data.territoryId;
                    
                    // Показываем модальное окно
                    editModalInstance.show();
                })
                .catch(error => {
                    alert('Произошла ошибка: ' + error.message);
                    console.error('Ошибка:', error);
                });
        }
        
        // Функция для удаления связи сотрудник-территория через AJAX
        function deleteEmployeeTerritory(employeeId, territoryId) {
            if (confirm('Вы уверены, что хотите удалить эту связь?')) {
                // Используем AJAX для удаления
                fetch('${pageContext.request.contextPath}/employeeterritory-delete', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'employeeId=' + employeeId + '&territoryId=' + territoryId
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert(data.message);
                        // Перезагружаем страницу, чтобы обновить таблицу
                        window.location.reload();
                    } else {
                        alert('Ошибка: ' + data.message);
                    }
                })
                .catch(error => {
                    alert('Произошла ошибка: ' + error.message);
                    console.error('Ошибка:', error);
                });
            }
        }
    </script>
</body>
</html> 