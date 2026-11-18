<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.amogh.dishdiscover.model.User" %>
<% User user=(User)session.getAttribute("user"); if(user==null){response.sendRedirect(request.getContextPath()+"/login");return;} String ctx=request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Profile settings · DishDiscover</title>
  <link rel="stylesheet" href="<%=ctx%>/assets/site.css">
</head>
<body>
<header class="site-header">
  <a class="brand" href="<%=ctx%>/home">
  <span class="brand-mark">✿</span>DishDiscover</a>
  <nav class="nav-links">
    <a href="<%=ctx%>/home">Home</a>
    <a href="<%=ctx%>/favorites">Favorites</a>
  </nav>
  <div class="nav-actions">
    <form action="<%=ctx%>/logout" method="post">
      <button class="btn btn-outline" type="submit">Log out</button>
    </form>
  </div>
</header>

<main class="page-wrap">
  <h1 class="page-heading">Your account</h1>
  <p class="lead">A little place to keep your details up to date.</p>
  <% if(request.getAttribute("error")!=null){ %>
  <div class="message message-error">
    <%=request.getAttribute("error")%>
  </div>
  <% } if(request.getAttribute("success")!=null){ %>
  <div class="message message-success">
    <%=request.getAttribute("success")%>
  </div>
  <% } %>
  <div class="profile-grid">
    <aside class="panel">
      <div class="profile-avatar">
        <%=user.getUsername().substring(0,1).toUpperCase()%>
      </div>
      <h2>
        <%=user.getUsername()%>
      </h2>
      <p class="muted">
        <%=user.getEmail()%>
      </p>
      <nav class="profile-menu">
        <a class="active" href="#profile-details">Profile details</a>
        <a href="#change-password">Change password</a>
        <a href="<%=ctx%>/favorites">My favorites →</a>
      </nav>
    </aside>
    <div class="profile-sections">
      <section class="panel" id="profile-details">
        <h2>Profile details</h2>
        <p class="muted">Update the name and email associated with your account.</p>
        <form action="<%=ctx%>/profile" method="post">
          <input type="hidden" name="action" value="profile">
          <div class="field">
            <label for="username">Username</label>
            <input id="username" name="username" value="<%=user.getUsername()%>" minlength="3" maxlength="30" pattern="[A-Za-z0-9_.-]+" required>
          </div>
          <div class="field">
            <label for="email">Email address</label>
            <input id="email" name="email" type="email" value="<%=user.getEmail()%>" required>
          </div>
          <button type="submit">Save profile</button>
        </form>
      </section>
      
      <section class="panel" id="change-password">
        <h2>Change password</h2>
        <p class="muted">For your security, confirm your current password first.</p>
        <form action="<%=ctx%>/profile" method="post">
          <input type="hidden" name="action" value="password">
          <div class="field">
            <label for="currentPassword">Current password</label>
            <input id="currentPassword" name="currentPassword" type="password" autocomplete="current-password" required>
          </div>
          <div class="field">
            <label for="newPassword">New password</label>
            <input id="newPassword" name="newPassword" type="password" minlength="8" autocomplete="new-password" required>
          </div>
          <button type="submit">Change password</button>
        </form>
      </section>
    </div>
  </div>
</main>

<footer class="site-footer">
  <div class="footer-inner">
    <a class="brand" href="<%=ctx%>/home">✿ DishDiscover</a>
    <span>Find a little inspiration. Make something lovely.</span>
  </div>
</footer>
</body>
</html>
