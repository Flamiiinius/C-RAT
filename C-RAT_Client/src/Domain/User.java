package Domain;

/**
 * C-RAT Server & Client
 * author @ThePoog
 * MasterProject @SINTEF & @UiO
 * November 2018
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class User implements Serializable {

    private static final long serialVersionUID = 102L;

    private final Integer userId;
    private final String name;
    private final String children; //String int IDs of its children
    private final String position;
    private final Integer parent;
    private final String parentName;
    private List<Integer> childrenList = new ArrayList<>();

    public User(int userId, String name, String position,Integer parent,String parentName){
        this.userId = userId;
        this.name = name;
        this.position = position;
        this.parent = parent;
        this.parentName = parentName;
        this.children = null;
    }

    public User(int userId, String name, String position,Integer parent,String parentName, String children){
        this.userId = userId;
        this.name = name;
        this.children = children;
        this.position = position;
        this.parent = parent;
        this.parentName = parentName;

        //children like 1,3,4
        if(children!=null){
            String[] tempStrings = children.split(Pattern.quote(","));
            for (String s: tempStrings) {
                Integer i = Integer.parseInt(s);
                childrenList.add(i);
            }
        }
    }

    public Integer getUserId(){
        return userId;
    }

    public String getName(){
        return name;
    }

    public Integer getParent(){
        return parent;
    }

    public String getPosition(){
        return position;
    }

    public List<Integer> getChildren(){
        return this.childrenList;
    }

    /*public static HashMap<Integer,User> mockupUsers(){

        HashMap<Integer,User> usersMap= new HashMap<>();

        User u1 = new User(1,"Rodrigo","Master Student",2);
        User u2 = new User(2,"Ketil","Risk Manager",null,"1,3,4");
        User u3 = new User(3,"Tian","Risk Owner",2);
        User u4 = new User(4,"Jhon","Risk Owner ",2);

        usersMap.put(1, u1);
        usersMap.put(2, u2);
        usersMap.put(3, u3);
        usersMap.put(4, u4);

        return  usersMap;
    }*/

}
