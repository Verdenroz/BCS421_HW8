package edu.farmingdale.alrajab.bcs421.database
import android.content.Context
import androidx.room.Room


class NameRepository private constructor(context: Context) {
    private val database : AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "person.db"
    )
        .allowMainThreadQueries()
        .build()


    companion object {
        private var instance: NameRepository? = null

        fun getInstance(context: Context): NameRepository {
            if (instance == null) {
                instance = NameRepository(context)
            }
            return instance!!
        }
    }



    private val usrDao = database.userDao()
    init {
        if (usrDao.getAll().isEmpty()) {
            addUser(User("Moaath","Alrajab"))
            addUser(User("James","Smith"))
            addUser(User("Ben","Adams"))
        }
    }


    fun getUser(id: Long): User? = usrDao.getUser(id)

    fun getAll(): List<User> = usrDao.getAll()

    fun addUser(usr: User) {
        usrDao.addUser(usr)
    }

    fun deleteUser(usr: User) = usrDao.delete(usr)

    fun findByName(first: String, last: String) = usrDao.findByName(first, last)

    fun updateName(first: String, last: String, id: Int) = usrDao.updateName(first, last, id)


}