package enums;

public enum ButtonEnum {
    CREATE_BUTTON("Tạo"), UPDATE_BUTTON("Cập nhật"), DELETE_ONE_RECORD_BUTTON("Xóa"),
    READ_BUTTON("Đọc"), DELETE_ALL_BUTTON("Xóa tất cả"),
    PREV_BUTTON("Quay lại"), NEXT_BUTTON("Tiếp")
    ;
    private String buttonName;

    ButtonEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonValue() {
        return buttonName;
    }
}
