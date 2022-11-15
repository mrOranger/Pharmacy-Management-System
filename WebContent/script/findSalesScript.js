const minDate = document.getElementById('date-1');
const maxDate = document.getElementById('date-2')

function submit(){
	let rows = document.getElementsByClassName('table-row');
	for(let i = 0; i < rows.length; i++){
		rows[i].remove();
	}
	const xhttp = new XMLHttpRequest();	
	/* Building of the callback function on the readychagestate change */
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 500){
			alert('Error during the submission!');
		}
		if(this.readyState == 4 && this.status == 200){
			/* If the server answer with the 200 we can delete the table's row */
			console.log(JSON.parse(this.responseText));
			let results = JSON.parse(this.responseText);
			for(let i = 0; i < results.sales.length; i++){
				let tbody = document.getElementById('body');
				let tr = document.createElement('tr');
				tr.classList.add('table-row');
				let tdCode = document.createElement('td');
				let tdSaleQuantity = document.createElement('td');
				let tdDate = document.createElement('td');
				let tdTotal = document.createElement('td');
				let nestedTable = document.createElement('table');
				nestedTable.classList.add('nested-table');
				let nestedTableBody = document.createElement('tbody');
				for(let j = 0; j < results.sales[i].products.length; j++){
					let nestedTableRow = document.createElement('tr');
					let nestedTableProductName = document.createElement('td');
					let nestedTableProductQuantity = document.createElement('td');
					let nestedTableProductTotal = document.createElement('td');
					nestedTableProductName.classList.add('nested-table-row');
					nestedTableProductQuantity.classList.add('nested-table-row');
					nestedTableProductTotal.classList.add('nested-table-row');
					nestedTableProductTotal.innerHTML = results.sales[i].products[j].total;
					nestedTableProductName.innerHTML = results.sales[i].products[j].name;
					nestedTableProductQuantity.innerHTML = results.sales[i].products[j].quantity;
					nestedTableRow.append(nestedTableProductName);
					nestedTableRow.append(nestedTableProductQuantity);
					nestedTableRow.append(nestedTableProductTotal);
					nestedTableBody.append(nestedTableRow);
				}
				nestedTable.append(nestedTableBody);
				nestedTable.classList.add('nested-table');
				nestedTable.setAttribute('align', 'center');
				tdCode.innerHTML = results.sales[i].code;
				tdSaleQuantity.innerHTML = results.sales[i].quantity;
				tdDate.innerHTML = results.sales[i].day;
				tdTotal.innerHTML = results.sales[i].total;
				tr.append(tdCode);
				tr.append(tdSaleQuantity);
				tr.append(tdDate);
				tr.append(tdTotal);
				tr.append(nestedTable);
				tbody.append(tr);
			}
			
		}
	}	
	/* Sending the request */
	xhttp.open('POST', '/PharmacyManagementSystem/findSales', true); //True means that the request is asynch
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(parseInput());
}

function parseInput(){
	if(minDate.value != '' && maxDate.value != ''){
		return "data_min="+ minDate.value + "&data_max=" + maxDate.value; 
	}
	if(minDate.value != '' && maxDate.value == ''){
		return "data_min="+ minDate.value;
	}
	if(minDate.value == '' && maxDate.value != ''){
		return "data_max=" + maxDate.value;
	}
	return "";
}


function deleteElement(rowIndex){
	const row = document.getElementsByClassName('table-row')[rowIndex];
	const rowText = row.children[0].outerText;
	const xhttp = new XMLHttpRequest();	
	/* Building of the callback function on the readychagestate change */
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 500){
			alert('Error during the submission!');
		}
		if(this.readyState == 4 && this.status == 200){
			/* If the server answer with the 200 we can delete the table's row */
			row.remove();
		}
	}	
	/* Sending the request */
	xhttp.open('POST', '/PharmacyManagementSystem/findSales', true); //True means that the request is asynch
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(parseData(rowText));
}

function parseData(text){
	return 'code=' + text;
}
