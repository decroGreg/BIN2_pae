import {postData,getData,deleteData,putData, onError} from "./util.js" ;
import{afficherDevis} from "./afficherDevis.js";
import {allHide, token} from "./index.js";
import {ajouterPhoto} from "./rendreDevisVisible.js";


function afficherDetailsDevis(response){
    allHide();
	$("#voir-details-devis").show();
	$("#btn-ajouter-photo").hide();
	$("#btn-photo-preferee").hide();
	$("#btn-rendre-visible").hide();
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
	$("#voir-details-devis #typeAmenagementDevis").attr("value", response.devisData.typeAmenagement);
	//Pour voir si on peut changer la value de dateDebutTravaux
	if(response.devisData.etat=="I"){
		$("#voir-details-devis #dateDebutTravaux").attr("value", " ");
	}
	else if(response.devisData.etat=="FD"){
		$("#voir-details-devis #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
		$("#btn-devis-repousserDate").attr("idDevis", response.devisData.idDevis);
	}
	else if(response.devisData.etat=="DC"){
		$("#voir-details-devis #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
	}
	else{
		$("#voir-details-devis #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
		$("#voir-details-devis #dateDebutTravaux").disabled = true;
	}
	$("#voir-details-devis #dureeTravauxDevis").attr("value", response.devisData.dureeTravaux);
	$("#voir-details-devis #btn-devis").attr("value", changerValeurBouton(response.devisData.etat));
	$("#voir-details-devis #btn-devis").click(e=>{
    	e.preventDefault();
    	if($("#voir-details-devis #btn-devis").val()=="Rendre visible"){
    		rendreVisible();
    	}
		var nouvelEtat = changerEtat(response.devisData.etat);	
		let data={};
		data.idDevis = response.devisData.idDevis;
		data.etatDevis = nouvelEtat;
		data.dateDebutTravaux = $("#voir-details-devis #dateDebutTravaux").val();
		console.log($("#voir-details-devis #dateDebutTravaux").val() + nouvelEtat + " " + data.idDevis);
		postData("/changementEtatDevis", data, token, afficherDetailsDevis, onError);
	});
	$("#voir-details-devis #btn-devis-annuler").click(e=>{
    	e.preventDefault();
		let data={};
		data.idDevis = response.devisData.idDevis;
		data.etatDevis = 'A';
		postData("/changementEtatDevis", data, token, afficherDevis,onError);	
	});
	$("btn-ajouter-photo").click(e=>{
		e.preventDefault();
		//ajouterPhoto();
	});
	$("btn-photo-preferee").click(e=>{
		e.preventDefault();
		//choisirPhotoPreferee();
	});
	$("btn-rendre-visible").click(e=>{
		e.preventDefault();
		let data={};
		data.idDevis = response.devisData.idDevis;
		data.etatDevis = "V";
		postData("/changementEtatDevis", data, token, afficherDetailsDevis, onError);
	});
}


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
		  $("#voir-details-devis #btn-devis").hide();
		  $("#voir-details-devis #btn-devis-annuler").hide();
		  $("#btn-ajouter-photo").show();
		  $("#btn-photo-preferee").show();
		  $("#btn-rendre-visible").show();
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