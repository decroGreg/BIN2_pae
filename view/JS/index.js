"use-strict";
import {postData,getData,deleteData,putData} from "./util.js" ;
const OUVRIER="o";
const CLIENT="client";
let token=undefined;
let user;
$('#navigation_bar').hide();


function encodeImagetoBase64(element) {
        
        var file = element.files[0];

        var reader = new FileReader();

        reader.onloadend = function() {
                
          $("img").attr("src",reader.result);
          
        }
        reader.readAsDataURL(file);        
}


let tempoUsersConf=[
        {
                "prenom":"Grégoire",
                "nom":"deC"
        },
        {
                "prenom":"Gregory",
                "nom":"delacroix"
        },
        {
                "prenom":"Gggggggggg",
                "nom":"deCaaaaaaaaaa"
        },
];

let tempUsers=[{"dateInscription":"2020-03-26 11:46:37.95006","email":"mrbrg@live.fr","idUser":0,"motDePasse":"mdp","nom":"B","prenom":"M","pseudo":"mrbrg","statut":"c","ville":"Bruxdells"},
				{"dateInscription":"2020-03-26 11:46:37.95006","email":"mrbrdddddddg@live.fr","idUser":1,"motDePasse":"fgr","nom":"Bdd","prenom":"Mdef","pseudo":"jbe","statut":"e","ville":"Brudddxdells"}];

let tempDevis=[{"date":null,"dureeTravaux":"2 mois","etat":"DDI","idClient":0,"idDevis":0,"idPhotoPreferee":0,"montant":3000.0},
				{"date":null,"dureeTravaux":"1 mois","etat":"A","idClient":0,"idDevis":0,"idPhotoPreferee":0,"montant":400.0}];


