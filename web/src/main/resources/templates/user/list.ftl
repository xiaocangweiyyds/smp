<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>列表</title>
    <script type="text/javascript">
        function del(id) {
            document.getElementById("del").action = "/user/users/"+id;
            document.getElementById("del").submit();
            return false;
        }
    </script>
</head>
<body>

<#-- delete 请求 -->
<form action="" method="POST" id="del">
    <input type="hidden" name="_method" value="DELETE"/>
</form>

<#-- 文件上传 -->
<form action="/templates/user/uploadfile" method="POST" enctype="multipart/form-data">
    <input type="file" name="files"/>
<#--    <input type="file" name="files"/>-->
    <input type="submit" value="上传"/>
</form>

    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th><p1>ID</p1></th>
            <th>头像</th>
            <th>名称</th>
            <th>性别</th>
            <th>年龄</th>
            <th>邮箱</th>
            <th>日期</th>
            <th>编辑</th>
        </tr>
        <#list lst as l>
            <tr>
                <th>${l.id}</th>
                <th><img src='/user/picturePreview/${l.id}' style="width: 200px"/>
                </th>
                <th><#if l.userName ??>${l.userName}</#if></th>
                <th><#if l.sex ??>${l.sex}</#if></th>
                <th><#if l.age ??>${l.age}</#if></th>
                <th><#if l.email ??>${l.email}</#if></th>
                <th><#if l.birthdate ??>${l.birthdate?string("yyyy-MM-dd")}</#if></th>
                <th>
                    <a href="javascript:del(${l.id})">删除</a>
                    <a href="/user/users/${l.id}">修改</a>
                    <a href="/user/downloadfile/${l.id}">下载图片</a>
                </th>
            </tr>
        </#list>
    </table>

<br/>
<br/>
<a href="/templates/user/users">添加</a>

</body>
</html>
