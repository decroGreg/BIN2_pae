import {creatHTMLFromString,getData,postData,onError} from "./util.js" ;
import {token} from "./index.js";
var photo={};
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
                photo="{"+reader.result+"}";
          console.log(reader.result);
          $("img").attr("src",reader.result);
          
        }
        reader.readAsDataURL(file);        
}


$(document).ready(function () {
        $(".introductionQuote").click(e=>{
                viewIntroductionQuote();
                getData("/introduireServlet",token,onGetAmenagements,onError);
                getData("/listeUsers",token,onGetClientQuoteForm,onError);
            });
        $("#Quote-Form-Client-Search").on("keyup", function() {
                filterSearchClient(this);
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
});


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
function viewIntroductionQuote(){
    $("#login").hide();
    $("#btn-deconnexion").hide();
    $("#wrong_passwd").hide();
    $("#test1").hide();
    $("#carousel").hide();
    $("#Register-confirmation").hide();
    $("#introductionQuoteForm").show();
   
}
export{onGetAmenagements,onGetClientQuoteForm,onPostIntroductionQuote,filterSearchClient,viewIntroductionQuote, encodeImagetoBase64};