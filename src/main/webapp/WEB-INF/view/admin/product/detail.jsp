<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="Laptop Shop" />
            <title>Product's detail</title>
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
                                    <h3>Product Detail with ID = ${id}</h3>
                                </div>
                            </div>
                            <hr />
                            <div class="card" style="width: 60%;">
                                <img src="/images/product/${product.image}" alt="ImagePreview">
                                <div class="card-header">
                                    Product Information
                                </div>

                                <ul class="list-group list-group-flush">

                                    <li class="list-group-item">Name: ${product.name}</li>
                                    <li class="list-group-item">Price: ${product.price}</li>
                                    <li class="list-group-item">Quantity: ${product.quantity}</li>
                                    <li class="list-group-item">Factory: ${product.factory}</li>
                                    <li class="list-group-item">Target: ${product.target}</li>
                                </ul>

                            </div>
                            <a class="mt-4 btn btn-success" href="/admin/product">Back</a>
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