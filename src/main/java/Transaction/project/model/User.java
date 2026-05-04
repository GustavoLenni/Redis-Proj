package Transaction.project.model;

public class User {
    private String id;
    private String name;
    private String password;
    private Double balance;

    // get e set
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public Double getBalance() {
        return balance;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setBalance(Double balance){
        this.balance = balance;
    }
}
