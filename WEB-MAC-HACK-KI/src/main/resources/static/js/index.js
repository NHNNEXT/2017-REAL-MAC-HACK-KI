/**
 * Created by Naver on 2017. 10. 17..
 */
class GuestFormat {
  constructor() {
    this.init();
    let nowGender;

  }

  init() {
    document.querySelector('#genderInput').addEventListener('click', (e) => {
      this.selectGender(e);
    });
  }

  selectGender(e) {
    console.log(e.target.tagName);
    if(e.target.tagName == 'INPUT') {
      console.log(e.target);
    }
  }
}

new GuestFormat();