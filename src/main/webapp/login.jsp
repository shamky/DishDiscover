<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.amogh.dishdiscover.model.User" %>
<% if (session.getAttribute("user") instanceof User) { response.sendRedirect(request.getContextPath()+"/home"); return; } String ctx=request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Log in · DishDiscover</title>
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
    <a class="btn btn-soft" href="<%=ctx%>/register">Sign up</a>
  </div>
</header>

<main class="page-wrap">
  <section class="form-card">
    <h1>Welcome back.</h1>
    <p class="intro">Sign in to return to your saved little collection.</p>
    <% if(request.getAttribute("error")!=null){ %>
    <div class="message message-error">
      <%=request.getAttribute("error")%>
    </div>
    <% } %>
    <form action="<%=ctx%>/login" method="post">
      <div class="field">
        <label for="username">Username</label>
        <input id="username" name="username" autocomplete="username" required>
      </div>
      <div class="field">
        <label for="password">Password</label>
        <input id="password" name="password" type="password" autocomplete="current-password" required>
      </div>
      <button type="submit">Log in</button>
    </form>
    <p class="form-foot">New to DishDiscover? <a href="<%=ctx%>/register">Create an account</a>
    </p>
  </section>
</main>

<footer class="site-footer">
  <div class="footer-inner">
    <a class="brand" href="<%=ctx%>/home">✿ DishDiscover</a>
    <span>Find a little inspiration. Make something lovely.</span>
  </div>
</footer>
</body>
</html>
