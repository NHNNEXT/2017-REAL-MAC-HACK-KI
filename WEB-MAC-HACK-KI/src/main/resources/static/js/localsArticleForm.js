/**
 * Created by Woohyeon on 2017. 12. 27..
 */
class LocalsArticle {
  constructor() {
    this.init();
  }

  init() {
    document.querySelector('.post-locals-article').addEventListener('click', e => {
      e.preventDefault();
      this.postLocalsArticle();
    });
  }

  postLocalsArticle() {
    let title = document.querySelector('.title-input').value;
    let location = document.querySelector('.search-city-input').value;
    let contents = document.querySelector('.contents-input').value;

    let article = {
      'title': title,
      'location': location,
      'contents': contents
    }

    fetch('/articles/locals', {
      method: 'POST',
      credentials: 'same-origin',
      headers: {
        'Accept': 'application/json',
        'dataType': 'json',
        'Content-Type': 'application/json;charset=UTF-8'
      },
      body: JSON.stringify(article)
    }).then(res => {
      if(res.status !== 201) {
        throw Error(res);
      }
      return res.json();
    }).then(json => {
      let location = '/articles/localsDetail/' + json.id;
      console.log(location);
      window.location.replace(location);
    });
  }
}

new LocalsArticle();