$(document).ready(	function() {
	$("#fileImg").change(function() {
		fileSize = this.files[0].size;

		if (fileSize > 1048576) {//1MB
			this.setCustomValidity("Debes seleccionar una imagen de una tamaño menor a 1MB");
			this.reportValidity();
		} else {
			showImageThumbnail(this);
		}

	})
});

function showImageThumbnail(fileInput) {
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#thumbnail").attr("src", e.target.result);
	};
	reader.readAsDataURL(file);
}

function showModalDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}

