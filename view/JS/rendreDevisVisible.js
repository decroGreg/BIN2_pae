import {postData,getData,deleteData, putData, onError} from "./util.js" ;
import {allHide, token} from "./index.js";
import{afficherDetailsDevis} from "./detailsDevis.js";


$(document).ready(function () {

	$("#btn-ajout-photo").off().click(e=>{
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
		if($("#ajouterPhoto #photoAjoutee img").attr("src") == "#"){
			alert("Vous devez ajouter un fichier.");
		}
		else{
			$("#ajouterPhoto").hide();
			data.idAmenagement = $("#listeTypeAmenagements option:selected").attr("id");
			putData("/ajouterPhoto", token, data, onPostPhoto, onError);
		}
	});
});


function ajouterPhoto(response){
	//response = tous les amenagements du devis et la description du type amenagement
	$("#btn-photo-preferee").show();
	$("#btn-rendre-visible").show();
	$("#choisirPhotoPreferee").hide();
	$("#formPhotoPreferee").hide();
	$("#ajouterPhoto").show();
	$("#ajouterPhoto #listeTypeAmenagements").html("");
	$("#ajouterPhoto #photoAjoutee img").attr("src", "#");
	$("#visibilitePhoto").checked = false;
	$("#ajouterPhoto #photoDevis").val("");
	Object.keys(response.amenagementsData).forEach(data=>{
		var html = "<option id="+response.amenagementsData[data].idAmenagement+">" + response.typesAmenagementData[data] + "</option>";
		$("#ajouterPhoto #listeTypeAmenagements").append(html);
	});
	$("#ajouterPhoto #photoDevis").change(e=>{
        encodeImagetoBase64(e.target);
	});
}

function choisirPhotoPreferee(response){
	//response = toutes les photos apres amenagement du devis
	$("#ajouterPhoto").hide();
	$("#btn-ajouter-photo").show();
	$("#choisirPhotoPreferee").show();
	$("#formPhotoPreferee").show();
	$("#formPhotoPreferee").html("");
	Object.keys(response.photosData).forEach(data=>{
		var html = "<div class='radio'><label><input type='radio' name='radioButton' id='" + response.photosData[data].idPhoto + "'>" + "<img src='"+ response.photosData[data].urlPhoto +"' width='193' height='130'>"  +"</label></div>";
		$("#formPhotoPreferee").append(html);
	});
	var boutonValider = "<div class='form-group col-md-3' style='text-align: right;'><input type='submit' id='btn-valider-preferee' class='btn btn-danger'  value='Valider'></div>";
	$("#formPhotoPreferee").append(boutonValider);
	$("#btn-valider-preferee").off().click(e=>{
		e.preventDefault();
		let data={};
		data.idPhoto = $('input[name=radioButton]:checked').attr("id");
		$("#formPhotoPreferee").hide();
		putData("/photoPreferee", token, data, onPostPhoto, onError);
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

function onPostPhoto(response){
	console.log("PHOTO PREF");
    if(response.success=="true"){
            $("#success-notification").fadeIn('slow').delay(1000).fadeOut('slow');
            $("#success-notification").text(response.message);
    }else{
            $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
            $("#error-notification").text(response.message);
    }
    afficherDetailsDevis(response);
}



export{ajouterPhoto, choisirPhotoPreferee};