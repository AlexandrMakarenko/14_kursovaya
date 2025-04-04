<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Управление представителями</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Управление представителями</h1>
        <a href="<c:url value='/'/>" class="btn btn-secondary mb-3">На главную</a>

        <!-- Форма добавления представителя -->
        <div class="card mb-4">
            <div class="card-header">
                <h5 class="mb-0">Добавить нового представителя</h5>
            </div>
            <div class="card-body">
                <form action="<c:url value='/representatives'/>" method="post">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="lastName">Фамилия</label>
                        <input type="text" class="form-control" id="lastName" name="lastName" required>
                    </div>
                    <div class="form-group">
                        <label for="firstName">Имя</label>
                        <input type="text" class="form-control" id="firstName" name="firstName" required>
                    </div>
                    <div class="form-group">
                        <label for="secondName">Отчество</label>
                        <input type="text" class="form-control" id="secondName" name="secondName">
                    </div>
                    <div class="form-group">
                        <label for="title">Должность</label>
                        <input type="text" class="form-control" id="title" name="title">
                    </div>
                    <div class="form-group">
                        <label for="birthDay">Дата рождения</label>
                        <input type="date" class="form-control" id="birthDay" name="birthDay">
                    </div>
                    <div class="form-group">
                        <label for="address">Адрес</label>
                        <input type="text" class="form-control" id="address" name="address">
                    </div>
                    <div class="form-group">
                        <label for="city">Город</label>
                        <input type="text" class="form-control" id="city" name="city">
                    </div>
                    <div class="form-group">
                        <label for="region">Регион</label>
                        <input type="text" class="form-control" id="region" name="region">
                    </div>
                    <div class="form-group">
                        <label for="phone">Телефон</label>
                        <input type="tel" class="form-control" id="phone" name="phone">
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email">
                    </div>
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </form>
            </div>
        </div>

        <!-- Таблица представителей -->
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Список представителей</h5>
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
                        <c:forEach items="${representatives}" var="representative">
                            <tr>
                                <td>${representative.id}</td>
                                <td>${representative.lastName} ${representative.firstName} ${representative.secondName}</td>
                                <td>${representative.title}</td>
                                <td><fmt:formatDate value="${representative.birthDay}" pattern="dd.MM.yyyy"/></td>
                                <td>${representative.address}, ${representative.city}, ${representative.region}</td>
                                <td>${representative.phone}<br>${representative.email}</td>
                                <td>
                                    <button class="btn btn-sm btn-warning" onclick="editRepresentative(${representative.id}, '${representative.lastName}', '${representative.firstName}', '${representative.secondName}', '${representative.title}', '${representative.birthDay}', '${representative.address}', '${representative.city}', '${representative.region}', '${representative.phone}', '${representative.email}')">
                                        Редактировать
                                    </button>
                                    <form action="<c:url value='/representatives'/>" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${representative.id}">
                                        <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Вы уверены?')">
                                            Удалить
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Модальное окно для редактирования -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Редактировать представителя</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="<c:url value='/representatives'/>" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" id="editId">
                        <div class="form-group">
                            <label for="editLastName">Фамилия</label>
                            <input type="text" class="form-control" id="editLastName" name="lastName" required>
                        </div>
                        <div class="form-group">
                            <label for="editFirstName">Имя</label>
                            <input type="text" class="form-control" id="editFirstName" name="firstName" required>
                        </div>
                        <div class="form-group">
                            <label for="editSecondName">Отчество</label>
                            <input type="text" class="form-control" id="editSecondName" name="secondName">
                        </div>
                        <div class="form-group">
                            <label for="editTitle">Должность</label>
                            <input type="text" class="form-control" id="editTitle" name="title">
                        </div>
                        <div class="form-group">
                            <label for="editBirthDay">Дата рождения</label>
                            <input type="date" class="form-control" id="editBirthDay" name="birthDay">
                        </div>
                        <div class="form-group">
                            <label for="editAddress">Адрес</label>
                            <input type="text" class="form-control" id="editAddress" name="address">
                        </div>
                        <div class="form-group">
                            <label for="editCity">Город</label>
                            <input type="text" class="form-control" id="editCity" name="city">
                        </div>
                        <div class="form-group">
                            <label for="editRegion">Регион</label>
                            <input type="text" class="form-control" id="editRegion" name="region">
                        </div>
                        <div class="form-group">
                            <label for="editPhone">Телефон</label>
                            <input type="tel" class="form-control" id="editPhone" name="phone">
                        </div>
                        <div class="form-group">
                            <label for="editEmail">Email</label>
                            <input type="email" class="form-control" id="editEmail" name="email">
                        </div>
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        function editRepresentative(id, lastName, firstName, secondName, title, birthDay, address, city, region, phone, email) {
            $('#editId').val(id);
            $('#editLastName').val(lastName);
            $('#editFirstName').val(firstName);
            $('#editSecondName').val(secondName);
            $('#editTitle').val(title);
            $('#editBirthDay').val(birthDay);
            $('#editAddress').val(address);
            $('#editCity').val(city);
            $('#editRegion').val(region);
            $('#editPhone').val(phone);
            $('#editEmail').val(email);
            $('#editModal').modal('show');
        }
    </script>
</body>
</html> 