import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database_lr.sampledata.User

@Database(entities = [User::class, Group::class], version = 1)
abstract class SchoolDatabase : RoomDatabase() {
    abstract fun schoolDao(): SchoolDao
}