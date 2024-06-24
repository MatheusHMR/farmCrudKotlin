package backupView

import android.app.AlertDialog
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sqliteprojectforfarms.Farm
import com.example.sqliteprojectforfarms.R
import com.example.sqliteprojectforfarms.farmDAO.FarmDAO
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class backupView : AppCompatActivity() {
    lateinit var bt_return : FloatingActionButton
    lateinit var bt_backup : Button
    lateinit var bt_remakeDatabase : Button
    lateinit var bt_loadbackup: Button
    lateinit var lv_farms: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_backup_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        bt_return = findViewById(R.id.BT_return)
        bt_remakeDatabase = findViewById(R.id.BT_remake)
        bt_backup = findViewById(R.id.BT_backup)
        bt_loadbackup = findViewById(R.id.BT_loadbackup)
        val main_activity = intent


        bt_remakeDatabase.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setTitle("ATTENTION!!")

            builder.setMessage("You're about to delete the entire database of the application!!" +
            "Are you sure you want to do that?")

            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                FarmDAO.getInstance(applicationContext).db.delete(applicationContext)
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        bt_return.setOnClickListener {
            this.finish()
        }

        bt_backup.setOnClickListener {
            val farmDao = FarmDAO(applicationContext)
            val allFarms = farmDao.getAllFarms()
            val farmsOnString = stringify(allFarms) //Save on a Txt file
            saveBackupToFile(farmsOnString)
        }

        bt_loadbackup.setOnClickListener{
            readBackupFromFile()
        }
    }

    fun stringify(farms: List<Farm>) : String{
        val stringifiedFarms = StringBuilder()
        farms.forEach { farm -> stringifiedFarms.append("$farm\n") }

        return stringifiedFarms.toString()
    }
    fun saveBackupToFile(backupData: String) {
        val backupFolder = File(this.filesDir, "backups")
        if (!backupFolder.exists()) {
            backupFolder.mkdirs()
        }

        val backupFile = File(backupFolder, "latest_backup.txt")
        backupFile.writeText(backupData)
    }

    fun readBackupFromFile() {
        val backupFolder = File(this.filesDir, "backups")
        val backupFile = File(backupFolder, "latest_backup.txt")

        if (backupFile.exists()) {
            val backupData = backupFile.readText()
            Log.i("DatabaseBackup", backupData)
        } else {
            Log.i("DatabaseBackup", "No backup file found.")
        }
    }

    val isExternalStorageReadOnly : Boolean get() {
        var readOnlyStorage = false
        val externalStorage = Environment.getExternalStorageState()
        if(Environment.MEDIA_MOUNTED_READ_ONLY == externalStorage){
            readOnlyStorage = true
        }
        return readOnlyStorage
    }

    val isExternalStorageAvailable : Boolean get(){
        var storageAvailable = false
        val externalStorage = Environment.getExternalStorageState()
        if(Environment.MEDIA_MOUNTED == externalStorage){
            storageAvailable = true
        }
        return storageAvailable
    }
}