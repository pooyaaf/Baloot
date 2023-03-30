<%@ page import="Baloot.Context.UserContext" %>
<%@page import="Baloot.View.BuyListModel"%>
<%@ page import="Baloot.Commands.GetBuyList" %>
<%@ page import="Baloot.View.CommodityShortModel" %>
<%@ page import="Baloot.Context.ContextManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%
//    GetBuyList getBuyList = new GetBuyList();
//    BuyListModel buyList = getBuyList.handle(UserContext.username);
    BuyListModel buyList = (BuyListModel) request.getAttribute("buyList");
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
    <li id="email">Email: <%=buyList.user.email%></li>
    <li id="birthDate">Birth Date: <%=buyList.user.birthDate%></li>
    <li id="address"><%=buyList.user.address%></li>
    <li id="credit">Credit: <%=buyList.user.credit%></li>
    <li>Current Buy List Price: <%=buyList.buyList.stream().mapToDouble(o -> o.commodityModel.price).sum()%></li>
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
    <% for (CommodityShortModel commodity : buyList.buyList) { %>
    <tr>
        <td><%=commodity.commodityModel.id%></td>
        <td><%=commodity.commodityModel.name%></td>
        <td><%=ContextManager.getInstance().getProvider(commodity.commodityModel.providerId).getName()%></td>
        <td><%=commodity.commodityModel.price%></td>
        <% List<String> categoryList = new ArrayList<>();
            for(String category : commodity.commodityModel.categories) {
                categoryList.add(category);
            }
            String categoryString = String.join(", ", categoryList);
        %>
        <td><%=categoryString%></td>
        <td><%=commodity.commodityModel.rating%></td>
        <td><%=commodity.commodityModel.inStock%></td>
        <td><a href="/commodities/<%=commodity.commodityModel.id%>>">Link</a></td>
        <td>
            <form action="" method="POST">
                <input id="form_commodity_id" type="hidden" name="commodityId" value="<%=commodity.commodityModel.id%>">
                <button type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody></table>
</body></html>