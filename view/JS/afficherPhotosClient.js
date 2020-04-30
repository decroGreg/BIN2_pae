import{token, allHide} from "./index.js";
import {postData,onError,creatHTMLFromString} from "./util.js" ;

function afficherPhotos(response){
	viewPhotosClient();
	$("#voir-photos-client").show();
	$("#voir-photos-client mdb-lightbox").html("");
	Object.keys(response.photosData).forEach(data => {
		var html = "<figure class='col-md-4'><a data-size='1600x1067'><img alt='picture' src='" + response.photosData[data].urlPhoto + "' class='img-fluid'></a></figure>";

		$("#voir-photos-client mdb-lightbox").append(html);
	});

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