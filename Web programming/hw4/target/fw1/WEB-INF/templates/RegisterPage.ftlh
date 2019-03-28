<#import "common.ftl" as c>

<@c.page>
<div class="form-box">
    <div class="header">Register</div>
    <div class="body">
        <form method="post" action="/register">
            <input type="hidden" name="action" value="register">
            <div class="field">
                <div class="name">
                    <label for="login">Login</label>
                </div>
                <div class="value">
                    <input id="login" name="login" value="${login!}"/>
                </div>
            </div>

            <div class="field">
                <div class="name">
                    <label for="email">Email</label>
                </div>
                <div class="value">
                    <input id="congf_pass" type="email" name="email" value="${email!}"/>
                </div>
            </div>

            <div class="field">
                <div class="name">
                    <label for="password">Password</label>
                </div>
                <div class="value">
                    <input id="password" type="password" name="password" value="${password!}"/>
                </div>
            </div>

            <div class="field">
                <div class="name">
                    <label for="conf_pass">Password Confirmation</label>
                </div>
                <div class="value">
                    <input id="congf_pass" type="password" name="congf_pass" value="${congf_pass!}"/>
                </div>
            </div>

            <div class="button-field">
                <input type="submit" value="Register">
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
