var ws;

function connect() {
    var username = document.getElementById("username").value;
    var chatRoom = document.getElementById("chatRoom").value;
    var wsserver = document.getElementById("wsserver").value;
    var url = wsserver + chatRoom + "/" + username;

    ws = new WebSocket(url);

    ws.onmessage = function(event) {
        console.log(event.data);

        var log = document.getElementById("log");
        log.innerHTML += "message from server: " + event.data + "\n";
    };

    ws.onopen = function(event) {
        var log = document.getElementById("log");
        log.innerHTML += "Connected to " + event.currentTarget.url + "\n";
    };

    ws.onerror = function(event) {
        console.error("WebSocket Error: ", event);
    };

    ws.onclose = function(event) {
        if (event.wasClean) {
            console.log(`Closed cleanly, code=${event.code}, reason=${event.reason}`);
        } else {
            console.error("Connection died");
        }
    };
}

function send() {
    var content = document.getElementById("msg").value;
    ws.send(content);
}