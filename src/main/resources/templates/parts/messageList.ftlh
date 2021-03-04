<#include "security.ftlh">

<div class="row row-cols-1 row-cols-md-3 g-4" xmlns="http://www.w3.org/1999/html" id="messageList">
    <#list messages as message>
        <div class="col" data-id="${message.id}">
            <div class="card my-3">
                <div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class=" card-img-top"/>
                    </#if>
                </div>
                <div class="m-2">
                    <span>${message.text}</span>
                    <br/>
                    <i>#${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    <a href="/user-messages/${message.author.id}">${message.authorName}</a>
                    <#if message.author.id == currentUserId>
                        <a class="btn btn-primary" href="/user-messages/${message.author.id}?message=${message.id}">
                            Edit
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
        <div class="input-group mb-3">
            No message
        </div>
    </#list>
</div>