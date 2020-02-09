package com.team364.frc2020.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @param <K> K refers to Key
 * @param <V> V refers to Value
 * Write, Store, Read json files *simply*
 */
public class JsonSimplifier<K, V> {
  /**
   * *PROGRAMMERS NOTE*(Colton)
   * Wow pretty generic class right? get it, haha yea im funny
   * Anyway, the point of this class is to make it easy to write, store, and read jsons.
   * As an added bonus this class also converts to maps so logic can be more effient.
   */

  JSONObject json;
  Map<K, V> map;
  JSONParser parser;
  Class<K> genericKey;
  Class<V> genericValue;
  Class<Map<K, V>> generic;

  public JsonSimplifier(JSONObject json){
    this.json = json;
    try{
      map = toMap();
    } catch(Exception e){e.printStackTrace();}
  }

  public void writeElement(String key, V value) throws JSONException {
    json.put(key, value);
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
  public Map<K, V> toMap() throws ParseException {
    return generic.cast(parser.parse(json.toString()));
  }


  public void writeJson(String fileName) {
    try {
      FileWriter fileWriter = new FileWriter(fileName, true);
      fileWriter.write(json.toString());
      fileWriter.flush();
      fileWriter.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
