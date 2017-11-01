/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



$('#close-customer-button').on('click', function () {
    var contextPathUrl = '/springthyme/customer';
    console.log("ContextPathUrl :::::: " + contextPathUrl);
    console.log("------------------ 1");
    window.location.href = contextPathUrl;
});

               