import {creatHTMLFromString} from "./util.js" ;
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
export{onGetAmenagements,onGetClientQuoteForm,onPostIntroductionQuote,filterSearchClient,viewIntroductionQuote};