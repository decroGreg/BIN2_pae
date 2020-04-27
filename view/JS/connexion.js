
const OUVRIER="O";
const CLIENT="C";
//vue après authentification
function viewAuthentification(user){
    $("#search-homepage").show();
    $("#search-devis-date-link").show();
    $("#search-devis-montant-link").show();
    $("#search-devis-amenagement-link").show();
    $("#search-client-link").hide();
    $("#search-utilisateur-link").hide();
    $("#search-amenagement-link").hide();
    $("#mesDevis").show();
    
    if(user &&user.statut===OUVRIER){
            $(".Register-confirmation-link").show();
            $(".introductionQuote").show(); 
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

function viewLogin(){

    console.log($("#login-form").target);
    $("#login-form").show();
    $("#btn-deconnexion").hide();
    $("#wrong_passwd").hide();
    $("#carousel").hide();

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

export{viewAuthentification,viewLogin,onPostRegister};