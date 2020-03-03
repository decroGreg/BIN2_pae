function postData(url="",data,token,onPost,onError){
    let headers;
    console.log("OK");
    console.log("Bearer " +token);
      if (token)
        headers = {
         "Content-Type": "application/json",
         Authorization: "Bearer " + token
        };
      else
        headers = {
          "Content-Type": "application/json"//faire des recherches sur l'utilités
    };
    
    console.log("passe condition");
    $.ajax({
        type: "post",
        contentType:"json",
        url: url,
        data: data,
        dataType: "dataType",
        success: onPost,
        error:onError
    });
    console.log("reponse");
}
function getData(){

}
function deleteData(){

}
function putData(){

}
export{postData,getData,deleteData,putData};