package models;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
//import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Table(name = "attendances")
@NamedQueries({
    @NamedQuery(
            name = "getAllAttendances",
            query = "SELECT r FROM Attendance AS r ORDER BY r.id DESC"
            ),
    @NamedQuery(
            name = "getAttendancesCount",
            query = "SELECT COUNT(r) FROM Attendance AS r"
            ),
    @NamedQuery(
            name = "getMyAllAttendances",
            query = "SELECT r FROM Attendance AS r WHERE r.employee = :employee ORDER BY r.id DESC"
            ),
    @NamedQuery(
            name = "getMyAttendancesCount",
            query = "SELECT COUNT(r) FROM Attendance AS r WHERE r.employee = :employee"
            )

})
@Entity

public class Attendance {

/*
カラム名    用途  データ型
id      リソース内での連番   数値型
employee_id 打刻社員の社員番号   文字列型
work_date   出勤日     日付型
start_time  出勤時刻    時間型
finish_time 退勤時刻    時間型
break_start_time    休憩開始時刻  時間型
break_finish_time   休憩終了時刻  時間型
break_time  休憩時間    時間型
working_hours   勤務時間    時間型
content     備考      テキスト型
created_at  登録日時    日時型
updated_at  更新日時    日時型

仕様
・出勤日(YYYY/MM/DD)、出勤時刻(hh:mm)、退勤時刻(hh:mm)は社員が入力
・勤務時間は、出勤時刻と退勤時刻から自動計算し、休憩時間を引いて表示
・登録日時、更新日時は以下のマネジメント目的で使う
  ・登録日時=社員が発行した出勤日時と剥離していないか
  ・更新日時=過去のタイムカードの勤怠情報を更新したか
 */


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "work_date", nullable = false)
    private Date work_date;

    @Column(name = "start_time", nullable = false)
    private Time start_time;

    @Column(name = "finish_time", nullable = false)
    private Time finish_time;

    @Column(name = "break_start_time", nullable = false)
    private Time break_start_time;

    @Column(name = "break_finish_time", nullable = false)
    private Time break_finish_time;

    @Column(name = "break_time", nullable = false)
    private Time break_time;

    @Column(name = "working_hours", nullable = false)
    private Time working_hours;

    @Lob
    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getWork_date() {
        return work_date;
    }

    public void setWork_date(Date work_date) {
        this.work_date = work_date;
    }

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public Time getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(Time finish_time) {
        this.finish_time = finish_time;
    }

    public Time getBreak_start_time() {
        return break_start_time;
    }

    public void setBreak_start_time(Time break_start_time) {
        this.break_start_time = break_start_time;
    }

    public Time getBreak_finish_time() {
        return break_finish_time;
    }

    public void setBreak_finish_time(Time break_finish_time) {
        this.break_finish_time = break_finish_time;
    }

    public Time getBreak_time() {
        return break_time;
    }

    public void setBreak_time(Time break_time) {
        this.break_time = break_time;
    }

    public Time getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(Time working_hours) {
        this.working_hours = working_hours;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * 休憩開始時間と休憩終了時間から休憩時間を自動計算する。

    public void calcBreakTime() {
        Duration duration = Duration.between(break_start_time, break_finish_time);
        setBreak_time(duration);
    }
*/
    /**
     * 出勤時間と退勤時間から勤務時間を自動計算する。<br>
     * 休憩時間があるときは勤務時間から休憩時間を引く。

    public void calcWorkingHours() {
        Duration duration = Duration.between(start_time, finish_time);
        setWorking_hours(duration);
        if(break_time != null) {
            duration = working_hours.minus(break_time);
            setWorking_hours(duration);
        }
    }
    */
}
