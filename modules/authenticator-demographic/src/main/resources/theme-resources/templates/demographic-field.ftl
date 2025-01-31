<#macro setPin name label value="" required=false fieldName=name autocomplete="off" autofocus=false>
    <@field.group name=name label=label error=error required=required>
	    <div class="${properties.kcInputGroup}">
		    <div class="${properties.kcInputGroupItemClass} ${properties.kcFill}">
		        <span class="${properties.kcInputClass} <#if error?has_content>${properties.kcError}</#if>">
		          <input id="${name}" name="${name}" value="${value}" type="password" pattern="\d{4}"
		                 title="Must contain 4 digits"
		                 autocomplete="${autocomplete}"
                                 <#if autofocus>autofocus</#if>
		                  aria-invalid="<#if error?has_content>true</#if>"/>
		        </span>
		    </div>
	    </div>
    </@field.group>
</#macro>
