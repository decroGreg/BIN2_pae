
import {creatHTMLFromString,getData,postData,onError,filterDropdown} from "./util.js" ;
import {token, allHide} from "./index.js";



$(document).ready(function () {
        $(".Register-confirmation-link").click(e=>{
                viewRegisterConfirmation();
                
                getData("/confirmation",token,onGetRegisterConfirmation,onError);
            
        });
       
});

var i=0;//permet d'afficher la meme liste des clients pour des bouttonsdifférentss
//affiche les demandes d'inscription dans un tableau
function onGetRegisterConfirmation(response){
        $("#Register-confirmation-body").html("");
        i=0;
        
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
                +'<a class="dropdown-item" value="O" href="#">Ouvrier</a>'
                +'</div>'
                +'</div></td>');
                btnStatus.appendChild(btnStatusEvent);
                btnStatusEvent.addEventListener("click",onClickStatusItem);
               
                tr.appendChild(btnStatus);

                //lié un client à l'utilisateurs
                var tdBtnClientLink=document.createElement("td");
                tdBtnClientLink.setAttribute("valueof","client");
                tdBtnClientLink.id="ClientLink"+i;
                

                var divForm=document.createElement("div");
                divForm.className="form-group col-md-3";

                var divDropdown=document.createElement("div");
                divDropdown.className="dropdown-Client";

                var button=document.createElement("button");
                button.className="btn btn-secondary dropdown-toggle";
                button.id="RegisterConfirmation-Form-Client-dropdown"+i;
                button.type="button";
                button.setAttribute("data-toggle","dropdown");
                button["data-toggle"]="dropdown";
                button.innerText="lie a l'utilisateur";
                console.log(button);
                var ul=document.createElement("ul");
                ul.className="dropdown-menu";
                ul.id="RegisterConfirmation-Form-Client-items"+i;

                var input=document.createElement("input");
                input.className="form-control";
                input.id="RegisterConfirmation-Form-Client-Search"+i;
                input.type="text";
                input.placeholder="Search...";
                input.addEventListener("keyup",function(){
                        filterDropdown(this);
                });

                ul.appendChild(input);
                divDropdown.appendChild(ul);
                divDropdown.appendChild(button);
                divForm.appendChild(divDropdown);
                tdBtnClientLink.appendChild(divForm);

                /*
                var btnClientLink=creatHTMLFromString('<div class="form-group col-md-3">'
                +'<div class="dropdown-Client">'
                + '<button class="btn btn-secondary dropdown-toggle" id="RegisterConfirmation-Form-Client-dropdown'+i+'" type="button" data-toggle="dropdown">lié à l\'utilisateur<span class="caret"></span></button>'
                + '<ul class="dropdown-menu" id="RegisterConfirmation-Form-Client-items'+i+'">'
                +  '<input class="form-control" id="RegisterConfirmation-Form-Client-Search'+i+'" type="text" onkeyup="filterDropdown(this)" placeholder="Search..">'
                +'</ul></div></div>');
                
                tdBtnClientLink.appendChild(btnClientLink);*/


                tr.appendChild(tdBtnClientLink);

                getData("/listeClients",token,onGetClientRegisterConfirmationForm,onError);
                
                //GetClient

                //creation du boutton confirmer
                var btnConfirmationEvent=creatHTMLFromString('<td><button id="bnt-Register-confirmation" class="btn btn-info"> Confirmer</button></td>');
                btnConfirmation.appendChild(btnConfirmationEvent);
                btnConfirmationEvent.addEventListener("click",onClickRegisterConfirmation);
                tr.appendChild(btnConfirmation);
                $("#Register-confirmation-body").append(tr);
                
                
        });
        

}
function onGetClientRegisterConfirmationForm(response){
        console.log(document.getElementById("RegisterConfirmation-Form-Client-items"+i))
        console.log($("#RegisterConfirmation-Form-Client-items"+i));

        response.clientsData.forEach(element => {
                console.log(element);
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
    allHide();
    $("#btn-deconnexion").hide();
    $("#Register-confirmation").show();
   
}