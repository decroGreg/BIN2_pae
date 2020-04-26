import{token, allHide} from "./index.js";
import{afficherDetailsDevis} from "./detailsDevis.js";
import {postData,onError,creatHTMLFromString} from "./util.js" ;


function onGetAmenagementDevis(response){
	afficherAmenagementDropDown(response,document.getElementById("amenagements-devis"));
}
function onGetAmenagementDevisClient(response){
	afficherAmenagementDropDown(response,document.getElementById("amenagements-devis-client"));
}

function afficherAmenagementDropDown(response,dropdown){
	console.log(response.typeAmenagements);
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
		}
	})
	var data={}
	dropdown.parentNode.parentNode.childNodes.forEach(e=>{
		if(e.name)
		data[e.name]=e.value;
	});
	if(idUser){
		data["idUser"]=idUser;
	}

	console.log(amenagements);
	console.log(data);
	var send={
		"data":data,
		"amenagements":amenagements
	}
	postData("rechercheDevis",token,data,afficherDevis,onError)
}




function afficherDevis(response){
	allHide();
	viewListeDevis();
	Object.keys(response.devisData).forEach(data => {
	    var html = "<tr>";
	    html+="<td><a href=\"#\" class=\"reference-devis\">"
	    	+ response.devisData[data].idDevis + "</a></td>\n<td>" 
	    	+ response.devisData[data].idClient + "</td>\n<td>" 
	    	+ response.devisData[data].date.substring(0,10) + "</td>\n<td>" 
	    	+ response.devisData[data].montant + "€</td>\n<td>" 
	    	+ response.devisData[data].dureeTravaux + "</td>\n<td>" 
	    	+ response.devisData[data].photoPreferee + "</td>\n<td>" 
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
	Object.keys(response.devisData).forEach(data => {
	    var html = "<tr>";
	    html+="<td><a href='#' class='reference-devis'>"
	    	+ response.devisData[data].idDevis + "</a></td>\n<td>"
	    	+ response.devisData[data].date.substring(0,10) + "</td>\n<td>" 
	    	+ response.devisData[data].montant + "€</td>\n<td>" 
	    	+ response.devisData[data].dureeTravaux + "</td>\n<td>" 
	    	+ response.devisData[data].photoPreferee + "</td>\n<td>" 
	    	+ response.devisData[data].etat + "</td></tr>";	    
	    $("#voir-devis-client tbody").append(html);
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
    $("#voir-details-devis-DC").hide();
    $("#voir-details-devis-DDI").hide();
    $("#voir-clients").hide();
    $("#voir-devis-client").hide();
    $("#voir-utilisateurs").hide();
    $("#voir-devis").show();
    $("#choisirPhotoPreferee").hide();
	$("#ajouterPhoto").hide();
}

export{afficherDevis, afficherDevisClient, viewListeDevis,onGetAmenagementDevis,onGetAmenagementDevisClient,searchDevis};