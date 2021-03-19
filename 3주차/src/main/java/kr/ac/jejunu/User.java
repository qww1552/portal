package kr.ac.jejunu;

public class User {
    private Integer id;
    // Integer를 쓴 이유 int는 null을 허용하지 않음,
    // int는 default가 0이 들어가지만 id 값이 0일 수 있음
    // 객체로 사용하면 이점이 많음
    private String name;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
