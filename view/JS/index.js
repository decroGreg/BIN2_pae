"use-strict";
import {postData,getData,onError, putData} from "./util.js" ;
import{viewAuthentification,viewLogin,onPostRegister} from "./connexion.js"
import{afficherClients,afficherClientsDropdown} from "./afficherClients.js";
import{afficherDevis, afficherDevisClient,onGetAmenagementDevis,onGetAmenagementDevisClient,onGetClientDevis,onGetClientDevisClient} from "./afficherDevis.js";
import{afficherUtilisateurs,afficherUtilisateursDropdown} from "./afficherUtilisateurs.js";
import{afficherDetailsDevis, changerEtat, changerValeurBouton} from "./detailsDevis.js";
import{remplirListeTypesAmenagement} from "./selectionnerTypeAmenagement.js";
import{afficherPhotos} from "./afficherPhotosClient.js";

let token=undefined;
let user;
$('#navigation_bar').hide();
$("#success-notification").hide();
$("#error-notification").hide();




function checkInput(data,message){
        var element;
        console.log(data);
        
        for(element in data){
                console.log(data[element])
                if(!data[element]){
                        
                        $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                        $("#error-notification").text(message);
                        return false;
                }
        }
        return true;
}

$(document).ready(e=>{
        token=localStorage.getItem("token");
        authentificationToken(token);
        getData("/voirTypesAmenagement", token, remplirListeTypesAmenagement, onError);
        $(".connexion").click(function (e) {
               viewLogin();
        });
        $("#imageMenu").click(e=>{
            if(token)
                viewAuthentification(user);
            else viewHomePage();
        });
                
        $(window).bind('scroll', function() {

                var navHeight = $( window ).height() - 70;
                if ($(window).scrollTop() <= $("#carousel").height()/2) {
                        $('#navigation_bar').hide();
                }
                else {
                        $('#navigation_bar').show();
                }
        });
 
    $("#homePage").click(e=>{
            if(token) viewAuthentification(user);
            else      viewHomePage
        });
    $("#btn-connexion").click(e=>{
            let data={};
            data.mail=$("#login-email").val();
            data.mdp=$("#login-pwd").val();
            if(!checkInput(data,"veuillez remplir tous les champs pour la connexion")) return;
            postData("/login",data,token,onPostLogin,onError);
            
    });
    $("#btn-register").click(e=>{
        let data={};
        data.firstname=$("#Register-firstname").val();
        data.lastname=$("#Register-lastname").val();
        data.mail=$("#Register-email").val();
        data.mdp=$("#Register-pwd").val();
        data.city=$("#Register-city").val();
        data.pseudo=$("#Register-pseudo").val();
        if(!checkInput(data,"veuillez remplir tous les champs du nouveau clients")) return;
        postData("/register",data,token,onPostRegister,onError);
        
    });
   

    $("#btn-deconnexion").click(e=>{
        e.preventDefault()
        token=undefined;
        localStorage.removeItem("token");
        console.log(token);
        console.log(localStorage.getItem("token"));
        viewHomePage();
        

    });
    
    $(".mesDevis").click(e=>{
        e.preventDefault();
    	allHide();
        mesDevis();
        //getData("/listeUsers",token,onGetClientDevisClient,onError);
        //alert();
        //getData("/introduireServlet",token,onGetAmenagementDevisClient,onError);
    });
    
    $("#btn-search-category").click(e=>{
    	e.preventDefault();
        if($("#search-option-category").val()=="utilisateur"){
            allHide();

            
            getData("/listeUsers",token,afficherUtilisateursDropdown,onError);
            getData("/listeUsers",token,afficherUtilisateurs,onError);
        }
        if($("#search-option-category").val()=="devis"){
                allHide();
                getData("/listeUsers",token,onGetClientDevis,onError);
                getData("/introduireServlet",token,onGetAmenagementDevis,onError);
        	getData("/listeDevis",token,afficherDevis,onError);
        }
        if($("#search-option-category").val()=="client"){
                allHide();
            getData("/listeClients",token,afficherClientsDropdown,onError);
            getData("/listeClients",token,afficherClients,onError);
        }
        
        if($("#search-option-category").val()=="date"){
        	allHide();
        	getData("/listeDevisClient",token,afficherDevisClient,onError);
        }
        
		if($("#search-option-category").val()=="montant"){
			allHide();
        	getData("/listeDevisClient",token,afficherDevisClient,onError);
		}
		
		if($("#search-option-category").val()=="type_amenagement"){
			allHide();
        	getData("/listeDevisClient",token,afficherDevisClient,onError);
		}
    });

   
    $("#btn-devis-repousserDate").click(e=>{
        let data={
                "date":$("#dateDebutTravaux").val(),
                "idDevis":$("#btn-devis-repousserDate").attr("idDevis"),
                "etat":$("#btn-devis-repousserDate").attr("etat")
        };
        console.log("id devis-->"+$("#btn-devis-repousserDate").attr("idDevis"));
        console.log(data);
        putData("/detailsDevis",token,data,onPutRepousserDate,onError);

    });

    $(".myInput").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $(".dropdown-menu li").filter(function() {
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
    
    $(".mesPhotos").click(e=>{
    	let data=user;
    	postData("/mesPhotos", data, token, afficherPhotos, onError);
    });
    
});
function onPutRepousserDate(response){
        alert("ok");
}


function allHide(){
        $("#voir-devis-client").hide();
        $("#3-category").hide();
        $("#login-form").hide();
        $("#wrong_passwd").hide();
        $("#carousel").hide();
        $("#introductionQuoteForm").hide();
        $("#Register-confirmation").hide();
        $("#voir-utilisateurs").hide();
        $("#voir-clients").hide();
        $("#voir-devis").hide();
    	$("#voir-photos-client").hide();
    	$("#ajouterPhoto").hide();
    	$("#choisirPhotoPreferee").hide();

        
}

//Home page non-connecté
function viewHomePage(){
        $(".connexion").show();
        $("#Register-confirmation").hide();
        $("#login-form").hide();

        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();
        $("#carousel").show();
        $(".Register-confirmation-link").hide();
        $(".introductionQuote").hide();
        $("#introductionQuoteForm").hide();
        $("#list-confirmation-link").hide(); 
        $("#voir-utilisateurs").hide();
        $("#voir-devis").hide();
        $("#voir-devis-client").hide();
        $("#voir-clients").hide();
        $("#voir-details-devis").hide();
        $("#mesDevis").hide();
        $("#mesPhotos").hide();
        $("#search-homepage").hide();
    	$("#voir-photos-client").hide();
    	$("#ajouterPhoto").hide();
    	$("#choisirPhotoPreferee").hide();



}
function authentificationToken(token){
        console.log("test"+token)
        if(token){
                if(user==undefined){
                      getData("/login",token,onPostLoginToken,onError);
                }
                else{
                        viewAuthentification(user);
                }
        }
        else{
                viewHomePage();
        }
}
//identification via le token
function onPostLoginToken(response){
        if(response.success=="true"){

                user=response.userData;
                $("#success-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#success-notification").text(response.message);
                viewAuthentification(user);
        }
        else{
                console.log(response.message);
                $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#error-notification").text(response.message);
                localStorage.removeItem("token");
                token=undefined;
        }
    }
    
    //Authentificaiton réussis
    function onPostLogin(response){
        if(response.success==="true"){
                $("#success-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#success-notification").text(response.message);
                console.log(response)
                token=response.token;
                localStorage.setItem("token",token);
                user=response.userData;
                console.log("user"+user.idUser);
                viewAuthentification(user);
        }else{ 
                console.log(response.message);
                $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#error-notification").text(response.message);
                
    
                
        }
    }


function mesDevis(){
	$("#voir-devis-client").show();	
	let data={};
	console.log("user = " + JSON.stringify(user));
	data = user;
	postData("/listeDevisClient",data,token,afficherDevisClient,onError);
}

export{token,allHide};

