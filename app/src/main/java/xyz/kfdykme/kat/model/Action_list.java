/**
  * Copyright 2017 bejson.com 
  */
package xyz.kfdykme.kat.model;
import java.util.List;

/**
 * Auto-generated: 2017-09-14 21:15:29
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Action_list {

    private String action_id;
    private Action_type action_type;
    private List<String> arg_list;
    private Code_actions code_actions;
    private int confidence;
    private List<String> exe_status;
    private List<Hint> hint_list;
    private String main_exe;
    private String say;


    public void setAction_id(String action_id) {
         this.action_id = action_id;
     }
     public String getAction_id() {
         return action_id;
     }

    public void setAction_type(Action_type action_type) {
         this.action_type = action_type;
     }
     public Action_type getAction_type() {
         return action_type;
     }

    public void setArg_list(List<String> arg_list) {
         this.arg_list = arg_list;
     }
     public List<String> getArg_list() {
         return arg_list;
     }

    public void setCode_actions(Code_actions code_actions) {
         this.code_actions = code_actions;
     }
     public Code_actions getCode_actions() {
         return code_actions;
     }

    public void setConfidence(int confidence) {
         this.confidence = confidence;
     }
     public int getConfidence() {
         return confidence;
     }

    public void setExe_status(List<String> exe_status) {
         this.exe_status = exe_status;
     }
     public List<String> getExe_status() {
         return exe_status;
     }

    public void setHint_list(List<Hint> hint_list) {
         this.hint_list = hint_list;
     }
     public List<Hint> getHint_list() {
         return hint_list;
     }

    public void setMain_exe(String main_exe) {
         this.main_exe = main_exe;
     }
     public String getMain_exe() {
         return main_exe;
     }

    public void setSay(String say) {
         this.say = say;
     }
     public String getSay() {
         return say;
     }

}