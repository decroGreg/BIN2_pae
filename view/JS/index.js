"use-strict";
import {postData,getData,onError, putData} from "./util.js" ;
import{viewAuthentification,viewLogin,onPostRegister,OUVRIER} from "./connexion.js"
//import{afficherClients,afficherClientsDropdown} from "./afficherClients.js";
//import{afficherDevis, afficherDevisClient,onGetAmenagementDevis,onGetAmenagementDevisClient,onGetClientDevis} from "./afficherDevis.js";
//import{afficherUtilisateurs,afficherUtilisateursDropdown} from "./afficherUtilisateurs.js";
//import{afficherDetailsDevis, changerEtat, changerValeurBouton} from "./detailsDevis.js";
import{remplirListeTypesAmenagement} from "./selectionnerTypeAmenagement.js";


let token=undefined;
let user;
$('#navigation_bar').hide();
$("#success-notification").hide();
$("#error-notification").hide();


$(document).ready(e=>{
        token=localStorage.getItem("token");
        authentificationToken(token);
        getData("/voirTypesAmenagement", token, remplirListeTypesAmenagement, onError);
        
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
 
        $("#btn-deconnexion").click(e=>{
                e.preventDefault()
                token=undefined;
                localStorage.removeItem("token");
                console.log(token);
                console.log(localStorage.getItem("token"));
                $("#userName").html("");
                viewHomePage();
        });
    


    
});

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
        allHide();
        $(".mesPhotos").hide();
        $(".mesDevis").hide();
        $(".introductionQuote").hide();
        $(".Register-confirmation-link").hide();

        $(".connexion").show();
        $("#Register-confirmation").hide();
        $("#login-form").hide();

        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();
        $("#carousel").show();
        $("#list-confirmation-link").hide(); 
        $("#voir-details-devis").hide();
        $("#search-homepage").hide();

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
function setTokenUser(varToken,varUser){
        token=varToken;
        user=varUser;
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
    
    

export{token,allHide,user,setTokenUser};

