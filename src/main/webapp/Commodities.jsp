<%@ page import="Baloot.Commands.GetCommoditiesList" %>
<%@ page import="Baloot.View.CommodityListModel" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Baloot.Context.UserContext" %>


<%

    GetCommoditiesList action = new GetCommoditiesList();
    CommodityListModel commodities = action.handle();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Commodities</title>
    <style>
        table{
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
<a href="/">Home</a>
<p id="username">username: <%=UserContext.username%></p>
<br><br>
<form action="" method="POST">
    <label>Search:</label>
    <input type="text" name="search" value="">
    <button type="submit" name="action" value="search_by_category">Search By Cagtegory</button>
    <button type="submit" name="action" value="search_by_name">Search By Name</button>
    <button type="submit" name="action" value="clear">Clear Search</button>
</form>
<br><br>
<form action="" method="POST">
    <label>Sort By:</label>
    <button type="submit" name="action" value="sort_by_rate">Rate</button>
</form>
<br><br>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Provider Name</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
        <th>Links</th>
    </tr>
    <% for(int i = 0; i < commodities.commoditiesList.size(); i+=1) { %>
    <tr>
        <td><%=commodities.commoditiesList.get(i).id%></td>
        <td><%=commodities.commoditiesList.get(i).name%></td>
        <td><%=commodities.commoditiesList.get(i).providerId%></td>
        <td><%=commodities.commoditiesList.get(i).price%></td>
        <% List<String> categoryList = new ArrayList<>();
            for(String category : commodities.commoditiesList.get(i).categories) {
                categoryList.add(category);
            }
            String categoryString = String.join(", ", categoryList);
        %>
        <td><%= categoryString %></td>

        <td><%=commodities.commoditiesList.get(i).rating%></td>
        <td><%=commodities.commoditiesList.get(i).inStock%></td>
        <td><a href="/commodities/<%=commodities.commoditiesList.get(i).id%>">Link</a></td>
    </tr>
    <% } %>
</table>
</body>
</html>