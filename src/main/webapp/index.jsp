<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>ADE College Credit Union API</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #e8f0fe;
            margin: 40px;
        }
        h1 {
            color: #1a73e8;
        }
        .service-list,
        .endpoint-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .service-item {
            background-color: #ffffff;
            margin: 15px 0;
            padding: 20px;
            border: 1px solid #cfd8dc;
            border-radius: 5px;
        }
        .service-item h2 {
            margin: 0 0 10px;
            color: #0d47a1;
        }
        .endpoint-item {
            margin: 5px 0;
        }
        .endpoint-link {
            text-decoration: none;
            color: #1a237e;
        }
        .endpoint-link:hover {
            text-decoration: underline;
            color: #3949ab;
        }
    </style>
</head>
<body>
    <h1>ADE College Credit Union API</h1>
    <ul class="service-list">
        <li class="service-item">
            <h2>Students</h2>
            <ul class="endpoint-list">
                <li class="endpoint-item">
                    <a class="endpoint-link" href="api/students">XML Endpoint</a>
                </li>
                <li class="endpoint-item">
                    <a class="endpoint-link" href="api/students/json">JSON Endpoint</a>
                </li>
            </ul>
        </li>
        <li class="service-item">
            <h2>Loans</h2>
            <ul class="endpoint-list">
                <li class="endpoint-item">
                    <a class="endpoint-link" href="api/loans">XML Endpoint</a>
                </li>
                <li class="endpoint-item">
                    <a class="endpoint-link" href="api/loans/json">JSON Endpoint</a>
                </li>
            </ul>
        </li>
        <li class="service-item">
            <h2>Repayments</h2>
            <ul class="endpoint-list">
                <li class="endpoint-item">
                    <a class="endpoint-link" href="api/repayments">XML Endpoint</a>
                </li>
                <li class="endpoint-item">
                    <a class="endpoint-link" href="api/repayments/json">JSON Endpoint</a>
                </li>
            </ul>
        </li>
    </ul>
</body>
</html>