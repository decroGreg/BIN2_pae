import {filterDropdown}from "./util.js";


$(document).ready(function () {
	$("#btn-search-Clients").click(e=>{
		var data={
			"name":$("#searchClientsNameDropdown").text(),
			"postalCode":$("#searchClientCodePostal").val(),
			"city":$("#searchClientsVille").val()
		};
		
		console.log(data);
		//postData("",token,data,afficherUtilisateurs,onError);
	})
});

function afficherClientsDropdown(response){

	//afficherNomDropdown(response,document.getElementById("clientsNameSearch"),"inputClientsNameSearch",document.getElementById("searchClientsNameDropdown"),"clientsData");
	$("#clientsNameSearch").html("");
	var input=document.createElement("input");
	input.id="inputClientsNameSearch";
	input.type="text";
	input.placeholder="Search..";
	input.className="form-control";
	input.addEventListener("keyup",function(){
		filterDropdown(this);
	});

	$("#clientsNameSearch").append(input);

	response.clientsData.forEach(e=>{
		var li=document.createElement("li");
		var a=document.createElement("a");
		a.innerHTML=e.nom+" "+e.prenom;
		a.addEventListener("click",function(){
			$("#searchClientsNameDropdown").text(a.innerHTML);
		});
		li.appendChild(a);
		$("#clientsNameSearch").append(li);
	});

}

function afficherClients(response){
	viewListeClients();
	$("#voir-clients tbody").html("");
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

function viewListeClients(){
	$("#login").hide();
    $("#btn-deconnexion").hide();
    $("#wrong_passwd").hide();
    $("#test1").hide();
    $("#carousel").hide();
    $("#Register-confirmation").hide();
    $("#introductionQuoteForm").hide();
    $("#voir-details-devis").hide();
    $("#voir-details-devis-DC").hide();
    $("#voir-details-devis-DDI").hide();
    $("#voir-devis").hide();
    $("#voir-devis-client").hide();
    $("#voir-utilisateurs").hide();
    $("#voir-clients").show();
	$("#choisirPhotoPreferee").hide();
	$("#ajouterPhoto").hide();
}

export{afficherClients, viewListeClients,afficherClientsDropdown};