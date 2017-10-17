/**
 * Created by Naver on 2017. 10. 17..
 */
class GuestForm {
  constructor() {
    this.init();
    let nowGender;
  }

  init() {
    console.log(document.querySelector('.mdl-button__ripple-container'));
    document.querySelector('.mdl-button__ripple-container').addEventListener('click', (e) => {
      this.postForm(e);
    });
  }

  postForm(e) {
    e.preventDefault();
    console.log(e.target);
    const guestData = {
      'name' : document.querySelector('#name').value,
      'email' : document.querySelector('#email').value,
      'age' : document.querySelector('#age').value,
      'gender' : document.querySelector('input[name="gender"]:checked').dataset.id,
      'nation' : document.querySelector('#nation').value,
      'theme' : document.querySelector('#theme').value,
      'attraction' : document.querySelector('#attraction').value
    }

    console.log(guestData);

    fetch('/party/guest', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(guestData)
    }).then(res => {
      console.log('send success');
      return res.json();
    }).then(json => {
      console.log('res: ', json);
    });
  }
}

document.addEventListener("DOMContentLoaded", (e) => {
  console.log("DOM fully loaded and parsed");
  setTimeout(() => {
    new GuestForm();
    }, 300);
});
