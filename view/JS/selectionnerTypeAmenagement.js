import{token, allHide} from "./index.js";
import {postData,getData,onError, putData} from "./util.js" ;


function remplirListeTypesAmenagement(response){
	$("#navBarTypesAmenagements").html("");
	$("#navBarStaticTypesAmenagements").html("");
	$("#my-carousel #my-carousel-indicators").html("");
	$("#my-carousel #my-carousel-inner").html("");

	
	//Remplit la liste des types d'amenagement
	Object.keys(response.typesAmenagementData).forEach(data=>{
		var html = "<a class='dropdown-item' href='#' id='" + response.typesAmenagementData[data].id + "'>"+ response.typesAmenagementData[data].description + "</a>";
		$("#navBarTypesAmenagements").append(html);
		$("#navBarStaticTypesAmenagements").append(html);
	});
	//Remplit le carrousel avec les photos apres amenagement
	var compteurSlides = 0;
	Object.keys(response.photosData).forEach(data=>{
		var carouselIndicator = "<li data-target='#myCarousel' data-slide-to='" + compteurSlides+ "'></li>";
		var photoCarousel = "<div class='item'><img src='"+ response.photosData[data].photo +"' id='" + response.photosData[data].idPhoto +"'></div>";
		$("#my-carousel #my-carousel-indicators").append(carouselIndicator);
		$("#my-carousel #my-carousel-inner").append(photoCarousel);
		
		compteurSlides++;
	});
	
	
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
	
}

export{remplirListeTypesAmenagement};