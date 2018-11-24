<#import "common.ftl" as c>

<@c.page>
<div class="form-box">
    <div class="header">Send Message</div>
    <div class="body" id="send-article-form">
        <form method="post" action="/article">
            <input type="hidden" name="action" value="article">
            <div class="field">
                <div class="name">
                    <label for=title>Title</label>
                </div>
                <div class="value">
                    <input id="title" name="title" value="${title!}"/>
                </div>
            </div>
            <div class="field">
                <div class="name">
                    <label for="line">Text</label>
                </div>
                <div class="value">
                    <textarea name="line" title="line">${line!}</textarea>
                </div>
            </div>
            <div class="button-field">
                <input type="submit" value="Send">
            </div>
            <#if error??>
            <div class="error">
                ${error}
            </div>
            </#if>
        </form>
    </div>
</div>

<script>
    $(function() {
        $(".form-box").submit(function () {
            var title = $(this).find("input[name='title']").val();
            var line = $(this).find("textarea[name='line']").val();
            var $error = $(this).find(".error").text("");
            $.post("", {action: "article", title: title, line: line}, function (response) {
                if (response["success"]) {
                    setMessage("Article added. WOWOWWOWOW!");
                    document.location.href = "/";
                } else {
                    $error.text(response["error"]);
                }
            });
            return false;
        });
    })
</script>
</@c.page>