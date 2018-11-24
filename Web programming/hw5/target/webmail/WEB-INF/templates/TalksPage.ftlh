<#-- @ftlvariable name="talks" type="java.util.List" -->
<#-- @ftlvariable name="user" type="ru.itmo.webmail.model.domain.User" -->
<#import "common.ftl" as c>

<@c.page>
     <div class="mess-box">
         <div class="blue">Send Message</div>
         <div class="body" id="send-message-form">
             <form method="post" action="/talks">
                 <input type="hidden" name="action" value="addTalk">
                 <div class="field">
                     <div class="name">
                         <label for="targetUserLogin">Recipient</label>
                     </div>
                     <div class="value">
                         <input id="targetUserLogin" name="targetUserLogin" value="${targetUserLogin!}"/>
                     </div>
                 </div>
                 <div class="field">
                     <div class="name">
                         <label id="text" for="text">Text</label>
                     </div>
                     <div class="value">
                         <textarea name="text" title="Text">${text!}</textarea>
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
    <#if talks?size != 0>
<div class="datatable">
    <div class="caption">Mailbox</div>
    <table>
        <tbody>
        <#list talks as talk>
        <tr>
            <th>
                <div class="op">
                    ${talk.sourceUserLogin} → ${talk.targetUserLogin}
                </div>
                <div class="t">
                    ${talk.text}
                </div>
            <#--<div class="x">
                ${talk.time}
            </div>-->
            </th>
        </tr>
        </#list>
    </table>
</div>
    </#if>



</@c.page>