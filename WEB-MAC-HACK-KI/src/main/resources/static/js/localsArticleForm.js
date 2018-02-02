/**
 * Created by Woohyeon on 2017. 12. 27..
 */
class LocalsArticle {
  constructor() {
    this.init();
  }

  init() {
    this.fileReader = new FileReader();

    this.photoInputTemplate = document.querySelector('#photo-template').innerHTML;
    this.photos = document.querySelector('#photos');

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

    this.photos.addEventListener('click', e => {
      if(e.target.classList.contains('add-photo-input')) {
        e.preventDefault();
        this.addPhotoInput(e);
      } else if(e.target.classList.contains('remove-photo-input')) {
        e.preventDefault();
        this.removePhotoInput(e);
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
      let photos = document.querySelectorAll('.photo input');
      let photoCount = photos.length;
      for(let i in photos) {
        let file = photos[i].files[0];

        if(!file) {
          return;
        }

        let formData = new FormData();
        formData.append('file', file);
        fetch('/uploads/photos/' + json.id, {
          method: 'POST',
          credentials: 'same-origin',
          body: formData
        }).then(res => {
          return res.json();
        }).then(res => {
          if(--photoCount === 0) {

          }
          let location = '/articles/localsDetail/' + json.id;
          console.log(location);
          window.location.replace(location);
        });
      }
    });
  }

  addPhotoInput(e) {
    let li = document.createElement('li');
    li.innerHTML = this.photoInputTemplate;
    li.classList.add('photo');
    e.target.closest('UL').insertBefore(li, e.target.closest('LI').nextSibling);
  }

  removePhotoInput(e) {
    if(document.querySelectorAll('.photo').length === 1) {
      return;
    }
    e.target.closest('LI').remove();
  }
}

new LocalsArticle();
