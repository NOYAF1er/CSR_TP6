$(document).ready(function() {
    $("#create-user-form button").click( function() {
        var new_user = new Object();

        new_user.name = $('input#name').val();
        new_user.age = $('input#age').val()

        console.log(new_user);

        $.ajax({
            type: "post",
            url: "/users/",
            data: JSON.stringify(new_user),
            success: function(data){
                console.log(data);
                window.location = "/users/";
            },
            dataType: "json",
            contentType : "application/json"
        });
    })

    $('#list-users').ready( function() {
        var users_table = $('#users-table tbody');

        $.ajax({
            type: "get",
            url: "/users/",
            success: function(data){
                console.log(data);

                $.each(data, function (item) {
                    var name = data[item].name;
                    var age = data[item].age;
                    var id = data[item].id;
                    var url = data[item].url;
                    var tweet_url = data[item].tweet_url;

                    users_table.append(
                    '<tr>' +
                        '<th><a href="' + url + '">' + id + '</a></th>' +
                        '<td>' + name + '</td>' +
                        '<td>' + age + '</td>' +
                        '<td><a type="button" class="btn btn-success btn-xs" href = "'+ tweet_url + '">tweets</a></td>' +
                        '<td><button type="button" class="btn btn-danger btn-xs" id="delete-user-' + id +'">delete</button></td>' +
                    '</tr>'
                    );

                    $('button#delete-user-' + id).click(function() {
                        $.ajax({
                            type: "delete",
                            url: "/users/" + id,
                            success: function(data){
                                console.log("User " + data.id + " deleted");
                                window.location = "/users/";
                            }
                        });
                    });
                    
                });
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("ajax error");
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
            },
            dataType: "json",
            contentType : "application/json"
        });
    });
});