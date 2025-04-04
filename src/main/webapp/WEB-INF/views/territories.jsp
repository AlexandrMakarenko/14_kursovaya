<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Управление территориями</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Управление территориями</h1>
        <a href="<c:url value='/'/>" class="btn btn-secondary mb-3">На главную</a>

        <!-- Форма добавления территории -->
        <div class="card mb-4">
            <div class="card-header">
                <h5 class="mb-0">Добавить новую территорию</h5>
            </div>
            <div class="card-body">
                <form action="<c:url value='/territories'/>" method="post">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="description">Описание территории</label>
                        <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="regionId">Регион</label>
                        <select class="form-control" id="regionId" name="regionId" required>
                            <c:forEach items="${regions}" var="region">
                                <option value="${region.id}">${region.description}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </form>
            </div>
        </div>

        <!-- Таблица территорий -->
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Список территорий</h5>
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Описание</th>
                            <th>Регион</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${territories}" var="territory">
                            <tr>
                                <td>${territory.id}</td>
                                <td>${territory.description}</td>
                                <td>
                                    <c:forEach items="${regions}" var="region">
                                        <c:if test="${region.id == territory.regionId}">
                                            ${region.description}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    <button class="btn btn-sm btn-warning" onclick="editTerritory(${territory.id}, '${territory.description}', ${territory.regionId})">
                                        Редактировать
                                    </button>
                                    <form action="<c:url value='/territories'/>" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${territory.id}">
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
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Редактировать территорию</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="<c:url value='/territories'/>" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" id="editId">
                        <div class="form-group">
                            <label for="editDescription">Описание территории</label>
                            <textarea class="form-control" id="editDescription" name="description" rows="3" required></textarea>
                        </div>
                        <div class="form-group">
                            <label for="editRegionId">Регион</label>
                            <select class="form-control" id="editRegionId" name="regionId" required>
                                <c:forEach items="${regions}" var="region">
                                    <option value="${region.id}">${region.description}</option>
                                </c:forEach>
                            </select>
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
        function editTerritory(id, description, regionId) {
            $('#editId').val(id);
            $('#editDescription').val(description);
            $('#editRegionId').val(regionId);
            $('#editModal').modal('show');
        }
    </script>
</body>
</html> 