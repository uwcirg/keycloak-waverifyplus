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
		    <div class="${properties.kcInputGroupItemClass}">
			    <button class="${properties.kcFormPasswordVisibilityButtonClass}" type="button"
			            aria-label="${msg('showPassword')}"
			            aria-controls="${name}" data-password-toggle
			            data-icon-show="fa-eye fas" data-icon-hide="fa-eye-slash fas"
			            data-label-show="${msg('showPassword')}" data-label-hide="${msg('hidePin')}">
				    <i class="fa-eye fas" aria-hidden="true"></i>
			    </button>
		    </div>
	    </div>
    </@field.group>
</#macro>
