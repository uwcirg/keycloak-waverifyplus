<#macro setPin name label value="" required=false fieldName=name autocomplete="off" autofocus=false>
    <@field.group name=name label=label error=error required=required>

	    <span class="${properties.kcInputWrapperClass!} <#if error?has_content>${properties.kcError}</#if>">
		<input id="${name}" name="${name}" value="${value}" type="password"
		       title="Must contain 4 digits"
		       class="${properties.kcInputClass!}"
		       autocomplete="${autocomplete}"
                       <#if autofocus>autofocus</#if>
			  aria-invalid="<#if error?has_content>true</#if>"/>
	</span>

	    <script>
                document.getElementById("pin").addEventListener("input", function () {
                    this.value = this.value.replace(/\D/g, '').slice(0, 4);
                });
	    </script>
    </@field.group>
</#macro>
