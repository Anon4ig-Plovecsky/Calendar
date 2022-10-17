import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoCursor
import org.bson.conversions.Bson
import com.mongodb.MongoClient
import org.json.JSONObject
import org.bson.Document

class Database {
    companion object {
        private var database: Database? = null
        fun getDatabase(): Database? = if(database != null) database else null
    } //TODO
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
        database = this
        mongo = MongoClient("localhost", 27017)
        mongoDatabase = mongo?.getDatabase("calendar")
        mongoCollection = mongoDatabase?.getCollection("todoList")
    }
    //------------------Private methods------------------//
    private fun findOne(day: Int, month: Int, year: Int, task: String): Document? {
        val searchValue = Document(keyDate, "$year-$month-$day")
        searchValue.append(keyTask, task)
        return mongoCollection?.find(searchValue)?.first()
    }
    private fun add(day: Int, month: Int, year: Int, task: String) {
        document = Document(keyDate, "$year-$month-$day")
        document?.append(keyTask, task)
        document?.append(keyTaskIsDone, "false")
        mongoCollection?.insertOne(document!!)
    }
    //-------------------Public methods------------------//
    fun find(day: Int, month: Int, year: Int): MutableSet<Map<String, String>> {
        val taskList = HashSet<Map<String, String>>()
        val search: MongoCursor<Document>? = mongoCollection?.find(Document(keyDate, "$year-$month-$day"))?.iterator()
        return if(search != null) {
            while (search.hasNext()) {
                val jsonObject = JSONObject(search.next().toJson())
                val map = HashMap<String, String>()
                map[MainWindow.keyTaskName] = jsonObject.getString(keyTask)
                map[MainWindow.keyIsDone] = jsonObject.getString(keyTaskIsDone)
                taskList.add(map)
            }
            taskList
        } else HashSet()
    }
    fun addOrUpdate(day: Int, month: Int, year: Int, newTask: String, oldTask: String) {
        val search = findOne(day, month, year, oldTask)
        if(search != null) {
            val updateValue: Bson = Document(keyTask, newTask)
            val updateOperation: Bson = Document("\$set", updateValue)
            mongoCollection?.updateOne(search, updateOperation)
        } else
            add(day, month, year, newTask)
    }
    fun delete(day: Int, month: Int, year: Int, task: String) {
        val search = findOne(day, month, year, task)
        if(search != null)
            mongoCollection?.deleteOne(search)
    }
}