/**
 * Created by Woohyeon on 2017. 12. 20..
 */
class Profile {
  constructor() {
    this.selected = document.querySelector('.selected');
    this.showing = document.querySelector('.contents-zone.is-visible');
    this.profileImageInput = document.querySelector('input.profile-image-input');
    this.profileImage = document.querySelector('.profile-image');
    this.profileUserId = this.profileImage.dataset.userId;
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
      if(e.target.tagName === 'TH') {
        this.select(e.target);
      }
    });

    this.setImageUrl();
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

    // this.sendImage("/uploads/userphotos/" + this.profileUserId, this.profileImageInput.files[0]);
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

    this.sendImage("/uploads/avatars/" + this.profileUserId, this.profileImageInput.files[0]);
  }

  setImageUrl() {
    let imageName = this.profileImage.dataset.userId;
    if (imageName !== null) {
      this.profileImage.style.backgroundImage = "url('/uploads/avatars/" + imageName + "')";
    }
  }

  sendImage(url, data) {
    let formData = new FormData();

    for(let name in data) {
      console.log(name + " : " + data[name]);
      // formData.append(name, data[name]);
    }

    formData.append('file', data);

    fetch(url, {
      method: 'POST',
      credentials: 'same-origin',
      body: formData
    }).then(res=>res.json())
      .then(res => {
        console.log(res);
      })
  }
}

new Profile();
