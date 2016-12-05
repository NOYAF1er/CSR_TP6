$(document).ready(function() {
    $("#create-clients-form button").click( function() {
        var new_client = new Object();

        new_client.qte = $('input#qte').val();

        console.log(new_client);

        $.ajax({
            type: "post",
            url: "/supermarche/clients",
            data: JSON.stringify(new_client),
            success: function(data){
                console.log(data);
                window.location.reload(true);
            },
            dataType: "json",
            contentType : "application/json"
        });
    })

    $('#list-clients').ready( function() {
        var clients_table = $('#clients-table tbody');

        $.ajax({
            type: "get",
            url: "/supermarche/clients",
            success: function(data){
                console.log(data);

                $.each(data, function (item) {
                    var id = data[item].id;
                    var url = data[item].url;

                    clients_table.append(
                    '<tr>' +
                        '<th><a href="' + url + '">' + id + '</a></th>' +
                        '<td><a href="' + url + '" onclick=alert("EnConstruction!")>' + url + '</a></td>' +
                    '</tr>'
                    );
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