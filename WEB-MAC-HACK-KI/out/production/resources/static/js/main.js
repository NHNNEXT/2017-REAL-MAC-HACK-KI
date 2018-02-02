/**
 * Created by Woohyeon on 2017. 12. 20..
 */
class Main {
  constructor() {
    this.arrow = document.querySelector('div.about-button');
    this.aboutButton = document.querySelector('a.about-button');
    document.querySelector('#search-button').addEventListener('click', e => {
      this.search();
    });

    this.aboutButton.addEventListener('mouseover', e => {
      this.arrow.classList.add('active');
    });

    this.aboutButton.addEventListener('mouseout', e => {
      this.arrow.classList.remove('active');
    });

    document.querySelector('ul.illust-zone').addEventListener('click', e => {
      if(e.target.tagName === 'LI') {
        this.shortcut(e);
      }
    });
  }

  shortcut(e) {
    document.querySelector('#ac2').value = e.target.getAttribute('data-id');
    document.querySelector('#search-button').click();
  }
}

new Main();