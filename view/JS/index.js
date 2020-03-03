"use-strict"
import {postData,getData,deleteData,putData} from "./util.js" ;
let token=null;

$(document).ready(e=>{
    
    $("#btn-connexion").click(e=>{
            let a=e.target.parentNode.parentNode;
            let data={};
            data.pseudo=a.username.value;
            data.mdp=a.password.value;
            postData("/login",data,token,onPost,onError);
    });
    
    
});
function onPost(response){
    localStorage.token=response.token;
}
function onError(response){

}