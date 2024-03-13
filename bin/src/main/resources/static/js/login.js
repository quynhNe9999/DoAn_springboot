$(function(){
	
	var TOKEN_KEY = "jwtToken";
	var $login = $("#login");
		
	// FUNCTIONS =============================================================
    function getJwtToken() {
        return localStorage.getItem(TOKEN_KEY);
    }

    function setJwtToken(token) {
        localStorage.setItem(TOKEN_KEY, token);
    }

    function removeJwtToken() {
        localStorage.removeItem(TOKEN_KEY);
    }

    function doLogin(loginData) {
        $.ajax({
            url: "/auth",
            type: "POST",
            data: JSON.stringify(loginData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
            	//setJwtToken(data.token);
                /*setJwtToken(data.token);
                $login.hide();
                $notLoggedIn.hide();
                showTokenInformation()
                showUserInformation();*/
            },
            error: function (jqXHR, textStatus, errorThrown) {
            	console.log(jqXHR);
                if (jqXHR.status === 401) {
                	console.log("error");
                    $('#loginErrorModal')
                        .modal("show")
                        .find(".modal-body")
                        .empty()
                        .html("<p>Spring exception:<br>" + jqXHR.responseJSON + "</p>");
                } else {
                    throw new Error("an unexpected error occured: " + errorThrown);
                }
            }
        });
    }
    
    function createAuthorizationTokenHeader() {
        var token = getJwtToken();
        if (token) {
            return {"X-Authorization": token};
        } else {
            return {};
        }
    }

    
 // REGISTER EVENT LISTENERS =============================================================
    $("#loginForm").submit(function (event) {
        event.preventDefault();

        var $form = $(this);
        var formData = {
            username: $form.find('input[name="username"]').val(),
            password: $form.find('input[name="password"]').val()
        };

        doLogin(formData);
    });
});
