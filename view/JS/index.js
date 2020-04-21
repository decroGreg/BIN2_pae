"use-strict";
import {postData,getData,deleteData,putData,creatHTMLFromString} from "./util.js" ;
import{viewAuthentification,viewLogin,onPostRegister} from "./connexion.js";
import{onGetRegisterConfirmation,viewRegisterConfirmation} from "./confirmerInscription.js";
import{onGetAmenagements,onGetClientQuoteForm,filterSearchClient,viewIntroductionQuote} from "./introduireDevis.js";
import{afficherClients, viewListeClients} from "./afficherClients.js";
import{afficherDevis, afficherDevisClient, viewListeDevis} from "./afficherDevis.js";
import{afficherUtilisateurs, viewListeUtilisateurs} from "./afficherUtilisateurs.js";
import{afficherDetailsDevis, changerEtat, changerValeurBouton} from "./detailsDevis.js";


const OUVRIER="O";
const CLIENT="C";
let token=undefined;
let user;
$('#navigation_bar').hide();
$("#success-notification").hide();
$("#error-notification").hide();
var photo={};


function encodeImagetoBase64(element) {
        var file = element.files[0];
        console.log(element);
        console.log(file);
        var reader = new FileReader();

        reader.onloadend = function() {
                photo="{"+reader.result+"}";
          console.log(reader.result);
          $("img").attr("src",reader.result);
          
        }
        reader.readAsDataURL(file);        
}
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
            console.log($("#login-email").val());
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
    $(".introductionQuote").click(e=>{
        viewIntroductionQuote();
        getData("/introduireServlet",token,onGetAmenagements,onError);
        getData("/listeUsers",token,onGetClientQuoteForm,onError);
    });

    /*
    filtre dropdow lié client
    */
    $("#Quote-Form-Client-Search").on("keyup", function() {
                filterSearchClient(this);
      });

      $("#Quote-Form-image").change(e=>{
              encodeImagetoBase64(e.target);
      })

      //envoyé donné Introduction devis
    $("#bnt-IntroductionQuote").click(e=>{
            console.log($("#imageQuote").attr("src"));
        
        
        if($("#Quote-Form-firstName").val()||$("#Quote-Form-email").val()){//changer ne prends pas tous les cas en considération
                var dataUser={
                        firstname:$("#Quote-Form-firstName").val(),
                        lastname:$("#Quote-Form-name").val(),
                        street:$("#Quote-Form-street").val(),
                        number:$("#Quote-Form-number").val(),
                        boite:$("#Quote-Form-boite").val(),
                        postalCode:$("#Quote-Form-postalCode").val(),
                        city:$("#Quote-Form-city").val(),
                        mail:$("#Quote-Form-email").val(),
                        phone:$("#Quote-Form-phoneNumber").val()
                };
                if(!checkInput(dataUser,"veuillez remplir tous les champs pour le nouveau client")) {
                        console.log("ne passe pas la methode");
                        return;
                }
                console.log(dataUser);
        
        }
        var type=[];
        var amenagementList=document.getElementsByClassName("form-check");
        for(var i=0;i<amenagementList.length;i++){
                var element=amenagementList[i].firstChild;
                if(element.checked){
                        type[i]=element.id;    
                }
        }
        var dataQuote={
                "client":$("#Quote-Form-Client-items").val(),
                "date":$("#Quote-Form-quoteDate").val(),
                "price":$("#Quote-Form-price").val(),
                "duration":$("#Quote-Form-duration").val(),
                "image":$("#imageQuote").attr("src")
                

        };
        if(!checkInput(dataQuote,"veuillez remplir tous les champs du devis")) return;//à voir si image peut être null;
        if(type.length==0){
                $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#error-notification").text("veillez introduire un type d'aménagement");
                return;
        }
        var data={
                "dataUser":dataUser,
                "dataQuote":dataQuote,
                "type":type
        }
        console.log(data.dataQuote.type);
        postData("/introduireServlet",data,token,onPostIntroductionQuote,onError);
        
    });

    $(".Register-confirmation-link").click(e=>{
            viewRegisterConfirmation();
            
            getData("/confirmation",token,onGetRegisterConfirmation,onError);
        
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
    });
    
    $("#btn-search-category").click(e=>{
    	e.preventDefault();
        if($("#search-option-category").val()=="utilisateur"){
        	viewListeUtilisateurs();
            //$("#voir-utilisateurs").show();
            getData("/listeUsers",token,afficherUtilisateurs,onError);
        }
        if($("#search-option-category").val()=="devis"){
        	viewListeDevis();
        	//$("#voir-devis").show();
        	getData("/listeDevis",token,afficherDevis,onError);
        }
        if($("#search-option-category").val()=="client"){
        	viewListeClients();
        	//$("#voir-clients").show();
            getData("/listeClients",token,afficherClients,onError);
        }
        
        if($("#search-option-category").val()=="date"){
        	allHide();
        	$("#voir-devis-client").show();
        	getData("/listeDevisClient",token,afficherDevisClient,onError);
        }
        
		if($("#search-option-category").val()=="montant"){
			allHide();
        	$("#voir-devis-client").show();
        	getData("/listeDevisClient",token,afficherDevisClient,onError);
		}
		
		if($("#search-option-category").val()=="type_amenagement"){
			allHide();
        	$("#voir-devis-client").show();
        	getData("/listeDevisClient",token,afficherDevisClient,onError);
		}
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






function onError(response){
        console.log("Erreur");
        $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
        $("#error-notification").text(response.message);
}

function mesDevis(){
	$("#voir-devis-client").show();	
	let data={};
	console.log("user = " + JSON.stringify(user));
	data = user;
	postData("/listeDevisClient",data,token,afficherDevisClient,onError);
}

/////////////////////////////////////////////////////////////////////////////////////////////test////////////////////////

