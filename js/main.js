let mapa = document.getElementById("mapa");
let popUp = document.getElementById("popUp");
let omitirBtn = document.querySelector("#popUp .buttons .omitir");
let crearEmergenciaBtn = document.querySelector("#popUp .buttons .crearEmergencia");
let popUpDos = document.getElementById("popUpDos");
let omitirBtnDos = document.querySelector("#popUpDos .buttons .omitir");
let crearEmergenciaBtnDos = document.querySelector("#popUpDos .buttons .crearEmergencia");
let avisado = false;

mapa.addEventListener("click", () => {
    if (mapa.src == "http://127.0.0.1:5500/assets/map_calor_0.jpg") {
        mapa.src = "http://127.0.0.1:5500/assets/map_calor_1.jpg";
        avisado = false;
    } else if (mapa.src == "http://127.0.0.1:5500/assets/map_calor_1.jpg" && avisado == false) {
        popUp.className = "show";
        avisado = true;
    } else if (mapa.src == "http://127.0.0.1:5500/assets/map_calor_1.jpg" && avisado == true) {
        mapa.src = "http://127.0.0.1:5500/assets/map_calor_2.jpg";
    } else if (mapa.src == "http://127.0.0.1:5500/assets/map_calor_2.jpg") {
        mapa.src = "http://127.0.0.1:5500/assets/map_calor_3.jpg";
    } else if (mapa.src == "http://127.0.0.1:5500/assets/map_calor_3.jpg") {
        mapa.src = "http://127.0.0.1:5500/assets/map_calor_4.jpg";
    }

    if (mapa.src == "http://127.0.0.1:5500/assets/map_map.jpg") {
        mapa.src = "http://127.0.0.1:5500/assets/map_activo.jpg";
        avisado = false;
    } else if (mapa.src == "http://127.0.0.1:5500/assets/map_activo.jpg" && avisado == false) {
        popUpDos.className = "show";
        avisado = true;
    }
});

mapa.addEventListener("dblclick", () => {
    if (mapa.src == "http://127.0.0.1:5500/assets/map_calor_4.jpg") {
        mapa.src = "http://127.0.0.1:5500/assets/map_calor_0.jpg";
        avisado = false;
    }

    if (mapa.src == "http://127.0.0.1:5500/assets/map_activo.jpg") {
        mapa.src = "http://127.0.0.1:5500/assets/map_map.jpg";
        avisado = false;
    }
});

function showNotification() {
    if (window.Notification) {
        Notification.requestPermission(function (status) {
            console.log('Status: ', status); // show notification permission if permission granted then show otherwise message will not show

            var options = {
                body: 'La ruta de evacuación que debe seguir es la siguiente', // body part of the notification
                dir: 'ltr', // use for derection of message
                image: '../assets/ruta_evacuacion.jpg' // use for show image
            }

            var n = new Notification('En estos momentos se esta presentando un incendio en el edificio L', options);
        });
    } else {
        alert('Your browser doesn\'t support notifications.');
    }
}

let fb = document.getElementById("fb");

function fbActive() {
    fb.className = active;
}

omitirBtn.addEventListener("click", () => {
    popUp.className = "hidden";
});
omitirBtnDos.addEventListener("click", () => {
    popUpDos.className = "hidden";
    mapa.src = "http://127.0.0.1:5500/assets/map_map.jpg";
});

crearEmergenciaBtn.addEventListener("click", () => {
    popUp.className = "hidden";
    mapa.src = "http://127.0.0.1:5500/assets/map_calor_4.jpg";
});

crearEmergenciaBtnDos.addEventListener("click", () => {
    popUpDos.className = "hidden";
    mapa.src = "http://127.0.0.1:5500/assets/map_activo.jpg";
});
// Numero de Estudiantes
let cantEstud = document.getElementById("cantEstudiantes");
setInterval(() => {
    cantEstud.innerText = Math.floor(Math.random() * 50) + 3500;
}, 5000);

//NUmero de Personal
let cantPersonal = document.getElementById("cantPersonal");
setInterval(() => {
    cantPersonal.innerText = Math.floor(Math.random() * 19) + 420;
}, 12900);

//Numero de Salones
let salonesOcup = document.getElementById("salonesOcup");
setInterval(() => {
    salonesOcup.innerText = Math.floor(Math.random() * 7) + 90;
}, 23800);

let hora = document.querySelector("#profileBar .hora");

setInterval(() => {
    var today = new Date();
    var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds() + " a. m.";
    if (today.getSeconds() <= 9 && today.getMinutes() <= 9) {
        time = today.getHours() + ":0" + today.getMinutes() + ":0" + today.getSeconds() + " a. m.";
    } else if (today.getSeconds() > 9 && today.getMinutes() <= 9) {
        time = today.getHours() + ":0" + today.getMinutes() + ":" + today.getSeconds() + " a. m.";
    } else if (today.getSeconds() <= 9 && today.getMinutes() > 9) {
        time = today.getHours() + ":" + today.getMinutes() + ":0" + today.getSeconds() + " a. m.";
    } else if (today.getSeconds() > 9 && today.getMinutes() > 9) {
        time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds() + " a. m.";
    }
    hora.innerText = time;
}, 1000);

let sensors = [...document.querySelectorAll("#dashboard .sensors article")]

sensors.forEach((sensor, index) => {
    sensor.addEventListener('click', () =>{
        console.log(sensor);
        for (let i = 0; i < sensors.length; i++) {
            const other = sensors[i];
            other.className = "";
        }
        sensor.className = "selected"
        if(index == 0) {
            mapa.src = "http://127.0.0.1:5500/assets/map_map.jpg";
        }
        if(index == 1) {
            mapa.src = "http://127.0.0.1:5500/assets/map_calor_0.jpg";
        }
    });
});