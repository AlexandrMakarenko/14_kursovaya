<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Управление сотрудниками</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />
    
    <div class="container-fluid mt-4">
        <h2>Управление сотрудниками</h2>
        <p>На этой странице вы можете управлять информацией о сотрудниках компании.</p>
        
        <div class="row">
            <!-- Таблица сотрудников (70% ширины) -->
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Список сотрудников</h5>
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>ФИО</th>
                                    <th>Должность</th>
                                    <th>Дата рождения</th>
                                    <th>Адрес</th>
                                    <th>Контактная информация</th>
                                    <th>Действия</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${employees}" var="emp">
                                    <tr>
                                        <td>${emp.id}</td>
                                        <td>${emp.lastName} ${emp.firstName} ${emp.secondName}</td>
                                        <td>${emp.title}</td>
                                        <td>
                                            <fmt:formatDate value="${emp.birthDay}" pattern="dd.MM.yyyy" />
                                        </td>
                                        <td>${emp.address}, ${emp.city}, ${emp.region}</td>
                                        <td>
                                            <div>Телефон: ${emp.phone}</div>
                                            <div>Email: ${emp.email}</div>
                                        </td>
                                        <td>
                                            <div class="btn-group">
                                                <button class="btn btn-sm btn-primary" onclick="editEmployee(${emp.id})">Редактировать</button>
                                                <button class="btn btn-sm btn-danger" onclick="deleteEmployee(${emp.id})">Удалить</button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            
            <!-- Форма добавления сотрудника (30% ширины) -->
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Добавить нового сотрудника</h5>
                    </div>
                    <div class="card-body">
                        <form action="<c:url value='/employees'/>" method="post">
                            <input type="hidden" name="action" value="add">
                            
                            <div class="form-group mb-3">
                                <label for="lastName">Фамилия</label>
                                <input type="text" class="form-control" id="lastName" name="lastName" required>
                            </div>
                            
                            <div class="form-group mb-3">
                                <label for="firstName">Имя</label>
                                <input type="text" class="form-control" id="firstName" name="firstName" required>
                            </div>
                            
                            <div class="form-group mb-3">
                                <label for="secondName">Отчество</label>
                                <input type="text" class="form-control" id="secondName" name="secondName">
                            </div>
                            
                            <div class="form-group mb-3">
                                <label for="title">Должность</label>
                                <input type="text" class="form-control" id="title" name="title" required>
                            </div>
                            
                            <div class="form-group mb-3">
                                <label for="birthDay">Дата рождения</label>
                                <input type="date" class="form-control" id="birthDay" name="birthDay">
                            </div>
                            
                            <div class="form-group mb-3">
                                <label for="address">Адрес</label>
                                <input type="text" class="form-control" id="address" name="address">
                            </div>
                            
                            <div class="form-group mb-3">
                                <label for="city">Город</label>
                                <input type="text" class="form-control" id="city" name="city">
                            </div>
                            
                            <div class="form-group mb-3">
                                <label for="region">Регион</label>
                                <input type="text" class="form-control" id="region" name="region">
                            </div>
                            
                            <div class="form-group mb-3">
                                <label for="phone">Телефон</label>
                                <input type="tel" class="form-control" id="phone" name="phone">
                            </div>
                            
                            <div class="form-group mb-3">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email">
                            </div>
                            
                            <button type="submit" class="btn btn-primary">Добавить</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Модальное окно для редактирования сотрудника -->
    <div class="modal fade" id="editEmployeeModal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Редактировать сотрудника</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="<c:url value='/employees'/>" method="post" id="editEmployeeForm">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" id="editId">
                        
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group mb-3">
                                    <label for="editLastName">Фамилия</label>
                                    <input type="text" class="form-control" id="editLastName" name="lastName" required>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group mb-3">
                                    <label for="editFirstName">Имя</label>
                                    <input type="text" class="form-control" id="editFirstName" name="firstName" required>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group mb-3">
                                    <label for="editSecondName">Отчество</label>
                                    <input type="text" class="form-control" id="editSecondName" name="secondName">
                                </div>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label for="editTitle">Должность</label>
                                    <input type="text" class="form-control" id="editTitle" name="title" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label for="editBirthDay">Дата рождения</label>
                                    <input type="date" class="form-control" id="editBirthDay" name="birthDay">
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group mb-3">
                            <label for="editAddress">Адрес</label>
                            <input type="text" class="form-control" id="editAddress" name="address">
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label for="editCity">Город</label>
                                    <input type="text" class="form-control" id="editCity" name="city">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label for="editRegion">Регион</label>
                                    <input type="text" class="form-control" id="editRegion" name="region">
                                </div>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label for="editPhone">Телефон</label>
                                    <input type="tel" class="form-control" id="editPhone" name="phone">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label for="editEmail">Email</label>
                                    <input type="email" class="form-control" id="editEmail" name="email">
                                </div>
                            </div>
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
    
    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
    <!-- Bootstrap JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        // Глобальная переменная для модального окна
        let editModalInstance;
        
        // При загрузке страницы инициализируем модальное окно
        document.addEventListener('DOMContentLoaded', function() {
            const editModalEl = document.getElementById('editEmployeeModal');
            editModalInstance = new bootstrap.Modal(editModalEl);
            
            // Добавляем обработчик события, который сбрасывает форму при закрытии модального окна
            editModalEl.addEventListener('hidden.bs.modal', function () {
                document.getElementById('editEmployeeForm').reset();
            });
        });
        
        // Функция для редактирования сотрудника
        function editEmployee(id) {
            // Загружаем данные сотрудника через AJAX
            fetch('${pageContext.request.contextPath}/employee-edit?id=' + id)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Ошибка при загрузке данных сотрудника');
                    }
                    return response.json();
                })
                .then(data => {
                    // Заполняем форму данными сотрудника
                    document.getElementById('editId').value = data.id;
                    document.getElementById('editLastName').value = data.lastName || '';
                    document.getElementById('editFirstName').value = data.firstName || '';
                    document.getElementById('editSecondName').value = data.secondName || '';
                    document.getElementById('editTitle').value = data.title || '';
                    document.getElementById('editBirthDay').value = data.birthDay || '';
                    document.getElementById('editAddress').value = data.address || '';
                    document.getElementById('editCity').value = data.city || '';
                    document.getElementById('editRegion').value = data.region || '';
                    document.getElementById('editPhone').value = data.phone || '';
                    document.getElementById('editEmail').value = data.email || '';
                    
                    // Показываем модальное окно
                    editModalInstance.show();
                })
                .catch(error => {
                    alert('Произошла ошибка: ' + error.message);
                    console.error('Ошибка:', error);
                });
        }
        
        // Функция для удаления сотрудника через AJAX
        function deleteEmployee(id) {
            if (confirm('Вы уверены, что хотите удалить этого сотрудника?')) {
                // Используем AJAX для удаления
                fetch('${pageContext.request.contextPath}/employee-delete', {
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