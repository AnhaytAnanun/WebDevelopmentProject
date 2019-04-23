/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var parser = parser = new DOMParser();

window.onload = function() {
    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = function() { 
        if (xmlHttpRequest.readyState === 4 && xmlHttpRequest.status === 200) {
            onReservationsLoaded(xmlHttpRequest.responseText);
        }
    };
    xmlHttpRequest.open("GET", "./api/reservations", true); // true for asynchronous 
    xmlHttpRequest.send(null);
};

onReservationsLoaded = function(bookGenresText) {
    var bookTopicsXML = parser.parseFromString(bookGenresText, 'text/xml');
    
    var books = bookTopicsXML.getElementsByTagName('books')[0].childNodes;

    var booksTable = document.getElementById('reservations');
    
    for (var bookIndex = books.length - 1; bookIndex >= 0; bookIndex--) {
        var book = books[bookIndex];
        
        var newRow = booksTable.insertRow(1);
        var bookTitleCell = newRow.insertCell(0);
        var authorNameCell = newRow.insertCell(1);
        var topicCell = newRow.insertCell(2);
        
        bookTitleCell.innerHTML = book.getElementsByTagName('book_name')[0].childNodes[0].nodeValue;
        authorNameCell.innerHTML = book.getElementsByTagName('author_name')[0].childNodes[0].nodeValue;
        topicCell.innerHTML = book.getElementsByTagName('topic_name')[0].childNodes[0].nodeValue;
    }
};