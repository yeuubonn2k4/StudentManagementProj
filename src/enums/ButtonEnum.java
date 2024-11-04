package enums;

public enum ButtonEnum {
    CREATE_BUTTON("Thêm"), UPDATE_BUTTON("Cập nhật"), DELETE_ONE_RECORD_BUTTON("Xóa"),
    READ_BUTTON("Hiển thị"), DELETE_ALL_BUTTON("Reset")
    ;
    private String buttonName;

    ButtonEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonValue() {
        return buttonName;
    }
}
