<#-- @ftlvariable name="users" type="ru.itmo.webmail.model.domain.User[]" -->
<#import "common.ftl" as c>

<@c.page>
<div class="datatable users">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Login</th>
            <th>Is Admin</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="3" class="no-data">No data</td>
        </tr>
        </tbody>
    </table>
</div>

<template class="users-tr">
    <tr>
        <td class="id">{{#}}</td>
        <td class="login">{{login}}</td>
        <td class="command">
            <button type="button" class="sh-button"></button>
        </td>
    </tr>
</template>
<script>

    function getName(isAdmin) {
        if (isAdmin) {
            return "disable";
        }
        return "enable";
    }

    function getUserId($button) {
        var s =$button.attr("id");
        return s.substr(s.indexOf("_")+1)
    }

    $(function () {
        var $noData = $(".users .no-data");
        $noData.text("Loading...");
        $.post("", {action: "find"}, function (users) {
            var $tbody = $(".users tbody");
            if (users.length > 0) {
                $tbody.empty();
                users.forEach(function (user) {
                    var $tr = cloneTemplate($("template.users-tr"));
                    $tr.find(".id").text(user.id);
                    $tr.find(".login").text(user.login);
                    $tr.find(".sh-button").text(getName(user.admin));
                    $tr.find(".sh-button")
                            .attr("id", "user_" + user.id)
                            .click(function (e) {
                                var $button = $(this);
                                e.preventDefault();
                                $.post({
                                    url: "/admin",
                                    data: {
                                        id: getUserId($button),
                                        action: "change"
                                    },
                                    success: function (result) {
                                        if (result["success"]) {
                                            $button.text(getName(result["admin"]));
                                        }
                                        if (!result["success"]) {
                                            setMessage("You are not an admin");
                                            document.location.href = "/";
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