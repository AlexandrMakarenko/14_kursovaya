<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Управление регионами</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />
    
    <div class="container-fluid mt-4">
        <h1 class="mb-4">Управление регионами</h1>
        
        <div class="row">
            <!-- Таблица регионов (70% ширины) -->
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Список регионов</h5>
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Описание</th>
                                    <th>Действия</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${regions}" var="region">
                                    <tr>
                                        <td>${region.id}</td>
                                        <td>${region.description}</td>
                                        <td>
                                            <button class="btn btn-sm btn-warning" onclick="editRegion(${region.id})">
                                                Редактировать
                                            </button>
                                            <button class="btn btn-sm btn-danger" onclick="deleteRegion(${region.id})">
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
            
            <!-- Форма добавления региона (30% ширины) -->
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Добавить новый регион</h5>
                    </div>
                    <div class="card-body">
                        <form action="<c:url value='/region'/>" method="post">
                            <input type="hidden" name="action" value="add">
                            <div class="form-group mb-3">
                                <label for="description">Описание региона</label>
                                <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
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
                    <h5 class="modal-title">Редактировать регион</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="<c:url value='/region'/>" method="post" id="editForm">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" id="editId">
                        <div class="form-group mb-3">
                            <label for="editDescription">Описание региона</label>
                            <textarea class="form-control" id="editDescription" name="description" rows="3" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/footer.jsp" />

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
        
        // Функция для редактирования региона
        function editRegion(id) {
            // Загружаем данные региона через AJAX
            fetch('${pageContext.request.contextPath}/region-edit?id=' + id)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Ошибка при загрузке данных региона');
                    }
                    return response.json();
                })
                .then(data => {
                    // Заполняем форму данными региона
                    document.getElementById('editId').value = data.id;
                    document.getElementById('editDescription').value = data.description;
                    
                    // Показываем модальное окно
                    editModalInstance.show();
                })
                .catch(error => {
                    alert('Произошла ошибка: ' + error.message);
                    console.error('Ошибка:', error);
                });
        }
        
        // Функция для удаления региона через AJAX
        function deleteRegion(id) {
            if (confirm('Вы уверены, что хотите удалить этот регион?')) {
                // Используем AJAX для удаления
                fetch('${pageContext.request.contextPath}/region-delete', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'id=' + id
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