toastr.options = {
	"closeButton" : true,
	"debug" : false,
	"newestOnTop" : false,
	"progressBar" : false,
	"positionClass" : "toast-bottom-right",
	"preventDuplicates" : true,
	"onclick" : null,
	"showDuration" : "300",
	"hideDuration" : "1000",
	"timeOut" : "5000",
	"extendedTimeOut" : "1000",
	"showEasing" : "swing",
	"hideEasing" : "linear",
	"showMethod" : "fadeIn",
	"hideMethod" : "fadeOut"
}

function alertaError(error) {
	toastr["error"](error, "Error")
}

function alertaExito(exito) {
	toastr["success"](exito)
}

function alertaInfo(info) {
	toastr["info"](info)
}

