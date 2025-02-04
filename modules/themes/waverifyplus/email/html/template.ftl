<#macro emailLayout>
	<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<style>
			body {
				font-family: Arial, sans-serif;
				margin: 0;
				padding: 0;
				display: flex;
				justify-content: center;
			}

			.container, .container > div {
				display: flex;
			}

			.container {
				width: 100%;
				background: #ffffff;
				border-radius: 8px;
				overflow: hidden;
				flex-direction: column;
				box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
			}

			.header {
				padding: 20px;
				flex-direction: column;
			}

			.header img {
				height: 50px;
				margin-right: 15px;
			}

			.header-title {
				font-size: 20px;
				font-weight: bold;
				color: rgb(200, 76, 14);
				margin: 15px 0;
			}

			.body {
				padding: 20px;
				text-align: left;
			}

			.separator {
				height: 1px;
				background: #ddd;
				margin: 20px 0;
			}

			.footer {
				text-align: center;
				padding: 20px;
			}

			.footer img {
				width: 100px;
			}
		</style>
	</head>
	<body>
	<div class="container">

		<div class="header">
			<img src="${url.resourcesUrl}/${properties.headerLogo}"
			     alt="${properties.headerLogoAlt}">
			<div class="header-title">${properties.title}</div>
		</div>

		<div class="body">
                    <#nested>
		</div>

		<div class="separator"></div>

		<div class="footer">
			<img src="${url.resourcesUrl}/${properties.footerLogo}"
			     alt="${properties.footerLogoAlt}">
		</div>

	</div>
	</body>
	</html>
</#macro>
