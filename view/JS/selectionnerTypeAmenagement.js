import{token, allHide} from "./index.js";
import {postData,getData,onError, putData} from "./util.js" ;


function remplirListeTypesAmenagement(response){
	$("#navBarTypesAmenagements").html("");
	$("#navBarStaticTypesAmenagements").html("");
	$("#myCarousel #carousel-indicators").html("");
	$("#myCarousel #carousel-inner").html("");

	
	//Remplit la liste des types d'amenagement
	Object.keys(response.typesAmenagementData).forEach(data=>{
		var html = "<a class='dropdown-item' href='#' id='" + response.typesAmenagementData[data].id + "'>"+ response.typesAmenagementData[data].description + "</a>";
		$("#navBarTypesAmenagements").append(html);
		$("#navBarStaticTypesAmenagements").append(html);
	});
	changerCarrousel(response);
	$("#navBarTypesAmenagements a").click(e=>{
		e.preventDefault();
		console.log("ID type amenagement = " + $(e.target).attr("id"));
		let data={};
		data.idTypeAmenagement = $(e.target).attr("id");
		postData("/voirTypesAmenagement", data, token, changerCarrousel, onError);
	});
	$("#navBarStaticTypesAmenagements a").click(e=>{
		e.preventDefault();
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
	var compteurSlides = 0;
	// Activate Carousel
    $(".carousel slide").attr("data-ride","carousel");
    $(".carousel slide").attr("data-interval", "2000");


	Object.keys(response.photosData).forEach(data=>{
		var carouselIndicator = "<li data-target='#myCarousel' data-slide-to='" + compteurSlides+ "'></li>";
		var photoCarousel = "<div class='carousel-item'>" +
							"<img class='d-block w-100' src='"+ response.photosData[data].urlPhoto +"' id='" + response.photosData[data].idPhoto+"' alt='slide'>" +
							"<div class='carousel-caption d-none d-md-block'>" +
							"<h5>"+response.typesAmenagementPhotosData[data].description+"</h5></div></div>";
		
		$("#myCarousel #carousel-indicators").append(carouselIndicator);
		$("#myCarousel #carousel-inner").append(photoCarousel);
		compteurSlides++;
	});
	$('.carousel-item').first().addClass('active');
    $('.carousel-indicators > li').first().addClass('active');
    $(".carousel slide").attr("data-ride","carousel");
    $(".carousel slide").attr("data-interval", "2000");
    /*$("#myCarousel").carousel();

    $('#myCarousel').carousel({
        interval: 2000
    });
    //$("#myCarousel").carousel();

    $('.carousel slide').carousel('cycle');
    // Enable Carousel Controls
    $(".carousel-control-prev").click(e=>{
    	e.stopPropagation();
    	console.log("PREV");
    	$("#myCarousel").carousel('prev');
    });
    
	$(".carousel-control-next").click(e=> {
		e.stopPropagation();
    	console.log("NEXT");
		$("#myCarousel").carousel('next');
	});*/

}

export{remplirListeTypesAmenagement};