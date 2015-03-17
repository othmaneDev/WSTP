EasySuggest = {};


EasySuggest.complete = (function (input, word){
	str = document.getElementById(input).value;
	str = str.substring(0,str.lastIndexOf(' ')+1)+word;
	document.getElementById(input).value = str;
	document.getElementById(input).focus();
});

EasySuggest.suggest = (function(list, input, suggestions){
	document.getElementById(suggestions).innerHTML='';
	last =  document.getElementById(input).value;
	last = last.substring(last.lastIndexOf(" ")+1);	

	for(i=0;i<list.length;i++){
		if(list[i].lastIndexOf(last)>=0 && last !="" && last !=" "){
			li = document.createElement("li");
			li.innerHTML=list[i];
			li.onclick=function(){
				EasySuggest.complete(input,this.innerHTML);
			};
			document.getElementById(suggestions).appendChild(li);
		}
	}
});