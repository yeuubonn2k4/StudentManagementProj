package enums;

public enum FieldEnum {
    MA_SV("Mã Sinh Viên: "), HO_VA_TEN("Họ và tên: "),
    LOP("Lớp: "), DIEM_GPA("Điểm GPA: ");
    private String fieldName;

    FieldEnum(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
