/**
 * Created by Woohyeon on 2017. 12. 20..
 */
class EditProfile {
  constructor() {
    this.init();
  }

  init() {
    var source   = document.getElementById("entry-template").innerHTML;
    var template = Handlebars.compile(source);
    var context = {
      people: [
        "Yehuda Katz",
        "Alan Johnson",
        "Charles Jolley"
      ]
    };
    var html    = template(context);

    console.log(html);
  }
}

new EditProfile();
