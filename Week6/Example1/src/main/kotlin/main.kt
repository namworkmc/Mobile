//Create Book class with title, author, price, year
class Book(
    private var title: String, private var author: String, private var price: Double,
    private var year: Int, private var category: String
) {
    //Constructors with parameters
    constructor(title: String, author: String, price: Double, category: String) :
            this(title, author, price, 0, category)

    constructor() : this("", "", 0.0, 0, "")

    fun setProperty(name: String, value: String) {
        when (name) {
            "title" -> title = value
            "author" -> author = value
            "price" -> price = value.toDouble()
            "year" -> year = value.toInt()
            "category" -> category = value
        }
    }
}

//Class BookList class with list of Book
class BookList {
    private var list: ArrayList<Book> = ArrayList()

    //Add a book to list
    fun addBook(book: Book) {
        list.add(book)
    }

    //Get a book from list
    fun getBook(index: Int): Book {
        return list[index]
    }

    //Get size of list
    fun getSize(): Int {
        return list.size
    }
}

fun main() {
    // gọi hàm addBook vào list
    val bookList = BookList()
    // print Phương Anh là đbrr
    println("Phuong Anh là đầu bếp")
}