<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="">

    <title>Like Page</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
          integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="/css/style.css">
</head>

<a href="/logout"><img src="https://i.ya-webdesign.com/images/log-out-icon-png-4.png"
                       style="width:42px;height:42px;border:0;position:absolute; top:0; right:10px;"></a>
<a href="/like/*"><img src="https://icons.iconarchive.com/icons/custom-icon-design/silky-line-user/512/users-icon.png"
                       style="width:42px;height:42px;border:0;position:absolute; top:-5px; right:60px;"></a>
<body style="background-color: #f5f5f5;">

<div class="col-4 offset-4">
    <div class="card">
        <div class="card-body">
            <div class="row">
                <div class="col-12 col-lg-12 col-md-12 text-center">


                    <img src="${user.picture}" alt="" class="mx-auto rounded-circle img-fluid">

                    <h3 class="mb-0 text-truncated">${user.name} ${user.surname}</h3>
                    <br>
                </div>

                <div class="col-12 col-lg-6">
                    <form method="post">
                        <button type="submit" class="btn btn-outline-danger btn-block" name="second"><span
                                    class="fa fa-times"></span>Dislike
                        </button>

                    </form>
                </div>
                <div class="col-12 col-lg-6">
                    <form method="post">
                        <button class="btn btn-outline-success btn-block" name="first"><span
                                    class="fa fa-heart"></span>Like
                        </button>
                    </form>
                </div>
                <!--/col-->
            </div>
            <!--/row-->
        </div>
        <!--/card-block-->
    </div>
</div>


</body>
</html>