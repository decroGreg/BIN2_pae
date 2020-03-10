"use-strict"
import {postData,getData,deleteData,putData} from "./util.js" ;
let token=undefined;

$(document).ready(e=>{
        token=localStorage.getItem("token");

        authentificationToken(token);
    $("#btn-connexion").click(e=>{
            let a=e.target.parentNode.parentNode;
            console.log(a);
            let data={};
            data.mail=a.email.value;
            data.mdp=a.password.value;
            postData("/login",data,token,onPost,onError);
            
    });

    $("#btn-deconnexion").click(e=>{
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