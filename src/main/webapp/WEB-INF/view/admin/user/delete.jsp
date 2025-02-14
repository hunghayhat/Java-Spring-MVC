<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Delete</title>

                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

                <!-- Latest compiled JavaScript -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <!-- <link href="/css/demo.css" rel="stylesheet"> -->
            </head>

            <body>
                <div class="container mt-5">
                    <div class="row">
                        <div>
                            <h3>Delete user ${id}</h3>
                            <hr />
                            <h4>Do you want to delete user ${id}</h4>
                            <div class=" mt-3 justify-content-between">
                                <form:form method="post" action="/admin/user/delete" modelAttribute="newUser">
                                    <button type="submit" class="btn btn-danger">Delete</button>
                                    <div style="display: none" class="mb-3">
                                        <label class="form-label">ID</label>
                                        <form:input type="text" class="form-control" path="id" />
                                    </div>
                                </form:form>
                                <a href="/admin/user" class="btn btn-success">Back</a>
                            </div>
                        </div>
                    </div>
                </div>
            </body>

            </html>