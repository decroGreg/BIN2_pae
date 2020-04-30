import {postData,getData,deleteData, putData, onError} from "./util.js" ;
import{afficherDevis} from "./afficherDevis.js";
import {allHide, token} from "./index.js";
import{afficherDetailsDevis} from "./detailsDevis.js";

function ajouterPhoto(response){
	//response = tous les amenagements du devis et la description du type amenagement
	$("#btn-ajouter-photo").hide();
	$("#btn-photo-preferee").hide();
	$("#btn-rendre-visible").hide();
	$("#choisirPhotoPreferee").hide();
	$("#ajouterPhoto").show();
	$("#ajouterPhoto #listeTypeAmenagements").html("");
	Object.keys(response.amenagementsData).forEach(data=>{
		var html = "<option id="+response.amenagementsData[data].idAmenagement+">" + response.typesAmenagementData[data] + "</option>";
		$("#ajouterPhoto #listeTypeAmenagements").append(html);
	});
	$("#ajouterPhoto #photoDevis").change(e=>{
        encodeImagetoBase64(e.target);
	});
	$("#btn-ajout-photo").click(e=>{
		e.preventDefault();
		let data={};
		var photo = $("#ajouterPhoto #photoAjoutee img").attr("src");
		data.urlPhoto = photo;
		if($("#visibilitePhoto").is(":checked")){
			data.visible = 1;
		}
		else{
			data.visible = 0;
		}
		data.idAmenagement = $("#listeTypeAmenagements option:selected").attr("id");
		console.log(data.urlPhoto);
		putData("/ajouterPhoto", token, data, afficherDetailsDevis, onError);
	});
}

function choisirPhotoPreferee(response){
	//response = toutes les photos apres amenagement du devis
	$("#choisirPhotoPreferee").show();
	$("#formPhotoPreferee").html(" ");
	Object.keys(response.photosData).forEach(data=>{
		var html = "<div class='radio'><label><input type='radio' name='radioButton' id='" + response.photosData[data].idPhoto + "'>" + "<img src='"+ response.photosData[data].urlPhoto +"'>"  +"</label></div>";
		$("#formPhotoPreferee").append(html);
	});
	var boutonValider = "<div class='form-group col-md-3' style='text-align: right;'><input type='submit' id='btn-valider-preferee' class='btn btn-danger'  value='Valider'></div>";
	$("#formPhotoPreferee").append(boutonValider);
	$("#btn-valider-preferee").click(e=>{
		e.preventDefault();
		let data={};
		data.idPhoto = $('input[name=radioButton]:checked').attr("id");
		console.log("idPhoto = " + data.idPhoto);
		putData("/photoPreferee", token, data, afficherDetailsDevis, onError);
	});
}

function encodeImagetoBase64(element) {
    var file = element.files[0];
    var reader = new FileReader();

    reader.onloadend = function() {
    	$("#ajouterPhoto #photoAjoutee img").attr("src",reader.result);
      
    }
    reader.readAsDataURL(file);        
}


export{ajouterPhoto, choisirPhotoPreferee};