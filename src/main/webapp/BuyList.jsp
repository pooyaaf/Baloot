<%@ page import="Baloot.Context.UserContext" %>
<%@page import="Baloot.View.UserInfoModel"%>
<%@ page import="Baloot.Context.ContextManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="Baloot.Model.CommodityModel" %>

<%
    UserInfoModel buyList = (UserInfoModel) request.getAttribute("buyList");
%>

<html lang="en"><head>
    <meta charset="UTF-8">
    <title>User</title>
    <style>
        li {
            padding: 5px
        }
        table{
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
<ul>
    <li id="username">Username: <%=UserContext.username%></li>
    <li id="email">Email: <%=buyList.userModel.email%></li>
    <li id="birthDate">Birth Date: <%=buyList.userModel.birthDate%></li>
    <li id="address"><%=buyList.userModel.address%></li>
    <li id="credit">Credit: <%=buyList.userModel.credit%></li>
    <li>Current Buy List Price: <%=buyList.buyList.stream().mapToDouble(o -> o.price * o.inStock).sum()%></li>
    <li>
        <a href="/credit">Add Credit</a>
    </li>
    <li>
        <form action="" method="POST">
            <label>Submit & Pay</label>
            <input id="form_payment" type="hidden" name="userId" value="Farhad">
            <button type="submit">Payment</button>
        </form>
    </li>
</ul>
<table>
    <caption>
        <h2>Buy List</h2>
    </caption>
    <tbody><tr>
        <th>Id</th>
        <th>Name</th>
        <th>Provider Name</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
        <th></th>
        <th></th>
    </tr>
    <% for (CommodityModel commodity : buyList.buyList) { %>
    <tr>
        <td><%=commodity.id%></td>
        <td><%=commodity.name%></td>
        <td><%=ContextManager.getInstance().getProvider(commodity.providerId).getName()%></td>
        <td><%=commodity.price%></td>
        <% List<String> categoryList = new ArrayList<>();
            for(String category : commodity.categories) {
                categoryList.add(category);
            }
            String categoryString = String.join(", ", categoryList);
        %>
        <td><%=categoryString%></td>
        <td><%=commodity.rating%></td>
        <td><%=commodity.inStock%></td>
        <td><a href="/commodities/<%=commodity.id%>>">Link</a></td>
        <td>
            <form action="/removeFromBuyList" method="POST">
                <input id="form_commodity_id" type="hidden" name="commodityId" value="<%=commodity.id%>">
                <button type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody></table>
</body></html>