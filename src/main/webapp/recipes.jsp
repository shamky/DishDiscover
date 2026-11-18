<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.amogh.dishdiscover.model.Recipe" %>
<%@ page import="com.amogh.dishdiscover.model.User" %>
<%
  String ctx=request.getContextPath(); User user=(User)session.getAttribute("user");
  String keyword=(String)request.getAttribute("keyword");
  List<Recipe> recipes=(List<Recipe>)request.getAttribute("recipes");
  Boolean categoryMode=(Boolean)request.getAttribute("categoryMode");
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Explore recipes · DishDiscover</title>
    <link rel="stylesheet" href="<%=ctx%>/assets/site.css">
  </head>
<body>
  <header class="site-header">
    <a class="brand" href="<%=ctx%>/home">
      <span class="brand-mark">✿</span>DishDiscover
    </a>
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
    <h1 class="page-heading">
      <%-- like it changes according to where the req comes from --%>
      <%=Boolean.TRUE.equals(categoryMode)?"Explore ":"Search results for "%>
      <%=keyword==null?"recipes":keyword%>
    </h1>
    <p class="lead">Find something lovely to cook next.</p>

    <form class="search-form" action="<%=ctx%>/search" method="get">
      <input
      class="search-input" name="keyword"
      value="<%=keyword==null?"":keyword%>"
      placeholder="Search recipes or ingredients..." required
      >
      <button type="submit">Search</button>
    </form>

    <%
      if(recipes==null||recipes.isEmpty()){
      %>
      <div class="empty-state">
        <h2>No recipes found</h2>
        <p>Try another search or browse our discoveries.</p>
        <a class="btn" href="<%=ctx%>/home#discover">Back to discover</a>
      </div>
      <%
      }
    else{ %>
    <div class="recipe-grid">
      <% for(Recipe recipe:recipes){ %>
      <article class="recipe-card">
        <a href="<%=ctx%>/recipeDetails?id=<%=recipe.getId()%>">
          <img
          loading="lazy"
          src="<%=recipe.getThumbnail()%>"
          alt="<%=recipe.getName()%>"
          >
          <div class="recipe-info">

            <% if(recipe.getCategory()!=null){ %>
            <span class="recipe-tag">
              <%=recipe.getCategory()%>
            </span>
            <%
            } %>

            <div class="recipe-title">
              <%=recipe.getName()%>
            </div>
            <span class="recipe-cta">View recipe →</span>
          </div>
        </a>
        <div class="recipe-info recipe-info-actions">
          <form action="<%=ctx%>/favorites" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="mealId" value="<%=recipe.getId()%>">
            <button type="submit">♡ Save to favorites</button>
          </form>
        </div>
      </article>
      <%
      } %>
    </div>
    <%
    } %>
  </main>

  <footer class="site-footer">
    <div class="footer-inner">
      <a class="brand" href="<%=ctx%>/home">✿ DishDiscover</a>
      <span>Recipe data provided by TheMealDB.</span>
    </div>
  </footer>
</body>
</html>
