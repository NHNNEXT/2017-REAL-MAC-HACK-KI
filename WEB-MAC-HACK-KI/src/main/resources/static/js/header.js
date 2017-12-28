/**
 * Created by Woohyeon on 2017. 12. 13..
 */
const serviceUrl = "http://localhost:8080/";
// const serviceUrl = "http://dev.amigotrip.co.kr/" // should be changed on release;
const emailregex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

class Header {
  constructor() {
    this.init();
  }

  init() {
    this.header = document.querySelector('header');
    this.loginModalBtn = document.querySelector('.login-button');
    this.signupModalBtn = document.querySelector('.signup-button');
    this.loginModal = document.querySelector('.bq-modal-signin');
    this.signupModal = document.querySelector('.bq-modal-signup');
    this.dimmer = document.querySelector('.dimmer');
    this.closeBtn = document.querySelectorAll('.modal-close-button');
    this.signupBtn = document.querySelector('.signup-btn');
    this.loginBtn = document.querySelector('.login-btn');
    this.signupLink = document.querySelector('.link-to-signup-btn');
    this.loginLink = document.querySelector('.link-to-signin');
    this.inputs = document.querySelectorAll('.bq-input');
    this.logoutBtn = document.querySelector('.logout-button');
    this.profileBtn = document.querySelector('.user-info');

    if (document.querySelector('title').innerText === 'Amigo') {
      this.header.querySelector('.header-search-city-input').classList.remove('is-visible');
    }

    window.addEventListener('scroll', e => {
      this.showHeaderUnderline();
    });

    if (this.loginModalBtn) this.loginModalBtn.addEventListener('click', this.addFadeIn.bind(this));
    if (this.signupModalBtn) this.signupModalBtn.addEventListener('click', this.addFadeIn.bind(this));
    if (this.logoutBtn) this.logoutBtn.addEventListener('click', () => {window.location.replace("/logout")});
    if (this.profileBtn) this.profileBtn.addEventListener('click', () => {window.location.replace("/users/profile/" + this.profileBtn.getAttribute('data-id'))});
    for (let i = 0; i < this.closeBtn.length; i++) {
      this.closeBtn[i].addEventListener('click', this.closeModal.bind(this));
    }
    this.signupLink.addEventListener('click', this.linkToSignup.bind(this));
    this.loginLink.addEventListener('click', this.linkToLogin.bind(this));
    this.loginBtn.addEventListener('click', this.tryLogin.bind(this));
    this.signupBtn.addEventListener('click', this.trySignup.bind(this));

    for (let input of this.inputs) {
      input.addEventListener('keyup', this.clearErrorMessage.bind(this));
    }

    this.timeout = null;
    document.querySelector("#login-email").onkeyup = this.bindWithDelay(this.checkEmailValid).bind(this);
    document.querySelector("#signup-email").onkeyup = this.bindWithDelay(this.checkEmailValid).bind(this);
    document.querySelector("#signup-password").onkeyup = this.bindWithDelay(this.checkPasswordConfirm).bind(this);
    document.querySelector("#signup-password-confirm").onkeyup = this.bindWithDelay(this.checkPasswordConfirm).bind(this);
    document.querySelector("#login-password").addEventListener('keyup', function(e) {
      this.disableButton(e);
      if (!this.checkInputNull(e)) {
        this.enableButton(e);
      }
    }.bind(this));
    document.querySelector("#signup-username").addEventListener('keyup', function(e) {
      this.disableButton(e);
      if (!this.checkInputNull(e)) {
        this.enableButton(e);
      }
    }.bind(this));
  }

  bindWithDelay(df) { // df for delayed function
    return function (e) {
      this.event = e;
      clearTimeout(this.timeout);

      this.timeout = setTimeout(df.bind(this), 200);
    }
  }

  showHeaderUnderline() {
    if (window.scrollY > 20 && !this.header.classList.contains('show-underline')) {
      this.header.classList.add('show-underline');
    } else if (window.scrollY < 21) {
      this.header.classList.remove('show-underline');
    }
  }

  addFadeIn(e) {
    this.dimmer.style.display = "block";
    if (e.target.classList.contains('login-button')) {
      this.loginModal.classList.add('fade-in');
    }
    if (e.target.classList.contains('signup-button')) {
      this.signupModal.classList.add('fade-in');
    }
  }

  linkToSignup(e) {
    this.loginModal.classList.remove('fade-in');
    this.signupModal.classList.add('fade-in');
  }

  linkToLogin(e) {
    if (e.target.tagName === "A") {
      this.signupModal.classList.remove('fade-in');
      this.loginModal.classList.add('fade-in');
    }
  }

  closeModal(e) {
    this.loginModal.classList.remove('fade-in');
    this.signupModal.classList.remove('fade-in');
    this.dimmer.style.display = "none";
  }

