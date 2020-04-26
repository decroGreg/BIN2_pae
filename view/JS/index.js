"use-strict";
import {postData,getData,onError, putData} from "./util.js" ;
import{viewAuthentification,viewLogin,onPostRegister} from "./connexion.js"
import{afficherClients} from "./afficherClients.js";
import{afficherDevis, afficherDevisClient,onGetAmenagementDevis,onGetAmenagementDevisClient,searchDevis} from "./afficherDevis.js";
import{afficherUtilisateurs} from "./afficherUtilisateurs.js";
import{afficherDetailsDevis, changerEtat, changerValeurBouton} from "./detailsDevis.js";

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
        $(".connexion").click(function (e) {

               viewLogin();
        });
        $("#imageMenu").click(e=>{
                viewHomePage();
                viewAuthentification(user);
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
 
    $("#homePage").click(viewHomePage);
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
    
    $("#mesDevis").click(e=>{
    	e.preventDefault();
    	allHide();
            mesDevis();
            getData("/introduireServlet",token,onGetAmenagementDevisClient,onError);
    });
    
    $("#btn-search-category").click(e=>{
    	e.preventDefault();
        if($("#search-option-category").val()=="utilisateur"){
            allHide();
            getData("/listeUsers",token,afficherUtilisateurs,onError);
        }
        if($("#search-option-category").val()=="devis"){
                allHide();
                getData("/introduireServlet",token,onGetAmenagementDevis,onError);
        	getData("/listeDevis",token,afficherDevis,onError);
        }
        if($("#search-option-category").val()=="client"){
        	allHide();
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

    $("#btn-search-devis-client").click(e=>{
        searchDevis(document.getElementById("amenagements-devis-client"),user.idUser);
    });
    $("#btn-search-devis").click(e=>{
        searchDevis(document.getElementById("amenagements-devis"));
    });

    $("#btn-devis-repousserDate").click(e=>{
        let data={
                "date":$("#dateDebutTravaux").val(),
                "idDevis":$("#btn-devis-repousserDate").attr("idDevis")
        };
        console.log("id devis-->"+$("#btn-devis-repousserDate").attr("idDevis"));
        //putData("",token,data,,onError); si reussis alors changer la date detailsDevis

    });

    $(".myInput").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $(".dropdown-menu li").filter(function() {
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
        });
    
});


function allHide(){
        $("#login").hide();
        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();
        $("#test1").hide();
        $("#carousel").hide();
        $("#introductionQuoteForm").hide();
        
        $("#Register-confirmation").hide();
        $("#success-notification").hide();
        $("#error-notification").hide();
}

//Home page non-connecté
function viewHomePage(){
      

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
        
        //$("#search-homepage").hide();
    	$("#voir-details-devis-DDI").hide();
    	$("#voir-details-devis-DC").hide();
    	$("#choisirPhotoPreferee").hide();
    	$("#ajouterPhoto").hide();


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

