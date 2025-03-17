<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="Laptop Shop" />
                    <title>Update an order</title>
                    <link href="/css/styles.css" rel="stylesheet" />
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                        crossorigin="anonymous"></script>

                </head>

                <body class="sb-nav-fixed">
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <jsp:include page="../layout/sidebar.jsp" />
                        <div id="layoutSidenav_content">
                            <main>
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Order update</h3>
                                            <hr />
                                            <div class="d-flex justify-content-between">
                                                <p>Order with id = ${currentOrder.id}</p>
                                                <p>Total price:
                                                    <fmt:formatNumber type="number"
                                                        value="${currentOrder.totalPrice}" />
                                                    đ
                                                </p>
                                            </div>
                                            <form:form method="post" action="/admin/order/update"
                                                modelAttribute="currentOrder">
                                                <div class="row g-3 mt-3">
                                                    <div class="col">
                                                        <label class="form-label">User</label>
                                                        <form:input type="input" class="form-control"
                                                            path="receiverName" disabled="true" />
                                                    </div>
                                                    <div class="col">
                                                        <label class="form-label">Status</label>
                                                        <form:select class="form-select" path="status">
                                                            <form:option value="COMPLETED">COMPLETED</form:option>
                                                            <form:option value="PENDING">PENDING</form:option>
                                                            <form:option value="SHIPPING">SHIPPING</form:option>
                                                            <form:option value="CANCELED">CANCELED</form:option>
                                                        </form:select>

                                                    </div>
                                                </div>

                                                <div style="display: none" class="mb-3">
                                                    <label class="form-label">ID</label>
                                                    <form:input type="text" class="form-control" path="id" />
                                                </div>
                                                <button type="submit" class="btn btn-warning mt-4">Save</button>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                            </main>
                            <jsp:include page="../layout/footer.jsp" />
                        </div>
                    </div>




                </body>

                </html>