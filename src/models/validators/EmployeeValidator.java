package models.validators;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DBUtil;

public class EmployeeValidator {
    public static List<String> validate(Employee e, Boolean code_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();

        String employeeCode_error = _validateEmployeeCode(e.getEmployeeCode(), code_duplicate_check_flag);
        if(!employeeCode_error.equals("")) {
            errors.add(employeeCode_error);
        }

        String employeeName_error = _validateEmployeeName(e.getEmployeeName());
        if(!employeeName_error.equals("")) {
            errors.add(employeeName_error);
        }

        String sectionCode_error = _validateSectionCode(e.getSectionCode());
        if(!sectionCode_error.equals("")) {
            errors.add(sectionCode_error);
        }

        String password_error = _validatePassword(e.getPassword(), password_check_flag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;
    }


    // 社員番号
    private static String _validateEmployeeCode(String employeeCode, Boolean code_duplicate_check_flag) {
        // 必須入力チェック
        if(employeeCode == null || employeeCode.equals("")) {
            return "社員番号を入力してください。";
        }else{
            // 正規表現のパターンを作成
            Pattern p = Pattern.compile("[0-9A-Z]{8}");
            Matcher m = p.matcher(employeeCode);
            if(m.find() == false){
                return "社員番号は半角8桁の 大文字英字 と 数字の組み合わせで入力して下さい";
            }
        }

        // すでに登録されている社員番号との重複チェック
        if(code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long employees_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class)
                                           .setParameter("employeeCode", employeeCode)
                                             .getSingleResult();
            em.close();
            if(employees_count > 0) {
                return "入力された社員番号の情報はすでに存在しています。";
            }
        }

        return "";
    }

    // 社員名の必須入力チェック
    private static String _validateEmployeeName(String employeeName) {
        if(employeeName == null || employeeName.equals("")) {
            return "氏名を入力してください。";
        }

        return "";
    }

    // 部署の必須入力チェック
    private static String _validateSectionCode(String sectionCode) {
        if(sectionCode == null || sectionCode.equals("")) {
            return "部署を入力してください。";
        }

        return "";
    }


    // パスワードの必須入力チェック
    private static String _validatePassword(String password, Boolean password_check_flag) {
        // パスワードを変更する場合のみ実行
        if(password_check_flag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return "";
    }
}
