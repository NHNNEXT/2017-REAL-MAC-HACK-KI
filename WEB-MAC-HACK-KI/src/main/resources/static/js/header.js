window.onload = () => {
    init();
}

function init() {
    let dimmer = document.querySelector("#dimmer");
    let loginBtn = document.querySelector(".login-button");
    let signupBtn = document.querySelector(".signup-button");

    loginBtn.addEventListener("click", () => {
        dimmer.style.display = "block";
        document.querySelector("#sign_in_modal").style.display = "block";
    });

    dimmer.addEventListener("click", () => {
        dimmer.style.display = "none";
        offModal();
    })

    function offModal() {
        let modals = document.querySelectorAll(".my_modal");
        for (modal of modals) {
            modal.style.display = "none";
        }
    }
}