function afficherClients(response){
	Object.keys(response.clientsData).forEach(data => {
		console.log(response.clientsData[data].prenom);
	    var html = "<tr>";
	    html+="<td>"
	    	+ response.clientsData[data].prenom + "</td>\n<td>" 
	    	+ response.clientsData[data].nom + "</td>\n<td>" 
	    	+ response.clientsData[data].codePostal + "</td>\n<td>" 
	    	+ response.clientsData[data].ville + "</td>\n<td>"
	    	+ response.clientsData[data].email + "</td>\n<td>"
	    	+ response.clientsData[data].telephone+ "</td></tr>";	    
	    $("#voir-clients tbody").append(html);
	});
}

export{afficherClients};