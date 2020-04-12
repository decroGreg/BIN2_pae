"use-strict";
import {postData,getData,deleteData,putData,creatHTMLFromString} from "./util.js" ;
import{viewAuthentification,viewLogin,onPostRegister} from "./connexion.js"
import{onGetRegisterConfirmation,viewRegisterConfirmation} from "./confirmerInscription.js";
import{onGetAmenagements,onGetClientQuoteForm,filterSearchClient,viewIntroductionQuote} from "./introduireDevis.js"

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
            allHide();
            $("#voir-utilisateurs").show();
            getData("/listeUsers",token,afficherUtilisateurs,onError);
        }
        if($("#search-option-category").val()=="devis"){
        	allHide();
        	$("#voir-devis").show();
        	getData("/listeDevis",token,afficherDevis,onError);
        }
        if($("#search-option-category").val()=="client"){
        	allHide();
        	$("#voir-clients").show();
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













/////////////////////////////////////////////////////////////////////////////////////////////test////////////////////////


function afficherUtilisateurs(response){
	Object.keys(response.usersData).forEach(data => {
	    var html = "<tr>";
	    html+="<td>" 
	    	+ response.usersData[data].pseudo + "</td>\n<td>" 
	    	+ response.usersData[data].nom + "</td>\n<td>" 
	    	+ response.usersData[data].prenom + "</td>\n<td>" 
	    	+ response.usersData[data].ville + "</td>\n<td>" 
	    	+ response.usersData[data].email + "</td>\n<td>" 
	    	+ response.usersData[data].statut + "</td></tr>";
	    $("#voir-utilisateurs tbody").append(html);
	});
}

function afficherDevis(response){
	Object.keys(response.devisData).forEach(data => {
	    var html = "<tr>";
	    html+="<td><a href=\"#\" class=\"reference-devis\">"
	    	+ response.devisData[data].idDevis + "</a></td>\n<td>" 
	    	+ response.devisData[data].idClient + "</td>\n<td>" 
	    	+ response.devisData[data].date.substring(0,10) + "</td>\n<td>" 
	    	+ response.devisData[data].montant + "€</td>\n<td>" 
	    	+ response.devisData[data].dureeTravaux + "</td>\n<td>" 
	    	+ response.devisData[data].photoPreferee + "</td>\n<td>" 
	    	+ response.devisData[data].etat + "</td></tr>";	    
	    $("#voir-devis tbody").append(html);
	});
	$("a.reference-devis").click(e=>{
    	let data={};
        data.idDevis=$(e.target).text();
        console.log("Reference devis : " + data.idDevis);
        postData("/detailsDevis",data,token, afficherDetailsDevis, onError);
        //getData("/detailsDevis", token, afficherDetailDevis, onError);
    });
}

function afficherDevisClient(response){
	Object.keys(response.devisData).forEach(data => {
	    var html = "<tr>";
	    html+="<td><a href='#' class='reference-devis'>"
	    	+ response.devisData[data].idDevis + "</a></td>\n<td>"
	    	+ response.devisData[data].date.substring(0,10) + "</td>\n<td>" 
	    	+ response.devisData[data].montant + "€</td>\n<td>" 
	    	+ response.devisData[data].dureeTravaux + "</td>\n<td>" 
	    	+ response.devisData[data].photoPreferee + "</td>\n<td>" 
	    	+ response.devisData[data].etat + "</td></tr>";	    
	    $("#voir-devis-client tbody").append(html);
	});
}

function afficherClients(response){
	Object.keys(response.clientsData).forEach(data => {
		console.log(response.clientsData[data].prenom);
	    var html = "<tr>";
	    html+="<td>"
	    	+ response.clientsData[data].prenom + "</td>\n<td>" 
	    	+ response.clientsData[data].nom + "</td>\n<td>" 
	    	+ response.clientsData[data].codePostal + "</td>\n<td>" 
	    	+ response.clientsData[data].ville + "</td>\n<td>"
	    	+ response.clientsData[data].email + "</td>\n<td>"
	    	+ response.clientsData[data].telephone+ "</td></tr>";	    
	    $("#voir-clients tbody").append(html);
	});
}

function afficherDetailsDevis(response){
	console.log(JSON.stringify(response.devisData));
        allHide();
    if(response.devisData.etat === "FD"){
    	viewDevisDDI(response);
    }
    else{
    	$("#voir-details-devis").show();
    	$("#voir-details-devis #dateDevis").attr("value", response.devisData.date.substring(0,10));
    	$("#voir-details-devis #montantDevis").attr("value", response.devisData.montant);
    	$("#voir-details-devis #etatDevis").attr("value", response.devisData.etat);
    	$("#voir-details-devis #typeAmenagementDevis").attr("value", response.devisData.typeAmenagement);
    	$("#voir-details-devis #dureeTravauxDevis").attr("value", response.devisData.dureeTravaux);
    	$("#voir-details-devis #btn-devis").attr("value", changerValeurBouton(response.devisData.etat));
    	$("#voir-details-devis #btn-devis").click(e=>{
    		var nouvelEtat = changerEtat(response.devisData.etat);	
    		let data={};
    		data.idDevis = response.devisData.idDevis;
    		data.etatDevis = nouvelEtat;
    		data.dateDebutTravaux = $("#voir-details-devis #dateDebutTravaux").val();
    		console.log($("#voir-details-devis #dateDebutTravaux").val());
    		putData("/detailsDevis", token, data, viewDevisDC, onError);
    	});
    }
}

function changerValeurBouton(etat){
	var valeurBouton;
	switch (etat) {
	  case 'I':
		  valeurBouton = "Confirmer commande";
		  break;
	  case 'DDI':
		  valeurBouton = "Confirmer date";
		  break;
	  case 'ANP':
		  valeurBouton = "Repousser date début";
		  break;
	  case 'DC':
		  valeurBouton = "Annuler la commande";
		  break;
	  case 'A':
		  valeurBouton = "";
		  break;
	  case 'EC':
		  valeurBouton = "Envoyer facture de milieu de chantier";
		  break;
	  case 'FM':
		  valeurBouton = "";
		  break;
	  case 'T':
		  valeurBouton = "Envoyer facture finale";
		  break;
	  case 'FF':
		  valeurBouton = "Rendre visible";
		  break;
	  default:
		  break;
	}
	return valeurBouton;
}

function changerEtat(etat){
	var nouvelEtat;
	switch (etat) {
	  case 'I':
		  nouvelEtat = "FD";
		  break;
	  case 'FD':
		  nouvelEtat = "DC";
		  break;
	  case 'DC':
		  nouvelEtat = "A";
		  break;
	  case 'A':
		  nouvelEtat = "FM";
		  break;
	  case 'FM':
		  nouvelEtat = "FF";
		  break;
	  case 'FF':
		  nouvelEtat = "V";
		  break;
	  default:
		  break;
	}
	
	return nouvelEtat;

}

function mesDevis(){
	$("#voir-devis-client").show();	
	let data={};
	console.log("user = " + JSON.stringify(user));
	data = user;
	postData("/listeDevisClient",data,token,afficherDevisClient,onError);
}

function viewDevisDC(response){
	allHide();
	$("#voir-details-devis-DC").show();
	$("#voir-details-devis-DDI").hide();
	console.log("ICI");
	$("#voir-details-devis-DC #dateDevis").attr("value", response.devisData.date.substring(0,10));
	$("#voir-details-devis-DC #montantDevis").attr("value", response.devisData.montant);
	$("#voir-details-devis-DC #etatDevis").attr("value", response.devisData.etat);
	$("#voir-details-devis-DC #typeAmenagementDevis").attr("value", response.devisData.typeAmenagement);
	$("#voir-details-devis-DC #dureeTravauxDevis").attr("value", response.devisData.dureeTravaux);
	$("#voir-details-devis-DC #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
	$("#voir-details-devis-DC #btn-devis").attr("value", changerValeurBouton(response.devisData.etat));
}

function viewDevisDDI(response){
	allHide();
	console.log("OK");
	$("#voir-details-devis-DDI").show();
	$("#voir-details-devis-DDI #dateDevis").attr("value", response.devisData.date.substring(0,10));
	$("#voir-details-devis-DDI #montantDevis").attr("value", response.devisData.montant);
	$("#voir-details-devis-DDI #etatDevis").attr("value", response.devisData.etat);
	$("#voir-details-devis-DDI #typeAmenagementDevis").attr("value", response.devisData.typeAmenagement);
	$("#voir-details-devis-DDI #dureeTravauxDevis").attr("value", response.devisData.dureeTravaux);
	$("#voir-details-devis-DDI #dateDebutTravaux").attr("value", response.devisData.dateDebutTravaux.substring(0,10));
	$("#voir-details-devis-DDI #btn-devis").attr("value", changerValeurBouton(response.devisData.etat));
	$("#voir-details-devis-DDI #btn-devis").click(e=>{
		var nouvelEtat = changerEtat(response.devisData.etat);	
		let data={};
		data.idDevis = response.devisData.idDevis;
		data.etatDevis = nouvelEtat;
		data.dateDebutTravaux = $("#voir-details-devis-DDI #dateDebutTravaux").val();
		putData("/detailsDevis", token, data);
		response.devisData.etat = data.etatDevis;
		response.devisData.dateDebutTravaux = data.dateDebutTravaux;
		viewDevisDC(response);
	});
}