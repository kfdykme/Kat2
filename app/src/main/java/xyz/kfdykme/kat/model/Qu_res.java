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
public class Qu_res {

    private List<Intent_candidates> intent_candidates;
    private String log_id;
    private String raw_query;
    private int status;
    private int timestamp;
    public void setIntent_candidates(List<Intent_candidates> intent_candidates) {
         this.intent_candidates = intent_candidates;
     }
     public List<Intent_candidates> getIntent_candidates() {
         return intent_candidates;
     }

    public void setLog_id(String log_id) {
         this.log_id = log_id;
     }
     public String getLog_id() {
         return log_id;
     }

    public void setRaw_query(String raw_query) {
         this.raw_query = raw_query;
     }
     public String getRaw_query() {
         return raw_query;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setTimestamp(int timestamp) {
         this.timestamp = timestamp;
     }
     public int getTimestamp() {
         return timestamp;
     }

}