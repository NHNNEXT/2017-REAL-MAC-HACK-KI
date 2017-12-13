/**
 * Created by Woohyeon on 2017. 12. 13..
 */
class ListFilter {
  constructor() {
    this.init();
  }

  init() {
    this.subject = 'Locals';
    this.themes = [];
    document.querySelector('.subject-filter').addEventListener('click', e => {
      if(e.target.tagName === 'LI') {
        this.changeSubject(e);
      }
    });
    document.querySelector('.theme-filter').addEventListener('click', e => {
      if(e.target.tagName === 'LI') {
        this.switchTheme(e);
      }
    });
  }

  changeSubject(e) {
    if(!e.target.classList.contains('is-checked')) {
      document.querySelector('.subject-filter .is-checked').classList.remove('is-checked');
      e.target.classList.add('is-checked');
      this.subject = e.target.innerText;
    }
  }

  switchTheme(e) {
    if(e.target.classList.contains('is-checked')) {
      e.target.classList.remove('is-checked');
      this.themes.splice(this.themes.indexOf(e.target.innerText), 1);
    } else {
      e.target.classList.add('is-checked');
      this.themes.push(e.target.innerText);
    }
    console.log(this.themes);
  }
}

new ListFilter();