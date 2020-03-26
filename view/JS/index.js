"use-strict";
import {postData,getData,deleteData,putData} from "./util.js" ;
const OUVRIER="o";
const CLIENT="client";
let token=undefined;
let user;
$('#navigation_bar').hide();
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

let tempUsers=[{"dateInscription":"2020-03-26 11:46:37.95006","email":"mrbrg@live.fr","idUser":0,"motDePasse":"mdp","nom":"B","prenom":"M","pseudo":"mrbrg","statut":"c","ville":"Bruxdells"},{"dateInscription":"2020-03-26 11:46:37.95006","email":"mrbrdddddddg@live.fr","idUser":0,"motDePasse":"fgr","nom":"Bdd","prenom":"Mdef","pseudo":"jbe","statut":"e","ville":"Brudddxdells"}];


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
    

    $(".Register-confirmation-link").click(e=>{
            viewRegisterConfirmation();
            
            //getData("/register_confirmation",token,onGetRegisterConfirmation,onError);
            onGetRegisterConfirmation();
    });
    $("#btn-status").click(e=>{
            alert();
            console.log(e.innerHTML);
            $("#btn-status").textSet(e.innerHTML);
    });
    $("#bnt-Register-confirmation").click(e=>{
        var data={
                
        }
        //postData("/register_confirmation",data,token,onError);
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
                //getData("/listUsers",token,afficherUtilisateurs,onError);
                afficherUtilisateurs();
                /*$("#voir-utilisateurs tbody").append("<tr><td>"+data+"</td></tr>");
                data = JSON.parse(data);
                $("#voir-utilisateurs tbody").append("<tr><td>"+data+"</td></tr>");
                var html = "<tr>";
                html+="<td>" + data.pseudo + "</td>\n<td>" + data.nom + "</td>\n<td>" + data.prenom + "</td>\n<td>" + data.ville + "</td>\n<td>" + data.mail + "</td>\n<td>" + data.statut + "</td></tr>;"
                $("#voir-utilisateurs tbody").append(html);*/
        }
    });
    
});

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
        $("#list-confirmation-link").hide(); 
        $("#voir-utilisateurs").hide();
}

//vue après authentification
function viewAuthentification(){
       
        if(user &&user.statut===OUVRIER){
                console.log("passage");
                $("#list-confirmation-link").show(); 
                
        }
        $("#connexion").hide();
        $('#navigation_bar').hide();
        $("#login-form").hide();
        $("#btn-deconnexion").show();
        $("#Register-confirmation").hide();
        $("#voir-utilisateurs").hide();

         
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
                console.log(element.nom);
                var tr=document.createElement("tr");
                var prenom=document.createElement("td");
                prenom.innerHTML=element.prenom;
                var nom=document.createElement("td");
                nom.innerHTML=element.nom;
                var btnStatus=document.createElement("td");
                var btnConfirmation=document.createElement("td");
                tr.appendChild(prenom);
                tr.appendChild(nom);
                //creation du boutton status
                btnStatus.appendChild(creatHTMLFromString('<td><div class="btn-group">'
                +'<button type="button" id="btn-status" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
                +'status'
                +'</button>'
                +'<div class="dropdown-menu">'
                +'<a class="dropdown-item" onClick="onClickStatusItem(this)" href="#">Client</a>'
                +'<a class="dropdown-item" onClick="onClickStatusItem(this)" href="#">Ouvrier</a>'
                +'</div>'
                +'</div></td>'));
                tr.appendChild(btnStatus);
               
                btnConfirmation.appendChild(creatHTMLFromString('<td><button id="bnt-Register-confirmation" class="btn btn-info"> Confirmer</button></td>'));
                tr.appendChild(btnConfirmation);
                $("#Register-confirmation-body").append(tr);
        });
}
function onClickStatusItem(element){
        alert(element.innerHTML);
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

}
/////////////////////////////////////////////////////////////////////////////////////////////test////////////////////////


function afficherUtilisateurs(response){
	tempUsers.forEach(data => {
	    var html = "<tr>";
	    html+="<td>" + data.pseudo + "</td>\n<td>" + data.nom + "</td>\n<td>" + data.prenom + "</td>\n<td>" + data.ville + "</td>\n<td>" + data.mail + "</td>\n<td>" + data.statut + "</td></tr>;"
	    $("#voir-utilisateurs tbody").append(html);
	});

}
