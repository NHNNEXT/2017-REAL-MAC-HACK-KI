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
    document.querySelector('.button.cancel-post-article').addEventListener('click', e => {
      e.preventDefault();
      window.location.replace('/');
    });
    document.querySelector('ul.select-theme').addEventListener('click', e => {
      if(e.target.tagName === 'LI') {
        this.toggleTheme(e);
      }

    });
  }

  toggleTheme(e) {
    if(e.target.classList.contains('is-checked')) {
      e.target.classList.remove('is-checked');
    } else {
      e.target.classList.add('is-checked');
    }
  }

  postLocalsArticle() {
    let title = document.querySelector('.title-input').value;
    let location = document.querySelector('#location').value;
    let contents = CKEDITOR.instances["editor1"].getData();
    let checkedElements = document.querySelectorAll('li.is-checked');

    let themes = [];

    checkedElements.forEach(element => {
      themes.push(element.getAttribute('data-id'));
    });

    let article = {
        'title': title,
        'location': location,
        'contents': contents
    };

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
      console.log(themes);
      return fetch('/articles/locals/' + json.id + '/themes', {
        method: 'PUT',
        credentials: 'same-origin',
        headers: {
          'Accept': 'application/json',
          'dataType': 'json',
          'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
          'themes': themes
        })
      });
    }).then(res => {
      return res.json();
    }).then(json => {
      let location = '/articles/localsDetail/' + json.id;
      console.log(location);
      window.location.replace(location);
    });
  }
}

new LocalsArticle();
