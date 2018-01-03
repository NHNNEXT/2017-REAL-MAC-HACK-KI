class Chatting {
  constructor() {
    this.init();
  }

  init() {
    this.chatListBtn = document.querySelector('.chatlist-button');
    this.chatList = document.querySelector('.chat-container');
    this.chatCloseBtn = document.querySelector('.chat-close-button');
    this.chatRoom = document.querySelector('.chat-room');
    this.messageArea = document.querySelector('.message-area');
    this.chatInput = document.querySelector('.chat-input-text');

    this.myMessageTemplate = Handlebars.templates['precompiles/myMessage'];
    this.partnerMessageTemplate = Handlebars.templates['precompiles/partnerMessage'];


    this.chatListBtn.addEventListener('click', () => {this.chatList.classList.toggle('fade-in')});
    this.chatCloseBtn.addEventListener('click', () => {this.chatRoom.classList.remove('fade-in')});
    this.chatList.addEventListener('click', function(e) {
      this.selectChatRoom(e);
    }.bind(this));
    this.chatInput.addEventListener('keyup', this.addMyMessage.bind(this));
  }

  addMyMessage(e) {
    if (e.keyCode == 13 && this.chatInput.value !== null) {
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

      this.syncScroll(this.messageArea);
    }
  }

  addPartnerMessage(e) {
    if (e.keyCode == 13 && this.chatInput.value !== null) {
      let time = new Date();
      time = time.toLocaleString('en-US', { hour12: true, hour: 'numeric', minute: 'numeric'});
      let data = {
        "time": time,
        "message": this.chatInput.value,
        "name": "bbq9234"
      }
      let newMessage = this.partnerMessageTemplate(data);
      let newDiv = document.createElement('div');
      newDiv.innerHTML = newMessage;
      this.messageArea.appendChild(newDiv);
    }
  }

  selectChatRoom(e) {
    let selected = document.querySelector('.chatroom-selected');

    if (e.target.closest('li').classList.contains('chatroom-selected')) {
      this.chatRoom.classList.add('fade-in');
      this.chatList.classList.remove('fade-in');
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