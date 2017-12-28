/**
 * Created by Woohyeon on 2017. 12. 27..
 */
class LocalsArticle {
  constructor() {
    this.init();
  }

  init() {
    this.starButton = document.querySelector('.star-button');
    this.userInfo = document.querySelector('.user-info');
    this.userId = this.userInfo.getAttribute('data-id');
    this.writerId = document.querySelector('article').getAttribute('data-id');

    this.initStarStatus();
    document.querySelector('.delete-button').addEventListener('click', e => {
      this.deleteArticle();
    });
    this.starButton.addEventListener('click', e => {
      this.star();
    });
  }

  star() {
    let star;
    if(this.starButton.classList.contains('active')) {
      this.starButton.classList.remove('active');
      star = false;
    } else {
      this.starButton.classList.add('active');
      star = true;
    }

    let toStarInfo = {
      toId: this.writerId,
      star: star,
    }

    fetch('/users/stars', {
      method: 'POST',
      credentials: 'same-origin',
      headers: {
        'Accept': 'application/json',
        'dataType': 'json',
        'Content-Type': 'application/json;charset=UTF-8'
      },
      body: JSON.stringify(toStarInfo)
    }).then(res => {
      if(res == null) {
        return;
      }
      return res.json();
    }).then(json => {
      console.log(json);
    });
  }

  initStarStatus() {
    if(document.querySelector('.user-info')) {
      let starsFrom = document.querySelectorAll('.stars-from');
      for(let i = 0 ; i < starsFrom.length ; i++) {
        if(starsFrom[i].innerText === this.userId) {
          this.starButton.classList.add('active');
        }
      }
    }
  }

  deleteArticle() {
    let articleId = document.querySelector('.delete-button').getAttribute('data-id');
    fetch('/articles/locals/' + articleId, {
      method: 'DELETE',
      credentials: 'same-origin',
      headers: {
        'Accept': 'application/json',
        'dataType': 'json',
        'Content-Type': 'application/json;charset=UTF-8'
      }
    }).then(res => {
      if(res.status !== 200) {
        throw Error(res);
      }
      return res.json();
    }).then(json => {
      let location = '/';
      console.log(location);
      window.location.replace(location);
    });
  }
}

new LocalsArticle();