<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Управление регионами</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Управление регионами</h1>
        <a href="<c:url value='/'/>" class="btn btn-secondary mb-3">На главную</a>

        <!-- Форма добавления региона -->
        <div class="card mb-4">
            <div class="card-header">
                <h5 class="mb-0">Добавить новый регион</h5>
            </div>
            <div class="card-body">
                <form action="<c:url value='/regions'/>" method="post">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="description">Описание региона</label>
                        <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </form>
            </div>
        </div>

        <!-- Таблица регионов -->
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
                                    <button class="btn btn-sm btn-warning" onclick="editRegion(${region.id}, '${region.description}')">
                                        Редактировать
                                    </button>
                                    <form action="<c:url value='/regions'/>" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${region.id}">
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
                    <h5 class="modal-title">Редактировать регион</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="<c:url value='/regions'/>" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" id="editId">
                        <div class="form-group">
                            <label for="editDescription">Описание региона</label>
                            <textarea class="form-control" id="editDescription" name="description" rows="3" required></textarea>
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
        function editRegion(id, description) {
            $('#editId').val(id);
            $('#editDescription').val(description);
            $('#editModal').modal('show');
        }
    </script>
</body>
</html> 