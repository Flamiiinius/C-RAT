package Domain;

/**
 * C-RAT Server
 * author @ThePoog
 * MasterProject @SINTEF
 * November 2018
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
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

    public int getUserId(){
        return userId;
    }

    public String getUsername(){
        return name;
    }

    public String getPosition(){
        return position;
    }

    public Integer getParent(){
        return this.parent;
    }

    public String getParentName(){
        return parentName;
    }

    public List<Integer> getChildren(){
        return this.childrenList;
    }

}
