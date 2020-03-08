function postData(url="",data={},token,onSuccess,onError){
    let headers;
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
    
   
    $.ajax({
        type: "post",
        contentType:"json",
        url: url,
        headers:headers,
        data: JSON.stringify(data),
        dataType: "json",
        success: onSuccess,
        error:onError
    });
    
}
function getData(){

}
function deleteData(){

}
function putData(){

}
export{postData,getData,deleteData,putData};