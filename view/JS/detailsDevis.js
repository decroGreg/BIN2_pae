import {postData,getData,deleteData,putData} from "./util.js" ;
import{afficherDevis} from "./afficherDevis.js";

function afficherDetailsDevis(response){
	console.log(JSON.stringify(response.devisData));
    allHide();
	$("#voir-details-devis").show();
	$("#voir-details-devis #dateDevis").attr("value", response.devisData.date.substring(0,10));
	$("#voir-details-devis #montantDevis").attr("value", response.devisData.montant);
	$("#voir-details-devis #etatDevis").attr("value", response.devisData.etat);
	$("#voir-details-devis #typeAmenagementDevis").attr("value", response.devisData.typeAmenagement);
	//Pour voir si on peut changer la value de dateDebutTravaux
	if(response.devisData.etatDevis=='I'){
		$("#voir-details-devis #dateDebutTravaux").attr("value", "");
	}
	else if(response.devisData.etatDevis=='FD'){
		$("#voir-details-devis #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
	}
	else if(response.devisData.etatDevis=='DC'){
		$("#voir-details-devis #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
	}
	else{
		$("#voir-details-devis #dateDebutTravaux").readOnly = true;
	}
	$("#voir-details-devis #dureeTravauxDevis").attr("value", response.devisData.dureeTravaux);
	$("#voir-details-devis #btn-devis").attr("value", changerValeurBouton(response.devisData.etat));
	$("#voir-details-devis #btn-devis").click(e=>{
		var nouvelEtat = changerEtat(response.devisData.etat);	
		let data={};
		data.idDevis = response.devisData.idDevis;
		data.etatDevis = nouvelEtat;
		data.dateDebutTravaux = $("#voir-details-devis #dateDebutTravaux").val();
		console.log($("#voir-details-devis #dateDebutTravaux").val());
		putData("/changementEtatDevis", token, data, afficherDetailsDevis, onError);
	});
	$("#voir-details-devis #btn-devis-annuler").click(e=>{
		let data={};
		data.idDevis = response.devisData.idDevis;
		data.etatDevis = 'A';
		postData("/changementEtatDevis", data, token, afficherDevis,onError);		  
	});
}

/*function viewDevisDC(response){
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
}*/

/*function viewDevisDDI(response){
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
	$("#voir-details-devis #btn-devis-annuler").click(e=>{
		let data={};
		data.idDevis = response.devisData.idDevis;
		data.etatDevis = 'A';
		postData("/changementEtatDevis", data, token, afficherDevis,onError);		  
	});

	
}*/

function changerValeurBouton(etat){
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
		  $("#voir-details-devis #btn-devis-annuler").hide();
		  break;
	  case 'FM':
		  valeurBouton = "Facture fin de chantier envoyee";
		  $("#voir-details-devis #btn-devis-annuler").hide();
		  break;
	  case 'FF':
		  valeurBouton = "Rendre visible";
		  $("#voir-details-devis #btn-devis-annuler").hide();
		  break;
	  case 'V':
		  valeurBouton = "";
		  $("#voir-details-devis #btn-devis").hide();
		  $("#voir-details-devis #btn-devis-annuler").hide();
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

export{afficherDetailsDevis, changerEtat, changerValeurBouton};