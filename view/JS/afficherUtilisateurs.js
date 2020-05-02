import{token, allHide} from "./index.js";
import{filterDropdown, postData,onError} from "./util.js";

$(document).ready(function () {
		$("#btn-search-utilisateurs").click(e=>{
			var data={
				name:$("#utilisateursNameSearch").val(),
				city:$("#searchUtilisateursVille").val()
			};
			if(data.name==="")
				data.name=document.getElementById("inputUtilisateursNameSearch").value;
			console.log(data);
			//postData("/listeUsers",data,token,afficherUtilisateurs,onError);
			$("#utilisateursNameSearch").val("");
		})
		
});
/*
function afficherUtilisateursDropdown(response){
	afficherNomDropdown(response,document.getElementById("utilisateursNameSearch"),"inputUtilisateursNameSearch",document.getElementById("searchUtilisateursNameDropdown"),"usersData");
}
*/



function afficherUtilisateursDropdown(response){ //(response,balise ul,id input,balise dropdown)
	console.log("passage");
	$("#utilisateursNameSearch").html("");
	var input=document.createElement("input");
	input.id="inputUtilisateursNameSearch";
	input.type="text";
	input.placeholder="Search..";
	input.className="form-control";
	input.addEventListener("keyup",function(){
		filterDropdown(this);
	});

	$("#utilisateursNameSearch").append(input);

	response.usersData.forEach(e=>{
		var li=document.createElement("li");
		var a=document.createElement("a");
		a.innerHTML=e.nom+" "+e.prenom;
		a.addEventListener("click",function(){
			$("#searchUtilisateursNameDropdown").text(a.innerHTML);
			$("#utilisateursNameSearch").val(e.nom);
		});
		li.appendChild(a);
		$("#utilisateursNameSearch").append(li);
	});

}


function afficherUtilisateurs(response){
	allHide();
	viewListeUtilisateurs();
	$("#voir-utilisateurs tbody").html("");
	Object.keys(response.usersData).forEach(data => {
	    var html = "<tr>";
	    html+="<td>" 
	    	+ response.usersData[data].pseudo + "</td>\n<td>" 
	    	+ response.usersData[data].nom + "</td>\n<td>" 
	    	+ response.usersData[data].prenom + "</td>\n<td>" 
	    	+ response.usersData[data].ville + "</td>\n<td>" 
	    	+ response.usersData[data].email + "</td>\n<td>" 
	    	+ response.usersData[data].statut + "</td></tr>";
	    $("#voir-utilisateurs tbody").append(html);
	});
}


function viewListeUtilisateurs(){
	$("#login").hide();
    $("#btn-deconnexion").hide();
    $("#wrong_passwd").hide();
    $("#test1").hide();
    $("#carousel").hide();
    $("#Register-confirmation").hide();
    $("#introductionQuoteForm").hide();
    $("#voir-details-devis").hide();
    $("#voir-clients").hide();
    $("#voir-devis-client").hide();
    $("#voir-devis").hide();
    $("#voir-utilisateurs").show();
    $("#choisirPhotoPreferee").hide();
	$("#ajouterPhoto").hide();
}
export{afficherUtilisateurs, viewListeUtilisateurs,afficherUtilisateursDropdown};