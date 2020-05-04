import {postData,getData,deleteData,putData, onError} from "./util.js" ;
import{afficherDevis} from "./afficherDevis.js";
import {allHide, token} from "./index.js";
import {ajouterPhoto, choisirPhotoPreferee} from "./rendreDevisVisible.js";


function afficherDetailsDevis(response){
    allHide();
	$("#voir-devis").show();
	$("#voir-details-devis").show();
	$("#btn-ajouter-photo").hide();
	$("#btn-photo-preferee").hide();
	$("#btn-rendre-visible").hide();
	$("#ajouterPhoto").hide();
	$("#choisirPhotoPreferee").hide();
	$("#btn-devis-repousserDate").hide();
	$("#photoPrefereeDevis").hide();
	$("#voir-details-devis #dateDebutTravaux").attr("value", " ");

	//Remplir donnees client
	$("#voir-details-devis #nomClient").attr("value", response.clientData.nom);
	$("#voir-details-devis #mailClient").attr("value", response.clientData.email);
	$("#voir-details-devis #prenomClient").attr("value", response.clientData.prenom);
	$("#voir-details-devis #telClient").attr("value", response.clientData.telephone);
	$("#voir-details-devis #codePostalClient").attr("value", response.clientData.codePostal);
	$("#voir-details-devis #villeClient").attr("value", response.clientData.ville);
	//Remplir donnees devis
	$("#voir-details-devis #dateDevis").attr("value", response.devisData.date.substring(0,10));
	$("#voir-details-devis #montantDevis").attr("value", response.devisData.montant);
	$("#voir-details-devis #etatDevis").attr("value", response.devisData.etat);
	//Remplir types d'amenagement
	var html = " ";
	$("#voir-details-devis #typeAmenagementDevis").html("");
	Object.keys(response.typesAmenagementData).forEach(data=>{
		html = html + response.typesAmenagementData[data] + "\n";
	});
	$("#voir-details-devis #typeAmenagementDevis").append(html);
	$("#voir-details-devis #typeAmenagementDevis").prop("disabled", true);
	
	//Si le devis est visible, j'affiche la photo preferee
	if(response.devisData.etat=="V" && response.photoPrefereeData != undefined){
		$("#photoPrefereeDevis").html("");
		$("#photoPrefereeDevis").show();
		$("#photoPrefereeDevis").append("<img src='"+ response.photoPrefereeData.urlPhoto +"' width='193' height='130'>");
	}
	//Pour voir si on peut changer la value de dateDebutTravaux

	if(response.devisData.etat=="I" || response.devisData.etat=="A"){
		$("#voir-details-devis #dateDebutTravaux").attr("value", " ");
		$("#voir-details-devis #dateDebutTravaux").prop("disabled", false);
	}
	else if(response.devisData.etat=="FD"){
		$("#btn-devis-repousserDate").show();
		$("#voir-details-devis #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
		$("#btn-devis-repousserDate").attr("idDevis", response.devisData.idDevis);
		console.log("afficher***********************"+response.devisData.idDevis);
		$("#btn-devis-repousserDate").attr("etat",response.devisData.etat);
		$("#voir-details-devis #dateDebutTravaux").prop("disabled", false);

	}
	else if(response.devisData.etat=="DC"){
		$("#btn-devis-repousserDate").show();
		$("#voir-details-devis #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
		$("#btn-devis-repousserDate").attr("idDevis", response.devisData.idDevis);
		console.log("afficher***********************"+response.devisData.idDevis);
		$("#btn-devis-repousserDate").attr("etat",response.devisData.etat);
		$("#voir-details-devis #dateDebutTravaux").prop("disabled", false);
	}
	else{
		$("#voir-details-devis #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
		$("#voir-details-devis #dateDebutTravaux").prop("disabled", true);
	}
	$("#voir-details-devis #dureeTravauxDevis").attr("value", response.devisData.dureeTravaux);
	
	var valBouton = changerValeurBouton(response.devisData.etat);
	$("#voir-details-devis #btn-devis-etat").attr("value", valBouton);
	
	//click sur changement etat
	$("#voir-details-devis #btn-devis-etat").off().click(e=>{
    	e.preventDefault();
		var nouvelEtat = changerEtat(response.devisData.etat);	
		let data={};
		data.idDevis = response.devisData.idDevis;
		data.etatDevis = nouvelEtat;
		data.dateDebutTravaux = $("#voir-details-devis #dateDebutTravaux").val();
		if (confirm("Etes vous sur de vouloir changer l'etat du devis?")) {
			postData("/changementEtatDevis", data, token, afficherDetailsDevis, onError);

	       } else {
	           return false;
	       }
	});
	
	//click sur annuler devis
	$("#voir-details-devis #btn-devis-annuler").off().click(e=>{
    	e.preventDefault();
		let data={};
		data.idDevis = response.devisData.idDevis;
		data.etatDevis = 'A';
		postData("/changementEtatDevis", data, token, afficherDetailsDevis,onError);	
	});
	
	//click sur ajouter photo
	$("#voir-details-devis #btn-ajouter-photo").off().click(e=>{
		e.preventDefault();
		let data={};
		data.idDevis = response.devisData.idDevis;
		postData("/ajouterPhoto", data, token, ajouterPhoto, onError);
	});
	
	//click sur choisir photo preferee
	$("#btn-photo-preferee").click(e=>{
		e.preventDefault();
		let data={};
		data.idDevis = response.devisData.idDevis;
		postData("/photoPreferee", data, token, choisirPhotoPreferee, onError);
	});
	
	//click sur rendre visible
	$("#voir-details-devis #btn-rendre-visible").click(e=>{
		e.preventDefault();
		let data={};
		data.idDevis = response.devisData.idDevis;
		data.etatDevis = "V";
		postData("/changementEtatDevis", data, token, afficherDetailsDevis, onError);
	});
}


function changerValeurBouton(etat){
	$("#voir-details-devis #btn-devis-etat").show();
	var valeurBouton;
	switch (etat) {
	  case 'I':
		  valeurBouton = "Confirmer commande";
		  $("#voir-details-devis #btn-devis-annuler").show();
		  break;
	  case 'FD':
		  valeurBouton = "Confirmer date";
		  $("#voir-details-devis #btn-devis-annuler").show();
		  break;
	  case 'DC':
		  valeurBouton = "Facture milieu de chantier envoyee";
		  $("#voir-details-devis #btn-devis-annuler").show();
		  break;
	  case 'A':
		  $("#voir-details-devis #btn-devis-etat").hide();
		  $("#voir-details-devis #btn-devis-annuler").hide();
		  break;
	  case 'FM':
		  valeurBouton = "Facture fin de chantier envoyee";
		  $("#voir-details-devis #btn-devis-annuler").hide();
		  break;
	  case 'FF':
		  $("#voir-details-devis #btn-devis-etat").hide();
		  $("#voir-details-devis #btn-devis-annuler").hide();
		  $("#btn-devis-repousserDate").hide();
		  $("#btn-ajouter-photo").show();
		  $("#btn-photo-preferee").show();
		  $("#btn-rendre-visible").show();
		  break;
	  case 'V':
		  valeurBouton = "";
		  $("#voir-details-devis #btn-devis-etat").hide();
		  $("#voir-details-devis #btn-devis-annuler").hide();
		  $("#btn-devis-repousserDate").hide();
		  break;
	  default:
		  break;
	}
	return valeurBouton;
}

function changerEtat(etat){
	var nouvelEtat;
	switch (etat) {
	  case 'I':
		  nouvelEtat = "FD";
		  break;
	  case 'FD':
		  nouvelEtat = "DC";
		  break;
	  case 'DC':
		  nouvelEtat = "FM";
		  break;
	  case 'A':
		  break;
	  case 'FM':
		  nouvelEtat = "FF";
		  break;
	  case 'FF':
		  nouvelEtat = "V";
		  break;
	  default:
		  break;
	}
	
	return nouvelEtat;

}

function afficherDetailsDevisClient(response){
    allHide();
	$("#voir-devis-client").show();
    $("#voir-details-devis").show();
	$("#btn-ajouter-photo").hide();
	$("#btn-photo-preferee").hide();
	$("#btn-rendre-visible").hide();
	$("#ajouterPhoto").hide();
	$("#choisirPhotoPreferee").hide();
	$("#voir-details-devis #btn-devis-etat").hide();
	$("#voir-details-devis #btn-devis-annuler").hide();
	$("#voir-details-devis #clientDevis").hide();
	$("#voir-details-devis #btn-devis-etat").hide();
	$("#btn-devis-repousserDate").hide();
	$("#voir-details-devis #dateDebutTravaux").attr("value", " ");
	
	//remplir details devis
	$("#voir-details-devis #dateDevis").attr("value", response.devisData.date.substring(0,10));
	$("#voir-details-devis #montantDevis").attr("value", response.devisData.montant);
	$("#voir-details-devis #etatDevis").attr("value", response.devisData.etat);
	$("#voir-details-devis #typeAmenagementDevis").attr("value", response.devisData.typeAmenagement);
	if(response.devisData.etat=="I" || response.devisData.etat=="A"){
		$("#voir-details-devis #dateDebutTravaux").attr("value", " ");
	}
	
	$("#voir-details-devis #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
	
	$("#voir-details-devis #dateDebutTravaux").prop("disabled", true);
	$("#voir-details-devis #dureeTravauxDevis").attr("value", response.devisData.dureeTravaux);
	//Remplir types d'amenagement
	var html = " ";
	$("#voir-details-devis #typeAmenagementDevis").html("");
	Object.keys(response.typesAmenagementData).forEach(data=>{
		html = html + response.typesAmenagementData[data] + "\n";
	});
	$("#voir-details-devis #typeAmenagementDevis").append(html);
	$("#voir-details-devis #typeAmenagementDevis").prop("disabled", true);
	//Si le devis est visible, j'affiche la photo preferee
	if(response.devisData.etat=="V" && response.photoPrefereeData != undefined){
		$("#photoPrefereeDevis").html("");
		$("#photoPrefereeDevis").show();
		$("#photoPrefereeDevis").append("<img src='"+ response.photoPrefereeData.urlPhoto +"' width='193' height='130'>");
	}

}

export{afficherDetailsDevis, changerEtat, changerValeurBouton, afficherDetailsDevisClient};