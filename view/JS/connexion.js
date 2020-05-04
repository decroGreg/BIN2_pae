import { allHide ,setTokenUser,token} from "./index.js";
import {postData,checkInput,onError} from "./util.js";
const OUVRIER="O";
const CLIENT="C";


$(document).ready(function(){
    $(".connexion").click(function (e) {
        viewLogin();
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
});


//Authentificaiton réussis
function onPostLogin(response){
    if(response.success==="true"){
            $("#success-notification").fadeIn('slow').delay(1000).fadeOut('slow');
            $("#success-notification").text(response.message);
            console.log(response)
            var token=response.token;
            localStorage.setItem("token",token);
            var user=response.userData;
            setTokenUser(token,user);
            console.log("user"+user.idUser);
            viewAuthentification(user);
    }else{ 
            console.log(response.message);
            $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
            $("#error-notification").text(response.message);
            

            
    }
}



//vue après authentification
function viewAuthentification(user){
    allHide();
    $("#userName").text("Bonjour! "+user.nom+" "+user.prenom);
    $(".connexion").hide();
    $("#search-homepage").show();
    $("#search-devis-link").hide();
    $("#search-client-link").hide();
    $("#search-utilisateur-link").hide();
    $("#search-amenagement-link").hide();
    $("#mesDevis").show();
    $("#carousel").show();
    $("#3-category").show();
    
    if(user &&user.statut===OUVRIER){
            $(".Register-confirmation-link").show();
            $(".introductionQuote").show(); 
            $("#search-client-link").show();
            $("#search-utilisateur-link").show();
            $("#search-devis-link").show();
            $("#mesDevis").show();

    }
    $(".mesPhotos").show();
    $(".mesDevis").show();
    $("#introductionQuoteForm").hide();
    $("#connexion").hide();
    $('#navigation_bar').hide();
    $("#login-form").hide();
    $("#btn-deconnexion").show();
    $("#Register-confirmation").hide();
    $("#voir-utilisateurs").hide();
    $("#voir-devis").hide();
    $("#voir-devis-client").hide();
    $("#voir-details-devis").hide();
    $("#voir-clients").hide();
    $("#voir-photos-client").hide();
     
}


function viewLogin(){
    allHide();
    console.log($("#login-form").target);
    $("#login-form").show();
    $("#btn-deconnexion").hide();
    $("#wrong_passwd").hide();
    $("#carousel").hide();

}
function onPostRegister(response){
    if(response.success=="true"){
            $("#success-notification").fadeIn('slow').delay(1000).fadeOut('slow');
            $("#success-notification").text(response.message);
    }else{ 
            console.log(response.message);
            $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
            $("#error-notification").text(response.message);
    }
}

export{viewAuthentification,viewLogin,onPostRegister,OUVRIER};