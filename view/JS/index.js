"use-strict";
import {postData,getData,deleteData,putData} from "./util.js" ;
const OUVRIER="O";
const CLIENT="C";
let token=undefined;
let user;
$('#navigation_bar').hide();
$("#success-notification").hide();
$("#error-notification").hide();


function encodeImagetoBase64(element) {
        var file = element.files[0];

        var reader = new FileReader();

        reader.onloadend = function() {
                
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
        $("#connexion").click(function (e) {
               
               viewLogin();
        });
        $("#imageMenu").click(e=>{
                viewHomePage();
                viewAuthentification();
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
    $("#introductionQuote").click(e=>{
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
function onPostIntroductionQuote(response){
        if(response.success=="true"){
                $("#success-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#success-notification").text(response.message);
        }else{
                $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#error-notification").text(response.message);
        }
        viewIntroductionQuote();
}
function filterSearchClient(element){
        var value = $(element).val().toLowerCase();
        $(".dropdown-menu li").filter(function() {
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
}

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
function viewIntroductionQuote(){
        $("#login").hide();
        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();
        $("#test1").hide();
        $("#carousel").hide();
        $("#Register-confirmation").hide();
        $("#introductionQuoteForm").show();
       
}
function viewRegisterConfirmation(){
        $("#login").hide();
        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();
        $("#test1").hide();
        $("#carousel").hide();
        $("#Register-confirmation").show();
        $("#introductionQuoteForm").hide();
       
}

function viewLogin(){
        $("#login-form").show();
        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();
        $("#carousel").hide();

}
//Home page non-connecté
function viewHomePage(){
        $("#test1").show();
        $("#login-form").hide();
        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();
        $("#carousel").show();
        $("#Register-confirmation").hide();
        $("#introductionQuote").hide();
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

//vue après authentification
function viewAuthentification(){
		$("#search-homepage").show();
		$("#search-devis-date-link").show();
        $("#search-devis-montant-link").show();
        $("#search-devis-amenagement-link").show();
        $("#search-client-link").hide();
        $("#search-utilisateur-link").hide();
        $("#search-amenagement-link").hide();
        $("#mesDevis").show();
        
        if(user &&user.statut===OUVRIER){
                $("#list-confirmation-link").show();
                $("#introductionQuote").show(); 
                $("#search-client-link").show();
                $("#search-utilisateur-link").show();
                $("#search-amenagement-link").show();
                $("#search-devis-link").show();
                $("#search-devis-date-link").hide();
                $("#search-devis-montant-link").hide();
                $("#search-devis-amenagement-link").hide();
                $("#mesDevis").show();

        }
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
        $("#voir-details-devis-DDI").hide();
    	$("#voir-details-devis-DC").hide();
         
}

function authentificationToken(token){
        console.log("test"+token)
        if(token){
                if(user==undefined){
                      //  getData("/login",token,onPostLogin,onError);
                      
                }
                viewAuthentification();
        }
        else{
                viewHomePage();
        }
}
//Authentificaiton réussis
function onPostLogin(response){
        if(response.success=="true"){
                $("#success-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#success-notification").text(response.message);
                console.log( $("#success-notification").text());
                token=response.token;
                localStorage.setItem("token",token);

                console.log("data: "+response.userData.prenom);
                user=response.userData;
                console.log("user"+user.idUser);
                
                console.log(localStorage.getItem("token"));
                viewAuthentification();
        }else{
                console.log(response.message);
                $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#error-notification").text(response.message);
                

                
        }
}
function onGetClientQuoteForm(response){
        response.usersData.forEach(element => {
                var li=document.createElement("li");
                var a=document.createElement("a");
                a.href="#";
                a.val=element.idUser;
                a.innerText=element.prenom+" "+element.nom;
                a.addEventListener("click",function(e){
                        
                        $("#Quote-Form-Client-items").val(e.target.val);
                        $("#Quote-Form-Client-dropdown").text(e.target.innerText);
                        console.log($("#Quote-Form-Client-items").val());

                });
                li.appendChild(a);
                $("#Quote-Form-Client-items").append(li);
        });
}

function onGetAmenagements(response){
     
     console.log(response.typeAmenagements);
        response.typeAmenagements.forEach(element => {//changer les donnees hanger le i par l'id
                console.log(element.id);
                var checkbox=creatHTMLFromString('<div class="form-check col-sm-3 col-form-label" >'
                +'<input  type="checkbox" id="'+element.id+'" value="option1">'
                +'<label for="#'+element.id+'">'+element.description+'</label>'
                +'</div>');
                $("#Quote-Form-layoutType").append(checkbox);
                
        });
}
var i=0;//permet d'afficher la meme liste des clients pour des bouttonsdifférentss
//affiche les demandes d'inscription dans un tableau
function onGetRegisterConfirmation(response){
        $("#Register-confirmation-body").html("");
        
        response.usersData.forEach(element => {
                var tr=document.createElement("tr");
                var prenom=document.createElement("td");
                prenom.innerHTML=element.prenom;
                prenom.value=element.idUser;
                console.log(element.idUser);
                prenom.setAttribute("valueof","id");
                var nom=document.createElement("td");
                nom.innerHTML=element.nom;

                nom.setAttribute("valueof","lastname");
                var btnStatus=document.createElement("td");
                btnStatus.setAttribute("valueof","status");
                var btnConfirmation=document.createElement("td");
                tr.appendChild(prenom);
                tr.appendChild(nom);
                //creation du boutton status
                
                var btnStatusEvent=creatHTMLFromString('<td><div class="btn-group">'
                +'<button type="button" id="btn-status" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
                +'status'
                +'</button>'
                +'<div class="dropdown-menu">'
                +'<a class="dropdown-item" value="c" href="#">Client</a>'
                +'<a class="dropdown-item" value="e" href="#">Ouvrier</a>'
                +'</div>'
                +'</div></td>');
                btnStatus.appendChild(btnStatusEvent);
                btnStatusEvent.addEventListener("click",onClickStatusItem);
               
                tr.appendChild(btnStatus);

                //lié un client à l'utilisateurs
                var tdBtnClientLink=document.createElement("td");
                tdBtnClientLink.setAttribute("valueof","client");
                tdBtnClientLink.id="ClientLink"+i;
                var btnClientLink=creatHTMLFromString('<div class="form-group col-md-3">'
                +'<div class="dropdown-Client">'
                + '<button class="btn btn-secondary dropdown-toggle" id="RegisterConfirmation-Form-Client-dropdown'+i+'" type="button" data-toggle="dropdown">lié à l\'utilisateur<span class="caret"></span></button>'
                + '<ul class="dropdown-menu" id="RegisterConfirmation-Form-Client-items'+i+'">'
                +  '<input class="form-control" id="RegisterConfirmation-Form-Client-Search'+i+'" type="text" placeholder="Search..">'
                +'</ul></div></div>');
                tdBtnClientLink.appendChild(btnClientLink);
                tr.appendChild(tdBtnClientLink);

                getData("/listeClients",token,onGetClientRegisterConfirmationForm,onError);
                
                //GetClient

                //creation du boutton confirmer
                var btnConfirmationEvent=creatHTMLFromString('<td><button id="bnt-Register-confirmation" class="btn btn-info"> Confirmer</button></td>');
                btnConfirmation.appendChild(btnConfirmationEvent);
                btnConfirmationEvent.addEventListener("click",onClickRegisterConfirmation);
                tr.appendChild(btnConfirmation);
                $("#Register-confirmation-body").append(tr);
                
                i++;
        });
        i=0;

}
function onGetClientRegisterConfirmationForm(response){
        response.clientsData.forEach(element => {
                var li=document.createElement("li");
                var a=document.createElement("a");
                a.href="#";
                a.setAttribute("valueofI",i);
                a.val=element.idClient;
                a.innerText=element.prenom+" "+element.nom;
                a.addEventListener("click",function(e){
                        
                        $("#ClientLink"+e.target.getAttribute("valueofI")).val(e.target.val);
                        $("#RegisterConfirmation-Form-Client-dropdown"+e.target.getAttribute("valueofI")).text(e.target.innerText);
                        
                        console.log($("#ClientLink"+e.target.getAttribute("valueofI")));

                });
                li.appendChild(a);
                $("#RegisterConfirmation-Form-Client-items"+i).append(li);
        });
        i++;

}
function onClickRegisterConfirmation(element){
        var btn=element.target;
        var data={};
        btn.parentNode.parentNode.childNodes.forEach(e => {
                console.log(e.getAttribute("valueof")+":"+e.value);
                console.log(e);
                if(e.getAttribute("valueof")!=null)
                data[e.getAttribute("valueof")]=e.value+"";
        });
        if(data.status==="undefined"){
                $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#error-notification").text("veuillez choisir un roles");
                return;

        }
        
        

        postData("/confirmation",data,token,onPostRegisterConfirmation,onError);
}
function onPostRegisterConfirmation(response){
        if(response.success=="true"){
                $("#success-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#success-notification").text(response.message);
                getData("/confirmation",token,onGetRegisterConfirmation,onError);

        }else{
                $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                $("#error-notification").text(response.message);
        }
}

function onClickStatusItem(element){
        
        var btn=element.target;
        if(btn.tagName=="A"){//vérifie si c'est un element <a>
                console.log($(btn).attr("value"));
                btn.parentNode.parentNode.parentNode.value=$(btn).attr("value");
                btn.parentNode.parentNode.firstChild.innerHTML=btn.innerHTML;
        }
}

//methode trouver sur: https://stackoverflow.com/questions/494143/creating-a-new-dom-element-from-an-html-string-using-built-in-dom-methods-or-pro
function creatHTMLFromString(htmlString){
        var div = document.createElement('div');
        div.innerHTML = htmlString.trim();

        // Change this to div.childNodes to support multiple top-level nodes
        return div.firstChild; 
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
		  nouvelEtat = "DDI";
		  break;
	  case 'DDI':
		  nouvelEtat = "DC";
		  break;
	  case 'ANP':
		  nouvelEtat = "DC";
		  break;
	  case 'DC':
		  nouvelEtat = "EC";
		  break;
	  case 'A':
		  nouvelEtat = "EC";
		  break;
	  case 'EC':
		  nouvelEtat = "FM";
		  break;
	  case 'FM':
		  nouvelEtat = "T";
		  break;
	  case 'T':
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