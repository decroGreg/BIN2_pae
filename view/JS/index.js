"use-strict";
import {postData,getData,onError, putData} from "./util.js" ;
import{viewAuthentification,viewLogin,onPostRegister,OUVRIER} from "./connexion.js"
import{afficherClients,afficherClientsDropdown} from "./afficherClients.js";
import{afficherDevis, afficherDevisClient,onGetAmenagementDevis,onGetAmenagementDevisClient,onGetClientDevis} from "./afficherDevis.js";
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
	$(".mesPhotos").click(e=>{
        e.preventDefault();
    	console.log("Mes photos");
    	let data=user;
    	postData("/mesPhotos", data, token, afficherPhotos, onError);
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

export{token,allHide,user};

