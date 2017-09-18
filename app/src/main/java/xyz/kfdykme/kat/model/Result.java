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
public class Result {

    private List<Action_list> action_list;
    private Qu_res qu_res;
    private Schema schema;
    private String session_id;
    public void setAction_list(List<Action_list> action_list) {
         this.action_list = action_list;
     }
     public List<Action_list> getAction_list() {
         return action_list;
     }

    public void setQu_res(Qu_res qu_res) {
         this.qu_res = qu_res;
     }
     public Qu_res getQu_res() {
         return qu_res;
     }

    public void setSchema(Schema schema) {
         this.schema = schema;
     }
     public Schema getSchema() {
         return schema;
     }

    public void setSession_id(String session_id) {
         this.session_id = session_id;
     }
     public String getSession_id() {
         return session_id;
     }

}