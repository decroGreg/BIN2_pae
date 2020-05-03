
import {creatHTMLFromString,getData,postData,onError, filterDropdown} from "./util.js" ;
import {token, allHide} from "./index.js";
var photo={};
var nbPhoto=0;
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

function encodeImagetoBase64(element) {
        var file = element.files[0];
        console.log(element);
        console.log(file);
        var reader = new FileReader();

        reader.onloadend = function() {
          $("#imageQuote").attr("src",reader.result);
        }
        reader.readAsDataURL(file);        
}


$(document).ready(function () {

        
        $("#CreerAmenagement").click(e=>{
                var data={
                        "description":$("#Modal-Form-Description").val()
                };
                if(!checkInput(data,"la description est vide")) return;
                console.log(data);
                postData("/amenagements",data,token,viewIntroductionQuote,onError);
        });

        $(".introductionQuote").click(e=>{
                viewIntroductionQuote();
               
            });
        $("#enregistrer-photo").click(e=>{
                photo[nbPhoto]=$("img").attr("src");
                console.log(photo);
                nbPhoto++;
        });

      $("#Quote-Form-image").change(e=>{
              encodeImagetoBase64(e.target);
      })
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
                    "date":$("#Quote-Form-quoteDate").val(),
                    "price":$("#Quote-Form-price").val(),
                    "duration":$("#Quote-Form-duration").val()
            };
            if(!checkInput(dataQuote,"veuillez remplir tous les champs du devis")){
                 console.log("PAS de Passage");
                 return;//à voir si image peut être null;
            }
            dataQuote.client=$("#Quote-Form-Client-items").val();
            dataQuote.user=$("#Quote-Form-User-items").val();
            if(type.length==0){
                    $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                    $("#error-notification").text("veillez introduire un type d'aménagement");
                    return;
            }
            if(photo<0){
                    console.log("il faut introduire une photo");
                    return;
            }
            var data={
                    "images":photo,
                    "dataUser":dataUser,
                    "dataQuote":dataQuote,
                    "type":type
            }
            console.log(data);
            console.log(data.images);
            postData("/introduireServlet",data,token,onPostIntroductionQuote,onError);
            photo={};
            
        });
});


function onGetClientQuoteForm(response){
                $("#Quote-Form-Client-items").text("");
                
                //création du input avec filtre
        var input=document.createElement("input");
        input.className="form-control";
        input.id="Quote-Form-Client-Search";
        input.type="text";
        input.placeholder="Search..";
        input.addEventListener("keyup",function() {
                filterDropdown(this);
        });
        $("#Quote-Form-Client-items").append(input);

        response.clientsData.forEach(element => {
                var li=document.createElement("li");
                var a=document.createElement("a");
                a.href="#";
                a.val=element.idClient;
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

//Samuel
function onGetUserQuoteForm(response){
        $("#Quote-Form-User-items").text("");
        
        //création du input avec filtre
        var input=document.createElement("input");
        input.className="form-control";
        input.id="Quote-Form-User-Search";
        input.type="text";
        input.placeholder="Search..";
        input.addEventListener("keyup",function() {
        filterDropdown(this);
        });
        $("#Quote-Form-User-items").append(input);

        response.usersData.forEach(element => {
        var li=document.createElement("li");
        var a=document.createElement("a");
        a.href="#";
        a.val=element.idUser;
        a.innerText=element.prenom+" "+element.nom;
        a.addEventListener("click",function(e){
                
                $("#Quote-Form-User-items").val(e.target.val);
                $("#Quote-Form-User-dropdown").text(e.target.innerText);
                console.log($("#Quote-Form-User-items").val());

        });
        li.appendChild(a);
        $("#Quote-Form-User-items").append(li);
});
}
//Fin Samuel

function onGetAmenagements(response){
 $("#Quote-Form-layoutType").text("");
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


function viewIntroductionQuote(){
    allHide();
    
    photo={};
    $("#introductionQuoteForm").show();
    getData("/introduireServlet",token,onGetAmenagements,onError);
    getData("/listeClients",token,onGetClientQuoteForm,onError);
    getData("/listeUsers",token,onGetUserQuoteForm,onError)
   
}

export{onGetAmenagements,onGetClientQuoteForm,onGetUserQuoteForm,onPostIntroductionQuote,viewIntroductionQuote, encodeImagetoBase64};

