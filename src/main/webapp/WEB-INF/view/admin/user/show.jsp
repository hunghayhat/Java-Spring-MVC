<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Laptop shop" />
                <title>List Users</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container mt-5">
                                <div class="col-12 mx-auto">
                                    <div class="d-flex justify-content-between">
                                        <h3>Manage Users</h3>
                                        <a href="/admin/user/create" class="btn btn-primary">Create an user</a>
                                    </div>
                                </div>
                                <hr />

                                <table class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Email</th>
                                            <th scope="col">Full Name</th>
                                            <th scope="col">Role</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="user" items="${users1}">

                                            <tr>
                                                <th>${user.id}</th>
                                                <td>${user.email}</td>
                                                <td>${user.fullName}</td>
                                                <td>${user.role.name}</td>
                                                <td>
                                                    <a href="/admin/user/${user.id}" class="btn btn-success">View</a>
                                                    <a href="/admin/user/update/${user.id}"
                                                        class="btn btn-warning">Update</a>
                                                    <a href="/admin/user/delete/${user.id}"
                                                        class="btn btn-danger">Delete</a>
                                                </td>
                                            </tr>
                                        </c:forEach>


                                    </tbody>
                                </table>
                                <nav aria-label="...">
                                    <ul class="pagination justify-content-center">
                                        <li class="page-item">
                                            <a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                href="/admin/user?page=${currentPage - 1}">Previous</a>
                                        </li>
                                        <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                                            <li class="page-item">
                                                <a class="${loop.index eq currentPage ? 'active page-link' : 'page-link'}"
                                                    href="/admin/user?page=${loop.index}">
                                                    ${loop.index}
                                                </a>
                                            </li>

                                        </c:forEach>
                                        <li class="page-item">
                                            <a class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                href="/admin/user?page=${currentPage + 1}">Next</a>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>


            </body>

            </html>