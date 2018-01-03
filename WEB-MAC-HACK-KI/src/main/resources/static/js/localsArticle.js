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
    this.writerId = document.querySelector('article').getAttribute('data-id');
    this.bigPictureModal = document.querySelector('.big-picture');

    if(this.userInfo !== null) {
      this.userId = this.userInfo.getAttribute('data-id');
      this.starCount = document.querySelector('.star-count');
      this.initStarStatus();
      document.querySelector('.delete-button').addEventListener('click', e => {
        this.deleteArticle();
      });
    }

    this.starButton.addEventListener('click', e => {
      this.star();
    });
    
    document.querySelector('.photos-zone').addEventListener('click', e => {
      this.bigPicture(e);
    });

    document.querySelector('.close-modal').addEventListener('click', e => {
      this.bigPictureModal.classList.remove('is-visible');
    });
  }

  star() {
    let star;
    if(this.starButton.classList.contains('active')) {
      this.starButton.classList.remove('active');
      star = false;
      this.starCount.innerText = parseInt(this.starCount.innerText) - 1;
    } else {
      this.starButton.classList.add('active');
      star = true;
      this.starCount.innerText = parseInt(this.starCount.innerText) + 1;
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

  bigPicture(e) {
    let clicked = e.target;
    if(clicked.tagName === 'IMG') {
      console.log('BIG PICTURE');
    }
    let imageUrl = clicked.getAttribute('src');
    this.bigPictureModal.style.backgroundImage = `url(${imageUrl})`
    this.bigPictureModal.classList.add('is-visible');
  }
}

new LocalsArticle();