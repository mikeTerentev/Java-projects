<#import "/spring.ftl" as spring>
<#import "./common.ftlh" as c>

<#macro error field>
    <@spring.bind field/>
    <#list spring.status.errorMessages as error>
        <div class="error">${error}</div>
        <#break/>
    </#list>
</#macro>

<@c.page>
    <#if notice??>
<div class="form-box">
    <div class="header">Notice</div>
    <div class="body">
        <div class="field">
            <div class="name">
                <label for="text">Created on</label>
            </div>
            <div class="value">
                ${notice.creationTime}
            </div>
        </div>
        <div class="field">
            <div class="name">
                <label for="text">Created by</label>
            </div>
            <div class="value">
                ${notice.user.login}
            </div>
        </div>
        <div class="field">
            <div class="name">
                <label for="text">Text</label>
            </div>
            <div class="value">
                ${notice.text}
            </div>
        </div>
            <div class="field">
                <div class="name">
                    <label for="text">Tags</label>
                </div>
               <#list notice.tags as tag>
                <div class="value">
                    ${tag.name}
                </div>
               </#list>
            </div>
    </div>
</div>

    <#if user??>
<br>
<div class="form-box">
    <div class="header">New Comment</div>
    <div class="body">
        <form method="post" action = "/notice/${notice.id}">
            <div class="field">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <div class="value">
                    <textarea id="text" rows="2" name="text">${comment.text!?html}</textarea>
                </div>
               <#if errors??>
               <div class="errors"> ${errors} </div>
               </#if>
                <@error 'comment.text'/>
            </div>

            <div class="button-field">
                <input type="submit" value="Send">
            </div>
        </form>
    </div>
</div>
    </#if>
    <#if notice.comments?has_content>
<br>
<div class="datatable">
    <div class="caption">Comments</div>
    <table>
        <thead>
        <tr>
            <th>Author</th>
            <th>Text</th>
        </tr>
        </thead>
        <tbody>
        <#list notice.comments as comment>
        <tr>
            <td>${comment.user.login}</td>
            <td>${comment.text}</td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
    </#if>
    <#else>
    Not found
    </#if>
</@c.page>