package edu.farmingdale.alrajab.bcs421.database

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.farmingdale.alrajab.bcs421.R
import edu.farmingdale.alrajab.bcs421.databinding.ActivityDatabaseBinding

class DatabaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatabaseBinding

    private lateinit var dbHelper: NameRepository

    private lateinit var dbAdapter: DBAdapter

    private lateinit var userList: ArrayList<User>

    // ToDO: Database link to be completed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatabaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper= NameRepository.getInstance(this)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        userList = dbHelper.getAll() as ArrayList<User>
        dbAdapter = DBAdapter(userList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dbAdapter



        dbHelper= NameRepository.getInstance(this)

        binding.readData.setOnClickListener { readData() }
        binding.writeData.setOnClickListener { writeData() }
        binding.updateNameBtn.setOnClickListener { updateName() }
        binding.fileUpdateBtn.setOnClickListener { updateWithFile()}

        dbAdapter.notifyDataSetChanged()
    }

    //writes to db and recycler view a new user
    private fun writeData() {
        if(binding.databaseFirstname.text.isNotEmpty() && binding.databaseLastname.text.isNotEmpty()){
            dbHelper.addUser(User(binding.databaseFirstname.text.toString(), binding.databaseLastname.text.toString()))
            userList.add(User(binding.databaseFirstname.text.toString(), binding.databaseLastname.text.toString()))
            dbAdapter.notifyDataSetChanged()
        }

    }

    //debug tool
    private fun readData() {
        dbHelper.getAll().forEach { Log.d("Data",it.firstName+" , "+ it.lastName) }
        dbAdapter.notifyDataSetChanged()
    }

    //updates first and last name where specified
    private fun updateName(){
        val oldFirst = binding.databaseOldfirstname.text.toString()
        val oldLast = binding.databaseOldlastname.text.toString()
        val newFirst = binding.databaseNewfirstname.text.toString()
        val newLast = binding.databaseNewlastname.text.toString()
        val userID = dbHelper.findByName(oldFirst, oldLast)
        var userIndex = userList.indexOfFirst { user -> user.firstName == oldFirst && user.lastName == oldLast }
        Log.d("ID", userID.toString())
        Log.d("Old First", oldFirst)
        Log.d("Old Last", oldLast)
        Log.d("New First", newFirst)
        Log.d("New Last", newLast)
        Log.d("Index", userIndex.toString())
        if (userID != null && userIndex != -1) {
            dbHelper.updateName(newFirst, newLast, userID)
            userList.set(userIndex, User(newFirst, newLast))
            dbAdapter.notifyDataSetChanged()
        }
        else{
            Toast.makeText(this, "Cannot find name to update", Toast.LENGTH_SHORT).show()
        }
    }

    //reads from file and adds to db and recycler view
    private fun updateWithFile(){
        val inputStream = openFileInput("myfile")
        val reader = inputStream.bufferedReader()
        val stringBuilder = StringBuilder()
        val lineSeparator = System.getProperty("line.separator")
        // Append each task to stringBuilder
        reader.forEachLine { stringBuilder.append(it).append(lineSeparator) }

        val names = stringBuilder.split("\n", " ")
        val firstNames = mutableListOf<String>()
        val lastNames = mutableListOf<String>()

        names.forEachIndexed{ index, string ->
            if(index % 2 == 0){
                firstNames.add(string)
            }
            else{
                lastNames.add(string)
            }
        }
        Log.d("File", names.toString())
        Log.d("First Names", firstNames.toString())
        Log.d("Last Names", lastNames.toString())

        if(lastNames.isNotEmpty()){
            firstNames.forEachIndexed { index, firstName ->
                val user = User(
                    firstName = firstName,
                    lastName = lastNames.getOrElse(index) { "" }
                )
                dbHelper.addUser(user)
                userList.add(user)
            }
            dbAdapter.notifyDataSetChanged()
        }

    }

}