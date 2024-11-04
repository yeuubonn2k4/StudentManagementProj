package enums;

public enum ColumnEnum {
    MA_SV("Mã Sinh Viên"), HO_VA_TEN("Họ và tên"),
    LOP("Lớp"), DIEM_GPA("Điểm GPA");
    private String columnName;

    ColumnEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
