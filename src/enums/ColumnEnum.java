package enums;

public enum ColumnEnum {
    MA_SV("MaSV"), HO_VA_TEN("Ho va ten"),
    LOP("Lop"), DIEM_GPA("Diem GPA");
    private String columnName;

    ColumnEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
