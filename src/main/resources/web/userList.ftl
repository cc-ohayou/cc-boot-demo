<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>cc test</title>
</head>
<body>
 Hello world!
 <#list userList as user>

 user: ${user.userName}! <br>
 Q:Why I like? <br>
 A:${user.description}!<br>

 </#list>

</body>
</html>