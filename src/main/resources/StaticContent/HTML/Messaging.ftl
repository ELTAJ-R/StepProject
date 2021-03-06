<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="">

    <title>Chat</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
          integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/buttonStyle.css">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="/css/style.css">
    <meta http-equiv="refresh" content="20"/>
</head>
<a href="/logout"><img src="https://i.ya-webdesign.com/images/log-out-icon-png-4.png"
                       style="width:42px;height:42px;border:0;position:absolute; top:0; right:10px;background-color: white"></a>
<a href="/users/*"><img
            src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/Facebook_like_thumb.png/700px-Facebook_like_thumb.png"
            style="width:42px;height:42px;border:0;position:absolute; top:0; right:60px;"></a>
<body>

<div class="container">
    <div class="row">
        <div class="chat-main col-6 offset-3">
            <div class="col-md-12 chat-header">
                <div class="row header-one text-white p-1">
                    <div class="col-md-6 name pl-2">
                        <i class="fa fa-comment"></i>
                        <h6 class="ml-1 mb-0">${user.name} ${user.surname}</h6>
                    </div>
                    <div class="col-md-6 options text-right pr-0">
                        <i class="fa fa-window-minimize hide-chat-box hover text-center pt-1"></i>
                        <p class="arrow-up mb-0">
                            <i class="fa fa-arrow-up text-center pt-1"></i>
                        </p>
                        <i class="fa fa-times hover text-center pt-1"></i>
                    </div>
                </div>
                <div class="row header-two w-100">
                    <div class="col-md-6 options-left pl-1">
                        <i class="fa fa-video-camera mr-3"></i>
                        <i class="fa fa-user-plus"></i>
                    </div>
                    <div class="col-md-6 options-right text-right pr-2">
                        <i class="fa fa-cog"></i>
                    </div>
                </div>
            </div>
            <div class="chat-content">
                <div class="col-md-12 chats pt-3 pl-2 pr-3 pb-3">
                    <ul class="p-0">
                        <#list messages as message>
                            <#if message.isSent='true'>
                                <li class="send-msg float-right mb-2">
                                <p class="pt-1 pb-1 pl-2 pr-2 m-0 rounded">
                                    ${message.message}
                                </p>
                                <span class="send-msg-time">${message.date}</span>
                                </li></#if>
                            <#if message.isSent='false'>
                                <li class="receive-msg float-left mb-2">
                                <div class="sender-img">
                                    <img src="${user.picture}" class="float-left">
                                </div>
                                <div class="receive-msg-desc float-left ml-2">
                                    <p class="bg-white m-0 pt-1 pb-1 pl-2 pr-2 rounded">
                                        ${message.message}
                                    </p>
                                    <span class="receive-msg-time">${user.name},${message.date}</span>
                                </div>
                                </li></#if>
                        </#list>
                    </ul>
                </div>
                <div class="col-md-12 p-2 msg-box border border-primary">
                    <div class="row">
                        <div class="col-md-2 options-left">
                            <i class="fa fa-smile-o"></i>
                        </div>
                        <form method="post">
                            <div class="col-md-7 pl-0">
                                <input type="text" name="message" class="border-0" placeholder="Send message"/>
                            </div>
                    </div>
                </div>
            </div>
            <div class="container">
                <button class="btn btn4" type="submit" style="position:absolute; bottom:20px; right:5px;">Send</button>
                </div></form>
        </div>
    </div>
</div>



</body>
</html>


