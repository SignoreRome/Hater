<div class="input-group mb-3">
    <a class="btn btn-primary" data-bs-toggle="collapse" href="#multiCollapseExample1" role="button"
       aria-expanded="false" aria-controls="multiCollapseExample1">
        Message editor
    </a>
</div>

<div class="collapse multi-collapse <#if message??>show</#if>" id="multiCollapseExample1">
    <div class="mb-3">
        <form method="post" enctype="multipart/form-data">
            <div class="input-group mb-3">
                <input type="text" class="form-control ${(textError??)?string('is-invalid','')}"
                       value="<#if message??>${message.text}</#if>" name="text" placeholder="Write message"/>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="input-group mb-3">
                <input type="text" class="form-control"
                       value="<#if message??>${message.tag}</#if>" name="tag" placeholder="Tag">
                <#if textError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="input-group mb-3">
                <div class="input-group mb-3">
                    <input type="file" class="form-control" name="file" id="customFile">
                    <label class="input-group-text" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <div class="input-group">
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </form>
    </div>
</div>