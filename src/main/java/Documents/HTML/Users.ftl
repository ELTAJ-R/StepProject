<!DOCTYPE html>
<html lang="en" xmlns:height="http://www.w3.org/1999/xhtml" xmlns:width="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
</head>
<body>
<p1>${user.name} ${user.surname} </p1>
<input type="image"
       src="${user.picture}" height="50" width="50"
       align="center" name="Profile">
<form method="post">
    <a href="/users"><input type="submit" content="Yes" style="color: darkgrey;border-radius: 10px" value="Yes"
                            name="first"></input></a>
    <a href="/users"><input type="submit" style="color: darkgrey;border-radius: 10px" value="No" name="second"></input></a>

</form>



</body>
<a href="/logout">Logout</a>
</html>