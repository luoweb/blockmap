var server = {};

Object.defineProperty(server, 'protocal', {
	get: function() {
		//return 'https';
		return '$#{ENV_DID_HTTP_TYPE}';
	}
});

Object.defineProperty(server, 'address', {
	get: function() {
	    //return 'baasf5-08.sdc.cs.icbc';
		return '$#{ENV_DID_SERVER_ADDRESS}';
	}
});

Object.defineProperty(server, 'contextRoot', {
	get: function() {
		return 'did/api';
	}
});

Object.defineProperty(server, 'url', {
	get: function() {
		return server.protocal + '://' + server.address + '/' + server.contextRoot + '/';
	}
});

 
