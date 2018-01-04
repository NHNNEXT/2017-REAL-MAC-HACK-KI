class Chatting {
  constructor() {
    this.init();
  }

  init() {
    this.chatListBtn = document.querySelector('.chatlist-button');
    this.chatList = document.querySelector('.chat-container');
    this.chatRoomList = document.querySelector('.chat-list');
    this.chatCloseBtn = document.querySelector('.chat-close-button');
    this.chatRoom = document.querySelector('.chat-room');
    this.messageArea = document.querySelector('.message-area');
    this.chatInput = document.querySelector('.chat-input-text');
    this.myUserId = document.querySelector('.session-info.user-info').dataset.id;
    this.myUserName = document.querySelector('.session-info.user-info').innerHTML;
    this.sendBtn = document.querySelector('.chat-send-icon');
    this.profileBtn = document.querySelector('.profile-button');
    this.currentRoomId;
    this.currentPartnerId;
    this.currentRoomMsgCount;
    this.currentFBRef;

    this.myMessageTemplate = Handlebars.templates['precompiles/myMessage'];
    this.partnerMessageTemplate = Handlebars.templates['precompiles/partnerMessage'];
    this.chatRoomTemplate = Handlebars.templates['precompiles/chatRoom'];

    this.database = firebase.database();

    this.profileBtn.addEventListener('click', () => {window.location.replace("/users/profile/" + this.myUserId)})
    this.chatListBtn.addEventListener('click', () => {this.chatList.classList.toggle('fade-in')});
    this.chatCloseBtn.addEventListener('click', function() {
      this.chatRoom.classList.remove('fade-in');
      this.currentFBRef.off()
    }.bind(this));
    this.chatList.addEventListener('click', function(e) {
      this.selectChatRoom(e);
    }.bind(this));
    this.chatInput.addEventListener('keyup', this.addMyMessage.bind(this));
    this.sendBtn.addEventListener('click', this.addMyMessage.bind(this));


    this.setProfileImgUrl(this.myUserId);
    this.getChatRooms();
  }

  setProfileImgUrl(myId) {
    console.log("myId : " + myId);
    if (myId != null) {
      this.profileBtn.style.backgroundImage = "url('/uploads/avatars/" + this.myUserId + "')"; // set fallback when user doesn't have profile image
    }
  }

  addMyMessage(e) {

    if (this.chatInput.value != "" && (e.keyCode == 13 || e.type == 'click')) {
      let time = new Date();
      time = time.toLocaleString('en-US', { hour12: true, hour: 'numeric', minute: 'numeric'});
      let data = {
        "time": time,
        "message": this.chatInput.value
      }
      let newMessage = this.myMessageTemplate(data);
      let newDiv = document.createElement('div');
      newDiv.innerHTML = newMessage;
      this.messageArea.appendChild(newDiv);

      this.sendMessage(this.currentRoomId, this.chatInput.value);

      this.chatInput.value = "";
      this.syncScroll(this.messageArea);
    }
  }

  sendMessage(roomId, message) {
    this.database.ref('messages/chatroom-' + roomId + '/m' + (++this.currentRoomMsgCount)).set({
      message: message,
      name: this.myUserName,
      timestamp: Date.now()
    });
  }

  addPartnerMessage(res) {
    let time = new Date(res.timestamp);
    time = time.toLocaleString('en-US', { hour12: true, hour: 'numeric', minute: 'numeric'});
    let data = {
      "time": time,
      "message": res.message,
      "name": res.name,
      "partnerId": this.currentPartnerId
    }
    let newMessage = this.partnerMessageTemplate(data);
    let newDiv = document.createElement('div');
    newDiv.innerHTML = newMessage;
    this.messageArea.appendChild(newDiv);

    this.syncScroll(this.messageArea);
  }

  getMessages(roomId, partnerName, partnerId) {
    this.messageArea.innerHTML = "";
    this.database.ref('messages/chatroom-' + roomId).once('value')
      .then(snapshot=>snapshot.val())
      .then(function(res) {
        console.log(res);
        for (let i = 1; i <= Object.keys(res).length; i++) {
          this.currentRoomMsgCount = Object.keys(res).length;
          let msg = res['m' + i];
          if (msg.name === partnerName) {
            let time = new Date(msg.timestamp);
            time = time.toLocaleString('en-US', { hour12: true, hour: 'numeric', minute: 'numeric'});
            let data = {
              "partnerId": partnerId,
              "name": partnerName,
              "message": msg.message,
              "time": time
            }
            let newMessage = this.partnerMessageTemplate(data);
            let newDiv = document.createElement('div');
            newDiv.innerHTML = newMessage;
            this.messageArea.appendChild(newDiv);
          } else {
            let time = new Date(msg.timestamp);
            time = time.toLocaleString('en-US', { hour12: true, hour: 'numeric', minute: 'numeric'});
            let data = {
              "time": time,
              "message": msg.message,
              "name": msg.name
            }
            let newMessage = this.myMessageTemplate(data);
            let newDiv = document.createElement('div');
            newDiv.innerHTML = newMessage;
            this.messageArea.appendChild(newDiv);
          }
        }
      }.bind(this))
      .then(function() {
        this.setRefWatcher(this.currentRoomId);
      }.bind(this));

    this.syncScroll(this.messageArea);
  }

  getChatRooms() {
    let url = "/chat/rooms/" + this.myUserId;
    fetch(url, {
      method: 'get',
      credentials: 'same-origin'
    }).then(res=>res.json())
      .then(function(res) {
        for (let chatroom of res) {
          let roomId = chatroom.id;
          let partner;
          let lastMessage;
          let time;

          for (let user of chatroom.users) {
            if (user.id != this.myUserId) {
              partner = user;
            }
          }

          this.database.ref('chats/chatroom-' + chatroom.id).once('value').then(function(snapshot) {
            let result = snapshot.val();
            lastMessage = result.lastMessage;
            time = new Date(result.timestamp);
            time = time.toLocaleString('en-US', { hour12: true, hour: 'numeric', minute: 'numeric'});
          }).then(function() {
            let data = {
              "partnerId": partner.id,
              "partnerName": partner.name,
              "lastMessage": lastMessage.substring(0, 20) + "...",
              "time": time,
              "chatroomId": chatroom.id
            }

            let newChatRoom = this.chatRoomTemplate(data);
            let newList = document.createElement('li');
            newList.innerHTML = newChatRoom;
            this.chatRoomList.appendChild(newList);
          }.bind(this));

        }
      }.bind(this));
  }

  setRefWatcher(roomId) {
    this.currentFBRef = this.database.ref('messages/chatroom-' + this.currentRoomId + '/');
    this.currentFBRef.on('value', function(snapshot) {
      console.log("current room msg count : " + this.currentRoomMsgCount);
      console.log(snapshot.val());
      if (snapshot.val()['m' + (this.currentRoomMsgCount + 1)] != null) { // 이벤트를 붙이는 시점에도 이벤트가 1번 호출되는 게 좀 문제가 되는데.. 해결 방법 없나?
        this.currentRoomMsgCount++;
        this.addPartnerMessage(snapshot.val()['m' + this.currentRoomMsgCount]);
      }
    }.bind(this));
  }

  selectChatRoom(e) {
    let selected = document.querySelector('.chatroom-selected');
    let clickedRoom = e.target.closest('li');

    if (clickedRoom.classList.contains('chatroom-selected')) {
      this.chatRoom.classList.add('fade-in');
      let partnerName = clickedRoom.querySelector('.chat-user-name').innerHTML;
      console.log(partnerName);
      this.chatRoom.querySelector('.chat-partner-name').innerHTML = partnerName;
      this.chatList.classList.remove('fade-in');
      this.getMessages(clickedRoom.querySelector('.chat-contents-container').dataset.chatroomId, partnerName,
                        clickedRoom.querySelector('.chat-contents-container').dataset.partnerId);
      this.currentRoomId = clickedRoom.querySelector('.chat-contents-container').dataset.chatroomId;
      this.currentPartnerId = clickedRoom.querySelector('.chat-contents-container').dataset.partnerId;
    } else {
      e.target.closest('li').classList.add('chatroom-selected');
    }

    if (selected !== null) {
      selected.classList.remove('chatroom-selected');
    }
  }

  syncScroll(e) {
    e.scrollTop = e.scrollHeight;
  }
}

new Chatting();