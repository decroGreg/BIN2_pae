"use-strict";
import {postData,getData,deleteData,putData} from "./util.js" ;
const OUVRIER="o";
const CLIENT="client";
let token=undefined;
let user;
$('#navigation_bar').hide();

function encodeImagetoBase64(element) {
        
        var file = element[0].files[0];

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
				{"dateInscription":"2020-03-26 11:46:37.95006","email":"mrbrdddddddg@live.fr","idUser":0,"motDePasse":"fgr","nom":"Bdd","prenom":"Mdef","pseudo":"jbe","statut":"e","ville":"Brudddxdells"}];

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
        viewIntroductionQuote();
    });
    $("#bnt-Register-confirmation").click(e=>{
        alert();
    });
    

    $("#bnt-IntroductionQuote").click(e=>{
        encodeImagetoBase64($("#Quote-Form-image"));
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
        
        var dataQuote={
                client:$("#Quote-Form-client").val(),
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
        postData("/introduireServlet",data,token,onPostIntroductionQuote,onError);
        
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
    
    $("#btn-search-category").click(e=>{
    	e.preventDefault();
        if($("#search-option-category").val()=="utilisateur"){
                allHide();
                $("#voir-utilisateurs").show();
                getData("/listeUsers",token,afficherUtilisateurs,onError);
                afficherUtilisateurs();
                /*$("#voir-utilisateurs tbody").append("<tr><td>"+data+"</td></tr>");
                data = JSON.parse(data);
                $("#voir-utilisateurs tbody").append("<tr><td>"+data+"</td></tr>");
                var html = "<tr>";
                html+="<td>" + data.pseudo + "</td>\n<td>" + data.nom + "</td>\n<td>" + data.prenom + "</td>\n<td>" + data.ville + "</td>\n<td>" + data.mail + "</td>\n<td>" + data.statut + "</td></tr>;"
                $("#voir-utilisateurs tbody").append(html);*/
        }
        if($("#search-option-category").val()=="devis"){
        	allHide();
        	$("#voir-devis").show();
        	getData("/listeDevis",token,afficherDevis,onError);
        	afficherDevis();
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
        	afficherDevisClient();
        }
        
		if($("#search-option-category").val()=="montant"){
			allHide();
        	$("#voir-devis-client").show();
        	getData("/listeDevisClient",token,afficherDevisClient,onError);
        	afficherDevisClient();
		}
		
		if($("#search-option-category").val()=="type_amenagement"){
			allHide();
        	$("#voir-devis-client").show();
        	getData("/listeDevisClient",token,afficherDevisClient,onError);
        	afficherDevisClient();
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
        $("#introductionQuote").hide();
        $("#introductionQuoteForm").hide();
        $("#list-confirmation-link").hide(); 
        $("#voir-utilisateurs").hide();
        $("#voir-devis").hide();
        $("#voir-devis-client").hide();

        $("#voir-clients").hide();
}

//vue après authentification
function viewAuthentification(){
       
        if(user &&user.statut===OUVRIER){
                console.log("passage");
                $("#list-confirmation-link").show();
                $("#introductionQuote").show(); 
                
        }
        $("#connexion").hide();
        $('#navigation_bar').hide();
        $("#login-form").hide();
        $("#btn-deconnexion").show();
        $("#Register-confirmation").hide();
        $("#voir-utilisateurs").hide();
        $("#voir-devis").hide();
        $("#voir-devis-client").hide();

         
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
	console.log("Erreur dans getData");
}
/////////////////////////////////////////////////////////////////////////////////////////////test////////////////////////


function afficherUtilisateurs(response){
	console.log("afficherUtilisateur"+response.usersData);
	var data = response.data;
	tempUsers.forEach(data => {
	    var html = "<tr>";
	    html+="<td>" 
	    	+ data.pseudo + "</td>\n<td>" 
	    	+ data.nom + "</td>\n<td>" 
	    	+ data.prenom + "</td>\n<td>" 
	    	+ data.ville + "</td>\n<td>" 
	    	+ data.mail + "</td>\n<td>" 
	    	+ data.statut + "</td></tr>";
	    $("#voir-utilisateurs tbody").append(html);
	});
}

function afficherDevis(response){
	tempDevis.forEach(data => {
	    var html = "<tr>";
	    html+="<td>"
	    	+ data.idClient + "</td>\n<td>" 
	    	+ data.date + "</td>\n<td>" 
	    	+ data.montant + "</td>\n<td>" 
	    	+ data.dureeTravaux + "</td>\n<td>" 
	    	+ data.photoPreferee + "</td>\n<td>" 
	    	+ data.etat + "</td></tr>";	    
	    $("#voir-devis tbody").append(html);
	});
}

function afficherDevisClient(response){
	console.log(response.data);
	var data = response.data;
	tempDevis.forEach(data => {
	    var html = "<tr>";
	    html+="<td>"
	    	+ data.date + "</td>\n<td>" 
	    	+ data.montant + "</td>\n<td>" 
	    	+ data.dureeTravaux + "</td>\n<td>" 
	    	+ data.photoPreferee + "</td>\n<td>" 
	    	+ data.etat + "</td></tr>";	    
	    $("#voir-devis-client tbody").append(html);
	});
}

function afficherClients(response){
	
}