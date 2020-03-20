function generaTabla() {

var elemento = document.getElementById("elemento");

     var newDiv = document.createElement("div");

  var newContent = document.createTextNode(elemento.value);
  newDiv.appendChild(newContent);

  var currentDiv = document.getElementById("div1");
  document.body.insertBefore(newDiv, currentDiv);
  Android.showToast( elemento.value );
  document.getElementById("elemento").value = "";
  }