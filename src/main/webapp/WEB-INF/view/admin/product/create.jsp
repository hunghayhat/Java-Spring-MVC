<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Laptop Shop" />
                <title>Create a new product</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                <script>
                    $(document).ready(() => {
                        const productFile = $("#productFile");
                        productFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>

                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
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
                                        <h3>Create a product</h3>
                                        <hr />
                                        <form:form method="post" action="/admin/product/create"
                                            modelAttribute="newProduct" enctype="multipart/form-data">
                                            <div class="row g-3 mt-3">
                                                <div class="col">
                                                    <c:set var="errorName">
                                                        <form:errors path="name" cssClass="invalid-feedback" />
                                                    </c:set>
                                                    <label class="form-label">Name</label>
                                                    <form:input type="input"
                                                        class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                                                        path="name" />
                                                    ${errorName}
                                                </div>
                                                <div class="col">
                                                    <c:set var="errorPrice">
                                                        <form:errors path="price" cssClass="invalid-feedback" />
                                                    </c:set>
                                                    <label class="form-label">Price</label>
                                                    <form:input type="input"
                                                        class="form-control ${not empty errorPrice ? 'is-invalid' : ''}"
                                                        path="price" />
                                                    ${errorPrice}
                                                </div>
                                            </div>
                                            <div class="mb-3 mt-3">
                                                <label class="form-label">Detail description</label>
                                                <div class="form-floating">
                                                    <form:textarea class="form-control" type="input"
                                                        path="detailDesc" />
                                                </div>
                                            </div>
                                            <div class="row g-3 mt-3 mb-3">
                                                <div class="col">
                                                    <label class="form-label">Short description</label>
                                                    <form:input type="input" class="form-control" path="shortDesc" />
                                                </div>
                                                <div class="col">
                                                    <label class="form-label">Quantity</label>
                                                    <form:input type="input" class="form-control" path="quantity" />
                                                </div>
                                            </div>

                                            <div class="row g-3 mt-3 mb-3">
                                                <div class="col">
                                                    <label class="form-label">Factory</label>
                                                    <form:select class="form-select" path="factory">
                                                        <form:option value="Apple">Apple</form:option>
                                                        <form:option value="Asus">Asus</form:option>
                                                        <form:option value="Dell">Dell</form:option>
                                                        <form:option value="Acer">Acer</form:option>
                                                        <form:option value="Lenovo">Lenovo</form:option>
                                                        <form:option value="HP">HP</form:option>
                                                        <form:option value="MSI">MSI</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="col">
                                                    <label class="form-label">Target</label>
                                                    <form:select class="form-select" path="target">
                                                        <form:option value="Gaming">Gaming</form:option>
                                                        <form:option value="Design">Design</form:option>
                                                        <form:option value="Office">Office</form:option>
                                                    </form:select>
                                                </div>
                                            </div>

                                            <div class="col">
                                                <label for="productFile" class="form-label">Image</label>
                                                <input class="form-control" type="file" id="productFile"
                                                    accept=".png, .jpg, .jpeg" name="userFile" />
                                            </div>
                                            <div class="col-12 mb-3 mt-3">
                                                <img style="max-height: 250px; display: none;" alt="product preview"
                                                    id="productPreview">

                                                <button type="submit" class="btn btn-primary mt-3">Create</button>
                                        </form:form>
                                    </div>
                                </div>
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