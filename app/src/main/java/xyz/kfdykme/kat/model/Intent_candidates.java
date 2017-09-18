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
public class Intent_candidates {

    private String extra_info;
    private String from_who;
    private String func_slot;
    private String intent;
    private double intent_confidence;
    private boolean intent_need_clarify;
    private String match_info;
    private List<Slots> slots;
    public void setExtra_info(String extra_info) {
         this.extra_info = extra_info;
     }
     public String getExtra_info() {
         return extra_info;
     }

    public void setFrom_who(String from_who) {
         this.from_who = from_who;
     }
     public String getFrom_who() {
         return from_who;
     }

    public void setFunc_slot(String func_slot) {
         this.func_slot = func_slot;
     }
     public String getFunc_slot() {
         return func_slot;
     }

    public void setIntent(String intent) {
         this.intent = intent;
     }
     public String getIntent() {
         return intent;
     }

    public void setIntent_confidence(double intent_confidence) {
         this.intent_confidence = intent_confidence;
     }
     public double getIntent_confidence() {
         return intent_confidence;
     }

    public void setIntent_need_clarify(boolean intent_need_clarify) {
         this.intent_need_clarify = intent_need_clarify;
     }
     public boolean getIntent_need_clarify() {
         return intent_need_clarify;
     }

    public void setMatch_info(String match_info) {
         this.match_info = match_info;
     }
     public String getMatch_info() {
         return match_info;
     }

    public void setSlots(List<Slots> slots) {
         this.slots = slots;
     }
     public List<Slots> getSlots() {
         return slots;
     }

}