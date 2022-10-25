/**
* js file for MarsPictureApi
* Mozhi Shen
*/
let userId = getUrlParameter('userId');
if(userId == null || userId ==''){
	userId ==localStorage.getItem('userId')
	if(userId == null || userId ==''){
		document.getElementById('createUser').value = true
	} else{
	//	fetch('/savedPreferences?userId='+userId)  fetch not working.
	//	.then(response =>response.json())
	//	.then(jsonResponse=>console.log(jsonResponse))
		window.location.herf ='/?userId='+userId
	}
}

if (userId != null && userId !=''){
	localStorage.setItem('userId',userId)
	document.getElementById('userId').value = userId
}

let marsApiButtons = document.querySelectorAll("button[id*='marsApi']")

marsApiButtons.forEach(button => button.addEventListener('click',function(){// pick up the click and get the specific rover	
									const buttonId =this.id
									const roverId = buttonId.split('marsApi')[1]
									let apiData = document.getElementById("marsApiRoverData")
									apiData.value =roverId  //assign the id of rover to marsApiRoverData
									document.getElementById('fromRoverType').submit() // submit the form so it can be picked up by service.
							}))

function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};

let marsRoverType = document.getElementById('marsApiRoverData').value

//console.log(getUrlParameter("marsApiRoverData"))
	
hightlightBtnByRoverType(marsRoverType)

let marsSol = document.getElementById('marsSol').value
if(marsSol != null && marsSol != '' && marsSol >= 0)//make sure the home default page would have sol =1.
document.getElementById("marsSol").value = marsSol

		
function hightlightBtnByRoverType(roverType){
	if (roverType == '')// default page is at opportunity
		roverType ='opportunity'
		
	document.getElementById("marsApi" +roverType).classList.remove('btn-secondary')
	document.getElementById("marsApi" +roverType).classList.add('btn-primary')
}	