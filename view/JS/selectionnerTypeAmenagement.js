import{token, allHide} from "./index.js";
import {postData,getData,onError, putData} from "./util.js" ;


function remplirListeTypesAmenagement(response){
	$("#navBarTypesAmenagements").html("");
	$("#navBarStaticTypesAmenagements").html("");
	$("#myCarousel #carousel-indicators").html("");
	$("#myCarousel #carousel-inner").html("");
	 //$('.carousel-inner,.carousel-indicators,.carousel-control-prev,.carousel-control-next').empty();

	
	//Remplit la liste des types d'amenagement
	Object.keys(response.typesAmenagementData).forEach(data=>{
		var html = "<a class='dropdown-item' href='#' id='" + response.typesAmenagementData[data].id + "'>"+ response.typesAmenagementData[data].description + "</a>";
		$("#navBarTypesAmenagements").append(html);
		$("#navBarStaticTypesAmenagements").append(html);
	});
	changerCarrousel(response);
	$("#navBarTypesAmenagements a").click(e=>{
		console.log("ID type amenagement = " + $(e.target).attr("id"));
		let data={};
		data.idTypeAmenagement = $(e.target).attr("id");
		postData("/voirTypesAmenagement", data, token, changerCarrousel, onError);
	});
	$("#navBarStaticTypesAmenagements a").click(e=>{
		console.log("ID type amenagement = " + $(e.target).attr("id"));
		let data={};
		data.idTypeAmenagement = $(e.target).attr("id");
		postData("/voirTypesAmenagement", data, token, changerCarrousel, onError);
	});
	
}

function changerCarrousel(response){
	//Remplit le carrousel avec les photos apres amenagement
	$("#myCarousel #carousel-indicators").html("");
	$("#myCarousel #carousel-inner").html("");
	 //$(".carousel-inner,.carousel-indicators,.carousel-control-prev,.carousel-control-next").empty();
	var compteurSlides = 0;
	Object.keys(response.photosData).forEach(data=>{
		console.log("idPhoto = " + response.photosData[data].idPhoto);
		var carouselIndicator = "<li data-target='#myCarousel' data-slide-to='" + compteurSlides+ "' id='"+ compteurSlides +"'></li>";
		$("#myCarousel #carousel-indicators").append(carouselIndicator);
		var photoCarousel = "<div class='item'><img src='"+ response.photosData[data].urlPhoto +"' id='" + response.photosData[data].idPhoto +"' alt='slide'></div>";
		$("#myCarousel #carousel-inner").append(photoCarousel);
		compteurSlides++;
	});
	$(".carousel-indicators li:first").addClass("active");
    $(".carousel-inner .item:first").addClass("active");
    $(".carousel").carousel({
    	  interval: 2000
    });
}

export{remplirListeTypesAmenagement};