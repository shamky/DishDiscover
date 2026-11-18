<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.amogh.dishdiscover.model.Recipe" %>
<%@ page import="com.amogh.dishdiscover.model.User" %>
<% User user=(User)session.getAttribute("user"); if(user==null){response.sendRedirect(request.getContextPath()+"/login");return;} List<Recipe> favorites=(List<Recipe>)request.getAttribute("favorites"); String ctx=request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>My favorites · DishDiscover</title>
<link rel="stylesheet" href="<%=ctx%>/assets/site.css">
</head>
<body>
<header class="site-header">
  <a class="brand" href="<%=ctx%>/home">
  <span class="brand-mark">✿</span>DishDiscover</a>
  <nav class="nav-links">
    <a href="<%=ctx%>/home">Home</a>
    <a href="<%=ctx%>/home#discover">Discover</a>
  </nav>
  <div class="nav-actions">
    <a class="btn btn-soft" href="<%=ctx%>/profile">Profile</a>
    <form action="<%=ctx%>/logout" method="post">
      <button class="btn btn-outline" type="submit">Log out</button>
    </form>
  </div>
</header>
<main class="page-wrap">
  <h1 class="page-heading">Your saved collection</h1>
  <p class="lead">Recipes you want to come back to, all in one place.</p>
  <% if(favorites==null||favorites.isEmpty()){ %>
  <div class="empty-state">
    <h2>No favorites just yet.</h2>
    <p>When a recipe catches your eye, save it here for later.</p>
    <a class="btn" href="<%=ctx%>/home#discover">Explore recipes</a>
  </div>
  <% }else{ %>
  <div class="recipe-grid">
    <% for(Recipe recipe:favorites){ %>
    <article class="recipe-card">
      <a href="<%=ctx%>/recipeDetails?id=<%=recipe.getId()%>">
        <img loading="lazy" src="<%=recipe.getThumbnail()%>" alt="<%=recipe.getName()%>">
        <div class="recipe-info">
          <% if(recipe.getCategory()!=null){ %>
          <span class="recipe-tag">
            <%=recipe.getCategory()%>
          </span>
          <% } %>
          <div class="recipe-title">
            <%=recipe.getName()%>
          </div>
        </div>
      </a>
      <div class="recipe-info recipe-info-actions">
        <div class="recipe-actions">
          <a href="<%=ctx%>/recipeDetails?id=<%=recipe.getId()%>">View recipe →</a>
          <form action="<%=ctx%>/favorites" method="post">
            <input type="hidden" name="action" value="remove">
            <input type="hidden" name="mealId" value="<%=recipe.getId()%>">
            <button class="btn-danger" type="submit">Remove</button>
          </form>
        </div>
      </div>
    </article>
    <% } %>
  </div>
  <% } %>
</main>
<footer class="site-footer">
  <div class="footer-inner">
    <a class="brand" href="<%=ctx%>/home">✿ DishDiscover</a>
    <span>Recipe data provided by TheMealDB.</span>
  </div>
</footer>
</body>
</html>
