/**
 * Created by Woohyeon on 2017. 12. 20..
 */
class EditProfile {
  constructor() {
    this.init();
  }

  init() {
    document.querySelector('.save').addEventListener('click', e => {
      this.editProfile(e);
    });
    this.profileUserId = document.querySelector('.top-info-zone').getAttribute('data-id');
    this.initNationSelect();
  }

  initNationSelect() {
    let nationCode = document.querySelector('.nation-select').getAttribute('data-id');
    let options = document.querySelectorAll('select.nation-select option');
    for(let i = 0 ; i < options.length ; i++) {
      if(options[i].getAttribute('value') === nationCode) {
        options[i].setAttribute('selected', '');
      }
    }
  }

  editProfile() {
    let city = document.querySelector('.city-input').value;
    let nationality = document.querySelector('.nation-select').value;
    let birthday = document.querySelector('.birthday-input').value;
    let gender = document.querySelector('.gender-select').value;
    let contents = CKEDITOR.instances["editor1"].getData();

    let userInformation = {
      'city': city,
      'nationality': nationality,
      'birthday': birthday,
      'contents': contents
    }

    fetch('/users/' + this.profileUserId, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'dataType': 'json',
        'Content-Type': 'application/json;charset=UTF-8'
      },
      body: JSON.stringify(userInformation)
    }).then(res => {
      return res.json();
    }).then(json => {
      console.log(json);
      // location.replace('/users/profile/' + this.profileUserId);
    });
  }
}

new EditProfile();
