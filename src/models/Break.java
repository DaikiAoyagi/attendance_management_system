package models;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Table(name = "breaks")

@NamedQueries({
    @NamedQuery(
            name = "getAllBreaks",
            query = "SELECT b FROM Break AS b ORDER BY b.id DESC"
            ),
    @NamedQuery(
            name = "getBreaksCount",
            query = "SELECT COUNT(b) FROM Break AS b"
            ),

    @NamedQuery(
            name = "getMyAllBreaks",
            query = "SELECT b FROM Break AS b WHERE b.employee = :employee ORDER BY b.id DESC"
            ),

    @NamedQuery(
            name = "getMyAllBreaksPerDay",
            query = "SELECT b FROM Break AS b WHERE b.employee = :employee AND b.work_date = :work_date ORDER BY b.break_start_time DESC"
            ),
    @NamedQuery(
            name = "getMyBreaksPerDayCount",
            query = "SELECT COUNT(b) FROM Break AS b WHERE b.employee = :employee AND b.work_date = :work_date"
            ),
    @NamedQuery(
            name = "getMyNowBreaks",
            query = "SELECT b FROM Break AS b WHERE b.employee = :employee AND b.work_date = :work_date AND b.break_start_time =:break_start_time"
            )
})

@Entity

public class Break {

/*
カラム名    用途  データ型
id      リソース内での連番   数値型
employee_id 打刻社員の社員番号   文字列型
work_date   出勤日     日付型
break_start_time    休憩開始時刻  時間型
break_finish_time   休憩終了時刻  時間型

仕様
new ＞勤怠管理側で休憩開始時間を打刻
    →createにて、テーブルにレコード追加（ユーザー、日付、休憩開始時間）
edit＞勤怠管理側で休憩終了時間を打刻
    →updateにて、休憩終了時間を登録（newで作成されたレコードを呼び出す。※ユーザー・日付)
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

    @Column(name = "break_start_time", nullable = false)
    private Time break_start_time;

    @Column(name = "break_finish_time", nullable = false)
    private Time break_finish_time;



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

}
