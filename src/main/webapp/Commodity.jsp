<%@ page import="Baloot.Model.CommodityModel" %>
<%@ page import="Baloot.Model.CommentViewModel" %>
<%@ page import="Baloot.Context.UserContext" %>


<%
  CommodityModel commodity = (CommodityModel) request.getAttribute("commodity");
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
  <li id="id">Id: <%=commodity.id%> </li>
  <li id="name">Name: <%=commodity.name%></li>
  <li id="providerName">Provider Name: <%=commodity.providerId%></li>
  <li id="price">Price: <%=commodity.price%></li>
  <li id="categories">Categories:
    <% for(String category : commodity.categories) { %>
    <%=category%>,
    <% } %>
  </li>
  <li id="rating">Rating: <%=commodity.rating%></li>
  <li id="inStock">In Stock: <%=commodity.inStock%></li>
</ul>

<label>Add Your Comment:</label>
<form action="" method="post">
  <input type="text" name="comment" value="" />
  <button type="submit">submit</button>
</form>
<br>
<form action="/rateCommodity/<%=commodity.id%>" method="POST">
  <label>Rate(between 1 and 10):</label>
  <input type="number" id="quantity" name="quantity" min="1" max="10">
  <button type="submit">Rate</button>
</form>
<br>
<form action="/addToBuyList/<%=commodity.id%>" method="POST">
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
  <tr>
    <td>user1</td>
    <td>Good</td>
    <td>2022-07-25</td>
    <td>
      <form action="" method="POST">
        <label>2</label>
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
      <form action="" method="POST">
        <label>1</label>
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
  <tr>
    <td>2341</td>
    <td>Galaxy S22</td>
    <td>Phone Provider No.1</td>
    <td>34000000</td>
    <td>Technology, Phone</td>
    <td>8.3</td>
    <td>17</td>
    <td><a href="/commodities/2341">Link</a></td>
  </tr>
</table>
</body>
</html>
