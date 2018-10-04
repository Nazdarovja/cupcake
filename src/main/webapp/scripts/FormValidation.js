/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var form = document.getElementsByTagName("form");//gets all forms on the page

form[0].onsubmit = function () {
    var uNameRegEx = /^[-\w\.\$\@\*\!\#]{6,12}$/;
    var pwdRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
    var emailRegExp = /^(([\-\w]+)\.?)+@(([\-\w ]+)\.?)+\.[a-zA-Z]{2,4}$/;

    var username = document.getElementById("username").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

//Check for Match
    var resUsername = checkInput(username, uNameRegEx);
    var resEmail = checkInput(email, emailRegExp);
    var resPwd = checkInput(password, pwdRegEx);
    
    if (!resUsername && !document.getElementById("usernameError").innerHTML.includes("Incorrect Username")) {
        document.getElementById("usernameError").innerHTML += "<BR/>Incorrect Username - Try again";
    }

    if (!resEmail && !document.getElementById("emailError").innerHTML.includes("Incorrect Email")) {
        document.getElementById("emailError").innerHTML += "<BR/>Incorrect Email- Try again";
    }
    
    if (!resPwd && !document.getElementById("passwordError").innerHTML.includes("Incorrect Password")) {
        document.getElementById("passwordError").innerHTML += "<BR/>Incorrect Password - Try again";
    }
    return resUsername && resEmail && resPwd;
};

function checkInput(value, regexp) {
    return regexp.test(value);
//    if (regexp.test(value) === false) {
//
//        return false;
//    }
//    return true;
}