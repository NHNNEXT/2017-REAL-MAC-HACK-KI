/**
 * Created by Woohyeon on 2017. 12. 20..
 */
class Profile {
  constructor() {
    this.selected = document.querySelector('.selected');
    this.showing = document.querySelector('.contents-zone.is-visible');
    this.profileImageInput = document.querySelector('input.profile-image-input');
    this.init();
  }



  init() {
    document.querySelector('.profile-image').addEventListener('click', e => {
      this.profileImageInput.click();
    });

    this.profileImageInput.addEventListener('change', e => {
      this.changeProfileImage(e);
    });

    document.querySelector('table').addEventListener('click', e => {
      console.log(e.target.tagName);
      if(e.target.tagName === 'TH') {
        this.select(e.target);
      }
    });
  }

  select(selected) {
    if(selected !== this.selected) {
      this.selected.classList.remove('selected');
      selected.classList.add('selected');
      this.selected = selected;
      this.changeContents(selected);
    }
  }

  changeContents(selected) {
    this.showing.classList.remove('is-visible');
    let toShow;
    if(selected.classList.contains('photos-button')) {
      toShow = document.querySelector('.contents-zone.photos-contents');
    } else if(selected.classList.contains('about-button')) {
      toShow = document.querySelector('.contents-zone.about-contents');
    } else if(selected.classList.contains('reviews-button')) {
      toShow = document.querySelector('.contents-zone.reviews-contents');
    }
    toShow.classList.add('is-visible');
    this.showing = toShow;
  }

  changeProfileImage(e) {
    let preview = document.querySelector('.profile-image');
    let file = e.target.files[0];
    let reader = new FileReader();

    reader.addEventListener('load', () => {
      preview.style.backgroundImage = 'url(' + reader.result + ')';
      console.log(preview.style.backgroundImage);
    }, false);

    if(file) {
      reader.readAsDataURL(file);
    }
  }
}

new Profile();