$(document).ready(e=>{
       
        token=localStorage.getItem("token");
        authentificationToken(token);
        $("#connexion").click(function (e) {
               
               viewLogin();
        });
                
        $(window).bind('scroll', function() {

        var navHeight = $( window ).height() - 70;

                if ($(window).scrollTop() <= $("#carousel").height()/2) {
                $('#navigation_bar').hide();
                }
                else {
                console.log("test");
                $('#navigation_bar').show();
                }
                });
    //$("#dateTimePicker").datetimepicker();
 
    
    $("#btn-connexion").click(e=>{
            console.log($("#login-email").val());
            let data={};
            data.mail=$("#login-email").val();
            data.mdp=$("#login-pwd").val();
            postData("/login",data,token,onPostLogin,onError);
            
    });
    $("#btn-register").click(e=>{
        console.log($("#Register-email").val());
        console.log($("#Register-city").val());
        let data={};
        data.firstname=$("#Register-firstname").val();
        data.lastname=$("#Register-lastname").val();
        data.mail=$("#Register-email").val();
        data.mdp=$("#Register-pwd").val();
        data.city=$("#Register-city").val();
        data.pseudo=$("#Register-pseudo").val();
        postData("/register",data,token,onPostRegister,onError);
        
    });
    $("#introductionQuote").click(e=>{
        var response=[{
                "a":"aaaaaaaaaaa"},{
                "a":"b"},{
                "a":"c"},{
                "a":"d"},{
                "a":"e"
        }];
        viewIntroductionQuote();

        //getData("/introduireServlet",token,onGetAmenagements,onError);
        onGetAmenagements(response);
        //getClient
        onGetClientQuoteForm(tempUsers);
    });

    /*
    filtre dropdow lié client
    */
    $("#Quote-Form-Client-Search").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $(".dropdown-menu li").filter(function() {
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
      });
      $("#Quote-Form-image").change(e=>{
              encodeImagetoBase64(e.target);
      })
      //evoyé donné Introduction devis
    $("#bnt-IntroductionQuote").click(e=>{
            console.log($("#imageQuote").attr("src"));
        encodeImagetoBase64($("#Quote-Form-image"));
        
        if($("#Quote-Form-firstName").val())
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
        var amenagementList=document.getElementsByClassName("form-check");
        for(var i=0;i<amenagementList.length;i++){
                var element=amenagementList[i].firstChild;
                if(element.checked){
                        console.log("ok");/////////////////////////////////
                }
        }
        var dataQuote={
                client:$("#Quote-Form-Client-items").val(),
                date:$("#Quote-Form-quoteDate").val(),
                price:$("#Quote-Form-price").val(),
                duration:$("#Quote-Form-duration").val(),
                image:$("#imageQuote").attr("src"),
                type:$("#Quote-Form-layoutType").val()

        };
        var data={
                "dataUser":dataUser,
                "dataQuote":dataQuote
        }
        console.log(data.dataQuote.image);
        console.log(data.dataQuote.type);
        //postData("/introduireServlet",data,token,onPostIntroductionQuote,onError);
        
    });

    $(".Register-confirmation-link").click(e=>{
            viewRegisterConfirmation();
            
            //getData("/register_confirmation",token,onGetRegisterConfirmation,onError);
            onGetRegisterConfirmation();
    });
   

    $("#btn-deconnexion").click(e=>{
        e.preventDefault()
        token=undefined;
        localStorage.removeItem("token");
        console.log(token);
        console.log(localStorage.getItem("token"));
        

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

}

function allHide(){
        $("#login").hide();
        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();
        $("#test1").hide();
        $("#carousel").hide();
        $("#Register-confirmation").hide();
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
}

function viewLogin(){
        $("#login-form").show();
        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();
        $("#carousel").hide();

}
//Home page non-connecté
function viewHomePage(){
        
        $("#login-form").hide();
        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();
        $("#carousel").show();
        $("#Register-confirmation").hide();
        //$("#introductionQuote").hide();
        //$("#introductionQuoteForm").hide();
        //$("#list-confirmation-link").hide(); 
        $("#voir-utilisateurs").hide();
        $("#voir-devis").hide();
        $("#voir-devis-client").hide();
        $("#voir-clients").hide();
        $("#voir-details-devis").hide();
        //$("#mesDevis").hide();
        //$("#search-homepage").hide();
}

//vue après authentification
function viewAuthentification(){
		$("#search-homepage").show();
		$("#search-devis-date-link").show();
        $("#search-devis-montant-link").show();
        $("#search-devis-amenagement-link").show();
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
        $("#connexion").hide();
        $('#navigation_bar').hide();
        $("#login-form").hide();
        $("#btn-deconnexion").show();
        $("#Register-confirmation").hide();
        $("#voir-utilisateurs").hide();
        $("#voir-devis").hide();
        $("#voir-devis-client").hide();
        $("#voir-details-devis").hide();
         
}

function authentificationToken(token){
        console.log("test"+token)
        if(token){
                if(user==undefined){
                      //  getData("/login",token,onPostLogin,onError);
                      alert();
                }    
                viewAuthentification();
        }
        else{
                viewHomePage();
        }
}
//Authentificaiton réussis
function onPostLogin(response){
        if(response.success==="true"){
                console.log("data: "+response.userData.prenom);
                user=response.userData;
                token=response.token;
                localStorage.setItem("token",token);
                console.log(localStorage.getItem("token"));
                viewAuthentification();
        }else{
                console.log("erreur");
                $("#wrong_passwd").show();
        }
}
function onGetClientQuoteForm(response){
        response.forEach(element => {
                var li=document.createElement("li");
                var a=document.createElement("a");
                a.href="#";
                a.val=element.idUser;// a changer en idClient
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
     var i=0;
        response.forEach(element => {//changer les donnees
                
                var checkbox=creatHTMLFromString('<div class="form-check col-sm-3 col-form-label" >'
                +'<input  type="checkbox" id="'+i+'" value="option1">'
                +'<label for="#'+i+'">'+element["a"]+'</label>'
                +'</div>');
                $("#Quote-Form-layoutType").append(checkbox);
                i++;
        });
}

//affiche les demandes d'inscription dans un tableau
function onGetRegisterConfirmation(response){
        
        
        tempoUsersConf.forEach(element => {
                var tr=document.createElement("tr");
                var prenom=document.createElement("td");
                prenom.innerHTML=element.prenom;
                prenom.val=element.prenom;
                prenom.setAttribute("valueof","firstname");
                var nom=document.createElement("td");
                nom.val=element.nom;
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
                +'<a class="dropdown-item" href="#">Client</a>'
                +'<a class="dropdown-item" href="#">Ouvrier</a>'
                +'</div>'
                +'</div></td>');
                btnStatus.appendChild(btnStatusEvent);
                btnStatusEvent.addEventListener("click",onClickStatusItem);
               
                tr.appendChild(btnStatus);

                var btnClientLink=creatHTMLFromString('<div class="dropdown-Client">'
                +'<button class="btn btn-secondary dropdown-toggle" id="Quote-Form-Client-dropdown" type="button" data-toggle="dropdown">lié au client<span class="caret"></span></button>'
                +'<ul class="dropdown-menu" id="Quote-Form-Client-items">'
                +'<input class="form-control" id="Quote-Form-Client-Search" type="text" placeholder="Search..">'
                +'</ul>'
                +'</div>');
                tr.appendChild(btnClientLink);
                
                //GetClient

                //creation du boutton confirmer
                var btnConfirmationEvent=creatHTMLFromString('<td><button id="bnt-Register-confirmation" class="btn btn-info"> Confirmer</button></td>');
                btnConfirmation.appendChild(btnConfirmationEvent);
                btnConfirmationEvent.addEventListener("click",onClickRegisterConfirmation);
                tr.appendChild(btnConfirmation);
                $("#Register-confirmation-body").append(tr);
        });
}
function onClickRegisterConfirmation(element){
        var btn=element.target;
        var data={};
        console.log(btn.parentNode.parentNode)
        btn.parentNode.parentNode.childNodes.forEach(e => {
                data[e.getAttribute("valueof")]=e.val;
                
                
        });
        console.log(data.status);
        

        postData("/confirmation",data,token,onPostRegisterConfirmation,onError);
}
function onPostRegisterConfirmation(response){
        alert("passage");
}

function onClickStatusItem(element){
        
        var btn=element.target;
        if(btn.tagName=="A"){//vérifie si c'est un element <a>
                btn.parentNode.parentNode.parentNode.val=btn.innerHTML
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
        if(response.success){
                alert();
        }
}

function onError(response){
	console.log("Erreur");
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
	    	+ response.devisData[data].date + "</td>\n<td>" 
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
        //console.log("apres postData");
        //getData("/detailsDevis", token, afficherDetailDevis, onError);
    });
}

function afficherDevisClient(response){
	Object.keys(response.devisData).forEach(data => {
	    var html = "<tr>";
	    html+="<td><a href='#' class='reference-devis'>"
	    	+ response.devisData[data].date + "</a></td>\n<td>" 
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
	/*allHide();
	$("#voir-devis").hide();
    $("#voir-devis-client").hide();*/
	$("#voir-details-devis").show();
	$("#voir-details-devis #dateDevis").attr("value", response.devisData.date);
	$("#voir-details-devis #montantDevis").attr("value", response.devisData.montant);
	$("#voir-details-devis #etatDevis").attr("value", response.devisData.etat);
	//$("#voir-details-devis #typeAmenagementDevis").attr("value", response.devisData.typeAmenagement);
	$("#voir-details-devis #dureeTravauxDevis").attr("value", response.devisData.dureeTravaux);
	$("#voir-details-devis #btn-devis").attr("value", changerValeurBouton(response.devisData.etat));
	$("#voir-details-devis #btn-devis").click(e=>{
		var nouvelEtat = changerEtat(response.devisData.etat);	
		let data={};
		data.etatDevis = nouvelEtat;
		console.log("Nouvel etat : " + nouvelEtat);
		console.log(data.etatDevis);
		//putData("/detailsDevis", token, data, afficherDetailsDevis, onError);

	});
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
		  valeurBouton = "";
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
		  nouvelEtat = "ANP";
		  break;
	  case 'ANP':
		  nouvelEtat = "DC";
		  break;
	  case 'DC':
		  nouvelEtat = "A";
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
	console.log("user = " + user);
	data.userId = user.userId;
	//postData("/listeDevisClient",data,token,afficherDevisClient,onError);
}