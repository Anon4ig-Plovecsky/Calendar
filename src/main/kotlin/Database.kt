import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.MongoClient
import org.bson.Document

class Database {
    //-------------------Database keys-------------------//
    private val keyDate = "date"
    private val keyTask = "task"
    private val keyTaskIsDone = "taskIsDone"
    //------------------Database values------------------//
    private var mongo: MongoClient? = null
    private var document: Document? = null
    private var mongoDatabase: MongoDatabase? = null
    private var mongoCollection: MongoCollection<Document>? = null
    //---------------------Initialize--------------------//
    init {
        mongo = MongoClient("localhost", 27017)
        mongoDatabase = mongo?.getDatabase("calendar")
        mongoCollection = mongoDatabase?.getCollection("todoList")
    }
//    private fun find(day: Int, month: Int, year: Int): MutableSet<Map<String, String>> {
//        val taskList = HashSet<Map<String, String>>()
//        document = mongoCollection?.find(Document(keyDate, "$year-$month-$day")) as Document
//
//    }
}