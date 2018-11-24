<#import "common.ftl" as c>

<@c.page>
<div class="form-box">
    <div class="header">Add News</div>
    <div class="body" id="ff">
        <form method="post" action="/news">
            <input type="hidden" name="action" value="news">
            <div class="field">
                <div class="name">
                    <label id="text" for="text">Text</label>
                </div>
                <div class="value">
                    <textarea name="text" title="Text"></textarea>
                </div>
            </div>
            <div class="button-field">
                <input type="submit" value="Add Post">
            </div>
            <#if error??>
            <div class="error">
                ${error}
            </div>
            </#if>
        </form>
    </div>
</div>
</@c.page>