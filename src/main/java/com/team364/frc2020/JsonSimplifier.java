package com.team364.frc2020;

import java.io.FileWriter;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @param <K> K refers to Key
 * @param <V> V refers to Value
 * Write, Store, Read json files *simply* *
 */
public class JsonSimplifier<K, V> {
  /**
   * *PROGRAMMERS NOTE*(Colton)
   * Wow pretty generic class right? get it, haha yea im funny
   * Anyway, the point of this class is to make it easy to write, store, and read jsons.
   * As an added bonus this class also converts to maps so logic can be more effient.
   */

  JSONObject json;
  String name;
  Map<K, V> map;
  JSONParser parser;
  Class<K> genericKey;
  Class<V> genericValue;
  Class<Map<K, V>> generic;

  public JsonSimplifier(String name){
    this.name = name;

    try{
      this.json = new JSONObject();
      map = toMap();
    } catch(Exception e){}
  }

  public String getName() {
    return null;
  }

  public void writeElement(String key, V value){
    try{
      json.put(key, value);
    }catch(JSONException e){e.printStackTrace();}
  }

  public void replaceElement(String key, V value){
    try{
      json.remove(key);
      json.put(key, value);
    }catch(JSONException e){e.printStackTrace();}
  }

  public int length() {
    return json.length();
  }

  public V get(String key, V whichElement) throws JSONException {
    return genericValue.cast(json.get(key));
  }

  public Map<K, V> getMap(){
    return map;
  }
  private Map<K, V> toMap() throws ParseException {
    return generic.cast(parser.parse(json.toString()));
  }


  public void writeJson() {
    try {
      FileWriter fileWriter = new FileWriter(name, true);
      fileWriter.write(json.toString());
      fileWriter.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
