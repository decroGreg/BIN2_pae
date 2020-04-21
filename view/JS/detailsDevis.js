import {putData,onError} from "./util.js" ;
import {allHide} from "./index.js";
function afficherDetailsDevis(response){
	console.log(JSON.stringify(response.devisData));
    allHide();
    if(response.devisData.etat === "FD"){
    	viewDevisDDI(response);
    }
    else{
    	$("#voir-details-devis").show();
    	$("#voir-details-devis #dateDevis").attr("value", response.devisData.date.substring(0,10));
    	$("#voir-details-devis #montantDevis").attr("value", response.devisData.montant);
    	$("#voir-details-devis #etatDevis").attr("value", response.devisData.etat);
    	$("#voir-details-devis #typeAmenagementDevis").attr("value", response.devisData.typeAmenagement);
    	$("#voir-details-devis #dureeTravauxDevis").attr("value", response.devisData.dureeTravaux);
    	$("#voir-details-devis #btn-devis").attr("value", changerValeurBouton(response.devisData.etat));
    	$("#voir-details-devis #btn-devis").click(e=>{
    		var nouvelEtat = changerEtat(response.devisData.etat);	
    		let data={};
    		data.idDevis = response.devisData.idDevis;
    		data.etatDevis = nouvelEtat;
    		data.dateDebutTravaux = $("#voir-details-devis #dateDebutTravaux").val();
    		console.log($("#voir-details-devis #dateDebutTravaux").val());
    		putData("/detailsDevis", token, data, viewDevisDC, onError);
    	});
    }
}

function viewDevisDC(response){
	allHide();
	$("#voir-details-devis-DC").show();
	$("#voir-details-devis-DDI").hide();
	console.log("ICI");
	$("#voir-details-devis-DC #dateDevis").attr("value", response.devisData.date.substring(0,10));
	$("#voir-details-devis-DC #montantDevis").attr("value", response.devisData.montant);
	$("#voir-details-devis-DC #etatDevis").attr("value", response.devisData.etat);
	$("#voir-details-devis-DC #typeAmenagementDevis").attr("value", response.devisData.typeAmenagement);
	$("#voir-details-devis-DC #dureeTravauxDevis").attr("value", response.devisData.dureeTravaux);
	$("#voir-details-devis-DC #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
	$("#voir-details-devis-DC #btn-devis").attr("value", changerValeurBouton(response.devisData.etat));
}

function viewDevisDDI(response){
	allHide();
	console.log("OK");
	$("#voir-details-devis-DDI").show();
	$("#voir-details-devis-DDI #dateDevis").attr("value", response.devisData.date.substring(0,10));
	$("#voir-details-devis-DDI #montantDevis").attr("value", response.devisData.montant);
	$("#voir-details-devis-DDI #etatDevis").attr("value", response.devisData.etat);
	$("#voir-details-devis-DDI #typeAmenagementDevis").attr("value", response.devisData.typeAmenagement);
	$("#voir-details-devis-DDI #dureeTravauxDevis").attr("value", response.devisData.dureeTravaux);
	$("#voir-details-devis-DDI #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
	$("#voir-details-devis-DDI #btn-devis").attr("value", changerValeurBouton(response.devisData.etat));
	$("#voir-details-devis-DDI #btn-devis").click(e=>{
		var nouvelEtat = changerEtat(response.devisData.etat);	
		let data={};
		data.idDevis = response.devisData.idDevis;
		data.etatDevis = nouvelEtat;
		data.dateDebutTravaux = $("#voir-details-devis-DDI #dateDebutTravaux").val();
		putData("/detailsDevis", token, data);
		response.devisData.etat = data.etatDevis;
		response.devisData.dateDebutTravaux = data.dateDebutTravaux;
		viewDevisDC(response);
	});
}

function changerValeurBouton(etat){
	var valeurBouton;
	switch (etat) {
	  case 'I':
		  valeurBouton = "Confirmer commande";
		  break;
	  case 'DDI':
		  valeurBouton = "Confirmer date";
		  break;
	  case 'ANP':
		  valeurBouton = "Repousser date début";
		  break;
	  case 'DC':
		  valeurBouton = "Annuler la commande";
		  break;
	  case 'A':
		  valeurBouton = "";
		  break;
	  case 'EC':
		  valeurBouton = "Envoyer facture de milieu de chantier";
		  break;
	  case 'FM':
		  valeurBouton = "";
		  break;
	  case 'T':
		  valeurBouton = "Envoyer facture finale";
		  break;
	  case 'FF':
		  valeurBouton = "Rendre visible";
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
		  nouvelEtat = "A";
		  break;
	  case 'A':
		  nouvelEtat = "FM";
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

export{afficherDetailsDevis, viewDevisDC, viewDevisDDI, changerEtat, changerValeurBouton};