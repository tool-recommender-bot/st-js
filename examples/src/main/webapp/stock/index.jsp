<%--

     Copyright 2011 Alexandru Craciun, Eyal Kaspi

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

--%>
<html>
<head>
<script src="${pageContext.request.contextPath}/js/stjs.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/generated-js/StockApplication.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/generated-js/ExtendedStockApplication.js" type="text/javascript"></script>

<script language="javascript">
onload=function(){
	new StockApplication("check constructor").init();
	StockApplication.test2();
	new ExtendedStockApplication("check constructor").test3("abc");
}
</script>
</head>
<body>
<h1>ST-JS example: a stock watchlist manager</h1>
<form id="form">
<table>
	<thead>
		<tr>
		<th>Stock</th><th>Last</th><th>Change</th><th>Remove</th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
<input type="text" id="newStock"><button id="addStock" type="submit">Add</button>
<div>Last time: <span id="timestamp"></span></div>
</form>
<span id="test1"></span>
<span id="test2"></span>
<span id="test3"></span>
</body>
</html>