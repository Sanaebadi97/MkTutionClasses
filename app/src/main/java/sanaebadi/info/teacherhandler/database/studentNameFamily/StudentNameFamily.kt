package sanaebadi.info.teacherhandler.database.studentNameFamily

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stu_name_last_name")
class StudentNameFamily(
    @ColumnInfo(name = "stu_first_name") var stu_first_name: String? = "",
    @ColumnInfo(name = "stu_last_name") var stu_last_name: String? = ""
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "stu_id")
    var stu_id: Int = 0
}