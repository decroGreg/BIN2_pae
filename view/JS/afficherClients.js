import {filterDropdown,postData,onError}from "./util.js";
import {token} from "./index.js";
import{afficherDevis} from "./afficherDevis.js";


$(document).ready(function () {
	$("#btn-search-Clients").click(e=>{
		var data={
			"name":$("#searchClientsNameDropdown").text(),
			"postalCode":$("#searchClientCodePostal").val(),
			"city":$("#searchClientsVille").val()
		};
		
		console.log(data);
		postData("/listeClients",data,token,afficherClients,onError);
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
		a.innerHTML=e.nom;//+" "+e.prenom;
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
	    html+="<td><a href='#' class='reference-client' id='"+ response.clientsData[data].idClient + "'>"
	    	+ response.clientsData[data].prenom + " " + response.clientsData[data].nom +"</a></td>\n<td>" 
	    	+ response.clientsData[data].codePostal + "</td>\n<td>" 
	    	+ response.clientsData[data].ville + "</td>\n<td>"
	    	+ response.clientsData[data].email + "</td>\n<td>"
	    	+ response.clientsData[data].telephone+ "</td></tr>";	    
	    $("#voir-clients tbody").append(html);
	});
	$("a.reference-client").click(e=>{
    	let data={};
        data.idClient=$(e.target).attr("id");
    	postData("/listeDevisClient",data,token,afficherDevis,onError);
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
    $("#voir-devis").hide();
    $("#voir-devis-client").hide();
    $("#voir-utilisateurs").hide();
    $("#voir-clients").show();
	$("#choisirPhotoPreferee").hide();
	$("#ajouterPhoto").hide();
}

export{afficherClients, viewListeClients,afficherClientsDropdown};