  tryLogin(e) {
    fetch("/users/login", {
      method: 'post',
      credentials: 'same-origin',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({email: document.querySelector("#login-email").value,
                            password: document.querySelector("#login-password").value})
    }).then(res=>res.json())
      .then(function(res) {
        if (res.id != null && res.id > 0) { // if login success
          window.location.replace(serviceUrl + "list");
        }

        if (res.message === "Email is wrong! Please check again.") {
          let loginEmailInput = document.querySelector("#login-email");
          this.clearForm(loginEmailInput);
          this.showErrorMessage(e, "There's no such email", loginEmailInput);
        }

        if (res.message === "Password is wrong! Please check again") {
          let loginPasswordInput = document.querySelector("#login-password");
          this.clearForm(loginPasswordInput);
          this.showErrorMessage(e, "Wrong password", loginPasswordInput);
        }
        console.log(res);
      }.bind(this));

    this.checkButtonActivate(e);
  }

  trySignup(e) {
    fetch(serviceUrl + "users/", {
      method: 'post',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({email: document.querySelector("#signup-email").value,
                            password: document.querySelector("#signup-password").value,
                            name: document.querySelector("#signup-username").value})
    }).then(res=>res.json())
      .then(function(res) {
        if (res.id != null && res.id > 0) { // if sign up success
          window.location.href = serviceUrl;
        } else {
          this.showErrorMessage(e, "email already exists", document.querySelector("#signup-email"))
        }
        console.log(res);
      }.bind(this));

    this.clearForm();
    this.checkButtonActivate(e);
  }

  clearForm(target) { // clear input form
    if (target) {
      target.value = "";
    } else {
      for (let input of this.inputs) {
        input.value = "";
      }
    }
  }

  clearErrorMessage(e, target) {
    if (target) {
      target.parentNode.querySelector('span').classList.remove('fade-in');
    } else {
      e.target.parentNode.querySelector('span').classList.remove('fade-in');
    }
  }

  showErrorMessage(e, message, target) {
    if (target) {
      target.parentNode.querySelector('span').innerHTML = message;
      target.parentNode.querySelector('span').classList.add('fade-in');
    } else {
      e.target.parentNode.querySelector('span').innerHTML = message;
      e.target.parentNode.querySelector('span').classList.add('fade-in');    }
  }

  checkEmailValid() {
    this.clearErrorMessage(this.event);
    if (!(emailregex.test(this.event.target.value))) {
      let message = "Invalid email form!";
      this.showErrorMessage(this.event, message);
    }

    this.disableButton(this.event);

    if (!this.checkInputNull(this.event) && this.checkButtonActivate(this.event)) {
      this.enableButton(this.event);
    }
  }

  checkPasswordConfirm() {
    let password = document.querySelector('#signup-password');
    let passwordConfirm = document.querySelector('#signup-password-confirm');
    this.clearErrorMessage(this.event, password);
    this.clearErrorMessage(this.event, passwordConfirm);
    if (password.value !== passwordConfirm.value) {
      let message = "password does not match!";
      this.showErrorMessage(this.event, message);
    }

    this.disableButton(this.event);

    if (!this.checkInputNull(this.event) && this.checkButtonActivate(this.event)) {
      this.enableButton(this.event);
    }
  }

  checkButtonActivate(e) {
    let errorMessages = e.target.parentNode.parentNode.querySelectorAll('span');
    for (let errorMessage of errorMessages) {
      if (errorMessage.classList.contains('fade-in')) {
        return false;
      }
    }

    return true;
  }

  checkInputNull(e) {
    for (let input of e.target.parentNode.parentNode.querySelectorAll('input')) {
      if (input.value == "") {
        return true;
      }
    }
    return false;
  }

  disableButton(e) {
    let button = e.target.parentNode.parentNode.querySelector('button');
    button.disabled = true;
    button.classList.add('disabled');
  }

  enableButton(e) {
    let button = e.target.parentNode.parentNode.querySelector('button');
    button.disabled = false;
    button.classList.remove('disabled');
  }

  showLastCharacter(e) { // not working properly. binded inputHidden to this
    let input = e.target;
    let inputHidden = document.querySelector("#" + input.id + "-hidden");
    let passwordCharacter = "•";

    if (e.key === "Backspace") {
      inputHidden.value = inputHidden.value.substring(0, this.value.length - 1);
    } else {
      inputHidden.value += input.value[input.value.length - 1];
    }

    if (input.value.length == 1) {
      inputHidden.value = input.value;
    }
    if (input.value.length > 1) {
      input.value = passwordCharacter.repeat(input.value.length - 1) + input.value[input.value.length - 1];
    }
  }
}

new Header();