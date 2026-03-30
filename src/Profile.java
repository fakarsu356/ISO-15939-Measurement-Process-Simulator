
class Profile{

private String Username;
private String School;
private String SessionName;
public String getUsername() {
    return Username;
}
public void setUsername(String username) {
    Username = username;
}
public String getSchool() {
    return School;
}
public void setSchool(String school) {
    School = school;
}
public String getSessionName() {
    return SessionName;
}
public void setSessionName(String sessionName) {
    SessionName = sessionName;
}
public Profile(String username, String school, String sessionName) {
    Username = username;
    School = school;
    SessionName = sessionName;
}



    
}