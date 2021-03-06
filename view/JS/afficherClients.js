import {filterDropdown,postData,onError, getData}from "./util.js";
import {token, allHide} from "./index.js";
import{afficherDevis} from "./afficherDevis.js";


$(document).ready(function () {
	$("#btn-search-Clients").click(e=>{
		var data={
			"name":$("#clientsNameSearch").val(),
			"postalCode":$("#searchClientCodePostal").val(),
			"city":$("#inputClientsCitySearch").val()
		};
		if(data.name==="")
			data.name=document.getElementById("inputClientsNameSearch").value;
		

		postData("/listeClients",data,token,afficherClients,onError);
		$("#clientsNameSearch").val("");
		$("#inputClientsCitySearch").val("");
		$("#inputClientsNameSearch").val("");
        $("#searchClientsCityDropdown").text("ville");
		$("#searchClientsNameDropdown").text("nom client");
	});
	
    $("#btn-search-category").click(e=>{
    	if($("#search-option-category").val()=="client"){
    	    allHide();
            getData("/rechercheDevis",token,afficherClientsDropdown,onError);
            getData("/listeClients",token,afficherClients,onError);
        }
    });

});

function afficherVilleDropdown(response){
	var input=document.getElementById("inputClientsCitySearch");
	$("#clientsCitySearch").html("");
	input.addEventListener("keyup",function(){

		$("#searchClientsCityDropdown").text(this.value);
		filterDropdown(this);
	});

	$("#clientsCitySearch").append(input);
	response.villes.forEach(e=>{
		var li=document.createElement("li");
		var a=document.createElement("a");
		a.innerHTML=e;
		a.addEventListener("click",function(){

			$("#searchClientsCityDropdown").text(a.innerHTML);
			$("#inputClientsCitySearch").val(a.innerHTML);
		});
		li.appendChild(a);
		$("#clientsCitySearch").append(li);
	});
	

}

function afficherClientsDropdown(response){
	$("#clientsNameSearch").html("");
	var input=document.createElement("input");
	input.id="inputClientsNameSearch";
	input.type="text";
	input.placeholder="Search..";
	input.className="form-control";
	input.addEventListener("keyup",function(){
		$("#searchClientsNameDropdown").text($(this).val());
		filterDropdown(this);
	});

	$("#clientsNameSearch").append(input);

	response.noms.forEach(e=>{
		var li=document.createElement("li");
		var a=document.createElement("a");
		a.innerHTML=e;
		a.addEventListener("click",function(){
			$("#searchClientsNameDropdown").text(a.innerHTML);
			$("#clientsNameSearch").val(e);
		});
		li.appendChild(a);
		$("#clientsNameSearch").append(li);
	});

}

function afficherClients(response){
	getData("/villes",token,afficherVilleDropdown,onError);
	viewListeClients();
	$("#voir-clients tbody").html("");
	Object.keys(response.clientsData).forEach(data => {

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