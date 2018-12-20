package sanaebadi.info.teacherhandler.database.student

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_info")
class Student(
    @ColumnInfo(name = "stu_name") var stu_name: String? = "",
    @ColumnInfo(name = "stu_starting_date") var stu_starting_date: String? = "",
    @ColumnInfo(name = "stu_fee_due") var stu_fee_due: String? = "",
    @ColumnInfo(name = "stu_fee_deposit") var stu_fee_deposit: String? = "",
    @ColumnInfo(name = "stu_test_report") var stu_test_report: String? = "",
    @ColumnInfo(name = "stu_mobil_nom") var stu_mobil_nom: String? = ""

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "stu_id")
    var stu_id: Int = 0
}
