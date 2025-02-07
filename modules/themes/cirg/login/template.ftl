<#macro registrationLayout bodyClass="" displayInfo=false displayMessage=true displayRequiredFields=false>
	<!DOCTYPE html>
	<html class="${properties.kcHtmlClass!}" <#if realm.internationalizationEnabled> lang="${locale.currentLanguageTag}"</#if>>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="author" content="Victor de Lima Soares">
		<meta name="robots" content="noindex, nofollow">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="keywords" content="health, summaries, control, sharable">

            <#if properties.meta?has_content>
                <#list properties.meta?split(' ') as meta>
			<meta name="${meta?split('==')[0]}" content="${meta?split('==')[1]}"/>
                </#list>
            </#if>

		<title>${msg("loginTitle",(realm.displayName!''))}</title>
		<link rel="icon" href="${url.resourcesPath}/img/favicon.ico"/>

            <#if properties.stylesCommon?has_content>
                <#list properties.stylesCommon?split(' ') as style>
			<link href="${url.resourcesPath}/${style}" rel="stylesheet"/>
                </#list>
            </#if>

            <#if properties.styles?has_content>
                <#list properties.styles?split(' ') as style>
			<link href="${url.resourcesPath}/${style}" rel="stylesheet"/>
                </#list>
            </#if>

            <#if properties.scripts?has_content>
                <#list properties.scripts?split(' ') as script>
			<script src="${url.resourcesPath}/${script}" type="text/javascript"></script>
                </#list>
            </#if>

            <#if scripts??>
                <#list scripts as script>
			<script src="${script}" type="text/javascript"></script>
                </#list>
            </#if>
	</head>

	<body class="${properties.kcBodyClass!} login-page cherry-page">

	<header id="header" class="container-fluid text-center py-2 m-0">
		<img src="${url.resourcesPath}/${properties.headerLogo}"
		     alt="${properties.headerLogoAlt}" class="img-fluid d-block header-logo">
            <#if properties.headerTitle??>
		    <h1 class="header-title">${properties.headerTitle}</h1>
            </#if>
	</header>

	<main class="kc-login-form container-fluid p-0">
		<div class="${properties.kcFormCardClass!}">
			<header class="${properties.kcFormHeaderClass!} kc-header-form">
				<div class="text-center">
					<img src="${url.resourcesPath}/${properties.formHeaderLogo}"
					     alt="UW Signature Logo" class="img-fluid mx-auto d-block">
				</div>
                            <#if realm.internationalizationEnabled && locale.supported?size gt 1>
				    <div class="${properties.kcLocaleMainClass!}" id="kc-locale">
					    <div id="kc-locale-wrapper" class="${properties.kcLocaleWrapperClass!}">
						    <div id="kc-locale-dropdown"
						         class="${properties.kcLocaleDropDownClass!}">
							    <a href="#"
							       id="kc-current-locale-link">${locale.current}</a>
							    <ul class="${properties.kcLocaleListClass!}">
                                                                <#list locale.supported as l>
									<li class="${properties.kcLocaleListItemClass!}">
										<a class="${properties.kcLocaleItemClass!}"
										   href="${l.url}">${l.label}</a>
									</li>
                                                                </#list>
							    </ul>
						    </div>
					    </div>
				    </div>
                            </#if>
                            <#if !(auth?has_content && auth.showUsername() && !auth.showResetCredentials())>
                                <#if displayRequiredFields>
					<div class="${properties.kcContentWrapperClass!}">
						<div class="${properties.kcLabelWrapperClass!} subtitle">
									<span class="subtitle"><span
											class="required">*</span> ${msg("requiredFields")}</span>
						</div>
						<div class="col-md-10">
							<h1 id="kc-page-title"><#nested "header"></h1>
						</div>
					</div>
                                <#else>
					<h1 id="kc-page-title"><#nested "header"></h1>
                                </#if>
                            <#else>
                                <#if displayRequiredFields>
					<div class="${properties.kcContentWrapperClass!}">
						<div class="${properties.kcLabelWrapperClass!} subtitle">
									<span class="subtitle"><span
											class="required">*</span> ${msg("requiredFields")}</span>
						</div>
						<div class="col-md-10">
                                                    <#nested "show-username">
							<div id="kc-username"
							     class="${properties.kcFormGroupClass!}">
								<div class="${properties.kcLabelWrapperClass!}">
									<label id="kc-attempted-username">${auth.attemptedUsername}</label>
								</div>
								<a id="reset-login"
								   href="${url.loginRestartFlowUrl}"
								   aria-label="${msg("restartLoginTooltip")}">
									<div class="kc-login-tooltip">
										<i class="${properties.kcResetFlowIcon!}"></i>
										<span class="kc-tooltip-text">${msg("restartLoginTooltip")}</span>
									</div>
								</a>
							</div>
						</div>
					</div>
                                <#else>
                                    <#nested "show-username">
					<div id="kc-username" class="${properties.kcFormGroupClass!}">
						<div class="${properties.kcLabelWrapperClass!}">
							<label id="kc-attempted-username">${auth.attemptedUsername}</label>
						</div>
						<a id="reset-login" href="${url.loginRestartFlowUrl}"
						   aria-label="${msg("restartLoginTooltip")}">
							<div class="kc-login-tooltip">
								<i class="${properties.kcResetFlowIcon!}"></i>
								<span class="kc-tooltip-text">${msg("restartLoginTooltip")}</span>
							</div>
						</a>
					</div>
                                </#if>
                            </#if>
			</header>
			<div id="kc-content" class="container">
				<div id="kc-content-wrapper" class="row justify-content-center">

                                    <#-- App-initiated actions should not see warning messages about the need to complete the action -->
                                    <#-- during login.                                                                               -->
                                    <#if displayMessage && message?has_content && (message.type != 'warning' || !isAppInitiatedAction??)>
					    <div class="alert-${message.type} ${properties.kcAlertClass!} alert-${message.type}">
						    <div class="pf-c-alert__icon">
                                                        <#if message.type = 'success'><span
								class="${properties.kcFeedbackSuccessIcon!}"></span></#if>
                                                        <#if message.type = 'warning'><span
								class="${properties.kcFeedbackWarningIcon!}"></span></#if>
                                                        <#if message.type = 'error'><span
								class="${properties.kcFeedbackErrorIcon!}"></span></#if>
                                                        <#if message.type = 'info'><span
								class="${properties.kcFeedbackInfoIcon!}"></span></#if>
						    </div>
						    <span class="${properties.kcAlertTitleClass!}">${kcSanitize(message.summary)?no_esc}</span>
					    </div>
                                    </#if>

                                    <#nested "form">

                                    <#-- Needs approval
                                    <#if auth?has_content && auth.showTryAnotherWayLink()>
					    <form id="kc-select-try-another-way-form"
					          action="${url.loginAction}" method="post">
                                                <@buttons.tryAnotherWay />
					    </form>
                                    </#if>
                                    -->

                                    <#nested "socialProviders">

                                    <#if displayInfo>
					    <div id="kc-info" class="${properties.kcSignUpClass!}">
						    <div id="kc-info-wrapper"
						         class="${properties.kcInfoAreaWrapperClass!}">
                                                        <#nested "info">
						    </div>
					    </div>
                                    </#if>
				</div>
			</div>

		</div>
	</main>
	</body>
	</html>
</#macro>
