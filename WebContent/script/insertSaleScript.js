let currentItems = 1;
let totalRow = 1;
const array = new Array();
let totalProducts = 1;
let totalSales = 0;

function addSale(){
	document.getElementsByClassName('fa-icon')[0].remove();
	currentItems++;
	document.getElementById('insert-form').innerHTML += `	
	<hr id = "separator-`+ currentItems +`"/>
	<div class = "row" id = "row-` + currentItems + `">
		<div class = "col" id = "remove-container-` + currentItems + `">
			<i class="fa fa-trash" aria-hidden="false" onclick = "deleteElement(` + currentItems + `)"></i>
		</div>
		<div class = "col" id = "product-name-container-` + currentItems + `">
			<input id = "product-name-` + currentItems + `" type="text" class="form-control rounded-pill form-control-lg product-name" placeholder="Name" onchange = "setValueName(` + currentItems + `)">
		</div>
		<div class = "col" id = "product-quantity-container-` + currentItems + `">
			<input id = "product-quantity-` + currentItems + `" type="number" min = "1" class="form-control rounded-pill form-control-lg product-quantity" placeholder="Quantity" onchange = "setValueQuantity(` + currentItems + `)" value = "1">
		</div>
		<div class = "col" id = "product-cost-container-` + currentItems + `">
			<input id = "product-cost-` + currentItems + `" type="number" min = "1" class="form-control rounded-pill form-control-lg product-cost" placeholder="Cost" onchange = "updateTotal()" value = "0">
		</div>
		<div class = "col" id = "add-` + currentItems + `">
			<i class="rounded-pill form-control-lg product-cost fa-icon fas fa-plus" onclick = "addSale()"></i>
		</div>
	</div>`;
	totalRow++;
	array.push(currentItems);
	updateTotalProducts();
}

function deleteElement(index){
	document.getElementById('row-' + index).remove();
	document.getElementById('separator-'+index).remove();
	updateTotalProducts();
	updateTotal();
	if(index == currentItems && (array.length > 1)){
		/* The element to delete is the last in the list */
		document.getElementById('add-'+(index - 1)).innerHTML += `<i class="rounded-pill form-control-lg product-cost fa-icon fas fa-plus" onclick = "addSale()"></i>`;
	}else{
		if(array.length == 1){
			/* If the items in the list are onyl two */
			document.getElementById('add-1').innerHTML += `<i class="rounded-pill form-control-lg product-cost fa-icon fas fa-plus" onclick = "addSale()"></i>`;
		}
	}
	array.splice(array.indexOf(index), 1);
}

function setValueQuantity(index){
	updateTotalProducts();
	document.getElementById('product-quantity-' + index).setAttribute('value', parseInt(document.getElementById('product-quantity-' + index).value, '10'));
}

function setValueName(index){
	document.getElementById('product-name-' + index).setAttribute('value', document.getElementById('product-name-' + index).value);
}
function updateTotal(){
	let total = 0;
	if(document.getElementById('product-cost-1') != null && document.getElementById('product-cost-1') != 'undefined'){
		document.getElementById('product-cost-1').setAttribute('value', parseFloat(document.getElementById('product-cost-1').value).toFixed(2));
		total += parseFloat(document.getElementById('product-cost-1').value);	
	}
	for(let i = 0; i < array.length; i++){
		if(document.getElementById('product-cost-' + array[i]) != null && document.getElementById('product-cost-' + array[i]) != 'undefined'){
			total += parseFloat(document.getElementById('product-cost-' + array[i]).value);
			document.getElementById('product-cost-' + array[i]).setAttribute('value', parseFloat(document.getElementById('product-cost-' + array[i]).value).toFixed(2));
		}
	}
	document.getElementById('total').innerHTML = 'Total: ' + total;
	totalSales = total;
}

function updateTotalProducts(){
	let total = 0;
	if(document.getElementById('product-quantity-1') != null && document.getElementById('product-quantity-1') != 'undefined'){
		total += parseInt(document.getElementById('product-quantity-1').value, '10');
	}
	for(let i = 0; i < array.length; i++){
		if(document.getElementById('product-quantity-' + array[i]) != null && document.getElementById('product-quantity-' + array[i]) != 'undefined'){
			total += parseInt(document.getElementById('product-quantity-' + array[i]).value, '10');	
		}
	}
	document.getElementById('total-items').innerHTML = 'Total items: ' + total;
	totalProducts = total;
}

function submit(){
	let data = parseJSONData();
	if(checkValues()){
		let data = 'data=' + JSON.stringify(parseJSONData());
		if(data != null){
			const xhttp = new XMLHttpRequest();	
			/* Building of the callback function on the readychagestate change */
			xhttp.onreadystatechange = function(){
				if(this.readyState == 4 && this.status == 500){
					alert('Error during the submission!');
				}
				if(this.readyState == 4 && this.status == 200){
					/* If the server answer with the 200 code then show a success alert */
					alert('Sale insert success!');
				}
			}	
			/* Sending the request */
			xhttp.open('POST', '/PharmacyManagementSystem/insertSale', true); //True means that the request is asynch
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhttp.send(data);
		}
	}
}

function parseJSONData(){
	let jsonObject = {
		"sales": [
			
		],
		"totalProducts": totalProducts,
		"total": totalSales
	};
	for(let i = 1; i <= currentItems; i++){
		if(document.getElementById('product-name-'+i) != null && document.getElementById('product-name-'+i) != 'undefined'){
			let objName = document.getElementById('product-name-'+i).value;
			let objCost = document.getElementById('product-cost-'+i).value;
			let objQuantity = document.getElementById('product-quantity-'+i).value;
			jsonObject.sales.push({"name": objName,"cost": objCost,"quantity": objQuantity});	
		}
	}
	return jsonObject;
}

function checkValues(){
	let check = true;
	for(let i = 0; i < array.length; i++){
		/* Add the error message to the password field */
		if(document.getElementById('error-name-'+array[i])){
			document.getElementById('error-name-'+array[i]).remove();
			document.getElementById('product-name-'+array[i]).classList.remove('border');
			document.getElementById('product-name-'+array[i]).classList.remove('border-danger');
		}
		if(document.getElementById('product-name-'+array[i]).value == ''){
			document.getElementById('product-name-'+array[i]).classList.add('border');
			document.getElementById('product-name-'+array[i]).classList.add('border-danger');
			let message = document.createElement('p');
			let textMessage = document.createTextNode('Name field cannot be empty!');
			message.setAttribute('id', 'error-name-'+array[i]);
			message.appendChild(textMessage);
			document.getElementById('product-name-container-'+array[i]).appendChild(message);
			check = false;
		}
	}
	return check;
}
