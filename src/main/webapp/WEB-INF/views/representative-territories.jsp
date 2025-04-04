<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Управление связями представителей и территорий</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Управление связями представителей и территорий</h1>
        <a href="<c:url value='/'/>" class="btn btn-secondary mb-3">На главную</a>

        <!-- Форма добавления связи -->
        <div class="card mb-4">
            <div class="card-header">
                <h5 class="mb-0">Добавить новую связь</h5>
            </div>
            <div class="card-body">
                <form action="<c:url value='/representative-territories'/>" method="post">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="employeeId">Представитель</label>
                        <select class="form-control" id="employeeId" name="employeeId" required>
                            <c:forEach items="${representatives}" var="representative">
                                <option value="${representative.id}">${representative.lastName} ${representative.firstName} ${representative.secondName} - ${representative.title}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
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

        <!-- Таблица связей -->
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Список связей</h5>
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Представитель</th>
                            <th>Территория</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${representativeTerritories}" var="rt">
                            <tr>
                                <td>
                                    <c:forEach items="${representatives}" var="representative">
                                        <c:if test="${representative.id == rt.employeeId}">
                                            ${representative.lastName} ${representative.firstName} ${representative.secondName} - ${representative.title}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:forEach items="${territories}" var="territory">
                                        <c:if test="${territory.id == rt.territoryId}">
                                            ${territory.description}
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>
                                    <form action="<c:url value='/representative-territories'/>" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="employeeId" value="${rt.employeeId}">
                                        <input type="hidden" name="territoryId" value="${rt.territoryId}">
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

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html> 