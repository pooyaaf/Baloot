<%@ page import="Baloot.Context.UserContext" %>
<%@ page import="Baloot.View.CommodityShortModel" %>
<%@ page import="Baloot.Model.CommentReportModel" %>
<%@ page import="java.text.Format" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Baloot.Entity.Commodity" %>
<%@ page import="Baloot.View.CommodityListModel" %>
<%@ page import="Baloot.Model.CommodityModel" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>


<%
  CommodityShortModel commodity = (CommodityShortModel) request.getAttribute("commodity");
  CommodityListModel suggestedCommodities = (CommodityListModel) request.getAttribute("suggested");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Commodity</title>
  <style>
    li {
      padding: 5px;
    }
    table {
      width: 100%;
      text-align: center;
    }
  </style>
</head>
<body>
<span>username: <%=UserContext.username%></span>
<br>
<ul>
  <li id="id">Id: <%=commodity.commodityModel.id%> </li>
  <li id="name">Name: <%=commodity.commodityModel.name%></li>
  <li id="providerName">Provider Name: <%=commodity.commodityModel.providerId%></li>
  <li id="price">Price: <%=commodity.commodityModel.price%></li>
  <li id="categories">Categories:
    <% for(String category : commodity.commodityModel.categories) { %>
    <%=category%>,
    <% } %>
  </li>
  <li id="rating">Rating: <%=commodity.commodityModel.rating%></li>
  <li id="inStock">In Stock: <%=commodity.commodityModel.inStock%></li>
</ul>

<label>Add Your Comment:</label>
<form action="/addComment/<%=commodity.commodityModel.id%>" method="post">
  <input type="text" name="comment" value="" />
  <button type="submit">submit</button>
</form>
<br>
<form action="/rateCommodity/<%=commodity.commodityModel.id%>" method="POST">
  <label>Rate(between 1 and 10):</label>
  <input type="number" id="quantity" name="quantity" min="1" max="10">
  <button type="submit">Rate</button>
</form>
<br>
<form action="/addToBuyList/<%=commodity.commodityModel.id%>" method="POST">
  <button type="submit">Add to BuyList</button>
</form>
<br />
<table>
  <caption><h2>Comments</h2></caption>
  <tr>
    <th>username</th>
    <th>comment</th>
    <th>date</th>
    <th>likes</th>
    <th>dislikes</th>
  </tr>
  <% for(CommentReportModel comment : commodity.commentsList) { %>
  <tr>
    <td><%=comment.username%></td>
    <td><%=comment.text%></td>
    <%Format formatter = new SimpleDateFormat("yyyy-MM-dd");%>
    <td><%=formatter.format(comment.date)%></td>
    <td>
      <form action="/voteComment/<%=comment.id%>" method="POST">
        <label><%=comment.like%></label>
        <input
                id="form_comment_id_like"
                type="hidden"
                name="comment_id"
                value="1"
        />
        <button type="submit">like</button>
      </form>
    </td>
    <td>
      <form action="/voteComment/<%=comment.id%>" method="POST">
        <label><%=comment.dislike%></label>
        <input
                id="form_comment_id_dislike"
                type="hidden"
                name="comment_id"
                value="-1"
        />
        <button type="submit">dislike</button>
      </form>
    </td>
  </tr>
  <% } %>
</table>
<br><br>
<table>
  <caption><h2>Suggested Commodities</h2></caption>
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
  <% for(CommodityModel suggestedCommodity : suggestedCommodities.commoditiesList) { %>
  <tr>
    <td><%=suggestedCommodity.id%></td>
    <td><%=suggestedCommodity.name%></td>
    <td><%=suggestedCommodity.providerId%></td>
    <td><%=suggestedCommodity.price%></td>
    <% List<String> categoryList = new ArrayList<>();
      for(String category : suggestedCommodity.categories) {
        categoryList.add(category);
      }
      String categoryString = String.join(", ", categoryList);
    %>
    <td><%= categoryString %></td>

    <td><%=suggestedCommodity.rating%></td>
    <td><%=suggestedCommodity.inStock%></td>
    <td><a href="/commodities/<%=suggestedCommodity.id%>">Link</a></td>
  </tr>
  <% } %>
</table>
</body>
</html>
