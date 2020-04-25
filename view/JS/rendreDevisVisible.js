import {postData,getData,deleteData,putData, onError} from "./util.js" ;
import{afficherDevis} from "./afficherDevis.js";
import {allHide, token} from "./index.js";
import{afficherDetailsDevis} from "./detailsDevis.js";


function ajouterPhoto(response){
	//response = tous les amenagements du devis et la description du type amenagement
	$("#btn-ajouter-photo").hide();
	$("#btn-photo-preferee").hide();
	$("#btn-rendre-visible").hide();
	$("#ajouterPhoto").show();
	Object.keys(response.amenagementsData).forEach(data=>{
		var html = "<option id="+response.amenagementsData[data].idAmenagement+">" + response.typesAmenagementData[data] + "</option>";
		$("#ajouterPhoto #listeTypeAmenagements").append(html);
	});
	$("#btn-ajout-photo").click(e=>{
		let data={};
		data.urlPhoto = $("#ajouterPhoto #photoDevis").val();
		data.idAmenagement = $("#listeTypeAmenagements option:selected").attr("id");
		putData("/ajouterPhoto", token, data, afficherDetailsDevis, onError);
	});
}

function choisirPhotoPreferee(response){
	//response = toutes les photos apres amenagement du devis
	$("#choisirPhotoPreferee").show();
	Object.keys(response.photosData).forEach(data=>{
		var html = "<div class='radio'><label><input type='radio' id='" + response.photosData[data].idPhoto + "'>" + "PHOTO"  +"</label></div>";
		$("#formPhotoPreferee").append(html);
	});
	$("#btn-valider-preferee").click(e=>{
		let data={};
		data.idPhoto = $('input[name=radioName]:checked', '#formPhotoPreferee').attr("id");
		putData("/photoPreferee", token, data, afficherDetailsDevis, onError);
	});
}





export{ajouterPhoto, choisirPhotoPreferee};