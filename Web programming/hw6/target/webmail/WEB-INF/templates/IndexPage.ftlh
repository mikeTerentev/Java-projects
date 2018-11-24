<#-- @ftlvariable name="message" type="java.lang.String" -->

<#import "common.ftl" as c>

<@c.page>
    <#if message??>
        <p>${message}</p>
    </#if>
    <div class="articles">
        No data
    </div>

<template class="article-tmpl">
    <article-form>
        <div class="title"></div>
        <div class="information"></div>
        <div class="body"></div>
        <div class="footer">
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                <div class="time"></div>
            </div>
        </div>
    </article-form>
    <br>
</template>
<script>
    $(function () {
        var $articles = $(".articles");
        $articles.text("Loading...");
        $.post("/article", {action: "toPublicView"}, function (articles) {
            if (articles.length > 0) {
                $articles.empty();
                articles.forEach(function (article) {
                    var $article = cloneTemplate($("template.article-tmpl"));
                    $article.find(".title").text(article.title);
                    $article.find(".information").text("By " + article.login);
                    $article.find(".body").text(article.text);
                    $article.find(".footer .time").text(article.creationTime);
                    $articles.append($article);
                });
            }
        })
    })
</script>
</@c.page>
