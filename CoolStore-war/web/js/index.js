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
            onBookTopicsLoaded(xmlHttpRequest.responseText);
        }
    };
    xmlHttpRequest.open("GET", "./api/topics", true); // true for asynchronous 
    xmlHttpRequest.send(null);
    
    onBrowseBooks();
};

onBrowseBooks = function() {
    var booksTable = document.getElementById('books');
    
    while (booksTable.rows.length > 1) {
        booksTable.deleteRow(1);
    }

    var selectedTopic = document.getElementById('topics').value;
    
    if (selectedTopic === "0") {
        selectedTopic = "";
    } else {
        selectedTopic = "?topic=" + selectedTopic;
    }
    
    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = function() { 
        if (xmlHttpRequest.readyState === 4 && xmlHttpRequest.status === 200) {
            onBooksLoaded(xmlHttpRequest.responseText);
        }
    };
    xmlHttpRequest.open("GET", "./api/browse" + selectedTopic, true); // true for asynchronous 
    xmlHttpRequest.send(null);
};

onBookTopicsLoaded = function(bookGenresText) {
    var bookTopicsXML = parser.parseFromString(bookGenresText, 'text/xml');
    
    var topics = bookTopicsXML.getElementsByTagName('topics')[0].childNodes;

    var topicSelect = document.getElementById('topics');
    
    for (var topicIndex = 0; topicIndex < topics.length; topicIndex++) {
        var topic = topics[topicIndex];
        topicSelect.options[topicSelect.options.length] = new Option(
                topic.getElementsByTagName('topic_name')[0].childNodes[0].nodeValue, 
                topic.getElementsByTagName('topic_id')[0].childNodes[0].nodeValue);
    }
};

onBooksLoaded = function(bookGenresText) {
    var bookTopicsXML = parser.parseFromString(bookGenresText, 'text/xml');
    
    var books = bookTopicsXML.getElementsByTagName('books')[0].childNodes;

    var booksTable = document.getElementById('books');
    
    for (var bookIndex = books.length - 1; bookIndex >= 0; bookIndex--) {
        var book = books[bookIndex];
        
        var newRow = booksTable.insertRow(1);
        var bookTitleCell = newRow.insertCell(0);
        var authorNameCell = newRow.insertCell(1);
        var topicCell = newRow.insertCell(2);
        var actionCell = newRow.insertCell(3);
        
        bookTitleCell.innerHTML = book.getElementsByTagName('book_name')[0].childNodes[0].nodeValue;
        authorNameCell.innerHTML = book.getElementsByTagName('author_name')[0].childNodes[0].nodeValue;
        topicCell.innerHTML = book.getElementsByTagName('topic_name')[0].childNodes[0].nodeValue;
        
        if (book.getElementsByTagName('is_available')[0].childNodes[0].nodeValue == 1) {
            actionCell.innerHTML = '<a class="reserve" href="./api/reserve?book=' + book.getElementsByTagName('book_id')[0].childNodes[0].nodeValue + '">Reserve</a>';
        } else {
            if (book.getElementsByTagName('already_reserved')[0].childNodes[0].nodeValue == 1) {
                actionCell.innerHTML = '<a class="reserved">Reserved</a>';
            } else {
                actionCell.innerHTML = '<a class="reserved">Unavailable</a>';                
            }
        }
    }
};