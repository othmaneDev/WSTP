var CheckTags = {
		 pattern:new RegExp("^(\\[[a-zA-Z0-9_-]+\\])*[a-zA-Z_-]+:([0-9]{1,2}|100)$")
};

CheckTags.checkTagsSyntaxe = function (inputTags){
	var success = true;
	var service = inputTags.split(" ");
	var i = 0;
	while(i<service.length && success){
		success = CheckTags.pattern.test(service[i]);
		i++;
	}
	return success;
};
