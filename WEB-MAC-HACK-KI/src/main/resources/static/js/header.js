/**
 * Created by Woohyeon on 2017. 12. 13..
 */
class Header {
  constructor() {
    this.init();
  }

  init() {
    this.header = document.querySelector('header');
    this.loginModal = document.querySelector('.bq-modal-signin');
    this.signupModal = document.querySelector('.bq-modal-signup');
    this.closeBtn = document.querySelector('.modal-close-button');
    this.signupBtn = document.querySelector('.signup-btn');
    this.loginBtn = document.querySelector('.login-btn');
    this.signupLink = document.querySelector('.link-to-signup-btn');
    this.loginLink = document.querySelector('.link-to-signin');

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