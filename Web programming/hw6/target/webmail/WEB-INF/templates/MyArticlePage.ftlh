<#import "common.ftl" as c>

<@c.page>
<div class="datatable articles">
    <div class="caption">My Articles</div>
    <table>
        <thead>
        <tr>
            <th>Title</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="3" class="no-data">No data</td>
        </tr>
        </tbody>
    </table>
</div>

<template class="articles-tr">
    <tr>
        <td class="title"></td>
        <td class="command">
            <button type="button" class="sh-button"></button>
        </td>
    </tr>
</template>
<script>
    function getName(hidden) {
        if (hidden) {
            return "Show";
        }
        return "Hide";
    }

    function getArticleId($button) {
        var id = $button.attr("id");
        return id.substr(id.indexOf("_") + 1);
    }

    $(function () {
        var $noData = $(".articles .no-data");
        $noData.text("Loading...");
        $.post("", {action: "toPrivateView"}, function (articles) {
            var $tbody = $(".articles tbody");
            if (articles.length > 0) {
                $tbody.empty();
                articles.forEach(function (article) {
                    var $tr = cloneTemplate($("template.articles-tr"));
                    var buttonText = getName(article.hidden);
                    $tr.find(".title").text(article.title);
                    $tr.find(".sh-button")
                            .attr("id", "article_" + article.id)
                            .text(buttonText)
                            .click(function () {
                                var $button = $(this);
                                $.post({
                                    url: "/myArticle",
                                    data: {
                                        id: getArticleId($button),
                                        action: "change"
                                    },
                                    success: function (result) {
                                        if (result["success"]) {
                                            $button.text(getName(result["hidden"]));
                                        }
                                    }
                                });
                            });
                    $tbody.append($tr);
                });
            } else {
                $noData.text("No data");
            }
        })
    })
</script>
</@c.page>