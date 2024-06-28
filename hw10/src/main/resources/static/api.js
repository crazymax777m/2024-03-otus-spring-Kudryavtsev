const fetchAuthors = () => fetch('/api/v1/authors')
    .then(resp => resp.json());

const fetchGenres = () => fetch('/api/v1/genres')
    .then(resp => resp.json());

const fetchBookById = bookId => fetch('/api/v1/books/' + bookId)
    .then(resp => resp.json());

const createBook = book => fetch('/api/v1/books', {
    method: 'post',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(book)
});

const updateBook = (bookId, book) => fetch('/api/v1/books/' + bookId, {
    method: 'put',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(book)
});

const deleteBookById = bookId =>
    fetch('/api/v1/books/' + bookId, { method: 'delete' });


