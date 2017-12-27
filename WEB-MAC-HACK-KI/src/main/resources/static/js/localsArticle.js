/**
 * Created by Woohyeon on 2017. 12. 27..
 */
class LocalsArticle {
  constructor() {
    this.init();
  }

  init() {
    document.querySelector('.delete-button').addEventListener('click', e => {
      this.deleteArticle();
    });
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