import androidx.room.*
import com.example.database_lr.sampledata.User

@Dao
interface SchoolDao {
    @Insert
    suspend fun insertGroup(group: Group)

    @Insert
    suspend fun insertStudent(student: User)

    @Query("SELECT * FROM User")
    suspend fun getAllStudents(): List<User>

    @Query("SELECT * FROM `Group`")
    suspend fun getAllGroups(): List<Group>
}