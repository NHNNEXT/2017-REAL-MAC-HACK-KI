/**
 * Created by Naver on 2017. 10. 17..
 */
class GuestForm {
  constructor() {
    this.init();
    let nowGender;
  }

  init() {
    document.querySelector('.mdl-button__ripple-container').addEventListener('click', (e) => {
      this.postForm(e);
    });
    this.setCalendar();
  }

  postForm(e) {
    e.preventDefault();
    console.log(e.target);
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

    document.querySelector('.mdl-layout__obfuscator').classList.add("is-visible");
    document.querySelector('.submit-success').classList.add("is-visible");

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
      document.querySelector('.submit-success.is-visible').innerHTML = '<h4>Thank you for using. <br>' +
        'Your request was successfully sent! <br>' +
        'Please check your email.</h4>';

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

document.addEventListener("DOMContentLoaded", (e) => {
  console.log("DOM fully loaded and parsed");
  setTimeout(() => {
    new GuestForm();
    }, 300);
});
