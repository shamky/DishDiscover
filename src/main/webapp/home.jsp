<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,com.amogh.dishdiscover.model.User,com.amogh.dishdiscover.model.Recipe" %>
<% User currentUser = (User) session.getAttribute("user");
  List<Recipe> discoverRecipes = (List<Recipe>) request.getAttribute("discoverRecipes");
  List<String> categories = (List<String>) request.getAttribute("categories");
  List<Recipe> favoritePreview = (List<Recipe>) request.getAttribute("favoritePreview");
String ctx = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>DishDiscover | Find your next favorite</title>
  <link rel="stylesheet" href="<%=ctx%>/assets/home.css">
</head>
<body>

<header class="nav">
  <%-- left side --%>
  <a class="brand" href="<%=ctx%>/home">
  <span class="brand-mark">✿</span>DishDiscover</a>

  <%-- right side --%>
  <nav class="navlinks">
    <a href="<%=ctx%>/home">Home</a>
    <a href="#discover">Discover</a>
    <a href="#categories">Categories</a>
    <%-- dependingg on the user exist or not --%>
    <% if(currentUser != null){ %>
    <a href="<%=ctx%>/favorites">Favorites</a>
    <%
    } %>
  </nav>
  <div class="actions"> <%-- Authentication stuff --%>
    <% if(currentUser == null){ %>
    <a class="btn btn-outline" href="<%=ctx%>/login">Log in</a>
    <a class="btn btn-primary" href="<%=ctx%>/register">Sign up</a>
    <%
    }
  else { %>
  <a class="btn btn-soft" href="<%=ctx%>/profile">Profile</a>
  <form action="<%=ctx%>/logout" method="post" class="inline-form">
    <button class="btn btn-outline" type="submit">Log out</button>
  </form>
  <%
  } %>
  </div>
</header>

<main>
  <section class="hero">
    <div class="hero-panel">
      <div>
        <div class="eyebrow">A little inspiration for your kitchen</div>
        <h1>Find something<br>delicious.</h1>
        <p>Discover recipes, explore new flavors, and find your next favorite thing to cook — one dish at a time.</p>

        <form
          class="searchbar"
          action="<%=ctx%>/search"
          method="get"
          >
          <input name="keyword" aria-label="Search recipes" placeholder="Search recipes or ingredients..." required>
          <button class="btn btn-primary" type="submit">Search&nbsp; →</button>
        </form>
      </div>

      <div class="hero-art">
        <div class="plate">
          <div class="plate-inner"></div>
        </div>
        <div class="floating-note">Made for curious cooks ♡</div>
      </div>

    </div>
  </section>

  <section class="section" id="discover">
    <div class="section-head">
      <div>
        <h2>A little bit of everything</h2>
        <p class="section-sub">Random discoveries for whatever you're craving.</p>
      </div>
      <a class="text-link" href="<%=ctx%>/home">Refresh picks ↻</a>
    </div>
    <%-- if some kinda error occurs then mention here --%>
    <% if(request.getAttribute("homeError") != null){ %>
    <div class="error">
      <%=request.getAttribute("homeError")%>
    </div>
    <% } %>

    <% if(discoverRecipes != null && !discoverRecipes.isEmpty()){ %>
    <div class="recipe-grid">
      <% for(Recipe r:discoverRecipes){ %>
      <a class="recipe-card" href="<%=ctx%>/recipeDetails?id=<%=r.getId()%>">
        <img loading="lazy" src="<%=r.getThumbnail()%>" alt="<%=r.getName()%>">

        <div class="recipe-info">
          <span class="recipe-tag">
            <%=r.getCategory()!=null && !r.getCategory().isBlank()?r.getCategory():"Something to try"%>
          </span>
          <div class="recipe-title">
            <%=r.getName()%>
          </div>
          <span class="recipe-cta">View recipe →</span>
        </div>
      </a>
      <% } %>
    </div>

    <% } else { %>
    <div class="empty">Recipes aren't available right now. Try searching for a dish above, or refresh the page in a moment.</div>
    <% } %>
  </section>

  <section class="section" id="categories">
    <div class="section-head">
      <div>
        <h2>What are you in the mood for?</h2>
        <p class="section-sub">Pick a category and start exploring.</p>
      </div>
    </div>
    <div class="category-row">
      <%
        String[] fallback={"Beef","Chicken","Dessert","Pasta","Seafood","Vegetarian"};
        String[] icons={"🥘","🍗","🍰","🍝","🐟","🥦"};
        if(categories!=null&&!categories.isEmpty()){
          for(int i=0;i<categories.size();i++){
            String cat=categories.get(i);
          %>
          <a class="category" href="<%=ctx%>/category?name=<%=java.net.URLEncoder.encode(cat, "UTF-8")%>">
            <span class="category-icon">
              <%=icons[i%icons.length]%>
            </span>
            <%=cat%>
          </a>
          <%
          }
        }
        else{
          for(int i=0;i<fallback.length;i++){
          %>

          <a class="category" href="<%=ctx%>/category?name=<%=fallback[i]%>">
            <span class="category-icon">
              <%=icons[i]%>
            </span>
            <%=fallback[i]%>
          </a>
          <% }} %>
        </div>
  </section>

  <section class="section" id="account">
    <% if(currentUser != null){ %>
    <div class="section-head">
      <div>
        <h2>Your saved little collection</h2>
        <p class="section-sub">Recipes you want to come back to.</p>
      </div>
      <a class="text-link" href="<%=ctx%>/favorites">View all favorites →</a>
    </div>
    <% if(favoritePreview!=null&&!favoritePreview.isEmpty()){ %>
    <div class="recipe-grid">
      <% for(Recipe r:favoritePreview){ %>
      <a class="recipe-card" href="<%=ctx%>/recipeDetails?id=<%=r.getId()%>">
        <img loading="lazy" src="<%=r.getThumbnail()%>" alt="<%=r.getName()%>">
        <div class="recipe-info">
          <div class="recipe-title">
            <%=r.getName()%>
          </div>
          <span class="recipe-cta">Cook this again →</span>
        </div>
      </a>
      <% } %>
    </div>
    <% }else{ %>
    <div class="empty">Your favorites will show up here once you've saved a recipe. <a class="text-link" href="#discover">Explore recipes →</a>
    </div>
    <% } %>
    <% }else{ %>
    <div class="callout">
      <div>
        <h2>Found something you love?</h2>
        <p class="section-sub">Create an account to save recipes and build your own collection.</p>
      </div>
      <a class="btn btn-primary" href="<%=ctx%>/register">Join DishDiscover →</a>
    </div>
    <% } %>
  </section>
</main>

<footer class="footer">
  <div class="footer-inner">
    <a class="brand" href="<%=ctx%>/home">✿ DishDiscover</a>
    <span>Find a little inspiration. Make something lovely.</span>
    <span>Recipe data provided by TheMealDB.</span>
  </div>
</footer>
</body>
</html>
