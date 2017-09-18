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
public class Schema {

    private List<Bot_merged_slots> bot_merged_slots;
    private String current_qu_intent;
    private double intent_confidence;
    public void setBot_merged_slots(List<Bot_merged_slots> bot_merged_slots) {
         this.bot_merged_slots = bot_merged_slots;
     }
     public List<Bot_merged_slots> getBot_merged_slots() {
         return bot_merged_slots;
     }

    public void setCurrent_qu_intent(String current_qu_intent) {
         this.current_qu_intent = current_qu_intent;
     }
     public String getCurrent_qu_intent() {
         return current_qu_intent;
     }

    public void setIntent_confidence(double intent_confidence) {
         this.intent_confidence = intent_confidence;
     }
     public double getIntent_confidence() {
         return intent_confidence;
     }

}