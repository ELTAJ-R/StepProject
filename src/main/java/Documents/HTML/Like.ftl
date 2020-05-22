<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="">

    <title>All Likes</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
          integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="/css/style.css">
</head>
<a href="/logout"><img src="https://i.ya-webdesign.com/images/log-out-icon-png-4.png"
                       style="width:42px;height:42px;border:0;position:absolute; top:0; right:10px;background-color: white"></a>
<a href="/users/*"><img
            src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/Facebook_like_thumb.png/700px-Facebook_like_thumb.png"
            style="width:42px;height:42px;border:0;position:absolute; top:0; right:60px;"></a>
<body>
<div class="container">
    <div class="row">
        <div class="col-8 offset-2">
            <div class="panel panel-default user_panel">
                <div class="panel-heading">
                    <h3 class="panel-title">Liked Users</h3>
                </div>
                <div class="panel-body">
                    <div class="table-container">
                        <table class="table-users table" border="0">
                            <#list users as user>
                            <tbody>
                            <tr>
                                <td width="10">
                                    <div class="avatar-img">
                                        <img class="img-circle" src="${user.picture}">  
                                    </div>
                                </td>
                                <td class="align-middle">
                                    <a href="/messages/${user.name}">${user.name} ${user.surname}</a>
                                </td>
                                <td class="align-middle">
                                </td>
                                <td class="align-middle">
                                    Last Login: ${user.lastLogin}<br><small class="text-muted"></small>
                                </td>
                            </tr>
                            </tbody>
                            </#list></a>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>