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
<div class="form-box">
    <div class="header">New notice</div>
    <div class="body" id="send-article-form">
        <form method="post" action="/notice">
            <input type="hidden" name="action" value="notice">
            <div class="field">
                <div class="name">
                    <label for="content">Text</label>
                </div>
                <div class="value">
                    <textarea name="content" title="content" >${content!}</textarea>
                </div>
               <@error 'noticeForm.content'/>
            </div>
            <div class="button-field">
                <input type="submit" value="Send">
            </div>
        </form>
    </div>
</div>
</@c.page>