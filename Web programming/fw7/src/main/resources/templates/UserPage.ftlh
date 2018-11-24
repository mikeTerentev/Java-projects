<#import "./common.ftlh" as c>

<@c.page>
<div class="user-page">
    <div class="userId">ID : ${user.id}</div>
    <div class="login">LOGIN : ${user.login}</div>
    <div class="creationTime">WITH US SINCE ${user.creationTime}</div>
</div>
</@c.page>