"use-strict";
import {postData,getData,deleteData,putData} from "./util.js" ;
let token=undefined;
$('#navigation_bar').hide();
$('#login-form').hide();


$(document).ready(e=>{
        
        
        token=localStorage.getItem("token");
        authentificationToken(token);
        $("#connexion").click(function (e) { 
                $("#carousel").hide();
                $('#login-form').show();
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
            postData("/login",data,token,onPost,onError);
            
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
        //postData("/login",data,token,onPost,onError);
        
    });

    $("#btn-deconnexion").click(e=>{
        e.preventDefault()
        token=undefined;
        localStorage.removeItem("token");
        console.log(token);
        console.log(localStorage.getItem("token"));
        

    });
});

function allHide(){
        $("#login").hide();
        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();
}
function viewLogin(){
        $("#login").show();
        $("#btn-deconnexion").hide();
        $("#wrong_passwd").hide();

}
function viewAuthentification(){
        $("#login").hide();
        $("#btn-deconnexion").show();
}
function authentificationToken(token){
        console.log("test"+token)
        if(token){              
                viewAuthentification();
        }
        else{
                viewLogin();
        }
}
function onPost(response){
        if(response.success==="true"){
                token=response.token;
                localStorage.setItem("token",token);
                console.log(localStorage.getItem("token"));
                viewAuthentification();
        }else{
                console.log("merreur");
                $("#wrong_passwd").show();
        }
}

function onError(response){

}