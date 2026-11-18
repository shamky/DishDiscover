<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.amogh.dishdiscover.model.Recipe" %>
<%@ page import="com.amogh.dishdiscover.model.User" %>
<%-- this is like the detailed recipe page --%>
<%
  Recipe recipe=(Recipe)request.getAttribute("recipe");
  User user=(User)session.getAttribute("user"); String ctx=request.getContextPath();
  if(recipe==null){
    response.sendRedirect(ctx+"/home");return;
  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>
  <%=recipe.getName()%> · DishDiscover</title>
  <link rel="stylesheet" href="<%=ctx%>/assets/site.css">
</head>
<body>
  <header class="site-header">
    <a class="brand" href="<%=ctx%>/home">
    <span class="brand-mark">✿</span>DishDiscover</a>
    <nav class="nav-links">
      <a href="<%=ctx%>/home">Home</a>
      <a href="<%=ctx%>/home#discover">Discover</a>
      <% if(user!=null){ %>
      <a href="<%=ctx%>/favorites">Favorites</a>
      <% } %>
    </nav>
    <div class="nav-actions">
      <% if(user==null){ %>
      <a class="btn btn-outline" href="<%=ctx%>/login">Log in</a>
      <a class="btn" href="<%=ctx%>/register">Sign up</a>
      <% }else{ %>
      <a class="btn btn-soft" href="<%=ctx%>/profile">Profile</a>
      <form action="<%=ctx%>/logout" method="post">
        <button class="btn btn-outline" type="submit">Log out</button>
      </form>
      <% } %>
    </div>
  </header>

  <main class="page-wrap">
    <p>
      <a href="<%=ctx%>/home">← Back to discover</a>
    </p>
    <div class="details-layout">
      <div>
        <img class="details-image" src="<%=recipe.getThumbnail()%>" alt="<%=recipe.getName()%>">
      </div>
      <article>
        <% if(recipe.getCategory()!=null){ %>
        <span class="recipe-tag">
          <%=recipe.getCategory()%>
        </span>
        <% } %>
        <h1 class="page-heading">
          <%=recipe.getName()%>
        </h1>
        <p class="lead">
          <%=recipe.getArea()==null?"A recipe to discover":recipe.getArea()+" cuisine"%>
        </p>
        <div class="toolbar">
          <% if(user!=null){ %>
          <form action="<%=ctx%>/favorites" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="mealId" value="<%=recipe.getId()%>">
            <button type="submit">♡ Save to favorites</button>
          </form>
          <% }else{ %>
          <a class="btn" href="<%=ctx%>/login">Log in to save</a>
          <% } %>
          <a class="btn btn-soft" href="<%=ctx%>/favorites">My favorites</a>
        </div>
        <section class="panel instructions-panel">
          <h2>Instructions</h2>
          <p class="instructions">
            <%=recipe.getInstructions()==null?"Instructions are not available for this recipe.":recipe.getInstructions()%>
          </p>
        </section>
      </article>
    </div>
  </main>

  <footer class="site-footer">
    <div class="footer-inner">
      <a class="brand" href="<%=ctx%>/home">✿ DishDiscover</a>
      <span>Recipe data provided by TheMealDB.</span>
    </div>
  </footer>
</body>
</html>
