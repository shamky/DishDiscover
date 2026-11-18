<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.amogh.dishdiscover.model.User" %>
<% if (session.getAttribute("user") instanceof User) {
    response.sendRedirect(request.getContextPath()+"/home");
    return; 
  } 
  String ctx=request.getContextPath(); 
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Create account · DishDiscover</title>
    <link rel="stylesheet" href="<%=ctx%>/assets/site.css">
  </head>
<body>
  <header class="site-header">
    <a class="brand" href="<%=ctx%>/home">
    <span class="brand-mark">✿</span>DishDiscover</a>
    <%-- right --%>
    <nav class="nav-links">
      <a href="<%=ctx%>/home">Home</a>
      <a href="<%=ctx%>/home#discover">Discover</a>
    </nav>
    <div class="nav-actions">
      <a class="btn btn-outline" href="<%=ctx%>/login">Log in</a>
    </div>
  </header>

  <main class="page-wrap">
    <section class="form-card">
      <h1>Make yourself at home.</h1>
      <p class="intro">Create an account to save recipes and find them again later.</p>
      <% if(request.getAttribute("error")!=null){ %>
      <div class="message message-error">
        <%=request.getAttribute("error")%>
      </div>
      <% } %>
      <form action="<%=ctx%>/register" method="post">
        <div class="field">
          <label for="username">Username</label>
          <input id="username" name="username" minlength="3" maxlength="30" pattern="[A-Za-z0-9_.-]+" autocomplete="username" required>
        </div>
        <div class="field">
          <label for="email">Email</label>
          <input id="email" name="email" type="email" autocomplete="email" required>
        </div>
        <div class="field">
          <label for="password">Password</label>
          <input id="password" name="password" type="password" minlength="8" autocomplete="new-password" required>
          <span class="muted small">Use at least 8 characters.</span>
        </div>
        <button type="submit">Create account</button>
      </form>
      <p class="form-foot">Already have an account? <a href="<%=ctx%>/login">Log in</a>
      </p>
    </section>
  </main>

  <footer class="site-footer">
    <div class="footer-inner">
      <a class="brand" href="<%=ctx%>/home">✿ DishDiscover</a>
      <span>Recipe data provided by TheMealDB.</span>
    </div>
  </footer>
</body>
</html>
