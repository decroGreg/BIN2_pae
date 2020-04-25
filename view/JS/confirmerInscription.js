import {creatHTMLFromString,onError} from "./util.js" ;
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
function viewRegisterConfirmation(){
    $("#login").hide();
    $("#btn-deconnexion").hide();
    $("#wrong_passwd").hide();
    $("#test1").hide();
    $("#carousel").hide();
    $("#Register-confirmation").show();
    $("#introductionQuoteForm").hide();
   
}
export{onGetRegisterConfirmation,viewRegisterConfirmation};