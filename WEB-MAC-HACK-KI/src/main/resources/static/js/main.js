/**
 * Created by Woohyeon on 2017. 12. 20..
 */
class Main {
  constructor() {
    document.querySelector('#search-button').addEventListener('click', e => {
      this.search();
    });
  }
}

new Main();
