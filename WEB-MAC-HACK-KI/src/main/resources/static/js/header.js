window.onload = () => {
    init();
}

function init() {
    let dimmer = document.querySelector("#dimmer");
    let loginBtn = document.querySelector(".login-button");
    let signupBtn = document.querySelector(".signup-button");

    loginBtn.addEventListener("click", () => {
        dimmer.style.display = "block";
        document.querySelector("#sign_in_modal").style.display = "block";
    });

    dimmer.addEventListener("click", () => {
        dimmer.style.display = "none";
        offModal();
    })

    function offModal() {
        let modals = document.querySelectorAll(".my_modal");
        for (modal of modals) {
            modal.style.display = "none";
        }
    }
}

/**
 * Created by Woohyeon on 2017. 12. 13..
 */
class Header {
  constructor() {
    this.init();
  }

  init() {
    this.header = document.querySelector('header');
    if(document.querySelector('title').innerText === 'Amigo') {
      this.header.querySelector('.header-search-city-input').classList.remove('is-visible');
    }

    window.addEventListener('scroll', e => {
      this.showHeaderUnderline();
    });
  }

  showHeaderUnderline() {
    if(window.scrollY > 20 && !this.header.classList.contains('show-underline')) {
      this.header.classList.add('show-underline');
    } else if(window.scrollY < 21) {
      this.header.classList.remove('show-underline');
    }
  }
}

new Header();