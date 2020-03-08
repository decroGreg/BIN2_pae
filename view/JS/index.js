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
            $("#login").hide();
    });
});
function authentificationToken(token){
        console.log("test"+token)
        if(token){              
                $("#login").hide();
        }
}
function onPost(response){
    
        token=response.token;
        localStorage.setItem("token",token);
        console.log(localStorage.getItem("token"));
    

}
function onError(response){

}