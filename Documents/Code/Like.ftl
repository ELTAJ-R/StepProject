<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Liked Profiles</title>
</head>
<body>

    <#list allLikes as like>
    <a href="/messages/${like}"> ${like}<br></a>
    </#list>
</form>

</body>
</html>