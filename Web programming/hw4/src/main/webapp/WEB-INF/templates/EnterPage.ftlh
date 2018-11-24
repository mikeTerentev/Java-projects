<#import "common.ftl" as c>

<@c.page>
  <div class="form-box">
      <div class="header">Login into Codeforces</div>
      <div class="body">
          <form method="post" action="/enter">
              <input type="hidden" name="action" value="enter">
              <div class="field">
                  <div class="name">
                      <label for="handleOrEmail">Handle/Email</label>
                  </div>
                  <div class="value">
                      <input id="handleOrEmail" name="handleOrEmail" value="${login!}"/>
                  </div>
              </div>
              <div class="field">
                  <div class="name">
                      <label for="password">Password</label>
                  </div>
                  <div class="value">
                      <input id="password" name="password" type="password"/>
                  </div>
          <#if error??>
            <div class="error">
                ${error}
            </div>
          </#if>
              </div>
              <div class="button-field">
                  <input type="submit" value="Login">
              </div>
          </form>
      </div>
  </div>
</@c.page>
