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
	var compteurSlides = 0;
	// Activate Carousel
   
	Object.keys(response.photosData).forEach(data=>{
		console.log("idPhoto = " + response.photosData[data].idPhoto);
		var carouselIndicator = "<li data-target='#myCarousel' data-slide-to='" + compteurSlides+ "'></li>";
		var photoCarousel = "<div class='item'><img class='d-block w-100' src='"+ response.photosData[data].urlPhoto +"' id='" + response.photosData[data].idPhoto +"' alt='slide'></div>";
		console.log(carouselIndicator);
		$("#myCarousel #carousel-indicators").append(carouselIndicator);
		$("#myCarousel #carousel-inner").append(photoCarousel);
		compteurSlides++;
	});
	$(".carousel-indicators .li:first").addClass("active");
    $(".carousel-inner .item:first").addClass("active");
    $(function(){
        // Activate Carousel
        $("#myCarousel").carousel();
    });
    $('#myCarousel').carousel({
    	  interval: 2000
    });
    // Enable Carousel Indicators
    $(".item").click(function(){
    	$("#myCarousel").carousel(1);
    });

    // Enable Carousel Controls
    $(".left").click(function(){
    	$("#myCarousel").carousel("prev");
    });
    
	$(".right").click(function() {
		$("#myCarousel").carousel("next");
	});
}

export{remplirListeTypesAmenagement};