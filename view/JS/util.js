function postData(url="",data={},token,onSuccess,onError){
 
    let headers;
   
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
function getData(url="",token,onSuccess,onError){
  let headers;
  if(token){
    headers={
      "Content-Type": "application/json",
      Authorization: "Bearer"+token
    };
  }else{
    headers={"Content-Type":"application/json"};
  }
  $.ajax({
    url:url,
    type:"get",
    headers:headers,
    dataType:"json",
    success:onSuccess,
    error:onError
  });

}
function deleteData(url="",token,data={},onGet,onError){
  let headers;
  
  if(token)
    headers ={
        "Content-Type": "application/json",
        Authorization:"Bearer " +token};

  else
    headers={
      "Content-Type": "application/json",
    };
    $.ajax({
      contentType:"json",
      type:"delete",
      url:url,
      headers:headers,
      data:JSON.stringify(data),
      dataType:"json",
      succes:onGet,
      error:onError
    });
}
function putData(url="",token,data={},onGet,onError){
  let headers;
 
  if(token)
      headers ={
        "Content-Type": "application/json",
        Authorization:token};
  else 
	  headers={
		  "Content-Type": "application/json"};
      $.ajax({
        contentType:"json",
        type:"put",
        url:url,
        headers:headers,
        data:JSON.stringify(data),
        dataType:"json",
        success:onGet,
        error:onError
      });
      
}

function onError(response){

  $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
  $("#error-notification").text(response.message);
}


//methode trouver sur: https://stackoverflow.com/questions/494143/creating-a-new-dom-element-from-an-html-string-using-built-in-dom-methods-or-pro
function creatHTMLFromString(htmlString){
  var div = document.createElement('div');
  div.innerHTML = htmlString.trim();

  // Change this to div.childNodes to support multiple top-level nodes
  return div.firstChild; 
}
function filterDropdown(element){
  var value = $(element).val().toLowerCase();
  $(".dropdown-menu li").filter(function() {
    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
    

  });
}
function checkInput(data,message){
  var element;

  
  for(element in data){
          if(!data[element]){
                  
                  $("#error-notification").fadeIn('slow').delay(1000).fadeOut('slow');
                  $("#error-notification").text(message);
                  return false;
          }
  }
  return true;
}


export{postData,getData,deleteData,putData,creatHTMLFromString,onError,filterDropdown,checkInput};