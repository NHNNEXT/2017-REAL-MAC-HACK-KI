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