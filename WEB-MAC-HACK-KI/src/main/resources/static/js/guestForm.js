/**
 * Created by Naver on 2017. 10. 17..
 */
class GuestForm {
  constructor() {
    this.init();
    this.nowModal;
    this.errorMessage;
    this.regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
  }

  init() {
    document.querySelector('#submit-btn').addEventListener('click', e => {
      this.postForm(e);
    });
    document.querySelector('#showVieo').addEventListener('click', e => {
      this.showVideo(e);
    });
    document.querySelector('.mdl-layout__obfuscator').addEventListener('click', e => {
      this.modalOff(e);
    });
    this.setCalendar();
  }

  showVideo(e) {
    e.preventDefault();
    this.nowModal = document.querySelector('.video-modal');
    document.querySelector('.mdl-layout__obfuscator').classList.add('is-visible');
    document.querySelector('.video-modal').classList.add('is-visible');
  }

  modalOff(e) {
    if (this.nowModal === document.querySelector('.post-result') || this.nowModal === document.querySelector('.loading-modal')) {
      console.log('Please submit and click button');
    } else {
      this.nowModal.classList.remove('is-visible');
      document.querySelector('.mdl-layout__obfuscator').classList.remove('is-visible');
      this.nowModal = null;
    }
  }

  postForm(e) {
    e.preventDefault();
    if(!this.formIsCorrect()) {
      return;
    }

    const guestData = {
      'name' : document.querySelector('#name').value,
      'email' : document.querySelector('#email').value,
      'age' : document.querySelector('#age').value,
      'gender' : document.querySelector('input[name="gender"]:checked')?
        document.querySelector('input[name="gender"]:checked').dataset.id : 'X',
      'language' : document.querySelector('#language').value,
      'date' : document.querySelector('#date').value,
      'theme' : document.querySelector('#theme').value,
      'attraction' : document.querySelector('#attraction').value
    };

    this.nowModal = document.querySelector('.loading-modal');
    document.querySelector('.mdl-layout__obfuscator').classList.add('is-visible');
    document.querySelector('.loading-modal').classList.add('is-visible');

    fetch('/party/guest', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(guestData)
    }).then(res => {
      return res.json();
    }).then(json => {
      this.nowModal = document.querySelector('.post-result');
      document.querySelector('.loading-modal.is-visible').classList.remove('is-visible');
      document.querySelector('.post-result').classList.add('is-visible');
      console.log('res: ', json);
    });
  }

  formIsCorrect() {
    let errorInput;
    let correct = true;
    if(document.querySelector('#name').value === '') {
      errorInput = document.querySelector('#name');
      correct = false;
    } else if(!this.regex.test(document.querySelector('#email').value)) {
      errorInput = document.querySelector('#email');
      correct = false;
    } else if(document.querySelector('#date').value === '') {
      errorInput = document.querySelector('#date');
      correct = false;
    }
    if(!correct) {
      errorInput.closest('div').classList.add('is-invalid');
      errorInput.focus();
    }
    return correct;
  }

  setCalendar() {
    const nowDay = new Date();
    const year = nowDay.getFullYear();
    const month = nowDay.getMonth() + 1;
    const date = nowDay.getDate();
    document.querySelector('#date').setAttribute('min', year + '-' + month  + '-' + date);
  }
}

document.addEventListener('DOMContentLoaded', (e) => {
  console.log('DOM fully loaded and parsed');
  new GuestForm();
});
