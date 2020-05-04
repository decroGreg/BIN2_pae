import{token, allHide,user} from "./index.js";
import{afficherDetailsDevis,afficherDetailsDevisClient} from "./detailsDevis.js";
import {postData,onError,creatHTMLFromString,filterDropdown, getData} from "./util.js" ;


$(document).ready(function () {

	$("#btn-search-devis-client").click(e=>{
        searchDevis(document.getElementById("amenagements-devis-client"),user.idUser);
    });
    $("#btn-search-devis").click(e=>{
        searchDevis(document.getElementById("amenagements-devis"));
    });
	$("#inputClientNameSearch").on("keyup", function(){
			$("#searchDevisNameDropdown").text($(this).val());
			filterDropdown(this);
	});
	$("#myInputSearchDevisClient").on("keyup", function(){
		console.log($(this).val());
		filterDropdown(this);
	});
	$("#myInputSearch").on("keyup", function(){
		console.log($(this).val());
		filterDropdown(this);
	});
	
    $("#btn-search-category").click(e=>{
        e.preventDefault();
    	if($("#search-option-category").val()=="devis"){
    		allHide();
            getData("/listeUsers",token,onGetClientDevis,onError);
            getData("/introduireServlet",token,onGetAmenagementDevis,onError);
            getData("/listeDevis",token,afficherDevis,onError);
        }
    });
    $(".mesDevis").click(e=>{
        e.preventDefault();
    	allHide();
        mesDevis();
        alert();
        getData("/introduireServlet",token,onGetAmenagementDevisClient,onError);

    });
    
});

function onGetClientDevis(response){
	afficherNomClientDropdown(response,document.getElementById("clientNameSearch"));	
}



function afficherNomClientDropdown(response,dropdown){
	var input=document.getElementById("inputClientNameSearch");
	dropdown.innerHTML="";
	dropdown.append(input);


	response.usersData.forEach(element=>{
		var li=document.createElement("li");
		li.value=element.idUser;
		var a=document.createElement("a");
		
		a.addEventListener("click",function(){
			$("#searchDevisNameDropdown").text(element.nom+" "+element.prenom);
			$("#dropdownClientName").val(element.nom);
			document.getElementById("dropdownClientName").name="name";
		});
		a.innerHTML=element.nom+" "+element.prenom;
		li.appendChild(a);
		dropdown.appendChild(li);
	});
	console.log(document.getElementById('search-devisClient'));
}




function onGetAmenagementDevis(response){
	afficherAmenagementDropDown(response,document.getElementById("amenagements-devis"),"myInputSearch");
}
function onGetAmenagementDevisClient(response){
	afficherAmenagementDropDown(response,document.getElementById("amenagements-devis-client"),"myInputSearchDevisClient");
}

function afficherAmenagementDropDown(response,dropdown,idInput){	
		var c=document.getElementById(idInput);
		dropdown.innerHTML="";
		dropdown.append(c);
		response.typeAmenagements.forEach(element=>{
			var checkbox=creatHTMLFromString(' <li><input type="checkbox" value="'+element.id+'">'+element.description+'</li>');
			dropdown.append(checkbox);
		});
		
}

function searchDevis(dropdown,idUser){
	var amenagements={};
	
	dropdown.childNodes.forEach(element=>{
		if(element.firstChild&&element.firstChild.checked){
			element=element.firstChild;
			amenagements[element.value]=element.value;
			element.checked=false;
		}
	})
	var data={}
	dropdown.parentNode.parentNode.childNodes.forEach(e=>{
		console.log(e);
		if(e.name){
			data[e.name]=e.value;
			e.value="";
		}
		
	});
	if(!data.name)
		data.name=$("#inputClientNameSearch").val();
	if(idUser){
		data["idUser"]=idUser+"";
	}

	console.log(amenagements);
	console.log(data);
	var send={
		"data":data,
		"amenagements":amenagements
	}
	if(idUser)
	postData("/rechercheDevis",send,token,afficherDevisClient,onError);
	else
	postData("/rechercheDevis",send,token,afficherDevis,onError);

	$("#searchDevisNameDropdown").html("nom client");
	$("#inputClientNameSearch").val("");
	$("#dropdownClientName").val(undefined);
}




function afficherDevis(response){
	allHide();
	viewListeDevis();
	$("#voir-devis").show();
	$("#voir-devis tbody").html("");
	Object.keys(response.devisData).forEach(data => {
	    var html = "<tr>";
	    html+="<td><a href=\"#\" class=\"reference-devis\">"
	    	+ response.devisData[data].idDevis + "</a></td>\n<td>" 
	    	+ response.clientsData[data].nom + " " + response.clientsData[data].prenom + "</td>\n<td>" 
	    	+ response.devisData[data].date.substring(0,10) + "</td>\n<td>" 
	    	+ response.devisData[data].montant + "€</td>\n<td>" 
	    	+ response.devisData[data].dureeTravaux + "</td>\n<td>"  
	    	+ response.devisData[data].etat + "</td></tr>";	    
	    $("#voir-devis tbody").append(html);
	});
	$("a.reference-devis").click(e=>{
    	let data={};
        data.idDevis=$(e.target).text();
        console.log("Reference devis : " + data.idDevis);
        postData("/detailsDevis",data,token, afficherDetailsDevis, onError);
    });
}

function afficherDevisClient(response){
	allHide();
	viewListeDevis();
    $("#voir-devis-client").show();
    $("#voir-devis-client tbody").html("");
	Object.keys(response.devisData).forEach(data => {
	    var html = "<tr>";
	    html+="<td><a href='#' class='reference-devis'>"
	    	+ response.devisData[data].idDevis + "</a></td>\n<td>"
	    	+ response.devisData[data].date.substring(0,10) + "</td>\n<td>" 
	    	+ response.devisData[data].montant + "€</td>\n<td>" 
	    	+ response.devisData[data].dureeTravaux + "</td>\n<td>" 
	    	+ response.devisData[data].etat + "</td></tr>";	    
	    $("#voir-devis-client tbody").append(html);
	});
	$("a.reference-devis").click(e=>{
    	let data={};
        data.idDevis=$(e.target).text();
        console.log("Reference devis : " + data.idDevis);
        postData("/detailsDevis",data,token, afficherDetailsDevisClient, onError);
    });
}

function viewListeDevis(){
	$("#login").hide();
    $("#btn-deconnexion").hide();
    $("#wrong_passwd").hide();
    $("#test1").hide();
    $("#carousel").hide();
    $("#Register-confirmation").hide();
    $("#introductionQuoteForm").hide();
    $("#voir-details-devis").hide();
    $("#voir-clients").hide();
    $("#voir-devis-client").hide();
    $("#voir-utilisateurs").hide();
    $("#voir-devis").hide();
    $("#choisirPhotoPreferee").hide();
	$("#ajouterPhoto").hide();
}

function mesDevis(){
	$("#voir-devis-client").show();	
	let data={};
	console.log("user = " + JSON.stringify(user));
	data = user;
	postData("/listeDevisClient",data,token,afficherDevisClient,onError);
}

export{afficherDevis, afficherDevisClient, viewListeDevis,onGetAmenagementDevis,onGetAmenagementDevisClient,searchDevis,onGetClientDevis};