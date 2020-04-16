<#ftl encoding='UTF-8'>
<#include "base.ftl"/>
<#macro content>
    <head>
        <script
                src="https://code.jquery.com/jquery-3.4.1.js"
                integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
                crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
        <script>
            var webSocket;

            function connect() {
                webSocket = new SockJS("http://localhost:8080/socket");

                webSocket.onmessage = function receiveMessage(response) {
                    let data = response['data'];
                    let json = JSON.parse(data);
                    $('#messageList').append("<strong>" + json['sender'] + ": </strong>" + json['text'] + "<br>")
                };

                webSocket.onerror = function errorShow() {
                    alert('Ошибка авторизации')
                }
            }

            function sendMessage(text, sender, receiver, pageId) {
                let message = {
                    'text': text,
                    'sender': sender,
                    'receiver': receiver,
                    'pageId': pageId
                };
                webSocket.send(JSON.stringify(message));
            }

        </script>

        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Chat</title>
    </head>


    <body onload="connect()">
    <div>
        <label for="message">Текст сообщения</label>
        <input name="message" id="message" placeholder="Message">
        <button onclick="sendMessage($('#message').val(),
        '${user.login}', '${receiver}', '${pageId}')">Send</button>
    </div>
    <br>

    <div>
        <h3> Messages </h3>

        <div id="messageList">
            <#list messages as message>
                <strong> ${message.sender}: </strong> ${message.text} <br>
            </#list>
        </div>
    </div>
    </body>


</#macro>
<@main/>