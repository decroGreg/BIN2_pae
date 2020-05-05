import{token, allHide, user} from "./index.js";
import {postData,onError,creatHTMLFromString} from "./util.js" ;


function afficherPhotos(response){
	viewPhotosClient();
	$("#voir-photos-client").show();
	$("#voir-photos-client #div-photos-client").html("");
	var html="";
	Object.keys(response.photosData).forEach(data => {
		html = html +  "<div class='col-lg-3 col-md-4 col-6'><a href='#' class='d-block mb-4 h-100'><img src='" + response.photosData[data].urlPhoto + "' alt='picture' width'400' height='300'></a></div>";

	});
	$("#voir-photos-client #div-photos-client").append(html);
}

function viewPhotosClient(){
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
	$("#voir-photos-client").show();
}

export{afficherPhotos};