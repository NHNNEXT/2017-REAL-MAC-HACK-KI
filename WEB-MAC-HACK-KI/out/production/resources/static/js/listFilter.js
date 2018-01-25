/**
 * Created by Woohyeon on 2017. 12. 13..
 */
class ListFilter {
  constructor() {
    this.init();
  }

  init() {
    this.subject = 'Locals';
    this.themes = ['Art & Culture', 'Nature', 'Healing', 'Food & Drink', 'Sports', 'Music', 'Nightlife', 'Concert'];

    this.localArticleList = document.querySelectorAll('.local-article');

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
    this.filtering();
  }

  filtering() {
    for(let i = 0 ; i < this.localArticleList.length ; i++) {
      if(this.canShow(this.localArticleList[i])) {
        this.localArticleList[i].classList.add('is-visible');
      } else {
        this.localArticleList[i].classList.remove('is-visible');
      }
    }
  }

  canShow(article) {
    for(let i = 0 ; i < this.themes.length ; i++) {
      if(article.classList.contains(this.themes[i])) {
        return true;
      }
    }
    return false;
  }
}

new ListFilter();