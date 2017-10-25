/**
 * Created by Naver on 2017. 10. 17..
 */
class GuestForm {
  constructor() {
    this.init();
    this.nowModal;
  }

  init() {
    document.querySelector('.mdl-button__ripple-container').addEventListener('click', e => {
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
    const guestData = {
      'name' : document.querySelector('#name').value,
      'email' : document.querySelector('#email').value,
      'age' : document.querySelector('#age').value,
      'gender' : document.querySelector('input[name="gender"]:checked').dataset.id,
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
  setTimeout(() => {
    new GuestForm();
    }, 1000);
});
